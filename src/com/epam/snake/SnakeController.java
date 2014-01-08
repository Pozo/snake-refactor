package com.epam.snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeController implements KeyListener {
    private final Board board;

    public SnakeController(Board board) {
        this.board = board;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(board.getSnake().canMoveLeft() && isLeftPressed(e)) {
            board.moveLeft();
        }
        if(board.getSnake().canMoveRight() && isRightPressed(e)) {
            
            board.moveRight();
        }
        if(board.getSnake().canMoveUp() && isUpPressed(e)) {
            
            board.moveUp();
        }
        if(board.getSnake().canMoveDown() && isDownPressed(e)) {
            
            board.moveDown();
        }
        if (isEscPressed(e)) {
            board.reset();
        }
    }

    private boolean isEscPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_ESCAPE;
    }

    private boolean isDownPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_DOWN;
    }

    private boolean isRightPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_RIGHT;
    }

    private boolean isUpPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_UP;
    }

    private boolean isLeftPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_LEFT;
    }
    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

}
