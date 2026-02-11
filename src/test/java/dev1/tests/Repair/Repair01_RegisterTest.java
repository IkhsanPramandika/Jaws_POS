package dev1.tests.Repair;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.Repair.RepairPage;
import org.testng.annotations.Test;

public class Repair01_RegisterTest extends BaseTest {

    @Test(description = "Step 1: Registrasi Barang Reparasi")
    public void testRegisterRepair() {
        RepairPage repairPage = new RepairPage(driver);
        String photoPath = System.getProperty("user.dir") + "/" + config.getProperty("REPAIR_PHOTO_NAME");

        repairPage.navigateToRegister();
        repairPage.searchAndPickCustomer(config.getProperty("REPAIR_CUSTOMER_PHONE"));

        // Simpan PLU ke TestDataProviders agar bisa dipakai Step 2
        TestDataProviders.REPAIR_PLU = repairPage.findAndPickItem();

        repairPage.submitRegistration(config.getProperty("REPAIR_KETERANGAN"), photoPath);
    }
}