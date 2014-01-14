package com.epam.game.snake;

import com.epam.game.GameScoreController;

public class SnakeGameScoreController implements GameScoreController {
    private int scores;
    
    @Override
    public int getScore() {
        return scores;
    }

    @Override
    public void incrementScore() {
        scores += 5;
    }
    @Override
    public void resetScore() {
        scores = 0;
    }
}
