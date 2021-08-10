package com.magento.pageModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchListingModel {

    @FindBy(css = "h1 > span")
    private WebElement searchTitle;
    @FindBy(css = ".product-items > li")
    private List<WebElement> productsList;
    @FindBy(css = "div > div > strong > a")
    private By productName;

    public SearchListingModel(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getSearchTitle() {
        return searchTitle;
    }

    public List<WebElement> getProductsList() {
        return productsList;
    }

    public By getProductName() {
        return productName;
    }
}
