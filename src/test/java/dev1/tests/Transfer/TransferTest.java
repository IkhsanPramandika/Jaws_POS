package dev1.tests.Transfer;

import dev1.tests.base.BaseTest;
import dev1.tests.base.TestDataProviders;
import dev1.pages.StockTransfer.TransferPage;
import org.testng.annotations.Test;

public class TransferTest extends BaseTest {

    @Test(priority = 1, description = "Step 1: Outgoing Transfer")
    public void test01_OutgoingTransfer() {
        TransferPage transferPage = new TransferPage(driver);

        transferPage.processOutgoingTransfer(
                config.getProperty("TRANSFER_DEST_STORE"),
                config.getProperty("TRANSFER_PLU"),
                config.getProperty("TRANSFER_QTY"),
                config.getProperty("TRANSFER_REMARK")
        );
    }

    @Test(priority = 2, dependsOnMethods = "test01_OutgoingTransfer", description = "Step 2: Incoming Transfer")
    public void test02_IncomingTransfer() {
        TransferPage transferPage = new TransferPage(driver);

        // Menggunakan nomor transfer yang didapat dari Step 1
        transferPage.processIncomingTransfer(TestDataProviders.TRANSFER_NO);
    }
}