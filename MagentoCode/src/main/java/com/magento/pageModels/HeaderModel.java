package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderModel {
    WebDriver driver;

    @FindBy(css = "#search")
    private WebElement searchBox;
    @FindBy(css = ".showcart")
    private WebElement cartIcon;
    @FindBy(css = ".header .links .customer-name button")
    private WebElement accountDropdown;
    @FindBy(css = ".header .links li .authorization-link")
    private WebElement logoutLink;

    /**
     * @param driver - Constructor
     */
    public HeaderModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * @return WebElement - Searchbox
     */
    public WebElement getSearchBox() {
        return searchBox;
    }

    /**
     * @return WebElement - Cart Icon
     */
    public WebElement getCartIcon() {
        return cartIcon;
    }

    /**
     * @return WebElement - Account Dropdown
     */
    public WebElement getAccountDropdown() {
        return accountDropdown;
    }

    /**
     * @return WebElement - Logout Link
     */
    public WebElement getLogoutLink() {
        return logoutLink;
    }
}
