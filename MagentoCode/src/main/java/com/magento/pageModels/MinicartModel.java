package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MinicartModel {

    @FindBy(css = ".action.viewcart")
    private WebElement viewCart;
    @FindBy(xpath = "//span[@class='counter qty']")
    private WebElement miniCartPop;
    @FindBy(css = "#mini-cart .product-item")
    private List<WebElement> miniProductsList;
    @FindBy(xpath = "//strong[@class='product-item-name']/a")
    private WebElement productName;
    @FindBy(css = ".product.options .toggle")
    private WebElement seeDetails;
    @FindBy(xpath = "//dd[@class='values']/span")
    private List<WebElement> swatchOptions;
    @FindBy(css = ".price-wrapper .minicart-price .price")
    private WebElement productPrice;
    @FindBy(css = ".cart-item-qty")
    private WebElement quantity;
    @FindBy(id = "top-cart-btn-checkout")
    private WebElement checkoutButton;

    /**
     * @param driver - Constructor
     */
    public MinicartModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * @return WebElement
     */
    public WebElement getViewCart() {
        return viewCart;
    }

    /**
     * @return WebElement
     */
    public WebElement getMiniCartPop() {
        return miniCartPop;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getMiniProductsList() {
        return miniProductsList;
    }

    /**
     * @return WebElement
     */
    public WebElement getProductName() {
        return productName;
    }

    /**
     * @return WebElement
     */
    public WebElement getSeeDetails() {
        return seeDetails;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSwatchOptions() {
        return swatchOptions;
    }

    /**
     * @return WebElement
     */
    public WebElement getProductPrice() {
        return productPrice;
    }

    /**
     * @return WebElement
     */
    public WebElement getQuantity() {
        return quantity;
    }

    /**
     * @return WebElement
     */
    public WebElement getCheckoutButton() {
        return checkoutButton;
    }
}
