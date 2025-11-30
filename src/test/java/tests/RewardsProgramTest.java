package tests;

import org.BaseTest;
import pages.HomePage;
import pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RewardsProgramTest extends BaseTest {

    @Test
    public void testRewardsProgramNavigation() {
        HomePage home = new HomePage(BaseTest.getDriver());
        home.goToProfile();

        ProfilePage profile = new ProfilePage(BaseTest.getDriver());

        Assert.assertTrue(profile.isDisplayed(),
                "Profile Page did NOT open!");

        profile.clickRewardsProgram();

        profile.waitForSeconds(5);

        Assert.assertTrue(profile.isRewardsPageDisplayed(),
                "Rewards Page did NOT open after clicking Rewards Program!");


        profile.clickBackButton();

        profile.waitForSeconds(3);

        Assert.assertTrue(profile.isDisplayed(),
                "Did NOT return to Profile Page after back!");

        System.out.println("All Rewards Program tests passed successfully!");
    }
}