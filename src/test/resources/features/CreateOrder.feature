@E2E @OrderManagement @Regression @P1
Feature: End-to-End E-Commerce Order Lifecycle Automation
  As a registered user of the Ecommerce application
  I want to search for products, manage cart items, complete checkout with shipping details, and process Stripe payment
  So that I can successfully place an order, verify order confirmation, track it in my order history, and ensure the shopping cart resets.

  Background:
    When User enters valid username and password
    Then User should be logged in

  @E2E_Order_001 @Smoke @StripePayment @OrderManagement
  Scenario Outline: Verify End-to-End Order Creation, Stripe Payment, Order History Tracking, and Cart Reset
    And User search product from the "<SheetName>" and "<RowNumber>"
    And User adds product to cart and proceeds to checkout
    And User fills shipping details with name "<FullName>", country "<Country>", state "<State>", city "<City>", pincode "<Pincode>", phone "<Phone>", address "<Address>"
    And User completes Stripe payment using card "<CardNumber>", expiry "<Expiry>", cvc "<CVC>"
    Then User verifies order details in My Orders history
    And User clicks on Cart and verifies that the cart is empty

    Examples:
      | SheetName | RowNumber | FullName | Country | State       | City   | Pincode | Phone      | Address                      | CardNumber       | Expiry | CVC |
      | Sheet1    | 1         | John Doe | India   | Maharashtra | Mumbai | 400001  | 9876543210 | 123, Park Avenue, Bandra West | 4242424242424242 | 12/34  | 123 |