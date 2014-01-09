package com.epam.game.snake.toplist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Toplist implements Serializable {
    private PlayerComparator playerComparator = new PlayerComparator();
    private static final long serialVersionUID = 1L;
    
    private static final int INITIAL_SIZE = 10;

    private ArrayList<Player> players = new ArrayList<>(INITIAL_SIZE);

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        Collections.sort(players, playerComparator);
        return players;
    }
    public int getHighestScore() {
        int highestScore = 0;
        
        if(players.size()>0) {
            for (Player player : players) {
                if(player.getScore()>highestScore) {
                    highestScore = player.getScore();
                }
            }
        }

        return highestScore;
    }
    public void removeLast() {
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        
    }
}