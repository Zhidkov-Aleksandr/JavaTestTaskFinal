package pages.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BaseMobilePage {

    @FindBy(id = "org.wikipedia:id/search_container")
    private WebElement searchContainer;

    @FindBy(id = "org.wikipedia:id/search_src_text")
    private WebElement searchInput;

    @FindBy(id = "org.wikipedia:id/search_close_btn")
    private WebElement searchCloseButton;

    @FindBy(id = "org.wikipedia:id/fragment_main_nav_tab_layout")
    private WebElement navigationTabs;

    @FindBy(id = "org.wikipedia:id/view_announcement_text")
    private WebElement announcementText;

    @FindBy(id = "org.wikipedia:id/main_drawer_login_button")
    private WebElement loginButton;

    public MainPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void clickSearchContainer() {
        waitForElementClickable(searchContainer);
        click(searchContainer);
    }

    public void enterSearchQuery(String query) {
        waitForElement(searchInput);
        sendKeys(searchInput, query);
    }

    public boolean isSearchInputDisplayed() {
        return isElementDisplayed(searchInput);
    }

    public void closeSearch() {
        if (isElementDisplayed(searchCloseButton)) {
            click(searchCloseButton);
        }
    }

    public boolean isMainPageDisplayed() {
        try {
            return isElementDisplayed(searchContainer) || isElementDisplayed(navigationTabs);
        } catch (Exception e) {
            return false;
        }
    }
}

