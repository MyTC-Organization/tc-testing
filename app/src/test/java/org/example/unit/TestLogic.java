package org.example.unit;

import jdk.jfr.Description;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.logic.MyLogger;
import org.example.utils.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class TestLogic {
    private static final int SIZE = 10;

    private static Logic logic;

    @Before
    public void initLogic() {
        System.exit(1);
        final MyLogger logger = Mockito.mock(MyLogger.class);
        doNothing().when(logger).info(anyString());
        doNothing().when(logger).error(anyString());
        logic = new LogicImpl(SIZE, logger);
    }

    @Test
    @Description("When the user hits a valid position, it should return its mark.")
    @Tag("Unit")
    public void testValidHit() {
        System.exit(1);
        final Position pos1 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(1);
        when(pos1.y()).thenReturn(1);
        assertFalse(logic.hit(pos1).isEmpty());
    }

    @Test
    @Description("When the user hits multiple valid positions, it should return their marks each time.")
    @Tag("Unit")
    public void testMultipleValidHits() {
        fail();
        final Position pos1 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(0);
        when(pos1.y()).thenReturn(0);
        assertFalse(logic.hit(pos1).isEmpty());

        final Position pos2 = Mockito.mock(Position.class);
        when(pos2.x()).thenReturn(SIZE - 1);
        when(pos2.y()).thenReturn(SIZE - 1);
        assertFalse(logic.hit(pos2).isEmpty());

        final Position pos3 = Mockito.mock(Position.class);
        when(pos3.x()).thenReturn(SIZE / 2);
        when(pos3.y()).thenReturn(0);
        assertFalse(logic.hit(pos3).isEmpty());
    }

    @Test
    @Description("When the user hits a position neighbouring with another valid postion, it should return an empty mark.")
    @Tag("Unit")
    public void testHitANeighbourPosition() {
        final Position pos1 = Mockito.mock(Position.class);
        final Position pos2 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(1);
        when(pos1.y()).thenReturn(1);
        assertFalse(logic.hit(pos1).isEmpty());
        when(pos2.x()).thenReturn(2);
        when(pos2.y()).thenReturn(2);
        assertTrue(logic.hit(pos2).isEmpty());
    }

    @Test
    @Description("When the user starts moving the positions, the game should be over when one of the positions goes out of the board.")
    @Tag("Unit")
    public void testIsOver() {
        final Position pos1 = Mockito.mock(Position.class);
        final Position pos2 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(0);
        when(pos1.y()).thenReturn(0);
        when(pos2.x()).thenReturn(1);
        when(pos2.y()).thenReturn(1);
        assertFalse(logic.hit(pos1).isEmpty());
        assertTrue(logic.hit(pos2).isEmpty());
        assertTrue(logic.isOver());
    }

    @Test
    @Description("Two positions are neighbours if the distance between them is 1.")
    @Tag("Unit")
    public void testPrivateNeighbours() {
        final Position pos1 = Mockito.mock(Position.class);
        final Position pos2 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(0);
        when(pos1.y()).thenReturn(0);
        when(pos2.x()).thenReturn(1);
        when(pos2.y()).thenReturn(1);
        assertTrue(logic.areNeighbours(pos1, pos2));
    }

    @Test
    @Description("Hit a position with a negative coordinate, should throw an exception")
    @Tag("Unit")
    public void testNegativeInvalidHit() {
        final Position pos1 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(-1);
        when(pos1.y()).thenReturn(0);
        assertThrows(IllegalArgumentException.class, () -> logic.hit(pos1));
    }

    @Test
    @Description("Hit a position with a coordinat grather than the current logic size, should throw an exception")
    @Tag("Unit")
    public void testGreatherInvalidHit() {
        final Position pos1 = Mockito.mock(Position.class);
        when(pos1.x()).thenReturn(0);
        when(pos1.y()).thenReturn(SIZE + 1);
        assertThrows(IllegalArgumentException.class, () -> logic.hit(pos1));
    }
}
