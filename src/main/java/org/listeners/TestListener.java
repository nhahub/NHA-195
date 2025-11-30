package org.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    private LocalDateTime suiteStartTime;
    private LocalDateTime testStartTime;

    @Override
    public void onStart(ITestContext context) {
        suiteStartTime = LocalDateTime.now();
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          TEST STARTED: " + context.getName());
        System.out.println("║          Start Time: " + formatDateTime(suiteStartTime));
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n");
    }

    @Override
    public void onFinish(ITestContext context) {
        LocalDateTime suiteEndTime = LocalDateTime.now();
        Duration duration = Duration.between(suiteStartTime, suiteEndTime);

        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          TEST FINISHED: " + context.getName());
        System.out.println("║          End Time: " + formatDateTime(suiteEndTime));
        System.out.println("║          Duration: " + formatDuration(duration));
        System.out.println("║");
        System.out.println("║          Tests Passed:  " + context.getPassedTests().size());
        System.out.println("║          Tests Failed:  " + context.getFailedTests().size());
        System.out.println("║          Tests Skipped: " + context.getSkippedTests().size());
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n");
    }

    @Override
    public void onTestStart(ITestResult result) {
        testStartTime = LocalDateTime.now();
        String testName = result.getMethod().getMethodName();

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ TEST STARTED: " + testName);
        System.out.println("│    Class: " + result.getTestClass().getName());
        System.out.println("│    Time: " + formatDateTime(testStartTime));
        System.out.println("└─────────────────────────────────────────────────────────┘");

        Allure.addAttachment("Test Start Time", formatDateTime(testStartTime));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LocalDateTime testEndTime = LocalDateTime.now();
        Duration duration = Duration.between(testStartTime, testEndTime);
        String testName = result.getMethod().getMethodName();

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ TEST PASSED: " + testName);
        System.out.println("│  Duration: " + formatDuration(duration));
        System.out.println("└─────────────────────────────────────────────────────────┘\n");

        Allure.addAttachment("Test Duration", formatDuration(duration));
        Allure.addAttachment("Status", "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LocalDateTime testEndTime = LocalDateTime.now();
        Duration duration = Duration.between(testStartTime, testEndTime);
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ TEST FAILED: " + testName);
        System.out.println("│  Duration: " + formatDuration(duration));
        System.out.println("│  Error: " + (throwable != null ? throwable.getMessage() : "Unknown"));
        System.out.println("└─────────────────────────────────────────────────────────┘\n");

        Allure.addAttachment("Test Duration", formatDuration(duration));
        Allure.addAttachment("Status", "FAILED");
        if (throwable != null) {
            Allure.addAttachment("Error Message", throwable.getMessage());
            Allure.addAttachment("Stack Trace", getStackTrace(throwable));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ TEST SKIPPED: " + testName);
        System.out.println("└─────────────────────────────────────────────────────────┘\n");

        Allure.addAttachment("Status", "SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("TEST FAILED BUT WITHIN SUCCESS PERCENTAGE: " + testName);
    }


    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long millis = duration.toMillis() % 1000;

        if (minutes > 0) {
            return String.format("%d min %d sec %d ms", minutes, seconds, millis);
        } else if (seconds > 0) {
            return String.format("%d sec %d ms", seconds, millis);
        } else {
            return String.format("%d ms", millis);
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}