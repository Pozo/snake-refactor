package com.epam.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.epam.snake.toplist.Toplist;
import com.epam.snake.toplist.ToplistComparator;

public class Snake extends JFrame implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;
	private int FRAME_WIDTH = 506;
	private int FRAME_HEIGHT = 380;
	
	// board
	private static final int BLOCK_UNIT_SIZE = 10;	
	private int boardWidth = 50 * BLOCK_UNIT_SIZE;
	private int boardHeight = 30 * BLOCK_UNIT_SIZE;

	private int xvalt = +BLOCK_UNIT_SIZE;
	private int yvalt;
	
	// game
	private boolean running;
	private int speed = 70;
	private int scores;
	
	// snake
	private int length = 3;
	
	private boolean canMoveLeft;
	private boolean canMoveRight = true;
	private boolean canMoveUp = true;
	private boolean canMoveDown = true;
	
	private boolean evett = true;
	private boolean magabament;
	private boolean gameover;
	
	private int[] pozx = new int[125];
	private int[] pozy = new int[125];
	private Point[] p = new Point[125];
	private Random random = new Random();

	private JButton[] kocka = new JButton[125];
	private JFrame frame;
	private JPanel jatekter, pontszam, top;
	private JPanel[] keret = new JPanel[4];
	private JMenuBar menubar;
	private JMenu jatek, beallitasok, segitseg;
	private JLabel pontkiiras;
	private JScrollPane scrollpane;

	private ArrayList<Toplist> lista;
	{
	    lista = new ArrayList<Toplist>();
	    lista.add(new Toplist("", 1));
	    lista.add(new Toplist("", 2));
	    lista.add(new Toplist("", 3));
	    lista.add(new Toplist("", 4));
	    lista.add(new Toplist("", 5));
	    lista.add(new Toplist("", 6));
	    lista.add(new Toplist("", 7));
	    lista.add(new Toplist("", 8));
	    lista.add(new Toplist("", 9));
	    lista.add(new Toplist("", 10));
	}

	/*
	 * Az értékek alaphelyzetbe állítása és a toplistát tartalmazó fájl
	 * megnyitása
	 */
	public void init() {
		pozx[0] = 24 * BLOCK_UNIT_SIZE;
		pozy[0] = 14 * BLOCK_UNIT_SIZE;
		//sebesseg = 70;
		//pontok = 0;
		//hossz = 3;
		//xvalt = +blockUnitSize;
		//yvalt = 0;
		/*
		fut = false;
		magabament = false;
		mehetbalra = false;
		mehetjobbra = true;
		mehetfel = true;
		mehetle = true;
		evett = true;
		gameover = false;
		*/
		//toplistabatesz();
		fajlmegnyitas();
	}

	/*
	 * A mozgatás elindításának függvénye.
	 */
	public void start() {
		running = true;
		(new Thread(this)).start();
	}

	/*
	 * A Snake() függvény. Ez a program lelke. Itt történik az ablak
	 * létrehozása, az ablak minden elemények hozzáadása, az értékek
	 * inicializálása, az elsõ snake létrehozása, valamint itt híodik meg a
	 * "mozgató" függvény is
	 */
	public Snake() {
		// Egy WIDTH, HEIGHT méretekkel rendelkezõ abalak létrehozása
		frame = new JFrame("Snake v0.7");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		// Az ablak részeinek létrehozása
		jatekter = new JPanel();
		pontszam = new JPanel();
		top = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Értékek inicializálása és a menü létrehozása
		init();
		menu();

		// A pálya részeinek részletes beállítása (pozíció, szélesség,
		// magasság, szín) és hozzáadása az ablakhoz
		frame.add(jatekter, BorderLayout.CENTER);
		frame.add(pontszam, BorderLayout.SOUTH);
		frame.setLayout(null);
		jatekter.setLayout(null);
		jatekter.setBounds(0, 0, boardWidth, boardHeight);
		jatekter.setBackground(Color.LIGHT_GRAY);
		pontszam.setBounds(0, boardHeight, boardWidth, 30);
		pontszam.setBackground(Color.GRAY);
		top.setBounds(0, 0, boardWidth, boardHeight);
		top.setBackground(Color.LIGHT_GRAY);

		// Keret megrajzolása és hozzáadása a pályához
		keret[0] = new JPanel();
		keret[0].setBounds(0, 0, boardWidth, BLOCK_UNIT_SIZE);
		keret[1] = new JPanel();
		keret[1].setBounds(0, 0, BLOCK_UNIT_SIZE, boardHeight);
		keret[2] = new JPanel();
		keret[2].setBounds(0, boardHeight - BLOCK_UNIT_SIZE, boardWidth, BLOCK_UNIT_SIZE);
		keret[3] = new JPanel();
		keret[3].setBounds(boardWidth - BLOCK_UNIT_SIZE, 0, BLOCK_UNIT_SIZE, boardHeight);
		jatekter.add(keret[0]);
		jatekter.add(keret[1]);
		jatekter.add(keret[2]);
		jatekter.add(keret[3]);

		// Az elsõ snake létrehozása és kirajzolása
		elsoSnake();

		// A pontszám kíírása a képernyõre
		pontkiiras = new JLabel("Pontszám: " + scores);
		pontkiiras.setForeground(Color.BLACK);
		pontszam.add(pontkiiras);

		// Az ablak beállításai
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}

	/*
	 * Ez a menüt létrehozõ függvény. Létrehozza a menüket, hozzáadja a
	 * funkcióikat, és a képernyõre viszi azokat
	 */
	public void menu() {
		// A 3 menupont létrehozása
		menubar = new JMenuBar();
		jatek = new JMenu("Játék");
		beallitasok = new JMenu("Beállítások");
		segitseg = new JMenu("Segítség");

		// A 3 menupontokon belüli lehetõségek létrehozása
		JMenuItem ujjatek = new JMenuItem("Új Játék (F2)");
		JMenuItem toplist = new JMenuItem("Toplista");
		JMenuItem kilepes = new JMenuItem("Kilépés (ALT+F4)");

		JMenuItem nehez = new JMenuItem("Nehéz");
		JMenuItem normal = new JMenuItem("Normál");
		JMenuItem konnyu = new JMenuItem("Könnyû");

		JMenuItem iranyitas = new JMenuItem("Irányítás");
		JMenuItem keszito = new JMenuItem("Készítõ");

		// Az Új Játék, a Toplista és a Kilépés funkciók hozzárendelése
		ujjatek.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		toplist.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jatekter, scrollpane);
			}
		});
		kilepes.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Ezek hozzáadása a Játék menüponthoz
		jatek.add(ujjatek);
		jatek.addSeparator();
		jatek.add(toplist);
		jatek.addSeparator();
		jatek.add(kilepes);
		menubar.add(jatek);

		// A sebesség változtatásának hozzárendelése
		nehez.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				speed = 50;
			}
		});
		normal.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				speed = 70;
			}
		});
		konnyu.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				speed = 90;
			}
		});

		// Ezek hozzáadása a Beállítások menüponthoz
		beallitasok.add(nehez);
		beallitasok.addSeparator();
		beallitasok.add(normal);
		beallitasok.addSeparator();
		beallitasok.add(konnyu);
		menubar.add(beallitasok);

		// A segítségek funkcióinak megvalósítása
		keszito.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jatekter, "Készítõ: Kérlek Refaktorálj\n" + "Programnév: Snake\n" + "Verziószám: v0.7");
			}
		});
		iranyitas.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jatekter, "Irányítás a kurzor segítségével:\n" + "-Fel nyíl: a kígyó felfele mozog\n"
						+ "-Le nyíl: a kígyó lefele mozog\n" + "-Jobbra nyíl: a kígyó jobbra mozog\n" + "-Balra nyíl: a kígyó balra mozog\n");
			}
		});

		// Ezek hozzáadása a Segítség menüponthoz
		segitseg.add(keszito);
		segitseg.addSeparator();
		segitseg.add(iranyitas);
		menubar.add(segitseg);

		// A teljes menü megjelenítése az ablakon
		frame.setJMenuBar(menubar);
	}

	/*
	 * Az újraindító függvény. Ennek meghívásakor az érték újra alapállapotba
	 * kerülnek, ami eddig az ablakon volt az eltûnik, a mozgatás megáll, a
	 * keret, az elsõ snake és a pontszám újra kirajzoldik, és meghívódik a
	 * mozgató függvény
	 */
	void reset() {
		// Az értékek kezdeti helyzetbe állítása
		init();

		// A pálya lepucolása
		jatekter.removeAll();
		scrollpane.removeAll();

		// Ha az elõzõ játékban meghalt a kígyó, akkor a játék vége kijelzõ
		// törlése az ablakból
		if (gameover == true) {
			frame.remove(top);
		}

		// A keret hozzáadása a pályához
		jatekter.add(keret[0]);
		jatekter.add(keret[1]);
		jatekter.add(keret[2]);
		jatekter.add(keret[3]);

		// Az elsõ kígyó létrehozása, kirajzolása
		elsoSnake();

		// A pálya hozzáadása az ablakhoz, annak újrarajzolása és a pontszám
		// kiírása
		frame.add(jatekter, BorderLayout.CENTER);
		frame.repaint();
		frame.setVisible(true);
		pontkiiras.setText("Pontszám: " + scores);

		// A mozgatás elindítása
		start();
	}

	/*
	 * Az elsõ snake létrehozása és a pályára rajzolása.
	 */
	void elsoSnake() {
		// Minden kockát külön rajzol ki a függvény, ezért a ciklus
		for (int i = 0; i < length; i++) {
			// Egy "kocka" létrehozása és annak beállításai (helyzet, szín)
			kocka[i] = new JButton();
			kocka[i].setEnabled(false);
			kocka[i].setBounds(pozx[i], pozy[i], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
			kocka[i].setBackground(Color.BLACK);

			// A kocka megjelenítése a pályán
			jatekter.add(kocka[i]);

			// A következõ elem koordinátáinak a megváltoztatása
			pozx[i + 1] = pozx[i] - BLOCK_UNIT_SIZE;
			pozy[i + 1] = pozy[i];
		}
	}

	/*
	 * Ez a függvény létrehozza az új ételt a pályán random helyen, és
	 * kirajzolja azt
	 */
	void novekszik() {
		// Létrehozza az új ételt, és hozzáadja a pályához
		kocka[length] = new JButton();
		kocka[length].setEnabled(false);
		kocka[length].setBackground(Color.BLACK);
		jatekter.add(kocka[length]);

		// Randomgenerátorral létrehozza az étel x,y koordinátáit
		int kajax = 20 + (BLOCK_UNIT_SIZE * random.nextInt(46));
		int kajay = 20 + (BLOCK_UNIT_SIZE * random.nextInt(26));

		// Beállítja a koordinátáit a kajának, és kirajzolja azt a megadott
		// pozícióban
		pozx[length] = kajax;
		pozy[length] = kajay;
		kocka[length].setBounds(pozx[length], pozy[length], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);

		// Megnöveli a kígyó hosszát jelzõ változót
		length++;
	}

	/*
	 * A fájlmegnyitó függvény megnyitja a "toplist.ser" nevû fájlt, mely a
	 * toplista szereplõit tartalmazza és ezeket a lista nevû ArrayListben
	 * eltárolja (deszerializálás)
	 */
	@SuppressWarnings("unchecked")
	void fajlmegnyitas() {
		// A fájl megnyitása
		try {
			InputStream file = new FileInputStream("toplista.ser");
			
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput in;
			in = new ObjectInputStream(buffer);

			// A fájl tartalmának bemásolása a lista ArrayListbe
			lista = (ArrayList<Toplist>) in.readObject();

			// A fájl bezárása
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * A fájlbaíró függvény a "toplista.ser" nevû fájlba beírja a legfrissebb
	 * toplista szereplõit (szerializálás)
	 */
	void fajlbairas() {
		// A fájl megnyitása
		try {
		    File file2 = new File("toplista.ser");
            if(!file2.exists()) {
                file2.createNewFile();
		    }
			OutputStream file = new FileOutputStream("toplista.ser");

			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput out;
			out = new ObjectOutputStream(buffer);

			// A lista ArrayList fájlba írása
			out.writeObject(lista);

			// A fájl bezárása
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Ez a függvény a játék végét vizsgálja. Megnézi, hogy a kígyó halála után
	 * felkerül-e a toplistára a játékos az elért eredményével. Ha igen akkor
	 * bekéri a nevét, és frissíti a toplistát. Ha nem akkor egy játék vége
	 * képernyõt rajzol ki. A végén pedig szerializál.
	 */
	void toplistabatesz() {
		// A pálya törlése a képernyõrõl.
		frame.remove(jatekter);

		// Ha az elért eredmény jobb az eddigi legkisebb eredménynél
		if (scores > lista.get(9).getScore()) {
			// Egy ArrayList létrehozása, mely a megadott nevet tárolja
			final ArrayList<String> holder = new ArrayList<String>();

			// A kiírások és a szövegmezõ létrehozása
			JLabel nyert1 = new JLabel("A játéknak vége!");
			JLabel nyert2 = new JLabel("Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):");
			final JTextField newnev = new JTextField(10);

			// Ezek hozzáadása a top panelhez
			top.removeAll();
			top.add(nyert1);
			top.add(nyert2);
			top.add(newnev);

			// A szövegmezõ tartalmának hozzásadása a holderhez
			newnev.addActionListener(new ActionListener() {
				@Override
                public void actionPerformed(ActionEvent e) {
					synchronized (holder) {
						holder.add(newnev.getText());
						holder.notify();
					}
					frame.dispose();
				}
			});

			// A top panel hozzáadása az ablakhoz, és az ablak újrarajzolása
			frame.add(top, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.repaint();

			// Várakozás a szövegezõ kitöltéséig
			synchronized (holder) {
				while (holder.isEmpty())
					try {
						holder.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			}

			// A lista utolsó elemének kicserélése az új listaelemmel és a lista
			// sorbarendezése
			ToplistComparator comp = new ToplistComparator();
			lista.remove(9);
			lista.add(new Toplist(holder.remove(0), scores));
			Collections.sort(lista, comp);

			// A toplista frissítése, és kirajzolása az ablakra
			toplistafrissites();
			top.removeAll();
			top.add(scrollpane);
			frame.repaint();
			// Ha az eredmény nincs bent a legjobb 10-be
		} else {
			// A kiirások létrehozása és hozzáadása az ablakhoz
			JLabel nemnyert1 = new JLabel("A játéknak vége!");
			JLabel nemnyert2 = new JLabel("Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2).");
			nemnyert1.setForeground(Color.BLACK);
			nemnyert2.setForeground(Color.BLACK);
			top.removeAll();
			top.add(nemnyert1);
			top.add(nemnyert2);

			// A toplista frissítése és a top panel hozzáadása az ablakhoz
			toplistafrissites();
			frame.add(top, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.repaint();
		}
		// Szerializálás
		fajlbairas();
	}

	/*
	 * Ez a függvény a toplistát egy táblázatba rakja
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void toplistafrissites() {
		// A táblázat fejlécének létrehozása
		Vector colnames = new Vector();
		colnames.add("Név");
		colnames.add("Pont");

		// A táblázat létrehozása egy ScrollPane-ben
		DefaultTableModel tablazatmodell = new DefaultTableModel(colnames, 0);
		JTable tablazat = new JTable(tablazatmodell);
		scrollpane = new JScrollPane(tablazat);

		// A táblázat feltöltése a lista elemeivel
		for (Toplist i : lista) {
			String[] row = { i.getPlayerName(), i.getstrpont() };
			tablazatmodell.addRow(row);
		}

	}

	/*
	 * A mozgató függvény megváltoztatja a kígyó pozícióját a megadott irányba,
	 * és közben vizsgálja, hogy a kígyó nem ütközött-e falnak vagy magának,
	 * illetve azt, hogy evett-e
	 */
	void mozgat() {
		// Lekéri a kígyó összes elemének pozícióját a pályán
		for (int i = 0; i < length; i++) {
			p[i] = kocka[i].getLocation();
		}

		// Megváltoztatja az elsõ elemnek a pozícióját a megadott irányba
		pozx[0] = pozx[0] + xvalt;
		pozy[0] = pozy[0] + yvalt;
		kocka[0].setBounds(pozx[0], pozy[0], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);

		// Megváltoztatja a többi elem helyzetét az elõtt lévõ elemére
		for (int i = 1; i < length; i++) {
			kocka[i].setLocation(p[i - 1]);
		}

		// Ellenõrzi, hogy a kígyó nem-e ment önmagába
		for (int i = 1; i < length - 1; i++) {
			if (p[0].equals(p[i])) {
				magabament = true;
			}
		}

		// Ellenõrzi, hogy a kígyó nem-e ment önmagába vagy falnak. Ha igen
		// akkor a játéknak vége procedúra zajlik le, illetve leáll a mozgatás
		if ((pozx[0] + 10 == boardWidth) || (pozx[0] == 0) || (pozy[0] == 0) || (pozy[0] + 10 == boardHeight) || (magabament == true)) {
			running = false;
			gameover = true;
			toplistabatesz();
		}

		// Ellenõrzi, hogy a kígyó nem érte-e el az ételt. Ha igen akkor növeli
		// a pontszámot
		if (pozx[0] == pozx[length - 1] && pozy[0] == pozy[length - 1]) {
			evett = true;
			scores = scores + 5;
			pontkiiras.setText("Pontszám: " + scores);
		}

		// Ha evett, akkor létrehozza az új ételt és növeli a kígyót, különben
		// az étel ott marad ahol volt
		if (evett == true) {
			novekszik();
			evett = false;
		} else {
			kocka[length - 1].setBounds(pozx[length - 1], pozy[length - 1], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
		}

		// A pálya frissítése
		jatekter.repaint();
		frame.setVisible(true);
	}

	@Override
    public void keyPressed(KeyEvent e) {
		if (canMoveLeft == true && e.getKeyCode() == KeyEvent.VK_LEFT) {
			xvalt = -BLOCK_UNIT_SIZE;
			yvalt = 0;
			canMoveRight = false;
			canMoveUp = true;
			canMoveDown = true;
		}
		if (canMoveUp == true && e.getKeyCode() == KeyEvent.VK_UP) {
			xvalt = 0;
			yvalt = -BLOCK_UNIT_SIZE;
			canMoveDown = false;
			canMoveRight = true;
			canMoveLeft = true;
		}
		if (canMoveRight == true && e.getKeyCode() == KeyEvent.VK_RIGHT) {
			xvalt = +BLOCK_UNIT_SIZE;
			yvalt = 0;
			canMoveLeft = false;
			canMoveUp = true;
			canMoveDown = true;
		}
		if (canMoveDown == true && e.getKeyCode() == KeyEvent.VK_DOWN) {
			xvalt = 0;
			yvalt = +BLOCK_UNIT_SIZE;
			canMoveUp = false;
			canMoveRight = true;
			canMoveLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			reset();
		}
	}

	@Override
    public void keyReleased(KeyEvent arg0) {
	}

	@Override
    public void keyTyped(KeyEvent arg0) {
	}

	/*
	 * A run metódus hivja meg megadott idõközönként a mozgató függvényt
	 */
	@Override
    public void run() {
	    running = true;	    
		while (running) {
			mozgat();
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
