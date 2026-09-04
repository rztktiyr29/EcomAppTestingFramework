package com.proton.tests;

import com.proton.base.BaseTest;
import com.proton.pages.LoginPage;
import com.proton.utils.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void testInvalidLogin() {

        LoginPage loginPage =
                new LoginPage();

        loginPage.login(
                "invalid_user_123456",
                "invalid_password"
        );

        String actualError =
                loginPage.getErrorMessageText();

        Assert.assertFalse(
                actualError.trim().isEmpty(),
                "TC-AUTH-003 Failed: Login error message was not displayed."
        );
    }

    @Test(priority = 2)
    public void testValidLogin() {

        LoginPage loginPage =
                new LoginPage();

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        Assert.assertTrue(
                loginPage.isInboxDisplayed(),
                "TC-AUTH-002 Failed: User was not redirected to Inbox."
        );
    }

    @Test(priority = 3)
    public void testLogout() {

        LoginPage loginPage =
                new LoginPage();

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        Assert.assertTrue(
                loginPage.isLoggedIn(),
                "Login failed before logout."
        );

        loginPage.logout();

        Assert.assertTrue(
                loginPage.isLoggedOut(),
                "TC-AUTH-004 Failed: User was not returned to login page."
        );
    }
}