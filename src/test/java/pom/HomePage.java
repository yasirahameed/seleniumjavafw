package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * HomePage class POM to handle elements and actions related to Home Page web page
 */
public class HomePage {

    private WebDriver driver;

    /**
     * Constructor to initialize the elements
     * @param driver
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  //initializing WebElement fields in Login POM Object class
    }

    /**
     * Method to get home page title
     * @return
     */
    public String getHomePageTitle() {
        return driver.getTitle();
    }
}

