@ui
Feature: User Login Functionality
  As a registered user
  I want to log in with my credentials
  So that I can access my email inbox safely

  @smoke @positive @login
  Scenario: Successful login with valid credentials
    When User enters valid username and password
    Then User should be logged in

#  @negative
#  Scenario Outline: Unsuccessful login with invalid credentials
#    When User enters username "<username>" and password "<password>"
#    Then An error message should be displayed
#
#    Examples:
#      | username        | password         |
#      | invalid_user1   | wrongpassword123 |
#      | testuser@pm.me  | invalidpass      |