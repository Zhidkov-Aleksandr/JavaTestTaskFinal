package pages.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultsPage extends BaseMobilePage {

    @FindBy(id = "org.wikipedia:id/page_list_item_title")
    private List<WebElement> searchResults;

    @FindBy(id = "org.wikipedia:id/search_results_list")
    private WebElement resultsList;

    @FindBy(id = "org.wikipedia:id/search_empty_text")
    private WebElement emptyResultsText;

    public SearchResultsPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public int getSearchResultsCount() {
        try {
            Thread.sleep(2000); // Даем время на загрузку результатов
            return searchResults.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasSearchResults() {
        return getSearchResultsCount() > 0;
    }

    public void clickSearchResult(int index) {
        if (index < searchResults.size() && index >= 0) {
            waitForElementClickable(searchResults.get(index));
            click(searchResults.get(index));
        }
    }

    public String getSearchResultTitle(int index) {
        if (index < searchResults.size() && index >= 0) {
            return getText(searchResults.get(index));
        }
        return "";
    }

    public boolean isResultsListDisplayed() {
        return isElementDisplayed(resultsList);
    }

    public boolean isEmptyResultsMessageDisplayed() {
        return isElementDisplayed(emptyResultsText);
    }
}

