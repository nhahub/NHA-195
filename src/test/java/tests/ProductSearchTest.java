package tests;

import org.BaseTest;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import pages.HomePage;
import pages.ProductPage;
import pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

public class ProductSearchTest extends BaseTest {

    @Test
    public void testSearchAddToCartAndVerifyInCart() {
        try {

            HomePage home = new HomePage(BaseTest.getDriver());
            ProductPage productPage = new ProductPage(BaseTest.getDriver());
            CartPage cartPage = new CartPage(BaseTest.getDriver());

            home.goToHomeTab();
            Assert.assertTrue(home.isDisplayed(), "Home Page not open!");


            home.searchUsingCoordinates("cosrx");
            home.waitForSeconds(1);

            productPage.selectProductFromSearch();
            productPage.waitForSeconds(1);


            Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page not open");


            productPage.clickAddToCart();

            Assert.assertTrue(productPage.isAddToCartSuccessMessageDisplayed(),
                    "Product not added to cart");

            productPage.clickBackButton();
            productPage.waitForSeconds(1);

            cartPage.clickCartTab();
            cartPage.waitForSeconds(1);

            Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not open");

            Assert.assertTrue(cartPage.isProductInCart("COSRX - Advanced Snail 96 Mucin Power Essence"),
                    "Product not found in cart");

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }


    @Test
    public void testSearchAddToCartAndVerifyInCart2nd() {
        try {

            HomePage home = new HomePage(BaseTest.getDriver());
            ProductPage productPage = new ProductPage(BaseTest.getDriver());
            CartPage cartPage = new CartPage(BaseTest.getDriver());

            home.goToHomeTab();
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);

            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 945, 362));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            BaseTest.getDriver().perform(Arrays.asList(tap));



            Assert.assertTrue(home.isDisplayed(), "Home Page not open!");


            home.searchUsingCoordinates("cosrx");
            home.waitForSeconds(1);

            productPage.selectProductFromSearch();
            productPage.waitForSeconds(1);


            Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page not open");


            productPage.clickAddToCart();

            Assert.assertTrue(productPage.isAddToCartSuccessMessageDisplayed(),
                    "Product not added to cart");

            productPage.clickBackButton();
            productPage.waitForSeconds(1);

            cartPage.clickCartTab();
            cartPage.waitForSeconds(1);

            Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not open");

            Assert.assertTrue(cartPage.isProductInCart("COSRX - Advanced Snail 96 Mucin Power Essence"),
                    "Product not found in cart");

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testApplyValidDiscountCode() throws IOException {
        try {

            CartPage cartPage = new CartPage(BaseTest.getDriver());

            cartPage.clickCartTab();
            cartPage.waitForSeconds(1);

            Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not open");

            cartPage.applyDiscountCode("KBSUMMER2025");

            Assert.assertTrue(cartPage.isDiscountApplied(), "Valid discount not applied");

            System.out.println("KBSUMMER2025 discount code worked correctly!");

        } catch (Exception e) {
            System.out.println("Valid discount test failed: " + e.getMessage());
            Assert.fail("Valid discount test failed: " + e.getMessage());
        }
    }

    @Test
    public void testApplyInvalidDiscountCode() throws IOException {
        try {
            CartPage cartPage = new CartPage(BaseTest.getDriver());

            cartPage.clickCartTab();
            cartPage.waitForSeconds(1);

            Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not open");

            cartPage.applyDiscountCode("KBSUMMER2026");


            Assert.assertTrue(cartPage.isInvalidDiscountMessageDisplayed(),
                    "Invalid discount message not displayed");
            cartPage.emptyCart();

            System.out.println("Invalid Discount Code Test COMPLETED SUCCESSFULLY!");

        } catch (Exception e) {
            System.out.println("Invalid discount test failed: " + e.getMessage());
            Assert.fail("Invalid discount test failed: " + e.getMessage());
        }
    }

    @Test
    public void testCompleteCheckoutFlow() throws IOException {
        try {

            CartPage cartPage = new CartPage(BaseTest.getDriver());

            cartPage.clickCartTab();
            Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart not opened");

            cartPage.applyDiscountCode("KBSUMMER2025");
            Assert.assertTrue(cartPage.isDiscountApplied(), "Valid discount failed");
            cartPage.waitForSeconds(4);

            cartPage.clickProceedToCheckout();

            Assert.assertTrue(cartPage.isPaymentPageDisplayed(), "Payment page not opened");

            System.out.println("Entire shopping journey completed successfully!");

        } catch (Exception e) {
            System.out.println("Complete checkout test failed: " + e.getMessage());
            Assert.fail("Complete checkout test failed: " + e.getMessage());
        }
    }


}