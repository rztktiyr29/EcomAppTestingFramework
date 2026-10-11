package com.sdet.framework.pages;

import com.sdet.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateOrderE2E extends BasePage {

    private final By searchBar =
            By.xpath("//input[@placeholder='Search Products...']");

    private final By productBox =
            By.xpath("//a[@href='/product/68d6c8d3dafe0376b64871b5']");

    private final By addToCartBtn =
            By.xpath("//button[contains(text(), 'Buy Now')]/following-sibling::button");

    private final By cartBtn =
            By.xpath("//a[@href='/cart']");

    private final By increaseBtn =
            By.xpath("//button[text()='Increase(+)']");

    private final By decreaseBtn =
            By.xpath("//button[text()='Decrease(-)']");

    private final By checkoutBtn =
            By.xpath("//button[text()='Check Out']");

    private final By nameInput =
            By.xpath("//input[@name='fullName']");

    private final By countryInput =
            By.xpath("//input[@name='country']");

    private final By stateInput =
            By.xpath("//input[@name='state']");

    private final By cityInput =
            By.xpath("//input[@name='city']");

    private final By pincodeInput =
            By.xpath("//input[@name='pincode']");

    private final By phoneNumberInput =
            By.xpath("//input[@name='phoneNumber']");

    private final By addressTextArea =
            By.xpath("//textarea[@name='address']");

    private final By submitBtn =
            By.xpath("//button[text()='Submit']");

    private final By proceedToPaymentBtn =
            By.xpath("//button[normalize-space()='Proceed to Payment']");

    private final By emailInput = By.id("email");

    // --- Order Confirmation Locators ---
    private final By orderIdTxt =
            By.xpath("//p[contains(., 'Order ID:')]");
    private final By paymentIdTxt =
            By.xpath("//p[contains(., 'Payment ID:')]");
    private final By statusTxt =
            By.xpath("//p[contains(., 'Status:')]");
    private final By amountTxt =
            By.xpath("//p[contains(., 'Amount:')]");
    private final By orderConfirmedHeading =
            By.xpath("//h2[contains(., 'Order Confirmed')]");
    private final By thankYouMessage =
            By.xpath("//p[normalize-space()='Thank you for your purchase!']");

    // --- Order History Locators ---
    private final By myOrdersNavBtn =
            By.xpath("//a[@href='/orderhistory' or normalize-space()='My Orders']");
    private final By allOrderHistoryIds =
            By.xpath("//h2[normalize-space()='My Orders']/following-sibling::div//h3[contains(text(), 'Order ID:')]");
    // --- Empty Cart Locators ---
    private final By emptyCartMessage =
            By.xpath("//p[normalize-space()='Cart is empty']");
    private final By continueShoppingBtn =
            By.xpath("//button[normalize-space()='Continue shopping....']");
    private final By priceElement = By.xpath("//h2[contains(@class, 'text-3xl font-bold')]");

    public CreateOrderE2E() {
        super();
    }

    /**
     * Enters product name into search bar and presses Enter.
     */
    public void searchProduct(String productName) throws InterruptedException {
        Thread.sleep(3000);
        sendKeys(searchBar, productName);
        DriverFactory.getDriver().findElement(searchBar).sendKeys(Keys.ENTER);
    }

    public void clickProductView() {
        click(productBox);
    }

    public void clickAddToCart() {
        click(addToCartBtn);
    }

    public void clickCart() {
        click(cartBtn);
    }

    public void clickIncreaseBtn() {
        click(increaseBtn);
    }

    public void clickDecreaseBtn() {
        click(decreaseBtn);
    }

    public void clickCheckoutBtn() {
        click(checkoutBtn);
    }

    // All method getting called in this one method
    public void addProduct(String productName) throws InterruptedException {
        searchProduct(productName);
        clickProductView();
        clickAddToCart();
        clickCart();
        clickIncreaseBtn();
        clickDecreaseBtn();
        clickCheckoutBtn();
    }

    public void enterFullName(String name) {
        sendKeys(nameInput, name);
    }

    public void enterCountry(String country) {
        sendKeys(countryInput, country);
    }

    public void enterState(String state) {
        sendKeys(stateInput, state);
    }

    public void enterCity(String city) {
        sendKeys(cityInput, city);
    }

    public void enterPincode(String pincode) {
        sendKeys(pincodeInput, pincode);
    }

    public void enterPhoneNumber(String phone) {
        sendKeys(phoneNumberInput, phone);
    }

    public void enterAddress(String address) {
        sendKeys(addressTextArea, address);
    }

    public void clickSubmit() {
        click(submitBtn);
    }

    public void fillAddressDetails(String fullName, String country, String state,
                                   String city, String pincode, String phone, String address) {
        enterFullName(fullName);
        enterCountry(country);
        enterState(state);
        enterCity(city);
        enterPincode(pincode);
        enterPhoneNumber(phone);
        enterAddress(address);
        clickSubmit();
    }

    public void clickProceedToPayment() {
        click(proceedToPaymentBtn);
    }

    // --- Order Confirmation Methods ---

    public String getOrderId() {
        return getText(orderIdTxt).replace("Order ID:", "").trim();
    }

    public String getPaymentId() {
        return getText(paymentIdTxt).replace("Payment ID:", "").trim();
    }

    public String getStatus() {
        return getText(statusTxt).replace("Status:", "").trim();
    }

    public String getAmount() {
        return getText(amountTxt).replace("Amount:", "").trim();
    }

    /**
     * Extracts and prints all confirmation details to console/logs.
     */
    public void printOrderSummary() {
        System.out.println("====== ORDER CONFIRMATION DETAILS ======");
        System.out.println("Order ID   : " + getOrderId());
        System.out.println("Payment ID : " + getPaymentId());
        System.out.println("Status     : " + getStatus());
        System.out.println("Amount     : " + getAmount());
        System.out.println("========================================");
    }

    public String getOrderConfirmedHeadingText() {
        return getText(orderConfirmedHeading).replace("✅", "").trim();
    }

    public String getThankYouMessageText() {
        return getText(thankYouMessage);
    }

    public boolean isOrderConfirmedDisplayed() {
        return isDisplayed(orderConfirmedHeading);
    }

    /**
     * Asserts the confirmation heading and thank-you text using TestNG Assertions.
     */
    public void verifyOrderConfirmation() {
        Assert.assertTrue(isOrderConfirmedDisplayed(),
                "Order Confirmed heading is not displayed on the UI!");

        Assert.assertEquals(getOrderConfirmedHeadingText(), "Order Confirmed",
                "Order Confirmed heading text mismatch!");

        Assert.assertEquals(getThankYouMessageText(), "Thank you for your purchase!",
                "Thank you message text mismatch!");
    }

    public void clickMyOrders() {
        click(myOrdersNavBtn);
    }

    /**
     * Fetches all Order IDs displayed on the My Orders page into a list of Strings.
     */
    public List<String> getAllOrderHistoryIds() {
        List<WebElement> elements = DriverFactory.getDriver().findElements(allOrderHistoryIds);
        return elements.stream()
                .map(element -> element.getText().replace("Order ID:", "").trim())
                .collect(Collectors.toList());
    }

    /**
     * Asserts that the expected Order ID exists anywhere within the My Orders history list.
     */
    public void assertOrderIdInHistory(String expectedOrderId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("Expected Order ID (Confirmation Page) : " + expectedOrderId);

        // Wait until at least one order element/card is present on the order history page
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(allOrderHistoryIds));
        } catch (Exception e) {
            System.out.println("Timed out waiting for order history elements to appear.");
        }

        // Retry mechanism with fluent waiting to handle delayed API rendering
        List<String> foundOrderIds = new ArrayList<>();
        boolean isFound = false;

        for (int attempt = 0; attempt < 5; attempt++) {
            foundOrderIds.clear();
            List<WebElement> orderElements = driver.findElements(allOrderHistoryIds);

            for (WebElement elem : orderElements) {
                String text = elem.getText().trim();
                if (text.contains(expectedOrderId)) {
                    isFound = true;
                    break;
                }
                foundOrderIds.add(text);
            }

            if (isFound) {
                break;
            }

            try {
                Thread.sleep(2000); // Wait 2 seconds before checking DOM again
            } catch (InterruptedException ignored) {}
        }

        System.out.println("All Found Order IDs in History : " + foundOrderIds);

        Assert.assertTrue(
                isFound,
                "Created Order ID '" + expectedOrderId + "' was NOT found in the My Orders history list! Available items: " + foundOrderIds
        );
    }
    // --- Empty Cart Action & Assertion Methods ---

    /**
     * Gets the text displayed when the cart is empty.
     */
    public String getEmptyCartMessageText() {
        return getText(emptyCartMessage);
    }

    /**
     * Checks if the 'Cart is empty' text element is visible on the DOM.
     */
    public boolean isEmptyCartMessageDisplayed() {
        return isDisplayed(emptyCartMessage);
    }

    /**
     * Navigates to the Cart page and asserts that the cart is completely empty post-purchase.
     */
    public void verifyCartIsEmpty() throws InterruptedException {
        pause(3);
        clickCart(); // Clicks the 'Cart' navigation button in top navbar

        Assert.assertTrue(isEmptyCartMessageDisplayed(),
                "'Cart is empty' message is not displayed on the screen!");

        Assert.assertEquals(getEmptyCartMessageText(), "Cart is empty",
                "Empty cart message text mismatch!");

        Assert.assertTrue(isDisplayed(continueShoppingBtn),
                "'Continue shopping....' button is not displayed on the empty cart page!");
    }
    public String getProductPrice() {
        // Selenium will look for the element ONLY when this method is called during the test step
        return driver.findElement(priceElement).getText().replace("$", "").trim();
    }
}