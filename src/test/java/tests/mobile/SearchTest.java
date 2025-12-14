package tests.mobile;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.mobile.MainPage;
import pages.mobile.SearchResultsPage;

public class SearchTest extends BaseMobileTest {

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][]{
                {"Java"},
                {"Android"},
                {"Appium"}
        };
    }

    @Test(priority = 1, description = "Проверка поиска статьи по ключевому слову")
    public void testSearchArticle() {
        MainPage mainPage = new MainPage();
        
        // Проверяем, что главная страница загрузилась
        Assert.assertTrue(mainPage.isMainPageDisplayed(), 
                "Главная страница должна отображаться");
        
        // Открываем поиск
        mainPage.clickSearchContainer();
        
        // Вводим запрос
        String searchQuery = "Java";
        mainPage.enterSearchQuery(searchQuery);
        
        // Ждем результатов
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        boolean hasResults = searchResultsPage.hasSearchResults();
        
        Assert.assertTrue(hasResults, 
                "Поиск должен вернуть результаты для запроса: " + searchQuery);
    }

    @Test(priority = 2, dataProvider = "searchQueries", description = "Параметризованный тест поиска")
    public void testSearchWithDifferentQueries(String query) {
        MainPage mainPage = new MainPage();
        mainPage.clickSearchContainer();
        mainPage.enterSearchQuery(query);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        boolean hasResults = searchResultsPage.hasSearchResults();
        boolean emptyResults = searchResultsPage.isEmptyResultsMessageDisplayed();
        
        Assert.assertTrue(hasResults || emptyResults, 
                "Должны быть либо результаты поиска, либо сообщение об их отсутствии для запроса: " + query);
    }

    @Test(priority = 3, description = "Проверка открытия статьи из результатов поиска")
    public void testOpenArticleFromSearch() {
        MainPage mainPage = new MainPage();
        mainPage.clickSearchContainer();
        mainPage.enterSearchQuery("Android");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        
        if (searchResultsPage.hasSearchResults()) {
            searchResultsPage.clickSearchResult(0);
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            pages.mobile.ArticlePage articlePage = new pages.mobile.ArticlePage();
            String articleTitle = articlePage.getArticleTitle();
            
            Assert.assertNotNull(articleTitle, "Заголовок статьи не должен быть null");
            Assert.assertFalse(articleTitle.isEmpty(), "Заголовок статьи не должен быть пустым");
        } else {
            Assert.fail("Нет результатов поиска для открытия статьи");
        }
    }
}

