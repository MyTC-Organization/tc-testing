package org.example.acceptance;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.acceptance.util.Parser;
import org.example.logic.LoggerImpl;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.utils.Position;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.fail;

public class NonFunctional {
    private Logic myLogic;
    private static final int SIZE = 10;
    private Optional<Integer> myReturnedValue;
    private long elapsedTime = 0;
    private Position myLastHit;
    private Exception myThrownException = null;

    private void initLogic(final int size) {
        this.myLogic =  new LogicImpl(size, new LoggerImpl());
        this.elapsedTime = 0;
        this.myLastHit = null;
        this.myReturnedValue = Optional.empty();
        this.myThrownException = null;
    }

    @Given("An empty board.")
    public void anEmptyBoard() {
        this.initLogic(SIZE);
    }

    @When("The user hits a random positions in the board:")
    public void theUserHitsARandomPositionsInTheBoard(final String board) {
        this.myLastHit = Parser.parseBoard(board, (logic, position) -> {
            final long currentTime = System.currentTimeMillis();
            logic.hit(position);
            this.elapsedTime = System.currentTimeMillis() - currentTime;
        }, this.myLogic);
    }


    @Then("The returned value should be returned in under {int} seconds.")
    public void theReturnedValueShouldBeReturnedInUnderSeconds(final int time) {
        if (time > 0 && (this.elapsedTime / 1000) > time) {
            fail();
        }
    }

    @When("The user hits a random positions in the board and asks for its mark:")
    public void theUserHitsARandomPositionsInTheBoardAndAsksForItsMark(final String board) {
        this.myLastHit = Parser.parseBoard(board, Logic::hit, this.myLogic);
        final long currentTime = System.currentTimeMillis();
        this.myLogic.getMark(this.myLastHit);
        this.elapsedTime = System.currentTimeMillis() - currentTime;
    }

    @And("Cheks if two positions are neighbours")
    public void cheksIfTwoPositionsAreNeighbours() {
        final Position other = new Position(0, 0);
        final long currentTime = System.currentTimeMillis();
        this.myLogic.areNeighbours(this.myLastHit, other);
        this.elapsedTime = System.currentTimeMillis() - currentTime;
    }

    @When("The user hits the position\\({int}, {int})")
    public void theUserHitsARandomPosition(int x, int y) {
        final Position pos = new Position(x, y);
        try {
            this.myLogic.hit(pos);
        } catch (final Exception e) {
            this.myThrownException = e;
        }
    }

    @Then("The system checks for the validity of the input position.")
    public void theSystemChecksForTheValidityOfTheInputPosition() {
        if (this.myThrownException != null) {
            fail();
        }
    }


    @Given("An empty board of dimension={int}")
    public void anEmptyBoardOfDimension(int size) {
        this.initLogic(size);
    }

    @When("The user hits the position\\({int}, {int}) and asks for its mark")
    public void theUserHitsThePositionAndAsksForItsMark(int x, int y) {
        final Position pos = new Position(x, y);
        final long currentTime = System.currentTimeMillis();
        this.myLogic.getMark(pos);
        this.elapsedTime = System.currentTimeMillis() - currentTime;
    }

    @When("The user hits the invalid position\\({int}, {int})")
    public void theUserHitsTheInvalidPosition(int x, int y) {
        final Position pos = new Position(x, y);
        try {
            this.myLogic.hit(pos);
        } catch (Exception e) {
            this.myThrownException = e;
        }
    }

    @Then("The system returns an error message without crashing")
    public void theSystemReturnsAnErrorMessageWithoutCrashing() {
        if (this.myThrownException == null) {
            fail();
        }
    }
}
