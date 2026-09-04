package com.proton.tests;

import com.proton.base.BaseTest;
import com.proton.pages.LoginPage;
import com.proton.pages.NavigationPage;
import com.proton.pages.MailBoxPage;
import com.proton.utils.ConfigReader;

import com.proton.utils.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InboxActionsTest extends BaseTest {


    @Test(priority = 1)
    public void testMarkAsRead() {

        MailBoxPage mailBoxPage =
                new MailBoxPage();

        Assert.assertTrue(
                mailBoxPage.getMessageCount() > 0,
                "TC-INBOX-001 Failed: No email available in Inbox."
        );

        mailBoxPage.selectFirstEmail();

        mailBoxPage.markAsRead();
    }

    @Test(priority = 2)
    public void testStarAndUnstarMessage() {

        MailBoxPage mailBoxPage =
                new MailBoxPage();

        NavigationPage navPage =
                new NavigationPage();

        Assert.assertTrue(
                mailBoxPage.getMessageCount() > 0,
                "TC-INBOX-002 Failed: No email available in Inbox."
        );

        mailBoxPage.selectFirstEmail();

        mailBoxPage.toggleStar();

        navPage.goToStarred();
        DriverFactory.getDriver().navigate().refresh();
        Assert.assertTrue(
                mailBoxPage.getMessageCount() > 0,
                "TC-INBOX-002 Failed: Starred message not found."
        );

        mailBoxPage.selectFirstEmail();

        mailBoxPage.toggleStar();
    }

    @Test(priority = 3)
    public void testArchiveMessage() {

        MailBoxPage mailBoxPage =
                new MailBoxPage();

        NavigationPage navPage =
                new NavigationPage();

        Assert.assertTrue(
                mailBoxPage.getMessageCount() > 0,
                "TC-INBOX-003 Failed: No email available in Inbox."
        );

        mailBoxPage.selectFirstEmail();

        mailBoxPage.archiveEmail();

        navPage.goToArchive();

        Assert.assertTrue(
                mailBoxPage.getMessageCount() > 0,
                "TC-INBOX-003 Failed: Archived message not found."
        );
    }
}