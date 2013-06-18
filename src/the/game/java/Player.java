package the.game.java;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Player {
    /**
	 * Notiz zur Klasse:
	 * Diese Klasse erstellt Spieler. Wird ein Objekt dieser Klasse mit dem
	 * Konstruktor Player(int posx, int posy) erstellt, so wird eine Spielfigur an der in den Parametern festgelegten
	 * Stelle eingefügt.
	 * Erstellt wird die Spielfigur über die Methode: createPlayer(int posx, int posy)
	 * Organisiert werden die Spieler über die Liste: playerList
	 */
	
    private int mx;		// move x
    private int my;		// move y
    private int x;		// x position
    private int y;		// y position
    private boolean permissionX;
    private boolean permissionY;
    public int imageSizeX=15;
    public int imageSizeY=15;
    private boolean alive;
    public static List<Player> playerList = new ArrayList<Player>();
    private static int index;
    //private int defaultPosX;
    //private int defaultPosY;
    private int lives;
    private int healthPoints;
    private int healthPointsMax;
    public static boolean showYouDiedImage=false;
    private boolean alreadyPunished;
    public boolean invincible;
    private boolean fire;
    private double resistance;
    
    private BufferedImage playerImgBufAlive;
    private BufferedImage playerImgBufDead;
    private BufferedImage playerImg;

    public Player(int posx, int posy) {		// Konstruktor mit Angabe der Startposition
    	
        x = posx;					// default x position
        y = posy;					// default y position
        alive = true;				// Spielfigur ist lebendig
        lives = 3;					// setzt die Startanzahl der Leben fest
        healthPointsMax = 100;		// setzt die maximale Anzahl an Lebenspunkten fest
        healthPoints = healthPointsMax;	// setzt die Lebenspunkte fest
        alreadyPunished = false; 	// Verhindert unnötiges Abziehen von Leben (lives): true, sobald ein Spieler ein Leben verloren hat, wieder false, sobald das Level neugestartet wird
        invincible = false;			// spieler nicht unbesiegbar
        fire = false;				// kontrolliert das Schießen
        resistance = 1;				// Faktor für Schaden (wird z.B. für Schild gebraucht)
        setPlayerImagePrepare();	// Player Image erstellen
    }
    
    public static void createPlayer(int posx, int posy) {	// erstellt neuen Spieler
    	WeaponManager.createWeaponManager();
    	ProjectileManager.createProjectileManager(playerList.size());
    	Score.createScore();
    	playerList.add(new Player(posx, posy));		// Objekt wird in Liste geschrieben
    	setDisplayLives();							// Lebensanzeige wird aktualisiert
    	DisplayLine.setDisplay();
    	Mana.createMana();
    }
    
    public static void resetAllPlayerPositions() {
		for(int a=0; a<Player.playerList.size(); a++) {			
			playerList.get(a).x = LevelCaller.getPlayerDefaultPosX();				// default x position
			playerList.get(a).y = LevelCaller.getPlayerDefaultPosY()+(a*(playerList.get(a).imageSizeY+1));		// default y position; +(a*16), um für verstzte Startpunkte zu sorgen, bei mehreren Spielern
		}
    }

    /**     REQUESTS     */
    public int getX() {		// returns actual x-position
        return x;
    }
    public int getY() {		// returns actual y-position
        return y;
    }
    public int getXAsFieldPos() {
		return (x-(x%20))/20;
	}
    public int getYAsFieldPos() {
		return (y-(y%20))/20;
	}
    public int getLives() {		// returns actual y-position
        return lives;
    }
    public int getHealthPoints() {		// returns actual y-position
        return healthPoints;
    }
    public boolean getFireStatus() {
    	return fire;
    }
    public BufferedImage getPlayerImage() {
		return playerImg;
    }
    /**     REQUESTS END     */
    
    /**     SETS     */
    private void setPlayerImagePrepare() {
    	playerImgBufAlive = new BufferedImage(imageSizeX,imageSizeY,BufferedImage.TYPE_INT_RGB);
    	playerImgBufDead = new BufferedImage(imageSizeX,imageSizeY,BufferedImage.TYPE_INT_RGB);
        try {
       		playerImgBufAlive = ImageIO.read(new File("src/the/game/java/right.png"));	
       		playerImgBufDead = ImageIO.read(new File("src/the/game/java/dead.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void setPlayerImage() {
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getRotateInstance(
        		Math.toRadians(Controls.getangle()),
				(double)imageSizeX/2.0, 
				(double)imageSizeY/2.0), 
				AffineTransformOp.TYPE_BILINEAR);
        if(alive)
        	playerImg = op.filter(playerImgBufAlive, null);
        else
        	playerImg = op.filter(playerImgBufDead, null);
    }
    public void setFireStatus(boolean fireStatus) {		// Für Steuerung
    	fire = fireStatus;
    }
    public void setMoveStatusX(int speed) {	// Für Steuerung
    	mx = speed;
    }
    public void setMoveStatusY(int speed) {	// Für Steuerung
    	my = speed;
    }
    private static void setDisplayLives() {
    	DisplayLine.setDisplay();
    }
    public void setDefaultStartPos(int posx, int posy) {	// Setzt und realisiert Startpunkt
    	x = posx;
    	y = posy;
    }
    public void setAbsoluteLives(int lifeCount) {
    	lives = lifeCount;
    }
    public void setAbsoluteHP(int hpCount) {
    	healthPoints = hpCount;
    }
    public static void setResistance(int playerID, double res) {
    	playerList.get(playerID).resistance = res;
    }
    public static void resetResistance(int playerID) {
    	playerList.get(playerID).resistance = 1;
    }
    /**     SETS END     */
    
    /**     RESETS     */
    public static void resetPlayerImages() {
    	for(int a=0; a<playerList.size(); a++) {
    		playerList.get(a).setPlayerImage();
    	}
    }
    /**     RESETS END     */
    
    /**     MOVEMENT     */
    public static void move() {	// Rechnung: (aktuelle Position + Bewegung für x- oder y-Achse) für jeden Aufruf
    	for(int a=0; a<playerList.size(); a++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
    		index = a;
    		if(playerList.get(index).alive) {								// Nur Bewegen, wenn Spieler lebendig
    			playerList.get(index).setMovementPermission();
    			if(Runner.codeRunning) {
		    		if(playerList.get(index).permissionX)
				    	playerList.get(index).x += playerList.get(index).mx;		// ID = 0: Beide Achsen aktualisieren.	> 0: nur x-Achse
		    		if(playerList.get(index).permissionY)
				    	playerList.get(index).y += playerList.get(index).my;		// ID = 0: Beide Achsen aktualisieren.	< 0: nur y-Achse
				    //System.out.println(playerList.get(index).permissionX + "   s" + playerList.get(index).permissionY);
		    		checkCollideWithMiscObjekts(a);
				    setDisplayLives();												// Aktualisiert die Lebensanzeige
		    	}
    		}
    	}
    }
        
    private void setMovementPermission() {	// Überprüft das Array 'itemMap' auf Gegenstände & Objekte in Bewegungsrichtung und erteilt Bewegungsfreigabe oder -verbot
    	permissionX = true;
    	permissionY = true;
    	int nextx = x + mx;
    	int nexty = y + my;
    	int borderXL = 0;			// Border left
    	int borderXR = Runner.getWidthF();//-21;			// Border right
    	int borderYU = 20;			// Border up
    	int borderYD = Runner.getHeightF();//-43;			// Border down
    	
		// Überprüfen ob Border passiert werden: XL (x-Achse, left)	XR (x-Achse, right)	YU (y-Achse, up)	YD (y-Achse, down)
    	if(mx==-1) {					// Check Border: left
    		if(x<=borderXL)
    			permissionX=false;
    	} else if(mx==1) {				// Check Border: right
    		if(x>=borderXR)
	    		permissionX=false;
    	}
    	if(my==-1) {					// Check Border: up
    		if(y<=borderYU)
	    		permissionY=false;
    	} else if(my==1) {				// Check Border: down
    		if(y>=borderYD)
	    		permissionY=false;
    	}
    	
    	// Umgebung im Array 'itemMap' überprüfen für die jeweilige Bewegungsrichtung (8 mögliche Richtungen)
    	// Ausgabswerte für permission sind "true", sofern sie nicht wegen den Border geändert wurden
    	// I	Bewegungsrichtung ermitteln
    	// II	Überprüfen, ob Bewegungsrichtung mit einer anderen Richtung kombiniert wurde (diagonal)	(Richtung den englischen Kommentaren zu entnehmen)
    	// 		II.a	Diagonal: Überprüfung der "künftigen" Position an 3 Eckpunkten (Fixpunkt: Oben links)
    	//				II.a.1	Wenn künftige Position blockiert, dann überprüfe zwei alternative Punkte, mit jeweils einem künftigen und einem aktuellen Achsenwert
    	//						Wenn einer dieser Punkte frei ist, ist die entsprechende Richtung zu wählen (ermöglicht "Gleiten" an den Wänden)
    	//						Wenn beide Achsen frei sind, entscheiden welche dominant sein soll
    	//				II.a.2	Wenn nichts blockiert, dann permission unverändert
    	//		II.b	Gerade: Überprüfung an der "künftigen" Position an 2 Eckpunkten
    	//				II.b.1	Wenn etwas im Weg, dann permission für die jeweilige Achse = false
    	//				II.b.2 	Wenn Weg frei, dann permission unverändert
    	if(mx==-1) {
    		if(my==-1){				// left, up
    			// 1. Fixpunkt
    			if(checkItemMap(nextx, nexty)==false) {							// 1. Beide blockiert
    				if(checkItemMap(x, nexty)==false) {								// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y)==false) {								// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten links
    			if(checkItemMap(nextx, nexty + imageSizeY)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x, nexty + imageSizeY)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y + imageSizeY)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Oben rechts
    			if(checkItemMap(nextx + imageSizeX, nexty)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imageSizeX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else if(my==1) {		// left, down
    			// 1. Fixpunkt
    			if(checkItemMap(nextx, nexty)==false) {							// 1. Beide blockiert
    				if(checkItemMap(x, nexty)==false) {								// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y)==false) {								// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten links
    			if(checkItemMap(nextx, nexty + imageSizeY)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x, nexty + imageSizeY)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y + imageSizeY)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten rechts
    			if(checkItemMap(nextx + imageSizeX, nexty + imageSizeY)==false) {	// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, nexty + imageSizeY)==false) {		// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imageSizeX, y + imageSizeY)==false) {		// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else {				// left
    			// 1. Fixpunkt
    			if(checkItemMap(nextx, nexty)==false) {								// 1. Links blockiert
    				permissionX = false;
    			}
    			// 2. Unten links
    			if(checkItemMap(nextx, nexty + imageSizeY)==false) {				// 1. Links blockiert
    				permissionX = false;
    			}
    		}	
    	} else if(mx==1) {
    		if(my==-1){				// right, up
    			// 1. Fixpunkt
    			if(checkItemMap(nextx, nexty)==false) {							// 1. Beide blockiert
    				if(checkItemMap(x, nexty)==false) {								// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y)==false) {								// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionY = false;										// (Y wegen Fehler beim Annähern an ecken von unten nach oben)
    				}
    			}
    			// 2. Oben rechts
    			if(checkItemMap(nextx + imageSizeX, nexty)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imageSizeX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten rechts
    			if(checkItemMap(nextx + imageSizeX, nexty + imageSizeY)==false) {	// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, nexty)==false) {					// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imageSizeX, y)==false) {					// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else if(my==1) {		// right, down
    			// 1. Oben rechts
    			if(checkItemMap(nextx + imageSizeX, nexty)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imageSizeX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten rechts
    			if(checkItemMap(nextx + imageSizeX, nexty + imageSizeY)==false) {	// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, nexty + imageSizeY)==false) {		// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imageSizeX, y + imageSizeY)==false) {		// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten links
    			if(checkItemMap(nextx, nexty + imageSizeY)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x, nexty + imageSizeY)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y + imageSizeY)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else {				// right
    			// 1. Oben rechts (nach rechts)
    			if(checkItemMap(nextx + imageSizeX, nexty)==false) {				// 1. Rechts blockiert
    				permissionX = false;
    			}
    			// 2. Unten rechts
    			if(checkItemMap(nextx + imageSizeX, nexty + imageSizeY)==false) {	// 1. Rechts blockiert
    				permissionX = false;
    			}
    		}	
    	} else if(my==-1) {   		// up
    		// 1. Fixpunkt (nach oben)
			if(checkItemMap(nextx, nexty)==false) {								// 1. Oben blockiert
				permissionY = false;
			}
			// 2. Oben rechts
			if(checkItemMap(nextx + imageSizeX, nexty)==false) {				// 1. Rechts blockiert
				permissionY = false;
			}
    	} else if(my==1) {			// down
    		// 1. Unten links (nach unten)
			if(checkItemMap(nextx, nexty + imageSizeY)==false) {				// 1. Unten blockiert
				permissionY = false;
			}
			// 2. Unten rechts
			if(checkItemMap(nextx + imageSizeX, nexty + imageSizeY)==false) {	// 1. Unten blockiert
				permissionY = false;
			}
    	}
    	LevelCaller.nextLevelAlready = false;
	}
    
    
    private boolean checkItemMap(int posx, int posy) {	// Überprüft den Arraybereich an der Angegebenen Stelle
		posx=(posx - (posx % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
		posy=(posy - (posy % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
    	boolean permission=true;
    	switch(LevelCreator.itemMap[posx][posy]) {	// Geht alle möglichen gespeicherten Tags in dem Arraybereich durch und aktualisiert permission und ggf. 'alive'
    	case 1:					// Wall
    		permission=false;
    		break;
    	case 4:					// Goal
			LevelCaller.setNextLevel();
    		break;
    	case 5:					// Final Goal
			LevelCaller.setFinalGoal();
    		break;
    	}
    	//checkItems();
    	return permission;
    }
    
    /*
    private void checkItems() {
		for(int a=0; a<Item.itemList.size(); a++) {		// geht Item für Item durch
			if(Item.itemList.get(a).valid==true) {				// nur überprüfen, wenn noch gültig
				if(Item.itemList.get(a).playerID==index) {			// wenn markiert für diesen Spieler:
					switch(Item.itemList.get(a).itemID) {
					case 11:	//	Shield
						invincible = true;
						break;
					}
				}
			}
		}
    }
    */
	
	private static void checkCollideWithMiscObjekts(int playerID) {
		// Goodies
		for(int goodieID=0; goodieID<Goodies.goodiesList.size(); goodieID++) {
			Goodies.checkPlayerCollideWithGoodie(playerID, goodieID);
		}
		
		// Checkpoints
		Checkpoints.checkPlayerCollideWithCheckpoint(playerID);
		
		// Traps
		for(int trapID=0; trapID<Traps.trapList.size(); trapID++) {
			Traps.checkPlayerCollideWithTraps(playerID, trapID);
		}
		
		// NPCs
		NPC.checkPlayerCollide();
	}
	/**     MOVEMENT END     */
    
    // LIFE STATUS
    public boolean getLifeStatus() {
    	return alive;
    }
    public void receiveLives(int heartCount) {
    	lives += heartCount;
    }
    
    /*
    public void setHealthPoints(int change) {
    	if(invincible==false && healthPoints>0) {
	    	healthPoints += change;
	    	if(healthPoints<=0)
	    		setLifeStatus(false);
	    	else
	    		setDisplayLives();
    	}
    }
    */
    public void reduceHealthPoints(int damage) {
    	if(invincible==false && healthPoints>0) {
	    	healthPoints -= (int)((double)damage * resistance);
	    	if(healthPoints<=0)
	    		setLifeStatus(false);
	    	else
	    		setDisplayLives();
    	}
    }
    
    private void setLifeStatus(boolean isAlive) {	// ändert 'alive' (vorallem für false) mit gegebenen Konsequenzen
    	if(invincible==false) {
	    	alive = isAlive;
	    	checkLifeStatus();
	    	setDisplayLives();
    	}
    }
    
    private void checkLifeStatus() {
    	if(showYouDiedImage==false && alreadyPunished==false) {	// nur wenn das Spiel läuft überprüfen
	    	if(alive==false) {				// Wenn alive=false, dann
	    		setPlayerImage();
	    		lives--;						// Leben: -1
	    		alreadyPunished = true;
	    		if(checkAllLivesGone()) {					// wenn alle Spieler tot sind, dann:
	    			LevelCaller.shutdownLevel();	// Löschen aller Levelparameter
		    		Runner.shutRunnerDown();
	    			LostWindow.main(null);
	    		} else {
	    			if(checkAllDead()) {
	    				showYouDiedImage = true;	// Meldung wird angezeigt und es wird angeboten das Level neu zu starten
	    			}
	    		}
	    	}
    	}
    }
    
    private void resetLevel() {
		for(int a=0; a<Player.playerList.size(); a++) {	// setzt alle spielerrelevanten Laufvariablen zurück
			if(playerList.get(a).lives>0) {				// nur zurücksetzen, wenn Spieler noch Leben hat
				playerList.get(a).healthPoints = playerList.get(a).healthPointsMax;
				playerList.get(a).alive = true;
				playerList.get(a).alreadyPunished = false;
				//playerList.get(a).checkDirection(-2);			// zurücksetzen des PlayerIcons
			}
		}
		LevelCaller.resetLevel();
		setDisplayLives();
    }
    
    private static boolean checkAllDead() {	// überprüft ob alle Spieler tot sind
		boolean allDead = true;		// Ausgangsposition
		for(int a=0; a<Player.playerList.size(); a++) {	// Überprüfen ob alle Spieler tot sind
			if(playerList.get(a).alive)			// wenn auch nur ein Spieler noch lebt, check=false
				allDead = false;
		}
    	return allDead;
    }
    
    private static boolean checkAllLivesGone() {	// überprüft ob alle Spieler ohne Leben sind
		boolean allLivesGones = true;		// Ausgangsposition
		for(int a=0; a<Player.playerList.size(); a++) {	// Überprüfen ob alle Spieler ohne Leben sind
			if(playerList.get(a).lives>0)			// wenn auch nur ein Spieler noch lebt, check=false
				allLivesGones = false;
		}
    	return allLivesGones;
    }
    // LIFE STATUS END
    
    public void setYouDiedScreenStatus(boolean display) {
    	if(showYouDiedImage) {
    		if(display==false) {
    			showYouDiedImage=display;	// somit = false
    			resetLevel();
    		}
    	} else {
    		if(display) {
    			showYouDiedImage=display;	// somit = true
    		}
    	}
    		
    }
    // KEY BINDINGS END	
}
