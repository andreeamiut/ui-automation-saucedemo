Feature: Login
  As a buyer I want to login so I can see products

  Scenario: Valid login
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should see the products page

  Scenario Outline: Invalid login
    Given I am on the SauceDemo login page
    When I login with username "<user>" and password "<pass>"
    Then I should see an error "<error>"

    Examples:
      | user            | pass         | error                                              |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out.             |
      | standard_user   | wrong        | do not match any user in this service             |
