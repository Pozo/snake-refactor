package com.epam.game.snake.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.epam.game.Game;

public class GameKeyListener implements KeyListener {
    private final Game game;

    public GameKeyListener(Game game) {
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(game.getEntity().canMoveLeft() && isLeftPressed(e)) {
            game.getEntityController().moveLeft();
        }
        if(game.getEntity().canMoveRight() && isRightPressed(e)) {
            
            game.getEntityController().moveRight();
        }
        if(game.getEntity().canMoveUp() && isUpPressed(e)) {
            
            game.getEntityController().moveUp();
        }
        if(game.getEntity().canMoveDown() && isDownPressed(e)) {
            
            game.getEntityController().moveDown();
        }
        if (isF2Pressed(e)) {
            game.reset();
        }
    }

    private boolean isF2Pressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_F2;
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
