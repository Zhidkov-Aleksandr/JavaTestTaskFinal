package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class AppiumDriverManager {
    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            try {
                UiAutomator2Options options = new UiAutomator2Options();
                options.setPlatformName(ConfigReader.getAndroidPlatformName());
                options.setPlatformVersion(ConfigReader.getAndroidPlatformVersion());
                options.setDeviceName(ConfigReader.getAndroidDeviceName());
                options.setAppPackage(ConfigReader.getAndroidAppPackage());
                options.setAppActivity(ConfigReader.getAndroidAppActivity());
                options.setAutomationName("UiAutomator2");
                options.setNoReset(false);
                options.setFullReset(false);

                String appPath = ConfigReader.getProperty("android.app.path");
                if (appPath != null && !appPath.isEmpty()) {
                    options.setApp(appPath);
                }

                driver = new AndroidDriver(URI.create(ConfigReader.getAppiumServerUrl()).toURL(), options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Failed to initialize Appium driver", e);
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

