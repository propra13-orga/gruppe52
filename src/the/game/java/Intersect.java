package the.game.java;

public class Intersect {
	
	private static int borderLe;
	private static int borderRi;
	private static int borderUp;
	private static int borderDo;
	private static int middleX;
	private static int middleY;
	
	private static int objBorderLe;
	private static int objBorderRi;
	private static int objBorderUp;
	private static int objBorderDo;
	
	public Intersect() {
		
	}
	
	public static boolean isCollidingWithPlayer(int playerID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Player.playerList.get(playerID).getX(), Player.playerList.get(playerID).getY(), Player.playerList.get(playerID).imageSizeX, Player.playerList.get(playerID).imageSizeY);
	}
	
	public static boolean isCollidingWithEnemy(int enemyID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Enemy.getX(enemyID), Enemy.getY(enemyID), Enemy.getImgSizeX(enemyID), Enemy.getImgSizeY(enemyID));
	}
	
	public static boolean isCollidingWithTracker(int trackerID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Tracker.getX(trackerID), Tracker.getY(trackerID), Tracker.getImgSizeX(trackerID), Tracker.getImgSizeY(trackerID));
	}
	
	public static boolean isPlayerCollidingWithGoodies(int playerID, int goodieID) {
		return checkCollide(Goodies.getX(goodieID), Goodies.getY(goodieID), Goodies.getImgSizeX(goodieID), Goodies.getImgSizeY(goodieID), Player.playerList.get(playerID).getX(), Player.playerList.get(playerID).getY(), Player.playerList.get(playerID).imageSizeX, Player.playerList.get(playerID).imageSizeY);
	}
	
	// Überprüfen ob die 4 Eckpunkte und der Mittelpunkt von Objekt1 innerhalb der Fläche von Objekt2 liegen, wenn ja return true
	private static boolean checkCollide(int obj1_posx, int obj1_posy, int obj1_fieldWidth, int obj1_fieldHeight, int obj2_posx, int obj2_posy, int obj2_fieldWidth, int obj2_fieldHeight) {		// Kontrolle der 4 Eckpunkte des Monsters
		boolean colliding=false;
		
		// Kanten des zu überprüfenden Objektes initialisieren
		borderLe = obj1_posx;
		borderUp = obj1_posy;
		borderRi = borderLe + obj1_fieldWidth;
		borderDo = borderUp + obj1_fieldHeight;
		middleX = borderLe+(borderRi-borderLe)/2;
		middleY = borderUp+(borderDo-borderUp)/2;
		
		// Kanten des Zielbereiches initialisieren
		objBorderLe = obj2_posx;
		objBorderUp = obj2_posy;
		objBorderRi = objBorderLe + obj2_fieldWidth;
		objBorderDo = objBorderUp + obj2_fieldHeight;
		
		// 4 Eckpunkte und Mittelpunkt überprüfen
		if((borderLe >= objBorderLe) && (borderLe <= objBorderRi) && (borderUp >= objBorderUp) && (borderUp <= objBorderDo))		// 1. Oben links (Fixpunkt)
			colliding = true;
		else if((borderRi >= objBorderLe) && (borderRi <= objBorderRi) && (borderUp >= objBorderUp) && (borderUp <= objBorderDo))	// 2. Oben rechts
			colliding = true;
		else if((borderRi >= objBorderLe) && (borderRi <= objBorderRi) && (borderDo >= objBorderUp) && (borderDo <= objBorderDo))	// 3. Unten rechts
			colliding = true;
		else if((borderLe >= objBorderLe) && (borderLe <= objBorderRi) && (borderDo >= objBorderUp) && (borderDo <= objBorderDo))	// 4. Unten links
			colliding = true;
		else if((middleX >= objBorderLe) && (middleX <= objBorderRi) && (middleY >= objBorderUp) && (middleY <= objBorderDo))		// 5. Mittelpunkt
			colliding = true;

		return colliding;
	}
}
