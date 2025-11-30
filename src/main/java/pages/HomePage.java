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

public class HomePage {

    private final AndroidDriver driver;

    private final By moisturizersSection =
            By.xpath("//android.view.View[@content-desc='Moisturizers']");

    private final By profileTab =
            By.xpath("//android.widget.Button[contains(@content-desc, 'Profile') and contains(@content-desc, 'Tab 3 of 3')]");

    private final By homeTab =
            By.xpath("//android.widget.Button[contains(@content-desc, 'Home') and contains(@content-desc, 'Tab 1 of 3')]");

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void goToHomeTab() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement homeBtn = wait.until(driver1 -> driver1.findElement(homeTab));
            homeBtn.click();
            waitForSeconds(1);
            System.out.println("Navigated to Home tab");
        } catch (Exception e) {
            System.out.println("Could not navigate to Home tab: " + e.getMessage());
        }
    }

    public boolean isDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait.until(driver1 -> driver1.findElement(moisturizersSection));
            boolean isDisplayed = element.isDisplayed();

            if (isDisplayed) {
                System.out.println("Home page is displayed");
            }
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Home page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void goToProfile() {
        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                System.out.println("Looking for Profile tab (attempt " + attempt + ")");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement tab = wait.until(driver1 -> driver1.findElement(profileTab));
                tab.click();
                waitForSeconds(1);

                System.out.println("Clicked on Profile tab");
                return;

            } catch (Exception e) {
                System.out.println("Profile tab not found on attempt " + attempt);

                if (attempt < maxRetries) {
                    try {
                        driver.getPageSource();
                        waitForSeconds(1);

                        try {
                            goToHomeTab();
                        } catch (Exception ex) {
                        }
                    } catch (Exception ex) {
                        System.out.println("Could not refresh page: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Could not find Profile tab after " + maxRetries + " attempts");

                    try {
                        String pageSource = driver.getPageSource();
                        System.out.println("Current page source length: " + pageSource.length() + " characters");

                        if (pageSource.contains("Tab")) {
                            System.out.println("Found 'Tab' text in page source");
                        } else {
                            System.out.println("No 'Tab' text found - app might be in wrong state");
                        }
                    } catch (Exception ex) {
                        System.out.println("Could not get page source: " + ex.getMessage());
                    }

                    throw new RuntimeException("Failed to navigate to Profile tab after " + maxRetries + " attempts", e);
                }
            }
        }
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void searchUsingCoordinates(String searchText) throws IOException {
        try {
            tapOnCoordinates(220, 366);
            waitForSeconds(2);
            enterText(searchText);
            waitForSeconds(1);
            pressEnter();
            waitForSeconds(3);

            System.out.println("Search completed successfully for: " + searchText);

        } catch (Exception e) {
            System.out.println("Search failed: " + e.getMessage());
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
            System.out.println("Mobile type failed, trying ADB");
            try {
                String command = "adb shell input text \"" + text + "\"";
                Runtime.getRuntime().exec(command);
            } catch (Exception ex) {
                System.out.println("Text entry also failed: " + ex.getMessage());
                throw ex;
            }
        }
    }

    private void pressEnter() throws IOException {
        try {
            driver.executeScript("mobile: pressKey", java.util.Map.of(
                    "keycode", 66
            ));
        } catch (Exception e) {
            System.out.println("Mobile pressKey failed, trying ADB");
            try {
                Runtime.getRuntime().exec("adb shell input keyevent 66");
            } catch (Exception ex) {
                System.out.println("ADB Enter also failed: " + ex.getMessage());
                throw ex;
            }
        }
    }
}