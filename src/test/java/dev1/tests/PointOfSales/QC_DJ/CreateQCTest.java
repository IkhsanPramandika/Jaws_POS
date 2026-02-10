package dev1.tests.PointOfSales.QC_DJ;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.PointOfSales.QC_DJ.QCPage; // Import dari package baru kamu
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;

public class CreateQCTest extends BaseTest {

    @Test(dataProvider = "pluOnlyData", dataProviderClass = TestDataProviders.class,
            description = "Tahap 1: Quality Control Barang Lama")
    public void testQCProcess(String plu) {
        QCPage qcPage = new QCPage(driver);

        try {
            // Menggunakan 'config' sesuai variabel di BaseTest
            driver.get(config.getProperty("HOME_URL"));
            qcPage.navigateToQC();

            // Pindah ke Tab QC List
            switchToNewWindow(Collections.singleton(mainWindowHandle));
            String listHandle = driver.getWindowHandle();

            // Cari dan Buka Detail
            qcPage.searchAndOpenPlu(plu);

            // Pindah ke Tab Submit Detail
            switchToNewWindow(driver.getWindowHandles());

            // Proses Klik Submit
            qcPage.submitQC();

            System.out.println("[SUCCESS] QC Selesai untuk PLU: " + plu);

            // Tutup tab detail dan kembali ke list
            driver.close();
            driver.switchTo().window(listHandle);
            driver.close();

        } catch (Exception e) {
            Assert.fail("Gagal pada alur QC: " + e.getMessage());
        } finally {
            // Pastikan kembali ke tab utama
            driver.switchTo().window(mainWindowHandle);
        }
    }
}