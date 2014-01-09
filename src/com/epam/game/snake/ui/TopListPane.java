package com.epam.game.snake.ui;

import javax.swing.JScrollPane;

import com.epam.game.snake.toplist.Player;
import com.epam.game.snake.toplist.TopListSerializer;
import com.epam.game.snake.toplist.Toplist;

public class TopListPane extends JScrollPane {
    private TopListSerializer listSerializer = new TopListSerializer();
    //private ToplistTable toplistTable;
    private Toplist topList = new Toplist();

    public TopListPane() {

        topList.setPlayers(listSerializer.read());
    }

    public int getHighestScore() {
        return topList.getHighestScore();
    }

    public void addPlayer(Player player) {
        topList.addPlayer(player);
        
    }
    public void refresh() {
        setViewportView(new ToplistTable(topList.getPlayers()));
        repaint();
    }

    public void persist() {
        listSerializer.write(topList.getPlayers());        
    }
}
