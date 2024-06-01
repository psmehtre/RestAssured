Feature: Verify FanCode city users' todos completion

  Scenario: Verify users in FanCode city have more than half of their todos completed
    Given I fetch all users
    And I fetch all todos
    When I filter users in FanCode city
    Then I verify each user has more than half of their todos completed
