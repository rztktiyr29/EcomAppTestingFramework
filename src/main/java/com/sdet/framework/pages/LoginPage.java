package com.sdet.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class LoginPage extends BasePage {

    private final By loginButton =
            By.xpath("//a[@href='/login']");

    private final By userNameField =
            By.xpath("//input[contains(@name , 'email')]");

    private final By passwordField =
            By.xpath("//input[contains(@name , 'password')]");

    private final By submitButton =
            By.xpath("//button[text()='Login']");

    private final By errorMessage =
            By.cssSelector("[data-testid='login:error-block']");

    private final By profileButton =
            By.xpath("//a[@href='/profile']");

    private final By logoutButton =
            By.cssSelector("button[data-testid='userdropdown:button:logout']");

    public LoginPage() {
        super();
    }
    public void clickLoginbuttton(){
       click(loginButton);
    }
    public void enterUsername(String username) {

        sendKeys(userNameField, username);
    }

    public void enterPassword(String password) {

        sendKeys(passwordField, password);
    }

    public void clickSubmit() {

        click(submitButton);
    }

    public void login(String username, String password) {
        clickLoginbuttton();
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



    public boolean isLoggedIn() {

        return isDisplayed(profileButton);
    }

    public void logout() {


        click(logoutButton);

        wait.until(driver ->
                driver.findElements(userNameField).size() > 0
        );
    }

    public boolean isLoggedOut() {

        return isDisplayed(userNameField);
    }
}