package com.epam.game.snake.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.epam.game.Game;
import com.epam.game.GameSpeed;
import com.epam.game.snake.SnakeGame;

public class SnakeGameMenuBar extends JMenuBar {
    private final SnakeGame game;

    public SnakeGameMenuBar(SnakeGame game) {
        this.game = game;

        add(buildFileMenu());
        add(buildSettingsMenu());
        add(buildHelpMenu());

    }

    private JMenu buildHelpMenu() {
        JMenu helpMenu = new JMenu("Segítség");
        
        JMenuItem iranyitas = new JMenuItem("Irányítás");
        JMenuItem keszito = new JMenuItem("Készítõ");
        keszito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.showAbout();
            }
        });
        iranyitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.showHelp();
            }
        });

        // Ezek hozzáadása a Segítség menüponthoz
        helpMenu.add(keszito);
        helpMenu.addSeparator();
        helpMenu.add(iranyitas);
        return helpMenu;
    }

    private JMenu buildFileMenu() {
        JMenu fileMenu = new JMenu("Játék");
        JMenuItem ujjatek = new JMenuItem("Új Játék (F2)");
        ujjatek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.reset();
            }
        });
        JMenuItem toplist = new JMenuItem("Toplista");
        toplist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.showToplist();
            }
        });
        JMenuItem kilepes = new JMenuItem("Kilépés (ALT+F4)");
        kilepes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.exit();
            }
        });

        // Ezek hozzáadása a Játék menüponthoz
        fileMenu.add(ujjatek);
        fileMenu.addSeparator();
        fileMenu.add(toplist);
        fileMenu.addSeparator();
        fileMenu.add(kilepes);
        return fileMenu;
    }

    private JMenu buildSettingsMenu() {
        JMenu settignsMenu = new JMenu("Beállítások");
        JMenuItem nehez = new JMenuItem("Nehéz");
        JMenuItem normal = new JMenuItem("Normál");
        JMenuItem konnyu = new JMenuItem("Könnyû");
        nehez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.setGameSpeed(GameSpeed.SLOW);
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.setGameSpeed(GameSpeed.NORMAL);
            }
        });
        konnyu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.setGameSpeed(GameSpeed.FAST);
            }
        });

        // Ezek hozzáadása a Beállítások menüponthoz
        settignsMenu.add(nehez);
        settignsMenu.addSeparator();
        settignsMenu.add(normal);
        settignsMenu.addSeparator();
        settignsMenu.add(konnyu);
        return settignsMenu;
    }

    public Game getGame() {
        return game;
    }

}
