package the.game.java;

public class LevelCreator {
	
	public static int[][] itemMap;			// deklariert ein zweidimensionales Array namens itemMap
	public static int distancePix = 20;
	private static int distanceField = 1;
	
	public LevelCreator() {	// Konstruktor
		itemMap = new int[getItemMapDimensions(0)][getItemMapDimensions(1)];	// itemMap wird erstellt, Größe ist abhängig von der Spielfeldgröße
		resetItemMap();	// alle Inhalte werden auf 0 gesetzt
	}

    
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
    
    public static void resetItemMap() {		// setzt alle Inhalte der itemMap auf 0
		for(int a=0; a<getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<getItemMapDimensions(1); b++) {	// Zeilenweise
				itemMap[a][b] = 0;
				if(a>=getItemMapDimensions(0))	// setzt die letzte Spalte (ist außerhalb des Feldes) als Goal
					itemMap[a][b] = 4;
			}
		}
    }
    
	public static void createWall(int pointX, int pointY, int width, int height){	// erstellt in der itemMap alle Tags für Wände
		//System.out.println("x:" + pointX + "   y:" + pointY + "    w:" + width + "    h:" + height);
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 1;		// Tag 1 = Wall
			}
		}
	}
	
	public static void createTrap(int pointX, int pointY){
		pointY += distanceField;
		itemMap[pointX][pointY] = 2;	// Tag 2 = Trap
	}
	
	public static void createHealthPoint(int pointX, int pointY){
		pointY += distanceField;
		itemMap[pointX][pointY] = 10;	// Tag 10 = Heart
	}
	
	public static void createShield(int pointX, int pointY){
		pointY += distanceField;
		itemMap[pointX][pointY] = 11;	// Tag 11 = Shield
	}
	
	public static void createGoal(int pointX, int pointY, int width, int height){
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 4;		// Tag 4 = Ziel
			}
		}
	}
	
	public static void createFinalGoal(int pointX, int pointY, int width, int height){
		pointY += distanceField;
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 5;		// Tag 5 = Finales Ziel
			}
		}
	}
	
	public static int getItemMapData(int x, int y) {
		return itemMap[x][y];
	}
	public static int getItemMapDataUsingPixels(int x, int y) {
		x = (x-(x%20))/20;
		y = (y-(y%20))/20;
		return itemMap[x][y];
	}
}
