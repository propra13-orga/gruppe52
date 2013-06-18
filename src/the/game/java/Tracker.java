package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;

public class Tracker {
	
	private int[][] flagMap;
	private Queue<Integer> pathQueueX;
	private Queue<Integer> pathQueueY;
	
	private int speed;
	private int delay;
	private int x;
	private int y;
	private int mx=0;
	private int my=0;
	private int damage;
	public static List<Tracker> trackerList = new ArrayList<Tracker>();
	private static int index;
	private int imgSizeTrackerX;
	private int imgSizeTrackerY;
	private int healthPoints;
	private boolean alive;
	private static boolean pathFindingActive = false;
	private boolean dumb;
	private Image imgEnemy;
	private Image imgEnemyDead;
	
	public Tracker(int posx, int posy) {	// Konstruktor 1: Parameter bestimmen Startposition
		flagMap = new int[LevelCreator.getItemMapDimensions(0)][LevelCreator.getItemMapDimensions(1)];
		pathQueueX = new LinkedList<Integer>();
		pathQueueY = new LinkedList<Integer>();
		
		x=posx;
		y=posy;
		mx=1;
		speed = -1;
		delay = 0;
		damage = 50;
		
		healthPoints = 100;
		alive = true;
		
		imgEnemy = setImage("enemy.gif");
		imgEnemyDead = setImage("trap.png");
		
		imgSizeTrackerX = 15;
		imgSizeTrackerY = 22;
		
		dumb = true;
	}
	public Tracker(int posx, int posy, boolean isDumb) {	// Konstruktor 2: Parameter bestimmen Startposition und Laufrichtung (durch Laufvariable, siehe Beispiel Klasse 'Enemy')
		flagMap = new int[LevelCreator.getItemMapDimensions(0)][LevelCreator.getItemMapDimensions(1)];
		pathQueueX = new LinkedList<Integer>();
		pathQueueY = new LinkedList<Integer>();
		
		x = posx;
		y = posy;
		mx = 0;
		my = 0;
		speed = -1;
		delay = 0;
		damage = 50;
		
		healthPoints = 100;
		alive = true;
		
		imgEnemy = setImage("enemy.gif");
		imgEnemyDead = setImage("trap.png");
		
		imgSizeTrackerX = 15;
		imgSizeTrackerY = 22;
		
		dumb = isDumb;
	}
	
	// Folgende Methoden erstellen einen neuen Tracker und erfassen ihn in der Liste 'trackerList' nach den Kriterien der Konstruktoren
	public static void createTracker(int pointX, int pointY) {
		trackerList.add(new Tracker(pointX, pointY));
	}
	public static void createTracker(int pointX, int pointY, boolean isDumb) {
		trackerList.add(new Tracker(pointX, pointY, isDumb));
	}
	
	// Abfragen zur aktuellen Position eines Trackers
	public static int getX(int listIndex) {
		return trackerList.get(listIndex).x;
	}
	public static int getY(int listIndex) {
		return trackerList.get(listIndex).y;
	}
	// Abfragen zum Image
	public static int getImgSizeX(int listIndex) {
		return trackerList.get(listIndex).imgSizeTrackerX;
	}
	public static int getImgSizeY(int listIndex) {
		return trackerList.get(listIndex).imgSizeTrackerY;
	}
	public static Image getImg(int trackerID) {
		if(trackerList.get(trackerID).alive)
			return trackerList.get(trackerID).imgEnemy;
		else
			return trackerList.get(trackerID).imgEnemyDead;
	}
	// Sonstige Abfragen
	public static boolean isAlive(int trackerID) {
		return trackerList.get(trackerID).alive;
	}
	
	// Lebenspunkte
	public void reduceHealthPoints(int reduce) {
		if(healthPoints>0) {
			healthPoints -= reduce;
			if(healthPoints<=0) {
				alive = false;
				Goodies.createCredits(x, y, 35);
			}
		}
	}
	
	// move-Methode aktualisiert bei jedem Aufruf mit Hilfe der Laufvariablen die Position aller Tracker
	public static void move() {
		if(TemporaryItem.flagEnemyFreezed)
			return;
		
		int faktor=1;
		for(int a=0; a<trackerList.size(); a++) {	// geht alle Tracker aus der Liste durch
			if(trackerList.get(a).alive) {
				index = a;
				trackerList.get(a).delay++;
				if(trackerList.get(a).delay>0) {	// Für Verzögerung der Aktualisierung = langsamere Bewegung; Einstellbar mit der Variablen 'speed'
					faktor=trackerList.get(a).delay;	// delay an faktor übergeben, somit wenn >1 wird geschwindigkeit gesteigert
					trackerList.get(a).delay = trackerList.get(index).speed;	// delay zurücksetzen
					trackerList.get(a).setDirection();	// Laufrichtung wird aktualisiert (Richtet sich nach Position des Spielers)
					if(trackerList.get(a).checkEnvironment() == false) {
						trackerList.get(a).x += trackerList.get(index).mx*faktor;
						trackerList.get(a).y += trackerList.get(index).my*faktor;
					}
				}
			}
		}
	}
	
	private void setDirection() {	// Setzt die Laufrichtung fest (richtet sich nach Position des Spielers)
			// Positionen von Spieler und Tracker initialisieren
			
			int trackerX = trackerList.get(index).x;
			int trackerY = trackerList.get(index).y;
			// Entscheiden, welcher Player verfolgt werden soll: gewählt wird kürzeste Distanz
			//for(int a=0; a<Player.playerList.size(); a++) {
			//	
			//}
			
			// Einfache Steigungsberechnung
			int targetX = 0;
			int targetY = 0;
			if(dumb) {
				targetX = Player.playerList.get(0).getX();
				targetY = Player.playerList.get(0).getY();
				
			} else {
				if(pathQueueX.peek()!=null) {
					targetX = pathQueueX.peek()*20+30;	// erfasst jeweils die Mitte der Zielkante (somit zusammen Mitte des Zielquadrats)
					targetY = pathQueueY.peek()*20+10;
					//System.out.println(targetX + "   |   " + targetY);
					if(x == targetX && y == targetY) {
						pathQueueX.poll();
						pathQueueY.poll();
						targetX = pathQueueX.peek()*20+10;	// erfasst jeweils die Mitte der Zielkante (somit zusammen Mitte des Zielquadrats)
						targetY = pathQueueY.peek()*20+10;
						System.out.println("NEXT!");
					}
				} else {
					//System.out.println("NULL!");
					targetX = x;
					targetY = y;
				}
			}
			if(targetX-trackerX>0)
				trackerList.get(index).mx = 1;
			else if(targetX-trackerX<0)
				trackerList.get(index).mx = -1;
			else
				trackerList.get(index).mx = 0;
			if(targetY-trackerY>0)
				trackerList.get(index).my = 1;
			else if(targetY-trackerY<0)
				trackerList.get(index).my = -1;
			else
				trackerList.get(index).my = 0;
			//System.out.println(mx + "   |   " + my);
	}
		
	private boolean checkEnvironment() {							// Kontrolle der 4 Eckpunkte des Trackers
		boolean colliding=false;
		
		// Positionen der Kanten initialisieren
		int mapPosLeft = (trackerList.get(index).x+trackerList.get(index).mx-((trackerList.get(index).x+trackerList.get(index).mx)%20))/20;
		int mapPosRight = (trackerList.get(index).x+trackerList.get(index).mx+imgSizeTrackerX-((trackerList.get(index).x+trackerList.get(index).mx+imgSizeTrackerX)%20))/20;
		int mapPosUp = (trackerList.get(index).y+trackerList.get(index).my-((trackerList.get(index).y+trackerList.get(index).my)%20))/20;
		int mapPosDown = (trackerList.get(index).y+trackerList.get(index).my+imgSizeTrackerY-((trackerList.get(index).y+trackerList.get(index).my+imgSizeTrackerY)%20))/20;
		
		// Ecken überprüfen (Überprüft wird die "künftige" Position)
		if(LevelCreator.itemMap[mapPosLeft][mapPosUp]>0)					// 1. Fixpunkt
			colliding = true;
		if(LevelCreator.itemMap[mapPosRight][mapPosUp]>0)					// 2. Oben rechts
			colliding = true;
		if(LevelCreator.itemMap[mapPosRight][mapPosDown]>0)					// 3. Unten rechts
			colliding = true;
		if(LevelCreator.itemMap[mapPosLeft][mapPosDown]>0)					// 4. Unten links
			colliding = true;
		
		// Wenn mehr als ein Spieler existiert, dürfen die Tracker weiterlaufen
		// Wenn nur ein Spieler existiert, keine Bewegung mehr, da die trackerList beim wiederaufruf von 'move()' bereits gelöscht sein wird
		if(Player.playerList.size()<=1) {
			if(colliding==false)
				colliding = checkPlayerCollide();
			else
				checkPlayerCollide();
		} else {
			checkPlayerCollide();
		}
		return colliding;
	}
	
	private boolean checkPlayerCollide() {			// Kontrolle der 4 Eckpunkte des Trackers
		boolean generalCollide = false;
		for(int a=0; a<Player.playerList.size(); a++) {	// Wird für alle Player nacheinander überprüft
			
			boolean colliding = Intersect.isCollidingWithPlayer(a, trackerList.get(index).x, trackerList.get(index).y, imgSizeTrackerX, imgSizeTrackerY);
			
			if(colliding) {
				generalCollide = true;
				if(pathFindingActive==false)
					Player.playerList.get(a).reduceHealthPoints(damage);	// Lebenspunkte abziehen -> wenn keine mehr übrig Spieler tot
			}
		}
		return generalCollide;
	}
	
	public void pathFinding() {
		pathFindingActive = true;
		resetFlagMap();
		int playerID = 0;
		boolean iAmLost = false;
		
		int posx = translatePixelToMapSquares(x);
		int posy = translatePixelToMapSquares(y);
		int playerPosX = translatePixelToMapSquares(Player.playerList.get(playerID).getX() + (Player.playerList.get(playerID).imageSizeX / 2));	// ergibt zusammen das Mapquadrat in dem sich der Mittelpunkt des Spielers befindet
		int playerPosY = translatePixelToMapSquares(Player.playerList.get(playerID).getY() + (Player.playerList.get(playerID).imageSizeY / 2));
		System.out.println(posx + " x " + posy);
		// Feld markieren
		flagMap[posx][posy] = 1;
		
		//while(checkPlayerCollide()==false && iAmLost==false) {
		int counter = 0;
		while((posx!=playerPosX || posy!=playerPosY) && iAmLost==false) {
			counter++;
			if(counter>3000) {
				System.out.println("BREAK!");
				break;
			}
			
			
			
			// Nächstes Feld suchen
			if(isWayFree(posx, posy-1)) {			// hoch
				// System.out.println("Up");
				posy--;
				pathQueueX.add(posx);
				pathQueueY.add(posy);
			} else if(isWayFree(posx+1, posy)) {	// rechts
				// System.out.println("Right");
				posx++;
				pathQueueX.add(posx);
				pathQueueY.add(posy);
			} else if(isWayFree(posx, posy+1)) {	// runter
				// System.out.println("Down");
				posy++;
				pathQueueX.add(posx);
				pathQueueY.add(posy);
			} else if(isWayFree(posx-1, posy)) {	// links
				// System.out.println("Left");
				posx--;
				pathQueueX.add(posx);
				pathQueueY.add(posy);
			} else {								// Alle blockiert	(Sackgasse)
				// Löscht solange Glieder aus der Schlange, bis eine alternative Route gefunden wurde (wenn es keine gibt, aufgeben)
				while(isWayFree(pathQueueX.peek(), pathQueueY.peek()+1)==false && isWayFree(pathQueueX.peek()+1, pathQueueY.peek())==false && isWayFree(pathQueueX.peek(), pathQueueY.peek()-1)==false && isWayFree(pathQueueX.peek()-1, pathQueueY.peek())==false && iAmLost==false) {
					if(pathQueueX.size()<=1) {
						iAmLost = true;
						System.out.println("I AM LOST!");
					} else {
						pathQueueX.poll();
						pathQueueY.poll();
					}
				}
				posx = pathQueueX.peek();
				posy = pathQueueY.peek();
			}
			
			// Feld markieren
			flagMap[posx][posy] = 1;
			
			//System.out.print("Counter: " + counter + ":    ");
			//System.out.println(posx + "   " + posy + "   |   " + playerPosX + "   " + playerPosY);
			
		}
		if(iAmLost==false)
			System.out.print("ICH HABS !");
		System.out.print("  Counter: " + counter);
		System.out.print("  Schritte: " + pathQueueX.size());
		System.out.println();
		System.out.println(x + "  " + y);
		System.out.println();
		
		//while(pathQueueX.peek()!=null) {
		//	System.out.println(pathQueueX.poll() + "   |   " + pathQueueY.poll());
		//}
		
		//DisplayManager.displayString(String.valueOf(counter), pathQueueX.peek()*20, pathQueueY.peek()*20, "dd");
		pathFindingActive = false;
		
	}
	
	private boolean isWayFree(int posx, int posy) {
		boolean free = (LevelCreator.getItemMapData(posx, posy)<=0);
		//System.out.println("ItemMap:" + LevelCreator.getItemMapData(posx, posy));
		if(free) {
			free = (flagMap[posx][posy] != 1);
			//System.out.println("FlagMap:" + flagMap[posx][posy]);
		}
		return free;
	}
	
	private void resetFlagMap() {
		for(int a=0; a<LevelCreator.getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<LevelCreator.getItemMapDimensions(1); b++) {	// Zeilenweise
				flagMap[a][b] = 0;
			}
		}
	}

	private int translatePixelToMapSquares(int value) {
		value = (value - (value % 20)) / 20;
		return value;
	}
	
	private Image setImage(String path) {
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
    	return ii.getImage();
    }
}