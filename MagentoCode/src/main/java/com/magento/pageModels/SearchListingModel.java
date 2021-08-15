package com.magento.pageModels;

import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchListingModel {
    private WebDriver driver;

    @FindBy(css = "h1 > span")
    private WebElement searchTitle;
    @FindBy(css = ".product-items > li")
    private List<WebElement> productsList;
    @FindBy(css = "div > div > strong > a")
    private List<WebElement> productName;

    /**
     * @param driver - Constructor
     */
    public SearchListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Select the product from the Listing page
     * @param productModel - ProductModel
     */
    public void selectProduct(ProductModel productModel) {
        String productName = ExcelUtils.getDataMap().get("product_name");
        Actions act = new Actions(driver);
        List<WebElement> prodList = getProductName();

        // Selecting the product from Listing page
        for (WebElement element : prodList) {
            if (element.getText().equals(productName)) {
                act.moveToElement(element).click().build().perform();
                break;
            }
        }

        // Click on Add to Cart
        productModel.getAddToCartButton().click();
    }

    /**
     * @return WebElement
     */
    public WebElement getSearchTitle() {
        return searchTitle;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductsList() {
        return productsList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductName() {
        return productName;
    }
}
