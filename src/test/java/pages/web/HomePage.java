package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = "input[type='search'], input[placeholder*='Поиск'], input[name='q'], .search-input")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit'], .search-button, button.search-btn")
    private WebElement searchButton;

    @FindBy(css = "nav a, .menu a, .navigation a, header a")
    private List<WebElement> navigationLinks;

    @FindBy(css = ".logo, a[href='/'], .header-logo")
    private WebElement logo;

    @FindBy(css = "h1, .title, .article-title")
    private WebElement pageTitle;

    @FindBy(css = ".article, .news-item, .post")
    private List<WebElement> articles;

    public HomePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(ConfigReader.getWebBaseUrl());
        // Даем время на загрузку страницы
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void search(String query) {
        WebElement input = null;
        // Пробуем найти поле поиска
        if (isElementDisplayed(searchInput)) {
            input = searchInput;
        } else {
            // Пробуем альтернативные селекторы
            try {
                List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='search'], input[placeholder*='поиск'], input[placeholder*='Поиск'], input[name='q'], input[name='search']"));
                for (WebElement elem : inputs) {
                    if (elem.isDisplayed()) {
                        input = elem;
                        break;
                    }
                }
            } catch (Exception e) {
                // Игнорируем
            }
        }
        
        if (input != null) {
            waitForElement(input);
            sendKeys(input, query);
            if (isElementDisplayed(searchButton)) {
                click(searchButton);
            } else {
                input.submit();
            }
        } else {
            throw new RuntimeException("Поле поиска не найдено");
        }
    }

    public boolean isSearchInputDisplayed() {
        // Пробуем найти через @FindBy
        if (isElementDisplayed(searchInput)) {
            return true;
        }
        // Пробуем альтернативные селекторы
        try {
            List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='search'], input[placeholder*='поиск'], input[placeholder*='Поиск'], input[name='q'], input[name='search']"));
            for (WebElement input : inputs) {
                if (input.isDisplayed()) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Игнорируем
        }
        return false;
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(logo);
    }

    public void clickLogo() {
        if (isElementDisplayed(logo)) {
            click(logo);
        }
    }

    public void clickNavigationLink(int index) {
        if (index < navigationLinks.size() && index >= 0) {
            waitForElementClickable(navigationLinks.get(index));
            click(navigationLinks.get(index));
        }
    }

    public int getNavigationLinksCount() {
        int count = navigationLinks.size();
        if (count > 0) {
            return count;
        }
        // Пробуем альтернативные селекторы
        try {
            List<WebElement> links = driver.findElements(By.cssSelector("nav a, .menu a, .navigation a, header a, .header a, .nav a, a[href*='/'], .menu-item a"));
            // Фильтруем только видимые ссылки
            long visibleCount = links.stream()
                    .filter(WebElement::isDisplayed)
                    .count();
            return (int) visibleCount;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean hasArticles() {
        if (!articles.isEmpty()) {
            return true;
        }
        // Пробуем альтернативные селекторы
        try {
            List<WebElement> articleElements = driver.findElements(By.cssSelector(".article, .news-item, .post, article, .card, .item, [class*='article'], [class*='news'], [class*='post']"));
            return !articleElements.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public int getArticlesCount() {
        return articles.size();
    }

    public WebDriver getDriver() {
        return driver;
    }
}

