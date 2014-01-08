package com.epam.snake.toplist;

import java.io.Serializable;

public class Toplist implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;

    public Toplist(String nev, int pont) {
        this.score = pont;
        this.playerName = nev;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getstrpont() {
        return Integer.toString(score);
    }
}