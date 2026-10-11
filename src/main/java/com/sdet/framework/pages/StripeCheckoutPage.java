package com.sdet.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class StripeCheckoutPage extends BasePage {

    // Standard Stripe Checkout Form Locators
    private final By emailInput =
            By.xpath("//input[@id='email' or @name='email' or @type='email']");
    private final By cardNumberInput =
            By.xpath("//input[@id='cardNumber' or @name='cardnumber' or @autocomplete='cc-number']");
    private final By cardExpiryInput =
            By.xpath("//input[@id='cardExpiry' or @name='exp-date' or @autocomplete='cc-exp']");
    private final By cardCvcInput =
            By.xpath("//input[@id='cardCvc' or @name='cvc' or @autocomplete='cc-csc']");
    private final By billingNameInput =
            By.xpath("//input[@id='billingName' or @name='billingName']");
    private final By payButton =
            By.xpath("//button[contains(@class, 'SubmitButton') or contains(., 'Pay')]");

    public StripeCheckoutPage() {
        super();
    }

    /**
     * Fills Stripe test card details and submits payment.
     */
    public void completeStripePayment(String cardNumber, String expiryDate, String cvc, String nameOnCard) throws InterruptedException {

        // 1. Fill email first so Stripe initializes the card payment container
        sendKeys(emailInput, "abc@xyz.com");
        pause(2);

        // 2. Fill card details in proper sequence
        sendKeys(cardNumberInput, cardNumber);
        sendKeys(cardExpiryInput, expiryDate);
        sendKeys(cardCvcInput, cvc);

        // 3. Handle optional billing name field using WebElement lookup (not casting By)
        if (isDisplayed(billingNameInput)) {
            WebElement nameElement = waitForElementToBeVisible(billingNameInput);
            Actions action = new Actions(driver);
            action.moveToElement(nameElement)
                    .click()
                    .sendKeys(nameOnCard)
                    .build()
                    .perform();
        }

        // 4. Submit payment
        click(payButton);
    }
}