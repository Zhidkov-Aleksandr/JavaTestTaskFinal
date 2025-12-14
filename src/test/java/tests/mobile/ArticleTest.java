package tests.mobile;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.mobile.ArticlePage;
import pages.mobile.MainPage;
import pages.mobile.SearchResultsPage;

public class ArticleTest extends BaseMobileTest {

    @Test(priority = 1, description = "Проверка заголовка открытой статьи")
    public void testArticleTitle() {
        MainPage mainPage = new MainPage();
        mainPage.clickSearchContainer();
        mainPage.enterSearchQuery("Wikipedia");
        
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
            
            ArticlePage articlePage = new ArticlePage();
            String articleTitle = articlePage.getArticleTitle();
            
            Assert.assertTrue(articlePage.isArticleTitleDisplayed(), 
                    "Заголовок статьи должен отображаться");
            Assert.assertNotNull(articleTitle, "Заголовок статьи не должен быть null");
            Assert.assertFalse(articleTitle.isEmpty(), "Заголовок статьи не должен быть пустым");
        } else {
            Assert.fail("Нет результатов поиска для проверки заголовка статьи");
        }
    }

    @Test(priority = 2, description = "Проверка содержимого статьи")
    public void testArticleContent() {
        MainPage mainPage = new MainPage();
        mainPage.clickSearchContainer();
        mainPage.enterSearchQuery("Java programming");
        
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
            
            ArticlePage articlePage = new ArticlePage();
            boolean hasContent = articlePage.isArticleContentDisplayed();
            
            Assert.assertTrue(hasContent, "Содержимое статьи должно отображаться");
        } else {
            Assert.fail("Нет результатов поиска для проверки содержимого статьи");
        }
    }

    @Test(priority = 3, description = "Проверка прокрутки статьи")
    public void testScrollArticle() {
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
            
            ArticlePage articlePage = new ArticlePage();
            
            // Проверяем, что статья открылась
            Assert.assertTrue(articlePage.isArticleTitleDisplayed(), 
                    "Заголовок статьи должен отображаться");
            
            // Прокручиваем статью вниз
            articlePage.scrollDown();
            articlePage.scrollDown();
            
            // Проверяем, что содержимое все еще отображается после прокрутки
            boolean hasContent = articlePage.isArticleContentDisplayed();
            Assert.assertTrue(hasContent, 
                    "Содержимое статьи должно отображаться после прокрутки");
        } else {
            Assert.fail("Нет результатов поиска для проверки прокрутки статьи");
        }
    }
}

