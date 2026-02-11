package dev1.pages.StockReceiving;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class StockReceivingPage extends BasePage {

    // === LOCATORS (DARI KODINGAN ASLI KAMU) ===
    @FindBy(linkText = "Stock Receiving") private WebElement menuStockReceiving;
    @FindBy(xpath = "//a[@data-toggle='dropdown' and contains(text(), 'Register')]") private WebElement menuRegister;
    @FindBy(linkText = "Loose Diamond") private WebElement subMenuLD;

    @FindBy(id = "content_kw") private WebElement inputSearch;
    @FindBy(id = "content_go") private WebElement btnGo;
    @FindBy(id = "content_keterangan") private WebElement inputKeterangan;
    @FindBy(id = "content_save") private WebElement btnSave; // Tombol Next/Save
    @FindBy(id = "content_suppliernama") private WebElement inputSupplier;
    @FindBy(id = "content_suppliernomor") private WebElement inputSupplierNo;
    @FindBy(id = "content_humanstocknama") private WebElement inputPenerima;
    @FindBy(id = "content_add") private WebElement btnAddStone;
    @FindBy(id = "content_totalcarat") private WebElement inputTotalCarat;
    @FindBy(xpath = "//button[@id='content_save' and contains(@class, 'btn-success')]") private WebElement btnFinish;

    public StockReceivingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Register Loose Diamond")
    public void navigateToRegisterLD() {
        click(menuStockReceiving);
        waitForOverlayToDisappear();
        click(menuRegister);
        click(subMenuLD);
        waitForOverlayToDisappear();
    }

    @Step("Pilih Gudang dan Isi Keterangan: {0}")
    public void fillInitialForm(String warehouseName, String warehouseId, String remark) {
        type(inputSearch, warehouseName);
        click(btnGo);
        waitForOverlayToDisappear();
        click(driver.findElement(By.xpath("//a[contains(@href, 'idwarehouse=" + warehouseId + "')]")));
        waitForOverlayToDisappear();

        type(inputKeterangan, remark);
        click(btnSave);
        waitForOverlayToDisappear();
    }

    @Step("Isi Info Supplier dan Tipe: {0}")
    public void fillSupplierInfo(String name, String no, String type) {
        type(inputSupplier, name);
        type(inputSupplierNo, no);
        if (type.equals("Consignment")) {
            click(driver.findElement(By.id("content_RadioConsign")));
        } else {
            click(driver.findElement(By.id("content_RadioNonConsign")));
        }
        click(driver.findElement(By.id("content_btnSave")));
        waitForOverlayToDisappear();
    }

    @Step("Input Nilai Tengah (Median) Size Control")
    public void inputMedianSize() {
        WebElement sizeControlElement = driver.findElement(By.id("content_sizecontrol"));
        String rangeText = sizeControlElement.getText();

        String[] parts = rangeText.replace(",", ".").split(" - ");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        double median = (min + max) / 2;

        String medianString = String.format("%.3f", median).replace('.', ',');
        type(inputTotalCarat, medianString);
        click(btnSave);
        waitForOverlayToDisappear();
    }
}