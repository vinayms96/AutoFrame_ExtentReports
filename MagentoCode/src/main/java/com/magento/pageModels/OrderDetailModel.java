package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderDetailModel {

    @FindBy(css = "h1 span")
    private WebElement orderTitle;

    /**
     * @param driver - Constructor
     */
    public OrderDetailModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * @return WebElement
     */
    public WebElement getOrderTitle() {
        return orderTitle;
    }
}
