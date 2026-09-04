package com.proton.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MailBoxPage extends BasePage {

    private final By searchInput =
            By.cssSelector("input[data-testid='input-input-element']");

    private final By searchSubmit =
            By.cssSelector("button[data-testid='header:search-button']");


    private final By firstEmailItem = By.xpath("(//label[@class='item-checkbox-label relative mr-3'])[1]");
    private final By markReadButton = By.cssSelector("button[data-testid='toolbar:read']");
    private final By messageItems = By.cssSelector("div[data-testid^='message-item:']");
    private final By starIconButton = By.xpath("(//button[@data-testid='item-star-true' or @data-testid='item-star-false'])[1]");
    private final By archiveButton =
            By.cssSelector("button[data-testid='toolbar:movetoarchive']");

    private final By noResultsMessage =
            By.cssSelector("div[data-testid='empty-folder:text']");

    private final By addFilterButton =
            By.cssSelector("button[data-testid='settings:add-filter']");

    private final By filterNameInput =
            By.id("filter-name");

    private final By filterConditionSubjectInput =
            By.id("condition-subject");

    private final By filterSaveButton =
            By.cssSelector("button[data-testid='filter:save']");

    private final By moreButton = By.cssSelector("button[data-shortcut-target='toggle-more-items']");
    public void search(String keyword) {

        sendKeys(searchInput, keyword);

        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER);

        waitForSearchResult();
    }

    private void waitForSearchResult() {

        try {

            wait.until(driver ->
                    driver.findElements(firstEmailItem).size() > 0
                            || driver.findElements(noResultsMessage).size() > 0
            );

        } catch (TimeoutException e) {

            log.warn("Search result state was not detected.");
        }
    }

    public boolean isNoResultsDisplayed() {

        return isDisplayed(noResultsMessage);
    }



    public void toggleStar() {

        click(starIconButton);
    }

    public void archiveEmail() {

        click(archiveButton);
    }



    public boolean isEmailPresentWithSubject(String subject) {

        if (subject == null || subject.trim().isEmpty()) {
            return false;
        }

        String safeSubject = escapeXPath(subject);

        By subjectLocator =
                By.xpath(
                        "//*[contains(normalize-space(.), " +
                                safeSubject +
                                ")]"
                );

        try {

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            subjectLocator
                    )
            );

            return true;

        } catch (TimeoutException e) {

            return false;
        }
    }

    public boolean isMessagePresentWithWait(String subject) {
        By subjectLocator = By.xpath("//*[contains(@data-testid, 'message-item') or contains(@class, 'item-container')]//*[text()='" + subject + "']");
        try {
            return waitForElementToBeVisible(subjectLocator) != null;
        } catch (Exception e) {
            return false;
        }
    }
    public void openFirstDraft() {
        By firstItem = By.cssSelector("div[data-testid^='message-item:']");
        click(firstItem);
    }
    public void createSubjectFilter(String subjectKeyword) {

        click(addFilterButton);

        sendKeys(
                filterNameInput,
                "AutoFilter-" + subjectKeyword
        );

        sendKeys(
                filterConditionSubjectInput,
                subjectKeyword
        );

        click(filterSaveButton);
    }




    public int getMessageCount() {
        try {

            waitForElementToBeVisible(messageItems);
            return driver.findElements(messageItems).size();
        } catch (Exception e) {
            // Return 0 if no messages load within the explicit wait duration
            return 0;
        }
    }

    private String escapeXPath(String value) {

        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        String[] parts = value.split("'");

        StringBuilder result = new StringBuilder("concat(");

        for (int i = 0; i < parts.length; i++) {

            if (i > 0) {
                result.append(", \"'\", ");
            }

            result.append("'")
                    .append(parts[i])
                    .append("'");
        }

        result.append(")");

        return result.toString();
    }
    public void selectFirstEmail() {
        // Wait until the input element is present in DOM and dispatch a native JS click event
        WebElement checkbox = waitForElementToBeVisible(firstEmailItem);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    }

    public void markAsRead() {
        click(markReadButton);
    }
}