package com.proton.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ComposePage extends BasePage {

    private final By composeButton =
            By.cssSelector("button[data-testid='sidebar:compose']");

    private final By recipientInput =
            By.cssSelector("input[data-testid='composer:to']");

    private final By ccButton =
            By.xpath("//button[@data-testid='composer:recipients:cc-button']");

    private final By ccInput =
            By.cssSelector("input[data-testid='composer:to-cc']");

    private final By bccButton =
            By.xpath("//button[@data-testid='composer:recipients:bcc-button']");

    private final By bccInput =
            By.cssSelector("input[data-testid='composer:to-bcc']");

    private final By subjectInput =
            By.cssSelector("input[data-testid='composer:subject']");

    private final By bodyIframe =
            By.cssSelector("iframe[data-testid='rooster-iframe']");

    private final By bodyEditor =
            By.cssSelector("[contenteditable='true']");

    private final By sendButton =
            By.cssSelector("button[data-testid='composer:send-button']");

    private final By closeButton =
            By.cssSelector("button[data-testid='composer:close-button']");

    private final By deleteDraftButton =
            By.cssSelector("button[data-testid='composer:delete-draft-button']");

    private final By confirmDeleteButton =
            By.cssSelector("footer button[type='submit']");

    private final By undoButton = By.cssSelector("button[data-testid='notification:undo-button']");



    private final By fileInput =
            By.cssSelector("input[type='file'][data-testid='composer:attachment-input']");

    // XPath targeting the modal title h1 element containing 'Sending error'
    private final By recipientError =
            By.xpath("//div[contains(@class,'modal-two-dialog-container')]//h1[text()='Sending error']");

    private final By composerContainer =
            By.cssSelector("div[data-testid='composer']");

    private final By notificationToast =
            By.cssSelector("div[role='alert'], .notification");

    public void openComposeWindow() {
        if (!isComposeOpen()) {
            click(composeButton);
        }
        waitForComposeWindow();
    }

    public void waitForComposeWindow() {
        waitForElementToBeVisible(subjectInput);
    }

    public void enterRecipient(String recipient) {
        sendKeys(recipientInput, recipient);
        try {
            WebElement element = waitForElementToBeVisible(recipientInput);
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            log.debug("Recipient ENTER was not required.");
        }
    }

    public void enterCC(String cc) {
        click(ccButton);
        sendKeys(ccInput, cc);
        try {
            waitForElementToBeVisible(ccInput).sendKeys(Keys.ENTER);
        } catch (Exception e) {
            log.debug("CC ENTER was not required.");
        }
    }

    public void enterBCC(String bcc) {
        click(bccButton);
        sendKeys(bccInput, bcc);
        try {
            waitForElementToBeVisible(bccInput).sendKeys(Keys.ENTER);
        } catch (Exception e) {
            log.debug("BCC ENTER was not required.");
        }
    }

    public void enterSubject(String subject) {
        sendKeys(subjectInput, subject);
    }

    public void enterBody(String body) {
        try {
            if (driver.findElements(bodyIframe).size() > 0) {
                switchToIframe(bodyIframe);
                WebElement editor = waitForElementToBeVisible(bodyEditor);
                editor.click();
                editor.sendKeys(body);
                switchToDefaultContent();
                return;
            }
        } catch (Exception e) {
            log.debug("Iframe editor was not usable. Trying normal contenteditable editor.");
            switchToDefaultContent();
        }

        WebElement editor = waitForElementToBeVisible(bodyEditor);
        editor.click();
        editor.sendKeys(body);
    }

    public void uploadAttachment(String filePath) {
        By fileInput = By.cssSelector("input[data-testid='composer-attachments-button']");

        // Find the hidden input element directly and send absolute path
        WebElement uploadElement = driver.findElement(fileInput);
        uploadElement.sendKeys(filePath);
    }

    public void clickSend() {
        click(sendButton);
        waitForSendCompletion();
    }

    public void waitForSendCompletion() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(sendButton));
        } catch (TimeoutException e) {
            log.debug("Send button did not disappear within wait period.");
        }
    }

    public void clickUndo() {
        // Wait for the dynamic notification toast to appear and click it immediately
        waitForElementToBeVisible(undoButton);
        click(undoButton);
    }

    public void closeCompose() {
        click(closeButton);
    }

    public void discardDraft() {
        click(deleteDraftButton);
        click(confirmDeleteButton);
    }

    public boolean isRecipientErrorDisplayed() {
        return isDisplayed(recipientError);
    }

    public String getRecipientErrorText() {
        return getText(recipientError);
    }

    public boolean isComposeOpen() {
        return isDisplayed(subjectInput);
    }

    public boolean isComposeWindowClosed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(composerContainer))
                    || isDisplayed(notificationToast);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForComposeToClose() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(composerContainer));
        } catch (Exception e) {
            return false;
        }
    }
}