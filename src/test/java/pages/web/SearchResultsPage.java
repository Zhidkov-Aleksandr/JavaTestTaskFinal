package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(css = ".search-results, .results, .search-result, .result-item")
    private List<WebElement> searchResults;

    @FindBy(css = ".search-query, .search-term, input[type='search']")
    private WebElement searchQueryInput;

    @FindBy(css = ".no-results, .empty-results, .not-found")
    private WebElement noResultsMessage;

    @FindBy(css = "h1, .search-title, .results-title")
    private WebElement resultsTitle;

    public SearchResultsPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public int getSearchResultsCount() {
        try {
            waitForElement(searchResults.get(0));
            return searchResults.size();
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

