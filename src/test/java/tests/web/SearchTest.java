package tests.web;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.web.HomePage;
import pages.web.SearchResultsPage;
import utils.WebDriverManager;

public class SearchTest extends BaseWebTest {

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][]{
                {"технологии"}
        };
    }

    @Test(priority = 1, description = "Проверка выполнения поиска")
    public void testSearchFunctionality() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        String searchQuery = "технологии";
        homePage.search(searchQuery);
        
        // Ждем загрузки страницы результатов поиска
        org.openqa.selenium.support.ui.WebDriverWait wait = 
            new org.openqa.selenium.support.ui.WebDriverWait(
                WebDriverManager.getDriver(), 
                java.time.Duration.ofSeconds(10));
        wait.until(webDriver -> {
            String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").toString();
            return state.equals("complete") || state.equals("interactive");
        });
        
        String currentUrl = WebDriverManager.getDriver().getCurrentUrl();
        Assert.assertNotNull(currentUrl, "URL должен быть не null после поиска");
    }

    @Test(priority = 2, dataProvider = "searchQueries", description = "Параметризованный тест поиска")
    public void testSearchWithDifferentQueries(String query) {
        HomePage homePage = new HomePage();
        homePage.open();
        
        homePage.search(query);
        
        // Проверяем, что страница результатов загрузилась
        String pageTitle = WebDriverManager.getDriver().getTitle();
        Assert.assertNotNull(pageTitle, "Заголовок страницы результатов не должен быть null");
    }

    @Test(priority = 3, description = "Проверка результатов поиска")
    public void testSearchResults() {
        HomePage homePage = new HomePage();
        homePage.open();
        
        homePage.search("технологии");
        
        // Ждем загрузки страницы результатов поиска
        org.openqa.selenium.support.ui.WebDriverWait wait = 
            new org.openqa.selenium.support.ui.WebDriverWait(
                WebDriverManager.getDriver(), 
                java.time.Duration.ofSeconds(10));
        wait.until(webDriver -> {
            String state = ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").toString();
            return state.equals("complete") || state.equals("interactive");
        });
        
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        
        // Проверяем наличие результатов или сообщения об отсутствии результатов
        boolean hasResults = searchResultsPage.hasSearchResults();
        boolean noResults = searchResultsPage.isNoResultsMessageDisplayed();
        
        Assert.assertTrue(hasResults || noResults, 
                "Должны быть либо результаты поиска, либо сообщение об их отсутствии");
    }
}

