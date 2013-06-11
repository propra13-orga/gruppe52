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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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
    
    // Initialisieren der benötigten Bilder
    private Image wall = setImagePath("wall.png");
    private Image trap = setImagePath("mine.gif");
    private Image monster = setImagePath("enemy.gif");
    private Image heart = setImagePath("heart.png");
    private Image background = setImagePath("bg.png");
    private Image projectile = setImagePath("projectile.png");
    private Image menu = setImagePath("headmenu.png");
    private Image finalGoal = setImagePath("goal.png");
    private Image layer = setImagePath("transparentLayer.png");
    private Image youDiedMenu = setImagePath("youdied.png");
    private Image shield = setImagePath("shield.png");
    
    
    public Setter(int levelNumber) {	// Konstruktor
        addKeyListener(new KAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MAdapter());

        setFocusable(true);					// Kann fokussiert werden; Relevant für den Keylistener
        setBackground(Color.GRAY);			// Setzt Hintergrund auf Grau
        setDoubleBuffered(true);			// verhindert mögliches Flackern

        new LevelCaller();					// erzeugt Objekt der Klasse LevelCaller
        LevelCaller.setLevel(levelNumber);	// Leveldaten werden initialisiert; Parameter legt fest welches Level gestartet wird

        Player.createPlayer(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY());		// erzeugt Objekt der Klasse Player: Fügt einen Spieler in das Spielfeld ein, an der durch die Parameter festgelegte Stelle
        //Player.createPlayer(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY()+16);	// (noch) zu Testzwecken wird ein zweiter Spieler erzeugt
        
        timer = new Timer(8, this);	// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt den Aktualisierungsintervall fest (in millisekunden)
        timer.start();				// startet den timer
    
        Controls.createControls();	// erstellt einen Controller (Für Steuerung zuständig)
    }
    

    public void paint(Graphics g) {			// Erzeugt und platziert alle Grafiken
        super.paint(g);
        long zeit = System.currentTimeMillis();
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.drawImage(background, 1, 1, this); 	// Hintergrundbild
        
        for(int a=0; a<((Runner.getWidthF()-(Runner.getWidthF()%20))/20); a++) {	// Titelmenü - Hintergrund darstellen
        	g2d.drawImage(menu, a*20, 0, this);
        }
        
        
        // Monster
		for(int a=0; a<Enemy.monsterList.size(); a++) { // Setzt ein Icon für jeden erstellten Gegner
			g2d.drawImage(Enemy.getImg(a), Enemy.getX(a), Enemy.getY(a), this);
		}
        // Tracker
		for(int a=0; a<Tracker.trackerList.size(); a++) { // Setzt ein Icon für jeden erstellten Tracker
			g2d.drawImage(Tracker.getImg(a), Tracker.getX(a), Tracker.getY(a), this);
		}
		// Projectiles
        for(int a=0; a<Projectile.projectileList.size(); a++) {	// Setzt ein Icon für jedes erstellte Projektil
        	//g2d.drawImage(Projectile.projectileList.get(a).getPlayerIcon(), Player.playerList.get(a).getX(), Player.playerList.get(a).getY(), this);
        	// TEST
        	if(Projectile.projectileList.get(a).isValid())
        		//g2d.drawString("o", Projectile.projectileList.get(a).getX(), Projectile.projectileList.get(a).getY());
        		g2d.drawImage(Projectile.projectileList.get(a).projectile, Projectile.projectileList.get(a).getX(), Projectile.projectileList.get(a).getY(), this);
        }
        // Display Line
		for(int a=0; a<DisplayLine.displayLineList.size(); a++) {
			if(DisplayLine.displayLineList.get(a).isString) {
				g2d.drawString(DisplayLine.displayLineList.get(a).magCount + "x", DisplayLine.displayLineList.get(a).x, DisplayLine.displayLineList.get(a).y);
			} else {
				g2d.drawImage(DisplayLine.displayLineList.get(a).img, DisplayLine.displayLineList.get(a).x, DisplayLine.displayLineList.get(a).y, this);
			}
		}
		
		
		// DisplayList
		for(int a=0; a<DisplayManager.displayList.size(); a++) {
        		g2d.drawImage(DisplayManager.displayList.get(a).img, DisplayManager.displayList.get(a).x, DisplayManager.displayList.get(a).y, this);
        }
		// AnimationList
		for(int a=0; a<DisplayManager.animationList.size(); a++) {
			g2d.drawImage(DisplayManager.animationList.get(a).img, DisplayManager.animationList.get(a).x, DisplayManager.animationList.get(a).y, this);
		}
		
		
        
        
		// ChangeableList
		for(int a=0; a<DisplayManager.changeableList.size(); a++) {
        		g2d.drawImage(DisplayManager.changeableList.get(a).img, DisplayManager.changeableList.get(a).x, DisplayManager.changeableList.get(a).y, this);
        }
		
		// Player
        for(int a=0; a<Player.playerList.size(); a++) {	// Setzt ein Icon für jeden erstellten Spieler
        	g2d.drawImage(Player.playerList.get(a).getPlayerImage(), Player.playerList.get(a).getX(), Player.playerList.get(a).getY(), this);
        }
		
		// FrontList
		for(int a=0; a<DisplayManager.frontList.size(); a++) {
			g2d.drawImage(DisplayManager.frontList.get(a).img, DisplayManager.frontList.get(a).x, DisplayManager.frontList.get(a).y, this);
		}
		
		// ChangeableStringList
		for(int a=0; a<DisplayManager.stringList.size(); a++) {
		        g2d.drawString(DisplayManager.stringList.get(a).text, DisplayManager.stringList.get(a).x, DisplayManager.stringList.get(a).y);
		        //System.out.println(DisplayManager.stringList.get(a).text+"   "+DisplayManager.stringList.get(a).x+"   "+DisplayManager.stringList.get(a).y);
		}
        
        
        
		// You Died Menu
		if(Player.showYouDiedImage) {
			g2d.drawImage(layer, 0, 0, this);
			g2d.drawImage(youDiedMenu, 200, 100, this);
		}
		
		// TODO: LÖSCHEN
		//g2d.drawImage(projectile, Controls.getDirectionMarkerX()+Runner.getCenterX(), Controls.getDirectionMarkerY()+Runner.getCenterY(), this);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
        //if(System.currentTimeMillis()-zeit>4)
			//System.out.println((System.currentTimeMillis()-zeit));
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
    
    

    public void actionPerformed(ActionEvent event) {	// Positionsdaten der bewegbaren Objekte aktualisieren und im Anschluss neu zeichnen
    	Enemy.move();
    	Tracker.move();
	    Player.move();
	    Item.setTime();
	    ProjectileManager.controlProjectiles();
	    DisplayManager.updateDisplayTimers();
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