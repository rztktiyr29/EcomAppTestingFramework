Feature: End-to-End E-commerce Product Update and Checkout Verification
  @ui @E2E @UI_API
  Scenario Outline: Admin updates product via API and user validates price on UI
    Given admin logs in with admin credentials
    When admin updates product "<productId>" with a random price from allowed list: "<allowedPrices>"
    And the product price in MongoDB is updated to match the API update for "<productId>"
    When User enters valid username and password
    Then User should be logged in
    And user navigates to product details page for "<productId>"
    Then the product price on the UI should match the updated price from the API

    Examples:
      | productId               | allowedPrices           |
      | 68d189ca2e7cf0f23c2ba39f | 499.00, 999.00, 1499.00 |