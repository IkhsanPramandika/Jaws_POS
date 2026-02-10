package dev1.pages.StockReceiving;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReceivingPage extends BasePage {

    // === LOCATORS: NAVIGASI ===
    @FindBy(linkText = "Stock Receiving") private WebElement menuReceiving;
    @FindBy(xpath = "//a[@data-toggle='dropdown' and contains(text(), 'Register')]") private WebElement dropdownRegister;
    @FindBy(linkText = "Loose Diamond") private WebElement subMenuLD;

    // === LOCATORS: REGISTER LD ===
    @FindBy(id = "content_kw") private WebElement inputKeyword;
    @FindBy(id = "content_go") private WebElement btnSearch;
    @FindBy(id = "content_keterangan") private WebElement txtKeterangan;
    @FindBy(id = "content_suppliernama") private WebElement txtSupplierNama;
    @FindBy(id = "content_suppliernomor") private WebElement txtSupplierNomor;
    @FindBy(id = "content_RadioConsign") private WebElement rdoConsign;
    @FindBy(id = "content_RadioNonConsign") private WebElement rdoNonConsign;
    @FindBy(id = "content_btnSave") private WebElement btnNextStep;
    @FindBy(id = "content_humanstocknama") private WebElement txtPenerima;
    @FindBy(id = "content_add") private WebElement btnAddStone;
    @FindBy(id = "content_sizecontrol") private WebElement lblSizeControl;
    @FindBy(id = "content_totalcarat") private WebElement inputCarat;
    @FindBy(id = "content_save") private WebElement btnSave;

    public ReceivingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Register Loose Diamond")
    public void navigateToRegisterLD() {
        click(menuReceiving);
        click(dropdownRegister);
        click(subMenuLD);
        waitForOverlayToDisappear();
    }

    @Step("Pilih Gudang dengan ID {0}")
    public void selectWarehouseById(String warehouseId) {
        type(inputKeyword, "nila");
        click(btnSearch);
        waitForOverlayToDisappear();
        // Menggunakan link dinamis sesuai file asli
        driver.findElement(By.xpath("//a[contains(@href, 'idwarehouse=" + warehouseId + "')]")).click();
        waitForOverlayToDisappear();
    }

    @Step("Isi Form Supplier & Tipe: {0}")
    public void fillSupplierAndStockType(String type) {
        type(txtSupplierNama, "test automation supplier");
        type(txtSupplierNomor, "123");
        if (type.equalsIgnoreCase("Consignment")) {
            click(rdoConsign);
        } else {
            click(rdoNonConsign);
        }
        click(btnNextStep);
        waitForOverlayToDisappear();
    }

    @Step("Hitung Median Karat dari Size Control")
    public void calculateAndInputMedianCarat() {
        wait.until(ExpectedConditions.visibilityOf(lblSizeControl));
        String rangeText = lblSizeControl.getText();

        // Logika matematika dari file asli
        String[] parts = rangeText.replace(",", ".").split(" - ");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);
        double median = (min + max) / 2;

        String medianString = String.format("%.3f", median).replace('.', ',');
        type(inputCarat, medianString);
        click(btnSave);
        waitForOverlayToDisappear();
    }

    @Step("Selesaikan Wizard Registrasi LD")
    public void finishWizard() {
        // Halaman 8 & 9
        click(btnSave);
        waitForOverlayToDisappear();
        click(btnSave);
        waitForOverlayToDisappear();

        // Halaman 10: Tombol Finish (Hijau)
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='content_save' and contains(@class, 'btn-success')]")
        ));
        finishButton.click();
        waitForOverlayToDisappear();
    }
}