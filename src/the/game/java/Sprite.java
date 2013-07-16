package the.game.java;

import java.awt.Image;
import the.game.java.Damage;

/**
 * Von dieser Klasse Erben alle Sprites
 */
public class Sprite {
	// Position & Bewegung
	protected double x, y, mx, my, speed;
	protected boolean permissionX, permissionY;
    
    // Darstellung und Bilder
    private Image imgAl, imgDe;
    protected Image currentImg;
    private String imgAlPath, imgDePath;
    protected int imgSizeX, imgSizeY;
    
    // Leben und HP
    protected boolean alive;
    protected int lives, healthPoints, healthPointsMax;
    
    // Fähigkeiten und Eigenschaften
    public boolean invincible;
    private double resistance;
    protected int damage;
    protected int damageType=0;
    
    // Status Flags
    protected boolean fire;
    protected boolean alreadyPunished;
    
    // Statistik (für Network)
    public double lastx = -1;
    public double lasty = -1;
    
    /**
     * Konstruktor(1) der Klasse Sprite
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Objekts
	 * @param imgPathDe Pfad zum Bild eines besiegten Objekts
     * @param lifeCount Lebenspunkte
     */
    public Sprite(double posx, double posy, String imgPathAl, String imgPathDe, int lifeCount) {		// Konstruktor mit Angabe der Startposition
    	
        x = posx;					// default x position
        y = posy;					// default y position
        speed = 1;
        alive = true;				// Spielfigur ist lebendig
        lives = 3;					// setzt die Startanzahl der Leben fest
        healthPointsMax = 100;		// setzt die maximale Anzahl an Lebenspunkten fest
        healthPoints = healthPointsMax;	// setzt die Lebenspunkte fest
        alreadyPunished = false; 	// Verhindert unnötiges Abziehen von Leben (lives): true, sobald ein Spieler ein Leben verloren hat, wieder false, sobald das Level neugestartet wird
        invincible = false;			// spieler nicht unbesiegbar
        fire = false;				// kontrolliert das Schießen
        resistance = 1;				// Faktor für Schaden (wird z.B. für Schild gebraucht)
        damage = 0;					// Schaden, der angerichtet werden kann
        imgAlPath = imgPathAl;
    	imgDePath = imgPathDe;
        prepareSpriteImg();			// Player Image erstellen
        setCurrentImg();
    }

    /**
     * Konstruktor(2) der Klasse Sprite
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Objekts
	 * @param imgPathDe Pfad zum Bild eines besiegten Objekts
     */
    public Sprite(double posx, double posy, String imgPathAl, String imgPathDe) {		// Konstruktor mit Angabe der Startposition
	
	    x = posx;					// default x position
	    y = posy;					// default y position
	    speed = 1;
	    alive = true;				// Spielfigur ist lebendig
	    lives = 1;					// setzt die Startanzahl der Leben fest
	    healthPointsMax = 100;		// setzt die maximale Anzahl an Lebenspunkten fest
	    healthPoints = healthPointsMax;	// setzt die Lebenspunkte fest
	    alreadyPunished = false; 	// Verhindert unnötiges Abziehen von Leben (lives): true, sobald ein Spieler ein Leben verloren hat, wieder false, sobald das Level neugestartet wird
	    invincible = false;			// spieler nicht unbesiegbar
	    fire = false;				// kontrolliert das Schießen
	    resistance = 1;				// Faktor für Schaden (wird z.B. für Schild gebraucht)
	    imgAlPath = imgPathAl;
		imgDePath = imgPathDe;
	    prepareSpriteImg();			// Player Image erstellen
	    setCurrentImg();
    }
        
    
    /********************************************************
     * 
     * MOVEMENT
     * 
    /********************************************************/
    
    /** Benötigt für Aufruf
    public static void move() {
    	
    }
    */
    
    /**
     * Aktualisiere Position
     * Wenn Sprite lebendig, Positionsvariablen mit den Movevariablen verrechnen
     * @param spriteID ID des Sprites (index in SpriteList)
     */
    protected void doMove(int spriteID) {
    	if(alive) {
    		setMovementPermission();
    		if(Runner.codeRunning) {
    			if(permissionX) {
    				x += mx * speed;
    			}
    			if(permissionY){
    				y += my * speed;
    			}
    			furtherInstPostMove(spriteID);
    		}
    	}
    }
    
    /**
     * Weitere Instruktionen: Nach move()
     * Aufgerufen: Nach erfolgreicher Bewegung (MovePermission nicht relevant)
     * @param spriteID ID des Sprites (index in SpriteList)
     */
    protected void furtherInstPostMove(int spriteID) {	// wird nach bewegung ausgeführt (in doMove)
    	
    }
    
    
    /********************************************************
     * 
     * MOVEMENTPERMISSION AND COLLIDE
     * 
    /********************************************************/
	
    /**
     * Permission-Variablen für move() setzten
     * Überprüfe, ob Bewegung durch doMove() möglich und setze entsprechend die Permission-Variablen
     */
    protected void setMovementPermission() {	// Überprüft das Array 'itemMap' auf Gegenstände & Objekte in Bewegungsrichtung und erteilt Bewegungsfreigabe oder -verbot
    	permissionX = true;
    	permissionY = true;
    	double nextx = x + mx * speed;
    	double nexty = y + my * speed;
    	int borderXL = 0;			// Border left
    	int borderXR = Runner.getWidthF();//-21;			// Border right
    	int borderYU = 20;			// Border up
    	int borderYD = Runner.getHeightF();//-43;			// Border down
    	
		// Überprüfen ob Border passiert werden: XL (x-Achse, left)	XR (x-Achse, right)	YU (y-Achse, up)	YD (y-Achse, down)
    	if(mx<0) {					// Check Border: left
    		if(x<=borderXL)
    			permissionX=false;
    	} else if(mx>0) {				// Check Border: right
    		if(x>=borderXR)
	    		permissionX=false;
    	}
    	if(my<0) {					// Check Border: up
    		if(y<=borderYU)
	    		permissionY=false;
    	} else if(my>0) {				// Check Border: down
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
    	if(mx<0) {
    		if(my<0){				// left, up
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
    			if(checkItemMap(nextx, nexty + imgSizeY)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x, nexty + imgSizeY)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y + imgSizeY)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Oben rechts
    			if(checkItemMap(nextx + imgSizeX, nexty)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x + imgSizeX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imgSizeX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else if(my>0) {		// left, down
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
    			if(checkItemMap(nextx, nexty + imgSizeY)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x, nexty + imgSizeY)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y + imgSizeY)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten rechts
    			if(checkItemMap(nextx + imgSizeX, nexty + imgSizeY)==false) {	// 1. Beide blockiert
    				if(checkItemMap(x + imgSizeX, nexty + imgSizeY)==false) {		// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imgSizeX, y + imgSizeY)==false) {		// 1.1 X bei gleichem Y blockiert?
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
    			if(checkItemMap(nextx, nexty + imgSizeY)==false) {				// 1. Links blockiert
    				permissionX = false;
    			}
    		}	
    	} else if(mx>0) {
    		if(my<0){				// right, up
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
    			if(checkItemMap(nextx + imgSizeX, nexty)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x + imgSizeX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imgSizeX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten rechts
    			if(checkItemMap(nextx + imgSizeX, nexty + imgSizeY)==false) {	// 1. Beide blockiert
    				if(checkItemMap(x + imgSizeX, nexty)==false) {					// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imgSizeX, y)==false) {					// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else if(my>0) {		// right, down
    			// 1. Oben rechts
    			if(checkItemMap(nextx + imgSizeX, nexty)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x + imgSizeX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imgSizeX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten rechts
    			if(checkItemMap(nextx + imgSizeX, nexty + imgSizeY)==false) {	// 1. Beide blockiert
    				if(checkItemMap(x + imgSizeX, nexty + imgSizeY)==false) {		// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx + imgSizeX, y + imgSizeY)==false) {		// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten links
    			if(checkItemMap(nextx, nexty + imgSizeY)==false) {			// 1. Beide blockiert
    				if(checkItemMap(x, nexty + imgSizeY)==false) {				// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(nextx, y + imgSizeY)==false) {				// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else {				// right
    			// 1. Oben rechts (nach rechts)
    			if(checkItemMap(nextx + imgSizeX, nexty)==false) {				// 1. Rechts blockiert
    				permissionX = false;
    			}
    			// 2. Unten rechts
    			if(checkItemMap(nextx + imgSizeX, nexty + imgSizeY)==false) {	// 1. Rechts blockiert
    				permissionX = false;
    			}
    		}	
    	} else if(my<0) {   		// up
    		// 1. Fixpunkt (nach oben)
			if(checkItemMap(nextx, nexty)==false) {								// 1. Oben blockiert
				permissionY = false;
			}
			// 2. Oben rechts
			if(checkItemMap(nextx + imgSizeX, nexty)==false) {				// 1. Rechts blockiert
				permissionY = false;
			}
    	} else if(my>0) {			// down
    		// 1. Unten links (nach unten)
			if(checkItemMap(nextx, nexty + imgSizeY)==false) {				// 1. Unten blockiert
				permissionY = false;
			}
			// 2. Unten rechts
			if(checkItemMap(nextx + imgSizeX, nexty + imgSizeY)==false) {	// 1. Unten blockiert
				permissionY = false;
			}
    	}
    	furtherInstPostSetMovemPerm();
	}
    
    /**
     * Weitere Instruktionen: NACH 'setMovementPermission()'
     * Aufgerufen:	Am Ende von 'setMovementPermission()'
     */
    protected void furtherInstPostSetMovemPerm() {
    	
    }
    
    /**
     * Getter:Wert aus MapArray
     * Parameter in Pixel!
     * Überprüft, ob für gegebene Position Hindernisse vorliegen
     * und leitet gegebenenfalls Konsequenzen für Kollision ein.
     * Methodenaufrufe:
     * 	checkCollideOnMiscObjekts(posx, posy)
     * 	checkItemMapInstructions (posx, posy)
     * @param posx		x-Position auf Spielfeld	(Pixel)
     * @param posy		y-Position auf Spielfeld	(Pixel)
     * @return			Kollision: ja/nein
     */
    private boolean checkItemMap(double posx, double posy) {	// Überprüft den Arraybereich an der Angegebenen Stelle
    	if(checkCollideOnMiscObjekts(posx, posy))
    		return false;
    	posx=(posx - (posx % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
		posy=(posy - (posy % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
    	return checkItemMapInstructions(posx, posy);
    }
    
    /**
     * Wert in MapArray Abfragen und Konsequanzen setzen
     * Aufruf durch 'checkItemMap()'
     * Zum Überschreiben gedacht! In Urform keinen Zweck!
     * @param posx		x-Position in Array	(Felder)
     * @param posy		Y-Position in Array	(Felder)
     * @return			Kollision: ja/nen
     */
    protected boolean checkItemMapInstructions(double posx, double posy) {
    	switch(LevelCreator.itemMap[(int)posx][(int)posy]) {	// Geht alle möglichen gespeicherten Tags in dem Arraybereich durch und aktualisiert permission und ggf. 'alive'
    	case 1:					// Wall
    		return false;
    	}
    	return true;
    }
    
    /**
     * Methode für weitere Kollisionsüberprüfungen
     * Zum Überschreiben gedacht!
     * @param posx		x-Position in Array	(Felder)
     * @param posy		x-Position in Array	(Felder)
     * @return			Kollision: ja/nen
     */
    protected boolean checkCollideOnMiscObjekts(double posx, double posy) {	// Wenn true, dann nicht passierbares Objekt gefunden
    	return false;
    }
	
    
    /********************************************************
     * 
     * LIFE AND HP
     * 
    /********************************************************/
    
    /**
     * ANZAHL LEBEN ERHÖHEN
     * Erhöht die Anzahl von Leben für einen Spieler
     * 
     * @param heartCount	Anzahl Leben, die hinzugefügt werden
     */
    public void receiveLives(int heartCount) {
    	lives += heartCount;
    }
    
    /**
     * HP (Lebenspunke) VERMINDERN
     * 
     * 
     * @param damage
     * @param offenderType
     */
    public void reduceHealthPoints(int damage, int offenderType) {
    	if(invincible==false && healthPoints>0) {
    		// Schaden im System abfragen; Wenn Schaden == 0 : return
    		damage = Damage.getDamage(damage, offenderType, damageType);	// Schaden abfragen
    		
    		if(damage<=0)
    			return;
    		
	    	healthPoints -= ((double)damage * resistance);

	    	if(healthPoints<=0)
	    		setLifeStatus(false);
	    	else
	    		furtherInstPostHealthReductionAndAlive();
    	}
    }
    
    /**
     * Setzt den Lebensstatus und verwaltet die Konsequenzen
     * @param isAlive Wert, den der Lebensstatus erhält
     */
    private void setLifeStatus(boolean isAlive) {	// ändert 'alive' (vorallem für false) mit gegebenen Konsequenzen
    	if(invincible==false) {
	    	alive = isAlive;
	    	checkLifeStatus();
    	}
    }
    /**
     * Setzt den Lebensstatus durchs Netzwerk und verwaltet die Konsequenzen
     * @param isAlive Wert, den der Lebensstatus erhält
     */
    public void setLifeStatusFromNetwork(boolean isAlive) {	// ändert 'alive' (vorallem für false) mit gegebenen Konsequenzen
    	if(invincible==false) {
	    	alive = isAlive;
	    	if(alive) {
	    		currentImg = imgAl;
	    	} else {
	    		currentImg = imgDe;
	    	}
	    	furtherInstPostLifeStatusUpdateViaNetwork(isAlive);
    	}
    }
    
    /**
     * ZUM ÜBERSCHREIBEN
     * @param isAlive
     */
    protected void furtherInstPostLifeStatusUpdateViaNetwork(boolean isAlive) {
    	
    }
    
    private void checkLifeStatus() {
    	if(alive==false && alreadyPunished==false) {	// Wenn alive=false, dann
    		decrementLive();
    		alreadyPunished = true;
    		currentImg = imgDe;
    		setCurrentImg();
    		doOnDeath();
    	}
    }
    
    /**
     * Verringert Leben um 1
     */
    protected void decrementLive() {
    	lives--;						// Leben -1
    }
    /**
     * Zum Überschreiben
     */
    protected void furtherInstPostHealthReductionAndAlive() {
    	
    }
    /**
     * Was passiert nach Tod? Zum Überschreiben
     */
    protected void doOnDeath() {
    	
    }
    
    
    /********************************************************
     * 
     * DARSTELLUNG UND BILDER
     * 
    /********************************************************/
    
    protected void prepareSpriteImg() {
    	imgAl = DisplayManager.getImage(imgAlPath);
    	imgDe = DisplayManager.getImage(imgDePath);
    	imgSizeX = imgAl.getWidth(null);
    	imgSizeY = imgDe.getHeight(null);
    }
    
    
    /********************************************************
     * 
     * SETTER
     * 
    /********************************************************/
    /**     SET POSITION UND BEWEGUNG     */
    public void setMoveStatusX(int speed) {	// Für Steuerung
    	mx = speed;
    }
    public void setMoveStatusY(int speed) {	// Für Steuerung
    	my = speed;
    }
    public void setPosition(int posx, int posy) {		// Ändert position des Sprites
    	x  = posx;
    	y  = posy;
    }
    public void setPosition(int posx, int posy, double movx, double movy) {		// Ändert position des Sprites
    	x  = posx;
    	y  = posy;
    	mx = movx;
    	my = movy;
    }
    
    /**     SET DARSTELLUNG UND BILDER     */
    private void setCurrentImg() {
    	if(alive)
    		currentImg = imgAl;
    	else
    		currentImg = imgDe;
    }
        
    /**     SET STATUS FLAGS     */
    public void setFireStatus(boolean fireStatus) {		// Für Steuerung
    	fire = fireStatus;
    }

    /**     SET LEBEN UND HP     */
    public void setAbsoluteLives(int lifeCount) {
    	lives = lifeCount;
    }
    public void setAbsoluteHP(int hpCount) {
    	healthPoints = hpCount;
    }
    
    /**     FÄHIGKEITEN UND EIGENSCHAFTEN     */
    public void setResistance(double res) {
    	resistance = res;
    }
    
    
    /********************************************************
     * 
     * GETTER
     * 
    /********************************************************/
    
    /**     GET POSITION UND BEWEGUNG     */
    public int getX() {		// returns actual x-position
        return (int)x;
    }
    public int getY() {		// returns actual y-position
        return (int)y;
    }
    public double getMX() {		// returns actual mx
        return mx;
    }
    public double getMY() {		// returns actual my
        return my;
    }
    public int getXAsFieldPos() {
		return (int)((x-(x%20))/20);
	}
    public int getYAsFieldPos() {
		return (int)((y-(y%20))/20);
	}
    
    /**     GET LEBEN UND HP     */
    public boolean getLifeStatus() {
    	return alive;
    }
    public int getLives() {		// returns actual y-position
        return lives;
    }
    public int getHealthPoints() {		// returns actual y-position
        return healthPoints;
    }
    
    /**     GET DARSTELLUNG UND BILDER     */
    public Image getImg() {
    	return currentImg;
    }
    
    /**     GET STATUS FLAGS     */
    public boolean getFireStatus() {
    	return fire;
    }
}
