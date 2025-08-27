package org.example.acceptance;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.example.logic.LoggerImpl;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.logic.MyLogger;
import org.example.utils.Position;

import java.util.Optional;

import static org.junit.Assert.fail;

public class Anomalies {
    private Logic myLogic;
    private int size;
    private final MyLogger myLogger = new LoggerImpl();
    private @Nullable Exception throwedException = null;
    private Optional<Integer> myReturnedMark = Optional.empty();


    private void initialiseLogic(final int size) {
        this.size = size;
        this.myLogic = new LogicImpl(this.size, myLogger);
        this.throwedException = null;
        this.myReturnedMark = Optional.empty();
    }
    @Given("A board with an edge of {int}.")
    public void aBoardWithAnEdgeOf(final int arg0) {
        if (arg0 > 0) {
            this.initialiseLogic(arg0);
        } else {
            fail();
        }
    }

    @When("The user hits a position outside from the board.")
    public void theUserHitsAPositionOutsideFromTheBoard() {
        try {
            this.myLogic.hit(new Position(-1, 0));
        } catch (Exception e) {
            this.throwedException = e;
        }
    }

    @Then("The system must handle this problem and avoid to save the input position.")
    public void theSystemMustHandleThisProblemAndAvoidToSaveTheInputPosition() {
        if (this.throwedException == null) {
            fail();
            this.throwedException = null;
        }
    }

    @And("The player hits at position \\({int}, {int})")
    public void thePlayerHitsAtPosition(int arg0, int arg1) {
        this.myLogic.hit(new Position(arg0, arg1));
    }

    @When("The player hits position \\({int}, {int}) again")
    public void thePlayerHitsPositionAgain(int arg0, int arg1) {
        try {
            this.myLogic.hit(new Position(arg0, arg1));
        } catch (Exception e) {
            this.throwedException = e;
        }
    }

    @Then("The system does not hit again and informs the user regarding this problem")
    public void theSystemDoesNotHitAgainAndInformsTheUserRegardingThisProblem() {
        if (this.throwedException == null) {
            fail();
        }
    }

    @When("The player checks position \\({int}, {int})")
    public void thePlayerChecksPosition(int arg0, int arg1) {
        this.myReturnedMark = this.myLogic.getMark(new Position(arg0, arg1));
    }

    @Then("The system returns no mark found")
    public void theSystemReturnsNorMarkFound() {
        if (this.myReturnedMark.isPresent()) {
            fail();
        }
    }

    @When("The player hits position \\({int}, {int})")
    public void thePlayerHitsPosition(int arg0, int arg1) {
        this.myReturnedMark = this.myLogic.hit(new Position(this.size - 1, this.size - 1));
    }

    @Then("The hit is valid")
    public void theHitIsValid() {
        if (this.myReturnedMark.isEmpty()) {
            fail();
        }
    }
}
