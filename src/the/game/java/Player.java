package the.game.java;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Player {
    /**
	 * Notiz zur Klasse:
	 * Diese Klasse erstellt Spieler. Wird ein Objekt dieser Klasse mit dem
	 * Konstruktor Player(int posx, int posy) erstellt, so wird eine Spielfigur an der in den Parametern festgelegten
	 * Stelle eingefügt.
	 * Erstellt wird die Spielfigur über die Methode: createPlayer(int posx, int posy)
	 * Organisiert werden die Spieler über die Liste: playerList
	 */
	
    private String playerIconPath = "right.png";	// Startbild

    private int mx;		// move x
    private int my;		// move y
    private int x;		// x position
    private int y;		// y position
    public int imageSizeX=15;
    public int imageSizeY=15;
    private Image playerIcon;	// playericon
    private ImageIcon ii;
    private boolean alive;
    public static List<Player> playerList = new ArrayList<Player>();
    private static int index;
    //private int defaultPosX;
    //private int defaultPosY;
    private int lives;
    public static boolean codeRunning=true;
    public static boolean showYouDiedImage=false;
    private boolean alreadyPunished;

    public Player(int posx, int posy) {		// Konstruktor mit Angabe der Startposition
        ii = new ImageIcon(this.getClass().getResource(playerIconPath));
        playerIcon = ii.getImage();
        x = posx;		// default x position
        y = posy;		// default y position
        alive = true;	// Spielfigur ist lebendig
        lives = 3;		// setzt die Startanzahl der Leben fest
        codeRunning = true;	// true, solange noch Objekte der Klasse Player gibt
        alreadyPunished = false; // Verhindert unnötiges Abziehen von Leben: true, sobald ein Spieler ein Leben verloren hat, wieder false, sobald das Level neugestartet wird
    }
    
    public static void createPlayer(int posx, int posy) {	// erstellt neuen Spieler
    	playerList.add(new Player(posx, posy));		// Objekt wird in Liste geschrieben
    	setDisplayLives();							// Lebensanzeige wird aktualisiert
    }
    
    public static void resetAllPlayerPositions() {
		for(int a=0; a<Player.playerList.size(); a++) {			
			playerList.get(a).x = LevelCaller.getPlayerDefaultPosX();				// default x position
			playerList.get(a).y = LevelCaller.getPlayerDefaultPosY()+(a*(playerList.get(a).imageSizeY+1));		// default y position; +(a*16), um für verstzte Startpunkte zu sorgen, bei mehreren Spielern
		}
    }
    
    // BILD ANIMATION
    private void setPlayerIcon(String path) {		// Bildvariable erhält neuen Pfad; Setter.print() aktualisiert das neue Bild
    	ii = new ImageIcon(this.getClass().getResource(path));
    	playerIcon = ii.getImage();
    }
    
    private void switchPlayerIcon(int flag) {		// flag gibt an, in welche Richtung sich Player bewegt und setzt richtungsabhängig das richtige Bild
    		// Left, Right, Up, Down    	
    		switch(flag) {
    		// 1. GERADE	MOVING
	    	case 7:	// Moving - left
	    		setPlayerIcon("left.png");
		    	break;
	    	case 3:	// Moving - right
	    		setPlayerIcon("right.png");
		    	break;
	    	case 1:	// Moving - up
		    	setPlayerIcon("up.png");
		    	break;
	    	case 5:	// Moving - down
		    	setPlayerIcon("down.png");
		    	break;  
		    // 2. SCHRÄG	MOVING
	    	case 2:	// Moving - up-right
		    	setPlayerIcon("upright.png");
		    	break;
	    	case 4:	// Moving - down-right
		    	setPlayerIcon("downright.png");
		    	break;
	    	case 6:	// Moving - down-left
		    	setPlayerIcon("downleft.png");
		    	break;
	    	case 8:	// Moving - up-left
		    	setPlayerIcon("upleft.png");
		    	break;
		    // 3. GERADE	STANDING STILL
		    case 17:	// Standing still - left
			    setPlayerIcon("standing_left.png");
			    break;
		    case 13:	// Standing still - right
			    setPlayerIcon("standing_right.png");
			    break;
		    case 11:	// Standing still - up
			    setPlayerIcon("standing_up.png");
			    break;
		    case 15:	// Standing still - down
			    setPlayerIcon("standing_down.png");
			    break;
    		}
    }
    // BILD ANIMATION END

    // REQUESTS
    public int getX() {		// returns actual x-position
        return x;
    }
    public int getY() {		// returns actual y-position
        return y;
    }
    public Image getPlayerIcon() {		// returns playericon
        return playerIcon;
    }
    // REQUESTS END
    
    // MOVEMENT RELEVANT
    public static void move() {	// Rechnung: (aktuelle Position + Bewegung für x- oder y-Achse) für jeden Aufruf
    	for(int a=0; a<playerList.size(); a++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
    		index = a;
    		if(playerList.get(index).alive) {								// Nur Bewegen, wenn Spieler lebendig
		    	int permission = playerList.get(index).checkMovementPermission();	// Umgebung auf Hindernisse überprüfen und ErlaubnisID einholen
		    	if(codeRunning) {
		    		if(permission<2){													// ID >= 2: Bewegen nicht erlaubt
				    	if(permission<=0)
				    		playerList.get(index).x += playerList.get(index).mx;		// ID = 0: Beide Achsen aktualisieren.	> 0: nur x-Achse
				    	if(permission>=0)
				    		playerList.get(index).y += playerList.get(index).my;		// ID = 0: Beide Achsen aktualisieren.	< 0: nur y-Achse
		    		}
		    	}
    		}
    	}
    }
        
    private int checkMovementPermission() {	// Überprüft das Array 'itemMap' auf Gegenstände & Objekte in Bewegungsrichtung und erteilt Bewegungsfreigabe oder -verbot
    	boolean permissionX = true;
    	boolean permissionY = true;
    	boolean permission = true;
    	int perm = 0;
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

    	// Übersetzen der permission-Parameter in die FreigabeID für 'move()'
    	if(permission==false)								// keine Bewegung erlaubt
    		perm = 2;
    	else if(permissionX==false && permissionY==false)	// keine Bewegung erlaubt
    		perm = 3;
    	else if(permissionX==false)							// x-Achse erlaubt
    		perm = 1;
    	else if(permissionY==false)							// y-Achse erlaubt
    		perm = -1;
    	else												// jede Bewegung erlaubt
    		perm = 0;
    	return perm;
	}
    
    
    private boolean checkItemMap(int posx, int posy) {	// Überprüft den Arraybereich an der Angegebenen Stelle
		posx=(posx - (posx % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
		posy=(posy - (posy % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
    	boolean permission=true;
    	switch(LevelCreator.itemMap[posx][posy]) {	// Geht alle möglichen gespeicherten Tags in dem Arraybereich durch und aktualisiert permission und ggf. 'alive'
    	case 1:					// Wall
    		permission=false;
    		break;
    	case 2:					// Trap	EVENTUELL EINE EIGENE KLASSE FÜR TRAPS
    		// Collision nur wenn Mitte passiert wird!
			if(checkPlayerCollideOnCenter(posx, posy)) {
				Player.playerList.get(index).setLifeStatus(false);
				LevelCreator.itemMap[posx][posy]=-2;
			}
    		break;
    	case 3:					// Enemy
    		permission=true;
    		playerList.get(index).setLifeStatus(false);
    		break;
    	case 4:					// Goal
			if(checkPlayerCollideOnCenter(posx, posy)) {
				LevelCaller.setNextLevel();
			}
    		break;
    	case 5:					// Final Goal
			if(checkPlayerCollideOnCenter(posx, posy)) {
				LevelCaller.setFinalGoal();
			}
    		break;
    	case 10:				// Health
    		lives++;
    		LevelCreator.itemMap[posx][posy] = 0;
    		break;
    	}
    	setDisplayLives();		// Aktualisiert die Lebensanzeige
    	return permission;
    }
    
	private boolean checkPlayerCollideOnCenter(int posx, int posy) {			// Kontrolle der 4 Eckpunkte des Monsters
		boolean colliding=false;
		for(int a=0; a<Player.playerList.size(); a++) {	// Wird für alle Player nacheinander überprüft
			// Positionen der Kanten initialisieren
			int mapPosLeft = posx * 20;			// Position in Pixel:	Position als Planquadrat (itemMap) mal 20 Pixel (=Planquadratbreite)
			int mapPosRight = posx * 20 + 20;
			int mapPosUp = posy * 20;
			int mapPosDown = posy * 20 + 20;
			int playerMapPosLeft = getX();
			int playerMapPosRight = getX() + imageSizeX;
			int playerMapPosUp = getY();
			int playerMapPosDown = getY() + imageSizeY;
			
			// Ecken überprüfen (Überprüft wird die "aktuelle" Position)
			// Es wird überprüft ob der Mittelpunkt des Gegners innerhalb des von dem Spieler belegten Intervalls liegt
			// wenn ja, stirbt der Spieler
			if((playerMapPosLeft<=(mapPosLeft+(mapPosRight-mapPosLeft)/2)) && ((mapPosLeft+(mapPosRight-mapPosLeft)/2) <= playerMapPosRight) && (playerMapPosUp <= (mapPosUp+(mapPosDown-mapPosUp)/2)) && ((mapPosUp+(mapPosDown-mapPosUp)/2) <= playerMapPosDown))		// 5. Mittelpunkt
				colliding = true;
		}
			return colliding;
	}
    // MOVEMENT RELEVANT END
    
    // LIFE STATUS
    public boolean getLifeStatus() {
    	return alive;
    }
    
    public void setLifeStatus(boolean isAlive) {	// ändert 'alive' (vorallem für false) mit gegebenen Konsequenzen
    	alive = isAlive;
    	checkLifeStatus();
    	setDisplayLives();
    }
    
    private void checkLifeStatus() {
    	if(showYouDiedImage==false && alreadyPunished==false) {	// nur wenn das Spiel läuft überprüfen
	    	if(alive==false) {				// Wenn alive=false, dann
	    		setPlayerIcon("dead.png");	// PlayerIcon für toten Spieler
	    		lives--;						// Leben: -1
	    		alreadyPunished = true;
	    		if(checkAllLivesGone()) {					// wenn alle Spieler tot sind, dann:
	    			codeRunning = false;			// Flag, die verhindert, dass Codereste ausgeführt werden, wenn bereits keine Objekte mehr existieren
		    		Enemy.codeRunning = false;
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
				playerList.get(a).alive = true;
				playerList.get(a).alreadyPunished = false;
				playerList.get(a).checkDirection(-2);			// zurücksetzen des PlayerIcons
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
    
    private static void setDisplayLives() {
    	resetDisplayLine();
    	int c=0;
		for(int a=0; a<Player.playerList.size(); a++) {
			for(int b=0; b<playerList.get(a).lives; b++) {
				LevelCreator.itemMap[b+c][0] = 10;			// Setzt die Stellen in der IconMap auf 10 für das Bild 'heart'
			}
			c+=5;	// Rückt 5 Spalten weiter, um Abstand zu schaffen für Lebensanzeige eines weiteren Spielers
		}
    }
    
    private static void resetDisplayLine() {		// Setzt die oberste Menuleiste zurück (leer)
		for(int a=0; a<LevelCreator.getItemMapDimensions(0); a++) {
			LevelCreator.itemMap[a][0] = 0;			// Setzt die Stellen in der IconMap auf 0 zurück
		}
    }
    // LIFE STATUS END

    // KEY BINDINGS
    public void keyPressed(KeyEvent event) {	// Wenn Tastatur benutzt wird folgende Tasten überprüfen:
        int key = event.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {			// <
        	mx = -1;							// MoveVariable x-Achse (gibt an wie viel Pixel sich der Spieler bewegt pro Aufruf von 'move()')
        	if(alive)
        		checkDirection(1);				// PlayerIcon für jeweilige Richtung anpassen
        }
        if (key == KeyEvent.VK_RIGHT) {			// >
        	mx = 1;								// MoveVariable x-Achse
        	if(alive)
        		checkDirection(2);				// PlayerIcon ...
        }
        if (key == KeyEvent.VK_UP) {			// ^
        	my = -1;							// MoveVariable y-Achse
        	if(alive)
        		checkDirection(3);				// PlayerIcon ...
        }
        if (key == KeyEvent.VK_DOWN) {			// v
        	my = 1;								// MoveVariable y-Achse
        	if(alive)
        		checkDirection(4);				// PlayerIcon ...
        }
    }
    public void keyReleased(KeyEvent event) {	// Wenn Taste nach Drücken wieder losgelassen wird, sonst wie oben
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            mx = 0;								// MoveVariable x-Achse wird auf 0 gesetzt, damit Figut stehen bleibt, wenn Taste losgelassen wird
            if(alive)
        		checkDirection(-1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            mx = 0;								// MoveVariable x-Achse ...
            if(alive)
        		checkDirection(-2);
        }
        if (key == KeyEvent.VK_UP) {
            my = 0;								// MoveVariable y-Achse ...
            if(alive)
        		checkDirection(-3);
        }
        if (key == KeyEvent.VK_DOWN) {
            my = 0;								// MoveVariable y-Achse ...
            if(alive)
        		checkDirection(-4);
        }
        if (key == KeyEvent.VK_SPACE) {
            if(showYouDiedImage) {
            	showYouDiedImage=false;
            	resetLevel();
            }
            
        }
    }
    
    public void keyPressed_wasd(KeyEvent event) {	// Wenn Tastatur benutzt wird folgende Tasten überprüfen:
        int key = event.getKeyCode();
        
        if (key == KeyEvent.VK_A) {			// <
        	mx = -1;							// MoveVariable x-Achse (gibt an wie viel Pixel sich der Spieler bewegt pro Aufruf von 'move()')
        	if(alive)
        		checkDirection(1);				// PlayerIcon für jeweilige Richtung anpassen
        }
        if (key == KeyEvent.VK_D) {			// >
        	mx = 1;								// MoveVariable x-Achse
        	if(alive)
        		checkDirection(2);				// PlayerIcon ...
        }
        if (key == KeyEvent.VK_W) {			// ^
        	my = -1;							// MoveVariable y-Achse
        	if(alive)
        		checkDirection(3);				// PlayerIcon ...
        }
        if (key == KeyEvent.VK_S) {			// v
        	my = 1;								// MoveVariable y-Achse
        	if(alive)
        		checkDirection(4);				// PlayerIcon ...
        }
    }
    public void keyReleased_wasd(KeyEvent event) {	// Wenn Taste nach Drücken wieder losgelassen wird, sonst wie oben
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_A) {
            mx = 0;								// MoveVariable x-Achse wird auf 0 gesetzt, damit Figut stehen bleibt, wenn Taste losgelassen wird
            if(alive)
        		checkDirection(-1);
        }
        if (key == KeyEvent.VK_D) {
            mx = 0;								// MoveVariable x-Achse ...
            if(alive)
        		checkDirection(-2);
        }
        if (key == KeyEvent.VK_W) {
            my = 0;								// MoveVariable y-Achse ...
            if(alive)
        		checkDirection(-3);
        }
        if (key == KeyEvent.VK_S) {
            my = 0;								// MoveVariable y-Achse ...
            if(alive)
        		checkDirection(-4);
        }
    }

    private void checkDirection(int flag) {		// Überprüft die Laufrichtung und setzt ein flag, dass diese an 'switchPlayerIcon()' übergibt, um das richtige Bild anzuzeigen
    	int checkSum;
	    boolean k1=false;	// Pressed key	Left
	    boolean k2=false;	// Pressed key	Right
	    boolean k3=false;	// Pressed key	Up
	    boolean k4=false;	// Pressed key	Down
	    boolean ks1=false;	// Released key	Left
	    boolean ks2=false;	// Released key	Right
	    boolean ks3=false;	// Released key	Up
	    boolean ks4=false;	// Released key	Down
    	
	    // Übersetzen der flags in obige boolean Werte
	    if(flag==1)
    		k1=true;
    	else if(flag==-1)
    		ks1=true;
    	if(flag==2)
    		k2=true;
    	else if(flag==-2)
    		ks2=true;
    	if(flag==3)
    		k3=true;
    	else if(flag==-3)
    		ks3=true;
    	if(flag==4)
    		k4=true;
    	else if(flag==-4)
    		ks4=true;
    	
    	// Ermitteln der Laufrichtung durch die boolean werten von oben und die MoveVariablen mx und my.
    	checkSum = mx + my;
    	// MOVING
    	if(k1) {
    		if(checkSum>-1) 		// down-left
    			flag=6;
    		else if(checkSum<-1)	// up-left
    			flag=8;
    		else					// left
    			flag=7;
    	} else if(k2) {
    		if(checkSum>1) 			// down-right
    			flag=4;
    		else if(checkSum<1)		// up-right
    			flag=2;
    		else					// right
    			flag=3;
    	} else if(k3) {
    		if(checkSum>-1) 		// up-right
    			flag=2;
    		else if(checkSum<-1)	// up-left
    			flag=8;
    		else					// up
    			flag=1;
    	} else if(k4) {
    		if(checkSum>1) 			// down-right
    			flag=4;
    		else if(checkSum<1)		// down-left
    			flag=6;
    		else					// down
    			flag=5;
    	// STANDING STILL
    	} else if(ks1) {
    		if(checkSum==0)			// left
    			flag=17;	
    		else if(checkSum>0)		// down
    			flag=5;			
    		else if(checkSum<0)		// up
    			flag=1;
    	} else if(ks2) {
    		if(checkSum==0)			// right
    			flag=13;			
    		else if(checkSum>0)		// down
    			flag=5;
    		else if(checkSum<0)		// up
    			flag=1;
    	} else if(ks3) {
    		if(checkSum==0)			// up
    			flag=11;
    		else if(checkSum>0)		// right
    			flag=3;
    		else if(checkSum<0)		// left
    			flag=7;
    	} else if(ks4) {
    		if(checkSum==0)			// down
    			flag=15;			
    		else if(checkSum>0)		// right
    			flag=3;
    		else if(checkSum<0)		// left
    			flag=7;
    	}
    	switchPlayerIcon(flag);
    }
    // KEY BINDINGS END	
}
