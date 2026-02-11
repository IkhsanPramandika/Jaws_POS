package dev1.pages.PointOfSales.Resell;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class ResellPage extends BasePage {

    // === LOCATORS: NAVIGASI ===
    @FindBy(linkText = "Point of Sales") private WebElement menuPOS;
    @FindBy(linkText = "Register") private WebElement menuRegister;
    @FindBy(linkText = "Resell") private WebElement subMenuResell;

    // === LOCATORS: FORM RESELL ===
    @FindBy(id = "content_search_customer") private WebElement btnSearchCustomer;
    @FindBy(id = "content_addrs") private WebElement btnAddProduct;
    @FindBy(id = "content_kw") private WebElement inputSearchPlu;
    @FindBy(id = "content_go") private WebElement btnGo;
    @FindBy(id = "content_review") private WebElement btnReview;

    public ResellPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Menu Resell")
    public void navigateToResell() {
        click(menuPOS);
        click(menuRegister);
        click(subMenuResell);
        waitForOverlayToDisappear();
    }

    @Step("Cari dan Tambah Produk PLU: {0}")
    public void addProductResell(String plu) {
        click(btnAddProduct);
        waitForOverlayToDisappear();
        type(inputSearchPlu, plu);
        click(btnGo);
        waitForOverlayToDisappear();
        // Klik tombol add/save untuk produk yang ditemukan
        driver.findElement(By.xpath("//a[contains(@href, 'Resell_ItemAddSave.aspx')]")).click();
        waitForOverlayToDisappear();
    }
}