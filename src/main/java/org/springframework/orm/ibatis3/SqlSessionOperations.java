package org.springframework.orm.ibatis3;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

/**
 * Interface that specifies a basic set of iBATIS SqlSession operations,
 * implemented by {@link SqlSessionTemplate}. Not often used, but a useful
 * option to enhance testability, as it can easily be mocked or stubbed.
 *
 * <p>Defines SqlSessionTemplate's convenience methods that mirror
 * the iBATIS {@link org.apache.ibatis.session.SqlSession}'s execution
 * methods. Users are strongly encouraged to read the iBATIS javadocs
 * for details on the semantics of those methods.
 *
 * @author Putthibong Boonbong
 * @since 3.0
 * @see org.springframework.orm.ibatis3.SqlSessionTemplate
 * @see org.apache.ibatis.session.SqlSession
 */
public interface SqlSessionOperations {

	/**
	 * @see org.apache.ibatis.session.SqlSession#selectOne(String)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    Object selectOne(String statement);

	/**
	 * @see org.apache.ibatis.session.SqlSession#selectOne(String, Object)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    Object selectOne(String statement, Object parameter);

	/**
	 * @see org.apache.ibatis.session.SqlSession#selectList(String, Object)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    @SuppressWarnings("unchecked")
    List selectList(String statement);

	/**
	 * @see org.apache.ibatis.session.SqlSession#selectList(String, Object)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    @SuppressWarnings("unchecked")
    List selectList(String statement, Object parameter);

	/**
	 * @see org.apache.ibatis.session.SqlSession#selectList(String, Object, org.apache.ibatis.session.RowBounds)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    @SuppressWarnings("unchecked")
    List selectList(String statement, Object parameter, RowBounds rowBounds);

	/**
	 * @see org.apache.ibatis.session.SqlSession#select(String, Object, org.apache.ibatis.session.ResultHandler)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    void select(String statement, Object parameter, ResultHandler handler);

	/**
	 * @see org.apache.ibatis.session.SqlSession#select(String, Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

	/**
	 * @see org.apache.ibatis.session.SqlSession#insert(String)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    int insert(String statement);

	/**
	 * @see org.apache.ibatis.session.SqlSession#insert(String, Object)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    int insert(String statement, Object parameter);

	/**
	 * @see org.apache.ibatis.session.SqlSession#update(String)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    int update(String statement);

	/**
	 * @see org.apache.ibatis.session.SqlSession#update(String, Object)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    int update(String statement, Object parameter);

	/**
	 * @see org.apache.ibatis.session.SqlSession#delete(String)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    int delete(String statement);

	/**
	 * @see org.apache.ibatis.session.SqlSession#delete(String, Object)
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    int delete(String statement, Object parameter);

	/**
	 * @see org.apache.ibatis.session.SqlSession#getMapper(Class) 
	 * @throws org.springframework.dao.DataAccessException in case of errors
	 */
    <T> T getMapper(Class<T> type);
}