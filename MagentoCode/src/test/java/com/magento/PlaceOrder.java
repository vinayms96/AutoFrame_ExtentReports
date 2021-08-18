package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.*;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;

public class PlaceOrder extends TestNGBase {
    public WebDriver driver;
    public WebDriverWait wait;
    JavascriptExecutor executor;
    private Select select;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 10);
        executor = (JavascriptExecutor) driver;

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Place Order");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Place Order from Minicart
     */
    @Test(description = "Place Order from Minicart", priority = 1, groups = {"placeOrder.minicart"})
    public void placeOrderMinicart() {
        // Setting up the Extent Report
        ExtentReport.createNode("Place Order from Minicart");

        HeaderModel headerModel = new HeaderModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);
        ProductModel productModel = new ProductModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);
        CheckoutModel checkoutModel = new CheckoutModel(driver);
        SuccessModel successModel = new SuccessModel(driver);
        OrderDetailModel orderDetailModel = new OrderDetailModel(driver);

        // Searching for the products
        headerModel.getSearchBox().sendKeys(ExcelUtils.getDataMap().get("search_text"), Keys.RETURN);
        Assert.assertTrue(searchListingModel.getSearchTitle().getText()
                .contains(ExcelUtils.getDataMap().get("search_text")));
        // Select the product passed
        searchListingModel.selectProduct(productModel);

        // Navigate to the Checkout page
        wait.until(ExpectedConditions.visibilityOf(productModel.getSuccessMessage()));
        headerModel.getCartIcon().click();
        minicartModel.getCheckoutButton().click();

        // Verify if Navigated to checkout shipping page
        try {
            wait.until(ExpectedConditions.elementToBeClickable(checkoutModel.getSignInLink()));
            Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#shipping"));
            Loggers.getLogger().info("Redirected to Checkout Shipping Page");
            ExtentReport.getExtentNode().info("Redirected to Checkout Shipping Page");
        } catch (AssertionError e) {
            Loggers.getLogger().error("Could NOT be redirected to Checkout Shipping Page");
            ExtentReport.getExtentNode().error("Could NOT be redirected to Checkout Shipping Page");
        }

        // Login to account from Checkout
        checkoutModel.checkoutLogin();

        // Verify Login and Add Address
        try {
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(checkoutModel.getSelectedShip())));
            Assert.assertFalse(checkoutModel.getSignInLink().isDisplayed());

            Loggers.getLogger().info("Logged into the User Account successfully");
            ExtentReport.getExtentNode().pass("Logged into the User Account successfully");
        } catch (AssertionError e) {
            checkoutModel.addShippingAddress();

            Loggers.getLogger().error("Shipping Address entered");
            ExtentReport.getExtentNode().fail("Shipping Address entered");
        }

        // Select Shipping Method
        if(checkoutModel.getSelectShipping().getAttribute("checked").equals("false"))
            checkoutModel.getSelectShipping().click();

        // Navigate to Checkout payment page
        checkoutModel.getNextSubmit().click();

        wait.until(ExpectedConditions.elementToBeClickable(checkoutModel.getPlaceOrder()));
        try {
            Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#payment"));
        } catch (Exception e) {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.elementToBeClickable(checkoutModel.getPlaceOrder()));
            Assert.assertTrue(driver.getCurrentUrl().contains("/checkout/#payment"));
        }

        // Place Order
        checkoutModel.getPlaceOrder().click();
        wait.until(ExpectedConditions.elementToBeClickable(successModel.getOrderLink()));

        // Verify Order Placed Successfully or not
        try {
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout/onepage/success/"));
            Assert.assertEquals(successModel.getSuccessTitle().getText(), "Thank you for your purchase!");

            Loggers.getLogger().info("Order Placed Successfully");
            ExtentReport.getExtentNode().pass("Order Placed Successfully");
        } catch (Exception e) {
            Loggers.getLogger().error("Order could NOT be placed");
            ExtentReport.getExtentNode().error("Order could NOT be placed");
        }

        String orderLink = successModel.getOrderLink().getAttribute("href");
        String orderId = successModel.getOrderId().getText();

        // Verify the Order Link and the Order Id
        try {
            successModel.getOrderLink().click();
            Assert.assertEquals(driver.getCurrentUrl(), orderLink);
            Assert.assertTrue(orderDetailModel.getOrderTitle().getText().contains(orderId));

            Loggers.getLogger().info("Order Id and Link verified");
            ExtentReport.getExtentNode().pass("Order Id and Link verified");
        } catch (Exception e) {
            Loggers.getLogger().error("Order Id and Link are NOT correct");
            ExtentReport.getExtentNode().fail("Order Id and Link are NOT correct");
        }
    }

}
