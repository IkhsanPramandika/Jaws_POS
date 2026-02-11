package dev1.tests.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    public void setUp() throws IOException {
        config = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        config.load(fis);

        // 1. Setup WebDriverManager
        WebDriverManager.chromedriver().setup();

        // 2. Konfigurasi ChromeOptions untuk CI/CD (Headless)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); //BISA DIMATIKAN
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(config.getProperty("BASE_URL"));
        mainWindowHandle = driver.getWindowHandle();
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
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

    protected void selectFromSelect2Dropdown(By locator, String searchText) {
        try {
            WebElement container = wait.until(ExpectedConditions.elementToBeClickable(locator));
            container.click();
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select2-input")));
            inputField.sendKeys(searchText);
            inputField.sendKeys(Keys.ENTER);

            waitForOverlayToDisappear();
        } catch (Exception e) {
            System.out.println("Gagal memilih di Select2: " + e.getMessage());
        }
    }

    protected void selectStore() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("content_search_store"))).click();
        type(By.id("content_keyword"), config.getProperty("DEFAULT_STORE_KEYWORD"));
        click(By.id("content_go"));
        click(By.xpath("//a[contains(., '" + config.getProperty("DEFAULT_STORE_NAME") + "')]"));
        waitForOverlayToDisappear();
    }

    protected void closeExtraTabs() {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(mainWindowHandle);
        driver.get(config.getProperty("HOME_URL"));
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