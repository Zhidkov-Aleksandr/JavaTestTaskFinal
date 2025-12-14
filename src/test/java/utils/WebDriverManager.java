package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverManager {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.getBrowser().toLowerCase();
            switch (browser) {
                case "chrome":
                    io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getWebTimeout()));
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            getDriver();
        }
        return wait;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}

