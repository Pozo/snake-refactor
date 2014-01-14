package com.epam.game.snake.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.epam.game.snake.SnakeGame;

public class FileMenu extends JMenu {
    private static final String EXIT = "Kilépés (ALT+F4)";
    private static final String TOPLIST = "Toplista";
    private static final String NEW_GAME = "Új Játék (F2)";
    private static final String MENU_NAME = "Játék";

    public FileMenu(final SnakeGame game) {
        super(MENU_NAME);
        JMenuItem ujjatek = new JMenuItem(NEW_GAME);
        ujjatek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
            }
        });
        JMenuItem toplist = new JMenuItem(TOPLIST);
        toplist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showToplist();
            }
        });
        JMenuItem kilepes = new JMenuItem(EXIT);
        kilepes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.exit();
            }
        });

        // Ezek hozzáadása a Játék menüponthoz
        add(ujjatek);
        addSeparator();
        add(toplist);
        addSeparator();
        add(kilepes);
    }
}
