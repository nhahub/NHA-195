package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class ProfilePage {

    private final AndroidDriver driver;

    private final By myAddresses = By.xpath("//android.view.View[@content-desc='My Addresses']");

    private final By rewardsProgramButton =
            By.xpath("//android.widget.Button[@content-desc='Rewards Program']");

    private final By rewardsTitle =
            By.xpath("//android.widget.TextView[@text='Rewards']");

    private final By backButton =
            By.xpath("//android.widget.Button[@content-desc='Back']");

    private final By addNewAddressButton =
            By.xpath("//android.widget.Button[contains(@content-desc, 'Add New Address')]");

    public ProfilePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForProfilePage() {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(driver1 -> driver1.findElement(myAddresses));
    }

    public boolean isDisplayed() {
        return waitForProfilePage().isDisplayed();
    }

    public void clickRewardsProgram() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement rewardsBtn = wait.until(driver1 -> driver1.findElement(rewardsProgramButton));
        rewardsBtn.click();
        System.out.println("Clicked on Rewards Program button");
    }

    public boolean isRewardsPageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement rewardsElement = wait.until(driver1 -> driver1.findElement(rewardsTitle));
            boolean isDisplayed = rewardsElement.isDisplayed();

            if (isDisplayed) {
                System.out.println("Rewards page opened successfully");
            }
            return isDisplayed;

        } catch (Exception e) {
            System.out.println("Rewards page not found: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement backBtn = wait.until(driver1 -> driver1.findElement(backButton));
        backBtn.click();
    }


    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void scrollDown() {
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
            System.out.println("Scrolled down");

        } catch (Exception e) {
            System.out.println("Scroll failed: " + e.getMessage());
        }
    }


}