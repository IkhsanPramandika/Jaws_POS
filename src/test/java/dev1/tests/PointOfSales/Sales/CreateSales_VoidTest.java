package dev1.tests.PointOfSales.Sales;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.PointOfSales.Sales.SalesPage;
import dev1.pages.PointOfSales.Sales.PaymentPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;

public class CreateSales_VoidTest extends BaseTest {

    @Test(dataProvider = "salesData", dataProviderClass = TestDataProviders.class,
            description = "Skenario Void: Verifikasi tombol Submit hilang setelah pembayaran dibatalkan")
    public void testVoidPaymentHidesSubmitButton(String plu, String paymentType) {
        SalesPage salesPage = new SalesPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);

        try {
            // === 1. NAVIGASI & INPUT ===
            driver.get(config.getProperty("HOME_URL"));
            salesPage.navigateToSalesMenu();
            switchToNewWindow(Collections.singleton(mainWindowHandle));

            selectStore(); // Helper dari BaseTest
            salesPage.selectCustomer(config.getProperty("DEFAULT_CUSTOMER"));
            salesPage.inputProductPLU(plu);
            salesPage.proceedToPayment();

            // === 2. PROSES PEMBAYARAN (Lunas) ===
            paymentPage.addPaymentDownPayment(TestDataProviders.PO_NUMBER_CASH);
            paymentPage.addPaymentCash(true); // Bayar lunas sisa tagihan
            waitForOverlayToDisappear();

            // === 3. VERIFIKASI 1: Tombol Submit Muncul ===
            Assert.assertTrue(salesPage.isSubmitButtonVisible(), "Tombol Submit seharusnya muncul setelah lunas.");

            // === 4. PROSES VOID ===
            System.out.println("Melakukan Void pada pembayaran Cash...");
            salesPage.voidCashPayment();

            // === 5. VERIFIKASI 2: Tombol Submit Hilang ===
            Assert.assertFalse(salesPage.isSubmitButtonVisible(), "Tombol Submit seharusnya HILANG setelah pembayaran di-void.");

            // Verifikasi Balance Due tidak nol
            String balance = salesPage.getBalanceDue();
            Assert.assertNotEquals(balance, "0", "Balance Due seharusnya kembali muncul (tidak 0) setelah void.");
            System.out.println("[SUCCESS] Verifikasi Void Berhasil. Balance kembali ke: " + balance);

        } catch (Exception e) {
            Assert.fail("Gagal pada pengujian Void Sales: " + e.getMessage());
        } finally {
            closeExtraTabs();
        }
    }
}