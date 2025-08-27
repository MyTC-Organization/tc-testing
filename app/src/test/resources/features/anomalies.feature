Feature: Logic Interaction with edge values.
  Scenario: The user interacts with a board and because of some bug he hits a position outside the grid.
    Given A board with an edge of 10.
    When The user hits a position outside from the board.
    Then The system must handle this problem and avoid to save the input position.


  Scenario: The user hits the same position twice
    Given A board with an edge of 10.
    And The player hits at position (1, 1)
    When The player hits position (1, 1) again
    Then The system does not hit again and informs the user regarding this problem


  Scenario: Get mark from a position that was never hit
    Given A board with an edge of 10.
    When The player checks position (2, 2)
    Then The system returns no mark found

  Scenario: A player hits maximum valid position
    Given A board with an edge of 10.
    When The player hits position (9, 9)
    Then The hit is valid
