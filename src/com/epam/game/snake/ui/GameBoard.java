package com.epam.game.snake.ui;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.epam.game.snake.Snake;
import com.epam.game.snake.SnakeGame;

public class GameBoard extends JPanel {
    public static final int BLOCK_UNIT_SIZE = 10;
    
    public static final int BOARD_WIDTH = 50 * BLOCK_UNIT_SIZE;
    public static final int BOARD_HEIGHT = 30 * BLOCK_UNIT_SIZE;
    
    
    private JPanel leftBorder = new JPanel();
    private JPanel rightBorder = new JPanel();
    private JPanel topBorder = new JPanel();
    private JPanel bottomBorder = new JPanel();
    
    
    private Random random = new Random();
    private Snake snake = new Snake();
    
    private Point[] positions = new Point[125];
    private JButton[] snakeBricks = new JButton[125];

    private final GameBoardRuleObserver boardRuleObserver = new GameBoardRuleObserver(this);
    private final SnakeGame snakeGame;
    
    {
        Arrays.fill(positions, new Point());
    }
    
    public GameBoard(SnakeGame game) {
        this.snakeGame = game;
        setLayout(null);
        setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.LIGHT_GRAY);
        
        leftBorder.setBounds(0, 0, BOARD_WIDTH, BLOCK_UNIT_SIZE);
        rightBorder.setBounds(0, 0, BLOCK_UNIT_SIZE, BOARD_HEIGHT);
        topBorder.setBounds(0, BOARD_HEIGHT - BLOCK_UNIT_SIZE, BOARD_WIDTH, BLOCK_UNIT_SIZE);
        bottomBorder.setBounds(BOARD_WIDTH - BLOCK_UNIT_SIZE, 0, BLOCK_UNIT_SIZE, BOARD_HEIGHT);
        
        addBorders();
        resetpositions();
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
    public void resetpositions() {
        positions[0] = new Point(24 * BLOCK_UNIT_SIZE,14 * BLOCK_UNIT_SIZE);
    }
    public void createSnake() {
        for (int i = 0; i < snake.getSize(); i++) {
            snakeBricks[i] = createSnakeBrick();
            snakeBricks[i].setBounds((int)positions[i].getX(), (int)positions[i].getY(), BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);

            add(snakeBricks[i]);

            positions[i + 1].setLocation(positions[i].getX() - BLOCK_UNIT_SIZE, positions[i].getY());
        }
    }
    private JButton createSnakeBrick() {
        JButton brick = new JButton();
        brick.setEnabled(false);
        brick.setBackground(Color.BLACK);
        return brick;
    }
    public void putNewFood() {
        int size = snake.getSize();

        snakeBricks[size] = createSnakeBrick();
        add(snakeBricks[size]);

        int kajax = 20 + (BLOCK_UNIT_SIZE * random.nextInt(46));
        int kajay = 20 + (BLOCK_UNIT_SIZE * random.nextInt(26));

        positions[size].setLocation(kajax,kajay);
        snakeBricks[size].setBounds(kajax, kajay, BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
    }
    public void stepSnake() {
        int size = snake.getSize();
        Point[] temp = new Point[size];

        for (int i = 0; i < size; i++) {
            temp[i] = snakeBricks[i].getLocation();
        }

        step();

        for (int i = 1; i < size; i++) {
            snakeBricks[i].setLocation(temp[i - 1]);
        }

        if (boardRuleObserver.hitTheWall() || boardRuleObserver.hasConflict(temp)) {
            snakeGame.getGameController().stop();
            snakeGame.clearPlayground();
            snakeGame.addToToplist();
        } else {
            if (hasReachTheFood()) {
                putNewFood();
                snake.incrementSize();
                snakeGame.getGameController().getGameScoreController().incrementScore();
                snakeGame.refreshActualScore();
            } else {
                moveSnake(size);
            }
        }
        repaint();
        setVisible(true);
    }
    private void moveSnake(int size) {
        snakeBricks[size - 1].setBounds((int)positions[size-1].getX(), (int)positions[size-1].getY(), BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
    }
    private void step() {
        positions[0].setLocation(positions[0].getX() + snakeGame.getSnakeDirection().getX(), positions[0].getY() + snakeGame.getSnakeDirection().getY());
        snakeBricks[0].setBounds((int) positions[0].getX(), (int) positions[0].getY(), BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
    }

    private boolean hasReachTheFood() {
        final int size = snake.getSize();
        return isXEqual(size) && isYEqual(size);
    }
    private boolean isYEqual(int size) {
        return Double.compare(positions[0].getY(),positions[size-1].getY()) == 0;
    }
    private boolean isXEqual(int size) {
        return Double.compare(positions[0].getX(), positions[size-1].getX()) == 0;
    }
    public Point[] getPositions() {
        return positions;
    }
    public Snake getSnake() {
        return snake;
    }
}
