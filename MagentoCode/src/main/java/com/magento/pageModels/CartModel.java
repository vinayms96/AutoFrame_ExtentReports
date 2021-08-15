package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartModel {
    private WebDriver driver;
    private String cartProductName;
    private ArrayList<String> cartSwatch;
    private String cartFinalPrice;
    private String cartSubtotalPrice;
    private String cartProductQty;

    @FindBy(css = ".cart.item .item-info")
    private List<WebElement> productList;
    @FindBy(xpath = "//td[@class='col item']")
    private List<WebElement> swatchOptions;
    @FindBy(css = ".price .cart-price .price")
    private List<WebElement> productFinalPrice;
    @FindBy(css = ".subtotal .cart-price .price")
    private List<WebElement> productSubtotalPrice;
    @FindBy(css = ".control.qty .qty")
    private List<WebElement> productQty;
    @FindBy(css = "h1 span")
    private WebElement cartTitle;
    @FindBy(css = "#shopping-cart-table tbody tr[class='item-info'] td div strong a")
    private List<WebElement> productsAdded;
    @FindBy(css = ".item .checkout")
    private WebElement checkoutButton;
    @FindBy(css = ".grand.totals")
    private WebElement grandTotalLabel;

    public CartModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public WebElement getCartTitle() {
        return cartTitle;
    }

    public List<WebElement> getProductsAdded() {
        return productsAdded;
    }

    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    public WebElement getGrandTotalLabel() {
        return grandTotalLabel;
    }

    /**
     * Fetching the Cart product details
     */
    public void fetchProductDetails() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOfAllElements(productList));
        cartSwatch = new ArrayList<>(10);

        for (WebElement element : productList) {
            // Fetching the Product name
            for (WebElement productObject : productsAdded) {
                Loggers.getLogger().info(productObject.getAttribute("innerHTML"));
                cartProductName = productObject.getText();
            }

            // Fetching the Product Swatch options
            for (WebElement swatch : swatchOptions) {
                String swatch_text = (String) ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].text;", swatch);
                cartSwatch.add(swatch_text);
//                cart_swatch.add(swatch.findElement(By.xpath("//dl/dd")).getText());
            }

            // Fetching Product Final Price
            for (WebElement final_price : productFinalPrice) {
                cartFinalPrice = final_price.getText();
            }

            // Fetching the Product Subtotal
            for (WebElement subtotal : productSubtotalPrice) {
                cartSubtotalPrice = subtotal.getText();
            }

            // Fetching the Product qty
            for (WebElement qty : productQty) {
                cartProductQty = qty.getAttribute("value");
            }
        }
        Loggers.getLogger().info("Fetched the Product Details from Cart");
        ExtentReport.getExtentNode().pass("Fetched the Product Details from Cart");
    }

    /**
     * Verify the Products added to Cart
     * @param driver - WebDriver
     * @return Boolean
     */
    public boolean verifyProducts(WebDriver driver) {
        HeaderModel headerModel = new HeaderModel(driver);
        MinicartModel minicartModel = new MinicartModel(driver);

        // Navigate to Cart page
        headerModel.getCartIcon().click();
        minicartModel.getViewCart().click();

        String productName = ExcelUtils.getDataMap().get("product_name");

        // Select the product passed from Listing page
        Iterator<WebElement> iter = getProductsAdded().iterator();
        while (iter.hasNext()) {
            String prodName = iter.next().getText();
            if (prodName.equals(productName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return String
     */
    public String getCartProductName() {
        return cartProductName;
    }

    /**
     * @return ArrayList<String>
     */
    public ArrayList<String> getCartSwatch() {
        return cartSwatch;
    }

    /**
     * @return String
     */
    public String getCartFinalPrice() {
        return cartFinalPrice;
    }

    /**
     * @return String
     */
    public String getCartSubtotalPrice() {
        return cartSubtotalPrice;
    }

    /**
     * @return String
     */
    public String getCartProductQty() {
        return cartProductQty;
    }
}
