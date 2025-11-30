package tests;

import org.BaseTest;
import pages.HomePage;
import pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileNavigationTest extends BaseTest {

    @Test
    public void testNavigateToProfile() {

        HomePage home = new HomePage(BaseTest.getDriver());
        home.goToProfile();

        ProfilePage profile = new ProfilePage(BaseTest.getDriver());

        Assert.assertTrue(
                profile.isDisplayed(),
                "Profile Page did NOT open"
        );

        System.out.println("Profile Page opened successfully!");
    }
}
