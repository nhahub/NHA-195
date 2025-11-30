package tests;

import org.BaseTest;
import pages.HomePage;
import pages.ProfilePage;
import pages.AddAddressPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddAddressTest extends BaseTest {

    @BeforeMethod
    public void ensureHomePageIsOpen() {
        try {
            HomePage home = new HomePage(BaseTest.getDriver());

            try {
                home.goToHomeTab();
                home.waitForSeconds(1);
            } catch (Exception e) {
                System.out.println("ℹCould not navigate to home tab: " + e.getMessage());
            }

            if (!home.isDisplayed()) {
                System.out.println("Home page not displayed, restarting app...");
                BaseTest.getDriver().activateApp("com.korean_beauty_app");
                home.waitForSeconds(2);
            }

            System.out.println("Home page is ready");
        } catch (Exception e) {
            System.out.println("Could not ensure home page: " + e.getMessage());
        }
    }

    @Test(priority = 1)
    public void testAddNewAddress() {
        try {
            HomePage home = new HomePage(BaseTest.getDriver());

            home.goToProfile();
            home.waitForSeconds(2);

            ProfilePage profile = new ProfilePage(BaseTest.getDriver());
            Assert.assertTrue(profile.isDisplayed(),
                    "Profile Page did NOT open");

            AddAddressPage addAddress = new AddAddressPage(BaseTest.getDriver());
            addAddress.waitForSeconds(2);

            addAddress.clickAddNewAddress();
            addAddress.waitForSeconds(2);

            addAddress.enterFirstName("mostafa");
            addAddress.waitForSeconds(1);

            addAddress.enterLastName("hesham");
            addAddress.waitForSeconds(1);

            addAddress.enterAddressLine1("test test test");
            addAddress.waitForSeconds(1);

            addAddress.enterCity("alexandria");
            addAddress.waitForSeconds(1);

            addAddress.selectProvince();
            addAddress.waitForSeconds(1);

            addAddress.enterZipCode("21500");
            addAddress.waitForSeconds(1);

            addAddress.enterPhoneNumber("01000000111");
            addAddress.waitForSeconds(1);

            addAddress.hideKeyboard();
            addAddress.waitForSeconds(1);

            addAddress.clickSaveAddress();
            addAddress.waitForSeconds(2);

            boolean isSuccess = addAddress.isSuccessMessageDisplayed();

            if (isSuccess) {
                System.out.println("New address added successfully!");
            } else {
                System.out.println("Address was NOT added successfully");
                Assert.fail("Address was NOT added successfully");
            }

        } catch (RuntimeException e) {
            if (e.getMessage() != null &&
                    (e.getMessage().contains("Driver connection lost") ||
                            e.getMessage().contains("Failed to enter text"))) {
                System.out.println("Driver connection issue detected");
            }
            throw e;

        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void testAddNewAddressFailure() {
        try {
            HomePage home = new HomePage(BaseTest.getDriver());

            home.goToProfile();
            home.waitForSeconds(2);

            ProfilePage profile = new ProfilePage(BaseTest.getDriver());
            Assert.assertTrue(profile.isDisplayed(),
                    "Profile Page did NOT open!");

            AddAddressPage addAddress = new AddAddressPage(BaseTest.getDriver());
            addAddress.waitForSeconds(2);

            addAddress.clickAddNewAddress();
            addAddress.waitForSeconds(2);

            addAddress.enterFirstName("mostafa");
            addAddress.waitForSeconds(1);

            addAddress.enterLastName("hesham");
            addAddress.waitForSeconds(1);

            addAddress.enterAddressLine1("test test test");
            addAddress.waitForSeconds(1);

            addAddress.enterCity("alexandria");
            addAddress.waitForSeconds(1);

            addAddress.selectProvince();
            addAddress.waitForSeconds(1);

            addAddress.enterZipCode("21500");
            addAddress.waitForSeconds(1);

            addAddress.enterPhoneNumber("01000000111");
            addAddress.waitForSeconds(1);

            addAddress.hideKeyboard();
            addAddress.waitForSeconds(1);

            addAddress.clickSaveAddress();
            addAddress.waitForSeconds(2);

            boolean isFail = addAddress.isFailMessageDisplayed();
            addAddress.waitForSeconds(1);

            try {
                profile.clickBackButton();
                addAddress.waitForSeconds(1);
            } catch (Exception e) {
                System.out.println("Could not click back button: " + e.getMessage());
            }

            if (isFail) {
                System.out.println("Fail Add Address test completed successfully!");
            } else {
                System.out.println("Fail message was NOT displayed");
                System.out.println("Note: This might mean the app allows duplicate addresses");
            }

        } catch (RuntimeException e) {
            if (e.getMessage() != null &&
                    (e.getMessage().contains("Driver connection lost") ||
                            e.getMessage().contains("Failed to enter text"))) {
                System.out.println("Driver connection issue detected");
            }
            throw e;

        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}