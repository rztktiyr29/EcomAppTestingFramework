package com.sdet.framework.hooks;

import com.sdet.framework.utils.DriverFactory;
import com.sdet.framework.utils.ConfigReader;
import com.sdet.framework.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.Base64;

public class Hooks {

    // ONLY runs before scenarios tagged with @ui
    @Before("@ui")
    public void setUp(Scenario scenario) {
        System.out.println(">>> LAUNCHING BROWSER FOR UI SCENARIO: " + scenario.getName());

        String browser = ConfigReader.getProperty("browser");
        WebDriver driver = DriverFactory.initDriver(browser);

        String baseUrl = ConfigReader.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new RuntimeException("Property 'baseUrl' was not found in config.properties!");
        }

        driver.get(baseUrl);
    }

    // ONLY runs after scenarios tagged with @ui
    @After("@ui")
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            byte[] screenshot = ScreenshotUtils.captureScreenshot();

            if (scenario.isFailed()) {
                scenario.attach(screenshot, "image/png", "Failed Scenario Screenshot");
            } else {
                scenario.attach(screenshot, "image/png", "Passed Scenario Screenshot");
            }
        }

        DriverFactory.quitDriver();
    }
}