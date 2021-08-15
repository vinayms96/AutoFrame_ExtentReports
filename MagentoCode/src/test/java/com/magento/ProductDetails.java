package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.HeaderModel;
import com.magento.pageModels.ListingModel;
import com.magento.pageModels.ProductModel;
import com.magento.pageModels.SearchListingModel;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductDetails extends TestNGBase {
    public WebDriver driver;
    WebDriverWait wait;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 30);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Verify Product Details");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Verifying the Product details with the listing page details
     */
    @Test(description = "Verify the Product Details in PDP", priority = 1, groups = {"productDetails.verifyDetails"})
    public void verifyProductDetails() {
        // Setting up the Extent Report
        ExtentReport.createNode("Verify Product Details");

        HeaderModel headerModel = new HeaderModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);
        ListingModel listingModel = new ListingModel(driver);
        ProductModel productModel = new ProductModel(driver);

        // Searching for the products
        headerModel.getSearchBox().sendKeys(ExcelUtils.getDataMap().get("search_text"), Keys.RETURN);
        Assert.assertTrue(searchListingModel.getSearchTitle().getText()
                .contains(ExcelUtils.getDataMap().get("search_text")));

        listingModel.fetchProductDetails();

        // Clicking on the Product name
        listingModel.getProductItemLink().get(listingModel.getProductId()).click();
        Loggers.getLogger().info("Clicked on '" + listingModel.getListProductName() + "' product");
        ExtentReport.getExtentNode().pass("Clicked on '" + listingModel.getListProductName() + "' product");

        // Verifying Product name
        Assert.assertEquals(listingModel.getListProductName(), productModel.getPdpProductName().getText());
        Loggers.getLogger().info("Product name verified successfully");
        ExtentReport.getExtentNode().pass("Product name verified successfully");

        // Fetching the Final price
        productModel.setProductFinalPrice(productModel);

        // Verifying Product prices
        try {
            if (!listingModel.getListProductOldPrice().equals("") || listingModel.getListProductOldPrice() != null) {
                // Fetching the Old price
                productModel.setProductOldPrice(productModel.getPdpOldPrice().getText());

                Assert.assertEquals(listingModel.getListProductOldPrice(), productModel.getProductOldPrice());
                Assert.assertEquals(listingModel.getListProductFinalPrice(), productModel.getProductFinalPrice());
                Loggers.getLogger().info("Product old price and final price verified successfully");
                ExtentReport.getExtentNode().pass("Product old price and final price verified successfully");
            }
        } catch (Exception e) {
            Assert.assertEquals(listingModel.getListProductFinalPrice(), productModel.getProductFinalPrice());
            Loggers.getLogger().info("Product final price verified successfully");
            ExtentReport.getExtentNode().pass("Product final price verified successfully");
        }
    }


}
