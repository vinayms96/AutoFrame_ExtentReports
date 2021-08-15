package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.HeaderModel;
import com.magento.pageModels.LoginModel;
import com.magento.pageModels.SignupModel;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class UserAccounts extends TestNGBase {
    public WebDriver driver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluent;

    /**
     * Setting up Loggers and Extent reports
     */
    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 10);
        fluent = new FluentWait<>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MICROSECONDS)
                .ignoring(Exception.class);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("User Accounts");
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    /**
     * Create New Account
     */
    @Test(description = "Creating the User Account", priority = 1, groups = {"userAccounts.accountCreate"})
    public void accountCreate() {
        // Setting up the Extent Report
        ExtentReport.createNode("Create User Account");

        // PageModel object
        SignupModel signupModel = new SignupModel(driver);
        LoginModel loginModel = new LoginModel(driver);

        // Redirecting to Create account page
        signupModel.getCreateAccountLink().click();

        // Filling the form
        signupModel.getFirstName().sendKeys(ExcelUtils.getDataMap().get("first_name"));
        signupModel.getLastName().sendKeys(ExcelUtils.getDataMap().get("last_name"));

        Loggers.getLogger().info("Redirected to Create Account page");
        if (Objects.equals(Property.getProperty("is_subscribed"), 1))
            signupModel.getIsSubscribed().click();

        signupModel.getEmailAddress().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        signupModel.getPassword().sendKeys(ExcelUtils.getDataMap().get("password"));
        signupModel.getPasswordConfirmation().sendKeys(ExcelUtils.getDataMap().get("password"));
        signupModel.getSubmit().click();

        // Fetching the User First and Last name from Excel
        String firstName = ExcelUtils.getDataMap().get("first_name");
        String lastName = ExcelUtils.getDataMap().get("last_name");

        // Verify if the account is created / not
        wait.until(ExpectedConditions.visibilityOf(signupModel.getMessages()));
        try {
            wait.until(ExpectedConditions.visibilityOf(loginModel.getUser_name()));
            Assert.assertTrue(loginModel.getUser_name().getText().contains(firstName + " " + lastName));
            Loggers.getLogger().info("User Account Created Successfully");
            ExtentReport.getExtentNode().pass("User Account Created Successfully");
        } catch (Exception e) {
            Assert.assertTrue(signupModel.getMessages().getText().contains("There is already an account with this email address."));
            Loggers.getLogger().error("User Account Already Exists");
            ExtentReport.getExtentNode().fail("User Account Already Exists");
        }
    }

    /**
     * Login to User Account
     */
    @Test(description = "Logging into the User Account", priority = 2, groups = {"userAccounts.accountLogin"})
    public void accountLogin() {
        // Setting up the Extent Report
        ExtentReport.createNode("Login to Account");

        // PageModel object
        LoginModel loginModel = new LoginModel(driver);

        // Click on Login link
        wait.until(ExpectedConditions.elementToBeClickable(loginModel.getLogin_link()));
        loginModel.getLogin_link().click();

        // Entering the form details
        loginModel.getEmail_id().sendKeys(ExcelUtils.getDataMap().get("email_id"));
        loginModel.getPassword().sendKeys(ExcelUtils.getDataMap().get("password"));
        Loggers.getLogger().info("Redirected to Login page");
        loginModel.getSubmit().click();

        String firstName = ExcelUtils.getDataMap().get("first_name");
        String lastName = ExcelUtils.getDataMap().get("last_name");

        // Verifying if user is logged in
        try {
            fluent.until(ExpectedConditions.textToBePresentInElement(loginModel.getUser_name(), firstName));
            Assert.assertTrue(loginModel.getUser_name().getText().contains(firstName + " " + lastName));
            Loggers.getLogger().info("User logged in Successfully");
            ExtentReport.getExtentNode().pass("User logged in Successfully");
        } catch (Exception e) {
            Loggers.getLogger().error("User could not be logged in");
            ExtentReport.getExtentNode().fail("User could not be logged in");
        }
    }

    /**
     * Logout of User Account
     */
    @Test(description = "Logout of the User Account", priority = 3, groups = {"userAccounts.accountLogout"}, dependsOnMethods = {"accountLogin"})
    public void accountLogout() {
        // Setting up the Extent Report
        ExtentReport.createNode("Logout of Account");

        // PageModel object
        HeaderModel headerModel = new HeaderModel(driver);

        // Logout from the account
        if(driver.getCurrentUrl().contains("customer/account/") || driver.getCurrentUrl().contains("/")) {
            headerModel.getAccountDropdown().click();
            try {
                headerModel.getLogoutLink().click();
                Assert.assertTrue(driver.getCurrentUrl().contains("customer/account/logout"));
                Loggers.getLogger().info("User is successfully Logged Out");
                ExtentReport.getExtentNode().pass("User is successfully Logged Out");
            } catch (Exception e) {
                Loggers.getLogger().error("User could NOT be logged out");
                ExtentReport.getExtentNode().fail("User could NOT be logged out");
            }
        } else {
            Loggers.getLogger().error("User is not Logged in");
            ExtentReport.getExtentNode().fail("User is not Logged in");
        }
    }

}
