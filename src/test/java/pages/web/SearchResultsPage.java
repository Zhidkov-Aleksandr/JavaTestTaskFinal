package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultsPage extends BasePage {

    // Локаторы для habr.com
    @FindBy(css = ".tm-articles-list__item, .tm-article-snippet, .search-result, .result-item, .tm-article-card, article[class*='article']")
    private List<WebElement> searchResults;

    @FindBy(css = ".search-query, .search-term, input[type='search'], input[name='q'], .tm-header-user-menu__search input")
    private WebElement searchQueryInput;

    @FindBy(css = ".no-results, .empty-results, .not-found, .tm-empty-state, [class*='empty']")
    private WebElement noResultsMessage;

    @FindBy(css = "h1, .tm-title, .search-title, .results-title, .tm-page__title")
    private WebElement resultsTitle;

    public SearchResultsPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public int getSearchResultsCount() {
        try {
            if (!searchResults.isEmpty()) {
                waitForElement(searchResults.get(0));
                return searchResults.size();
            }
            // Пробуем альтернативные селекторы для habr.com
            List<WebElement> results = driver.findElements(org.openqa.selenium.By.cssSelector(".tm-articles-list__item, .tm-article-snippet, .search-result, .result-item, .tm-article-card, article[class*='article']"));
            return results.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasSearchResults() {
        return getSearchResultsCount() > 0;
    }

    public String getSearchQuery() {
        try {
            waitForElement(searchQueryInput);
            return searchQueryInput.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        return isElementDisplayed(noResultsMessage);
    }

    public String getResultsTitle() {
        try {
            return getText(resultsTitle);
        } catch (Exception e) {
            return "";
        }
    }

    public void clickSearchResult(int index) {
        if (index < searchResults.size() && index >= 0) {
            click(searchResults.get(index));
        }
    }
}

