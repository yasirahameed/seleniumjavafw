package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import pom.HomePage;
import pom.LoginPage;
import utilities.ConfigLoaderUtil;
import utilities.ScreenshotUtil;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class LoginPageTest extends BaseClass {
	private LoginPage loginPage; // LoginPage object
	private HomePage homePage; // HomePage object
	private static final Logger logger = LogManager.getLogger(LoginPageTest.class);
	String timestamp = null;	
	String screenshotFilePath = null;
	
	// loginTest to test login functionality
	@Test
	public void loginTest() {
		
			// Object creation and initialization of login page elements
			loginPage = new LoginPage(driver);
			logger.info("Created new instance of Login POM");

			// Method call to test login functionality
			logger.info("Getting username and password from resources/config/properties, and passing"
					+ " to login method in Login Page POM");
			
			
			loginPage.login(ConfigLoaderUtil.getProperty("username"), ConfigLoaderUtil.getProperty("password"),extent);
			
			// Object creation and initialization of home page elements
			logger.info("If username and password is correct then driver holds HomePage URL");
			homePage = new HomePage(driver);
			logger.info("Created new instance of HomePage POM using driver");
			  // Define the wait time
           
			// Assert the home page title to verify login success
			try {
				 // Define the wait time
				// Set an explicit wait time of 10 seconds
			    WebDriverWait wait1Sec = new WebDriverWait(driver, Duration.ofSeconds(1));

		        // Wait for an element to be present and interactable
		        WebElement element = wait1Sec.until(ExpectedConditions.presenceOfElementLocated(By.className("oxd-topbar-header-userarea")));
		        
				logger.info("AssertEquals HomePage Title to check if we had successfully logged into Home Page");
				Assert.assertEquals(homePage.getHomePageTitle(), "OrangeHRM");
				logger.info("Successful Login.");

				// Generate a timestamp for the screenshot filename
				 timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

				// Define the screenshot file path
				 screenshotFilePath = ScreenshotUtil.getScreenshotFilePath() + timestamp	+ ".png";

				// Take the screenshot
				 logger.info("Taking screen shot of the home page.");
				ScreenshotUtil.takeScreenshot(driver, screenshotFilePath);

				//screenshotFilePath = System.getProperty("user.dir");
				logger.info("adding test case entry into extent reports.");
				extent.createTest("Login Test").addScreenCaptureFromPath(screenshotFilePath)
						.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());

				// extent.createTest("Login").pass("Login Test Passed");
			} catch (AssertionError e) {
				// Generate a timestamp for the screenshot filename
				 timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

				// Define the screenshot file path
				 screenshotFilePath = ScreenshotUtil.getScreenshotFilePath() + timestamp + ".png";

				// Take the screenshot
				ScreenshotUtil.takeScreenshot(driver, screenshotFilePath);

				ExtentTest test = extent.createTest("Login Test").addScreenCaptureFromPath(screenshotFilePath)
						.fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
				test.fail(MarkupHelper.createLabel("Login was successful, but something went wrong while navigation to main page", ExtentColor.RED));
				
				
				logger.error("Test failed: ", e);
				throw e;
			}

		
	}
}
