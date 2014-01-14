package com.epam.game.snake;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.epam.game.GameController;
import com.epam.game.entity.EntityController;
import com.epam.game.entity.EntityDirectionObserver;
import com.epam.game.snake.toplist.Player;
import com.epam.game.snake.ui.GameBoard;
import com.epam.game.snake.ui.GameKeyListener;
import com.epam.game.snake.ui.GameMessageHelper;
import com.epam.game.snake.ui.ScorePanel;
import com.epam.game.snake.ui.menu.SnakeGameMenuBar;
import com.epam.game.snake.ui.toplist.TopListPane;
import com.epam.game.snake.ui.toplist.TopPanel;

public class SnakeGameImpl extends JFrame implements Runnable, SnakeGame {
    private static final String GAME_NAME = "Snake game";
    private int FRAME_WIDTH = 506;
    private int FRAME_HEIGHT = 380;
    
    private GameBoard playground  = new GameBoard(this);
    private GameController gameController = new SnakeGameController(this);    
    private EntityController snakeController = new SnakeController(this);
    
    private ScorePanel scorePanel = new ScorePanel();    
    private TopPanel topPanel = new TopPanel();
    private TopListPane topListPane = new TopListPane();

    public void start() {
        (new Thread(this, GAME_NAME)).start();
    }
    public SnakeGameImpl() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new SnakeGameMenuBar(this));
        
        add(playground, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.SOUTH);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
        addKeyListener(new GameKeyListener(this));
        
        playground.createSnake();
    }
    @Override
    public void reset() {
        playground.resetpositions();
        playground.clear();
        playground.createSnake();
        add(playground, BorderLayout.CENTER);
        
        gameController.getGameScoreController().resetScore();
        
        if (!gameController.isRunning()) {
            remove(topPanel);
        }
        topListPane.refresh();
        scorePanel.refreshActualScore(gameController.getGameScoreController().getScore());
        
        repaint();        
        start();
    }
    @Override
    public void addToToplist() {
        ArrayList<JComponent> labels = new ArrayList<>();

        if (gameController.getGameScoreController().getScore() > topListPane.getHighestScore()) {
           
            final JTextField playername = new JTextField(10);            
            playername.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topListPane.addPlayer(new Player(playername.getText(), gameController.getGameScoreController().getScore()));

                    topListPane.refresh();
                    topPanel.refill(topListPane);
                    add(topPanel, BorderLayout.CENTER);
                    setVisible(true);
                    repaint();
                }
            });
            labels.add(new JLabel(GameMessageHelper.getGameOverMessage()));
            labels.add(new JLabel(GameMessageHelper.getCongratMessage()));
            labels.add(playername);
        } else {
            labels.add(new JLabel(GameMessageHelper.getGameOverMessage()));
            labels.add(new JLabel(GameMessageHelper.getApologizeMessage()));

        }
        topPanel.refill(labels);
        add(topPanel, BorderLayout.CENTER);
        
        topListPane.refresh();
        topListPane.persist();
        setVisible(true);
        repaint();
    }
    void move() {
        playground.stepSnake();
    }
    @Override
    public void run() {
        gameController.start();
        while (gameController.isRunning()) {
            try {
                move();
                Thread.sleep(gameController.getGameSpeedController().getSpeed().getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void exit() {
        System.exit(0);        
    }

    @Override
    public void showToplist() {
        JOptionPane.showMessageDialog(playground, topListPane);        
    }
    @Override
    public void showAbout() {
        JOptionPane.showMessageDialog(playground, GameMessageHelper.getAboutMessage());        
    }

    @Override
    public void showHelp() {
        JOptionPane.showMessageDialog(playground, GameMessageHelper.getControllMessage());
    }

    @Override
    public EntityDirectionObserver getEntityDirectionController() {
        return playground.getSnake().getController();
    }

    @Override
    public EntityController getEntityController() {
        return snakeController;
    }
    @Override
    public void clearPlayground() {
        remove(playground);
    }
    @Override
    public void refreshActualScore() {
        scorePanel.refreshActualScore(gameController.getGameScoreController().getScore());        
    }
    @Override
    public Point getSnakeDirection() {
        return gameController.getGameEntityDirectionController().getDirection();
    }
    @Override
    public GameBoard getPlayground() {
        return playground;
    }
    @Override
    public GameController getGameController() {
        return gameController;
    }
}
