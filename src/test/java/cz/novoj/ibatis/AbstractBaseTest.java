package cz.novoj.ibatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 *
 * @author Jan Novotný, FG Forrest a.s. (c) 2007
 * @version $Id: $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				"classpath:spring/testSetup.xml",
				"classpath:META-INF/spring/datasource.xml",
				"classpath:META-INF/spring/ibatis-integration.xml"
		}
)
public abstract class AbstractBaseTest {
	public static final String DATABASE_CREATE_SQL = "database/create.sql";
	public static final String DATABASE_DROP_SQL = "database/drop.sql";
	private static final String DATABASE_DATALOAD_SQL = "database/test-data.sql";
	@Autowired(required = true)
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "dataSource")	
	private DataSource dataSource;

	@Before
	public void setupDatabase() throws Exception {
		runScript(dataSource, DATABASE_CREATE_SQL);
		runScript(dataSource, DATABASE_DATALOAD_SQL);
	}

	@After
	public void dropDatabase() throws Exception {
		runScript(dataSource, DATABASE_DROP_SQL);
	}

	protected static void runScript(DataSource ds, String resource) throws IOException, SQLException {
		Connection connection = ds.getConnection();
		try {
			ScriptRunner runner = new ScriptRunner(connection);
			runner.setAutoCommit(true);
			runner.setStopOnError(false);
			runner.setLogWriter(null);
			runScript(runner, resource);
		}
		finally {
			connection.close();
		}
	}

	protected static void runScript(ScriptRunner runner, String resource) throws IOException, SQLException {
		Reader reader = Resources.getResourceAsReader(resource);
		try {
			runner.runScript(reader);
		} finally {
			reader.close();
		}
	}

	protected int countRowsInTable(String tableName) {
		return jdbcTemplate.queryForInt("select count(*) from " + tableName);
	}
}
