package com.magento.pageModels;

import com.magento.project_setup.TestNGBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class LoginModel extends TestNGBase {

    @FindBy(xpath = "(//ul[@class='header links']/li/a)[1]")
    private WebElement loginLink;
    @FindBy(xpath = "//div/input[@id='email']")
    private WebElement emailId;
    @FindBy(xpath = "(//div/input[@id='pass'])[1]")
    private WebElement password;
    @FindBy(xpath = "(//div/button[@id='send2'])[1]")
    private WebElement submit;
    @FindBy(css = ".greet.welcome .logged-in")
    private WebElement userName;

    /**
     * @param driver - Webdriver element
     */
    public LoginModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @return WebElement - Login Link
     */
    public WebElement getLoginLink() {
        return loginLink;
    }

    /**
     * @return WebElement - Email Field
     */
    public WebElement getEmailId() {
        return emailId;
    }

    /**
     * @return WebElement - Password field
     */
    public WebElement getPassword() {
        return password;
    }

    /**
     * @return WebElement - Submit button
     */
    public WebElement getSubmit() {
        return submit;
    }

    /**
     * @return WebElement - Username field
     */
    public WebElement getUserName() {
        return userName;
    }

}
