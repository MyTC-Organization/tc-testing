
package org.example.integration;

import jdk.jfr.Description;
import org.example.GUI;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.logic.MyLogger;
import org.example.utils.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;

import javax.swing.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestLogicGui {
    private final static int SIZE = 10;
    private static GUI gui;
    private static final MyLogger mockLogger = Mockito.mock(MyLogger.class);
    private static final Logic spyLogic = Mockito.spy(new LogicImpl(SIZE, mockLogger));
    private static final Runnable spyRunnable = Mockito.spy(Runnable.class);

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        gui = new GUI(SIZE, spyRunnable);
        doNothing().when(mockLogger).info(anyString());
        doNothing().when(mockLogger).error(anyString());
        doNothing().when(spyRunnable).run();
        replaceLogic();
    }

    @Test
    @Description("When a button is pressed the logic's hit should be invoked.")
    @Tag("Integration")
    public void testOnPressButtonLogicHits() {
        try {
            final Map<JButton, Position> cells = this.getGUICells();
            final JButton button = cells.keySet().iterator().next();
            final Position position = cells.get(button);
            button.doClick();
            verify(spyLogic, times(1)).hit(position);
            verify(spyLogic, atLeast(1)).isOver();
        }catch (Exception e) {
            fail();
        }
    }

    @Test
    @Description("When a button is pressed the logic's hit should be invoked along with the getMark.")
    @Tag("Integration")
    public void testOnPressButtonTextChange() {
        try {
            final Map<JButton, Position> cells = this.getGUICells();
            final JButton button = cells.keySet().iterator().next();
            final Position position = cells.get(button);
            button.doClick();
            verify(spyLogic, times(1)).hit(position);
            verify(spyLogic, times(1)).getMark(position);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Description("When the game is over, the logic's isOver should be invoked and the isOver handler should be run.")
    @Tag("Integration")
    public void testIsOver() {
        try {
            final Map<JButton, Position> cells = this.getGUICells();
            final JButton button = getButton(cells, new Position(0, 0));
            final JButton button2 = getButton(cells, new Position(0, 1));
            button.doClick();
            button2.doClick();
            verify(spyLogic, times(1)).hit(new Position(0, 0));
            verify(spyLogic, times(1)).hit(new Position(0, 1));
            verify(spyLogic, atLeast(1)).isOver();
            assertTrue(spyLogic.isOver());
            verify(spyRunnable, times(1)).run();
        }catch (Exception e) {
            fail();
        }
    }

    private JButton getButton(final Map<JButton, Position> cells, final Position position) {
        final Optional<JButton> opt = cells.keySet()
                .stream()
                .filter(key -> cells.get(key).equals(position))
                .findFirst();
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new IllegalStateException("Button not found");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<JButton, Position> getGUICells() throws NoSuchFieldException, IllegalAccessException {
        final Field field = gui.getClass().getDeclaredField("cells");
        field.setAccessible(true);
        return (Map<JButton, Position>) field.get(gui);
    }

    private void replaceLogic() throws NoSuchFieldException, IllegalAccessException {
        final Field field = gui.getClass().getDeclaredField("logic");
        field.setAccessible(true);
        field.set(gui, spyLogic);
    }

}