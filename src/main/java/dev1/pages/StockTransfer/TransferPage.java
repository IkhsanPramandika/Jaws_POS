package dev1.pages.StockTransfer;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class TransferPage extends BasePage {

    // === LOCATORS: NAVIGASI ===
    @FindBy(linkText = "Inventory") private WebElement menuInventory;
    @FindBy(xpath = "//a[contains(text(),'Transfer')]") private WebElement menuTransfer;
    @FindBy(id = "menu_TF1") private WebElement subMenuOutgoing;
    @FindBy(id = "menu_TF2") private WebElement subMenuIncoming;

    // === LOCATORS: OUTGOING TRANSFER ===
    @FindBy(id = "s2id_select-to-location") private WebElement selectDestStore;
    @FindBy(id = "input-product-plu") private WebElement inputPlu;
    @FindBy(id = "input-product-qty") private WebElement inputQty;
    @FindBy(id = "btn-add-product") private WebElement btnAddProduct;
    @FindBy(id = "input-remark") private WebElement txtRemark;
    @FindBy(id = "btn-submit-transfer") private WebElement btnSubmit;
    @FindBy(id = "btn-confirm-yes") private WebElement btnConfirmYes;

    // === LOCATORS: INCOMING TRANSFER ===
    @FindBy(id = "input-search-no-transfer") private WebElement inputSearchNoTf;
    @FindBy(id = "btn-search-transfer") private WebElement btnSearchTf;
    @FindBy(xpath = "//button[contains(text(),'Terima')]") private WebElement btnReceive;

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    @Step("Proses Outgoing Transfer ke {0}")
    public void processOutgoingTransfer(String destStore, String plu, String qty, String remark) {
        click(menuInventory);
        click(menuTransfer);
        click(subMenuOutgoing);
        waitForOverlayToDisappear();

        // Pilih Toko Tujuan & Input Barang
        selectFromSelect2(selectDestStore, destStore);
        type(inputPlu, plu);
        type(inputQty, qty);
        click(btnAddProduct);

        type(txtRemark, remark);
        click(btnSubmit);
        click(btnConfirmYes);
        waitForOverlayToDisappear();
    }

    @Step("Proses Incoming Transfer untuk No: {0}")
    public void processIncomingTransfer(String transferNo) {
        click(menuInventory);
        click(menuTransfer);
        click(subMenuIncoming);

        type(inputSearchNoTf, transferNo);
        click(btnSearchTf);
        waitForOverlayToDisappear();

        click(btnReceive);
        click(btnConfirmYes);
        waitForOverlayToDisappear();
    }
}