package dev1.pages.PointOfSales.Sales;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import io.qameta.allure.Attachment;

public class PaymentPage extends BasePage {

    // === TABS & INPUTS ===
    @FindBy(xpath = "//a[@href='##cash']") private WebElement tabCash;
    @FindBy(xpath = "//a[@href='##creditcard']") private WebElement tabCredit;
    @FindBy(xpath = "//a[@href='##debitcard']") private WebElement tabDebit;

    @FindBy(id = "content_txt_creditcard_cardholdername") private WebElement inputName;
    @FindBy(id = "content_txt_creditcard_approvalcode") private WebElement inputAppCode;
    @FindBy(id = "content_txt_creditcard_cardholdernumber") private WebElement inputCardNum;

    // === BUTTONS ===
    @FindBy(xpath = "//button[contains(@onclick, 'confirm_cash')]") private WebElement btnConfirmCash;
    @FindBy(xpath = "//button[contains(@onclick, 'confirm_creditcard')]") private WebElement btnConfirmCredit;
    @FindBy(xpath = "//button[contains(@onclick, 'confirm_debitcard')]") private WebElement btnConfirmDebit;

    @FindBy(id = "content_save_payment_cash") private WebElement btnAddCash;
    @FindBy(id = "content_save_payment_creditcard") private WebElement btnAddCredit;
    @FindBy(id = "content_save_payment_debitcard") private WebElement btnAddDebit;

    @FindBy(id = "content_submit") private WebElement btnSubmitFinal;
    @FindBy(xpath = "//button[contains(text(), 'Yakin') and contains(@onclick, 'issubmit')]") private WebElement btnYakinSubmit;

    // Locator untuk nomor invoice di tab baru (Contoh ID: content_lbl_invoiceno)
    @FindBy(id = "content_lbl_invoiceno") private WebElement lblInvoiceNo;


    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @Step("Proses Pembayaran: {0}")
    public void processPayment(String type, String cardHolder, String appCode, String cardNum) {
        switch (type.toUpperCase()) {
            case "CASH":
                click(tabCash);
                click(btnConfirmCash);
                click(btnAddCash);
                break;
            case "CREDIT":
                click(tabCredit);
                type(inputName, cardHolder); // Menggunakan parameter
                type(inputAppCode, appCode);   // Menggunakan parameter
                type(inputCardNum, cardNum);   // Menggunakan parameter
                click(btnConfirmCredit);
                click(btnAddCredit);
                break;
            case "DEBIT":
                click(tabDebit);
                type(inputName, cardHolder);
                type(inputAppCode, appCode);
                type(inputCardNum, cardNum);
                click(btnConfirmDebit);
                click(btnAddDebit);
                break;
        }
        waitForOverlayToDisappear();
    }

    @Step("Ambil Nomor Invoice dari Tab Baru")
    public String getInvoiceNumber() {
        wait.until(d -> lblInvoiceNo.isDisplayed());
        String invoiceNo = lblInvoiceNo.getText().trim();
        saveInvoiceToAllure(invoiceNo); // Masukkan ke report
        return invoiceNo;
    }

    @Attachment(value = "Nomor Invoice Terbentuk", type = "text/plain")
    public String saveInvoiceToAllure(String message) {
        return message;
    }

    @Step("Submit Final dan Verifikasi Tab")
    public void submitAndVerifyTabs(int expectedTabs) {
        click(btnSubmitFinal);
        wait.until(d -> btnYakinSubmit.isDisplayed());
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnYakinSubmit);

        // Tunggu sampai tab invoice terbuka
        wait.until(d -> d.getWindowHandles().size() == expectedTabs);
        Assert.assertEquals(driver.getWindowHandles().size(), expectedTabs, "FAIL: Jumlah tab tidak sesuai!");
    }

    @Step("Tambah Pembayaran Down Payment")
    public void addPaymentDownPayment(String poNumber) {
        click(By.xpath("//a[@href='##downpayment']"));
        click(By.xpath("//button[contains(@onclick, 'confirm_downpayment')]"));
        click(By.id("content_save_payment_downpayment"));
        type(By.id("content_kw"), poNumber);
        click(By.id("content_go"));
        click(By.xpath("//a[contains(@href, 'Payment_DPAddSave.aspx')]"));
        waitForOverlayToDisappear();
    }

    @Step("Tambah Pembayaran Cash")
    public void addPaymentCash(boolean isFullPayment) {
        click(By.xpath("//a[@href='##cash']"));
        click(By.xpath("//button[contains(@onclick, 'confirm_cash')]"));
        click(By.id("content_save_payment_cash"));
        waitForOverlayToDisappear();
    }
}
