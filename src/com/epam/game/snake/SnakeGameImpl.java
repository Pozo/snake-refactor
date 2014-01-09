package com.epam.game.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.epam.game.EntityController;
import com.epam.game.EntityDirection;
import com.epam.game.GameSpeed;
import com.epam.game.snake.toplist.Player;
import com.epam.game.snake.ui.GameBoard;
import com.epam.game.snake.ui.GameKeyListener;
import com.epam.game.snake.ui.ScorePanel;
import com.epam.game.snake.ui.SnakeGameMenuBar;
import com.epam.game.snake.ui.TopListPane;
import com.epam.game.snake.ui.TopPanel;

public class SnakeGameImpl extends JFrame implements Runnable, SnakeGame {
    private int FRAME_WIDTH = 506;
    private int FRAME_HEIGHT = 380;
    
    private static final int BLOCK_UNIT_SIZE = 10;

    private Point direction = new Point(+BLOCK_UNIT_SIZE, 0);
    
    // game
    private boolean gameRunning;
    private int speed = 70;
    private int scores;
    
    private Point[] positions = new Point[125];
    
    private Random random = new Random();

    private JButton[] snakeBricks = new JButton[125];
    
    private EntityController snakeController = new SnakeController(this);
    private GameBoard playground  = new GameBoard();//buildGameBoard();
    private ScorePanel scorePanel = new ScorePanel();
    
    private TopPanel topPanel = new TopPanel();
    
    //private JLabel actualScore;
    //private JScrollPane scrollpane;
    private TopListPane topListPane = new TopListPane();
    
    private Snake snake = new Snake();

    //private Toplist topList = new Toplist();
    
    
    {
        Arrays.fill(positions, new Point());
        //Collections.fill(topList, new Toplist("", 0));

        positions[0] = new Point(24 * BLOCK_UNIT_SIZE,14 * BLOCK_UNIT_SIZE);

        //listSerializer.write(topList.getPlayers());
        //topList.setPlayers(listSerializer.read());
        //topListPane.setTopList(topList);
    }

    /*
     * A mozgatás elindításának függvénye.
     */
    public void start() {
        (new Thread(this)).start();
    }

    public SnakeGameImpl() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new SnakeGameMenuBar(this));
        
        add(playground, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.SOUTH);
        setLayout(null);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(new GameKeyListener(this));
        
        createSnake();
    }
    @Override
    public void reset() {
        positions[0] = new Point(24 * BLOCK_UNIT_SIZE,14 * BLOCK_UNIT_SIZE);
        playground.clear();

        if (!isRunning()) {
            topListPane.removeAll();
            remove(topPanel);
        }
        createSnake();

        add(playground, BorderLayout.CENTER);
        repaint();
        setVisible(true);
        scorePanel.refreshActualScore(scores);
        // A mozgatás elindítása
        start();
    }

    /*
     * Az elsõ snake létrehozása és a pályára rajzolása.
     */
    void createSnake() {
        // Minden kockát külön rajzol ki a függvény, ezért a ciklus
        for (int i = 0; i < snake.getSize(); i++) {
            // Egy "kocka" létrehozása és annak beállításai (helyzet, szín)
            snakeBricks[i] = createSnakeBrick();
            snakeBricks[i].setBounds((int)positions[i].getX(), (int)positions[i].getY(), BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);


            // A kocka megjelenítése a pályán
            playground.add(snakeBricks[i]);

            // A következõ elem koordinátáinak a megváltoztatása
            positions[i + 1].setLocation(positions[i].getX() - BLOCK_UNIT_SIZE, positions[i].getY());
        }
    }
    private JButton createSnakeBrick() {
        JButton brick = new JButton();
        brick.setEnabled(false);
        brick.setBackground(Color.BLACK);
        return brick;
    }
    @Override
    public void putNewFood() {
        // this.board.putEntityBrick(food);
        
        int size = snake.getSize();
        // Létrehozza az új ételt, és hozzáadja a pályához
        snakeBricks[size] = createSnakeBrick();
        playground.add(snakeBricks[size]);

        // Randomgenerátorral létrehozza az étel x,y koordinátáit
        int kajax = 20 + (BLOCK_UNIT_SIZE * random.nextInt(46));
        int kajay = 20 + (BLOCK_UNIT_SIZE * random.nextInt(26));

        // Beállítja a koordinátáit a kajának, és kirajzolja azt a megadott
        // pozícióban
        positions[size].setLocation(kajax,kajay);
        snakeBricks[size].setBounds(kajax, kajay, BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
    }
    private void toplistabatesz() {
        ArrayList<JComponent> labels = new ArrayList<>();

        // Ha az elért eredmény jobb az eddigi legkisebb eredménynél
        if (getScore() > topListPane.getHighestScore()) {
           
            final JTextField playername = new JTextField(10);            
            playername.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topListPane.addPlayer(new Player(playername.getText(), scores));

                    //A toplista frissítése, és kirajzolása az ablakra
                    //scrollpane = new JScrollPane(new ToplistTable(topList.getPlayers()));
                    topListPane.refresh();
                    topPanel.refill(topListPane);
                    add(topPanel, BorderLayout.CENTER);
                    repaint();
                    //refillTop(labels);
                }
            });
            labels.add(new JLabel("A játéknak vége!"));
            labels.add(new JLabel("Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):"));
            labels.add(playername);
        } else {
            labels.add(new JLabel("A játéknak vége!"));
            labels.add(new JLabel("Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2)."));
            topListPane.refresh();
            //scrollpane = new JScrollPane(new ToplistTable(topList.getPlayers()));
        }
        topPanel.refill(labels);
        add(topPanel, BorderLayout.CENTER);
        repaint();
        //refillTop(labels);
        // Szerializálás
        topListPane.persist();
    }
    void move() {
        int size = snake.getSize();
        Point[] temp = new Point[size];
        // Lekéri a kígyó összes elemének pozícióját a pályán
        for (int i = 0; i < size; i++) {
            temp[i] = snakeBricks[i].getLocation();
        }

        // Megváltoztatja az elsõ elemnek a pozícióját a megadott irányba
        positions[0].setLocation(positions[0].getX()+direction.getX(), positions[0].getY()+direction.getY());
        snakeBricks[0].setBounds((int) positions[0].getX(), (int) positions[0].getY(), BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);

        // Megváltoztatja a többi elem helyzetét az elõtt lévõ elemére
        for (int i = 1; i < size; i++) {
            snakeBricks[i].setLocation(temp[i - 1]);
        }

        // Ellenõrzi, hogy a kígyó nem-e ment önmagába vagy falnak. Ha igen
        // akkor a játéknak vége procedúra zajlik le, illetve leáll a mozgatás
        if (hitTheWall() || hasConflict(temp)) {
            stop();
            remove(playground);
            toplistabatesz();
        } else {
            // Ellenõrzi, hogy a kígyó nem érte-e el az ételt. Ha igen akkor növeli
            // a pontszámot
            if (hasReachTheFood()) {
                putNewFood();
                snake.incrementSize();
                incrementScore();
                scorePanel.refreshActualScore(scores);
            } else {
                snakeBricks[size - 1].setBounds((int)positions[size-1].getX(), (int)positions[size-1].getY(), BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
            }            
        }
        playground.repaint();
        setVisible(true);
    }

    private boolean hasReachTheFood() {
        int size = snake.getSize();
        return positions[0].getX() == positions[size-1].getX() && positions[0].getY() == positions[size-1].getY();
    }
    private boolean hitTheWall() {
        return hitRightWall() || hitLeftWall() || hitBottomWall() || hitTopWall();
    }
    private boolean hitRightWall() {
        return (positions[0].getX() + 10 == GameBoard.BOARD_WIDTH);
    }
    private boolean hitLeftWall() {
        return positions[0].getX() == 0;
    }
    private boolean hitBottomWall() {
        return positions[0].getY() == 0;
    }
    private boolean hitTopWall() {
        return positions[0].getY() + 10 == GameBoard.BOARD_HEIGHT;
    }
    /*
     * A run metódus hivja meg megadott idõközönként a mozgató függvényt
     */
    @Override
    public void run() {
        gameRunning = true;     
        while (isRunning()) {
            try {
                move();
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void setSnakeDirection(EntityDirection newDirection) {
        switch (newDirection) {
        case UP:
            direction.setLocation(0, -BLOCK_UNIT_SIZE);
            break;
        case DOWN:
            direction.setLocation(0, +BLOCK_UNIT_SIZE);
            break;
        case LEFT:
            direction.setLocation(-BLOCK_UNIT_SIZE, 0);
            break;
        case RIGHT:
        default:
            direction.setLocation(+BLOCK_UNIT_SIZE, 0);
            break;
        }
        snake.setCurrentDirection(newDirection);
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
    public void setGameSpeed(GameSpeed speed) {
        switch (speed) {
        case SLOW:
            this.speed = SnakeGameSpeed.SLOW.getSpeed();
            break;
        case FAST:
            this.speed = SnakeGameSpeed.FAST.getSpeed();
            break;
        case NORMAL:
        default:
            this.speed = SnakeGameSpeed.NORMAL.getSpeed();
            break;
        }
    }

    @Override
    public void showAbout() {
        JOptionPane.showMessageDialog(playground, "Készítõ: Kérlek Refaktorálj\n" + "Programnév: Snake\n" + "Verziószám: v0.7");        
    }

    @Override
    public void showHelp() {
        JOptionPane.showMessageDialog(playground, "Irányítás a kurzor segítségével:\n" + "-Fel nyíl: a kígyó felfele mozog\n"
                + "-Le nyíl: a kígyó lefele mozog\n" + "-Jobbra nyíl: a kígyó jobbra mozog\n" + "-Balra nyíl: a kígyó balra mozog\n");
        
    }

    @Override
    public Snake getEntity() {
        return snake;
    }

    @Override
    public EntityController getEntityController() {
        return snakeController;
    }

    @Override
    public boolean isRunning() {
        return gameRunning;
    }

    @Override
    public void stop() {
        gameRunning = false;
    }


    public boolean hasConflict(Point[] temp) {
        boolean hit = false;
        
        for (int i = 1; i < snake.getSize() - 1; i++) {
            if (temp[0].equals(temp[i])) {
                hit = true;
                break;
            }
        }
        return hit;
    }

    @Override
    public int getScore() {
        return scores;
    }

    @Override
    public void incrementScore() {
        scores += 5;
    }
}
