package cz.novoj.ibatis.init;

import cz.novoj.ibatis.ProductMapper;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * Programmatic iBatis initialization - not XML here!
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class NoXmlSingletonConnectionManager {
	private static SqlSessionFactory sqlSessionFactory = null;
	private static final Object monitor = new Object();

	private NoXmlSingletonConnectionManager() {}

	public static SqlSessionFactory getSessionFactory() {
		if (sqlSessionFactory == null) {
			ClassLoader clsLoader = Thread.currentThread().getContextClassLoader();
			synchronized(monitor) {
				if (sqlSessionFactory == null) {
					Properties properties = readProperties(clsLoader);
					DataSource dataSource = new UnpooledDataSource(
							properties.getProperty("jdbc.driver"),
							properties.getProperty("jdbc.url"),
							properties.getProperty("jdbc.username"),
							properties.getProperty("jdbc.password")
					);
					TransactionFactory transactionFactory = new JdbcTransactionFactory();
					Environment environment =
					new Environment("development", transactionFactory, dataSource);
					Configuration configuration = new Configuration(environment);
					configuration.addMapper(ProductMapper.class);
					sqlSessionFactory =	new SqlSessionFactoryBuilder().build(configuration);
				}
			}
		}
		return sqlSessionFactory;
	}

	private static Properties readProperties(ClassLoader clsLoader) {
		Reader propertiesReader = new InputStreamReader(
				clsLoader.getResourceAsStream(
						"config/connection.properties"
				)
		);
		try {
			Properties properties = new Properties();
			properties.load(propertiesReader);
			return properties;
		}
		catch(IOException ex) {
			//should not happen
			throw new RuntimeException(ex);
		}
		finally {
			IOUtils.closeQuietly(propertiesReader);
		}
	}

}