package com.proton.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class LoginPage extends BasePage {

    private final By usernameField =
            By.id("username");

    private final By passwordField =
            By.id("password");

    private final By submitButton =
            By.xpath("//button[@type='submit']");

    private final By errorMessage =
            By.cssSelector("[data-testid='login:error-block']");

    private final By composeButton =
            By.cssSelector("button[data-testid='sidebar:compose']");

    private final By userMenuButton =
            By.cssSelector("button[data-testid='heading:userdropdown']");

    private final By logoutButton =
            By.cssSelector("button[data-testid='userdropdown:button:logout']");

    public LoginPage() {
        super();
    }

    public void enterUsername(String username) {

        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {

        sendKeys(passwordField, password);
    }

    public void clickSubmit() {

        click(submitButton);
    }

    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickSubmit();
    }

    public String getErrorMessageText() {

        try {
            return getText(errorMessage);
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean isInboxDisplayed() {

        return isDisplayed(composeButton);
    }

    public boolean isLoggedIn() {

        return isDisplayed(composeButton);
    }

    public void logout() {

        click(userMenuButton);

        click(logoutButton);

        wait.until(driver ->
                driver.findElements(usernameField).size() > 0
        );
    }

    public boolean isLoggedOut() {

        return isDisplayed(usernameField);
    }
}