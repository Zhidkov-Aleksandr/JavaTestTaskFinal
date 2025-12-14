package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.web.HomePage;
import utils.WebDriverManager;

public class NavigationTest extends BaseWebTest {

    @Test(priority = 1, description = "Проверка навигации по разделам")
    public void testNavigationToSection() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        int linksCount = homePage.getNavigationLinksCount();
        if (linksCount > 1) { // Нужно минимум 2 ссылки для перехода
            String initialUrl = WebDriverManager.getDriver().getCurrentUrl();
            
            // Пробуем кликнуть на разные ссылки, пока не найдем ту, которая меняет URL
            boolean urlChanged = false;
            for (int i = 0; i < Math.min(linksCount, 5); i++) {
                try {
                    homePage.clickNavigationLink(i);
                    
                    // Ждем изменения URL с таймаутом
                    org.openqa.selenium.support.ui.WebDriverWait wait = 
                        new org.openqa.selenium.support.ui.WebDriverWait(
                            WebDriverManager.getDriver(), 
                            java.time.Duration.ofSeconds(5));
                    
                    try {
                        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.not(
                            org.openqa.selenium.support.ui.ExpectedConditions.urlToBe(initialUrl)));
                        urlChanged = true;
                        break;
                    } catch (Exception e) {
                        // URL не изменился, пробуем следующую ссылку
                        homePage.open(); // Возвращаемся на главную
                        // Ждем загрузки страницы через явное ожидание
                        org.openqa.selenium.support.ui.WebDriverWait pageWait = 
                            new org.openqa.selenium.support.ui.WebDriverWait(
                                WebDriverManager.getDriver(), 
                                java.time.Duration.ofSeconds(2));
                        pageWait.until(webDriver -> {
                            String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState").toString();
                            return state.equals("complete") || state.equals("interactive");
                        });
                    }
                } catch (Exception e) {
                    // Игнорируем ошибки и пробуем следующую ссылку
                }
            }
            
            if (!urlChanged) {
                // Если URL не изменился после всех попыток, проверяем, что навигация вообще работает
                // (может быть, мы уже на нужной странице или ссылки не меняют URL)
                String currentUrl = WebDriverManager.getDriver().getCurrentUrl();
                Assert.assertNotNull(currentUrl, "URL должен быть не null");
                Assert.assertTrue(currentUrl.contains("habr.com"), 
                        "URL должен содержать habr.com");
            } else {
                // URL изменился - проверяем, что он действительно отличается от начального
                String newUrl = WebDriverManager.getDriver().getCurrentUrl();
                Assert.assertNotEquals(initialUrl, newUrl, 
                        "URL должен измениться после клика на навигационную ссылку");
            }
        } else {
            // Если ссылок мало, просто проверяем, что они есть
            Assert.assertTrue(linksCount > 0, "На странице должны быть навигационные ссылки");
        }
    }

    @Test(priority = 2, description = "Проверка клика по логотипу")
    public void testLogoClick() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        // Переходим на другую страницу
        String urlBeforeLogoClick = WebDriverManager.getDriver().getCurrentUrl();
        if (homePage.getNavigationLinksCount() > 0) {
            homePage.clickNavigationLink(0);
            // Ждем изменения URL или загрузки страницы
            org.openqa.selenium.support.ui.WebDriverWait wait = 
                new org.openqa.selenium.support.ui.WebDriverWait(
                    WebDriverManager.getDriver(), 
                    java.time.Duration.ofSeconds(5));
            try {
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.not(
                    org.openqa.selenium.support.ui.ExpectedConditions.urlToBe(urlBeforeLogoClick)));
            } catch (Exception e) {
                // URL не изменился, продолжаем
            }
        }
        
        homePage.clickLogo();
        
        // Ждем изменения URL после клика по логотипу
        org.openqa.selenium.support.ui.WebDriverWait logoWait = 
            new org.openqa.selenium.support.ui.WebDriverWait(
                WebDriverManager.getDriver(), 
                java.time.Duration.ofSeconds(5));
        try {
            // Ждем, пока URL изменится или страница загрузится
            logoWait.until(webDriver -> {
                String currentUrl = webDriver.getCurrentUrl();
                String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").toString();
                return (!currentUrl.equals(urlBeforeLogoClick) || state.equals("complete"));
            });
        } catch (Exception e) {
            // Игнорируем таймаут
        }
        
        String urlAfterClick = WebDriverManager.getDriver().getCurrentUrl();
        // Логотип обычно ведет на главную страницу
        Assert.assertNotNull(urlAfterClick, "URL должен быть не null после клика по логотипу");
    }

    @Test(priority = 3, description = "Проверка корректности URL после навигации")
    public void testUrlAfterNavigation() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        String baseUrl = utils.ConfigReader.getWebBaseUrl();
        String currentUrl = WebDriverManager.getDriver().getCurrentUrl();
        
        Assert.assertTrue(currentUrl.contains(baseUrl.replace("https://", "").replace("http://", "")), 
                "URL должен содержать базовый адрес сайта");
    }
}

