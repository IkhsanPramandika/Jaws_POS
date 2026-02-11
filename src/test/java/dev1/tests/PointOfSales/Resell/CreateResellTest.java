package dev1.tests.PointOfSales.Resell;

import dev1.tests.base.BaseTest;
import dev1.pages.PointOfSales.Resell.ResellPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateResellTest extends BaseTest {

    @Test(description = "Proses Transaksi Resell (Buyback)")
    public void testCreateResell() {
        ResellPage resellPage = new ResellPage(driver);

        try {
            driver.get(config.getProperty("HOME_URL"));
            resellPage.navigateToResell();

            // 1. Pilih Customer
            driver.findElement(By.id("content_search_customer")).click();
            waitForOverlayToDisappear();
            driver.findElement(By.id("content_keyword")).sendKeys(config.getProperty("DEFAULT_CUSTOMER"));
            driver.findElement(By.id("content_go")).click();
            waitForOverlayToDisappear();
            driver.findElement(By.xpath("//a[contains(@href, 'stid=')]")).click();

            // 2. Tambah Produk yang akan di-resell (Buyback)
            resellPage.addProductResell(config.getProperty("RESELL_PLU"));

            // 3. Review & Submit
            driver.findElement(By.id("content_review")).click();
            waitForOverlayToDisappear();

            // Klik "Yes/Save" menggunakan ID
            driver.findElement(By.id("content_save")).click();

            System.out.println("[SUCCESS] Transaksi Resell Berhasil.");

        } catch (Exception e) {
            Assert.fail("Gagal pada alur Resell: " + e.getMessage());
        }
    }
}