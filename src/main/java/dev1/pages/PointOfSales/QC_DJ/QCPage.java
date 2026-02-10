package dev1.pages.PointOfSales.QC_DJ;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QCPage extends BasePage {

    // === LOCATORS: NAVIGASI ===
    @FindBy(linkText = "Point of Sales") private WebElement menuPOS;
    @FindBy(linkText = "Register") private WebElement menuRegister;
    @FindBy(linkText = "QC DJ") private WebElement subMenuQC;

    // === LOCATORS: QC LIST PAGE ===
    @FindBy(id = "content_kw") private WebElement inputSearchPlu;
    @FindBy(id = "content_go") private WebElement btnDisplay;
    @FindBy(xpath = "//a[contains(@href, 'DJ_Submit.aspx')]") private WebElement btnOpenDetail;

    // === LOCATORS: QC SUBMIT PAGE ===
    @FindBy(id = "content_save") private WebElement btnSubmitQC;

    public QCPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Menu QC DJ")
    public void navigateToQC() {
        click(menuPOS);
        click(menuRegister);
        click(subMenuQC);
        waitForOverlayToDisappear();
    }

    @Step("Cari PLU di QC List: {0}")
    public void searchAndOpenPlu(String plu) {
        type(inputSearchPlu, plu);
        click(btnDisplay);
        waitForOverlayToDisappear();
        click(btnOpenDetail);
    }

    @Step("Klik Submit QC")
    public void submitQC() {
        // Scroll ke tombol save agar aman dari elemen yang menutupi
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSubmitQC);
        click(btnSubmitQC);
        waitForOverlayToDisappear();
    }
}