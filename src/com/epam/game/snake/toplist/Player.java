package com.epam.game.snake.toplist;

import java.io.Serializable;

public class Player implements Serializable {
    private final String name;
    private final int score;
    
    public Player(String name, int score) {
        super();
        this.name = name;
        this.score = score;
    }
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
}
