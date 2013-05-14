package the.game.java;

public class LevelCreator {
	
	public static int[][] itemMap;
	
	public LevelCreator() {
		itemMap = new int[getItemMapDimensions(0)][getItemMapDimensions(1)];
		resetItemMap();
	}

    
    public static int getItemMapDimensions(int axis) {	// Set height and width of the itemMap
    	int value = 0;
    	if(axis==0)
    		value = (Runner.getWidthF() - (Runner.getWidthF() % 20))/20;
    	else if(axis==1)
    		value = (Runner.getHeightF() - (Runner.getHeightF() % 20))/20;
    	return value;
    }
    
    public static void resetItemMap() { // ?!?!?!?!?
		for(int a=Runner.getWidthF(); a<Runner.getWidthF(); a++) {	// Spaltenweise
			for(int b=Runner.getHeightF(); b<Runner.getHeightF(); b++) {
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
	
	public static void createMonster(int pointX, int pointY){
		itemMap[pointX][pointY] = 3;
	}
	
	public static int getItemMapData(int x, int y) {
		return itemMap[x][y];
	}
}
