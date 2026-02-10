package dev1.tests.Repair;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.Repair.RepairPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Set;

public class Repair04_PaymentTest extends BaseTest {

    @Test(description = "Step 4: Proses Pembayaran Reparasi")
    public void testRepairPayment() {
        if (TestDataProviders.REPAIR_PLU == null) {
            TestDataProviders.REPAIR_PLU = "ABB040504";
        }

        String plu = TestDataProviders.REPAIR_PLU;
        String payTabHandle = null;

        try {
            driver.switchTo().window(mainWindowHandle);
            driver.get(config.getProperty("HOME_URL"));

            RepairPage repairPage = new RepairPage(driver);
            repairPage.initiatePayment(plu);

            Set<String> allHandles = driver.getWindowHandles();
            for (String handle : allHandles) {
                if (!handle.equals(mainWindowHandle)) {
                    driver.switchTo().window(handle);
                    payTabHandle = handle;
                    break;
                }
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-modal-confirmation"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-confirmation-submit"))).click();

            System.out.println("[SUCCESS] Pembayaran untuk PLU " + plu + " telah disubmit.");

        } catch (Exception e) {
            Assert.fail("Gagal pada proses Pembayaran Reparasi: " + e.getMessage());
        } finally {
            if (payTabHandle != null && driver.getWindowHandles().contains(payTabHandle)) {
                driver.switchTo().window(payTabHandle);
                driver.close();
            }
            driver.switchTo().window(mainWindowHandle);
        }
    }
}