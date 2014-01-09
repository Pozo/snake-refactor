package com.epam.game.snake;

import com.epam.game.Game;

public interface SnakeGame extends Game {
    void putNewFood();
    
    void showToplist();
    void showAbout();
    void showHelp();
}
