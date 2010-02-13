package org.springframework.orm.ibatis3.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.ibatis3.SqlSessionTemplate;
import org.springframework.util.Assert;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;

/**
 * Convenient super class for iBATIS SqlSession data access objects. Requires a
 * SqlSession to be set, providing a SqlSessionTemplate based on it to
 * subclasses.
 * 
 * <p>
 * Instead of a plain SqlSession, you can also pass a preconfigured
 * SqlSessionTemplate instance in. This allows you to share your
 * SqlSessionTemplate configuration for all your DAOs, for example a custom
 * SQLExceptionTranslator to use.
 * 
 * @author Putthibong Boonbong
 * @since 3.0
 * @see #setSqlSessionFactory
 * @see #setSqlSessionTemplate
 * @see org.springframework.orm.ibatis3.SqlSessionTemplate
 * @see org.springframework.orm.ibatis3.SqlSessionTemplate#setExceptionTranslator
 */
public abstract class SqlSessionDaoSupport extends DaoSupport {
    private SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate();

    private boolean externalTemplate = false;

    /**
     * Set the JDBC DataSource to be used by this DAO. Not required: The
     * SqlSession might carry a shared DataSource.
     * 
     * @see #setSqlSessionFactory
     */
    public final void setDataSource(DataSource dataSource) {
        if (!this.externalTemplate) {
            this.sqlSessionTemplate.setDataSource(dataSource);
        }
    }

    /**
     * Return the JDBC DataSource used by this DAO.
     */
    public final DataSource getDataSource() {
        return this.sqlSessionTemplate.getDataSource();
    }

    /**
     * Set the iBATIS Database Layer SqlSession to work with. Either this or a
     * "SqlSessionTemplate" is required.
     * 
     * @see #setSqlSessionTemplate
     */
    @Autowired
    public final void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        if (!this.externalTemplate) {
            this.sqlSessionTemplate.setSqlSessionFactory(sqlSessionFactory);
        }
    }

    /**
     * Return the iBATIS Database Layer SqlSession that this template works
     * with.
     */
    public final SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionTemplate.getSqlSessionFactory();
    }

    /**
     * Set the SqlSessionTemplate for this DAO explicitly, as an alternative to
     * specifying a SqlSession.
     * 
     * @see #setSqlSessionFactory
     */
    public final void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        Assert.notNull(sqlSessionTemplate, "SqlSessionTemplate must not be null");
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.externalTemplate = true;
    }

    /**
     * Return the SqlSessionTemplate for this DAO, pre-initialized with the
     * SqlSession or set explicitly.
     */
    public final SqlSessionTemplate getSqlSessionTemplate() {
        return this.sqlSessionTemplate;
    }

    protected void checkDaoConfig() {
        if (!this.externalTemplate) {
            this.sqlSessionTemplate.afterPropertiesSet();
        }
    }

}
