package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class Enemy {
	
	private int x;
	private int y;
	private int mx=0;
	private int my=0;
	public static List<Enemy> monsterList = new ArrayList<Enemy>();
	private static int index;
	private static int imgSizeMonsterX = 15;
	private static int imgSizeMonsterY = 19;
	public static boolean codeRunning = true;
	private int enemyType;
	
	// Konstruktor 1: Nur Startposition angegeben: Bewegung nur horizontal
	public Enemy(int posx, int posy) {	
		x=posx;
		y=posy;
		mx=1;
		codeRunning = true;		// =true solange ein Objekt der Klasse Enemy existiert
		enemyType = 0;
	}
	// Konstruktor 2: Startposition und Laufvariablen (movex =1 rechts; movex =-1 links; moveY =1 unten; moveY =-1 oben)
	public Enemy(int posx, int posy, int moveX, int moveY) {	
		x = posx;
		y = posy;
		mx = moveX;
		my = moveY;
		codeRunning = true;
		enemyType = 0;
	}
	// Konstruktor 3: Startposition und 
	// Laufvariablen (movex =1 rechts; movex =-1 links; moveY =1 unten; moveY =-1 oben), 
	// Type legt die Monsterart fest (=0 ist normaler Feind, der hin und her läuft; =1 ist 'Bouncy', der sich wie ein Flummi verhält)
	public Enemy(int posx, int posy, int moveX, int moveY, int type) {
		x = posx;
		y = posy;
		mx = moveX;
		my = moveY;
		codeRunning = true;
		enemyType = type;
	}
	
	// Folgende Methoden erstellen neu Gegner nach Kriterien der Konstruktoren oben
	// Weiterhin werden die neuen Objekte der Klasse Enemy in einer Liste 'monsterList' erfasst
	public static void createMonster(int pointX, int pointY) {
		monsterList.add(new Enemy(pointX, pointY));
	}
	public static void createMonster(int pointX, int pointY, int moveX, int moveY) {
		monsterList.add(new Enemy(pointX, pointY, moveX, moveY));
	}
	public static void createMonster(int pointX, int pointY, int moveX, int moveY, int type) {
		monsterList.add(new Enemy(pointX, pointY, moveX, moveY, type));
	}
	
	// Abfragen zur aktuellen Position eines Gegners
	public static int getX(int listIndex) {
		return monsterList.get(listIndex).x;
	}
	public static int getY(int listIndex) {
		return monsterList.get(listIndex).y;
	}
	
	// move-Methode für alle Gegner
	public static void move() {		// wird aus Setter aufgerufen und aktualisiert die Position aller Gegner
		int faktor=1;	// Multiplikator für die Geschwindigkeit normaler Gegner
		for(int a=0; a<monsterList.size(); a++) {	// Schleife um alle Gegner in der Liste anzusprechen
			index = a;
			switch(monsterList.get(index).enemyType) {
				case 0:		// Bewegung für normalen Feind
					if(monsterList.get(index).checkEnvironment() == false) {
						if(codeRunning) {
							monsterList.get(index).x += monsterList.get(index).mx*faktor;
							monsterList.get(index).y += monsterList.get(index).my*faktor;
						}
					} else {		// Wenn nicht passierbares Objekt Getroffen wird, dann Laufvariablen invertieren
						if(codeRunning) {
							
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
		
	private boolean checkEnvironment() {	// Kontrolle der 4 Eckpunkte des Monsters
		boolean colliding=false;
		// Positionen der Kanten initialisieren
		int mapPosLeft = (monsterList.get(index).x+monsterList.get(index).mx-((monsterList.get(index).x+monsterList.get(index).mx)%20))/20;
		int mapPosRight = (monsterList.get(index).x+monsterList.get(index).mx+imgSizeMonsterX-((monsterList.get(index).x+monsterList.get(index).mx+imgSizeMonsterX)%20))/20;
		int mapPosUp = (monsterList.get(index).y+monsterList.get(index).my-((monsterList.get(index).y+monsterList.get(index).my)%20))/20;
		int mapPosDown = (monsterList.get(index).y+monsterList.get(index).my+imgSizeMonsterY-((monsterList.get(index).y+monsterList.get(index).my+imgSizeMonsterY)%20))/20;
		// Ecken überprüfen (Überprüft wird die "künftige" Position)
		if(LevelCreator.itemMap[mapPosLeft][mapPosUp]>0)					// 1. Fixpunkt
			colliding = true;
		if(LevelCreator.itemMap[mapPosRight][mapPosUp]>0)					// 2. Oben rechts
			colliding = true;
		if(LevelCreator.itemMap[mapPosRight][mapPosDown]>0)					// 3. Unten rechts
			colliding = true;
		if(LevelCreator.itemMap[mapPosLeft][mapPosDown]>0)					// 4. Unten links
			colliding = true;
		checkPlayerCollide();	// Überprüft, ob der Gegner mit einem Player kollidiert, falls ja wird lebensstatus des Spielers aktualisiert
		return colliding;
	}
	
	private void checkPlayerCollide() {		// Kontrolle der 4 Eckpunkte des Monsters
		for(int a=0; a<Player.playerList.size(); a++) {	// Wird für alle Player nacheinander überprüft
			boolean colliding=false;
			// Positionen der Kanten initialisieren
			int mapPosLeft = monsterList.get(index).x;
			int mapPosRight = monsterList.get(index).x + imgSizeMonsterX;
			int mapPosUp = monsterList.get(index).y;
			int mapPosDown = monsterList.get(index).y + imgSizeMonsterY;
			int playerMapPosLeft = Player.playerList.get(a).getX();
			int playerMapPosRight = Player.playerList.get(a).getX() + Player.playerList.get(a).imageSizeX;
			int playerMapPosUp = Player.playerList.get(a).getY();
			int playerMapPosDown = Player.playerList.get(a).getY() + Player.playerList.get(a).imageSizeY;
			
			// Ecken überprüfen (Überprüft wird die "aktuelle" Position)
			// Es wird überprüft ob die 4 Ecken des Gegners innerhalb des von dem Spieler belegten Intervalls liegen
			// wenn ja, stirbt der Spieler
			if((playerMapPosLeft<=mapPosLeft) && (mapPosLeft <= playerMapPosRight) && (playerMapPosUp <= mapPosUp) && (mapPosUp <= playerMapPosDown))		// 1. Fixpunkt
				colliding = true;
			if((playerMapPosLeft<=mapPosRight) && (mapPosRight <= playerMapPosRight) && (playerMapPosUp <= mapPosUp) && (mapPosUp <= playerMapPosDown))		// 2. Oben rechts
				colliding = true;
			if((playerMapPosLeft<=mapPosRight) && (mapPosRight <= playerMapPosRight) && (playerMapPosUp <= mapPosDown) && (mapPosDown <= playerMapPosDown))	// 3. Unten rechts
				colliding = true;
			if((playerMapPosLeft<=mapPosLeft) && (mapPosLeft <= playerMapPosRight) && (playerMapPosUp <= mapPosDown) && (mapPosDown <= playerMapPosDown))	// 4. Unten links
				colliding = true;
			if((playerMapPosLeft<=(mapPosLeft+(mapPosRight-mapPosLeft)/2)) && ((mapPosLeft+(mapPosRight-mapPosLeft)/2) <= playerMapPosRight) && (playerMapPosUp <= (mapPosUp+(mapPosDown-mapPosUp)/2)) && ((mapPosUp+(mapPosDown-mapPosUp)/2) <= playerMapPosDown))		// 5. Mittelpunkt
				colliding = true;
	
			if(colliding) {										// Wenn Gegner mit Spieler kollidiert:
				Player.playerList.get(a).setLifeStatus(false);	// Lebensstatus auf falsch setzen -> Spieler tot
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
			posx=(posx - (posx % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
			posy=(posy - (posy % 20 )) / 20;	// Umrechnung: Pixel in Planquadrat des Arrays
	    	boolean permission=true;	// Ausgangswert
	    	if(LevelCreator.itemMap[posx][posy]>0)
	    		permission = false;		// Falls irgendein Objekt im Weg, ist permission = false
	    	return permission;
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