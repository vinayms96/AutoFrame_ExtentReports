package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SuccessModel {

    @FindBy(css = "h1 span")
    private WebElement successTitle;
    @FindBy(css = ".checkout-success a")
    private WebElement orderLink;
    @FindBy(css = ".checkout-success a strong")
    private WebElement orderId;

    /**
     * @param driver - Constructor
     */
    public SuccessModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * @return WebElement
     */
    public WebElement getSuccessTitle() {
        return successTitle;
    }

    /**
     * @return WebElement
     */
    public WebElement getOrderLink() {
        return orderLink;
    }

    /**
     * @return WebElement
     */
    public WebElement getOrderId() {
        return orderId;
    }
}
