package dev1.tests.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

public class BaseTest {
    protected static WebDriver driver;
    protected static Properties config;
    protected static String mainWindowHandle;
    protected static WebDriverWait wait;

    @BeforeSuite
    public void setUp() throws IOException {
        config = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        config.load(fis);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(config.getProperty("BASE_URL"));
        mainWindowHandle = driver.getWindowHandle();
    }

    protected void switchToNewWindow(Set<String> existingHandles) {
        wait.until(d -> d.getWindowHandles().size() > existingHandles.size());

        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            if (!existingHandles.contains(handle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    protected void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("overlay")));
        } catch (Exception e) {
            // Abaikan jika overlay tidak muncul atau sudah hilang
        }
    }

    @AfterMethod
    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot();
        }
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] captureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}