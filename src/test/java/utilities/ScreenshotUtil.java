package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * ScreenshotUtil class used to handle taking screen shots
 */
public class ScreenshotUtil {
	private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class); // logger variable used for logging


	/**
	 * Method to take screen shot and same to the provided filePath
	 * @param driver
	 * @param filePath
	 */
    public static void takeScreenshot(WebDriver driver, String filePath) {
        // Cast driver to TakesScreenshot
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        // Take the screenshot
        File screenshotFile = screenshotTaker.getScreenshotAs(OutputType.FILE);
        try {
            // Create the destination file path
            File destinationFile = new File(filePath);
            // Copy the screenshot to the destination file
            Files.copy(screenshotFile.toPath(), destinationFile.toPath());
            logger.info("Screenshot saved ");
            //System.out.println("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage());
            
        }
    }
    
    /**
     * Method to get screen shot's directory path along with Static screenshot_ stamp for the file name
     * @return
     */
    public static String getScreenshotFilePath(){
    	// Define the screenshot file path
        String screenshotFilePath = System.getProperty("user.dir") + "\\images\\screenshot_";
        return screenshotFilePath;
     

    }


}
