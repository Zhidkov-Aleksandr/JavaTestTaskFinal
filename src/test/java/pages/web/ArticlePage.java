package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticlePage extends BasePage {

    @FindBy(css = "h1, .article-title, .post-title, .title")
    private WebElement articleTitle;

    @FindBy(css = ".article-content, .post-content, .content, article")
    private WebElement articleContent;

    @FindBy(css = ".article-date, .post-date, .date, time")
    private WebElement articleDate;

    @FindBy(css = ".article-author, .author, .post-author")
    private WebElement articleAuthor;

    @FindBy(css = ".share-button, .social-share, button[aria-label*='Поделиться']")
    private WebElement shareButton;

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

    public boolean isArticleContentDisplayed() {
        return isElementDisplayed(articleContent);
    }

    public String getArticleDate() {
        try {
            return getText(articleDate);
        } catch (Exception e) {
            return "";
        }
    }

    public String getArticleAuthor() {
        try {
            return getText(articleAuthor);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isShareButtonDisplayed() {
        return isElementDisplayed(shareButton);
    }
}

