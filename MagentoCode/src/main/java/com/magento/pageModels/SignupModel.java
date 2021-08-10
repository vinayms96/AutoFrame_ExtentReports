package com.magento.pageModels;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.modules.MouseActions;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SignupModel extends TestNGBase {

    @FindBy(css = ".header .links")
    private WebElement headerLinks;
    @FindBy(xpath = "//ul[@class='header links']/li[3]/a")
    private WebElement createAccountLink;
    @FindBy(xpath = "//div/input[@id='firstname']")
    private WebElement firstName;
    @FindBy(xpath = "//div/input[@id='lastname']")
    private WebElement lastName;
    @FindBy(xpath = "//div/input[@id='is_subscribed']")
    private WebElement isSubscribed;
    @FindBy(xpath = "//div/input[@id='email_address']")
    private WebElement emailAddress;
    @FindBy(xpath = "//div/input[@id='password']")
    private WebElement password;
    @FindBy(xpath = "//div/input[@id='password-confirmation']")
    private WebElement passwordConfirmation;
    @FindBy(css = ".action.submit.primary")
    private WebElement submit;
    @FindBy(xpath = "//div[@data-ui-id='message-error']/div")
    private WebElement messages;

    /**
     * @param driver - Webdriver element
     */
    public SignupModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @return WebElement - Header Links
     */
    public WebElement getHeaderLinks() {
        return headerLinks;
    }

    /**
     * @return WebElement - Create Account Link
     */
    public WebElement getCreateAccountLink() {
        return createAccountLink;
    }

    /**
     * @return WebElement - Firstname Field
     */
    public WebElement getFirstName() {
        return firstName;
    }

    /**
     * @return WebElement - Lastname Field
     */
    public WebElement getLastName() {
        return lastName;
    }

    /**
     * @return WebElement - Subscribed Checkbox
     */
    public WebElement getIsSubscribed() {
        return isSubscribed;
    }

    /**
     * @return WebElement - Email Address
     */
    public WebElement getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return WebElement - Password
     */
    public WebElement getPassword() {
        return password;
    }

    /**
     * @return WebElement - Password Confirmation
     */
    public WebElement getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * @return WebElement - Submit button
     */
    public WebElement getSubmit() {
        return submit;
    }

    /**
     * @return WebElement - Messages
     */
    public WebElement getMessages() {
        return messages;
    }

    /**
     * Click on Create Account link
     */
    /*public void clickCreateAccountLink(WebDriver driver) {
        // Creating Extent Node
        ExtentReport.createNode("Create Account: Click Create Account Link");

        // Clicking on link
        MouseActions.moveClickEvent(driver, create_account_link);

        // Logging and Reporting
        Loggers.getLogger().info("Create account link clicked");
        ExtentReport.getExtentNode().pass("Create account link clicked");
    }*/

    /**
     * Fill the Create account form and hit Submit
     */
//    public void fillCustomerForm(WebDriver driver) {
//        // Creating Extent Node
//        ExtentReport.createNode("Create Account: Fill Customer Form");
//
//        // Filling the form fields
//        first_name.sendKeys(ExcelUtils.getDataMap().get("first_name"));
//        Loggers.getLogger().info("Entered the Firstname");
//        ExtentReport.getExtentNode().pass("Entered the Firstname");
//
//        last_name.sendKeys(ExcelUtils.getDataMap().get("last_name"));
//        Loggers.getLogger().info("Entered the Lastname");
//        ExtentReport.getExtentNode().pass("Entered the Lastname");
//
////        if (Property.getProperty("is_subscribed").equals(1)) {
////            is_subscribed.click();
////            ExtentReport.getExtentSubNode().pass("Check the Subscriber Newsletter");
////        }
//
//        email_address.sendKeys(ExcelUtils.getDataMap().get("email_id"));
//        Loggers.getLogger().info("Entered the Email id");
//        ExtentReport.getExtentNode().pass("Entered the Email id");
//
//        password.sendKeys(ExcelUtils.getDataMap().get("password"));
//        Loggers.getLogger().info("Entered the Password");
//        ExtentReport.getExtentNode().pass("Entered the Password");
//
//        password_confirmation.sendKeys(ExcelUtils.getDataMap().get("password"));
//        Loggers.getLogger().info("Entered the Confirmation Password");
//        ExtentReport.getExtentNode().pass("Entered the Confirmation Password");
//
//        MouseActions.moveClickEvent(driver, submit);
//        Loggers.getLogger().info("Clicked on Submit button");
//        ExtentReport.getExtentNode().pass("Clicked on Submit button");
//
//    }
}
