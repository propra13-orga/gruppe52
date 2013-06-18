package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * Verwaltet alle Objekte der Klasse Enemy
 *
 */
public class Enemy {
	
	private int x;
	private int y;
	private int mx=0;
	private int my=0;
	public static List<Enemy> monsterList = new ArrayList<Enemy>();				// Alle Enemys werden in monsterList 'archiviert'
	private static int index;
	private int imgSizeMonsterX;
	private int imgSizeMonsterY;
	private int enemyType;
	private int healthPoints;
	private boolean alive;
	private Image imgEnemy;
	private Image imgEnemyDead;
	private ImageIcon ii;
	private int damage;
	
	/*
	 * Konstruktor 1: Nur Startposition angegeben: Bewegung nur horizontal 
	 * @param posx = Startpos x
	 * @param posy = Startpos y
	 */
	public Enemy(int posx, int posy) {	
		x=posx;											// x = aktuelle Position x = Startpos x
		y=posy;											// y = aktuelle Position y= Startpos y
		mx=1;											// Horizontale Bewegung
		enemyType = 0;									// kein Bouncy
		healthPoints = 100;								// Enemy bekommt 100 Lebenspunkte
		alive = true;									// Monster existiert & ist in Bewegung
		imgEnemy = setImage("enemy.gif");				// Bild für lebendiges Monster auf map
		imgEnemyDead = setImage("trap.png");			// Bild für gestorbenes Monster auf map
		imgSizeMonsterX = 15;							// Breite des Monsters
		imgSizeMonsterY = 19;							// Höhe des Monsters
		damage = 80;									// Schaden den der Player bei Berührung mit Monster erleidet
	}
	
	/*
	 * Konstruktor 2: Startposition und Laufvariablen angegeben: Bewegung horizontal, vertikal oder diagonal
	 * @param posx = Startpos x
	 * @param posy = Startpos y
	 * @param moveX = Bewegung horizontal; 1 = rechts; -1 = links; Höhere Zahlen bedeuten schnellere Bewegung
	 * @param moveY = Bewegung vertikal; 1 = unten; -1 = oben; Höhere Zahlen bedeuten schnellere Bewegung
	 */
	public Enemy(int posx, int posy, int moveX, int moveY) {	
		x = posx;								// xPosition = posx
		y = posy;								// yPosition = posy
		mx = moveX;								// Bewegung in Richtung x (rechts)
		my = moveY;								// Bewegung in Richtung y (unten)
		enemyType = 0;							// kein Bouncy
		healthPoints = 100;						// Enemy bekommt 100 Lebenspunkte
		alive = true;							// Monster existiert & ist Bewegung
		imgEnemy = setImage("enemy.gif");		// Bild für lebendiges Monster auf map
		imgEnemyDead = setImage("trap.png");	// Bild für gestorbenes Monster auf Map
		imgSizeMonsterX = 15;					// Breite des Enemys
		imgSizeMonsterY = 19;					// Höhe des Enemys
		damage = 80;							// Schaden den der Player bei Berührung mit Monster erleidet
	}

	/*
	 * Konstruktor 3: Startposition, Laufvariablen (moveX!=0 und moveY!=0) und Type=1 angegeben: Bouncy (Flummi-artige Bewegung); 
	 * Type=0 und moveX=0 oder moveY=0: normales Monster wie Konstruktor 2
	 * @param posx = Startpos x
	 * @param posy = Startpos y
	 * @param moveX  = Bewegung horizontal; 1 = rechts; -1 = links; Höhere Zahlen bedeuten schnellere Bewegung
	 * @param moveY = Bewegung vertikal; 1 = unten; -1 = oben; Höhere Zahlen bedeuten schnellere Bewegung
	 * @param type = 1 für Bouncy, 0 für normales Monster
	 */
	public Enemy(int posx, int posy, int moveX, int moveY, int type) {
		x = posx;								// xPosition = posx
		y = posy;								// yPosition = posy
		mx = moveX;								// Bewegung in richtung x (rechts)
		my = moveY;								// Bewegung in richtung y (unten)
		enemyType = type;						// 1 für Bouncy, 2 für normal
		healthPoints = 100;						// 100 Lebenspunkte für Bouncy
		alive = true;							// Bouncys existieren und sind in Bewegung
		imgEnemy = setImage("enemy.gif");		// Bild für lebendiges Bouncy auf map
		imgEnemyDead = setImage("trap.png");	// Bild für gestorbenes Bouncy auf map
		imgSizeMonsterX = 15;					// Breite des Bouncys
		imgSizeMonsterY = 19;					// Höhe des Bouncys
		damage = 80;							// Schaden den der Player bei Berührung mit Bouncy erleidet
	}
	
	/*
	 * Erstellt ein Monster mit horizontaler Bewegung und fügt es der monsterList hinzu
	 * @param pointX = Startposx
	 * @param pointY = Startposy
	 */
	public static void createMonster(int pointX, int pointY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		monsterList.add(new Enemy(pointX, pointY));
	}
	/*
	 * Erstellt ein Monster mit horizontaler, vertikaler oder diagonaler Bewegung und fügt es der MonsterList hinzu
	 * @param pointX = startpos x
	 * @param pointY = startpos y
	 * @param moveX = Bewegung horizontal; 1 = rechts; -1 = links; Höhere Zahlen bedeuten schnellere Bewegung
	 * @param moveY = Bewegung vertikal; 1 = unten; -1 = oben; Höhere Zahlen bedeuten schnellere Bewegung
	 */
	public static void createMonster(int pointX, int pointY, int moveX, int moveY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		monsterList.add(new Enemy(pointX, pointY, moveX, moveY));
	}
	/*
	 * Erstellt ein Bouncy, dass sich standartmäßig zuerst nach unten-rechts bewegt
	 * @param pointX = Startpos x
	 * @param pointY = Startpos y
	 */
	public static void createBouncy(int pointX, int pointY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		monsterList.add(new Enemy(pointX, pointY, 1, 1, 1));
	}
	/*
	 * Erstellt ein Bouncy dessen Beginn-Laufrichtung von moveX und moveY abhängt
	 * @param pointX
	 * @param pointY
	 * @param moveX
	 * @param moveY
	 */
	public static void createBouncy(int pointX, int pointY, int moveX, int moveY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		monsterList.add(new Enemy(pointX, pointY, moveX, moveY, 1));
	}
	/*
	 * Erstellt ein Monster, dass ein Boncy bei type=0 oder ein normales Monster bei type=0 ist
	 * @param pointX = Startpos x
	 * @param pointY = Startpos y
	 * @param moveX  = Bewegung horizontal; 1 = rechts; -1 = links; Höhere Zahlen bedeuten schnellere Bewegung
	 * @param moveY = Bewegung vertikal; 1 = unten; -1 = oben; Höhere Zahlen bedeuten schnellere Bewegung
	 * @param type = 1 für Bouncy, 0 für normales Monster
	 */
	public static void createMonster(int pointX, int pointY, int moveX, int moveY, int type) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		monsterList.add(new Enemy(pointX, pointY, moveX, moveY, type));
	}
	
	/*
	 * bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
	 */
    private Image setImage(String path) {
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
	
	/*
	 *  Abfragen zur aktuellen Position eines Gegners
	 */
	public static int getX(int listIndex) {
		return monsterList.get(listIndex).x;
	}
	public static int getY(int listIndex) {
		return monsterList.get(listIndex).y;
	}
	/*
	 *  Abfragen zur Größe des Monsters
	 */
	public static int getImgSizeX(int listIndex) {
		return monsterList.get(listIndex).imgSizeMonsterX;
	}
	public static int getImgSizeY(int listIndex) {
		return monsterList.get(listIndex).imgSizeMonsterY;
	}
	/*
	 * Organisiert welches Bild für jedes Monster der monsterList angezeigt wird;
	 * abhängig von Lebensstatus des Monsters
	 */
	public static Image getImg(int index) {
		if(monsterList.get(index).alive)
			return monsterList.get(index).imgEnemy;
		else
			return monsterList.get(index).imgEnemyDead;
	}
	/* 
	 * Sonstige Abfragen
	 */
	public static int getType(int listIndex) {
		return monsterList.get(listIndex).enemyType;
	}
	public static int getMX(int listIndex) {
		return monsterList.get(listIndex).mx;
	}
	public static int getMY(int listIndex) {
		return monsterList.get(listIndex).my;
	}
	public static boolean isAlive(int listIndex) {
		return monsterList.get(listIndex).alive;
	}
	
	/*
	 *  Lebenspunkte verringern
	 */
	public void reduceHealthPoints(int reduce) {
		if(healthPoints>0) {
			healthPoints -= reduce;
			if(healthPoints<=0) {
				alive = false;
				Goodies.createCredits(x, y, 20); 			// Punkte für den Score können eingesammelt werden an 
			}
		}
	}
	
	/*
	 *  move-Methode für alle Gegner
	 */
	public static void move() {		// wird aus Setter aufgerufen und aktualisiert die Position aller Gegner
		if(TemporaryItem.flagEnemyFreezed)
			return;
		
		int faktor=1;				// Multiplikator für die Geschwindigkeit normaler Gegner
		for(int a=0; a<monsterList.size(); a++) {	// Schleife um alle Gegner in der Liste anzusprechen
			index = a;
			if(monsterList.get(index).alive) {	// Bewegung nur gestattet, wenn Gegner lebendig
				switch(monsterList.get(index).enemyType) {
					case 0:		// Bewegung für normalen Feind
						if(monsterList.get(index).checkEnvironment() == false) {
							if(Runner.codeRunning) {
								monsterList.get(index).x += monsterList.get(index).mx*faktor;
								monsterList.get(index).y += monsterList.get(index).my*faktor;
							}
						} else {		// Wenn nicht passierbares Objekt Getroffen wird, dann Laufvariablen invertieren
							if(Runner.codeRunning) {
								
									if(monsterList.get(index).mx!=0)
										monsterList.get(index).mx = monsterList.get(index).mx * (-1);
									if(monsterList.get(index).my!=0)
										monsterList.get(index).my = monsterList.get(index).my * (-1);
							}
						}
						break;
					case 1:		// Bewegung für Bouncies
						monsterList.get(index).setBouncyMovement();
						monsterList.get(index).x += monsterList.get(index).mx;
						monsterList.get(index).y += monsterList.get(index).my;
						monsterList.get(index).checkPlayerCollide();
						break;
				}
			}
		}
	}
	
	//TODO: ÜBERARBEITEN!
	private boolean checkEnvironment() {	// Kontrolle der 4 Eckpunkte des Monsters
		boolean colliding=checkCollideOnMiscObjekts(x-1+mx, y+1+my); // Diverse Objekte prüfen;
		if(colliding==false) {
			// Positionen der Kanten initialisieren
			int mapPosLeft = (x+mx-((x+mx)%20))/20;
			int mapPosRight = (x+mx+imgSizeMonsterX-((x+mx+imgSizeMonsterX)%20))/20;
			int mapPosUp = (y+my-((y+my)%20))/20;
			int mapPosDown = (y+my+imgSizeMonsterY-((y+my+imgSizeMonsterY)%20))/20;
			// Ecken überprüfen (Überprüft wird die "künftige" Position)
			if(LevelCreator.itemMap[mapPosLeft][mapPosUp]>0)					// 1. Fixpunkt
				colliding = true;
			if(LevelCreator.itemMap[mapPosRight][mapPosUp]>0)					// 2. Oben rechts
				colliding = true;
			if(LevelCreator.itemMap[mapPosRight][mapPosDown]>0)					// 3. Unten rechts
				colliding = true;
			if(LevelCreator.itemMap[mapPosLeft][mapPosDown]>0)					// 4. Unten links
				colliding = true;
		}
		checkPlayerCollide();	// Überprüft, ob der Gegner mit einem Player kollidiert, falls ja wird lebensstatus des Spielers aktualisiert
		return colliding;
	}
	
	private void checkPlayerCollide() {		// Kontrolle der 4 Eckpunkte des Monsters
		for(int a=0; a<Player.playerList.size(); a++) {	// Wird für alle Player nacheinander überprüft
			
			boolean colliding = Intersect.isCollidingWithPlayer(a, monsterList.get(index).x, monsterList.get(index).y, imgSizeMonsterX, imgSizeMonsterY);
	
			if(colliding) {										// Wenn Gegner mit Spieler kollidiert:
				Player.playerList.get(a).reduceHealthPoints(damage);	// Lebenspunkte abziehen -> wenn keine mehr übrig Spieler tot
			}
		}
	}
	
	private void setBouncyMovement() {
		// Überprüft Status (0: nichts im Weg, Bewegung frei)
		switch(checkMovementPermission()) {
		case 3:		// Laufrichtung wird komplett invertiert
			mx *= (-1);
			my *= (-1);
			break;
		case 1:		// Laufrichtung der y-Achse wird invertiert	(bei Aufprall gegen horizontale Wand)
			my *= (-1);
			break;
		case -1:	// Laufrichtung der x-Achse wird invertiert	(bei Aufprall gegen vertikale Wand)
			mx *= (-1);
			break;
		}
	}
	
	private boolean checkItemMap(int posx, int posy) {	// Überprüft den Arraybereich an der angegebenen Stelle
		boolean permission = !checkCollideOnMiscObjekts(posx, posy); // Diverse Objekte prüfen
		if(permission) {
			posx=(posx - (posx % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
			posy=(posy - (posy % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
		    if(LevelCreator.itemMap[posx][posy]>0)
		    	permission = false;		// Falls irgendein Objekt im Weg, ist permission = false
		}
	    return permission;
	}
	
	private boolean checkCollideOnMiscObjekts(int posx, int posy) {
		for(int a=0; a<Traps.trapList.size(); a++) {
			if(Intersect.isCollidingWithTrap(a, posx, posy, 1, 1)) {
				return true;
			}
		}
		return false;
	}
	
	private int checkMovementPermission() {	// Überprüft das Array 'itemMap' auf Gegenstände & Objekte in Bewegungsrichtung und erteilt Bewegungsfreigabe oder -verbot
    	boolean permissionX = true;
    	boolean permissionY = true;
    	int perm = 0;
    	int nextx = x + mx;
    	int nexty = y + my;
    	int borderXL = 0;			// Border left
    	int borderXR = Runner.getWidthF();			// Border right
    	int borderYU = 20;			// Border up
    	int borderYD = Runner.getHeightF();			// Border down
		
		// Überprüfen ob Border passiert werden: XL (x-Achse, left)	XR (x-Achse, right)	YU (y-Achse, up)	YD (y-Achse, down)
    	if(mx==-1) {					// Check Border: left
    		if(x<=borderXL) {
    			permissionX=false;
    			permissionY=false;
    		}
    	} else if(mx==1) {				// Check Border: right
    		if(x>=borderXR) {
	    		permissionX=false;
    			permissionY=false;
    		}
    	}
    	if(my==-1) {					// Check Border: up
    		if(y<=borderYU) {
	    		permissionY=false;
	    		permissionY=false;
    		}
    	} else if(my==1) {				// Check Border: down
    		if(y>=borderYD) {
	    		permissionY=false;
    			permissionY=false;
    		}
    	}
    	if(permissionY==true && permissionX==true) {	// falls border berührt werden, überprüfung unnötig
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
	    					permissionY = false;
	    				}
	    			}
	    			// 2. Unten links
	    			if(checkItemMap(nextx, nexty + imgSizeMonsterY)==false) {			// 1. Beide blockiert
	    				if(checkItemMap(x, nexty + imgSizeMonsterY)==false) {				// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx, y + imgSizeMonsterY)==false) {				// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    			// 3. Oben rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty)==false) {			// 1. Beide blockiert
	    				if(checkItemMap(x + imgSizeMonsterX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx + imgSizeMonsterX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
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
	    					permissionY = false;
	    				}
	    			}
	    			// 2. Unten links
	    			if(checkItemMap(nextx, nexty + imgSizeMonsterY)==false) {			// 1. Beide blockiert
	    				if(checkItemMap(x, nexty + imgSizeMonsterY)==false) {				// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx, y + imgSizeMonsterY)==false) {				// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    			// 3. Unten rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {	// 1. Beide blockiert
	    				if(checkItemMap(x + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {		// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx + imgSizeMonsterX, y + imgSizeMonsterY)==false) {		// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    		} else {				// left
	    			// 1. Fixpunkt
	    			if(checkItemMap(nextx, nexty)==false) {								// 1. Links blockiert
	    				permissionX = false;
	    			}
	    			// 2. Unten links
	    			if(checkItemMap(nextx, nexty + imgSizeMonsterY)==false) {				// 1. Links blockiert
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
	    					permissionX = false;
	    				}
	    			}
	    			// 2. Oben rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty)==false) {			// 1. Beide blockiert
	    				if(checkItemMap(x + imgSizeMonsterX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx + imgSizeMonsterX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    			// 3. Unten rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {	// 1. Beide blockiert
	    				if(checkItemMap(x + imgSizeMonsterX, nexty)==false) {					// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx + imgSizeMonsterX, y)==false) {					// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    		} else if(my==1) {		// right, down
	    			// 1. Oben rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty)==false) {			// 1. Beide blockiert
	    				if(checkItemMap(x + imgSizeMonsterX, nexty)==false) {				// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx + imgSizeMonsterX, y)==false) {				// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    			// 2. Unten rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {	// 1. Beide blockiert
	    				if(checkItemMap(x + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {		// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx + imgSizeMonsterX, y + imgSizeMonsterY)==false) {		// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    			// 3. Unten links
	    			if(checkItemMap(nextx, nexty + imgSizeMonsterY)==false) {			// 1. Beide blockiert
	    				if(checkItemMap(x, nexty + imgSizeMonsterY)==false) {				// 1.2 Y bei gleichem X blockiert?
	    					permissionY = false;
	    				}
	    				if(checkItemMap(nextx, y + imgSizeMonsterY)==false) {				// 1.1 X bei gleichem Y blockiert?
	    	    			permissionX = false;
	    	    		}
	    				if(permissionX && permissionY) {
	    					permissionX = false;
	    					permissionY = false;
	    				}
	    			}
	    		} else {				// right
	    			// 1. Oben rechts (nach rechts)
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty)==false) {				// 1. Rechts blockiert
	    				permissionX = false;
	    			}
	    			// 2. Unten rechts
	    			if(checkItemMap(nextx + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {	// 1. Rechts blockiert
	    				permissionX = false;
	    			}
	    		}	
	    	} else if(my==-1) {   		// up
	    		// 1. Fixpunkt (nach oben)
				if(checkItemMap(nextx, nexty)==false) {								// 1. Oben blockiert
					permissionY = false;
				}
				// 2. Oben rechts
				if(checkItemMap(nextx + imgSizeMonsterX, nexty)==false) {				// 1. Rechts blockiert
					permissionY = false;
				}
	    	} else if(my==1) {			// down
	    		// 1. Unten links (nach unten)
				if(checkItemMap(nextx, nexty + imgSizeMonsterY)==false) {				// 1. Unten blockiert
					permissionY = false;
				}
				// 2. Unten rechts
				if(checkItemMap(nextx + imgSizeMonsterX, nexty + imgSizeMonsterY)==false) {	// 1. Unten blockiert
					permissionY = false;
				}
	    	}
    	}

    	// Übersetzen der permission-Parameter in die FreigabeID für 'move()'
    	if(permissionX==false && permissionY==false)	// keine Bewegung erlaubt
    		perm = 3;
    	else if(permissionX==false)							// x-Achse erlaubt
    		perm = 1;
    	else if(permissionY==false)							// y-Achse erlaubt
    		perm = -1;
    	else												// jede Bewegung erlaubt
    		perm = 0;
    	return perm;
	}
}