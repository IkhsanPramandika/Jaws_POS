package dev1.pages.PointOfSales.TradeIn;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class TradeInPage extends BasePage {

    // === LOCATORS: QC DJ ===
    @FindBy(linkText = "QC DJ") private WebElement menuQC;
    @FindBy(id = "content_kw") private WebElement inputSearchQC;
    @FindBy(id = "content_go") private WebElement btnDisplayQC;
    @FindBy(id = "content_save") private WebElement btnSubmitQC;

    // === LOCATORS: TRADE IN ===
    @FindBy(linkText = "Trade In") private WebElement menuTradeIn;
    @FindBy(id = "content_search_customer") private WebElement btnSearchCust;
    @FindBy(id = "content_txt_plu") private WebElement inputPluBaru;
    @FindBy(id = "content_addrs") private WebElement btnAddBarangLama;
    @FindBy(id = "content_review") private WebElement btnReview;

    public TradeInPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Menu QC DJ")
    public void navigateToQC() {
        click(menuQC);
        waitForOverlayToDisappear();
    }

    @Step("Navigasi ke Menu Trade In")
    public void navigateToTradeIn() {
        click(menuTradeIn);
        waitForOverlayToDisappear();
    }
}
