package dev1.tests.Repair;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.Repair.RepairPage;
import org.testng.annotations.Test;

public class Repair02_ServicePointTest extends BaseTest {

    @Test(description = "Step 2: Update Status di Service Point")
    public void testServicePoint() {
        RepairPage repairPage = new RepairPage(driver);

        // Mengambil PLU hasil Step 1
        String plu = TestDataProviders.REPAIR_PLU;

        repairPage.processServicePoint(plu);
    }
}