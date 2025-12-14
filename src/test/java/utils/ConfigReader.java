package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties"
            );
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getWebBaseUrl() {
        return getProperty("web.base.url");
    }

    public static String getBrowser() {
        return getProperty("web.browser");
    }

    public static int getWebTimeout() {
        return Integer.parseInt(getProperty("web.timeout"));
    }

    public static String getAppiumServerUrl() {
        return getProperty("appium.server.url");
    }

    public static String getAndroidPlatformName() {
        return getProperty("android.platform.name");
    }

    public static String getAndroidPlatformVersion() {
        return getProperty("android.platform.version");
    }

    public static String getAndroidDeviceName() {
        return getProperty("android.device.name");
    }

    public static String getAndroidAppPackage() {
        return getProperty("android.app.package");
    }

    public static String getAndroidAppActivity() {
        return getProperty("android.app.activity");
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url");
    }
}

