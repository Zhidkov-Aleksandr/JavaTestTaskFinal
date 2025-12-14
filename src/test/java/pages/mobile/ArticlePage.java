package pages.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticlePage extends BaseMobilePage {

    @FindBy(id = "org.wikipedia:id/view_page_title_text")
    private WebElement articleTitle;

    @FindBy(id = "org.wikipedia:id/view_page_subtitle_text")
    private WebElement articleSubtitle;

    @FindBy(id = "org.wikipedia:id/page_contents_container")
    private WebElement articleContent;

    @FindBy(id = "org.wikipedia:id/view_page_header_image")
    private WebElement articleImage;

    @FindBy(id = "org.wikipedia:id/page_toolbar_button_bookmark")
    private WebElement bookmarkButton;

    @FindBy(id = "org.wikipedia:id/view_page_menu_overflow")
    private WebElement menuButton;

    public ArticlePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public String getArticleTitle() {
        try {
            waitForElement(articleTitle);
            return getText(articleTitle);
        } catch (Exception e) {
            return "";
        }
    }

    public String getArticleSubtitle() {
        try {
            return getText(articleSubtitle);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isArticleContentDisplayed() {
        return isElementDisplayed(articleContent);
    }

    public boolean isArticleTitleDisplayed() {
        return isElementDisplayed(articleTitle);
    }

    public void clickBookmarkButton() {
        if (isElementDisplayed(bookmarkButton)) {
            click(bookmarkButton);
        }
    }

    public void scrollDown() {
        try {
            driver.executeScript("mobile: scroll", 
                java.util.Map.of("direction", "down"));
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollToElement(String elementId) {
        try {
            WebElement element = driver.findElement(
                org.openqa.selenium.By.id(elementId));
            scrollToElement(element);
        } catch (Exception e) {
            // Элемент не найден
        }
    }
}

