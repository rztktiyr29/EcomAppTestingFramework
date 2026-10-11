package com.sdet.framework.utils;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver initDriver(String browser) {

        if (driver.get() != null) {
            return driver.get();
        }

        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome";
        }

        boolean headless =
                Boolean.parseBoolean(
                        ConfigReader.getProperty(
                                "headless"
                        )
                );

        switch (browser.trim().toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                // 1. Set experimental preferences to block save bubbles & autofill popups
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("autofill.profile_enabled", false);
                chromePrefs.put("autofill.credit_card_enabled", false);
                chromePrefs.put("profile.default_content_setting_values.notifications", 2);
                chromePrefs.put("profile.default_content_setting_values.geolocation", 2);

                chromeOptions.setExperimentalOption("prefs", chromePrefs);

                // 2. Add browser arguments to suppress UI overlays & ensure container stability
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                chromeOptions.addArguments(
                        "--start-maximized",
                        "--disable-notifications",
                        "--disable-popup-blocking",
                        "--disable-save-password-bubble",
                        "--disable-autofill",
                        "--disable-infobars",
                        "--no-sandbox",
                        "--disable-dev-shm-usage"
                );

                driver.set(
                        new ChromeDriver(chromeOptions)
                );

                break;

            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }

                // Suppress password saving prompt in Firefox
                firefoxOptions.addPreference("signon.rememberSignons", false);
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);

                driver.set(
                        new FirefoxDriver(firefoxOptions)
                );

                break;

            case "edge":

                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();

                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                edgePrefs.put("autofill.profile_enabled", false);
                edgePrefs.put("autofill.credit_card_enabled", false);

                edgeOptions.setExperimentalOption("prefs", edgePrefs);

                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }

                edgeOptions.addArguments(
                        "--start-maximized",
                        "--disable-notifications",
                        "--disable-popup-blocking",
                        "--disable-save-password-bubble",
                        "--disable-autofill"
                );

                driver.set(
                        new EdgeDriver(edgeOptions)
                );

                break;

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }

        WebDriver webDriver = getDriver();

        webDriver.manage()
                .timeouts()
                .implicitlyWait(Duration.ZERO);

        webDriver.manage()
                .timeouts()
                .pageLoadTimeout(Duration.ofSeconds(60));

        webDriver.manage()
                .timeouts()
                .scriptTimeout(Duration.ofSeconds(30));

        try {
            webDriver.manage().window().maximize();
        } catch (Exception ignored) {
        }

        webDriver.manage().deleteAllCookies();

        return webDriver;
    }

    public static WebDriver getDriver() {

        return driver.get();
    }

    public static void quitDriver() {

        WebDriver webDriver = driver.get();

        if (webDriver != null) {

            try {
                webDriver.quit();
            } finally {
                driver.remove();
            }
        }
    }
}