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
	 * Az �rt�kek alaphelyzetbe �ll�t�sa �s a toplist�t tartalmaz� f�jl
	 * megnyit�sa
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
	 * A mozgat�s elind�t�s�nak f�ggv�nye.
	 */
	public void start() {
		running = true;
		(new Thread(this)).start();
	}

	/*
	 * A Snake() f�ggv�ny. Ez a program lelke. Itt t�rt�nik az ablak
	 * l�trehoz�sa, az ablak minden elem�nyek hozz�ad�sa, az �rt�kek
	 * inicializ�l�sa, az els� snake l�trehoz�sa, valamint itt h�odik meg a
	 * "mozgat�" f�ggv�ny is
	 */
	public Snake() {
		// Egy WIDTH, HEIGHT m�retekkel rendelkez� abalak l�trehoz�sa
		frame = new JFrame("Snake v0.7");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		// Az ablak r�szeinek l�trehoz�sa
		jatekter = new JPanel();
		pontszam = new JPanel();
		top = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �rt�kek inicializ�l�sa �s a men� l�trehoz�sa
		init();
		menu();

		// A p�lya r�szeinek r�szletes be�ll�t�sa (poz�ci�, sz�less�g,
		// magass�g, sz�n) �s hozz�ad�sa az ablakhoz
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

		// Keret megrajzol�sa �s hozz�ad�sa a p�ly�hoz
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

		// Az els� snake l�trehoz�sa �s kirajzol�sa
		elsoSnake();

		// A pontsz�m k��r�sa a k�perny�re
		pontkiiras = new JLabel("Pontsz�m: " + scores);
		pontkiiras.setForeground(Color.BLACK);
		pontszam.add(pontkiiras);

		// Az ablak be�ll�t�sai
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}

	/*
	 * Ez a men�t l�trehoz� f�ggv�ny. L�trehozza a men�ket, hozz�adja a
	 * funkci�ikat, �s a k�perny�re viszi azokat
	 */
	public void menu() {
		// A 3 menupont l�trehoz�sa
		menubar = new JMenuBar();
		jatek = new JMenu("J�t�k");
		beallitasok = new JMenu("Be�ll�t�sok");
		segitseg = new JMenu("Seg�ts�g");

		// A 3 menupontokon bel�li lehet�s�gek l�trehoz�sa
		JMenuItem ujjatek = new JMenuItem("�j J�t�k (F2)");
		JMenuItem toplist = new JMenuItem("Toplista");
		JMenuItem kilepes = new JMenuItem("Kil�p�s (ALT+F4)");

		JMenuItem nehez = new JMenuItem("Neh�z");
		JMenuItem normal = new JMenuItem("Norm�l");
		JMenuItem konnyu = new JMenuItem("K�nny�");

		JMenuItem iranyitas = new JMenuItem("Ir�ny�t�s");
		JMenuItem keszito = new JMenuItem("K�sz�t�");

		// Az �j J�t�k, a Toplista �s a Kil�p�s funkci�k hozz�rendel�se
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

		// Ezek hozz�ad�sa a J�t�k men�ponthoz
		jatek.add(ujjatek);
		jatek.addSeparator();
		jatek.add(toplist);
		jatek.addSeparator();
		jatek.add(kilepes);
		menubar.add(jatek);

		// A sebess�g v�ltoztat�s�nak hozz�rendel�se
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

		// Ezek hozz�ad�sa a Be�ll�t�sok men�ponthoz
		beallitasok.add(nehez);
		beallitasok.addSeparator();
		beallitasok.add(normal);
		beallitasok.addSeparator();
		beallitasok.add(konnyu);
		menubar.add(beallitasok);

		// A seg�ts�gek funkci�inak megval�s�t�sa
		keszito.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jatekter, "K�sz�t�: K�rlek Refaktor�lj\n" + "Programn�v: Snake\n" + "Verzi�sz�m: v0.7");
			}
		});
		iranyitas.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jatekter, "Ir�ny�t�s a kurzor seg�ts�g�vel:\n" + "-Fel ny�l: a k�gy� felfele mozog\n"
						+ "-Le ny�l: a k�gy� lefele mozog\n" + "-Jobbra ny�l: a k�gy� jobbra mozog\n" + "-Balra ny�l: a k�gy� balra mozog\n");
			}
		});

		// Ezek hozz�ad�sa a Seg�ts�g men�ponthoz
		segitseg.add(keszito);
		segitseg.addSeparator();
		segitseg.add(iranyitas);
		menubar.add(segitseg);

		// A teljes men� megjelen�t�se az ablakon
		frame.setJMenuBar(menubar);
	}

	/*
	 * Az �jraind�t� f�ggv�ny. Ennek megh�v�sakor az �rt�k �jra alap�llapotba
	 * ker�lnek, ami eddig az ablakon volt az elt�nik, a mozgat�s meg�ll, a
	 * keret, az els� snake �s a pontsz�m �jra kirajzoldik, �s megh�v�dik a
	 * mozgat� f�ggv�ny
	 */
	void reset() {
		// Az �rt�kek kezdeti helyzetbe �ll�t�sa
		init();

		// A p�lya lepucol�sa
		jatekter.removeAll();
		scrollpane.removeAll();

		// Ha az el�z� j�t�kban meghalt a k�gy�, akkor a j�t�k v�ge kijelz�
		// t�rl�se az ablakb�l
		if (gameover == true) {
			frame.remove(top);
		}

		// A keret hozz�ad�sa a p�ly�hoz
		jatekter.add(keret[0]);
		jatekter.add(keret[1]);
		jatekter.add(keret[2]);
		jatekter.add(keret[3]);

		// Az els� k�gy� l�trehoz�sa, kirajzol�sa
		elsoSnake();

		// A p�lya hozz�ad�sa az ablakhoz, annak �jrarajzol�sa �s a pontsz�m
		// ki�r�sa
		frame.add(jatekter, BorderLayout.CENTER);
		frame.repaint();
		frame.setVisible(true);
		pontkiiras.setText("Pontsz�m: " + scores);

		// A mozgat�s elind�t�sa
		start();
	}

	/*
	 * Az els� snake l�trehoz�sa �s a p�ly�ra rajzol�sa.
	 */
	void elsoSnake() {
		// Minden kock�t k�l�n rajzol ki a f�ggv�ny, ez�rt a ciklus
		for (int i = 0; i < length; i++) {
			// Egy "kocka" l�trehoz�sa �s annak be�ll�t�sai (helyzet, sz�n)
			kocka[i] = new JButton();
			kocka[i].setEnabled(false);
			kocka[i].setBounds(pozx[i], pozy[i], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
			kocka[i].setBackground(Color.BLACK);

			// A kocka megjelen�t�se a p�ly�n
			jatekter.add(kocka[i]);

			// A k�vetkez� elem koordin�t�inak a megv�ltoztat�sa
			pozx[i + 1] = pozx[i] - BLOCK_UNIT_SIZE;
			pozy[i + 1] = pozy[i];
		}
	}

	/*
	 * Ez a f�ggv�ny l�trehozza az �j �telt a p�ly�n random helyen, �s
	 * kirajzolja azt
	 */
	void novekszik() {
		// L�trehozza az �j �telt, �s hozz�adja a p�ly�hoz
		kocka[length] = new JButton();
		kocka[length].setEnabled(false);
		kocka[length].setBackground(Color.BLACK);
		jatekter.add(kocka[length]);

		// Randomgener�torral l�trehozza az �tel x,y koordin�t�it
		int kajax = 20 + (BLOCK_UNIT_SIZE * random.nextInt(46));
		int kajay = 20 + (BLOCK_UNIT_SIZE * random.nextInt(26));

		// Be�ll�tja a koordin�t�it a kaj�nak, �s kirajzolja azt a megadott
		// poz�ci�ban
		pozx[length] = kajax;
		pozy[length] = kajay;
		kocka[length].setBounds(pozx[length], pozy[length], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);

		// Megn�veli a k�gy� hossz�t jelz� v�ltoz�t
		length++;
	}

	/*
	 * A f�jlmegnyit� f�ggv�ny megnyitja a "toplist.ser" nev� f�jlt, mely a
	 * toplista szerepl�it tartalmazza �s ezeket a lista nev� ArrayListben
	 * elt�rolja (deszerializ�l�s)
	 */
	@SuppressWarnings("unchecked")
	void fajlmegnyitas() {
		// A f�jl megnyit�sa
		try {
			InputStream file = new FileInputStream("toplista.ser");
			
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput in;
			in = new ObjectInputStream(buffer);

			// A f�jl tartalm�nak bem�sol�sa a lista ArrayListbe
			lista = (ArrayList<Toplist>) in.readObject();

			// A f�jl bez�r�sa
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
	 * A f�jlba�r� f�ggv�ny a "toplista.ser" nev� f�jlba be�rja a legfrissebb
	 * toplista szerepl�it (szerializ�l�s)
	 */
	void fajlbairas() {
		// A f�jl megnyit�sa
		try {
		    File file2 = new File("toplista.ser");
            if(!file2.exists()) {
                file2.createNewFile();
		    }
			OutputStream file = new FileOutputStream("toplista.ser");

			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput out;
			out = new ObjectOutputStream(buffer);

			// A lista ArrayList f�jlba �r�sa
			out.writeObject(lista);

			// A f�jl bez�r�sa
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Ez a f�ggv�ny a j�t�k v�g�t vizsg�lja. Megn�zi, hogy a k�gy� hal�la ut�n
	 * felker�l-e a toplist�ra a j�t�kos az el�rt eredm�ny�vel. Ha igen akkor
	 * bek�ri a nev�t, �s friss�ti a toplist�t. Ha nem akkor egy j�t�k v�ge
	 * k�perny�t rajzol ki. A v�g�n pedig szerializ�l.
	 */
	void toplistabatesz() {
		// A p�lya t�rl�se a k�perny�r�l.
		frame.remove(jatekter);

		// Ha az el�rt eredm�ny jobb az eddigi legkisebb eredm�nyn�l
		if (scores > lista.get(9).getScore()) {
			// Egy ArrayList l�trehoz�sa, mely a megadott nevet t�rolja
			final ArrayList<String> holder = new ArrayList<String>();

			// A ki�r�sok �s a sz�vegmez� l�trehoz�sa
			JLabel nyert1 = new JLabel("A j�t�knak v�ge!");
			JLabel nyert2 = new JLabel("Gratul�lok! Felker�lt�l a toplist�ra. K�rlek add meg a neved (max 10 bet�):");
			final JTextField newnev = new JTextField(10);

			// Ezek hozz�ad�sa a top panelhez
			top.removeAll();
			top.add(nyert1);
			top.add(nyert2);
			top.add(newnev);

			// A sz�vegmez� tartalm�nak hozz�sad�sa a holderhez
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

			// A top panel hozz�ad�sa az ablakhoz, �s az ablak �jrarajzol�sa
			frame.add(top, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.repaint();

			// V�rakoz�s a sz�vegez� kit�lt�s�ig
			synchronized (holder) {
				while (holder.isEmpty())
					try {
						holder.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			}

			// A lista utols� elem�nek kicser�l�se az �j listaelemmel �s a lista
			// sorbarendez�se
			ToplistComparator comp = new ToplistComparator();
			lista.remove(9);
			lista.add(new Toplist(holder.remove(0), scores));
			Collections.sort(lista, comp);

			// A toplista friss�t�se, �s kirajzol�sa az ablakra
			toplistafrissites();
			top.removeAll();
			top.add(scrollpane);
			frame.repaint();
			// Ha az eredm�ny nincs bent a legjobb 10-be
		} else {
			// A kiir�sok l�trehoz�sa �s hozz�ad�sa az ablakhoz
			JLabel nemnyert1 = new JLabel("A j�t�knak v�ge!");
			JLabel nemnyert2 = new JLabel("Sajnos nem ker�lt be az eredm�nyed a legjobb 10-be. Pr�b�lkozz �jra (F2).");
			nemnyert1.setForeground(Color.BLACK);
			nemnyert2.setForeground(Color.BLACK);
			top.removeAll();
			top.add(nemnyert1);
			top.add(nemnyert2);

			// A toplista friss�t�se �s a top panel hozz�ad�sa az ablakhoz
			toplistafrissites();
			frame.add(top, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.repaint();
		}
		// Szerializ�l�s
		fajlbairas();
	}

	/*
	 * Ez a f�ggv�ny a toplist�t egy t�bl�zatba rakja
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void toplistafrissites() {
		// A t�bl�zat fejl�c�nek l�trehoz�sa
		Vector colnames = new Vector();
		colnames.add("N�v");
		colnames.add("Pont");

		// A t�bl�zat l�trehoz�sa egy ScrollPane-ben
		DefaultTableModel tablazatmodell = new DefaultTableModel(colnames, 0);
		JTable tablazat = new JTable(tablazatmodell);
		scrollpane = new JScrollPane(tablazat);

		// A t�bl�zat felt�lt�se a lista elemeivel
		for (Toplist i : lista) {
			String[] row = { i.getPlayerName(), i.getstrpont() };
			tablazatmodell.addRow(row);
		}

	}

	/*
	 * A mozgat� f�ggv�ny megv�ltoztatja a k�gy� poz�ci�j�t a megadott ir�nyba,
	 * �s k�zben vizsg�lja, hogy a k�gy� nem �tk�z�tt-e falnak vagy mag�nak,
	 * illetve azt, hogy evett-e
	 */
	void mozgat() {
		// Lek�ri a k�gy� �sszes elem�nek poz�ci�j�t a p�ly�n
		for (int i = 0; i < length; i++) {
			p[i] = kocka[i].getLocation();
		}

		// Megv�ltoztatja az els� elemnek a poz�ci�j�t a megadott ir�nyba
		pozx[0] = pozx[0] + xvalt;
		pozy[0] = pozy[0] + yvalt;
		kocka[0].setBounds(pozx[0], pozy[0], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);

		// Megv�ltoztatja a t�bbi elem helyzet�t az el�tt l�v� elem�re
		for (int i = 1; i < length; i++) {
			kocka[i].setLocation(p[i - 1]);
		}

		// Ellen�rzi, hogy a k�gy� nem-e ment �nmag�ba
		for (int i = 1; i < length - 1; i++) {
			if (p[0].equals(p[i])) {
				magabament = true;
			}
		}

		// Ellen�rzi, hogy a k�gy� nem-e ment �nmag�ba vagy falnak. Ha igen
		// akkor a j�t�knak v�ge proced�ra zajlik le, illetve le�ll a mozgat�s
		if ((pozx[0] + 10 == boardWidth) || (pozx[0] == 0) || (pozy[0] == 0) || (pozy[0] + 10 == boardHeight) || (magabament == true)) {
			running = false;
			gameover = true;
			toplistabatesz();
		}

		// Ellen�rzi, hogy a k�gy� nem �rte-e el az �telt. Ha igen akkor n�veli
		// a pontsz�mot
		if (pozx[0] == pozx[length - 1] && pozy[0] == pozy[length - 1]) {
			evett = true;
			scores = scores + 5;
			pontkiiras.setText("Pontsz�m: " + scores);
		}

		// Ha evett, akkor l�trehozza az �j �telt �s n�veli a k�gy�t, k�l�nben
		// az �tel ott marad ahol volt
		if (evett == true) {
			novekszik();
			evett = false;
		} else {
			kocka[length - 1].setBounds(pozx[length - 1], pozy[length - 1], BLOCK_UNIT_SIZE, BLOCK_UNIT_SIZE);
		}

		// A p�lya friss�t�se
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
	 * A run met�dus hivja meg megadott id�k�z�nk�nt a mozgat� f�ggv�nyt
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