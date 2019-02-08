@Login
Feature: I should be able to log in as different users to your application

  @Valid
  Scenario: Login as a valid user
    Given I am in your application url
    When I log in as a "Valid" user
    Then I should be on Home Page

  @Invalid
  Scenario: Login as an InValid user
    Given I am in your application url
    When I log in as a "Invalid" user
    Then I should be on Login Page

    @1000TNs
    Scenario: TO find out the range of BTNs which are portable or not
    Given I am in your application url
    When I log in as a "Valid" user
    Then i need to input range of NPA and NXX and search for 1000TNs
    And in TAI check for the range of BTNs is Portable or not
    Then copy the data sheet to shared directory
    Then I send TN details in an email
    
    @10000TNs
    Scenario: TO find out the range of BTNs which are portable or not
    Given I am in your application url
    When I log in as a "Valid" user
    Then i need to input range of NPA and NXX and search for 10000TNs
    And in TAI check for the range of BTNs is Portable or not
    Then copy the data sheet to shared directory
    Then I send TN details in an email
   