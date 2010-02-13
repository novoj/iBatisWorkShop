package org.springframework.orm.ibatis3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;

import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.dao.CleanupFailureDataAccessException;

import org.springframework.util.Assert;

// note this class does not translate IBatisException to DataSourceException since iBATIS now uses runtime exceptions
public final class SqlSessionUtils {
    private static final Log logger = LogFactory.getLog(SqlSessionUtils.class);

    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory) {
        DataSource dataSource = sessionFactory.getConfiguration().getEnvironment().getDataSource();
        ExecutorType executorType = sessionFactory.getConfiguration().getDefaultExecutorType();
        return getSqlSession(sessionFactory, dataSource, executorType);
    }

    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory, DataSource dataSource) {
        ExecutorType executorType = sessionFactory.getConfiguration().getDefaultExecutorType();
        return getSqlSession(sessionFactory, dataSource, executorType);
    }

    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory, ExecutorType executorType) {
        DataSource dataSource = sessionFactory.getConfiguration().getEnvironment().getDataSource();
        return getSqlSession(sessionFactory, dataSource, executorType);
    }

    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory, DataSource dataSource,
            ExecutorType executorType) {
        // either return the existing SqlSession or create a new one
        SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

        if (holder != null && holder.isSynchronizedWithTransaction()) {
            holder.requested();
            logger.debug("Fetching SqlSession from current transaction");
            return holder.getSqlSession();
        }

        boolean transactionAware = (dataSource instanceof TransactionAwareDataSourceProxy);
        Connection con = null;

        try {
            con = transactionAware ? dataSource.getConnection() : DataSourceUtils.getConnection(dataSource);
        }
        catch (SQLException sqle) {
            throw new CannotGetJdbcConnectionException("Could not get JDBC Connection for SqlSession", sqle);
        }

        if (!usesManagedTransactions(sessionFactory.getConfiguration())
                && DataSourceUtils.isConnectionTransactional(unwrapConnection(holder.getSqlSession()), dataSource)) {
            throw new IllegalTransactionStateException(
                    "Pre-bound JDBC Connection found! iBATIS SqlSession does not support "
                            + "running within DataSourceTransactionManager if told to manage the DataSource itself.");
        }

        logger.debug("Creating SqlSession from SqlSessionFactory");

        // assume either DataSourceTransactionManager or the underlying
        // connection pool already dealt with enabling auto commit.
        // This may not be a good assumption, but the overhead of checking
        // connection.getAutoCommit() again may be expensive (?) in some drivers
        // (see DataSourceTransactionManager.doBegin()). One option would be to
        // only check for auto commit if this function is being called outside
        // of DSTxMgr, but to do that we would need to be able to call
        // ConnectionHolder.isTransactionActive(), which is protected and not
        // visible to this class.
        SqlSession session = sessionFactory.openSession(executorType, con);

        // register session holder and bind it to enable synchronization
        // note the DataSource should be synchronized with the transaction
        // either through DataSourceTxMgr or another tx synchronization
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            logger.debug("Registering transaction synchronization for SqlSession");
            holder = new SqlSessionHolder(session, dataSource);
            TransactionSynchronizationManager.bindResource(sessionFactory, holder);
            TransactionSynchronizationManager.registerSynchronization(new SqlSessionSynchronization(holder,
                    sessionFactory, dataSource));
            holder.setSynchronizedWithTransaction(true);
            holder.requested();
        }

        return session;
    }

    public static void commitSqlSession(SqlSession session, SqlSessionFactory sessionFactory) throws SQLException {
        commitSqlSession(session, sessionFactory, false);
    }

    public static void commitSqlSession(SqlSession session, SqlSessionFactory sessionFactory, boolean force)
            throws SQLException {
        SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

        if (holder == null) {
            // assume the configured DataSource created the session connection
            if (shouldManageConnection(session, session.getConfiguration().getEnvironment().getDataSource())) {
                logger.debug("Commiting non-transactional SqlSession JDBC Connection");
                session.getConnection().commit();
            }

            logger.debug("Commiting non-transactional SqlSession");
            session.commit(force);
        }
        // else assume transaction will handle commit
    }

    public static void rollbackSqlSession(SqlSession session, SqlSessionFactory sessionFactory) throws SQLException {
        rollbackSqlSession(session, sessionFactory);
    }

    public static void rollbackSqlSession(SqlSession session, SqlSessionFactory sessionFactory, boolean force)
            throws SQLException {
        SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

        if (holder == null) {
            // assume the configured DataSource created the session connection
            if (shouldManageConnection(session, session.getConfiguration().getEnvironment().getDataSource())) {
                logger.debug("Rolling back non-transactional SqlSession JDBC Connection");
                session.getConnection().commit();
            }

            logger.debug("Rolling back non-transactional SqlSession");
            session.rollback(force);
        }
        else {
            holder.setRollbackOnly();
        }
    }

    public static void closeSqlSession(SqlSession session, SqlSessionFactory sessionFactory) {
        SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

        if ((holder == null) || (session != holder.getSqlSession())) {
            // assume the configured DataSource created the session connection
            if (shouldManageConnection(session, session.getConfiguration().getEnvironment().getDataSource())) {
                logger.debug("Closing non-transactional SqlSession JDBC Connection");
                DataSourceUtils.releaseConnection(session.getConnection(), null);
            }
            session.close();
        }
        else {
            DataSourceUtils.releaseConnection(unwrapConnection(session), holder.getDataSource());
            holder.released();
            // assume transaction synchronization will actually close session
        }
    }

    public static boolean shouldManageConnection(SqlSession session, DataSource dataSource) {
        Configuration configuration = session.getConfiguration();

        if (usesManagedTransactions(configuration)) {
            if (DataSourceUtils.isConnectionTransactional(unwrapConnection(session), dataSource)) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            if (DataSourceUtils.isConnectionTransactional(unwrapConnection(session), dataSource)) {
                throw new IllegalStateException(
                        "iBATIS is not using ManagedTransactions and the current JDBC Connection is transactional. Cannot determine what to do with the current Connection.");
            }
            else {
                return false;
            }
        }
    }

    public static boolean usesManagedTransactions(Configuration configuration) {
        return configuration.getEnvironment().getTransactionFactory().getClass().isAssignableFrom(
                ManagedTransactionFactory.class);
    }

    public static boolean isSqlSessionTransactional(SqlSession session, SqlSessionFactory sessionFactory) {
        if (sessionFactory == null) {
            return false;
        }

        SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

        return (holder != null) && (holder.getSqlSession() == session);
    }

    private static Connection unwrapConnection(SqlSession session) {
        // unwrap the sql session connection that may be a logging Proxy
        // otherwise the DataSourceUtils.connectionEquals method will always
        // return false
        Connection con = session.getConnection();

        if (Proxy.isProxyClass(con.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(con);

            if (handler instanceof ConnectionLogger) {
                con = ((ConnectionLogger) handler).getConnection();
            }
        }

        return con;
    }

    private static final class SqlSessionSynchronization extends TransactionSynchronizationAdapter {
        private final SqlSessionHolder holder;
        private final SqlSessionFactory sessionFactory;
        private final boolean manageConnection;

        public SqlSessionSynchronization(SqlSessionHolder holder, SqlSessionFactory sessionFactory,
                DataSource dataSource) {
            Assert.notNull(holder);
            Assert.notNull(sessionFactory);
            this.holder = holder;
            this.sessionFactory = sessionFactory;
            // close or rollback the session's connection only if iBatis is not
            // managing the transaction and the connection is not being managed
            // by some other Spring transaction
            this.manageConnection = usesManagedTransactions(holder.getSqlSession().getConfiguration())
                    && !DataSourceUtils.isConnectionTransactional(unwrapConnection(holder.getSqlSession()), dataSource);

        }

        @Override
        public int getOrder() {
            // order right after any Connection synchronization
            return DataSourceUtils.CONNECTION_SYNCHRONIZATION_ORDER + 1;
        }

        @Override
        public void suspend() {
            TransactionSynchronizationManager.unbindResource(sessionFactory);

        }

        @Override
        public void resume() {
            TransactionSynchronizationManager.bindResource(sessionFactory, holder.getSqlSession());
        }

        @Override
        public void afterCommit() {
            try {
                if (manageConnection) {
                    logger.debug("Transaction synchronization committing JDBC Connection");
                    holder.getSqlSession().getConnection().commit();
                }
            }
            catch (SQLException sqle) {
                throw new CleanupFailureDataAccessException("SqlSessionSynchronization could not commit", sqle);
            }
            finally {
                logger.debug("Transaction synchronization committing SqlSession");
                holder.getSqlSession().commit(true);
            }
        }

        @Override
        public void afterCompletion(int status) {
            try {
                if (status != STATUS_COMMITTED) {
                    logger.debug("Transaction synchronization rolling back SqlSession");
                    holder.getSqlSession().rollback(true);

                    if (manageConnection) {
                        logger.debug("Transaction synchronization rolling back JDBC Connection");
                        holder.getSqlSession().getConnection().rollback();
                    }
                }
            }
            catch (SQLException sqle) {
                throw new CleanupFailureDataAccessException("SqlSessionSynchronization could not rollback", sqle);
            }
            finally {
                if (!holder.isOpen()) {
                    logger.debug("Transaction synchronization closing SqlSession");
                    TransactionSynchronizationManager.unbindResource(sessionFactory);
                    holder.getSqlSession().close();
                }
            }
        }
    }
}
