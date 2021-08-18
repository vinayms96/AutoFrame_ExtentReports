package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.pageModels.*;
import com.magento.pickers.RandomPicker;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddToCart extends TestNGBase implements Constants {
    public WebDriver driver;
    WebDriverWait wait;
    private String productOldPrice;
    private String productFinalPrice;
    private String productQuantity;
    private String productName;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 30);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Add To Cart & Verify");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Adding the Product to cart
     */
    @Test(description = "Add a simple product to cart", priority = 1, groups = {"addToCart.addProducts"})
    public void addToCart() {
        // Setting up the Extent Report
        ExtentReport.createNode("Add to Cart");

        HeaderModel headerModel = new HeaderModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        CartModel cartModel = new CartModel(driver);

        // Searching for the products
        headerModel.getSearchBox().sendKeys(ExcelUtils.getDataMap().get("search_text"), Keys.RETURN);
        Assert.assertTrue(searchListingModel.getSearchTitle().getText()
                .contains(ExcelUtils.getDataMap().get("search_text")));
        // Select the product passed
        searchListingModel.selectProduct(productModel);

        productName = productModel.getPdpProductName().getText();
        productFinalPrice = productModel.getPdpFinalPrice().getText();
        productQuantity = productModel.getQuantity().getAttribute("value");
        try {
            if (!listingModel.getListProductOldPrice().equals("") || listingModel.getListProductOldPrice() != null) {
                // Fetching the Old price
                productOldPrice = productModel.getPdpOldPrice().getText();
            }
        } catch (Exception e) {
            Loggers.getLogger().info("No Special Price");
        }

        try {
            if (productModel.getProductOptions().isDisplayed()) {
                // Logging and Reporting
                Loggers.getLogger().info("Selected a Configurable Product");
                ExtentReport.getExtentNode().info("Selected a Configurable Product");

                productModel.setProductSwatches();

                if (productModel.getSwatchesSizeList().get(0).isDisplayed()) {
                    int size_option = RandomPicker.numberPicker(productModel.getSwatchesSizeList().size());
                    WebElement size_element = productModel.getSwatchesSizeList().get(size_option);

                    // Selecting the Swatches
                    MouseActions.moveClickEvent(driver, size_element);
                    productModel.getProductSwatches().add(size_element.getAttribute("option-label"));

                    // Logging and Reporting
                    Loggers.getLogger().info("Swatch '" + productModel.getProductSwatches().get(0) + "' is selected");
                    ExtentReport.getExtentNode().info("Swatch '" + productModel.getProductSwatches().get(0) + "' is selected");
                }

                if (productModel.getSwatchesColorList().get(0).isDisplayed()) {
                    int color_option = RandomPicker.numberPicker(productModel.getSwatchesColorList().size());
                    WebElement color_element = productModel.getSwatchesColorList().get(color_option);

                    // Selecting the Swatches
                    MouseActions.moveClickEvent(driver, color_element);
                    productModel.getProductSwatches().add(color_element.getAttribute("option-label"));

                    // Logging and Reporting
                    Loggers.getLogger().info("Swatch '" + productModel.getProductSwatches().get(1) + "' is selected");
                    ExtentReport.getExtentNode().info("Swatch '" + productModel.getProductSwatches().get(1) + "' is selected");
                }
            }
        } catch (Exception e) {
            Loggers.getLogger().info("Selected a Simple Product");
            ExtentReport.getExtentNode().info("Selected a Simple Product");
        }

        // Selecting the qty to add
        productModel.setProductQuantity(ExcelUtils.getDataMap().get("qty"));
        productModel.getQuantity().sendKeys(Keys.DELETE);
        productModel.getQuantity().sendKeys(productModel.getProductQuantity());

        // Verify the success message
        try {
            wait.until(ExpectedConditions.visibilityOf(productModel.getSuccessMessage()));
            Assert.assertTrue(productModel.getSuccessMessage().getText().contains("shopping cart"));
            Loggers.getLogger().info("Success message is displayed");
            ExtentReport.getExtentNode().pass("Success message is displayed");
        } catch (Exception e) {
            Loggers.getLogger().error("Success message is NOT displayed");
            ExtentReport.getExtentNode().error("Success message is NOT displayed");
        }
    }

    /**
     * Verify the Products added to Cart
     */
    @Test(description = "Verify the Products added to Cart", priority = 2, groups = {"addToCart.verifyProducts"})
    public void verifyProductsInCart() {
        // Setting up the Extent Report
        ExtentReport.createNode("Verify Products");

        HeaderModel headerModel = new HeaderModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CartModel cartModel = new CartModel(driver);

        // Navigate to the Cart page
        wait.until(ExpectedConditions.visibilityOf(productModel.getSuccessMessage()));
        headerModel.getCartIcon().click();
        minicartModel.getViewCart().click();
        cartModel.fetchProductDetails();

        try {
            // Verifying product details
            Assert.assertEquals(productName, cartModel.getCartProductName());
            Assert.assertEquals(productFinalPrice, cartModel.getCartFinalPrice());
            Assert.assertEquals(productQuantity, cartModel.getCartProductQty());
//        for (int swatch = 0; swatch < cart_swatch.size(); swatch++) {
//            Assert.assertEquals(cart_swatch.get(swatch), ProductModel.getProduct_swatches().get(swatch));
//        }

            // Logging and Extent Reports
            Loggers.getLogger().info("'" + productName + "' successfully added to cart");
            ExtentReport.getExtentNode().pass("'" + productName + "' successfully added to cart");
        } catch (AssertionError e) {
            Loggers.getLogger().error("'" + productName + "' could NOT be added to cart");
            ExtentReport.getExtentNode().fail("'" + productName + "' could NOT be added to cart");
        }
    }

}
