package dev1.tests.OutletInventory;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.OutletInventory.VerifyStockPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class VerifyStockTest extends BaseTest {

    @Test(dataProvider = "paymentData", dataProviderClass = TestDataProviders.class)
    public void testStockVerification(String plu, String paymentType) {
        VerifyStockPage stockPage = new VerifyStockPage(driver);

        try {
            // 1. Cek di Diamond Jewellery List (Harus Kosong)
            stockPage.navigateToDJList();
            stockPage.searchPlu(plu);

            List<WebElement> rows = driver.findElements(By.xpath("//table[@id='content_tb']/tbody/tr/td[contains(text(), '" + plu + "')]"));
            Assert.assertTrue(rows.isEmpty(), "Item PLU " + plu + " masih ada di stok!");

            // 2. Cek di Stock Ledger (Harus tercatat -1 Penjualan)
            stockPage.navigateToStockLedger();
            stockPage.searchPlu(plu);

            Assert.assertEquals(stockPage.getLedgerValue(), "-1", "Value Ledger salah!");
            Assert.assertEquals(stockPage.getLedgerRemarks(), "Penjualan", "Remarks Ledger salah!");

            System.out.println("[SUCCESS] Verifikasi Stok Berhasil untuk: " + plu);

        } catch (Exception e) {
            Assert.fail("Gagal verifikasi stok: " + e.getMessage());
        } finally {
            // Gunakan config.getProperty agar tidak error merah
            driver.get(config.getProperty("HOME_URL"));
            waitForOverlayToDisappear();
        }
    }
}