package the.game.java;

/**
 * Erstellt alle weichtigen Objekte auf der Map
 *
 */
public class LevelCreator {
	
	public static int[][] itemMap;			// deklariert ein zweidimensionales Array namens itemMap
	public static int distancePix = 20;
	private static int distanceField = 1;
	
	/**
	 * Konstruktor der Klasse LevelCreator
	 * itemMap wird erstellt. Alle Inhalte werden auf 0 gesetzt.
	 */
	public LevelCreator() {	// Konstruktor
		itemMap = new int[getItemMapDimensions(0)][getItemMapDimensions(1)];	// itemMap wird erstellt, Größe ist abhängig von der Spielfeldgröße
		resetItemMap();	// alle Inhalte werden auf 0 gesetzt
	}

    /**
     * Ermittelt die benötigte Größe der ItemMap
     * @param axis 0 für x-Achse, 1 für y-Achse
     * @return Größe der itemMap
     */
    public static int getItemMapDimensions(int axis) {	// ermittelt die benötigte Größe für die itemMap
    	int value = 0;
    	// Für die jeweilige Achse: ermittelt anzahl der abschnitte mit 20 pixel breite/höhe
    	if(axis==0)	{		// x-Achse
    		value = (Runner.getWidthF() - (Runner.getWidthF() % 20))/20+1;		// +1 um möglichen Error abzufangen (falls Figur außerhalb des Spielfeldes Daten abfragt)
    		if((Runner.getWidthF() % 20)>0)
    			value++;
    	} else if(axis==1) {	// y-Achse
    		value = (Runner.getHeightF() - (Runner.getHeightF() % 20))/20+1;	// +1 für Menu oben
    		if((Runner.getHeightF() % 20)>0)
    			value++;
    	}
    	return value;
    }
    
    /**
     * Setzt alle Inhalte der ItemMap auf 0.
     */
    public static void resetItemMap() {		// setzt alle Inhalte der itemMap auf 0
		for(int a=0; a<getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<getItemMapDimensions(1); b++) {	// Zeilenweise
				itemMap[a][b] = 0;
				if(a>=getItemMapDimensions(0))	// setzt die letzte Spalte (ist außerhalb des Feldes) als Goal
					itemMap[a][b] = 4;
			}
		}
    }
    
    /**
     * Erstellt in der itemMap alle Tags für Wände
     * @param pointX x-Position des Objekts
     * @param pointY y-Position des Objekts
     */
    public static void createWall(int pointX, int pointY){	// erstellt in der itemMap alle Tags für Wände
    	pointY += distanceField;
    	itemMap[pointX][pointY] = 1;		// Tag 1 = Wall
	}
    /**
     * Erstellt in der itemMap alle Tags für Wände mit Breite und Höhe
     * @param pointX x-Position des Objekts
     * @param pointY y-Position des Objekts
     * @param width Breite der Wand
     * @param height Höhe der Wand
     */
	public static void createWall(int pointX, int pointY, int width, int height){	// erstellt in der itemMap alle Tags für Wände
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 1;		// Tag 1 = Wall
			}
		}
	}
	/**
     * Erstellt in der itemMap alle Tags für Traps
     * @param pointX x-Position des Objekts
     * @param pointY y-Position des Objekts
	 */
	public static void createTrap(int posx, int posy){
		posy += distanceField;
		//itemMap[pointX][pointY] = 2;	// Tag 2 = Trap
		Traps.createMine(posx*20, posy*20);
	}
	/**
     * Erstellt in der itemMap alle Tags für Traps mit Breite und Höhe
     * @param pointX x-Position des Objekts
     * @param pointY y-Position des Objekts
     * @param width Breite der Trap
     * @param height Höhe der Trap
	 */
	public static void createTrap(int posx, int posy, int width, int height){
		posy += distanceField;
		for(int a=posx; a<width+posx; a++) {	// Spaltenweise
			for(int b=posy; b<height+posy; b++) {
				Traps.createMine(posx*20, posy*20);
				//itemMap[a][b] = 2;		// Tag 2 = Trap
			}
		}
	}
	/**
	 * Erstellt in der itemMap alle Tags für Goodies
	 * @param posx x-Position des Goodies
	 * @param posy y-Position des Goodies
	 * @param type typ des Goodies
	 */
	public static void createGoodie(int posx, int posy, String type){
		posy += distanceField;
		Goodies.createGoodie(posx, posy, type);
	}
	/**
	 * Erstellt in der itemMap alle Tags für Lebenspunkte
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 */
	public static void createHealthPoint(int posx, int posy){
		posy += distanceField;
		Goodies.createGoodie(posx, posy, "heart");
	}
	/**
	 * Erstellt in der itemMap alle Tags für Manapunkte
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 */
	public static void createMana(int posx, int posy){
		posy += distanceField;
		Goodies.createGoodie(posx, posy, "mana");
	}
	/**
	 * Erstellt in der itemMap alle Tags für Schilder
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 */
	public static void createShield(int posx, int posy){
		posy += distanceField;
		Goodies.createGoodie(posx, posy, "shield");
	}
	/**
	 * Erstellt in der itemMap alle Tags für Zielbereiche
	 * @param pointX x-Position des Objekts
	 * @param pointY y-Position des Objekts
	 */
	public static void createGoal(int pointX, int pointY){
		itemMap[pointX][pointY] = 3;		// Tag 3 = Ziel
	}
	/**
	 * Erstellt in der itemMap alle Tags für Zielbereiche mit Höhe und Breite
	 * @param pointX x-Position des Objekts
	 * @param pointY y-Position des Objekts
	 * @param width Breite des Objekts
	 * @param height Höhe des Objekts
	 */
	public static void createGoal(int pointX, int pointY, int width, int height){
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 3;		// Tag 3 = Ziel
			}
		}
	}
	
	public static void createMPGoal(int pointX, int pointY, int teamID){
		itemMap[pointX][pointY] = 50+teamID;		// Tag 50+team= Ziel für team
	}
	public static void createMPGoal(int pointX, int pointY, int width, int height, int teamID){
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 50+teamID;			// Tag 50+team = Ziel für team
			}
		}
	}
	
	
	/**
	 * 
	 * Erstellt in der itemMap alle Tags für Finale Zielbereiche mit Höhe und Breite
	 * @param pointX x-Position des Objekts
	 * @param pointY y-Position des Objekts
	 * @param width Breite des Objekts
	 * @param height Höhe des Objekts
	 */
	public static void createFinalGoal(int pointX, int pointY, int width, int height){
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 5;		// Tag 5 = Finales Ziel
			}
		}
	}
	/**
	 * Gibt den (Tag) Inhalt der Map zurück
	 * @param x x-Position im Array
	 * @param y y-Position im Array
	 * @return Tag in der Map
	 */
	public static int getItemMapData(int x, int y) {
		return itemMap[x][y];
	}
	/**
	 * Gibt den (Tag) Inhalt der Map zurück
	 * @param x x-Position in Pixeln
	 * @param y y-Position in Pixeln
	 * @return Tag in der Map
	 */
	public static int getItemMapDataUsingPixels(int x, int y) {
		x = (x-(x%20))/20;
		y = (y-(y%20))/20;
		return itemMap[x][y];
	}
}
