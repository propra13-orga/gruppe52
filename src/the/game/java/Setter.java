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

/**
 * Verwaltet das Panel, dass auf das Frame gesetzt wird.
 * Hier drin läuft das Hauptspiel
 * Die Klasse beinhaltet die Paint-Methode, in welcher alles gezeichnet wird.
 */
public class Setter extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
		
	public static Timer timer;	// erzeugt eine Variable timer des Typs Timer
    private ImageIcon ii;		// erzeugt eine Variable ii (ImageIcon) des Typs ImageIcon
    public static List<Image> imageList = new ArrayList<Image>();
    public static List<Integer> positionList = new ArrayList<Integer>();
    public static List<Long> timingList = new ArrayList<Long>();
    public static long timeStamp = System.currentTimeMillis();
    
    private static JPanel jp;
    
    // Initialisieren der benötigten Bilder
    private Image background = setImagePath("bg.png");
    private Image layer = setImagePath("transparentLayer.png");
    private Image youDiedMenu = setImagePath("youdied.png");
    
    /**
     * Konstruktor(1) der Klasse Setter 
     * wird verwendet wenn ein Level mithilfe einer Levelnummer aufgerufen wird
     * @param levelNumber Levelnummer
     */
    public Setter(int levelNumber) {	// Konstruktor
        addKeyListener(new KAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MAdapter());

        setFocusable(true);					// Kann fokussiert werden; Relevant für den Keylistener
        setBackground(Color.GRAY);			// Setzt Hintergrund auf Grau
        setDoubleBuffered(true);			// verhindert mögliches Flackern
        setOpaque(true);					// setzt ein JPanel undurchsichtig
        
        Controls.createControls();	// erstellt einen Controller (Für Steuerung zuständig)
        
        new LevelCaller();					// erzeugt Objekt der Klasse LevelCaller
        
        for(int a=0; a<Settings.getPlayerCount(); a++) {
        	Player.createPlayer();
        }
        
        LevelCaller.setLevel(levelNumber);	// Leveldaten werden initialisiert; Parameter legt fest welches Level gestartet wird
        
        timer = new Timer(8, this);	// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt den Aktualisierungsintervall fest (in millisekunden)
        timer.start();				// startet den timer
        
        // JPanel
        jp = this;
        
        if(Settings.isMultiplayer()) {
        	NetClient.main(null);
        }
        
    }

    /**
     * Konstruktor(2) der Klasse Setter
     * Wird verwendet wenn ein Pfad des Levels angegeben wird
     * @param path Pfad zur Leveldatei
     */
    public Setter(String path) {	// Konstruktor
        addKeyListener(new KAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MAdapter());

        setFocusable(true);					// Kann fokussiert werden; Relevant für den Keylistener
        setBackground(Color.GRAY);			// Setzt Hintergrund auf Grau
        setDoubleBuffered(true);			// verhindert mögliches Flackern
        setOpaque(true);					// setzt ein JPanel undurchsichtig
        
        Controls.createControls();	// erstellt einen Controller (Für Steuerung zuständig)
        
        new LevelCaller();					// erzeugt Objekt der Klasse LevelCaller
        
        for(int a=0; a<Settings.getPlayerCount(); a++) {
        	Player.createPlayer();
        }
        
        LevelCaller.setLevel(path);	// Leveldaten werden initialisiert; Parameter legt fest welches Level gestartet wird
        
        timer = new Timer(8, this);	// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt den Aktualisierungsintervall fest (in millisekunden)
        timer.start();				// startet den timer
        
        // JPanel
        jp = this;
        
        if(Settings.isMultiplayer()) {
        	NetClient.main(null);
        }
        
    }

    /**
     * Paintmethode des Spiels
     * Verwaltet alles, was gezeichnet wird
     */
    public void paint(Graphics g) {			// Erzeugt und platziert alle Grafiken
        super.paint(g);
        //long zeit = System.currentTimeMillis();
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.drawImage(background, 0, 0, this); 	// Hintergrundbild
        
        // Monster
        for(int a=0; a<Enemy.enemyList.size(); a++) { // Setzt ein Icon für jeden erstellten Gegner
			g2d.drawImage(Enemy.getImg(a), Enemy.getX(a), Enemy.getY(a), this);
			//System.out.println("Enemy: " + Enemy.getX(a));
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
    }
    
    /**
     * Sorgt dafür, dass Bilder dargestellt werden können
     * @param img Bild, das dargestellt werden soll
     * @param posx x-Pos des Bildes
     * @param posy y-Pos des Bildes
     */
    public static void displayImage(Image img, int posx, int posy, int last) {	// TODO: Darstellung von endlischer Dauer
    	imageList.add(img);
    	positionList.add(posx);
    	positionList.add(posy);
    	timingList.add(System.currentTimeMillis());
    }
    
    /**
     * bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
     * @param path Bildpfad
     * @return Bild
     */
    public Image setImagePath(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
    
    /**
     * Gibt das Panel zurück
     * @return panel
     */
    public static JPanel getJPanel() {
    	return jp;
    }
    
    
	/**
	 *  Positionsdaten der bewegbaren Objekte aktualisieren und im Anschluss neu zeichnen
	 */
    public void actionPerformed(ActionEvent event) {	// Positionsdaten der bewegbaren Objekte aktualisieren und im Anschluss neu zeichnen
    	Enemy.move();
	    Player.move();
	    TemporaryItem.updater();
	    //ProjectileManager.controlProjectiles();
	    Projectile.move();
	    DisplayManager.updateDisplayTimers();
	    Traps.updater();
	
	    Controls.controls.updateGamePad();
    	repaint();		// neu zeichnen
    }

    /**
     * Verwaltet die Interaktionen mit der Tastatur
     */
    public class KAdapter extends KeyAdapter {
    	/**
    	 * Wenn Taste losgelassen wird:
    	 */
        public void keyReleased(KeyEvent event) {		// Wenn Taste losgelassen wird:
        	Controls.controls.keyReleased(event);	// ruft in der Klasse Player für den Spieler 0 die Methode 'keyReleased' auf und übergibt das event
        	//if(Player.playerList.size()>1)							// wenn mehr als 1 Spieler, dann WASD ebenfalls aktualisieren
        	//	Player.playerList.get(1).keyReleased_wasd(event);		// gleich wie oben, nur für die WASD Steuerung
        }
        /**
         * Wenn Taste gedrückt wird:
         */
        public void keyPressed(KeyEvent event) {		// Wenn Taste gedrückt wird:
        	Controls.controls.keyPressed(event);		// ruft in der Klasse Player für den Spieler 0 die Methode 'keyPressed' auf und übergibt das event
        	//if(Player.playerList.size()>1)							// wenn mehr als 1 Spieler, dann WASD ebenfalls aktualisieren
        	//	Player.playerList.get(1).keyPressed_wasd(event);		// gleich wie oben, nur für die WASD Steuerung
        }
    }
    
    /**
     * Verwaltet die Interaktionen mit der Maus
     */
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