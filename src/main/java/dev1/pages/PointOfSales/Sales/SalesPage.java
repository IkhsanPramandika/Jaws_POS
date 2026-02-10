package dev1.pages.PointOfSales.Sales;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SalesPage extends BasePage {

    // === Locators (Page Factory) ===
    @FindBy(linkText = "Point of Sales")
    private WebElement menuPOS;

    @FindBy(linkText = "Register")
    private WebElement menuRegister;

    @FindBy(linkText = "Sales")
    private WebElement subMenuSales;

    @FindBy(id = "content_search_customer")
    private WebElement btnSearchCustomer;

    @FindBy(id = "content_keyword")
    private WebElement inputKeywordCust;

    @FindBy(id = "content_go")
    private WebElement btnGoCust;

    @FindBy(xpath = "//a[contains(@href, 'stid=1071389')]")
    private WebElement selectCustomerTarget;

    @FindBy(id = "content_txt_plu")
    private WebElement fieldPLU;

    @FindBy(id = "content_btn_add_plu")
    private WebElement btnAddPLU;

    @FindBy(id = "content_review")
    private WebElement btnReview;

    @FindBy(id = "content_save")
    private WebElement btnContinuePayment;

    public SalesPage(WebDriver driver) {
        super(driver);
    }

    // === Actions ===
    @Step("Navigasi ke Menu Sales")
    public void navigateToSalesMenu() {
        click(menuPOS);
        click(menuRegister);
        click(subMenuSales);
    }

    @Step("Pilih Customer: {0}")
    public void selectCustomer(String phone) {
        click(btnSearchCustomer);
        type(inputKeywordCust, phone);
        click(btnGoCust);
        click(selectCustomerTarget);
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
        click(btnContinuePayment);
        waitForOverlayToDisappear();
    }
}