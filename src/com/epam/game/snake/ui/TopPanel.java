package com.epam.game.snake.ui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class TopPanel extends JPanel {
    public TopPanel() {
        setBounds(0, 0, GameBoard.BOARD_WIDTH, GameBoard.BOARD_HEIGHT);
        setBackground(Color.LIGHT_GRAY);
    }
    public void refill(JComponent component) {
        removeAll();
        add(component);
    }
    public void refill(ArrayList<JComponent> labels) {
        removeAll();
        for (JComponent jComponent : labels) {
            add(jComponent);
        }
    }
}
