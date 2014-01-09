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
        JMenu helpMenu = new JMenu("Seg�ts�g");
        
        JMenuItem iranyitas = new JMenuItem("Ir�ny�t�s");
        JMenuItem keszito = new JMenuItem("K�sz�t�");
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

        // Ezek hozz�ad�sa a Seg�ts�g men�ponthoz
        helpMenu.add(keszito);
        helpMenu.addSeparator();
        helpMenu.add(iranyitas);
        return helpMenu;
    }

    private JMenu buildFileMenu() {
        JMenu fileMenu = new JMenu("J�t�k");
        JMenuItem ujjatek = new JMenuItem("�j J�t�k (F2)");
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
        JMenuItem kilepes = new JMenuItem("Kil�p�s (ALT+F4)");
        kilepes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGameMenuBar.this.game.exit();
            }
        });

        // Ezek hozz�ad�sa a J�t�k men�ponthoz
        fileMenu.add(ujjatek);
        fileMenu.addSeparator();
        fileMenu.add(toplist);
        fileMenu.addSeparator();
        fileMenu.add(kilepes);
        return fileMenu;
    }

    private JMenu buildSettingsMenu() {
        JMenu settignsMenu = new JMenu("Be�ll�t�sok");
        JMenuItem nehez = new JMenuItem("Neh�z");
        JMenuItem normal = new JMenuItem("Norm�l");
        JMenuItem konnyu = new JMenuItem("K�nny�");
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

        // Ezek hozz�ad�sa a Be�ll�t�sok men�ponthoz
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
