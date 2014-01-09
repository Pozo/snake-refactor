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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.epam.game.EntityController;
import com.epam.game.EntityDirection;
import com.epam.game.GameSpeed;
import com.epam.game.snake.toplist.Player;
import com.epam.game.snake.toplist.TopListSerializer;
import com.epam.game.snake.toplist.Toplist;
import com.epam.game.snake.ui.GameKeyListener;
import com.epam.game.snake.ui.SnakeGameMenuBar;
import com.epam.game.snake.ui.ToplistTable;

public class SnakeGameImpl extends JFrame implements Runnable, SnakeGame, EntityController {

    private static final long serialVersionUID = 1L;
    private int FRAME_WIDTH = 506;
    private int FRAME_HEIGHT = 380;
    
    // board
    private static final int BLOCK_UNIT_SIZE = 10;
    private int boardWidth = 50 * BLOCK_UNIT_SIZE;
    private int boardHeight = 30 * BLOCK_UNIT_SIZE;

    //private int xvalt = +BLOCK_UNIT_SIZE;
    //private int yvalt;
    
    private Point direction = new Point(+BLOCK_UNIT_SIZE, 0);
    
    // game
    private boolean gameRunning;
    private int speed = 70;
    private int scores;
    
    private Point[] positions = new Point[125];
    
    //private Point[] p = new Point[125];
    private Random random = new Random();

    private JButton[] snakeBricks = new JButton[125];
    
    private JPanel[] border = new JPanel[4];
    private JPanel playground  = buildGameBoard();
    
    private JPanel top = new JPanel();
    
    private JLabel actualScore;
    private JScrollPane scrollpane;
    
    private Snake snake = new Snake();

    private Toplist topList = new Toplist();
    private TopListSerializer listSerializer = new TopListSerializer();
    
    {
        top.setBounds(0, 0, boardWidth, boardHeight);
        top.setBackground(Color.LIGHT_GRAY);
        
        Arrays.fill(positions, new Point());
        //Collections.fill(topList, new Toplist("", 0));

        positions[0] = new Point(24 * BLOCK_UNIT_SIZE,14 * BLOCK_UNIT_SIZE);

        //listSerializer.write(topList.getPlayers());
        topList.setPlayers(listSerializer.read());
    }

    /*
     * A mozgatás elindításának függvénye.
     */
    public void start() {
        (new Thread(this)).start();
    }

    /*
     * A Snake() függvény. Ez a program lelke. Itt történik az ablak
     * létrehozása, az ablak minden elemények hozzáadása, az értékek
     * inicializálása, az elsõ snake létrehozása, valamint itt híodik meg a
     * "mozgató" függvény is
     */
    public SnakeGameImpl() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new SnakeGameMenuBar(this));
        
        add(playground, BorderLayout.CENTER);
        add(buildScorePanel(), BorderLayout.SOUTH);
        setLayout(null);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(new GameKeyListener(this));
        
        createSnake();
    }

    private JPanel buildScorePanel() {
        JPanel scorePanel = new JPanel();
        
        actualScore = new JLabel();
        actualScore.setForeground(Color.BLACK);
        refreshActualScore();
        scorePanel.add(actualScore);
        
        scorePanel.setBounds(0, boardHeight, boardWidth, 30);
        scorePanel.setBackground(Color.GRAY);
        return scorePanel;
    }
    private void refreshActualScore() {
        actualScore.setText("Pontszám: " + scores);
    }
    private JPanel buildGameBoard() {
        JPanel playground  = new JPanel();
        playground.setLayout(null);
        playground.setBounds(0, 0, boardWidth, boardHeight);
        playground.setBackground(Color.LIGHT_GRAY);
        
        border[0] = new JPanel();
        border[0].setBounds(0, 0, boardWidth, BLOCK_UNIT_SIZE);
        border[1] = new JPanel();
        border[1].setBounds(0, 0, BLOCK_UNIT_SIZE, boardHeight);
        border[2] = new JPanel();
        border[2].setBounds(0, boardHeight - BLOCK_UNIT_SIZE, boardWidth, BLOCK_UNIT_SIZE);
        border[3] = new JPanel();
        border[3].setBounds(boardWidth - BLOCK_UNIT_SIZE, 0, BLOCK_UNIT_SIZE, boardHeight);
        playground.add(border[0]);
        playground.add(border[1]);
        playground.add(border[2]);
        playground.add(border[3]);
        return playground;
    }

    /*
     * Az újraindító függvény. Ennek meghívásakor az érték újra alapállapotba
     * kerülnek, ami eddig az ablakon volt az eltûnik, a mozgatás megáll, a
     * keret, az elsõ snake és a pontszám újra kirajzoldik, és meghívódik a
     * mozgató függvény
     */
    @Override
    public void reset() {
        positions[0] = new Point(24 * BLOCK_UNIT_SIZE,14 * BLOCK_UNIT_SIZE);
        // A pálya lepucolása
        playground.removeAll();

        // Ha az elõzõ játékban meghalt a kígyó, akkor a játék vége kijelzõ
        // törlése az ablakból
        if (!isRunning()) {
            scrollpane.removeAll();
            remove(top);
        }

        // A keret hozzáadása a pályához
        playground.add(border[0]);
        playground.add(border[1]);
        playground.add(border[2]);
        playground.add(border[3]);

        // Az elsõ kígyó létrehozása, kirajzolása
        createSnake();

        // A pálya hozzáadása az ablakhoz, annak újrarajzolása és a pontszám
        // kiírása
        add(playground, BorderLayout.CENTER);
        repaint();
        setVisible(true);
        refreshActualScore();
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

    /*
     * Ez a függvény a játék végét vizsgálja. Megnézi, hogy a kígyó halála után
     * felkerül-e a toplistára a játékos az elért eredményével. Ha igen akkor
     * bekéri a nevét, és frissíti a toplistát. Ha nem akkor egy játék vége
     * képernyõt rajzol ki. A végén pedig szerializál.
     */
    private void toplistabatesz() {
        ArrayList<JComponent> labels = new ArrayList<>();

        // Ha az elért eredmény jobb az eddigi legkisebb eredménynél
        if (getScore() > topList.getHighestScore()) {
           
            final JTextField playername = new JTextField(10);            
            playername.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topList.addPlayer(new Player(playername.getText(), scores));

                    //A toplista frissítése, és kirajzolása az ablakra
                    scrollpane = new JScrollPane(new ToplistTable(topList.getPlayers()));
               
                    ArrayList<JComponent> labels = new ArrayList<>();
                    labels.add(scrollpane);
                    refillTop(labels);
                }
            });
            labels.add(new JLabel("A játéknak vége!"));
            labels.add(new JLabel("Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):"));
            labels.add(playername);
        } else {
            labels.add(new JLabel("A játéknak vége!"));
            labels.add(new JLabel("Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2)."));
            scrollpane = new JScrollPane(new ToplistTable(topList.getPlayers()));
        }
        refillTop(labels);
        // Szerializálás
        listSerializer.write(topList.getPlayers());
    }
    private void refillTop(ArrayList<JComponent> labels) {
        top.removeAll();
        for (JComponent jComponent : labels) {
            top.add(jComponent);
        }
        add(top, BorderLayout.CENTER);
        setVisible(true);
        repaint();
    }
    /*
     * A mozgató függvény megváltoztatja a kígyó pozícióját a megadott irányba,
     * és közben vizsgálja, hogy a kígyó nem ütközött-e falnak vagy magának,
     * illetve azt, hogy evett-e
     */
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
                refreshActualScore();
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
        return (positions[0].getX() + 10 == boardWidth);
    }
    private boolean hitLeftWall() {
        return positions[0].getX() == 0;
    }
    private boolean hitBottomWall() {
        return positions[0].getY() == 0;
    }
    private boolean hitTopWall() {
        return positions[0].getY() + 10 == boardHeight;
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
    public void moveLeft() {
        snake.setCurrentDirection(EntityDirection.LEFT);
        direction.setLocation(-BLOCK_UNIT_SIZE, 0);
    }
    @Override
    public void moveRight() {
        snake.setCurrentDirection(EntityDirection.RIGHT);
        direction.setLocation(+BLOCK_UNIT_SIZE, 0);
    }
    @Override
    public void moveUp() {
        snake.setCurrentDirection(EntityDirection.UP);
        direction.setLocation(0, -BLOCK_UNIT_SIZE);
    }
    @Override
    public void moveDown() {
        snake.setCurrentDirection(EntityDirection.DOWN);
        direction.setLocation(0, +BLOCK_UNIT_SIZE);
    }
    @Override
    public void exit() {
        System.exit(0);        
    }

    @Override
    public void showToplist() {
        JOptionPane.showMessageDialog(playground, scrollpane);        
    }

    @Override
    public void setGameSpeed(GameSpeed speed) {
        switch (speed) {
        case SLOW:
            SnakeGameSpeed.SLOW.getSpeed();
            break;
        case FAST:
            SnakeGameSpeed.FAST.getSpeed();
            break;
        case NORMAL:
        default:
            SnakeGameSpeed.NORMAL.getSpeed();
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
        return this;
    }

    @Override
    public boolean isRunning() {
        return gameRunning;
    }

    @Override
    public void stop() {
        gameRunning = false;
    }

    @Override
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
