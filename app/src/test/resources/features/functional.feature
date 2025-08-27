Feature: Logic Interaction

  Scenario: The user interacts with 10x10 board.
    Given The board is empty
    When The user hits the position at x=0, y=0
    Then The returned value should be one

  Scenario: The user interacts with 10x10 board and hits two random positions.
    Given The board is empty
    When The user hits two random positions in the board:
    """
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 X 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 X 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    Then The returned value should be two

  Scenario: The user interacts with 10x10 board, hits a random positions and finds out which are the neighbouring positions of a hit.
    Given The board is empty
    When The user hits a random cell in the board:
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
    Then all 8 adjacent cells are identified as neighbors

  Scenario: The user interacts with 10x10 board, hits two neighbouring positions and finds out that the positions start moving.
    Given The board is empty
    When The user hits two random cell in the board which are neighbouring:
    """
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 1 0 0 0 0 0
    0 0 0 0 0 2 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    Then The hitted positions start moving and the result board should be like this:
    """
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 1 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """

  Scenario: The user interacts with 10x10 board, moves a hitted positions outside the border and then the game is over.
    Given The board is empty
    When The user hits two random cell in the board which are neighbouring and close to the boarder (distance 0):
    """
    1 0 0 0 0 0 0 0 0 0
    0 2 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    Then The game is over


  Scenario: The user interacts with 10x10 board and hits two neighbouring positions, the game is not over untile they reach a position out of the border.
    Given The board is empty
    When The user hits two random cell in the board which are neighbouring:
    """
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 1 0 0 0 0 0
    0 0 0 0 0 2 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    0 0 0 0 0 0 0 0 0 0
    """
    Then Every hit will move the point number 1 until it reaches the out of the board and the game ends.

    Scenario: The user interacts with 10x10 board and hits positions that are not neighbours.
      Given The board is empty
      When The user hits non neighbouring positions in the board:
      """
      1 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 4 0 0
      0 0 0 0 0 0 0 0 0 0
      8 0 0 0 0 0 0 0 0 6
      0 0 0 0 2 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 9 0 0 0 0 0 0 0 0
      0 0 0 3 0 0 0 0 0 0
      0 0 0 0 0 0 0 5 0 0
      0 7 0 0 0 0 0 0 0 0
      """
      Then The hits do not move and the game is not over yet. The following board should look like the input board.
      """
      1 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 4 0 0
      0 0 0 0 0 0 0 0 0 0
      8 0 0 0 0 0 0 0 0 6
      0 0 0 0 2 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 9 0 0 0 0 0 0 0 0
      0 0 0 3 0 0 0 0 0 0
      0 0 0 0 0 0 0 5 0 0
      0 7 0 0 0 0 0 0 0 0
      """
