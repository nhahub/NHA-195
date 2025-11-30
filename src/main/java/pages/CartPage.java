package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

public class CartPage {

    private final AndroidDriver driver;

    private final By cartTab =
            By.xpath("//android.widget.Button[contains(@content-desc, 'Cart') and contains(@content-desc, 'Tab 2 of 3')]");

    private final By cartItem =
            By.xpath("//*[contains(@content-desc, 'COSRX - Advanced Snail 96 Mucin Power Essence')]");

    private final By cartTitle =
            By.xpath("//*[contains(@content-desc, 'Cart') or contains(@text, 'Cart')]");

    private final By applyButton =
            By.xpath("//android.widget.Button[@content-desc='Apply']");

    private final By removeDiscountButton =
            By.xpath("//android.widget.Button[@content-desc='Remove discount']");

    private final By invalidDiscountMessage =
            By.xpath("//android.view.View[@content-desc='Invalid discount code']");

    private final By proceedToCheckoutButton =
            By.xpath("//android.widget.Button[@content-desc='Proceed to Checkout']");

    private final By emptyCartButton =
            By.xpath("//android.widget.Button[@content-desc='Empty Cart']");

    private final By clearConfirmationButton =
            By.xpath("//android.widget.Button[@content-desc='Clear']");

    private final By paymentPageTitle =
            By.xpath("//android.widget.TextView[@resource-id='payment']");
    public CartPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickCartTab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement cartBtn = wait.until(driver1 -> driver1.findElement(cartTab));
            cartBtn.click();
        } catch (Exception e) {
            System.out.println("Failed to click Cart tab: " + e.getMessage());
            throw e;
        }
    }

    public boolean isCartPageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement cartElement = wait.until(driver1 ->
                    driver1.findElement(By.xpath("//*[contains(@content-desc, 'Cart') or contains(@text, 'Cart')]")));
            boolean isDisplayed = cartElement.isDisplayed();

            if (isDisplayed) {
                System.out.println("Cart page opened successfully");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Cart page not opened: " + e.getMessage());
            return false;
        }
    }

    public boolean isProductInCart(String productName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement product = wait.until(driver1 ->
                    driver1.findElement(By.xpath("//*[contains(@content-desc, '" + productName + "')]")));
            boolean isDisplayed = product.isDisplayed();

            if (isDisplayed) {
                System.out.println("Product found in cart: " + productName);
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Product not found in cart: " + productName);
            return false;
        }
    }

    public void applyDiscountCode(String discountCode) throws IOException {
        try {
            tapOnCoordinates(247, 1026);
            waitForSeconds(1);
            enterText(discountCode);
            waitForSeconds(1);
            clickApplyButton();
            waitForSeconds(2);

            System.out.println("Discount code completed");

        } catch (Exception e) {
            System.out.println("Failed to apply discount code: " + e.getMessage());
            throw e;
        }
    }

    private void tapOnCoordinates(int x, int y) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 0);

            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(tap));

        } catch (Exception e) {
            System.out.println("Failed to tap on coordinates: " + e.getMessage());
            throw e;
        }
    }

    private void enterText(String text) throws IOException {
        try {
            driver.executeScript("mobile: type", java.util.Map.of(
                    "text", text
            ));
        } catch (Exception e) {

            try {
                String command = "adb shell input text \"" + text + "\"";
                Runtime.getRuntime().exec(command);
            } catch (Exception ex) {
                System.out.println("text entry also failed: " + ex.getMessage());
                throw ex;
            }
        }
    }

    private void clickApplyButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement applyBtn = wait.until(driver1 -> driver1.findElement(applyButton));
            applyBtn.click();
        } catch (Exception e) {
            System.out.println("Failed to click Apply button: " + e.getMessage());
            throw e;
        }
    }

    public boolean isDiscountApplied() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement removeBtn = wait.until(driver1 -> driver1.findElement(removeDiscountButton));
            boolean isDisplayed = removeBtn.isDisplayed();

            if (isDisplayed) {
                System.out.println("Discount applied successfully");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Discount not applied: " + e.getMessage());
            return false;
        }
    }

    public boolean isInvalidDiscountMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement message = wait.until(driver1 -> driver1.findElement(invalidDiscountMessage));
            boolean isDisplayed = message.isDisplayed();

            if (isDisplayed) {
                System.out.println("Invalid discount message displayed correctly");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Invalid discount message not displayed: " + e.getMessage());
            return false;
        }
    }

    public void removeDiscount() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement removeBtn = wait.until(driver1 -> driver1.findElement(removeDiscountButton));
            removeBtn.click();
            System.out.println("Removed discount");
            waitForSeconds(1);
        } catch (Exception e) {
            System.out.println("ℹNo discount to remove");
        }
    }

    public void clickProceedToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement checkoutBtn = wait.until(driver1 -> driver1.findElement(proceedToCheckoutButton));
            checkoutBtn.click();
        } catch (Exception e) {
            System.out.println("Failed to click Proceed to Checkout: " + e.getMessage());
            throw e;
        }
    }

    public boolean isPaymentPageDisplayed() {
        try {
            waitForSeconds(15);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement paymentTitle = wait.until(driver1 -> driver1.findElement(paymentPageTitle));
            boolean isDisplayed = paymentTitle.isDisplayed();

            if (isDisplayed) {
                System.out.println("Payment page opened successfully");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Payment page not opened: " + e.getMessage());
            return false;
        }
    }
    public void emptyCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement emptyCartBtn = wait.until(driver1 -> driver1.findElement(emptyCartButton));
            emptyCartBtn.click();
            System.out.println("Clicked on Empty Cart button");

            waitForSeconds(2);

            WebElement clearBtn = wait.until(driver1 -> driver1.findElement(clearConfirmationButton));
            clearBtn.click();
            System.out.println("Clicked on Clear confirmation button");

            waitForSeconds(2);
            System.out.println("Cart emptied successfully");

        } catch (Exception e) {
            System.out.println("Failed to empty cart: " + e.getMessage());
            throw e;
        }
    }

}