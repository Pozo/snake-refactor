package com.epam.game.snake.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.epam.game.GameSpeed;
import com.epam.game.snake.SnakeGame;

public class SettingsMenu extends JMenu {
    private static final String MENU_NAME = "Beállítások";
    
    private static final String GAME_EASY = "Könnyû";
    private static final String GAME_NORMAL = "Normál";
    private static final String GAME_HARD = "Nehéz";

    public SettingsMenu(final SnakeGame game) {
        super(MENU_NAME);
        JMenuItem nehez = new JMenuItem(GAME_HARD);
        JMenuItem normal = new JMenuItem(GAME_NORMAL);
        JMenuItem konnyu = new JMenuItem(GAME_EASY);
        
        nehez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getGameController().getGameSpeedController().setGameSpeed(GameSpeed.SLOW);
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getGameController().getGameSpeedController().setGameSpeed(GameSpeed.NORMAL);
            }
        });
        konnyu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getGameController().getGameSpeedController().setGameSpeed(GameSpeed.FAST);
            }
        });

        add(nehez);
        addSeparator();
        add(normal);
        addSeparator();
        add(konnyu);
    }
}
