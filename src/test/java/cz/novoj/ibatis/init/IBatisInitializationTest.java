package cz.novoj.ibatis.init;

import cz.novoj.ibatis.infrastructure.AbstractBaseTest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * These tests show 3 ways of iBatis initialization: XML, programmatic, Spring.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class IBatisInitializationTest extends AbstractBaseTest {
	@Autowired(required = true)
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * This test shows how iBatis could be initialized by XML definition.
	 * This is the common way of iBatis usage.
	 * @throws Exception
	 */
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

	/**
	 * This test shows how iBatis could be initialized by pure programmatic approach.
	 * @throws Exception
	 */
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

	/**
	 * This test shows how iBatis could be initialized by Spring configuration with combination of XML definition.
	 * This is the solution proposal for Spring Framework 3.1 from the issue SPR-5991.
	 * @throws Exception
	 */
	@Test
	public void testIBatisSpringInit() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[]{
						"classpath:spring/testSetup.xml",
						"classpath:META-INF/spring/datasource.xml",
						"classpath:META-INF/spring/ibatis-integration.xml"
				}
		);
		SqlSessionFactory fct = (SqlSessionFactory)ctx.getBean("sqlSessionFactory");
		SqlSession session = fct.openSession();
		try {
			assertNotNull(session.getConnection());
		} finally {
			session.close();
		}
	}

}
