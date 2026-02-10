package dev1.tests.Repair;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.Repair.RepairPage;
import org.testng.annotations.Test;
import java.util.Collections;

public class Repair03_FinishTest extends BaseTest {

    @Test(description = "Step 3: Menyelesaikan Proses Reparasi")
    public void testFinishRepair() {
        RepairPage repairPage = new RepairPage(driver);
        String plu = TestDataProviders.REPAIR_PLU;

        repairPage.goToFinishForm(plu);

        // Pindah ke tab baru yang terbuka
        switchToNewWindow(Collections.singleton(mainWindowHandle));

        repairPage.fillFinishDetail("Selesai QC - Automation");

        // Tutup tab finish dan kembali ke tab utama
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
}