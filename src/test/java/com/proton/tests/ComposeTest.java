package com.proton.tests;

import com.proton.base.BaseTest;
import com.proton.pages.ComposePage;
import com.proton.pages.LoginPage;
import com.proton.pages.NavigationPage;
import com.proton.pages.MailBoxPage;
import com.proton.utils.ConfigReader;

import com.proton.utils.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.proton.pages.BasePage.generateRandomSubject;


public class ComposeTest extends BaseTest {



    @Test(priority = 1)
    public void testSendValidEmail() {
        System.out.println("==========2nd SEND EMAIL TEST START ==========");
        ComposePage composePage = new ComposePage();
        NavigationPage navPage = new NavigationPage();
        MailBoxPage mailBoxPage = new MailBoxPage();

        String subject = generateRandomSubject("ProtonTest");
        composePage.openComposeWindow();
        composePage.enterRecipient(ConfigReader.getProperty("receiverUsername"));
        composePage.enterSubject(subject);
        composePage.enterBody("Sample test body execution.");

        composePage.clickSend();
composePage.waitForComposeToClose();

        Assert.assertTrue(
                composePage.isComposeWindowClosed(),
                "TC-COMPOSE-001 Failed: Compose window did not close after clicking Send."
        );


        navPage.goToSent();


        Assert.assertTrue(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-COMPOSE-001 Failed: Email not found in Sent folder."
        );
        System.out.println("========== SEND EMAIL TEST END ==========");
    }

    @Test(priority = 2)
    public void testSendInvalidRecipientFormat() {

        ComposePage composePage = new ComposePage();

        composePage.openComposeWindow();
        composePage.enterRecipient("notanemail@proton.me");
        composePage.enterSubject("Test");
        composePage.enterBody("Sample test body execution.");

        composePage.clickSend();

        Assert.assertEquals(
                composePage.getRecipientErrorText(),
                "Sending error",
                "TC-COMPOSE-002 Failed: Error modal header does not match expected text."
        );
    }

    @Test(priority = 3)
    public void testSendWithCCAndBCC() {

        ComposePage composePage = new ComposePage();
        NavigationPage navPage = new NavigationPage();
        MailBoxPage mailBoxPage = new MailBoxPage();

        String subject = generateRandomSubject("ProtonTest");

        composePage.openComposeWindow();

        composePage.enterRecipient(ConfigReader.getProperty("receiverUsername"));

        composePage.enterCC(ConfigReader.getProperty("cc"));

        composePage.enterBCC(ConfigReader.getProperty("bcc"));

        composePage.enterSubject(subject);

        composePage.enterBody("CC and BCC automation test.");

        composePage.clickSend();

        composePage.waitForComposeToClose();

        navPage.goToSent();

        Assert.assertTrue(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-COMPOSE-003 Failed: Message not found in Sent."
        );
    }

@Test(priority = 4)

public void testTwoAccountVerification() {

    ComposePage composePage = new ComposePage();
    LoginPage loginPage = new LoginPage();
    MailBoxPage mailBoxPage = new MailBoxPage();
    NavigationPage navPage = new NavigationPage();

    String subject = generateRandomSubject("ProtonTest");

    composePage.openComposeWindow();

    composePage.enterRecipient(
            ConfigReader.getProperty("receiverUsername")
    );

    composePage.enterSubject(subject);

    composePage.enterBody("Two account verification test.");

    composePage.clickSend();


    composePage.waitForComposeToClose();

    navPage.goToSent();

    Assert.assertTrue(
            mailBoxPage.isMessagePresentWithWait(subject),
            "TC-COMPOSE-004 Pre-condition Failed: Message was not found in Sent folder before logout."
    );


    loginPage.logout();


    loginPage.login(
            ConfigReader.getProperty("receiverUsername"),
            ConfigReader.getProperty("receiverPassword")
    );
    DriverFactory.getDriver().navigate().refresh();
    Assert.assertTrue(
            loginPage.isLoggedIn(),
            "Receiver account login failed."
    );

    Assert.assertTrue(
            mailBoxPage.isMessagePresentWithWait(subject),
            "TC-COMPOSE-004 Failed: Message missing in receiver inbox."
    );

}}