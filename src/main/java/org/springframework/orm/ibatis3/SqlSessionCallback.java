package org.springframework.orm.ibatis3;

import java.sql.SQLException;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.SqlSession;

/**
 * Callback interface for data access code that works with the iBATIS
 * {@link Executor} interface. To be used
 * with {@link SqlSessionTemplate}'s <code>execute</code> method,
 * assumably often as anonymous classes within a method implementation.
 *
 * @author Putthibong Boonbong
 * @since 3.0
 * @see SqlSessionTemplate
 * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
 */
public interface SqlSessionCallback<T> {

	/**
	 * Gets called by <code>SqlSessionTemplate.execute</code> with an active
	 * <code>SqlSession</code>. Does not need to care about activating
	 * or closing the <code>SqlSession </code>, or handling transactions.
	 *
	 * <p>If called without a thread-bound JDBC transaction (initiated by
	 * DataSourceTransactionManager), the code will simply get executed on the
	 * underlying JDBC connection with its transactional semantics. If using
	 * a JTA-aware DataSource, the JDBC connection and thus the callback code
	 * will be transactional if a JTA transaction is active.
	 *
	 * <p>Allows for returning a result object created within the callback,
	 * i.e. a domain object or a collection of domain objects.
	 * A thrown custom RuntimeException is treated as an application exception:
	 * It gets propagated to the caller of the template.
	 *
	 * @param sqlSession an active iBATIS SqlSession, passed-in as
	 * Executor interface here to avoid manual lifecycle handling
	 * @return a result object, or <code>null</code> if none
	 * @throws SQLException if thrown by the iBATIS SQL Maps API
	 * @see SqlSessionTemplate#execute
	 */
    T doInSqlSession(SqlSession sqlSession) throws Exception;
}
