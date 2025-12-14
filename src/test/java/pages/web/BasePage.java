package pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverManager;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = WebDriverManager.getDriver();
        this.wait = WebDriverManager.getWait();
        PageFactory.initElements(driver, this);
    }

    protected void waitForElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            // Игнорируем таймаут для более гибкой обработки
        }
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
}

