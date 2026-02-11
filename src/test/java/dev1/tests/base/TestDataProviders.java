package dev1.tests.base;

import org.testng.annotations.DataProvider;

public class TestDataProviders {

    // === 1. DYNAMIC DATA CONTAINERS  ===
    // Tempat penyimpanan sementara untuk estafet data antar Test
    public static String PO_NUMBER_CASH = null;
    public static String PO_NUMBER_CREDIT = null;
    public static String PO_NUMBER_DEBIT = null;

    public static String REPAIR_PLU = null;
    public static String TRANSFER_NO = null;

    // === 2. DATA PROVIDERS (Tambahkan method untuk looping test) ===

    @DataProvider(name = "salesData")
    public static Object[][] getSalesData() {
        return new Object[][] {
                { "ABD024388", "CASH" },
                { "ABD024388", "CREDIT" },
                { "ABD024388", "DEBIT" }
        };
    }

    @DataProvider(name = "dpData")
    public static Object[][] getDPData() {
        return new Object[][] {
                { "ABD024388", "CASH" },
                { "ABD024388", "CREDIT" },
                { "ABD024388", "DEBIT" }
        };
    }

    @DataProvider(name = "consignmentData")
    public static Object[][] getConsignmentData() {
        return new Object[][] {
                { "Consignment" },
                { "Non-Consignment" }
        };
    }

    @DataProvider(name = "paymentData")
    public static Object[][] getPaymentData() {
        return new Object[][] {
                { "ABD024388", "CASH" }
        };
    }
}