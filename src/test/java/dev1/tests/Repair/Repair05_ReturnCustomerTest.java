package dev1.tests.Repair;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.Repair.RepairPage;
import org.testng.annotations.Test;

public class Repair05_ReturnCustomerTest extends BaseTest {

    @Test(description = "Step 5: Pengembalian ke Customer")
    public void testReturnToCustomer() {
        RepairPage repairPage = new RepairPage(driver);
        String plu = TestDataProviders.REPAIR_PLU;

        System.out.println("[LOG] Mengembalikan PLU: " + plu + " ke Customer.");
        repairPage.processReturn(plu);

        System.out.println("--- SELURUH ALUR REPAIR SELESAI ---");
    }
}