package com.proton.pages;

import com.aventstack.extentreports.Status;
import com.proton.utils.DriverFactory;
import com.proton.utils.ExtentManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();

        if (this.driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized. Make sure BaseTest.setUp() runs before creating Page objects."
            );
        }

        int explicitWait = ConfigReaderSafe.getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    protected WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(By locator) {

        log.info("Clicking element: {}", locator);

        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(
                    Status.INFO,
                    "Clicking element: " + locator
            );
        }

        try {
            waitForElementToBeClickable(locator).click();

        } catch (ElementClickInterceptedException e) {

            log.warn("Normal click intercepted. Trying JavaScript click.");

            WebElement element = waitForElementPresent(locator);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);

        } catch (StaleElementReferenceException e) {

            log.warn("Stale element. Retrying click.");

            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(locator)
            )).click();
        }
    }

    protected void sendKeys(By locator, String text) {

        log.info("Typing into element: {}", locator);

        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(
                    Status.INFO,
                    "Typing into element: " + locator
            );
        }

        WebElement element = waitForElementToBeVisible(locator);

        element.click();

        element.clear();

        if (text != null) {
            element.sendKeys(text);
        }
    }

    protected String getText(By locator) {

        log.info("Fetching text from: {}", locator);

        return waitForElementToBeVisible(locator).getText();
    }

    public boolean isDisplayed(By locator) {

        try {
            return waitForElementToBeVisible(locator).isDisplayed();

        } catch (TimeoutException |
                 NoSuchElementException |
                 StaleElementReferenceException e) {

            return false;
        }
    }

    public boolean isPresent(By locator) {

        try {
            return driver.findElements(locator).size() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    protected void clearAndType(By locator, String text) {

        WebElement element = waitForElementToBeVisible(locator);

        element.click();
        element.clear();

        if (text != null) {
            element.sendKeys(text);
        }
    }

    public void switchToIframe(By locator) {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));

        log.info("Switched to iframe: {}", locator);
    }

    public void switchToDefaultContent() {

        driver.switchTo().defaultContent();

        log.info("Switched to default content");
    }

    public void uploadFile(By locator, String absoluteFilePath) {

        if (absoluteFilePath == null || absoluteFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Attachment path cannot be empty.");
        }

        java.io.File file = new java.io.File(absoluteFilePath);

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "Attachment file does not exist: " + absoluteFilePath
            );
        }

        WebElement element = waitForElementPresent(locator);

        element.sendKeys(file.getAbsolutePath());

        log.info("Uploaded file: {}", file.getAbsolutePath());
    }

    public void clickUsingJS(By locator) {

        WebElement element = waitForElementPresent(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    public void clickWithRetry(By locator) {

        int attempts = 0;

        while (attempts < 3) {

            try {

                wait.until(
                        ExpectedConditions.elementToBeClickable(locator)
                ).click();

                return;

            } catch (StaleElementReferenceException |
                     ElementClickInterceptedException e) {

                attempts++;

                log.warn(
                        "Click attempt {} failed for {}",
                        attempts,
                        locator
                );

                if (attempts == 3) {
                    throw e;
                }
            }
        }
    }

    protected void waitForPageLoad() {

        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    protected void pressEnter(By locator) {

        WebElement element = waitForElementToBeVisible(locator);

        element.sendKeys(Keys.ENTER);
    }

    private static class ConfigReaderSafe {

        static int getExplicitWait() {

            String value = com.proton.utils.ConfigReader
                    .getProperty("explicitWait");

            try {
                return Integer.parseInt(value);

            } catch (Exception e) {
                return 20;
            }
        }
    }
    public static String generateRandomSubject(String prefix) {
        int randomNum = (int) (Math.random() * 90000) + 10000;
        return prefix + "_" + randomNum;
    }


}