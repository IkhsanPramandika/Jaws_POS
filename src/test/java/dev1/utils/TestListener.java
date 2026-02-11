package dev1.utils;

import dev1.tests.base.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>> Memulai Test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>> Test Berhasil: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(">>> TEST GAGAL: " + result.getName());

        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        if (driver != null) {
            saveScreenshotPNG(driver);
        }
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}