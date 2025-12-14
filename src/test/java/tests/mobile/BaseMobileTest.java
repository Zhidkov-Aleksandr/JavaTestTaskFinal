package tests.mobile;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.SkipException;
import utils.AppiumDriverManager;

import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Базовый класс для мобильных тестов.
 * Этот класс не содержит тестовых методов - он предоставляет общую настройку (setUp/tearDown)
 * для всех мобильных тестов, которые наследуются от него.
 * Реальные тесты находятся в классах SearchTest и ArticleTest.
 */
@SuppressWarnings("unused")
public class BaseMobileTest {
    
    /**
     * Пустой тест для удовлетворения требований линтера.
     * Этот тест отключен, так как BaseMobileTest - это базовый класс для настройки,
     * а не класс с реальными тестами.
     */
    @org.testng.annotations.Test(enabled = false)
    public void baseClassTest() {
        // Этот метод существует только для подавления предупреждения линтера
        // Реальные тесты находятся в классах SearchTest и ArticleTest
    }
    
    @BeforeClass
    public void setUp() {
        // Проверяем доступность Appium Server перед запуском тестов
        if (!isAppiumServerRunning()) {
            throw new SkipException("Appium Server не запущен. Пропускаем мобильные тесты. " +
                    "Для запуска мобильных тестов запустите Appium Server: appium");
        }
        try {
            AppiumDriverManager.getDriver();
        } catch (Exception e) {
            throw new SkipException("Не удалось подключиться к Appium Server или эмулятору. " +
                    "Убедитесь, что Appium Server запущен и эмулятор подключен. Ошибка: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            AppiumDriverManager.quitDriver();
        } catch (Exception e) {
            // Игнорируем ошибки при закрытии
        }
    }

    private boolean isAppiumServerRunning() {
        try {
            String appiumUrl = utils.ConfigReader.getAppiumServerUrl();
            java.net.URL url = URI.create(appiumUrl + "/status").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode == 200;
        } catch (Exception e) {
            return false;
        }
    }
}

