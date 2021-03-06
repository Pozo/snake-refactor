package com.epam.game.snake.ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
    private static final int PANEL_HEIGHT = 30;
    private static final String LABEL_SCORE = "Pontsz�m: ";
    
    private final JLabel actualScore = new JLabel();
    private int currentScore = 0;

    public ScorePanel() {
        actualScore.setForeground(Color.BLACK);
        this.setLabelText();
        add(actualScore);

        setBounds(0, GameBoard.BOARD_HEIGHT, GameBoard.BOARD_WIDTH, PANEL_HEIGHT);
        setBackground(Color.GRAY);
   }

    private void setLabelText() {
        actualScore.setText(String.format("%s %s", LABEL_SCORE, currentScore));
    }

    public void refreshActualScore(int actualScore) {
        this.currentScore = actualScore;
        this.setLabelText();
    }
}
