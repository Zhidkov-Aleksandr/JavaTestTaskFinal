package pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumDriverManager;

import java.time.Duration;

public class BaseMobilePage {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BaseMobilePage() {
        this.driver = AppiumDriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void waitForElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        waitForElementClickable(element);
        element.click();
    }

    protected void sendKeys(WebElement element, String text) {
        waitForElement(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForElement(element);
        return element.getText();
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            waitForElement(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollToElement(WebElement element) {
        driver.executeScript("mobile: scroll", 
            java.util.Map.of("element", element, "direction", "down"));
    }
}

