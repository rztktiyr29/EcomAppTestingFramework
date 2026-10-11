Feature: E2E Product Price Update and Purchase Workflow

  @UpdateProductApi
  Scenario Outline: Admin updates product price dynamically for checkout
    Given admin logs in with admin credentials
    When admin updates product "<productId>" with a random price from allowed list: "<allowedPrices>"
    Then the product API update response code should be 200

    Examples:
      | productId               | allowedPrices           |
      | 68d189ca2e7cf0f23c2ba39f | 499.00, 999.00, 1499.00 |
#      | 678a12bc34de56789012346 | 199.00, 399.00, 699.00  |