package dev1.pages.PointOfSales.DP;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class DPPage extends BasePage {

    // === LOCATORS: NAVIGASI ===
    @FindBy(linkText = "Point of Sales") private WebElement menuPOS;
    @FindBy(linkText = "Register") private WebElement menuRegister;
    @FindBy(linkText = "Down Payment") private WebElement subMenuDP;

    // === LOCATORS: FORM DP ===
    @FindBy(id = "content_search_customer") private WebElement btnSearchCustomer;
    @FindBy(id = "content_txt_amount") private WebElement inputAmount;
    @FindBy(id = "content_txt_remark") private WebElement inputRemark;
    @FindBy(id = "content_save") private WebElement btnNext;

    public DPPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Menu Down Payment")
    public void navigateToDP() {
        click(menuPOS);
        click(menuRegister);
        click(subMenuDP);
        waitForOverlayToDisappear();
    }

    @Step("Isi Nominal DP: {0}")
    public void fillDPDetails(String amount, String remark) {
        type(inputAmount, amount);
        type(inputRemark, remark);
        click(btnNext);
        waitForOverlayToDisappear();
    }
}