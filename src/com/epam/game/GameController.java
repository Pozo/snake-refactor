package com.epam.game;

import com.epam.game.entity.EntityDirectionController;


public interface GameController {
    void start();
    void stop();
    boolean isRunning();
    
    EntityDirectionController  getGameEntityDirectionController();
    GameSpeedController getGameSpeedController();
    GameScoreController getGameScoreController();
}
