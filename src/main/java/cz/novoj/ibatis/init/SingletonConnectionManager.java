package cz.novoj.ibatis.init;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * iBatis initialization by XML configuration.
 *
 * @author Jan Novotný
 * @version $Id: $
 */
public class SingletonConnectionManager {
	private static SqlSessionFactory sqlSessionFactory = null;
	private static final Object monitor = new Object();

	private SingletonConnectionManager() {}

	public static SqlSessionFactory getSessionFactory() {		
		if (sqlSessionFactory == null) {
			ClassLoader clsLoader = Thread.currentThread().getContextClassLoader();
			synchronized(monitor) {
				if (sqlSessionFactory == null) {
					Properties properties = readConnectionProperties(clsLoader);

					Reader mapperConfigReader = new InputStreamReader(
							clsLoader.getResourceAsStream("META-INF/iBatis/MapperConfig.xml")
					);
					try{
						SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
						sqlSessionFactory = sqlSessionFactoryBuilder.build(mapperConfigReader, properties);
					} finally {
						IOUtils.closeQuietly(mapperConfigReader);
					}
				}
			}
		}
		return sqlSessionFactory;
	}

	private static Properties readConnectionProperties(ClassLoader clsLoader) {
		Properties properties;
		Reader propertiesReader = new InputStreamReader(
				clsLoader.getResourceAsStream(
						"config/connection.properties"
				)
		);
		try {
			properties = new Properties();
			properties.load(propertiesReader);
		}
		catch(IOException ex) {
			//should not happen
			throw new RuntimeException(ex);
		}
		finally {
			IOUtils.closeQuietly(propertiesReader);
		}
		return properties;
	}

}
