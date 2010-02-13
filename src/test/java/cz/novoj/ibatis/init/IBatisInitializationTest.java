package cz.novoj.ibatis.init;

import cz.novoj.ibatis.AbstractBaseTest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * These tests show 3 ways of iBatis initialization: XML, programmatic, Spring.
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
public class IBatisInitializationTest extends AbstractBaseTest {
	@Autowired(required = true)
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testIBatisXmlInit() throws Exception {
		SqlSessionFactory fct = SingletonConnectionManager.getSessionFactory();
		SqlSession session = fct.openSession();
		try {
			assertNotNull(session.getConnection());
		} finally {
			session.close();
		}
	}

	@Test
	public void testIBatisProgrammaticInit() throws Exception {
		SqlSessionFactory fct = NoXmlSingletonConnectionManager.getSessionFactory();
		SqlSession session = fct.openSession();
		try {
			assertNotNull(session.getConnection());
		} finally {
			session.close();
		}
	}

	@Test
	public void testIBatisSpringInit() throws Exception {
		SqlSessionFactory fct = NoXmlSingletonConnectionManager.getSessionFactory();
		SqlSession session = fct.openSession();
		try {
			assertNotNull(session.getConnection());
		} finally {
			session.close();
		}
	}

}
