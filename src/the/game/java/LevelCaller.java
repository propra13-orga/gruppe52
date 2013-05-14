package the.game.java;

public class LevelCaller {
	
	private static int level=0;
	
	public LevelCaller(){
		new LevelCreator();
	}
	
	public static void setLevel(int levelnr){
		level = levelnr;
		LevelCreator.resetItemMap();
		switch(levelnr) {
		case 1:
			setLevel1();
			break;
		case 2:
			setLevel2();
			break;
		case 3:
			setLevel3();
			break;
		}
	}
	
	public static int getLevelNumber() {
		return level;
	}
	
    public static void levelCompleted() {
	    Runner.shutRunnerDown();
	    //new WinWindow();    	
    }
	
	private static void setLevel1(){
		// Monster
		//Enemy en1 = new Enemy(10, 14);
		//Enemy en2 = new Enemy(10, 16);
		// Traps
		LevelCreator.createTrap(10,10);
		// Walls
		LevelCreator.createWall(0,0,48,1);
		LevelCreator.createWall(0,25,48,1);
		LevelCreator.createWall(0,0,1,12);
		LevelCreator.createWall(0,15,1,12);
		LevelCreator.createWall(47,0,1,12);
		LevelCreator.createWall(24,0,1,12);
		LevelCreator.createWall(24,15,1,12);
		LevelCreator.createWall(12,3,1,24);
		LevelCreator.createWall(36,3,1,24);
		LevelCreator.createWall(47,15,1,12);
	}	
	private static void setLevel2(){	
		// Walls
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(0, 1, 1, 10);
		LevelCreator.createWall(0, 11, 7, 1);
		LevelCreator.createWall(6, 12, 1, 11);
		LevelCreator.createWall(0, 14, 1, 11);
		LevelCreator.createWall(7, 1, 1, 5);
		LevelCreator.createWall(3, 3, 1, 6);
		LevelCreator.createWall(4, 8, 8, 1);
		LevelCreator.createWall(12, 3, 1, 22);
		LevelCreator.createWall(17, 1, 1, 22);
		LevelCreator.createWall(22, 3, 1, 22);
		LevelCreator.createWall(27, 1, 1, 22);
		LevelCreator.createWall(32, 3, 1, 22);
		LevelCreator.createWall(37, 1, 1, 22);
		LevelCreator.createWall(38, 22, 7, 1);
		LevelCreator.createWall(38, 16, 7, 1);
		LevelCreator.createWall(40, 19, 7, 1);
		LevelCreator.createWall(42, 3, 1, 11);
		LevelCreator.createWall(47, 1, 1, 10);
		LevelCreator.createWall(43, 13, 5, 1);
		LevelCreator.createWall(47, 14, 1, 11);
	}	
	private static void setLevel3(){	
		// Walls
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(0, 6, 1, 19);
		LevelCreator.createWall(0, 1, 1, 3);
		LevelCreator.createWall(1, 3, 2, 1);
		LevelCreator.createWall(5, 3, 3, 1);
		LevelCreator.createWall(5, 3, 1, 4);
		LevelCreator.createWall(3, 6, 2, 1);
		LevelCreator.createWall(3, 7, 1, 7);
		LevelCreator.createWall(4, 13, 4, 1);
		LevelCreator.createWall(7, 9, 1, 4);
		LevelCreator.createWall(5, 9, 1, 3);
		LevelCreator.createWall(6, 9, 1, 1);
		LevelCreator.createWall(10, 1, 1, 6);
		LevelCreator.createWall(8, 6, 2, 1);
		LevelCreator.createWall(1, 16, 7, 1);
		LevelCreator.createWall(3, 19, 1, 4);
		LevelCreator.createWall(4, 19, 9, 1);
		LevelCreator.createWall(13, 19, 1, 6);
		LevelCreator.createWall(6, 22, 5, 1);
		LevelCreator.createWall(6, 23, 1, 2);
		LevelCreator.createWall(10, 16, 1, 3);
		LevelCreator.createWall(10, 9, 1, 5);
		LevelCreator.createWall(11, 13, 3, 1);
		LevelCreator.createWall(13, 14, 1, 3);
		LevelCreator.createWall(14, 16, 9, 1);
		LevelCreator.createWall(13, 3, 1, 8);
		LevelCreator.createWall(14, 10, 2, 1);
		LevelCreator.createWall(16, 3, 1, 13);
		LevelCreator.createWall(16, 17, 1, 6);
		LevelCreator.createWall(17, 22, 3, 1);
		LevelCreator.createWall(19, 1, 1, 4);
		LevelCreator.createWall(17, 7, 6, 1);
		LevelCreator.createWall(22, 3, 1, 4);
		LevelCreator.createWall(19, 11, 1, 3);
		LevelCreator.createWall(25, 3, 1, 7);
		LevelCreator.createWall(22, 11, 1, 5);
		LevelCreator.createWall(19, 10, 22, 1);
		LevelCreator.createWall(28, 1, 1, 7);
		LevelCreator.createWall(22, 20, 1, 5);
		LevelCreator.createWall(19, 19, 7, 1);
		LevelCreator.createWall(25, 13, 1, 7);
		LevelCreator.createWall(28, 11, 1, 12);
		LevelCreator.createWall(25, 22, 3, 1);
		LevelCreator.createWall(31, 3, 1, 7);
		LevelCreator.createWall(34, 1, 1, 4);
		LevelCreator.createWall(35, 4, 1, 1);
		LevelCreator.createWall(32, 7, 7, 1);
		LevelCreator.createWall(38, 3, 1, 4);
		LevelCreator.createWall(31, 13, 1, 10);
		LevelCreator.createWall(34, 11, 1, 12);
		LevelCreator.createWall(32, 22, 2, 1);
		LevelCreator.createWall(37, 13, 2, 1);
		LevelCreator.createWall(37, 14, 1, 11);
		LevelCreator.createWall(38, 18, 1, 1);
		LevelCreator.createWall(38, 22, 7, 1);
		LevelCreator.createWall(41, 3, 1, 5);
		LevelCreator.createWall(41, 10, 1, 10);
		LevelCreator.createWall(40, 15, 1, 1);
		LevelCreator.createWall(42, 3, 3, 1);
		LevelCreator.createWall(44, 4, 1, 15);
		LevelCreator.createWall(42, 19, 6, 1);
		LevelCreator.createWall(47, 1, 1, 16);
		LevelCreator.createWall(47, 20, 1, 5);
	}
}
