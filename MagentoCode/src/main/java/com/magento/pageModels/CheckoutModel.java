package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CheckoutModel implements Constants {
    Select select;
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".authentication-wrapper .action")
    private WebElement signInLink;
    @FindBy(xpath = "//input[@name='firstname']")
    private WebElement firstName;
    @FindBy(xpath = "//input[@name='lastname']")
    private WebElement lastName;
    @FindBy(id = "login-email")
    private WebElement emailField;
    @FindBy(id = "login-password")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@name='street[0]']")
    private WebElement streetAddress;
    @FindBy(xpath = "//input[@name='city']")
    private WebElement city;
    @FindBy(xpath = "//select[@name='region_id']")
    private WebElement state;
    @FindBy(xpath = "//select[@name='country_id']")
    private WebElement country;
    @FindBy(xpath = "//input[@name='postcode']")
    private WebElement postCode;
    @FindBy(xpath = "//input[@name='telephone']")
    private WebElement telephone;
    @FindBy(xpath = "(//div[@class='block-content'] //button)[1]")
    private WebElement loginSubmit;
    @FindBy(css = ".shipping-address-item.selected-item")
    private WebElement selectedShip;
    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> shippingRates;
    @FindBy(css = "#shipping-method-buttons-container .button")
    private WebElement nextSubmit;
    @FindBy(xpath = "//td/input[1]")
    private WebElement selectShipping;
    @FindBy(xpath = "//td[4]")
    private By shipMethodName;
    @FindBy(id = "billing-address-same-as-shipping-checkmo")
    private WebElement setDefaultBilling;
    @FindBy(css = ".opc-progress-bar-item._active")
    private WebElement paymentPageRedirect;
    @FindBy(css = ".action.primary.checkout")
    private WebElement placeOrder;

    /**
     * @param driver - WebDriver element
     */
    public CheckoutModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Login to Account from Checkout page
     */
    public void checkoutLogin() {
        wait = new WebDriverWait(driver, 10);

        getSignInLink().click();

        // Getting the Customer Details
        ExcelUtils.excelConfigure(CUSTOMER_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));

        // Fill the login form
        wait.until(ExpectedConditions.visibilityOf(getEmailField()));
        getEmailField().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        getPasswordField().sendKeys(ExcelUtils.getDataMap().get("password"));
        getLoginSubmit().click();

        Loggers.getLogger().info("Clicked on Submit button");
        ExtentReport.getExtentNode().info("Clicked on Submit button");
    }

    /**
     * Fill the Shipping Address form in Checkout page
     */
    public void addShippingAddress() {
        select = new Select(getCountry());
        select.selectByValue(ExcelUtils.getDataMap().get("country"));

        select = new Select(getState());
        select.selectByValue(ExcelUtils.getDataMap().get("state"));

        getStreetAddress().sendKeys(ExcelUtils.getDataMap().get("street_address"));
        getCity().sendKeys(ExcelUtils.getDataMap().get("city"));
        getPostCode().sendKeys(ExcelUtils.getDataMap().get("post_code"));
        getTelephone().sendKeys(ExcelUtils.getDataMap().get("mobile_number"));
    }

    /**
     * @return WebElement - Signin Link
     */
    public WebElement getSignInLink() {
        return signInLink;
    }

    /**
     * @return WebElement - Email Field
     */
    public WebElement getEmailField() {
        return emailField;
    }

    /**
     * @return WebElement - Password Field
     */
    public WebElement getPasswordField() {
        return passwordField;
    }

    /**
     * @return WebElement - Street Address
     */
    public WebElement getStreetAddress() {
        return streetAddress;
    }

    /**
     * @return WebElement - City
     */
    public WebElement getCity() {
        return city;
    }

    /**
     * @return WebElement - State
     */
    public WebElement getState() {
        return state;
    }

    /**
     * @return WebElement - Country
     */
    public WebElement getCountry() {
        return country;
    }

    /**
     * @return WebElement - Postcode
     */
    public WebElement getPostCode() {
        return postCode;
    }

    /**
     * @return WebElement - Telephone
     */
    public WebElement getTelephone() {
        return telephone;
    }

    /**
     * @return WebElement - Login Submit Button
     */
    public WebElement getLoginSubmit() {
        return loginSubmit;
    }

    /**
     * @return WebElement - Selected Shipping Method
     */
    public WebElement getSelectedShip() {
        return selectedShip;
    }

    /**
     * @return List<WebElement> - Shipping Rates
     */
    public List<WebElement> getShippingRates() {
        return shippingRates;
    }

    /**
     * @return WebElement - Next Submit Button
     */
    public WebElement getNextSubmit() {
        return nextSubmit;
    }

    /**
     * @return WebElement - Select Shipping Method
     */
    public WebElement getSelectShipping() {
        return selectShipping;
    }

    /**
     * @return By - Ship Method Name
     */
    public By getShipMethodName() {
        return shipMethodName;
    }

    /**
     * @return WebElement - Set Default Billing
     */
    public WebElement getSetDefaultBilling() {
        return setDefaultBilling;
    }

    /**
     * @return WebElement - Place Order Button
     */
    public WebElement getPlaceOrder() {
        return placeOrder;
    }

    /**
     * @return WebElement - First Name
     */
    public WebElement getFirstName() {
        return firstName;
    }

    /**
     * @return WebElement - Last Name
     */
    public WebElement getLastName() {
        return lastName;
    }

    /**
     * @return WebElement - Payment Page
     */
    public WebElement getPaymentPageRedirect() {
        return paymentPageRedirect;
    }
}
