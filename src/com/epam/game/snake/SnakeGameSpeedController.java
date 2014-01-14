package com.epam.game.snake;

import com.epam.game.GameSpeed;
import com.epam.game.GameSpeedController;

public class SnakeGameSpeedController implements GameSpeedController {
    private GameSpeed gameSpeed = GameSpeed.NORMAL;
    
    @Override
    public GameSpeed getSpeed() {
        return gameSpeed;
    }

    @Override
    public void setGameSpeed(GameSpeed gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

}
