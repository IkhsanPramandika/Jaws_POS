package dev1.pages.Repair;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RepairPage extends BasePage {

    // === LOCATORS: NAVIGASI UMUM ===
    @FindBy(linkText = "Repair") private WebElement menuRepair;
    @FindBy(xpath = "//a[@data-toggle='dropdown' and contains(text(),'Register')]") private WebElement dropdownRegister;
    @FindBy(xpath = "//a[@data-toggle='dropdown' and contains(text(), 'Perajin')]") private WebElement menuPerajin;

    // === LOCATORS: STEP 1 (REGISTER) ===
    @FindBy(id = "menu_RG1") private WebElement subMenuFormReparasi;
    @FindBy(id = "btn-modal-search-customer") private WebElement btnSearchCust;
    @FindBy(id = "input-keyword-customer") private WebElement inputKeywordCust;
    @FindBy(id = "btn-search-customer") private WebElement btnGoCust;
    @FindBy(xpath = "//button[@data-id-customer]") private WebElement btnPickCust;
    @FindBy(id = "form-textarea-keterangan-reparasi") private WebElement txtKeteranganAwal;
    @FindBy(id = "input-file-foto-product") private WebElement uploadFoto;
    @FindBy(id = "btn-modal-confirmation") private WebElement btnSimpan;
    @FindBy(id = "btn-confirmation-submit") private WebElement btnYakinSimpan;

    // === LOCATORS: STEP 2 (SERVICE POINT) ===
    @FindBy(id = "menu_PN2") private WebElement subMenuServicePoint;
    @FindBy(id = "input-plu-reparasi") private WebElement inputSearchPlu;
    @FindBy(id = "btn-search-do") private WebElement btnCariPlu;
    @FindBy(xpath = "//button[@data-status='2']") private WebElement btnStatusTerima;
    @FindBy(xpath = "//button[@data-status='3']") private WebElement btnStatusProses;
    @FindBy(xpath = "//button[@data-status='4']") private WebElement btnStatusSelesai;
    @FindBy(id = "btn-update-status") private WebElement btnKonfirmasiStatus;

    // === LOCATORS: STEP 3 (FINISH) ===
    @FindBy(id = "menu_RG2") private WebElement subMenuReparasiServicePoint;
    @FindBy(xpath = "//a[contains(@class, 'btn-warning') and contains(text(), 'Finish')]") private WebElement btnFinishWarning;
    @FindBy(id = "KeteranganRepair") private WebElement inputKetFinish;

    // === LOCATORS: STEP 4 & 5 (PAYMENT & RETURN) ===
    @FindBy(id = "menu_RG6") private WebElement subMenuPembayaranReparasi;
    @FindBy(id = "menu_RG7") private WebElement subMenuPengembalianCustomer;
    @FindBy(id = "input-search-product") private WebElement inputSearchPay;
    @FindBy(xpath = "//a[contains(text(), 'Bayar')]") private WebElement btnBayar;

    public RepairPage(WebDriver driver) {
        super(driver);
    }

    // --- ACTIONS STEP 1: REGISTER ---
    @Step("Navigasi ke Form Registrasi")
    public void navigateToRegister() {
        click(menuRepair);
        click(dropdownRegister);
        click(subMenuFormReparasi);
    }

    @Step("Cari Customer dengan HP: {0}")
    public void searchAndPickCustomer(String phone) {
        click(btnSearchCust);
        type(inputKeywordCust, phone);
        click(btnGoCust);
        click(btnPickCust);
        waitForOverlayToDisappear();
    }

    @Step("Scroll dan Pilih Item Valid dari Tabel")
    public String findAndPickItem() {
        List<WebElement> rows = driver.findElements(By.xpath("//tbody[@id='tbody-customer-item-table']/tr"));
        // Ambil baris tengah agar aman saat scroll
        WebElement row = rows.get(rows.size() / 2);
        String plu = row.findElement(By.xpath("./td[2]")).getText().trim();
        WebElement btnPilih = row.findElement(By.xpath(".//button[contains(text(), 'Pilih')]"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnPilih);
        btnPilih.click();
        waitForOverlayToDisappear();
        return plu;
    }

    @Step("Submit Form Registrasi")
    public void submitRegistration(String keterangan, String photoPath) {
        uploadFoto.sendKeys(photoPath);
        type(txtKeteranganAwal, keterangan);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSimpan);
        click(btnSimpan);
        click(btnYakinSimpan);
        waitForOverlayToDisappear();
    }

    // --- ACTIONS STEP 2: SERVICE POINT ---
    @Step("Proses Status Berantai di Service Point")
    public void processServicePoint(String plu) {
        click(menuRepair);
        click(menuPerajin);
        click(subMenuServicePoint);

        type(inputSearchPlu, plu);
        click(btnCariPlu);
        waitForOverlayToDisappear();

        // Siklus: Terima -> Proses -> Selesai
        WebElement[] steps = {btnStatusTerima, btnStatusProses, btnStatusSelesai};
        for (WebElement step : steps) {
            click(step);
            click(btnKonfirmasiStatus);
            waitForOverlayToDisappear();
        }
    }

    // --- ACTIONS STEP 3: FINISH ---
    @Step("Navigasi ke Finish Reparasi")
    public void goToFinishForm(String plu) {
        click(menuRepair);
        click(dropdownRegister);
        click(subMenuReparasiServicePoint);

        type(inputSearchPlu, plu);
        click(btnCariPlu);
        waitForOverlayToDisappear();
        click(btnFinishWarning);
    }

    @Step("Isi Form Finish di Tab Baru")
    public void fillFinishDetail(String ket) {
        type(inputKetFinish, ket);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSimpan);
        click(btnSimpan);
        click(btnYakinSimpan);
        waitForOverlayToDisappear();
    }

    // --- ACTIONS STEP 4: PAYMENT ---
    @Step("Proses Pembayaran")
    public void initiatePayment(String plu) {
        click(menuRepair);
        click(dropdownRegister);
        click(subMenuPembayaranReparasi);

        type(inputSearchPay, plu);
        click(btnCariPlu);
        waitForOverlayToDisappear();
        click(btnBayar);
    }

    // --- ACTIONS STEP 5: RETURN ---
    @Step("Proses Pengembalian ke Customer")
    public void processReturn(String plu) {
        click(menuRepair);
        click(dropdownRegister);
        click(subMenuPengembalianCustomer);

        type(inputSearchPay, plu);
        click(btnCariPlu);
        waitForOverlayToDisappear();

        click(btnSimpan); // Tombol "Proses" di halaman return
        click(btnYakinSimpan);
    }
}