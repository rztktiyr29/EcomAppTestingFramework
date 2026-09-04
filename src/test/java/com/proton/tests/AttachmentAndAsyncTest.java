package com.proton.tests;

import com.proton.base.BaseTest;
import com.proton.pages.ComposePage;
import com.proton.pages.LoginPage;
import com.proton.pages.NavigationPage;
import com.proton.pages.MailBoxPage;
import com.proton.utils.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class AttachmentAndAsyncTest extends BaseTest {



    @Test(priority = 1)
    public void testSendWithAttachment() {

        ComposePage composePage =
                new ComposePage();

        String filePath =
                new File(
                        "src/test/resources/sample.pdf"
                ).getAbsolutePath();

        Assert.assertTrue(
                new File(filePath).exists(),
                "Attachment file does not exist: " + filePath
        );

        composePage.openComposeWindow();

        composePage.enterRecipient(
                ConfigReader.getProperty("receiverUsername")
        );

        composePage.enterSubject(
                "Attachment Test "
                        + System.currentTimeMillis()
        );

        composePage.enterBody(
                "Attachment automation test."
        );

        composePage.uploadAttachment(filePath);

        composePage.clickSend();
    }

    @Test(priority = 2)

    public void testUndoSend() {

        ComposePage composePage = new ComposePage();

        composePage.openComposeWindow();

        composePage.enterRecipient(
                ConfigReader.getProperty("username")
        );

        composePage.enterSubject(
                "Undo Send Test " + System.currentTimeMillis()
        );

        composePage.enterBody(
                "Undo send automation test."
        );

        composePage.clickSend();
        composePage.clickUndo();

        Assert.assertTrue(
                composePage.isComposeOpen(),
                "TC-ASYNC-001 Failed: Compose did not reopen after undo."
        );
    }

    @Test(priority = 3)
    public void testNormalSendAfterUndoExpires() {

        ComposePage composePage =
                new ComposePage();

        NavigationPage navPage =
                new NavigationPage();

        MailBoxPage mailBoxPage =
                new MailBoxPage();

        String subject =
                "Async Completion "
                        + System.currentTimeMillis();

        composePage.openComposeWindow();

        composePage.enterRecipient(
                ConfigReader.getProperty("username")
        );

        composePage.enterSubject(subject);

        composePage.enterBody(
                "Async completion automation test."
        );

        composePage.clickSend();

        navPage.goToSent();

        Assert.assertTrue(
                mailBoxPage.isMessagePresentWithWait(subject),
                "TC-ASYNC-002 Failed: Email missing in Sent."
        );
    }
}