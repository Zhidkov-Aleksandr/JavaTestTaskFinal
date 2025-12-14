package tests.web;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.WebDriverManager;

public class BaseWebTest {
    
    @BeforeClass
    public void setUp() {
        WebDriverManager.getDriver();
    }

    @AfterClass
    public void tearDown() {
        WebDriverManager.quitDriver();
    }
}

