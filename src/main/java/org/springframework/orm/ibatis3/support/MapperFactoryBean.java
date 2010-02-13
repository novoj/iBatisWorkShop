package org.springframework.orm.ibatis3.support;

import org.springframework.beans.factory.FactoryBean;

/**
 * FactoryBean that enables injection of iBATIS Mapper Interfaces.
 *  
 * @author Eduardo Macarrón
 * @since 3.0
 * @see #SqlSessionDaoSupport
 * @see #setSqlSessionTemplate
 * @see org.springframework.orm.ibatis3.support.SqlSessionDaoSupport
 * @see org.springframework.orm.ibatis3.SqlSessionTemplate
 */

public class MapperFactoryBean extends SqlSessionDaoSupport implements FactoryBean<Object> {

    private Class<?> mapperInterface;

    public void setMapperInterface(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object getObject() throws Exception {
    	return getSqlSessionTemplate().getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
