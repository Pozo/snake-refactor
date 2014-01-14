package com.epam.game.snake.ui.toplist;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.epam.game.snake.ui.GameBoard;

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
