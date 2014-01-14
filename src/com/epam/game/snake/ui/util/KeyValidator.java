package com.epam.game.snake.ui.util;

import java.awt.event.KeyEvent;

public class KeyValidator {

    public static boolean isF2Pressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_F2;
    }

    public static boolean isDownPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_DOWN;
    }

    public static boolean isRightPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_RIGHT;
    }

    public static boolean isUpPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_UP;
    }

    public static boolean isLeftPressed(KeyEvent e) {
        return e.getKeyCode() == KeyEvent.VK_LEFT;
    }

}
