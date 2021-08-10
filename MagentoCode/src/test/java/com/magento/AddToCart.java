package com.magento;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.pageModels.HeaderModel;
import com.magento.pageModels.SearchListingModel;
import com.magento.project_setup.Initialize;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AddToCart extends Initialize implements Constants {
    public WebDriver driver;
    WebDriverWait wait;
    FluentWait<WebDriver> fluent;

    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();
        wait = new WebDriverWait(driver, 30);
        fluent = new FluentWait<>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MICROSECONDS)
                .ignoring(Exception.class);

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("Add To Cart");

        // Getting the Website Details
        ExcelUtils.excelConfigure(WEBSITE_SAMPLE_DATA);
        ExcelUtils.getRowData(Integer.parseInt(Objects.requireNonNull(Property.getProperty("testRow"))));
    }

    @Test(groups = {"addToCart"})
    public void addToCart() {
        HeaderModel headerModel = new HeaderModel(driver);
        SearchListingModel searchListingModel = new SearchListingModel(driver);

        headerModel.getSearchBox().sendKeys(ExcelUtils.getDataMap().get("search_text"), Keys.RETURN);
        Assert.assertTrue(searchListingModel.getSearchTitle().getText()
                .contains(ExcelUtils.getDataMap().get("search_text")));

        String productName = ExcelUtils.getDataMap().get("product_name");
        Actions act = new Actions(driver);

        List<WebElement> prodList = searchListingModel.getProductsList();

        for (WebElement element : prodList) {
            if(element.findElement(By.cssSelector("div > div > strong > a")).getText().equals(productName))
                act.moveToElement(element).click().build().perform();
        }

    }

}
