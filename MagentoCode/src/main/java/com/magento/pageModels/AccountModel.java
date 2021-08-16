package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountModel {

    @FindBy(css = ".box-information .box-content p")
    private WebElement userName;

    /**
     * @param driver - Constructor
     */
    public AccountModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * @return WebElement
     */
    public WebElement getUserName() {
        return userName;
    }
}
