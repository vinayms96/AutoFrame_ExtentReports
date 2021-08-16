package com.magento.pageModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    private String productOldPrice;
    private String productFinalPrice;
    private String productQuantity;
    private ArrayList<String> productSwatches;
    private String productName;

    @FindBy(xpath = "//h1/span")
    private WebElement pdpProductName;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']/span")
    private WebElement pdpOldPrice;
    @FindBy(xpath = "(//span[@data-price-type='finalPrice'])[1]/span")
    private WebElement pdpFinalPrice;
    @FindBy(id = "product-options-wrapper")
    private WebElement productOptions;
    @FindBy(xpath = "//div[@class='swatch-attribute size']/div/div")
    private List<WebElement> swatchesSizeList;
    @FindBy(xpath = "//div[@class='swatch-attribute color']/div/div")
    private List<WebElement> swatchesColorList;
    @FindBy(id = "qty")
    private WebElement quantity;
    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartButton;
    @FindBy(xpath = "//div[@data-ui-id='message-error']/div")
    private WebElement errorMessage;
    @FindBy(xpath = "//div[@data-ui-id='message-success']/div")
    private WebElement successMessage;

    /**
     * @param driver - Constructor
     */
    public ProductModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * @param productOldPrice - String
     */
    public void setProductOldPrice(String productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    /**
     * @param productModel - ProductModel
     */
    public void setProductFinalPrice(ProductModel productModel) {
        productFinalPrice = productModel.getPdpFinalPrice().getText();
        this.productFinalPrice = productFinalPrice;
    }

    /**
     * @param productQuantity - String
     */
    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * Create an object of Product Swatches
     */
    public void setProductSwatches() {
        this.productSwatches = new ArrayList<>();
    }

    /**
     * @return String
     */
    public String getProductOldPrice() {
        return productOldPrice;
    }

    /**
     * @return String
     */
    public String getProductFinalPrice() {
        return productFinalPrice;
    }

    /**
     * @return String
     */
    public String getProductQuantity() {
        return productQuantity;
    }

    /**
     * @return ArrayList<String>
     */
    public ArrayList<String> getProductSwatches() {
        return productSwatches;
    }

    /**
     * @return String
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @return WebElement
     */
    public WebElement getPdpProductName() {
        return pdpProductName;
    }

    /**
     * @return WebElement
     */
    public WebElement getPdpOldPrice() {
        return pdpOldPrice;
    }

    /**
     * @return WebElement
     */
    public WebElement getPdpFinalPrice() {
        return pdpFinalPrice;
    }

    /**
     * @return WebElement
     */
    public WebElement getProductOptions() {
        return productOptions;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSwatchesSizeList() {
        return swatchesSizeList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSwatchesColorList() {
        return swatchesColorList;
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
    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    /**
     * @return WebElement
     */
    public WebElement getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return WebElement
     */
    public WebElement getSuccessMessage() {
        return successMessage;
    }
}
