package com.sdet.framework.stepdef;

import com.sdet.framework.pages.BasePage;
import com.sdet.framework.pages.CreateOrderE2E;
import com.sdet.framework.pages.StripeCheckoutPage;
import com.sdet.framework.utils.ExcelUtility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class CreateOrderStepDefs extends BasePage {

    private final CreateOrderE2E createOrderPage = new CreateOrderE2E();
    private final StripeCheckoutPage stripePage = new StripeCheckoutPage();
    private static String createdOrderId;

    @Then("User search product from the {string} and {string}")
    public void userSearchProductFromTheAnd(String sheetName, String rowNumber) throws InterruptedException {
        int rowNum = Integer.parseInt(rowNumber);
        String productName = ExcelUtility.getData(sheetName, "ProductName", rowNum);

        Assert.assertFalse(
                productName.isEmpty(),
                "Failed to fetch product name from sheet '" + sheetName + "' at row " + rowNum
        );

        createOrderPage.searchProduct(productName);
    }

    @And("User adds product to cart and proceeds to checkout")
    public void userAddsProductToCartAndProceedsToCheckout() throws InterruptedException {
        pause(2);
        createOrderPage.clickProductView();
        pause(2);
        createOrderPage.clickAddToCart();
        pause(2);
        createOrderPage.clickCart();
        pause(2);
        createOrderPage.clickIncreaseBtn();
        pause(2);
        createOrderPage.clickDecreaseBtn();
        pause(2);
        createOrderPage.clickCheckoutBtn();
    }

    @And("User fills shipping details with name {string}, country {string}, state {string}, city {string}, pincode {string}, phone {string}, address {string}")
    public void userFillsShippingDetails(String fullName, String country, String state,
                                         String city, String pincode, String phone, String address) {
        createOrderPage.fillAddressDetails(fullName, country, state, city, pincode, phone, address);
        createOrderPage.clickProceedToPayment();
    }

    @And("User completes Stripe payment using card {string}, expiry {string}, cvc {string}")
    public void userCompletesStripePaymentUsingCardExpiryCvc(String cardNumber, String expiry, String cvc) {
        // Submit Stripe payment details
        stripePage.completeStripePayment(cardNumber, expiry, cvc, "John Doe");

        // Verify order confirmation UI and capture generated Order ID
        createOrderPage.verifyOrderConfirmation();
        createOrderPage.printOrderSummary();
        createdOrderId = createOrderPage.getOrderId();

    }

    @Then("User verifies order details in My Orders history")
    public void userVerifiesOrderDetailsInMyOrdersHistory() throws InterruptedException {
        pause(5);
        createOrderPage.clickMyOrders();
        createOrderPage.assertOrderIdInHistory(createdOrderId);
    }

    @And("User clicks on Cart and verifies that the cart is empty")
    public void userClicksOnCartAndVerifiesThatTheCartIsEmpty() throws InterruptedException {
        createOrderPage.verifyCartIsEmpty();
    }
}