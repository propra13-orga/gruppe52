package the.game.java;

/**
 * In dieser Klasse können alle Objekte auf Kollision mit anderen Objekten geprüft werden.
 * Für Kollision mit Player, Enemy, Tracker, Goodies, Trap gibt es eigene Methoden
 * Alle Methoden geben true zurück, falls die angegebenen Objekte kollidieren, sonst false.
 */
public class Intersect {
	
	// Ränder des ersten Objekts
	private static int borderLe;
	private static int borderRi;
	private static int borderUp;
	private static int borderDo;
	// Mittelpunkt des ersten Objekts
	private static int middleX;
	private static int middleY;
	//Ränder des zweiten Objekts
	private static int objBorderLe;
	private static int objBorderRi;
	private static int objBorderUp;
	private static int objBorderDo;
	
	public Intersect() {
		
	}
	
	/*
	 * Überprüft, ob ein ausgewähltes Objekt mit dem Player kollidiert und gibt true zurück, wenn dies
	 * der Fall ist.
	 * @param playerID = nr des Players in PlayerList
	 * @param objPosx = xPos des Objekts
	 * @param objPosy = yPos des Objekts
	 * @param objFieldWidth = Breite des Objekts
	 * @param objFieldHeight = Höhe des Objekts
	 * @return true, wenn Objekt und Spieler kollidieren
	 */
	public static boolean isCollidingWithPlayer(int playerID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		// Objekt = Objekt1; Player = Objekt2;
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Player.playerList.get(playerID).getX(), Player.playerList.get(playerID).getY(), Player.playerList.get(playerID).imageSizeX, Player.playerList.get(playerID).imageSizeY);
	}
	
	/*
	 * Überprüft, ob ein ausgewähltes Objekt mit dem Enemy kollidiert und gibt true zurück, wenn dies
	 * der Fall ist.
	 * @param enemyID = nr des Enemys in EnemyList
	 * @param objPosx = xPos des Objekts
	 * @param objPosy = yPos des Objekts
	 * @param objFieldWidth = Breite des Objekts
	 * @param objFieldHeight = Höhe des Objekts
	 * @return true, wenn Objekt und Enemy kollidieren
	 */
	public static boolean isCollidingWithEnemy(int enemyID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		// Objekt = Objekt1, Enemy = Objekt 2
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Enemy.getX(enemyID), Enemy.getY(enemyID), Enemy.getImgSizeX(enemyID), Enemy.getImgSizeY(enemyID));
	}
	
	public static boolean isCollidingWithTrap(int trapID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Traps.getX(trapID), Traps.getY(trapID), Traps.getImgSizeX(trapID), Traps.getImgSizeY(trapID));
	}
	
	/*
	 * Überprüft, ob ein ausgewähltes Objekt mit dem Tracker kollidiert und gibt true zurück, wenn dies
	 * der Fall ist.
	 * @param trackerID = nr des Trackers in TrackerList
	 * @param objPosx = xPos des Objekts
	 * @param objPosy = yPos des Objekts
	 * @param objFieldWidth = Breite des Objekts
	 * @param objFieldHeight = Höhe des Objekts
	 * @return true, wenn Objekt und Tracker kollidieren
	 */
	public static boolean isCollidingWithTracker(int trackerID, int objPosx, int objPosy, int objFieldWidth, int objFieldHeight) {
		// Objekt = Objekt1; Tracker = Objekt 2
		return checkCollide(objPosx, objPosy, objFieldWidth, objFieldHeight, Tracker.getX(trackerID), Tracker.getY(trackerID), Tracker.getImgSizeX(trackerID), Tracker.getImgSizeY(trackerID));
	}
	
	/*
	 * Überprüft, ob ein Spieler mit einem Goodie (zB shield) kollidiert und gibt true zurück, wenn dies
	 * der Fall ist.
	 * @param playerID = nr des Players in PlayerList
	 * @param goodieID = nr des Goodies in GoodieList
	 * @return true, wenn Player und Goodie kollidieren
	 */
	public static boolean isPlayerCollidingWithGoodies(int playerID, int goodieID) {
		//Goodie = Objekt1; Player = Objekt2
		return checkCollide(Goodies.getX(goodieID), Goodies.getY(goodieID), Goodies.getImgSizeX(goodieID), Goodies.getImgSizeY(goodieID), Player.playerList.get(playerID).getX(), Player.playerList.get(playerID).getY(), Player.playerList.get(playerID).imageSizeX, Player.playerList.get(playerID).imageSizeY);
	}
	
	/*
	 * Überprüft, ob ein Spieler mit einer Falle kollidiert und gibt true zurück, wenn dies
	 * der Fall ist.
	 * @param playerID = nr des Players in PlayerList
	 * @param trapID = nr der Falle in TrapList
	 * @return true, wenn Player und Trap kollidieren
	 */
	public static boolean isPlayerCollidingWithTrap(int playerID, int trapID) {
		int ulx = Traps.getX(trapID) + Traps.getOffsetX(trapID);
		int uly = Traps.getY(trapID) + Traps.getOffsetY(trapID);
		int width = Traps.getImgSizeX(trapID) - (Traps.getOffsetX(trapID) * 2);
		int height = Traps.getImgSizeY(trapID) - (Traps.getOffsetY(trapID) * 2);
		// trap = Objekt 1; Player = Objekt 2
		return checkCollide(ulx, uly, width, height, Player.playerList.get(playerID).getX(), Player.playerList.get(playerID).getY(), Player.playerList.get(playerID).imageSizeX, Player.playerList.get(playerID).imageSizeY);
	}
	
	/*
	 * Überprüft, ob zwei Objekte kollidieren. Alle boolean is[...]CollidingWith[...] Methoden verweisen auf diese Methode.
	 * Hierbei wird überprüft, ob das erste Objekt mit dem Zweiten in Berührung kommt. Der Mittelpunkt wird zusätzlich zu den
	 * Rändern überprüft, falls die Objekte unterschiedliche größen haben, speziell falls das zweite Objekt größer ist als das erste.
	 * Falls die Objekte kollidieren wird true zurückgegeben.
	 * @param obj1_posx = xPosition des ersten Objekts
	 * @param obj1_posy = yPosition des ersten Objekts
	 * @param obj1_fieldWidth = Breite des ersten Objekts
	 * @param obj1_fieldHeight = Höhe des ersten Objekts
	 * @param obj2_posx = xPosition des zweiten Objekts
	 * @param obj2_posy = yPosition des zweiten Objekts
	 * @param obj2_fieldWidth = Breite des zweiten Objekts
	 * @param obj2_fieldHeight = Höhe des zweiten Objekts
	 * @return true, wenn die beiden angegebenen Objekte kollidieren
	 */
	private static boolean checkCollide(int obj1_posx, int obj1_posy, int obj1_fieldWidth, int obj1_fieldHeight, int obj2_posx, int obj2_posy, int obj2_fieldWidth, int obj2_fieldHeight) {		// Kontrolle der 4 Eckpunkte des Monsters
		boolean colliding=false;										// zum Beginn des aufrufs wird colliding prinzipiell auf false gesetzt
		
		// Kanten und Mittelpunkt des zu überprüfenden ersten Objektes initialisieren
		borderLe = obj1_posx;							// Kante links
		borderUp = obj1_posy;							// Kante oben
		borderRi = borderLe + obj1_fieldWidth;			// Kante rechts
		borderDo = borderUp + obj1_fieldHeight;			// Kante unten
		middleX = borderLe+(borderRi-borderLe)/2;		// xPosition des Mittelpunktes
		middleY = borderUp+(borderDo-borderUp)/2;		// yPosition des Mittelpunktes
		
		// Kanten des Zielbereiches (zu überprüfendes zweite Objekt) initialisieren
		objBorderLe = obj2_posx;						// Kante links
		objBorderUp = obj2_posy;						// Kante oben
		objBorderRi = objBorderLe + obj2_fieldWidth;	// Kante rechts
		objBorderDo = objBorderUp + obj2_fieldHeight;	// Kante links 
		
		// 4 Eckpunkte und Mittelpunkt überprüfen. Wenn Werte von Objekt1 innerhalb Objekt2+Rand liegen, wird colliding auf true gesetzt
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
