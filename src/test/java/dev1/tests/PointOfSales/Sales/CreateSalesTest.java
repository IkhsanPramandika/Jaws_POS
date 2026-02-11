package dev1.tests.PointOfSales.Sales;

import dev1.tests.base.BaseTest;
import dev1.pages.PointOfSales.Sales.SalesPage;
import dev1.pages.PointOfSales.Sales.PaymentPage;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Epic("POS Automation")
@Feature("Sales Module")
public class CreateSalesTest extends BaseTest {

    /**
     * DataProvider untuk menjalankan test secara berulang (Looping)
     * Mengambil PLU dan Tipe Pembayaran dari config.properties
     */

    @DataProvider(name = "salesData")
    public Object[][] getSalesData() {
        return new Object[][] {
                { config.getProperty("PLU_CASH"), "CASH" },
                { config.getProperty("PLU_CREDIT"), "CREDIT" },
                { config.getProperty("PLU_DEBIT"), "DEBIT" }
        };
    }

    @Test(dataProvider = "salesData", description = "Skenario Penjualan Multi-Metode Pembayaran")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Menjalankan alur penjualan penuh mulai dari input PLU hingga verifikasi tab invoice & order.")
    public void testCompleteSalesFlow(String plu, String paymentType) {
        SalesPage salesPage = new SalesPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // --- PHASE 1: NAVIGASI & INPUT BARANG ---
        salesPage.navigateToSalesMenu();
        switchToNewWindow(Collections.singleton(mainWindowHandle));

        salesPage.selectCustomer(config.getProperty("DEFAULT_CUSTOMER"));
        salesPage.inputProductPLU(plu);
        salesPage.proceedToPayment();

        // --- PHASE 2: PEMBAYARAN DINAMIS ---
        // Data cardHolder, appCode, dan cardNum diambil otomatis dari config
        paymentPage.processPayment(
                paymentType,
                config.getProperty("CARD_HOLDER_NAME"),
                config.getProperty("APPROVAL_CODE"),
                config.getProperty("CARD_NUMBER")
        );

        // --- PHASE 3: SUBMIT & ASSERTION ---
        // Verifikasi total 4 tab: Home, Sales Lunas, Invoice, Order
        paymentPage.submitAndVerifyTabs(4);

        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            driver.switchTo().window(handle);
            try {
                shortWait.until(ExpectedConditions.urlContains("Print_InvoiceDJ.aspx"));
                String noInvoice = paymentPage.getInvoiceNumber();
                System.out.println("[INFO] " + paymentType + " Invoice Berhasil Dibuat: " + noInvoice);
                break;
            } catch (Exception e) {
            }
        }

        closeExtraTabs();
    }

}