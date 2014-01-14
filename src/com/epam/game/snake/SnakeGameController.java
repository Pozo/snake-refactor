package com.epam.game.snake;

import com.epam.game.Game;
import com.epam.game.GameController;
import com.epam.game.GameScoreController;
import com.epam.game.GameSpeedController;
import com.epam.game.entity.EntityDirectionController;
import com.epam.game.snake.entity.SnakeEntityDirectionController;

public class SnakeGameController implements GameController {
    private final GameScoreController gameScoreController = new SnakeGameScoreController();
    private final GameSpeedController gameSpeedController = new SnakeGameSpeedController();
    private final EntityDirectionController gameEntityDirectionController;

    private boolean gameRunning;
    
    public SnakeGameController(Game game) {
        this.gameEntityDirectionController = new SnakeEntityDirectionController(game);
    }
     @Override
    public boolean isRunning() {
        return gameRunning;
    }

    @Override
    public void stop() {
        gameRunning = false;
    }
    @Override
    public void start() {
        gameRunning = true;        
    }

    @Override
    public GameScoreController getGameScoreController() {
        return gameScoreController;
    }

    @Override
    public GameSpeedController getGameSpeedController() {
        return gameSpeedController;
    }

    @Override
    public EntityDirectionController getGameEntityDirectionController() {
        return gameEntityDirectionController;
    }
}
