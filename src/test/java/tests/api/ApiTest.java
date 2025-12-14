package tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class ApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.getApiBaseUrl();
    }

    @Test(priority = 1, description = "Проверка доступности GitHub API")
    public void testGitHubApiAvailability() {
        Response response = given()
                .when()
                .get("/")
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, 
                "GitHub API должен быть доступен (статус код 200)");
    }

    @Test(priority = 2, description = "Проверка получения информации о пользователе")
    public void testGetUserInfo() {
        String username = "octocat"; // Тестовый пользователь GitHub
        
        Response response = given()
                .when()
                .get("/users/" + username)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, 
                "Запрос информации о пользователе должен вернуть статус 200");

        String login = response.jsonPath().getString("login");
        Assert.assertEquals(login, username, 
                "Логин пользователя должен совпадать с запрошенным");
    }

    @Test(priority = 3, description = "Проверка получения репозиториев пользователя")
    public void testGetUserRepositories() {
        String username = "octocat";
        
        Response response = given()
                .when()
                .get("/users/" + username + "/repos")
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, 
                "Запрос репозиториев должен вернуть статус 200");

        // Проверяем, что ответ содержит массив
        Object repos = response.jsonPath().get("$");
        Assert.assertNotNull(repos, "Ответ должен содержать список репозиториев");
    }

    @Test(priority = 4, description = "Проверка обработки несуществующего пользователя")
    public void testNonExistentUser() {
        String username = "nonexistentuser12345xyz";
        
        Response response = given()
                .when()
                .get("/users/" + username)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404, 
                "Запрос несуществующего пользователя должен вернуть статус 404");
    }

    @Test(priority = 5, description = "Проверка структуры ответа API")
    public void testApiResponseStructure() {
        Response response = given()
                .when()
                .get("/users/octocat")
                .then()
                .extract()
                .response();

        // Проверяем наличие обязательных полей
        String login = response.jsonPath().getString("login");
        String id = response.jsonPath().getString("id");
        String url = response.jsonPath().getString("url");

        Assert.assertNotNull(login, "Ответ должен содержать поле 'login'");
        Assert.assertNotNull(id, "Ответ должен содержать поле 'id'");
        Assert.assertNotNull(url, "Ответ должен содержать поле 'url'");
    }
}

