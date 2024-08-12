package pom;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import utilities.ScreenshotUtil;

/**
 * LoginPage class POM to handle elements and actions related to Login web page
 */
public class LoginPage {
	private static final Logger logger = LogManager.getLogger(LoginPage.class);
	WebDriver driver;
	
    // Define locators using @FindBy annotation
	//WebElement username is a text box and located by name
    @FindBy(name = "username")
    private WebElement usernameField;

  //WebElement password is a text box and located by name
    @FindBy(name = "password")
    private WebElement passwordField;

  //WebElement loginButton is a button and located by className
    @FindBy(className = "orangehrm-login-button")
    private WebElement loginButton;


    /**
     * Constructor to initialize the elements
     * @param driver
     */
    public LoginPage(WebDriver driver) {    
    	this.driver = driver;
        PageFactory.initElements(driver, this); //initializing WebElement fields in Login POM Object class
    }

    /**
     * Method used to perform the login action
     * @param username
     * @param password
     * @param extent
     */
    public void login(String username, String password,ExtentReports extent) {
    	try {
    		
        usernameField.sendKeys(username); //entering username
        logger.info("username entered");
        passwordField.sendKeys(password);//entering password
        logger.info("password entered");
        logger.info("Selecting Login Button"); //selecting login button to init the login action
        loginButton.click();
    	}
    	catch(NoSuchElementException e) { // if login fails
    		// Generate a timestamp for the screenshot filename
			 String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

			// Define the screenshot file path
			 String screenshotFilePath = ScreenshotUtil.getScreenshotFilePath() + timestamp	+ ".png";

			// Take the screenshot
			ScreenshotUtil.takeScreenshot(driver, screenshotFilePath);
						
			Throwable t = new NoSuchElementException("Issuing while trying to log-in");		
					
			ExtentTest test = extent.createTest("POM-LoginPage").addScreenCaptureFromPath(screenshotFilePath)
					.fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
			
			test.fail(MarkupHelper.createLabel("Unable to locate login page controls...Missing/Invlaid URL or controls", ExtentColor.RED));
			
			 test.fail(t);
			
			logger.error("Test failed: ", e);
			throw e;
    	}
    }
}
