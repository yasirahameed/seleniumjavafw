package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigLoaderUtil class used to load configuration settings from
 * src/test/resources/config.properties file
 */
public class ConfigLoaderUtil {
	private static final Logger logger = LogManager.getLogger(ConfigLoaderUtil.class); // logger variable used for
																						// logging
	private static Properties properties = new Properties(); // Properties object init

	// Static blocks used to init static variable properties
	static {
		logger.info("loading properties from config.properties file in src/test/resources/");
		loadProperties("config.properties");
	}

	/**
	 * Method used to read the configuration file from src/test/resources
	 * 
	 * @param fileName
	 */
	private static void loadProperties(String fileName) {
		logger.info("In loadProperties reading the config file into input stream");
		try (InputStream input = ConfigLoaderUtil.class.getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				logger.info("Sorry, unable to find " + fileName);
				return;
			}
			logger.info("loading properties inot properties object");
			properties.load(input); // loading properties
		} catch (IOException ex) {
			logger.error("config file reading error.");
			ex.printStackTrace();
		}
	}

	/**
	 * Method to get a specific key value from the config file and return it to the
	 * call method
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Method to get a specific key value from the config file and return it to the
	 * call method
	 * 
	 * @param key
	 * @return
	 */
	public static int getIntProperty(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}
}
