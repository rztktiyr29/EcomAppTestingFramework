package com.sdet.framework.hooks;

import com.aventstack.extentreports.ExtentTest;
import com.sdet.framework.utils.DriverFactory;
import com.sdet.framework.utils.ConfigReader;
import com.sdet.framework.utils.ExtentManager;
import com.sdet.framework.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.Base64;

public class Hooks {

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        // Log scenario start immediately before any driver initialization
        System.out.println(">>> HOOK STARTING SCENARIO: " + scenario.getName() + " | Thread ID: " + Thread.currentThread().getId());

        // Create test entry in ExtentReport
//        ExtentTest test = ExtentManager.getInstance().createTest(scenario.getName());
//        ExtentManager.setTest(test);

        // Initialize driver
        String browser = ConfigReader.getProperty("browser");
        WebDriver driver = DriverFactory.initDriver(browser);

        String baseUrl = ConfigReader.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new RuntimeException("Property 'baseUrl' was not found in config.properties!");
        }

        driver.get(baseUrl);
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // 1. Capture screenshot using YOUR byte[] method
            byte[] screenshot = ScreenshotUtils.captureScreenshot();

            // 2. Convert byte[] to Base64 String for ExtentReports
            String base64Image = Base64.getEncoder().encodeToString(screenshot);

            if (scenario.isFailed()) {
                // Attach to native Cucumber HTML report
                scenario.attach(screenshot, "image/png", "Failed Scenario Screenshot");

                // Attach to Extent Report
//                ExtentManager.getTest().fail("Scenario Failed")
//                        .addScreenCaptureFromBase64String(base64Image);
            } else {
                // Attach to native Cucumber HTML report
                scenario.attach(screenshot, "image/png", "Passed Scenario Screenshot");

                // Attach to Extent Report
//                ExtentManager.getTest().pass("Scenario Passed")
//                        .addScreenCaptureFromBase64String(base64Image);
            }
        }

        // Clean up driver and Extent instances
        DriverFactory.quitDriver();
//        ExtentManager.unload();

        // Write Extent Report to reports/ExtentReport.html
//        ExtentManager.flush();
    }
}