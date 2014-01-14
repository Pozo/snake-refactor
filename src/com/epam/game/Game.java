package com.epam.game;

import com.epam.game.entity.EntityController;
import com.epam.game.entity.EntityDirectionObserver;
import com.epam.game.snake.ui.GameBoard;

public interface Game {
    void reset();    
    void exit();
    
    EntityDirectionObserver getEntityDirectionController();
    EntityController getEntityController();
    GameBoard getPlayground();
}
