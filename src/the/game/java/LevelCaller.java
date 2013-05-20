package the.game.java;

public class LevelCaller {
	
	private static int level=0;		// Laufvariable für Level
	private static int defaultX;	// Startposition (x) für Player
	private static int defaultY;	// Startposition (y) für Player
	
	public LevelCaller(){
		// sobald ein Objekt der Klasse LevelCaller erstellt wird, wird auch ein Objekt der Klasse LevelCreator erstellt
		// die beiden Klassen sind von einander abhängig und haben allein keinen Sinn
		// sie lieben sich und werden viele kleine Methoden haben
		new LevelCreator();	
	}
	
	public static void setLevel(int levelnr){
		level = levelnr;						// level nimmt den Wert vom Parameter an -> dient als Laufvariable
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.monsterList.clear();				// Monsterbestand wird auf 0 gesetzt
		Tracker.trackerList.clear();			// Trackerbestand wird auf 0 gesetzt
		switch(levelnr) {						// Levelnr wird überprüft:
		case 1:
			setLevel1();							// Level 1 wird ausgeführt
			break;
		case 2:
			setLevel2();							// Level 2 wird ausgeführt
			break;
		case 3:
			setLevel3();							// Level 3 wird ausgeführt
			break;
		}
	}
	
	public static void setNextLevel() {
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.monsterList.clear();				// Monsterbestand wird auf 0 gesetzt
		Tracker.trackerList.clear();			// Trackerbestand wird auf 0 gesetzt
		level++;								// Laufvariable level wird um 1 erhöht
		setLevel(level);						// Level nr level wird gestartet
		Player.resetAllPlayerPositions();		// Position der Spieler wird neu gesetzt
	}
	

	public static void setFinalGoal() {			// wird gestartet wenn das Ziel des Spiels erreicht wurde
		shutdownLevel();						// das ganze Spiel wird zurückgesetzt und beendet
		WinWindow.main(null);					// das "GewonnenFenster" öffnet sich
	}
	
	public static void resetLevel() {
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.monsterList.clear();				// Monsterbestand wird auf 0 gesetzt
		Tracker.trackerList.clear();			// Trackerbestand wird auf 0 gesetzt
		setLevel(level);						// Level nr level wird gestartet (Das gleiche Level)
		Player.resetAllPlayerPositions();		// Position der Spieeler wird neu gesetzt.
	}
	
	public static void shutdownLevel() {
		Player.codeRunning = false;				// Flag, die verhindert, dass Codereste ausgeführt werden, wenn bereits keine Objekte mehr existieren
		Enemy.codeRunning = false;
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.monsterList.clear();				// Monsterbestand wird auf 0 gesetzt
		Tracker.trackerList.clear();			// Trackerbestand wird auf 0 gesetzt
		Player.playerList.clear();				// Position der Spieler wird neu gesetzt
		Setter.timer.stop();					// Timer ausschalten
		Runner.shutRunnerDown();				// beendet das Speilfenster
	}
	
	public static int getLevelNumber() {		// gibt Levelnr aus
		return level;
	}
	
	public static int getPlayerDefaultPosX() {	// gibt Startposition (x) des Players aus
		return defaultX;
	}
	
	public static int getPlayerDefaultPosY() {	// gibt Startposition (y) des Players aus
		return defaultY;
	}
	
	private static void setPlayerDefaultPos(int defX, int defY) {	// ändert die Startposition des Players
		defaultX = defX*20;
		defaultY = defY*20;
	}
	
	private static void setLevel1(){
		
		// Default Player Position
		setPlayerDefaultPos(0, 13);
		
		// Items
		LevelCreator.createHealthPoint(18, 24);
		
		// Monster
		Enemy.createMonster(600, 320, 1, 1, 1);
		Enemy.createMonster(820, 400, -1, -1, 1);
		Enemy.createMonster(120, 120, -1, -1, 1);
		
		// Tracker
		Tracker.createTracker(16*20, 22*20, -1, -1);
		Tracker.createTracker(20*20, 22*20, -1, -1);

		// Goal
		LevelCreator.createGoal(47,13,1,3);
		
		// Traps
		LevelCreator.createTrap(10,11);
		
		// Walls
		LevelCreator.createWall(0,1,48,1);
		LevelCreator.createWall(0,26,48,1);
		LevelCreator.createWall(0,1,1,12);
		LevelCreator.createWall(0,16,1,12);
		LevelCreator.createWall(47,1,1,12);
		LevelCreator.createWall(24,1,1,12);
		LevelCreator.createWall(24,16,1,12);
		LevelCreator.createWall(12,4,1,24);
		LevelCreator.createWall(36,4,1,24);
		LevelCreator.createWall(47,16,1,12);
	}
	
	private static void setLevel2(){
		
		// Default Player Position
		setPlayerDefaultPos(0, 13);
		
		// Goal
		LevelCreator.createGoal(47, 12, 1, 3);
		
		// Traps
		LevelCreator.createTrap(2,13);
		LevelCreator.createTrap(2,14);
		LevelCreator.createTrap(7,18);
		LevelCreator.createTrap(8,18);
		LevelCreator.createTrap(10,18);
		LevelCreator.createTrap(11,18);
		LevelCreator.createTrap(9,16);
		LevelCreator.createTrap(42,18);
		LevelCreator.createTrap(40,19);
		LevelCreator.createTrap(44,19);
		LevelCreator.createTrap(44,21);
		LevelCreator.createTrap(40,21);
		LevelCreator.createTrap(42,22);
		LevelCreator.createTrap(43,7);
		LevelCreator.createTrap(44,7);
		LevelCreator.createTrap(45,7);
		LevelCreator.createTrap(44,9);
		LevelCreator.createTrap(45,9);
		LevelCreator.createTrap(46,9);
		
		
		// Monster
			// Monster Bouncy
		Enemy.createMonster(414, 180, -1, -1, 1);
		Enemy.createMonster(512, 60, -1, -1, 1);
		Enemy.createMonster(614, 480, -1, -1, 1);
			// Monster horizontal
		Enemy.createMonster(20, 460, 1, 0);
		Enemy.createMonster(140, 460, 1, 0);
		Enemy.createMonster(270, 120, 1, 0);
		Enemy.createMonster(290, 460, 1, 0);
		Enemy.createMonster(360, 120, 1, 0);
		Enemy.createMonster(400, 460, 1, 0);
		Enemy.createMonster(470, 120, 1, 0);
		Enemy.createMonster(460, 460, 1, 0);
		Enemy.createMonster(580, 120, 1, 0);
		Enemy.createMonster(570, 460, 1, 0);
		Enemy.createMonster(690, 120, 1, 0);
		Enemy.createMonster(660, 460, 1, 0);
		Enemy.createMonster(770, 120, 1, 0);
		Enemy.createMonster(780, 280, 1, 0);
		Enemy.createMonster(910, 120, 1, 0);
		
		// Walls
		LevelCreator.createWall(0, 1, 48, 1);
		LevelCreator.createWall(0, 26, 48, 1);
		LevelCreator.createWall(0, 2, 1, 10);
		LevelCreator.createWall(0, 12, 7, 1);
		LevelCreator.createWall(6, 13, 1, 11);
		LevelCreator.createWall(0, 15, 1, 11);
		LevelCreator.createWall(7, 2, 1, 5);
		LevelCreator.createWall(3, 4, 1, 6);
		LevelCreator.createWall(4, 9, 8, 1);
		LevelCreator.createWall(12, 4, 1, 22);
		LevelCreator.createWall(17, 2, 1, 22);
		LevelCreator.createWall(22, 4, 1, 22);
		LevelCreator.createWall(27, 2, 1, 22);
		LevelCreator.createWall(32, 4, 1, 22);
		LevelCreator.createWall(37, 2, 1, 22);
		LevelCreator.createWall(38, 23, 7, 1);
		LevelCreator.createWall(38, 17, 7, 1);
		LevelCreator.createWall(40, 20, 7, 1);
		LevelCreator.createWall(42, 4, 1, 11);
		LevelCreator.createWall(47, 2, 1, 10);
		LevelCreator.createWall(43, 14, 5, 1);
		LevelCreator.createWall(47, 15, 1, 11);
	}	
	
	private static void setLevel3(){
		
		// Default Player Position
		setPlayerDefaultPos(0, 5);
		
		// Final Goal
		LevelCreator.createFinalGoal(47, 18, 1, 3);
		
		// Items
		LevelCreator.createHealthPoint(6, 11);
		LevelCreator.createHealthPoint(38, 25);
		
		// Traps
		LevelCreator.createTrap(7,16);
		LevelCreator.createTrap(8,21);
		LevelCreator.createTrap(26,25);
		LevelCreator.createTrap(33,25);
		LevelCreator.createTrap(41,21);
		LevelCreator.createTrap(38,2);
		LevelCreator.createTrap(42,2);
		LevelCreator.createTrap(45,6);
		LevelCreator.createTrap(45,9);
		LevelCreator.createTrap(45,12);
		LevelCreator.createTrap(45,15);
		
		// Monster
			// Monster Horizontal
		Enemy.createMonster(80, 60, -1, 0);
		Enemy.createMonster(180, 40, -1, 0);
		Enemy.createMonster(180, 160, -1, 0);
		Enemy.createMonster(120, 300, -1, 0);
		Enemy.createMonster(180, 440, -1, 0);
		Enemy.createMonster(440, 180, -1, 0);
		Enemy.createMonster(540, 60, -1, 0);
		Enemy.createMonster(600, 480, -1, 0);
		Enemy.createMonster(720, 260, -1, 0);
		Enemy.createMonster(860, 440, -1, 0);
		Enemy.createMonster(880, 60, -1, 0);
			//Monster Vertikal
		Enemy.createMonster(40, 120, 0, 1);
		Enemy.createMonster(140, 160, 0, 1);
		Enemy.createMonster(360, 360, 0, 1);
		Enemy.createMonster(520, 100, 0, 1);
		Enemy.createMonster(600, 320, 0, 1);
		Enemy.createMonster(780, 380, 0, 1);
		Enemy.createMonster(920, 160, 0, 1);
		
		// Walls
		LevelCreator.createWall(0, 1, 48, 1);
		LevelCreator.createWall(0, 26, 48, 1);
		LevelCreator.createWall(0, 7, 1, 19);
		LevelCreator.createWall(0, 2, 1, 3);
		LevelCreator.createWall(1, 4, 2, 1);
		LevelCreator.createWall(5, 4, 3, 1);
		LevelCreator.createWall(5, 4, 1, 4);
		LevelCreator.createWall(3, 7, 2, 1);
		LevelCreator.createWall(3, 8, 1, 7);
		LevelCreator.createWall(4, 14, 4, 1);
		LevelCreator.createWall(7, 10, 1, 4);
		LevelCreator.createWall(5, 10, 1, 3);
		LevelCreator.createWall(6, 10, 1, 1);
		LevelCreator.createWall(10, 2, 1, 6);
		LevelCreator.createWall(8, 7, 2, 1);
		LevelCreator.createWall(1, 17, 7, 1);
		LevelCreator.createWall(3, 20, 1, 4);
		LevelCreator.createWall(4, 20, 9, 1);
		LevelCreator.createWall(13, 20, 1, 6);
		LevelCreator.createWall(6, 23, 5, 1);
		LevelCreator.createWall(6, 24, 1, 2);
		LevelCreator.createWall(10, 17, 1, 3);
		LevelCreator.createWall(10, 10, 1, 5);
		LevelCreator.createWall(11, 14, 3, 1);
		LevelCreator.createWall(13, 15, 1, 3);
		LevelCreator.createWall(14, 17, 9, 1);
		LevelCreator.createWall(13, 4, 1, 8);
		LevelCreator.createWall(14, 11, 2, 1);
		LevelCreator.createWall(16, 4, 1, 13);
		LevelCreator.createWall(16, 18, 1, 6);
		LevelCreator.createWall(17, 23, 3, 1);
		LevelCreator.createWall(19, 2, 1, 4);
		LevelCreator.createWall(17, 8, 6, 1);
		LevelCreator.createWall(22, 4, 1, 4);
		LevelCreator.createWall(19, 12, 1, 3);
		LevelCreator.createWall(25, 4, 1, 7);
		LevelCreator.createWall(22, 12, 1, 5);
		LevelCreator.createWall(19, 11, 22, 1);
		LevelCreator.createWall(28, 2, 1, 7);
		LevelCreator.createWall(22, 21, 1, 5);
		LevelCreator.createWall(19, 20, 7, 1);
		LevelCreator.createWall(25, 14, 1, 7);
		LevelCreator.createWall(28, 12, 1, 12);
		LevelCreator.createWall(25, 23, 3, 1);
		LevelCreator.createWall(31, 4, 1, 7);
		LevelCreator.createWall(34, 2, 1, 4);
		LevelCreator.createWall(35, 5, 1, 1);
		LevelCreator.createWall(32, 8, 7, 1);
		LevelCreator.createWall(38, 4, 1, 4);
		LevelCreator.createWall(31, 14, 1, 10);
		LevelCreator.createWall(34, 12, 1, 12);
		LevelCreator.createWall(32, 23, 2, 1);
		LevelCreator.createWall(37, 14, 2, 1);
		LevelCreator.createWall(37, 15, 1, 11);
		LevelCreator.createWall(38, 19, 1, 1);
		LevelCreator.createWall(38, 23, 7, 1);
		LevelCreator.createWall(41, 4, 1, 5);
		LevelCreator.createWall(41, 11, 1, 10);
		LevelCreator.createWall(40, 16, 1, 1);
		LevelCreator.createWall(42, 4, 3, 1);
		LevelCreator.createWall(44, 5, 1, 15);
		LevelCreator.createWall(42, 20, 6, 1);
		LevelCreator.createWall(47, 2, 1, 16);
		LevelCreator.createWall(47, 21, 1, 5);
	}
}
