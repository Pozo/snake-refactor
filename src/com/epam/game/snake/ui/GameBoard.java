package com.epam.game.snake.ui;

import java.awt.Color;

import javax.swing.JPanel;

public class GameBoard extends JPanel {
    private static final int BLOCK_UNIT_SIZE = 10;
    
    public static final int BOARD_WIDTH = 50 * BLOCK_UNIT_SIZE;
    public static final int BOARD_HEIGHT = 30 * BLOCK_UNIT_SIZE;
    
    
    private JPanel leftBorder = new JPanel();
    private JPanel rightBorder = new JPanel();
    private JPanel topBorder = new JPanel();
    private JPanel bottomBorder = new JPanel();
    
    public GameBoard() {
        setLayout(null);
        setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.LIGHT_GRAY);
        
        leftBorder.setBounds(0, 0, BOARD_WIDTH, BLOCK_UNIT_SIZE);
        rightBorder.setBounds(0, 0, BLOCK_UNIT_SIZE, BOARD_HEIGHT);
        topBorder.setBounds(0, BOARD_HEIGHT - BLOCK_UNIT_SIZE, BOARD_WIDTH, BLOCK_UNIT_SIZE);
        bottomBorder.setBounds(BOARD_WIDTH - BLOCK_UNIT_SIZE, 0, BLOCK_UNIT_SIZE, BOARD_HEIGHT);
        
        addBorders();
    }
    private void addBorders() {
        add(leftBorder);
        add(rightBorder);
        add(topBorder);
        add(bottomBorder);
    }
    public void clear() {
        this.removeAll();
        this.addBorders();
    }
}
