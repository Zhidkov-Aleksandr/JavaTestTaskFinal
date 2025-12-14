package tests.mobile;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.AppiumDriverManager;

public class BaseMobileTest {
    
    @BeforeClass
    public void setUp() {
        AppiumDriverManager.getDriver();
    }

    @AfterClass
    public void tearDown() {
        AppiumDriverManager.quitDriver();
    }
}

