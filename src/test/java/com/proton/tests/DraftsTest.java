package com.proton.tests;

import com.proton.base.BaseTest;
import com.proton.pages.ComposePage;
import com.proton.pages.LoginPage;
import com.proton.pages.NavigationPage;
import com.proton.pages.MailBoxPage;
import com.proton.utils.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.proton.pages.BasePage.generateRandomSubject;

public class DraftsTest extends BaseTest {

    @Test(priority = 1)
    public void testDraftAutoSave() {

        ComposePage composePage = new ComposePage();
        NavigationPage navPage = new NavigationPage();
        MailBoxPage mailBoxPage = new MailBoxPage();

        String subject = generateRandomSubject("ProtonTest");

        composePage.openComposeWindow();
        composePage.enterSubject(subject);
        composePage.enterBody("Draft auto save test.");

        composePage.closeCompose();
        composePage.waitForComposeToClose();

        navPage.goToDrafts();

        Assert.assertTrue(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-DRAFT-001 Failed: Draft not saved."
        );
    }

    @Test(priority = 2)
    public void testDiscardDraft() {

        ComposePage composePage = new ComposePage();
        NavigationPage navPage = new NavigationPage();
        MailBoxPage mailBoxPage = new MailBoxPage();

        String subject = "Draft Discard " + System.currentTimeMillis();

        composePage.openComposeWindow();
        composePage.enterSubject(subject);


        composePage.discardDraft();

        navPage.goToDrafts();

        Assert.assertFalse(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-DRAFT-002 Failed: Discarded draft still exists."
        );
    }

    @Test(priority = 3)
    public void testDraftTransitionsToSent() throws InterruptedException {

        ComposePage composePage = new ComposePage();
        NavigationPage navPage = new NavigationPage();
        MailBoxPage mailBoxPage = new MailBoxPage();

        String subject = "Draft to Sent " + System.currentTimeMillis();
        String username = ConfigReader.getProperty("receiverUsername");


        composePage.openComposeWindow();
        composePage.enterRecipient(username);
        composePage.enterSubject(subject);
        composePage.enterBody("Draft to sent test.");

        composePage.closeCompose();
        composePage.waitForComposeToClose();


        navPage.goToDrafts();

        Assert.assertTrue(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-DRAFT-003 Failed: Initial draft was not saved in Drafts folder."
        );
    Thread.sleep(5000);

        mailBoxPage.openFirstDraft();

        Assert.assertTrue(
                composePage.isComposeOpen(),
                "TC-DRAFT-003 Failed: Draft composer did not open after clicking draft item."
        );


        composePage.clickSend();
        composePage.waitForComposeToClose();

        navPage.goToSent();

        Assert.assertTrue(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-DRAFT-003 Failed: Sent message missing in Sent folder."
        );
    }
}