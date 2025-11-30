package tests;

import org.BaseTest;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void testAppOpenedSuccessfully() {
        HomePage home = new HomePage(BaseTest.getDriver());

        Assert.assertTrue(
                home.isDisplayed(),
                "App did NOT go to home page"
        );

        System.out.println("App opened successfully in home page");

    }
}
