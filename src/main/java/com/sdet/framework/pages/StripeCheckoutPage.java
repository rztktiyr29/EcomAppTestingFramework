package com.sdet.framework.pages;

import org.openqa.selenium.By;

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
        sendKeys(emailInput, "abc@xyz.com");
        pause(1);
        sendKeys(cardNumberInput, cardNumber);
        pause(1);
        sendKeys(cardExpiryInput, expiryDate);
        pause(1);
        sendKeys(cardCvcInput, cvc);
        pause(1);
        if (isDisplayed(billingNameInput)) {
            sendKeys(billingNameInput, nameOnCard);
        }
        pause(1);
        click(payButton);
    }
}