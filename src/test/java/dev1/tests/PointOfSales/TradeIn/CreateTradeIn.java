package dev1.tests.PointOfSales.TradeIn;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.PointOfSales.TradeIn.TradeInPage; // Import dari package baru kamu
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;

public class CreateTradeIn extends BaseTest {

    @Test(dataProvider = "tradeInData", dataProviderClass = TestDataProviders.class,
            description = "Tahap 2: Transaksi Tukar Tambah (Trade In)")
    public void testTradeInTransaction(String pluBaru, String pluLama, String paymentType) {
        TradeInPage tradePage = new TradeInPage(driver);

        try {
            driver.get(config.getProperty("HOME_URL"));
            tradePage.navigateToTradeIn();

            // Pindah ke Tab Trade In
            switchToNewWindow(Collections.singleton(mainWindowHandle));

            // Gunakan 'wait' yang diwarisi dari BaseTest agar stabil
            wait.until(d -> driver.findElement(By.id("content_txt_plu"))).sendKeys(pluBaru);
            driver.findElement(By.id("content_btn_add_plu")).click();
            waitForOverlayToDisappear();

            // Input Barang Lama dari Customer
            driver.findElement(By.id("content_addrs")).click();
            wait.until(d -> driver.findElement(By.id("content_kw"))).sendKeys(pluLama);
            driver.findElement(By.id("content_go")).click();
            driver.findElement(By.xpath("//a[contains(@href, 'Resell_ItemAddSave.aspx')]")).click();
            waitForOverlayToDisappear();

            // Review & Lanjut ke Pembayaran
            driver.findElement(By.id("content_review")).click();

            // Gunakan JavascriptExecutor untuk klik tombol konfirmasi yang sering tertutup overlay
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                    driver.findElement(By.id("content_save")));

            System.out.println("[SUCCESS] Transaksi Trade In " + pluBaru + " Berhasil disubmit.");

        } catch (Exception e) {
            Assert.fail("Gagal pada alur Trade In: " + e.getMessage());
        } finally {
            driver.get(config.getProperty("HOME_URL"));
        }
    }
}