package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pickers.RandomPicker;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ListingModel {
    private WebDriver driver;
    private String list_product_name;
    private String list_product_final_price;
    private String list_product_old_price;
    private int product_id;

    @FindBy(css = ".product-item")
    private List<WebElement> productList;
    @FindBy(css = ".product-item-link")
    private List<WebElement> productItemLink;
    @FindBy(xpath = "//span[@data-price-type='finalPrice']")
    private List<WebElement> finalPriceList;
    @FindBy(xpath = "//span[@data-price-type='oldPrice']")
    private List<WebElement> oldPriceList;
    @FindBy(xpath = "//button[@type='submit']")
    private List<WebElement> submitButton;
    @FindBy(xpath = "//div[@data-ui-id='message-success']/div/a")
    private WebElement addSuccess;

    /**
     * @param driver - Constructor
     */
    public ListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Fetch the Product Name, Old Price(If present) and Final Price
     */
    public void fetchProductDetails() {
        // Setting ExtentReports
        ExtentReport.createNode("Fetch product details in Listing page");

        // Pick random product number
        product_id = RandomPicker.numberPicker(productList.size());

        // Fetching the Product name
        list_product_name = productItemLink.get(product_id).getText();
        Loggers.getLogger().info("Product '" + list_product_name + "' is picked");
        ExtentReport.getExtentNode().pass("Product '" + list_product_name + "' is picked");

        // Fetching Old Price if present
        try {
            if (oldPriceList.get(product_id).isDisplayed()) {
                // Fetching the Old Price in listing page
                list_product_old_price = oldPriceList.get(product_id).findElement(By.className("price")).getText();
                Loggers.getLogger().info("Product Old Price: " + list_product_old_price);
                ExtentReport.getExtentNode().pass("Product Old Price: " + list_product_old_price);

                // Fetching the Final Price in listing page
                list_product_final_price = finalPriceList.get(product_id).findElement(By.className("price")).getText();
                Loggers.getLogger().info("Product Final Price: " + list_product_final_price);
                ExtentReport.getExtentNode().pass("Product Final Price: " + list_product_final_price);
            }
        } catch (Exception e) {
            // Fetching Final Price if Old Price not present
            try {
                if (finalPriceList.get(product_id).isDisplayed()) {
                    list_product_final_price = finalPriceList.get(product_id).findElement(By.className("price")).getText();
                    Loggers.getLogger().info("Product Final Price: " + list_product_final_price);
                    ExtentReport.getExtentNode().pass("Product Final Price: " + list_product_final_price);
                }
            } catch (Exception er) {
                Loggers.getLogger().error("Could not fetch the Product Price in listing page");
                ExtentReport.getExtentNode().pass("Could not fetch the Product Price in listing page");
            }
        }
    }

    /**
     * Select the product from the Listing
     * @param searchListingModel - SearchListingModel
     * @param productModel - ProductModel
     */
    public void selectProduct(SearchListingModel searchListingModel, ProductModel productModel) {
        String productName = ExcelUtils.getDataMap().get("product_name");
        Actions act = new Actions(driver);
        List<WebElement> prodList = searchListingModel.getProductName();

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
     * @return String
     */
    public String getListProductName() {
        return list_product_name;
    }

    /**
     * @return String
     */
    public String getListProductFinalPrice() {
        return list_product_final_price;
    }

    /**
     * @return String
     */
    public String getListProductOldPrice() {
        return list_product_old_price;
    }

    /**
     * @return Integer
     */
    public int getProductId() {
        return product_id;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductList() {
        return productList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getProductItemLink() {
        return productItemLink;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getFinalPriceList() {
        return finalPriceList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getOldPriceList() {
        return oldPriceList;
    }

    /**
     * @return List<WebElement>
     */
    public List<WebElement> getSubmitButton() {
        return submitButton;
    }

    /**
     * @return List<WebElement>
     */
    public WebElement getAddSuccess() {
        return addSuccess;
    }
}
