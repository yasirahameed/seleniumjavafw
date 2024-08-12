package tests;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import utilities.ConfigLoaderUtil;
import utilities.DriverFactoryUtil;

/**
 * BaseClass class is the starting point of testing. It defines the Pre and Post
 * testing configurations.
 */
public class BaseClass {

	protected WebDriver driver; // Web Driver variable
	private static final Logger logger = LogManager.getLogger(BaseClass.class); // logger variable used for logging

	// ExtentReports variables
	protected ExtentReports extent;

	/**
	 * Method used to setup, the browser extent report
	 * 
	 */

	@BeforeSuite
	public void setUp() {
		try {
			logger.info(
					"calling ConfigLoaderUtil.getProperty(key) method and passing key=browser to get browser name to be used for testing.");
			String browser = ConfigLoaderUtil.getProperty("browser"); // getting browser name from config.properties
																		// file
			logger.info("Config file has browser = " + browser);

			// getting the right browser object initialized
			logger.info("Using switch statement to initialize the driver object with browser " + browser);

			switch (browser.toLowerCase()) {
			case "chrome":
				driver = DriverFactoryUtil.getChromeDriver();
				logger.info("Chrome Driver initialized");
				break;
			case "firefox":
				driver = DriverFactoryUtil.getFirefoxDriver();
				logger.info("Firefox Driver initialized");
				break;
			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}
			// Set an implicit wait time of 10 seconds
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
			logger.info("After Driver initialization waiting for 1 sec");

			// baseURL value from config.properties file
			try {
				logger.info("Getting initial URL to start testing");
				driver.get(ConfigLoaderUtil.getProperty("baseURL"));
			} catch (RuntimeException e) {
				logger.error("Getting initial URL failed");
				throw e;
			}

		} catch (Exception e) {
			logger.error("Error initializing driver: ", e);
			throw e;
		}

		// Initialize ExtentReports
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark/Spark.html");
		// ExtentReports report
		extent.attachReporter(spark);
	}

	/**
	 * Method used to release all resources at the end of the testing
	 **/
	@AfterSuite
	public void tearDown() {
		try {
			if (driver != null) {
				driver.quit(); // quitting the web driver
				extent.flush(); // call to flush() method make sure that report data is written to the file
								// system
			}
		} catch (Exception e) {
			logger.error("Error quitting driver: ", e);
		}
	}
}
