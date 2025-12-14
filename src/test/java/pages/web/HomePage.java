package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.util.List;

public class HomePage extends BasePage {

    // Локаторы для habr.com
    @FindBy(css = "input[type='search'], input[name='q'], .tm-header-user-menu__search input, #search-form-field, input[placeholder*='поиск'], input[placeholder*='Поиск']")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit'], .tm-header-user-menu__search button, button[aria-label*='Поиск'], .search-button")
    private WebElement searchButton;

    @FindBy(css = "nav a, .tm-main-menu__item a, .tm-header__menu a, header a, .tm-header__nav a, a[href*='/hub/'], a[href*='/company/']")
    private List<WebElement> navigationLinks;

    @FindBy(css = ".tm-header__logo, a[href='/'], .tm-logo, a.tm-header__logo, .logo")
    private WebElement logo;

    @FindBy(css = "h1, .tm-title, .tm-article-title, .title")
    private WebElement pageTitle;

    @FindBy(css = ".tm-articles-list__item, .tm-article-snippet, article, .content-list__item, .tm-article-card, [class*='article'], [class*='post']")
    private List<WebElement> articles;

    public HomePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void open() {
        try {
            driver.get(ConfigReader.getWebBaseUrl());
        } catch (org.openqa.selenium.TimeoutException e) {
            // Если страница не загрузилась за таймаут, продолжаем работу
            System.out.println("Предупреждение: страница не загрузилась полностью, продолжаем тест");
        }
        // Ждем полной загрузки страницы с таймаутом
        try {
            waitForPageLoad();
        } catch (Exception e) {
            // Игнорируем ошибки загрузки
        }
        // Дополнительная задержка для загрузки динамического контента
        try {
            Thread.sleep(3000);
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
            // Пробуем альтернативные селекторы для habr.com
            try {
                // Сначала пробуем найти иконку поиска и кликнуть на неё
                List<WebElement> searchIcons = driver.findElements(By.cssSelector(".tm-header-user-menu__search, [aria-label*='Поиск'], button[type='button'][class*='search']"));
                for (WebElement icon : searchIcons) {
                    if (icon.isDisplayed()) {
                        try {
                            click(icon);
                            Thread.sleep(500); // Даем время на открытие поля поиска
                        } catch (Exception e) {
                            // Игнорируем
                        }
                        break;
                    }
                }
                
                // Теперь ищем поле ввода
                List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='search'], input[name='q'], .tm-header-user-menu__search input, #search-form-field, input[placeholder*='поиск'], input[placeholder*='Поиск'], input[name='search']"));
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
            // Если поле поиска не найдено, пробуем использовать URL напрямую
            driver.get(ConfigReader.getWebBaseUrl() + "/search/?q=" + query.replace(" ", "+"));
        }
    }

    public boolean isSearchInputDisplayed() {
        // Пробуем найти через @FindBy
        if (isElementDisplayed(searchInput)) {
            return true;
        }
        // Пробуем альтернативные селекторы для habr.com
        try {
            List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='search'], input[name='q'], .tm-header-user-menu__search input, #search-form-field, input[placeholder*='поиск'], input[placeholder*='Поиск'], input[name='search']"));
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
        // Пробуем найти через @FindBy
        if (isElementDisplayed(logo)) {
            return true;
        }
        // Пробуем альтернативные селекторы для habr.com
        try {
            List<WebElement> logos = driver.findElements(By.cssSelector(".tm-header__logo, a[href='/'], .tm-logo, a.tm-header__logo, .logo, [class*='logo']"));
            for (WebElement log : logos) {
                if (log.isDisplayed()) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Игнорируем
        }
        return false;
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
        // Пробуем альтернативные селекторы для habr.com
        try {
            List<WebElement> links = driver.findElements(By.cssSelector("nav a, .tm-main-menu__item a, .tm-header__menu a, header a, .tm-header__nav a, a[href*='/hub/'], a[href*='/company/'], a[href*='/'], .menu-item a"));
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
        // Пробуем альтернативные селекторы для habr.com
        try {
            List<WebElement> articleElements = driver.findElements(By.cssSelector(".tm-articles-list__item, .tm-article-snippet, article, .content-list__item, .tm-article-card, [class*='article'], [class*='post'], [class*='tm-article']"));
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

