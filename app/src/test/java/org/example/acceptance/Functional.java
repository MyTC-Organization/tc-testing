package org.example.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.acceptance.util.Parser;
import org.example.logic.LoggerImpl;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.logic.MyLogger;
import org.example.utils.Position;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class Functional {
    private Logic logic;
    private Optional<Integer> lastOutput;
    private Position lastHit;
    private final int size = 10;
    final MyLogger logger = new LoggerImpl();

    public Functional() {
        this.logic = new LogicImpl(size, logger);
    }

    private void clearAndCreate() {
        this.logic = new LogicImpl(size, logger);
    }


    @When("The user hits the position at x=0, y=0")
    @Then("The returned value should be one")
    public void theReturnedValueShouldBeOne() {
        this.clearAndCreate();
        this.lastOutput = this.logic.hit(new Position(0, 0));
        assertFalse(this.lastOutput.isEmpty());
        final int value = this.lastOutput.get();
        assertEquals(1, value);
    }

    @Then("The returned value should be two")
    public void theReturnedValueShouldBeTwo() {
        System.out.println(this.lastOutput);
        if (this.lastOutput.isEmpty()) {
            fail();
        }
        final int value = this.lastOutput.get();
        assertEquals(2, value);
    }

    @Given("The board is empty")
    public void theBoardIsEmpty() {
        this.clearAndCreate();
    }

    @Then("The game is over")
    public void theGameIsOver() {
        assertTrue(this.logic.isOver());
    }

    @When("The user hits a random cell at x={int}, y={int}")
    public void theUserHitsARandomCellAtXY(int arg0, int arg1) {
        this.lastHit = new Position(arg0, arg1);
        this.logic.hit(this.lastHit);
    }

    @Then("all {int} adjacent cells are identified as neighbors")
    public void allAdjacentCellsAreIdentifiedAsNeighbors(int arg0) {
        final List<Position> directions = List.of(new Position(0, 0), new Position(0, 1),
                new Position(1, 0), new Position(1, 1), new Position(0, -1), new Position(1, -1),
                new Position(-1, 0), new Position(-1, 1));
        assertTrue(directions.stream().allMatch(direction ->
                        this.logic.areNeighbours(new Position(this.lastHit.x() + direction.x(),
                                this.lastHit.y() + direction.y()), this.lastHit)));
    }

    @When("The user hits a random cell in the board:")
    public void theUserHitsARandomCellAt(String board) {
        this.lastHit = Parser.parseBoard(board, (log, pos) -> {
            this.lastOutput = this.logic.hit(pos);
        }, this.logic);
    }

    @When("The user hits two random positions in the board:")
    public void theUserHitsTwoRandomPositionsInTheBoard(String board) {
        this.lastHit = Parser.parseBoard(board, (log, pos) -> {
            this.lastOutput = this.logic.hit(pos);
        }, this.logic);
    }

    @When("The user hits two random cell in the board which are neighbouring:")
    public void theUserHitsTwoRandomCellInTheBoardWhichAreNeighbouring(String board) {
        this.lastHit = Parser.parseBoard(board, (log, pos) -> {
            this.lastOutput = this.logic.hit(pos);
        }, this.logic);
    }

    @Then("The hitted positions start moving and the result board should be like this:")
    public void theHittedPositionsStartMovingAndTheResultBoardShouldBeLikeThis(String board) {
        final int nRows = board.split("\n").length;
        final String[] rows = board.split("\n");
        for (int row = 0; row < nRows; row++) {
            final String line = rows[row];
            final String[] cols = line.split(" ");
            final int nCols = line.split(" ").length;
            for (int col = 0; col < nCols; col++) {
                final String cell = cols[col];
                if (cell.equals("X")) {
                    if(this.logic.getMark(new Position(row, col)).isPresent()) {
                        fail();
                    }
                    break;
                }
            }
        }
        assertTrue(this.lastOutput.isEmpty());
    }

    @When("The user hits two random cell in the board which are neighbouring and close to the boarder \\(distance 0):")
    public void theUserHitsTwoRandomCellInTheBoardWhichAreNeighbouringAndCloseToTheBoarderDistance(String board) {
        this.lastHit = Parser.parseBoard(board,
                (log, pos) -> {this.lastOutput = this.logic.hit(pos);}
                ,this.logic);
    }

    @Then("Every hit will move the point number 1 until it reaches the out of the board and the game ends.")
    public void everyHitWillMoveThePointNumberUntileItReachesTheOutOfTheBoard() {
        while(!this.logic.isOver()) {
            this.randomHit();
        }
    }

    private void randomHit() {
        for(int row = 0; row < this.size; row++) {
            for(int col = 0; col < this.size; col++) {
                final Position pos = new Position(row, col);
                if (this.logic.getMark(pos).isEmpty()) {
                    this.logic.hit(pos);
                }
            }
        }
    }

    @When("The user hits non neighbouring positions in the board:")
    public void theUserHitsNonNeighbouringPositionsInTheBoard(String board) {
        this.lastHit = Parser.parseBoard(board, (log, pos) -> {
            this.lastOutput = this.logic.hit(pos);
        }, this.logic);
    }

    @Then("The hits do not move and the game is not over yet. The following board should look like the input board.")
    public void theHitsDoNotMoveAndTheGameIsNotOverYet(String board) {
        assertTrue(this.lastOutput.isPresent());
        assertFalse(this.logic.isOver());
        this.lastHit = Parser.parseBoard(board, (log, pos) -> {
            if (log.getMark(pos).isEmpty()) {
                fail();
            }
        }, this.logic);
    }
}
