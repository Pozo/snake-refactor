package com.epam.game.snake.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.epam.game.snake.SnakeGame;

public class HelpMenu extends JMenu {
    private static final String CREATOR = "K�sz�t�";
    private static final String CONTROLL = "Ir�ny�t�s";
    private static final String MENU_NAME = "Seg�ts�g";

    public HelpMenu(final SnakeGame game) {
        super(MENU_NAME);
        
        JMenuItem iranyitas = new JMenuItem(CONTROLL);
        JMenuItem keszito = new JMenuItem(CREATOR);
        keszito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showAbout();
            }
        });
        iranyitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showHelp();
            }
        });

        add(keszito);
        addSeparator();
        add(iranyitas);
    }
}
