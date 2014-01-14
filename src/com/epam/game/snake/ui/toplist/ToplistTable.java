package com.epam.game.snake.ui.toplist;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.epam.game.snake.toplist.Player;

public class ToplistTable extends JTable {
    public ToplistTable(ArrayList<Player> players) {
        Vector<String> colnames = new Vector<String>();
        colnames.add("Név");
        colnames.add("Pont");
        
        DefaultTableModel tableModel = new DefaultTableModel(colnames, 0);
        setModel(tableModel);

        for (Player player : players) {
            String[] row = { player.getName(), String.valueOf(player.getScore()) };
            tableModel.addRow(row);
        }
    }
}
