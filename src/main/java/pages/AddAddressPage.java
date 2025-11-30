package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class AddAddressPage {

    private final AndroidDriver driver;

    private final By addNewAddressButton =
            By.xpath("//android.widget.Button[contains(@content-desc, 'Add New Address')]");

    private final By firstNameField =
            By.xpath("//android.widget.EditText[@hint='First Name']");

    private final By lastNameField =
            By.xpath("//android.widget.EditText[@hint='Last Name']");

    private final By addressLine1Field =
            By.xpath("//android.widget.EditText[@hint='Address Line 1']");

    private final By cityField =
            By.xpath("//android.widget.EditText[@hint='City']");

    private final By provinceDropdown =
            By.xpath("//android.widget.Button[@content-desc='Province']");

    private final By alexandriaOption =
            By.xpath("//android.widget.Button[@content-desc='Alexandria']");

    private final By zipCodeField =
            By.xpath("//android.widget.EditText[@hint='ZIP/Postal Code']");

    private final By phoneNumberField =
            By.xpath("//android.widget.EditText[@hint='Phone Number']");

    private final By saveAddressButton =
            By.xpath("//android.widget.Button[@content-desc='Save Address']");

    private final By successMessage =
            By.xpath("//android.view.View[contains(@content-desc, 'Address added successfully')]");

    private final By failMessage =
            By.xpath("//android.view.View[@content-desc='Failed to add address. Please check the data and try again.']");

    public AddAddressPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickAddNewAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement addBtn = wait.until(driver1 -> driver1.findElement(addNewAddressButton));
            addBtn.click();
            System.out.println("Clicked Add New Address button");
        } catch (Exception e) {
            System.out.println("Button not found, scrolling down...");
            scrollToFindElement(addNewAddressButton, 3);
            WebElement addBtn = wait.until(driver1 -> driver1.findElement(addNewAddressButton));
            addBtn.click();
            System.out.println("Clicked Add New Address button after scrolling");
        }
    }

    private void scrollToFindElement(By locator, int maxScrollAttempts) {
        for (int i = 0; i < maxScrollAttempts; i++) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    System.out.println("Element found after scroll");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Scroll attempt " + (i + 1) + "/" + maxScrollAttempts);
                scrollDown();
                waitForSeconds(2);
            }
        }
        throw new RuntimeException("Element not found after " + maxScrollAttempts + " scroll attempts: " + locator);
    }

    private void scrollDown() {
        try {
            int screenWidth = driver.manage().window().getSize().getWidth();
            int screenHeight = driver.manage().window().getSize().getHeight();

            int startX = screenWidth / 2;
            int startY = (int) (screenHeight * 0.6);
            int endY = (int) (screenHeight * 0.3);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence scroll = new Sequence(finger, 0);

            scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(scroll));

        } catch (Exception e) {
            System.out.println("Scroll failed: " + e.getMessage());
            tryAlternativeScroll();
        }
    }

    private void tryAlternativeScroll() {
        try {
            driver.executeScript("mobile: scrollGesture", java.util.Map.of(
                    "left", 100, "top", 500, "width", 500, "height", 1000,
                    "direction", "down",
                    "percent", 1.0
            ));
        } catch (Exception e) {
            System.out.println("Alternative scroll also failed: " + e.getMessage());
        }
    }

    private void enterTextWithClick(By locator, String text, String fieldName) {
        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                System.out.println("Entering text in " + fieldName + " (attempt " + attempt + ")");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                WebElement field = wait.until(driver1 -> driver1.findElement(locator));

                field.click();
                waitForSeconds(1);

                clearFieldSafely(field);
                waitForSeconds(1);

                field.sendKeys(text);
                waitForSeconds(1);

                System.out.println("Successfully entered text in " + fieldName);
                return; // ✅ هنا المشكلة! لازم return عشان نطلع من الـ loop

            } catch (org.openqa.selenium.WebDriverException e) {
                if (e.getMessage().contains("socket hang up") ||
                        e.getMessage().contains("Could not proxy command")) {

                    System.out.println("Socket hang up detected on attempt " + attempt);

                    if (attempt < maxRetries) {
                        System.out.println("Retrying after brief pause");
                        waitForSeconds(2);

                        if (attempt == maxRetries - 1) {
                            try {
                                driver.getPageSource();
                            } catch (Exception ex) {
                                System.out.println("Driver appears unhealthy, may need restart");
                                throw new RuntimeException("Driver connection lost for " + fieldName, e);
                            }
                        }
                    } else {
                        System.out.println("Failed after " + maxRetries + " attempts");
                        throw new RuntimeException("Failed to enter text in " + fieldName + " after " + maxRetries + " attempts", e);
                    }
                } else {
                    throw e;
                }
            } catch (Exception e) {
                System.out.println("Unexpected error in " + fieldName + ": " + e.getMessage());
                throw e;
            }
        }
    }

    private void clearFieldSafely(WebElement field) {
        try {
            String currentText = field.getText();
            if (currentText != null && !currentText.isEmpty()) {
                field.sendKeys(org.openqa.selenium.Keys.chord(
                        org.openqa.selenium.Keys.CONTROL, "a"
                ));
                waitForSeconds(1);
                field.sendKeys(org.openqa.selenium.Keys.DELETE);
            }
        } catch (Exception e1) {
            try {
                field.clear();
            } catch (Exception e2) {
                System.out.println("Could not clear field, proceeding anyway");
            }
        }
    }

    public void enterFirstName(String firstName) {
        enterTextWithClick(firstNameField, firstName, "First Name");
    }

    public void enterLastName(String lastName) {
        enterTextWithClick(lastNameField, lastName, "Last Name");
    }

    public void enterAddressLine1(String address) {
        enterTextWithClick(addressLine1Field, address, "Address Line 1");
    }

    public void enterCity(String city) {
        enterTextWithClick(cityField, city, "City");
    }

    public void selectProvince() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement provinceBtn = wait.until(driver1 -> driver1.findElement(provinceDropdown));
        provinceBtn.click();

        waitForSeconds(1);

        WebElement alexandriaBtn = wait.until(driver1 -> driver1.findElement(alexandriaOption));
        alexandriaBtn.click();

        System.out.println("Province selected");
    }

    public void enterZipCode(String zipCode) {
        enterTextWithClick(zipCodeField, zipCode, "ZIP/Postal Code");
    }

    public void enterPhoneNumber(String phone) {
        scrollToFindElement(phoneNumberField, 2);
        enterTextWithClick(phoneNumberField, phone, "Phone Number");
    }

    public void clickSaveAddress() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement saveBtn = wait.until(driver1 -> driver1.findElement(saveAddressButton));
        saveBtn.click();
        System.out.println("Clicked Save Address button");
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement message = wait.until(driver1 -> driver1.findElement(successMessage));
            boolean isDisplayed = message.isDisplayed();

            if (isDisplayed) {
                System.out.println("Success message displayed");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Success message NOT displayed");
            return false;
        }
    }

    public boolean isFailMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement message = wait.until(driver1 -> driver1.findElement(failMessage));
            boolean isDisplayed = message.isDisplayed();

            if (isDisplayed) {
                System.out.println("Fail message displayed");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Fail message NOT displayed");
            return false;
        }
    }

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
            System.out.println("Keyboard hidden");
        } catch (Exception e) {
            System.out.println("Keyboard was not visible or already hidden");
        }
    }
}