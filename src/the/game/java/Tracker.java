package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class Tracker {
	
	private int speed;
	private int delay;
	private int x;
	private int y;
	private int mx=0;
	private int my=0;
	public static List<Tracker> trackerList = new ArrayList<Tracker>();
	private static int index;
	private static int imgSizeTrackerX = 15;
	private static int imgSizeTrackerY = 22;
	
	public Tracker(int posx, int posy) {	// Konstruktor 1: Parameter bestimmen Startposition
		x=posx;
		y=posy;
		mx=1;
		speed = -1;
		delay = 0;
	}
	public Tracker(int posx, int posy, int moveX, int moveY) {	// Konstruktor 2: Parameter bestimmen Startposition und Laufrichtung (durch Laufvariable, siehe Beispiel Klasse 'Enemy')
		x = posx;
		y = posy;
		mx = moveX;
		my = moveY;
		speed = -1;
		delay = 0;
	}
	
	// Folgende Methoden erstellen einen neuen Tracker und erfassen ihn in der Liste 'trackerList' nach den Kriterien der Konstruktoren
	public static void createTracker(int pointX, int pointY) {
		trackerList.add(new Tracker(pointX, pointY));
	}
	public static void createTracker(int pointX, int pointY, int moveX, int moveY) {
		trackerList.add(new Tracker(pointX, pointY, moveX, moveY));
	}
	
	// Abfragen zur aktuellen Position eines Trackers
	public static int getX(int listIndex) {
		return trackerList.get(listIndex).x;
	}
	public static int getY(int listIndex) {
		return trackerList.get(listIndex).y;
	}
	
	// move-Methode aktualisiert bei jedem Aufruf mit Hilfe der Laufvariablen die Position aller Tracker
	public static void move() {
		int faktor=1;
		for(int a=0; a<trackerList.size(); a++) {	// geht alle Tracker aus der Liste durch
			index = a;
			trackerList.get(index).delay++;
			if(trackerList.get(index).delay>0) {	// Für Verzögerung der Aktualisierung = langsamere Bewegung; Einstellbar mit der Variablen 'speed'
				faktor=trackerList.get(index).delay;	// delay an faktor übergeben, somit wenn >1 wird geschwindigkeit gesteigert
				trackerList.get(index).delay = trackerList.get(index).speed;	// delay zurücksetzen
				trackerList.get(index).setDirection();	// Laufrichtung wird aktualisiert (Richtet sich nach Position des Spielers)
				if(trackerList.get(index).checkEnvironment() == false) {
					trackerList.get(index).x += trackerList.get(index).mx*faktor;
					trackerList.get(index).y += trackerList.get(index).my*faktor;
				}
			}
		}
	}
	
	private void setDirection() {	// Setzt die Laufrichtung fest (richtet sich nach Position des Spielers)
			// Positionen von Spieler und Tracker initialisieren
			int playerX = Player.playerList.get(0).getX();
			int playerY = Player.playerList.get(0).getY();
			int trackerX = trackerList.get(index).x;
			int trackerY = trackerList.get(index).y;
			// Entscheiden, welcher Player verfolgt werden soll: gewählt wird kürzeste Distanz
			//for(int a=0; a<Player.playerList.size(); a++) {
			//	
			//}
			// Einfache Steigungsberechnung
			if(playerX-trackerX>0)
				trackerList.get(index).mx = 1;
			else if(playerX-trackerX<0)
				trackerList.get(index).mx = -1;
			else
				trackerList.get(index).mx = 0;
			if(playerY-trackerY>0)
				trackerList.get(index).my = 1;
			else if(playerY-trackerY<0)
				trackerList.get(index).my = -1;
			else
				trackerList.get(index).my = 0;
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
		boolean colliding=false;
		for(int a=0; a<Player.playerList.size(); a++) {	// Wird für alle Player nacheinander überprüft
			// Positionen der Kanten initialisieren
			int mapPosLeft = trackerList.get(index).x;
			int mapPosRight = trackerList.get(index).x + imgSizeTrackerX;
			int mapPosUp = trackerList.get(index).y;
			int mapPosDown = trackerList.get(index).y + imgSizeTrackerY;
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
	
			if(colliding)
				Player.playerList.get(a).setLifeStatus(false);
		}
		return colliding;
	}
	
}