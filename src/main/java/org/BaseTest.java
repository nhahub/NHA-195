package org;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        cleanOldAllureResults();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        System.out.println("Setting up test class");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        System.out.println("Starting new test method");

        if (getDriver() == null) {
            initializeDriver();
        } else {
            checkDriverHealth();
        }
    }

    private void initializeDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Android Device");
        options.setUdid("qklz7tjfa67xhacu");
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");

        options.setAppPackage("com.korean_beauty_app");
        options.setAppActivity("com.korean_beauty_app.MainActivity");
        options.setNoReset(true);

        options.setCapability("uiautomator2ServerInstallTimeout", 60000);
        options.setCapability("androidInstallTimeout", 90000);
        options.setCapability("newCommandTimeout", 300);
        options.setCapability("uiautomator2ServerLaunchTimeout", 60000);
        options.setCapability("autoGrantPermissions", true);

        options.setCapability("skipServerInstallation", false);
        options.setCapability("skipDeviceInitialization", false);

        AndroidDriver androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.set(androidDriver);

        System.out.println("Driver initialized successfully");

        waitForAppToLoad();
    }

    private void waitForAppToLoad() {
        try {
            Thread.sleep(3000);

            if (getDriver() != null) {
                getDriver().getPageSource();
                System.out.println("App loaded successfully");
            }
        } catch (Exception e) {
            System.out.println("App load verification failed: " + e.getMessage());
        }
    }

    private void checkDriverHealth() {
        try {
            if (getDriver() != null) {
                getDriver().getPageSource();
                System.out.println("Driver is healthy");
            }
        } catch (Exception e) {
            System.out.println("Driver crashed, restarting");
            restartDriver();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result.getName());
        }

        String status = getTestStatus(result.getStatus());
        System.out.println("Test method finished: " + result.getName() + " - " + status + " ---");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        System.out.println("Test class finished");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        System.out.println("Test Suite Finished");
        closeAllDrivers();
        generateAllureReport();
    }
    private void captureScreenshot(String testName) {
        try {
            if (getDriver() != null) {
                byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot - " + testName, "image/png",
                        new ByteArrayInputStream(screenshot), "png");
                System.out.println("Screenshot captured for: " + testName);
            }
        } catch (Exception e) {
            System.out.println("Could not take screenshot: " + e.getMessage());
        }
    }

    private void cleanOldAllureResults() {
        try {
            String resultsPath = System.getProperty("user.dir") + "/target/allure-results";
            java.io.File resultsDir = new java.io.File(resultsPath);

            if (resultsDir.exists()) {
                deleteDirectory(resultsDir);
                System.out.println("Old Allure results cleaned");
            }

            resultsDir.mkdirs();
        } catch (Exception e) {
            System.out.println("Could not clean old results: " + e.getMessage());
        }
    }

    private void deleteDirectory(java.io.File directory) {
        java.io.File[] files = directory.listFiles();
        if (files != null) {
            for (java.io.File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    private void generateAllureReport() {
        try {
            System.out.println("Generating Allure Report...");

            String projectPath = System.getProperty("user.dir");
            String command = "cmd /c cd " + projectPath + " && allure generate target/allure-results --clean -o target/allure-report";

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Allure Report generated successfully!");
                System.out.println("Report location: target/allure-report/index.html");

                openAllureReport();
            } else {
                System.out.println("Allure Report generation failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            System.out.println("Error generating Allure Report: " + e.getMessage());
        }
    }

    private void openAllureReport() {
        try {
            String projectPath = System.getProperty("user.dir");
            String command = "cmd /c cd " + projectPath + " && allure open target/allure-report";
            Runtime.getRuntime().exec(command);
            System.out.println("Opening Allure Report in browser...");
        } catch (Exception e) {
            System.out.println("Could not open report automatically: " + e.getMessage());
        }
    }

    private String getTestStatus(int status) {
        switch (status) {
            case ITestResult.SUCCESS:
                return "PASSED";
            case ITestResult.FAILURE:
                return "FAILED";
            case ITestResult.SKIP:
                return "SKIPPED";
            default:
                return "UNKNOWN";
        }
    }

    public void restartDriver() {
        System.out.println("Restarting driver...");
        closeDriver();

        try {
            initializeDriver();

            resetAppToHomePage();

            System.out.println("Driver restarted successfully");
        } catch (Exception e) {
            System.out.println("Failed to restart driver: " + e.getMessage());
            throw new RuntimeException("Failed to restart driver", e);
        }
    }

    private void resetAppToHomePage() {
        try {
            if (getDriver() != null) {
                getDriver().activateApp("com.korean_beauty_app");
                Thread.sleep(2000);

                System.out.println("App reset to home page");
            }
        } catch (Exception e) {
            System.out.println("Could not reset app to home: " + e.getMessage());
        }
    }

    private void closeDriver() {
        try {
            if (getDriver() != null) {
                getDriver().quit();
                driver.remove();
                System.out.println("Driver closed");
            }
        } catch (Exception e) {
            System.out.println("Error while closing driver: " + e.getMessage());
            driver.remove();
        }
    }

    private void closeAllDrivers() {
        closeDriver();
    }

    public static AndroidDriver getDriver() {
        return driver.get();
    }
}