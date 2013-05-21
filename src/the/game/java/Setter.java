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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Setter extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Timer timer;	// erzeugt eine Variable timer des Typs Timer
    private ImageIcon ii;		// erzeugt eine Variable ii (ImageIcon) des Typs ImageIcon
    private int interval;
    
    public Setter(int levelNumber) {	// Konstruktor
        addKeyListener(new TAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt
        setFocusable(true);					// Kann fokussiert werden; Relevant für den Keylistener
        setBackground(Color.GRAY);			// Setzt Hintergrund auf Grau
        setDoubleBuffered(true);			// verhindert mögliches Flackern

        new LevelCaller();					// erzeugt Objekt der Klasse LevelCaller
        LevelCaller.setLevel(levelNumber);	// Leveldaten werden initialisiert; Parameter legt fest welches Level gestartet wird

        Player.createPlayer(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY());		// erzeugt Objekt der Klasse Player: Fügt einen Spieler in das Spielfeld ein, an der durch die Parameter festgelegte Stelle
        //Player.createPlayer(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY()+16);	// (noch) zu Testzwecken wird ein zweiter Spieler erzeugt
        
        interval = 8;
        timer = new Timer(interval, this);			// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt den Aktualisierungsintervall fest (in millisekunden)
        timer.start();						// startet den timer
    }
    

    public void paint(Graphics g) {			// Erzeugt und platziert alle Grafiken
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        // Initialisieren der benötigten Bilder
        Image wall = setImagePath("wall.png");
        Image trap = setImagePath("mine.gif");
        Image monster = setImagePath("enemy.gif");
        Image heart = setImagePath("heart.png");
        Image background = setImagePath("bg.png");
        Image explosion = setImagePath("explosion.gif");
        Image menu = setImagePath("headmenu.png");
        Image finalGoal = setImagePath("goal.png");
        Image layer = setImagePath("transparentLayer.png");
        Image youDiedMenu = setImagePath("youdied.png");
        Image shield = setImagePath("shield.png");
        
        g2d.drawImage(background, 1, 1, this); 	// Titelmenü - Hintergrund darstellen
        
        for(int a=0; a<((Runner.getWidthF()-(Runner.getWidthF()%20))/20); a++) {
        	g2d.drawImage(menu, a*20, 0, this);
        }
        
        // Geht die gesamte itemMap durch und stellt alles dar, was dort angegeben wurde; Jedes darzustellende Objekt hat eine ID
        for(int a=0; a < LevelCreator.getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<LevelCreator.getItemMapDimensions(1); b++) {		// Zeilenweise
				switch(LevelCreator.getItemMapData(a, b)) {
				case 1:
					g2d.drawImage(wall, a*20, b*20, this);		// Wall
					break;
				case 2:
					g2d.drawImage(trap, a*20, b*20, this);		// Trap
					break;
				case -2:
					g2d.drawImage(explosion, a*20, b*20, this);	// Explosion
					break;
				case 10:
					g2d.drawImage(heart, a*20, b*20, this);		// Hearts
					break;
				case 5:
					g2d.drawImage(finalGoal, a*20, b*20, this);	// Final Goal
					break;
				case 11:
					g2d.drawImage(shield, a*20, b*20, this);	// Shield
					break;
				}
				if(LevelCreator.getItemMapData(a, b)>100 && LevelCreator.getItemMapData(a, b)<200) {
					String str = (LevelCreator.getItemMapData(a, b)-100) + "s";	// verbleibende sekunden
					g2d.drawString(str, a*20, 15);	// Timer
				}
			}
		}
		// Player
        for(int a=0; a<Player.playerList.size(); a++) {	// Setzt ein Icon für jeden erstellten Spieler
        	g2d.drawImage(Player.playerList.get(a).getPlayerIcon(), Player.playerList.get(a).getX(), Player.playerList.get(a).getY(), this);
        }
        // Monster
		for(int a=0; a<Enemy.monsterList.size(); a++) { // Setzt ein Icon für jeden erstellten Gegner
			g2d.drawImage(monster, Enemy.getX(a), Enemy.getY(a), this);
		}
        // Tracker
		for(int a=0; a<Tracker.trackerList.size(); a++) { // Setzt ein Icon für jeden erstellten Tracker
			g2d.drawImage(monster, Tracker.getX(a), Tracker.getY(a), this);
		}
		// You Died Menu
		if(Player.showYouDiedImage) {
			g2d.drawImage(layer, 0, 0, this);
			g2d.drawImage(youDiedMenu, 200, 100, this);
		}
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
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
	    Item.setTime(interval);
    	repaint();		// neu zeichnen
    }

    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent event) {		// Wenn Taste losgelassen wird:
        	Player.playerList.get(0).keyReleased(event);	// ruft in der Klasse Player für den Spieler 0 die Methode 'keyReleased' auf und übergibt das event
        	if(Player.playerList.size()>1)							// wenn mehr als 1 Spieler, dann WASD ebenfalls aktualisieren
        		Player.playerList.get(1).keyReleased_wasd(event);		// gleich wie oben, nur für die WASD Steuerung
        }

        public void keyPressed(KeyEvent event) {		// Wenn Taste gedrückt wird:
        	Player.playerList.get(0).keyPressed(event);		// ruft in der Klasse Player für den Spieler 0 die Methode 'keyPressed' auf und übergibt das event
        	if(Player.playerList.size()>1)							// wenn mehr als 1 Spieler, dann WASD ebenfalls aktualisieren
        		Player.playerList.get(1).keyPressed_wasd(event);		// gleich wie oben, nur für die WASD Steuerung
        }
    }
}