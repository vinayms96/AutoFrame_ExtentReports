package com.magento.project_setup;

import com.magento.loggers.Loggers;
import com.magento.utilities.Property;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

public class Initialize {
    public WebDriver driver = null;

    /**
     * Invoke the Browser specified as System Argument (Chrome or Firefox)
     * Also selecting Browser Modes (Headless or not)
     * off -> Headless
     */
    public WebDriver initializeDriver() {

        // Setting Browser Capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);

        // Setting Browser Options
        ChromeOptions ch_options = new ChromeOptions();
        ch_options.merge(capabilities);

        FirefoxOptions ff_options = new FirefoxOptions();
        ff_options.merge(capabilities);

        // Setting Browser Mode
        if (Property.getProperty("headless").equalsIgnoreCase("on")) {
            ch_options.addArguments("--headless");
            ff_options.addArguments("--headless");
        }

        // Selecting the Browser
        if (Property.getProperty("browser").equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
//            System.setProperty("webdriver.chrome.driver", "./../magento_data/browser_drivers/chromedriver");
            driver = new ChromeDriver(ch_options);
            Loggers.getLogger().info("Chrome browser is Launched");
        } else if (Property.getProperty("browser").equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
//            System.setProperty("webdriver.gecko.driver", "./../magento_data/browser_drivers/geckodriver");
            driver = new FirefoxDriver(ff_options);
            Loggers.getLogger().info("Firefox browser is Launched");
        }

        // Hitting the URL and Maximizing the window
        driver.manage().window().maximize();
        driver.get(Property.getProperty("url"));

        try {
            Assert.assertEquals(driver.getTitle(), Property.getProperty("title"));
            Loggers.getLogger().info("Website Url is hit");
        } catch (Exception e) {
            Loggers.getLogger().error("Could not launch the website");
        }
        return driver;
    }

}