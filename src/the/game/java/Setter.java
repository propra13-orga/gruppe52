package the.game.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;


public class Setter extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public static Timer timer;	// erzeugt eine Variable timer des Typs Timer
    private ImageIcon ii;		// erzeugt eine Variable ii (ImageIcon) des Typs ImageIcon
    public static List<Image> imageList = new ArrayList<Image>();
    public static List<Integer> positionList = new ArrayList<Integer>();
    public static List<Long> timingList = new ArrayList<Long>();
    
    private static JPanel jp;
    
    // Initialisieren der benötigten Bilder
    //private Image wall = setImagePath("wall.png");
    //private Image trap = setImagePath("mine.gif");
    //private Image monster = setImagePath("enemy.gif");
    //private Image heart = setImagePath("heart.png");
    private Image background = setImagePath("bg.png");
    //private Image projectile = setImagePath("projectile.png");
    //private Image menu = setImagePath("headmenu.png");
    //private Image finalGoal = setImagePath("goal.png");
    private Image layer = setImagePath("transparentLayer.png");
    private Image youDiedMenu = setImagePath("youdied.png");
    //private Image shield = setImagePath("shield.png");
    
    
    public Setter(int levelNumber) {	// Konstruktor
        addKeyListener(new KAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MAdapter());

        setFocusable(true);					// Kann fokussiert werden; Relevant für den Keylistener
        setBackground(Color.GRAY);			// Setzt Hintergrund auf Grau
        setDoubleBuffered(true);			// verhindert mögliches Flackern
        setOpaque(true);					// setzt ein JPanel undurchsichtig

        new LevelCaller();					// erzeugt Objekt der Klasse LevelCaller
        
        Player.createPlayer(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY());		// erzeugt Objekt der Klasse Player: Fügt einen Spieler in das Spielfeld ein, an der durch die Parameter festgelegte Stelle
        //Player.createPlayer(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY()+16);	// (noch) zu Testzwecken wird ein zweiter Spieler erzeugt
        
        LevelCaller.setLevel(levelNumber);	// Leveldaten werden initialisiert; Parameter legt fest welches Level gestartet wird
        
        timer = new Timer(8, this);	// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt den Aktualisierungsintervall fest (in millisekunden)
        timer.start();				// startet den timer
        
        // JPanel
        jp = this;
        
        Controls.createControls();	// erstellt einen Controller (Für Steuerung zuständig)
    }


    public void paint(Graphics g) {			// Erzeugt und platziert alle Grafiken
        super.paint(g);
        //long zeit = System.currentTimeMillis();
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.drawImage(background, 0, 0, this); 	// Hintergrundbild
        
        // Monster
		for(int a=0; a<Enemy.monsterList.size(); a++) { // Setzt ein Icon für jeden erstellten Gegner
			g2d.drawImage(Enemy.getImg(a), Enemy.getX(a), Enemy.getY(a), this);
		}
        // Tracker
		for(int a=0; a<Tracker.trackerList.size(); a++) { // Setzt ein Icon für jeden erstellten Tracker
			g2d.drawImage(Tracker.getImg(a), Tracker.getX(a), Tracker.getY(a), this);
		}
		
        
		
		
		// DisplayList
		for(int a=0; a<DisplayManager.displayList.size(); a++) {
        	g2d.drawImage(DisplayManager.displayList.get(a).img, DisplayManager.displayList.get(a).x, DisplayManager.displayList.get(a).y, this);
        }
		

		DisplayManager.draw(g2d);
        
		
		
		// You Died Menu
		if(Player.showYouDiedImage) {
			g2d.drawImage(layer, 0, 0, this);
			g2d.drawImage(youDiedMenu, 200, 100, this);
		}
		
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
        //if(System.currentTimeMillis()-zeit>4)
		//	System.out.println((System.currentTimeMillis()-zeit));
    }
    
    public static void displayImage(Image img, int posx, int posy, int last) {	// TODO: Darstellung von endlischer Dauer
    	//Image img = setImagePath(imgPath);
    	imageList.add(img);
    	positionList.add(posx);
    	positionList.add(posy);
    	timingList.add(System.currentTimeMillis());
    }
    
    public Image setImagePath(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
    
    public static JPanel getJPanel() {
    	return jp;
    }
    
    

    public void actionPerformed(ActionEvent event) {	// Positionsdaten der bewegbaren Objekte aktualisieren und im Anschluss neu zeichnen
    	Enemy.move();
    	Tracker.move();
	    Player.move();
	    TemporaryItem.updater();
	    ProjectileManager.controlProjectiles();
	    DisplayManager.updateDisplayTimers();
	    Traps.updater();
    	repaint();		// neu zeichnen
    }

    public class KAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent event) {		// Wenn Taste losgelassen wird:
        	Controls.controls.keyReleased(event);	// ruft in der Klasse Player für den Spieler 0 die Methode 'keyReleased' auf und übergibt das event
        	//if(Player.playerList.size()>1)							// wenn mehr als 1 Spieler, dann WASD ebenfalls aktualisieren
        	//	Player.playerList.get(1).keyReleased_wasd(event);		// gleich wie oben, nur für die WASD Steuerung
        }

        public void keyPressed(KeyEvent event) {		// Wenn Taste gedrückt wird:
        	Controls.controls.keyPressed(event);		// ruft in der Klasse Player für den Spieler 0 die Methode 'keyPressed' auf und übergibt das event
        	//if(Player.playerList.size()>1)							// wenn mehr als 1 Spieler, dann WASD ebenfalls aktualisieren
        	//	Player.playerList.get(1).keyPressed_wasd(event);		// gleich wie oben, nur für die WASD Steuerung
        }
    }
    
    public class MAdapter implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Controls.controls.mouseClicked(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Controls.controls.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Controls.controls.mouseExited(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Controls.controls.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Controls.controls.mouseReleased(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Controls.controls.mouseDragged(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Controls.controls.mouseMoved(e);
		}
    }

}