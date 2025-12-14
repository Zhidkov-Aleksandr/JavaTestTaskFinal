package pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ArticlePage extends BasePage {

    // Локаторы для habr.com
    @FindBy(css = "h1, .tm-article-title, .tm-title, .article-title, .post-title, .title, .tm-page__title")
    private WebElement articleTitle;

    @FindBy(css = ".tm-article-body, .article-content, .post-content, .content, article, .tm-article-presenter__content")
    private WebElement articleContent;

    @FindBy(css = ".tm-article-datetime, .article-date, .post-date, .date, time, [datetime]")
    private WebElement articleDate;

    @FindBy(css = ".tm-user-info__username, .article-author, .author, .post-author, .tm-user-info__name")
    private WebElement articleAuthor;

    @FindBy(css = ".share-button, .social-share, button[aria-label*='Поделиться'], .tm-article-share, [class*='share']")
    private WebElement shareButton;

    public ArticlePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public String getArticleTitle() {
        try {
            if (isElementDisplayed(articleTitle)) {
                waitForElement(articleTitle);
                return getText(articleTitle);
            }
            // Пробуем альтернативные селекторы для habr.com
            List<WebElement> titles = driver.findElements(org.openqa.selenium.By.cssSelector("h1, .tm-article-title, .tm-title, .article-title, .tm-page__title"));
            for (WebElement title : titles) {
                if (title.isDisplayed() && !title.getText().isEmpty()) {
                    return title.getText();
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isArticleContentDisplayed() {
        if (isElementDisplayed(articleContent)) {
            return true;
        }
        // Пробуем альтернативные селекторы для habr.com
        try {
            List<WebElement> contents = driver.findElements(org.openqa.selenium.By.cssSelector(".tm-article-body, .article-content, .post-content, .content, article, .tm-article-presenter__content"));
            for (WebElement content : contents) {
                if (content.isDisplayed()) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Игнорируем
        }
        return false;
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

