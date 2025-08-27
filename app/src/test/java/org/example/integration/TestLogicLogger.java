package org.example.integration;

import jdk.jfr.Description;
import org.example.logic.LoggerImpl;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.logic.MyLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestLogicLogger {
    private static final int SIZE = 10;
    private static Logic logic;
    final MyLogger logger = Mockito.spy(LoggerImpl.class);

    @Before
    public void initLogic() {
        logic = new LogicImpl(SIZE, logger);
    }

    @Test
    @Description("When the isOver method is called, it should print a log message.")
    @Tag("Integration")
    public void testIsOverPrintsLog() {
        assertFalse(logic.isOver());
        verify(logger, times(1)).info("Check if the game is over");
    }

    @Test
    @Description("When the user hits a position, the logger should print different log messages.")
    @Tag("Integration")
    public void testHitPrintsLogs() {
        final var pos1 = Mockito.mock(org.example.utils.Position.class);
        when(pos1.x()).thenReturn(1);
        when(pos1.y()).thenReturn(1);
        assertFalse(logic.hit(pos1).isEmpty());
        verify(logger, atLeast(2)).info(anyString());
        verify(logger, times(1)).info("Hit at position: " + pos1);
        verify(logger, times(1)).info("Check if the game is over");
        verify(logger, times(1)).info("Add the hitted position");
    }

    @Test
    @Description("When the user calls getMark, the logger should print a log message regarding the input position.")
    @Tag("Integration")
    public void testGetMarkPrintsLog() {
        final var pos1 = Mockito.mock(org.example.utils.Position.class);
        when(pos1.x()).thenReturn(1);
        when(pos1.y()).thenReturn(1);
        assertTrue(logic.getMark(pos1).isEmpty());
        verify(logger, times(1)).info("Get mark for position: " + pos1);
    }

    @Test
    @Description("When the game is over, the logger should print the message 'The Game is Over'.")
    @Tag("Integration")
    public void testPrintsWhenGameIsOver() {
        final var pos1 = Mockito.mock(org.example.utils.Position.class);
        final var pos2 = Mockito.mock(org.example.utils.Position.class);
        final var pos3 = Mockito.mock(org.example.utils.Position.class);
        when(pos1.x()).thenReturn(0);
        when(pos2.x()).thenReturn(1);
        when(pos3.x()).thenReturn(2);
        when(pos1.y()).thenReturn(0);
        when(pos2.y()).thenReturn(1);
        when(pos3.y()).thenReturn(2);
        assertFalse(logic.hit(pos1).isEmpty());
        assertTrue(logic.hit(pos2).isEmpty());
        verify(logger, atLeast(4)).info(anyString());
        verify(logger, times(1)).info("Moving positions");
        verify(logger, times(1)).info("Checking if " + pos1 + " and " + pos2 + " are neighbours");
        assertTrue(logic.hit(pos3).isEmpty());
        verify(logger, times(1)).info("The Game is Over");
    }
}
