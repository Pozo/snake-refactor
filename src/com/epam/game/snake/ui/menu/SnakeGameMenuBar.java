package com.epam.game.snake.ui.menu;

import javax.swing.JMenuBar;

import com.epam.game.snake.SnakeGame;

public class SnakeGameMenuBar extends JMenuBar {
    public SnakeGameMenuBar(SnakeGame game) {
        add(new FileMenu(game));
        add(new SettingsMenu(game));
        add(new HelpMenu(game));
    }
}
