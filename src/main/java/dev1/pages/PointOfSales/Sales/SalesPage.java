package dev1.pages.PointOfSales.Sales;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SalesPage extends BasePage {

    // === LOCATORS SALES ===
    @FindBy(linkText = "Point of Sales") private WebElement menuPOS;
    @FindBy(linkText = "Register") private WebElement menuRegister;
    @FindBy(linkText = "Sales") private WebElement subMenuSales;
    @FindBy(id = "content_search_customer") private WebElement btnSearchCustomer;
    @FindBy(id = "content_keyword") private WebElement inputKeywordCust;
    @FindBy(id = "content_go") private WebElement btnGoCust;
    @FindBy(id = "content_txt_plu") private WebElement fieldPLU;
    @FindBy(id = "content_btn_add_plu") private WebElement btnAddPLU;
    @FindBy(id = "content_review") private WebElement btnReview;
    @FindBy(id = "content_save") private WebElement btnContinuePayment;

    // === LOCATORS VOID & STATUS ===
    @FindBy(id = "content_submit") private WebElement btnSubmit;
    @FindBy(id = "content_spn_payment_balancedue") private WebElement lblBalanceDue;

    private final String voidCashButtonXpath = "//table[@id='content_tb3']/tbody/tr[not(contains(., 'Down Payment')) and .//a[contains(@class, 'btn-danger')]]//a[contains(@class, 'btn-danger')]";

    public SalesPage(WebDriver driver) {
        super(driver);
    }

    // === ACTIONS ===

    @Step("Navigasi ke Menu Sales")
    public void navigateToSalesMenu() {
        click(menuPOS);
        click(menuRegister);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subMenuSales);
        waitForOverlayToDisappear();
    }

    @Step("Pilih Customer: {0}")
    public void selectCustomer(String phone) {
        click(btnSearchCustomer);
        type(inputKeywordCust, phone);
        click(btnGoCust);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'stid=')]"))).click();
        waitForOverlayToDisappear();
    }

    @Step("Input Produk PLU: {0}")
    public void inputProductPLU(String plu) {
        type(fieldPLU, plu);
        click(btnAddPLU);
        waitForOverlayToDisappear();
    }

    @Step("Lanjut ke Halaman Pembayaran")
    public void proceedToPayment() {
        click(btnReview);
        // Pastikan overlay hilang setelah klik review sebelum klik continue
        waitForOverlayToDisappear();
        click(btnContinuePayment);
        waitForOverlayToDisappear();
    }

    @Step("Lakukan Void pada Cash Payment")
    public void voidCashPayment() {
        WebElement voidBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(voidCashButtonXpath)));
        voidBtn.click();
        waitForOverlayToDisappear();
    }

    public boolean isSubmitButtonVisible() {
        try {
            return btnSubmit.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public String getBalanceDue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content_spn_payment_balancedue")))
                .getText().replace(".", "");
    }

}