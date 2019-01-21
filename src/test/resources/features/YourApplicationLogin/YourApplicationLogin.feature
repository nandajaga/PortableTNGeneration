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

    @NPA_NXX
    Scenario: TO find out the range of BTNs which are portable or not
    Given I am in your application url
    When I log in as a "Valid" user
    Then i need to input range of NPA and NXX and search 
    And in TAI check for the range of BTNs is Portable or not
    Then I send TN details in an email
    
   