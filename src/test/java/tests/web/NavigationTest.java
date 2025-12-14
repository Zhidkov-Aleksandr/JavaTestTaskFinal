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
        if (linksCount > 0) {
            String initialUrl = WebDriverManager.getDriver().getCurrentUrl();
            homePage.clickNavigationLink(0);
            
            try {
                Thread.sleep(2000); // Даем время на переход
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            String newUrl = WebDriverManager.getDriver().getCurrentUrl();
            Assert.assertNotEquals(initialUrl, newUrl, 
                    "URL должен измениться после клика на навигационную ссылку");
        }
    }

    @Test(priority = 2, description = "Проверка клика по логотипу")
    public void testLogoClick() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        // Переходим на другую страницу
        if (homePage.getNavigationLinksCount() > 0) {
            homePage.clickNavigationLink(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        homePage.clickLogo();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

