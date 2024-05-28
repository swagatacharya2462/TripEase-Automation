Feature: HomePage

  @S1
  Scenario: Flight Booking
    Given I am on MakeMyTrip Home Page.
    When Fill all the user requirement and click on search
    Then Print the minimum fare after discount.