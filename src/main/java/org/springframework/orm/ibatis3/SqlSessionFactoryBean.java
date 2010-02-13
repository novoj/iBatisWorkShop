package org.springframework.orm.ibatis3;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.ibatis.parsing.XNode;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link org.springframework.beans.factory.FactoryBean} that creates an iBATIS
 * {@link org.apache.ibatis.session.SqlSessionFactory}. This is the usual way to
 * set up a shared iBATIS SqlSessionFactory in a Spring application context; the
 * SqlSessionFactory can then be passed to iBATIS-based DAOs via dependency
 * injection.
 * 
 * <p>
 * Either
 * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager} or
 * {@link org.springframework.transaction.jta.JtaTransactionManager} can be used
 * for transaction demarcation in combination with a SqlSessionFactory, with JTA
 * only necessary for transactions which span multiple databases.
 * 
 * <p>
 * Allows for specifying a DataSource at the SqlSessionFactory level. This is
 * preferable to per-DAO DataSource references, as it allows for lazy loading
 * and avoids repeated DataSource references in every DAO.
 * 
 * @author Putthibong Boonbong
 * @since 3.0
 * @see #setConfigLocation
 * @see #setDataSource
 * @see org.springframework.orm.ibatis3.SqlSessionTemplate#setSqlSessionFactory
 * @see org.springframework.orm.ibatis3.SqlSessionTemplate#setDataSource
 */
public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean {

    private Resource[] configLocations;

    private Resource[] mapperLocations;

    private DataSource dataSource;

    private Class<? extends TransactionFactory> transactionFactoryClass = ManagedTransactionFactory.class;

    private Properties transactionFactoryProperties;

    private Properties sqlSessionFactoryProperties;

    private SqlSessionFactory sqlSessionFactory;

    private String environment = SqlSessionFactoryBean.class.getSimpleName();

    public SqlSessionFactoryBean() {}

    /**
     * Set the location of the iBATIS SqlSessionFactory config file. A typical
     * value is "WEB-INF/ibatis-configuration.xml".
     * 
     * @see #setConfigLocations
     */
    public void setConfigLocation(Resource configLocation) {
        this.configLocations = (configLocation != null ? new Resource[] { configLocation } : null);
    }

    /**
     * Set multiple locations of iBATIS SqlSessionFactory config files that are
     * going to be merged into one unified configuration at runtime.
     */
    public void setConfigLocations(Resource[] configLocations) {
        this.configLocations = configLocations;
    }

    /**
     * Set locations of iBATIS mapper files that are going to be merged into the
     * SqlSessionFactory configuration at runtime.
     * <p>
     * This is an alternative to specifying "&lt;sqlmapper&gt;" entries in an
     * iBATIS config file. This property being based on Spring's resource
     * abstraction also allows for specifying resource patterns here: e.g.
     * "/myApp/*-mapper.xml".
     * <p>
     */
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    /**
     * Set optional properties to be passed into the SqlSessionFactoryBuilder,
     * as alternative to a <code>&lt;properties&gt;</code> tag in the
     * sql-map-config.xml file. Will be used to resolve placeholders in the
     * config file.
     * 
     * @see #setConfigLocation
     * @see org.apache.ibatis.session.SqlSessionFactoryBuilder
     */
    public void setSqlSessionFactoryProperties(Properties sqlSessionFactoryProperties) {
        this.sqlSessionFactoryProperties = sqlSessionFactoryProperties;
    }

    /**
     * Set the JDBC DataSource that this instance should manage transactions
     * for. The DataSource should match the one used by the Hibernate
     * SessionFactory: for example, you could specify the same JNDI DataSource
     * for both.
     * <p>
     * A transactional JDBC Connection for this DataSource will be provided to
     * application code accessing this DataSource directly via DataSourceUtils
     * or DataSourceTransactionManager.
     * <p>
     * The DataSource specified here should be the target DataSource to manage
     * transactions for, not a TransactionAwareDataSourceProxy. Only data access
     * code may work with TransactionAwareDataSourceProxy, while the transaction
     * manager needs to work on the underlying target DataSource. If there's
     * nevertheless a TransactionAwareDataSourceProxy passed in, it will be
     * unwrapped to extract its target DataSource.
     * 
     * @see org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
     * @see org.springframework.jdbc.datasource.DataSourceUtils
     * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            // If we got a TransactionAwareDataSourceProxy, we need to perform
            // transactions for its underlying target DataSource, else data
            // access code won't see properly exposed transactions (i.e.
            // transactions for the target DataSource).
            this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        }
        else {
            this.dataSource = dataSource;
        }
    }

    /**
     * Set the iBATIS TransactionFactory class to use. Default is
     * <code>org.apache.ibatis.transaction.managed.ManagedTransactionFactory</code>
     * .
     * <p>
     * Will only get applied when using a Spring-managed DataSource. An instance
     * of this class will get populated with the given DataSource and
     * initialized with the given properties.
     * <p>
     * The default ManagedTransactionFactory is appropriate if there is external
     * transaction management that the SqlSession should participate in: be it
     * Spring transaction management, EJB CMT or plain JTA. This should be the
     * typical scenario. If there is no active transaction, SqlSession
     * operations will execute SQL statements non-transactionally.
     * <p>
     * JdbcTransactionFactory is only necessary when using connection retrieved
     * from the DataSource to manage the scope of transaction. If there is no
     * explicit transaction, SqlSession operations will automatically start a
     * transaction for their own scope (in contrast to the external transaction
     * mode, see above).
     * <p>
     * <b>It is strongly recommended to use iBATIS SqlSessions with Spring
     * transaction management (or EJB CMT).</b> In this case, the default
     * ManagedTransactionFactory is fine. Lazy loading and SqlSession operations
     * without explicit transaction demarcation will execute
     * non-transactionally.
     * <p>
     * 
     * @see #setDataSource
     * @see #setTransactionFactoryProperties(java.util.Properties)
     * @see org.apache.ibatis.transaction.TransactionFactory
     * @see org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
     * @see org.apache.ibatis.transaction.managed.ManagedTransactionFactory
     * @see org.apache.ibatis.transaction.Transaction
     */
    public void setTransactionFactoryClass(Class<TransactionFactory> transactionFactoryClass) {
        if (transactionFactoryClass == null) {
            throw new IllegalArgumentException("Invalid transactionFactoryClass: does not implement "
                    + "org.apache.ibatis.transaction.TransactionFactory");
        }
        this.transactionFactoryClass = transactionFactoryClass;
    }

    /**
     * Set properties to be passed to the TransactionFactory instance used by
     * this SqlSessionFactory.
     * 
     * @see org.apache.ibatis.transaction.TransactionFactory#setProperties(java.util.Properties)
     * @see org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
     * @see org.apache.ibatis.transaction.managed.ManagedTransactionFactory
     */
    public void setTransactionFactoryProperties(Properties transactionFactoryProperties) {
        this.transactionFactoryProperties = transactionFactoryProperties;
    }

    /**
     * <b>NOTE:</b> This class <em>overrides</em> any Environment you have set
     * in the iBATIS config file. This is used only as a placeholder name. The
     * default value is <code>SqlSessionFactoryBean.class.getSimpleName()</code>
     * .
     * 
     * @param environment
     *            the environment name
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void afterPropertiesSet() throws Exception {
        this.sqlSessionFactory = buildSqlSessionFactory(this.configLocations, this.mapperLocations,
                this.sqlSessionFactoryProperties);
    }

    /**
     * Build a SqlSessionFactory instance based on the given standard
     * configuration.
     * <p>
     * The default implementation uses the standard iBATIS
     * {@link XMLConfigBuilder} API to build a SqlSessionFactory instance based
     * on an Reader.
     * 
     * @param configLocations
     *            the config files to load from
     * @param mapperLocations
     *            the mapper files to load from
     * @param properties
     *            the SqlSessionFactory properties (if any)
     * @return the SqlSessionFactory instance (never <code>null</code>)
     * @throws IOException
     *             if loading the config file failed
     * @see org.apache.ibatis.builder.xml.XMLConfigBuilder#parse()
     */
    protected SqlSessionFactory buildSqlSessionFactory(Resource[] configLocations, Resource[] mapperLocations,
            Properties properties) throws IOException, IllegalAccessException, InstantiationException {

        if (ObjectUtils.isEmpty(configLocations)) {
            throw new IllegalArgumentException("At least one 'configLocation' entry is required");
        }

        XMLConfigBuilder xmlConfigBuilder = null;
        Configuration configuration = null;

        for (Resource configLocation : configLocations) {
            try {
                Reader reader = new InputStreamReader(configLocation.getInputStream());
                xmlConfigBuilder = new XMLConfigBuilder(reader, null, properties);
                configuration = xmlConfigBuilder.parse();
            }
            catch (IOException ex) {
                throw new NestedIOException("Failed to parse config resource: " + configLocation, ex.getCause());
            }
        }

        assert configuration != null;

        if (this.dataSource != null) {
            TransactionFactory transactionFactory = (TransactionFactory) this.transactionFactoryClass.newInstance();
            transactionFactory.setProperties(transactionFactoryProperties);
            Environment environment = new Environment(this.environment, transactionFactory, this.dataSource);

            configuration.setEnvironment(environment);
        }

        if (mapperLocations != null) {
            Map<String, XNode> sqlFragments = new HashMap<String, XNode>();
            for (Resource mapperLocation : mapperLocations) {
                try {
                    Reader reader = new InputStreamReader(mapperLocation.getInputStream());
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(reader, configuration, mapperLocation
                            .toString(), sqlFragments);
                    xmlMapperBuilder.parse();
                }
                catch (Exception ex) {
                    throw new NestedIOException("Failed to parse mapping resource: " + mapperLocation, ex);
                }
            }
        }

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    public SqlSessionFactory getObject() throws Exception {
        return sqlSessionFactory;
    }

    public Class<? extends SqlSessionFactory> getObjectType() {
        return sqlSessionFactory == null ? SqlSessionFactory.class : sqlSessionFactory.getClass();
    }

    public boolean isSingleton() {
        return true;
    }
}
