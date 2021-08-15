package com.magento.pageModels;

import com.magento.project_setup.TestNGBase;
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

}
