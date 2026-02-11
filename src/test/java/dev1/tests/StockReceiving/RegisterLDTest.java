package dev1.tests.StockReceiving;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.StockReceiving.StockReceivingPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterLDTest extends BaseTest {

    @Test(dataProvider = "consignmentData", dataProviderClass = TestDataProviders.class)
    public void testRegisterLooseDiamond(String consignmentType) {
        StockReceivingPage ldPage = new StockReceivingPage(driver);

        try {
            driver.get(config.getProperty("HOME_URL"));
            ldPage.navigateToRegisterLD();

            // Step 1 & 2: Pilih Gudang & Keterangan
            ldPage.fillInitialForm(
                    config.getProperty("LD_WAREHOUSE_KEYWORD"),
                    config.getProperty("LD_WAREHOUSE_ID"),
                    "test automation " + consignmentType
            );

            // Step 3: Supplier
            ldPage.fillSupplierInfo(
                    config.getProperty("LD_SUPPLIER_NAME"),
                    config.getProperty("LD_SUPPLIER_NO"),
                    consignmentType
            );

            // Step 4: Penerima
            type(By.id("content_humanstocknama"), config.getProperty("LD_RECEIVER_NAME"));
            click(By.id("content_save"));
            waitForOverlayToDisappear();

            // Step 5 & 6: Cari Batu
            click(By.id("content_add"));
            waitForOverlayToDisappear();
            type(By.id("content_kw"), config.getProperty("LD_STONE_ID"));
            click(By.id("content_go"));
            waitForOverlayToDisappear();
            click(By.xpath("//a[contains(@href, 'LD_StoneAddSave.aspx')]"));

            // Step 7: Logika Median Size
            ldPage.inputMedianSize();

            // Step 8, 9, 10: Wizard Finish
            click(By.id("content_save")); // Hal 8
            waitForOverlayToDisappear();
            click(By.id("content_save")); // Hal 9
            waitForOverlayToDisappear();

            click(By.xpath("//button[@id='content_save' and contains(@class, 'btn-success')]"));
            waitForOverlayToDisappear();

            Assert.assertTrue(driver.getCurrentUrl().contains("LD.aspx"), "Gagal kembali ke list LD!");

        } catch (Exception e) {
            Assert.fail("Gagal Register LD: " + e.getMessage());
        }
    }
}