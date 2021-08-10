package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.loggers.Loggers;
import com.magento.pageModels.LoginModel;
import com.magento.pageModels.SignupModel;
import com.magento.project_setup.TestNGBase;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.By;
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
        wait = new WebDriverWait(driver, 30);
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
            ExtentReport.getExtentTest().pass("User Account Created Successfully");
        } catch (Exception e) {
            Assert.assertTrue(signupModel.getMessages().getText().contains("There is already an account with this email address."));
            Loggers.getLogger().error("User Account Already Exists");
            ExtentReport.getExtentTest().fail("User Account Already Exists");
        }
    }

    /**
     * Login to User Account
     */
    @Test(description = "Logging into the User Account", priority = 2, groups = {"userAccounts.accountLogin"})
    public void accountLogin() {
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
            ExtentReport.getExtentTest().pass("User logged in Successfully");
        } catch (Exception e) {
            Loggers.getLogger().error("User could not be logged in");
            ExtentReport.getExtentTest().fail("User could not be logged in");
        }
    }

}
