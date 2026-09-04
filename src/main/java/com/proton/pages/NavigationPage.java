package com.proton.pages;

import org.openqa.selenium.By;

public class NavigationPage extends BasePage {

    private final By inboxLink =
            By.cssSelector("a[data-testid='navigation-link:inbox']");

    private final By draftsLink = By.xpath("//a[contains(@data-testid,'drafts')]//span[text()='Drafts']");

    private final By sentLink = By.xpath("//a[contains(@class,'navigation-link')]//span[text()='Sent']");

    private final By starredLink =
            By.cssSelector("a[data-testid='navigation-link:starred']");

    private final By archiveLink =
            By.cssSelector("a[data-testid='navigation-link:archive']");

    private final By settingsLink =
            By.cssSelector("a[data-testid='navigation-link:settings']");
    private final By moreButton = By.cssSelector("button[data-shortcut-target='toggle-more-items']");
    public void goToInbox() {

        click(inboxLink);
    }

    public void goToDrafts() {

        click(draftsLink);
    }

    public void goToSent() {

        click(sentLink);
    }

    public void goToStarred() {

        click(starredLink);
    }

    public void goToArchive() {
        click(moreButton);
        click(archiveLink);
    }

    public void goToSettings() {

        click(settingsLink);
    }
}