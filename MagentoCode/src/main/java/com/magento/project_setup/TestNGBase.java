package com.magento.project_setup;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.mail.SMTPMail;
import com.magento.mysql.CustomerData;
import com.magento.mysql.JdbcConnection;
import com.magento.mysql.WebsiteData;
import com.magento.utilities.ExcelUtils;
import com.magento.utilities.Property;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestNGBase extends Initialize implements Constants {

    /**
     * Executing all the Pre Test Run methods in @BeforeSuite
     */
    @BeforeSuite(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRun() {

        // Setting the Loggers
        Loggers.setLogger(TestNGBase.class.getName());

        // configuring the Extent Reports
        ExtentReport.extentReport();

        // Configuring the Excel Data
        ExcelUtils.excelConfigure(CUSTOMER_SAMPLE_DATA);

        // Configuring the Database Connection
        JdbcConnection.establishConnection();

        // Add full or Update the database from Excel
        String property = Property.getProperty("updateData");

        switch (property) {
            case "update":
                // Updating the table and data
                CustomerData.updateCustomerData();
                WebsiteData.updateWebsiteData();
                break;
            default:
                break;
        }

    }

    /**
     * Closing the Browser after the end of each Test
     */
    @AfterClass(description = "Post Test configurations", alwaysRun = true)
    public void postTestRuns() {
        driver.quit();
        Loggers.getLogger().info("Browser is closed");
    }

    /**
     * Executing all the Post Test Run methods in @AfterSuite
     */
    @AfterSuite(description = "Final finish configurations", alwaysRun = true)
    public void postTestRun() {
        // Closing the Database Connection
        try {
            JdbcConnection.getConnection().close();
            Loggers.getLogger().info("Database connection is closed.");
        } catch (Exception e) {
            Loggers.getLogger().error(e.getMessage());
        }

        // Flushing the Extent Reports to generate the report
        if (Property.getProperty("extent").equalsIgnoreCase("enable")) {
            ExtentReport.getExtentReports().flush();
            SMTPMail.sendEmail();
            Loggers.getLogger().info("Extent Report is flushed and report is created");
        }
    }

}