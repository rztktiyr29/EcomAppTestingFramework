package com.proton.base;

import com.proton.pages.LoginPage;
import com.proton.utils.ConfigReader;
import com.proton.utils.DriverFactory;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp() {

        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("url");
        DriverFactory.initDriver(browser);

        DriverFactory.getDriver().get(url);
        LoginPage loginPage = new LoginPage();

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );
    }

    @AfterMethod
    public void tearDown() {

        System.out.println("Scenario Completed");

        DriverFactory.quitDriver();
    }
}