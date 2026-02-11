package dev1.tests.PointOfSales.DP;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.PointOfSales.DP.DPPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

public class CreateDownPaymentTest extends BaseTest {

    @Test(description = "Proses Pembuatan DP dengan Multi-Payment Lengkap")
    public void testCreateDownPaymentMulti() {
        DPPage dpPage = new DPPage(driver);

        try {
            // 1. Inisialisasi Halaman
            driver.get(config.getProperty("HOME_URL"));
            dpPage.navigateToDP();

            // 2. Pilih Customer
            driver.findElement(By.id("content_search_customer")).click();
            waitForOverlayToDisappear();
            driver.findElement(By.id("content_keyword")).sendKeys(config.getProperty("DEFAULT_CUSTOMER"));
            driver.findElement(By.id("content_go")).click();
            waitForOverlayToDisappear();
            driver.findElement(By.xpath("//a[contains(@href, 'stid=')]")).click();

            // 3. Isi Detail DP
            dpPage.fillDPDetails(config.getProperty("DP_AMOUNT"), config.getProperty("DP_REMARK"));

            // 4. CONTOH KOMBINASI MULTI-PAYMENT
            payWithCash(config.getProperty("DP_CASH_PART"));
            payWithDebit(config.getProperty("DP_DEBIT_PART"));
            payWithCredit();

            // 5. Submit Akhir
            driver.findElement(By.id("content_submit")).click();
            waitForOverlayToDisappear();

            Set<String> allHandles = driver.getWindowHandles();
            for (String handle : allHandles) {
                if (!handle.equals(mainWindowHandle)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            WebElement poNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(text(), 'PO/F/')]")
                        ));
                        String poNumber = poNumberElement.getText().trim();
                        TestDataProviders.PO_NUMBER_CASH = poNumber;
                        System.out.println("--- NOMOR PO BERHASIL DISIMPAN: " + poNumber + " ---");

            System.out.println("[SUCCESS] Transaksi DP Multi-Payment Berhasil.");

        } catch (Exception e) {
            Assert.fail("Gagal pada alur DP Multi-Payment: " + e.getMessage());
        }
    }

    // === HELPER METHODS PEMBAYARAN (MULTI-PAYMENT) ===

    private void payWithCash(String amount) {
        wait.until(d -> driver.findElement(By.xpath("//a[@href='##cash']"))).click();
        driver.findElement(By.id("content_txt_cash_amount")).clear();
        driver.findElement(By.id("content_txt_cash_amount")).sendKeys(amount);
        driver.findElement(By.id("content_save_payment_cash")).click();
        waitForOverlayToDisappear();
    }

    private void payWithDebit(String amount) {
        wait.until(d -> driver.findElement(By.xpath("//a[@href='##debitcard']"))).click();
        driver.findElement(By.id("content_txt_debitcard_amount")).clear();
        driver.findElement(By.id("content_txt_debitcard_amount")).sendKeys(amount);
        driver.findElement(By.id("content_txt_debitcard_cardholdername")).sendKeys(config.getProperty("CARD_HOLDER_NAME"));
        driver.findElement(By.id("content_txt_debitcard_approvalcode")).sendKeys(config.getProperty("APPROVAL_CODE"));
        selectFromSelect2Dropdown(By.id("s2id_content_ddl_debitcard_edc"), "BCA");
        driver.findElement(By.id("content_save_payment_debitcard")).click();
        waitForOverlayToDisappear();
    }

    private void payWithCredit() {
        wait.until(d -> driver.findElement(By.xpath("//a[@href='##creditcard']"))).click();
        driver.findElement(By.id("content_txt_creditcard_cardholdername")).sendKeys(config.getProperty("CARD_HOLDER_NAME"));
        driver.findElement(By.id("content_txt_creditcard_approvalcode")).sendKeys(config.getProperty("APPROVAL_CODE"));
        driver.findElement(By.id("content_txt_creditcard_cardholdernumber")).sendKeys(config.getProperty("CARD_NUMBER"));
        selectFromSelect2Dropdown(By.id("s2id_content_ddl_creditcard_edc"), "BCA");
        selectFromSelect2Dropdown(By.id("s2id_content_ddl_creditcard_bankprogram"), "12 Bulan");
        driver.findElement(By.id("content_save_payment_creditcard")).click();
        waitForOverlayToDisappear();
    }
}