Feature: Non Functional Requirements
  Scenario: (Performance) Hit logic must be performend in under 2 seconds.
    Given An empty board.
    When The user hits a random positions in the board:
    """
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 X 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    Then The returned value should be returned in under 2 seconds.


  Scenario: (Performance) The returned mark of a position must be performend in under 2 seconds.
    Given An empty board.
    When The user hits a random positions in the board and asks for its mark:
    """
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 X 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    Then The returned value should be returned in under 2 seconds.

  Scenario: (Performance) The neighbour check must be performend in under 2 seconds.
    Given An empty board.
    When The user hits a random positions in the board:
    """
    0 0 0 0 0 0 0 0 0 0
    0 1 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 2 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    And Cheks if two positions are neighbours
    Then The returned value should be returned in under 2 seconds.


  Scenario Outline: (Performance) Given a table with different dimensions, from a 2x2 up to a 100x100, the returned mark
    of a position must be done in under two seconds.
    Given An empty board of dimension=<dimension>
    When The user hits the position(0, 0) and asks for its mark
    Then The returned value should be returned in under 2 seconds.
    Examples:
      | dimension |
      | 2         |
      | 5         |
      | 10        |
      | 50        |
      | 100       |

  Scenario: (Security) A user hits a random position. The system checks if the position is valid before doing the logic.
    Given An empty board.
    When The user hits the position(0, 0)
    Then The system checks for the validity of the input position.

   Scenario: (Reliability) The system must handle invalid input gracefully without crashing
     Given An empty board of dimension=5
     When The user hits the invalid position(-1, 1000)
     Then The system returns an error message without crashing

