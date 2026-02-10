package dev1.pages.OutletInventory;

import dev1.pages.Base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VerifyStockPage extends BasePage {

    // === LOCATORS: NAVIGASI ===
    @FindBy(xpath = "//a[contains(@href, '/OutletInv/')]") private WebElement menuInventory;
    @FindBy(xpath = "//a[contains(@class, 'dropdown-toggle') and contains(text(), 'Report')]") private WebElement menuReport;
    @FindBy(xpath = "//ul[contains(@class, 'dropdown-menu')]//a[contains(., 'Stock')]") private WebElement subMenuStock;
    @FindBy(linkText = "Diamond Jewellery List") private WebElement subMenuDJList;
    @FindBy(linkText = "Stock Ledger") private WebElement subMenuStockLedger;

    // === LOCATORS: SEARCH & TABLE ===
    @FindBy(id = "content_kw") private WebElement inputSearch;
    @FindBy(id = "content_go") private WebElement btnGo;
    @FindBy(xpath = "//table[@id='content_tb']/tbody/tr[2]") private WebElement firstLedgerRow;

    public VerifyStockPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigasi ke Diamond Jewellery List")
    public void navigateToDJList() {
        click(menuInventory);
        click(menuReport);
        // Hover ke Stock karena ini menu bertingkat
        new Actions(driver).moveToElement(subMenuStock).perform();
        click(subMenuDJList);
        waitForOverlayToDisappear();
    }

    @Step("Navigasi ke Stock Ledger")
    public void navigateToStockLedger() {
        click(menuReport);
        click(subMenuStockLedger);
        waitForOverlayToDisappear();
    }

    @Step("Cari PLU: {0}")
    public void searchPlu(String plu) {
        type(inputSearch, plu);
        click(btnGo);
        waitForOverlayToDisappear();
    }

    public String getLedgerValue() {
        return firstLedgerRow.findElement(By.xpath("./td[6]")).getText();
    }

    public String getLedgerRemarks() {
        return firstLedgerRow.findElement(By.xpath("./td[7]")).getText();
    }
}