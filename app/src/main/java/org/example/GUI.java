package org.example;
import org.example.logic.LoggerImpl;
import org.example.logic.Logic;
import org.example.logic.LogicImpl;
import org.example.logic.MyLogger;
import org.example.utils.Position;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private static final MyLogger logger = new LoggerImpl();
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    private Runnable isOverHandler;

    public GUI(int size, Runnable isOverHandler) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 70*size);
        this.logic = new LogicImpl(size, logger);
        this.isOverHandler = isOverHandler;

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);

        final ActionListener al = e -> {
            handleClick((JButton) e.getSource());
        };

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton();
                this.cells.put(jb, new Position(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
    }

    public void showGUI() {
        this.setVisible(true);
    }

    private void handleClick(final JButton jb) {
        this.logic.hit(this.cells.get(jb));
        for (var entry: this.cells.entrySet()){
            entry.getKey().setText(
                    this.logic
                            .getMark(entry.getValue())
                            .map(String::valueOf)
                            .orElse(" "));
        }
        if (this.logic.isOver()){
            this.isOverHandler.run();
        }
    }

}
