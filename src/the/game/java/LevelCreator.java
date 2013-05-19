package the.game.java;

public class LevelCreator {
	
	public static int[][] itemMap;
	
	public LevelCreator() {
		itemMap = new int[getItemMapDimensions(0)][getItemMapDimensions(1)];
		resetItemMap();
	}

    
    public static int getItemMapDimensions(int axis) {	// Set height and width of the itemMap
    	int value = 0;
    	if(axis==0)			// x-Achse
    		value = (Runner.getWidthF() - (Runner.getWidthF() % 20))/20;
    	else if(axis==1)	// y-Achse
    		value = (Runner.getHeightF() - (Runner.getHeightF() % 20))/20+1;	// +1 für Menu oben
    	return value;
    }
    
    public static void resetItemMap() {
		for(int a=0; a<getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<getItemMapDimensions(1); b++) {
				itemMap[a][b] = 0;
			}
		}
    }
    
	public static void createWall(int pointX, int pointY, int width, int height){
		//System.out.println("x:" + pointX + "   y:" + pointY + "    w:" + width + "    h:" + height);
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 1;
			}
		}
	}
	
	public static void createTrap(int pointX, int pointY){
		itemMap[pointX][pointY] = 2;
	}
	
	public static void createHealthPoint(int pointX, int pointY){
		itemMap[pointX][pointY] = 10;
	}
	
	public static void createGoal(int pointX, int pointY, int width, int height){
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 4;
			}
		}
	}
	
	public static void createFinalGoal(int pointX, int pointY, int width, int height){
		for(int a=pointX; a<width+pointX; a++) {	// Spaltenweise
			for(int b=pointY; b<height+pointY; b++) {
				itemMap[a][b] = 5;
			}
		}
	}
	
	public static int getItemMapData(int x, int y) {
		return itemMap[x][y];
	}
}
