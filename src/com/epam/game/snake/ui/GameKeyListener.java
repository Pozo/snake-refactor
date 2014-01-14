package com.epam.game.snake.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.epam.game.Game;
import com.epam.game.snake.ui.util.KeyValidator;

public class GameKeyListener implements KeyListener {
    private final Game game;

    public GameKeyListener(Game game) {
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        handleLeft(e);
        handleRight(e);
        handleUp(e);
        handleDown(e);
        handleF2(e);
    }

    private void handleF2(KeyEvent e) {
        if (KeyValidator.isF2Pressed(e)) {
            game.reset();
        }
    }

    private void handleDown(KeyEvent e) {
        if(game.getEntityDirectionController().canMoveDown() && KeyValidator.isDownPressed(e)) {
            game.getEntityController().moveDown();
        }
    }

    private void handleUp(KeyEvent e) {
        if(game.getEntityDirectionController().canMoveUp() && KeyValidator.isUpPressed(e)) {
            game.getEntityController().moveUp();
        }
    }

    private void handleRight(KeyEvent e) {
        if(game.getEntityDirectionController().canMoveRight() && KeyValidator.isRightPressed(e)) {
            game.getEntityController().moveRight();
        }
    }

    private void handleLeft(KeyEvent e) {
        if(game.getEntityDirectionController().canMoveLeft() && KeyValidator.isLeftPressed(e)) {
            game.getEntityController().moveLeft();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

}
