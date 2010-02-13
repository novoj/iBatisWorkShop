package org.springframework.orm.ibatis3;

import org.apache.ibatis.session.SqlSession;
import javax.sql.DataSource;

import org.springframework.transaction.support.ResourceHolderSupport;

public final class SqlSessionHolder extends ResourceHolderSupport {
    private final SqlSession session;
    private final DataSource dataSource;

    public SqlSessionHolder(SqlSession session, DataSource dataSource) {
        assert session != null;
        this.session = session;
        this.dataSource = dataSource;
    }

    public SqlSession getSqlSession() {
        return session;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
