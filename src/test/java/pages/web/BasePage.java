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
        // Инициализируем элементы с задержкой, чтобы избежать зависаний
        try {
            PageFactory.initElements(driver, this);
        } catch (Exception e) {
            // Игнорируем ошибки инициализации
        }
    }

    protected void waitForPageLoad() {
        try {
            WebDriverWait pageWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
            pageWait.until(webDriver -> {
                String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").toString();
                return state.equals("complete") || state.equals("interactive");
            });
        } catch (Exception e) {
            // Игнорируем ошибки - страница может загружаться асинхронно
        }
    }

    protected void waitForElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            // Игнорируем таймаут для более гибкой обработки
        }
    }

    protected void waitForElementClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            // Игнорируем таймаут для более гибкой обработки
        }
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
            // Используем короткий таймаут для проверки наличия элемента
            WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(2));
            shortWait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            // Если элемент не найден за 2 секунды, возвращаем false
            return false;
        }
    }
}

