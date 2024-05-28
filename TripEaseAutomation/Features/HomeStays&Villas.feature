Feature: HomeStays & Villas Page

  @S2
  Scenario: Villa Booking
    Given I am on MakeMyTrip HomeStays&Villas Page
    When Fill all the requirements and click on search
    And Fill the required filters
    Then Print the results in descending orders according to ratings