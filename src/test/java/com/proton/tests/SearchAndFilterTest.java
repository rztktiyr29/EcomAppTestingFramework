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

public class SearchAndFilterTest extends BaseTest {


    @Test(priority = 1)
    public void testKeywordSearch() {

        MailBoxPage mailBoxPage =
                new MailBoxPage();

        mailBoxPage.search("Proton");


        Assert.assertTrue(
                mailBoxPage.getMessageCount() > 0
                        || mailBoxPage.isNoResultsDisplayed(),
                "TC-SEARCH-001 Failed: Search result state was not displayed."
        );
    }

    @Test(priority = 2)
    public void testSearchNoResults() {

        MailBoxPage mailBoxPage =
                new MailBoxPage();

        mailBoxPage.search(
                "xyz123nonexistent987654"
        );

        Assert.assertTrue(
                mailBoxPage.isNoResultsDisplayed(),
                "TC-SEARCH-002 Failed: Empty search state not displayed."
        );
    }


}