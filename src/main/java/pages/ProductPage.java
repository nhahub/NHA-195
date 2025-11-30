package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductPage {

    private final AndroidDriver driver;


    private final By productImage =
            By.xpath("//android.widget.ImageView[contains(@content-desc, 'COSRX - Advanced Snail 96 Mucin Power Essence')]");

    private final By descriptionSection =
            By.xpath("//android.view.View[@content-desc='Description']");

    private final By addToCartButton =
            By.xpath("//android.widget.Button[@content-desc='Add to Cart']");

    private final By successMessage =
            By.xpath("//android.view.View[contains(@content-desc, 'had been added')]");

    private final By backButton =
            By.xpath("//android.widget.Button[@content-desc='Back']");

    public ProductPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void selectProductFromSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement product = wait.until(driver1 -> driver1.findElement(productImage));
            product.click();
            System.out.println("Selected product from search results");
            waitForSeconds(2);
        } catch (Exception e) {
            System.out.println("Failed to select product: " + e.getMessage());
            throw e;
        }
    }

    public boolean isProductPageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement description = wait.until(driver1 -> driver1.findElement(descriptionSection));
            boolean isDisplayed = description.isDisplayed();

            if (isDisplayed) {
                System.out.println("Product page opened successfully");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Product page not opened: " + e.getMessage());
            return false;
        }
    }

    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement addButton = wait.until(driver1 -> driver1.findElement(addToCartButton));
            addButton.click();
            System.out.println("Clicked Add to Cart button");
            waitForSeconds(1);
        } catch (Exception e) {
            System.out.println("Failed to click Add to Cart: " + e.getMessage());
            throw e;
        }
    }

    public boolean isAddToCartSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement message = wait.until(driver1 -> driver1.findElement(successMessage));
            boolean isDisplayed = message.isDisplayed();

            if (isDisplayed) {
                System.out.println("Add to Cart success");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Add to Cart success message not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement backBtn = wait.until(driver1 -> driver1.findElement(backButton));
            backBtn.click();
            System.out.println("Clicked Back button");
            waitForSeconds(1);
        } catch (Exception e) {
            System.out.println("Failed to click Back button: " + e.getMessage());
            throw e;
        }
    }
}