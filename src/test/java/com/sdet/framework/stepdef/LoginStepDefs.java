package com.sdet.framework.stepdef;

import com.sdet.framework.pages.LoginPage;
import com.sdet.framework.utils.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginStepDefs {

    private LoginPage loginPage;

    public LoginStepDefs() {
    }

    @When("User enters valid username and password")
    public void userEntersValidUsernameAndPassword() {
//        String username = ConfigReader.getProperty("username");
//        String password = ConfigReader.getProperty("password");
        loginPage = new LoginPage();
        loginPage.login("bWayne7@gmail.com", "Batman@123");
    }

    @When("User enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("User should be redirected to the inbox")
    public void userShouldBeRedirectedToTheInbox() {
        Assert.assertTrue(
                loginPage.isLoggedIn(),
                "Login failed: Compose button / Inbox is not displayed."
        );
    }

    @Then("An error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        String errorText = loginPage.getErrorMessageText();
        Assert.assertFalse(
                errorText.trim().isEmpty(),
                "Expected error message was not displayed on the screen."
        );
    }
    @Then("User should be logged in")
    public void isloggedin(){
        boolean status = loginPage.isLoggedIn();
        try {
            Assert.assertTrue(status, "Profile button not found. Not logged in.");
            System.out.println("Logged in successfully.");
        } catch (AssertionError e) {
            System.out.println("Profile button not found.");
            throw e; // Re-throw so TestNG still marks the test step as FAILED
        }

    }
}
