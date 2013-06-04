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
		settingData();
		level = levelnr;						// level nimmt den Wert vom Parameter an -> dient als Laufvariable
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
	
	private static void settingData() {
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.monsterList.clear();				// Monsterbestand wird auf 0 gesetzt
		Tracker.trackerList.clear();			// Trackerbestand wird auf 0 gesetzt
		Weapon.loadWeapons();					// Alle Waffen laden
	}
	
	public static void setNextLevel() {
		settingData();
		level++;								// Laufvariable level wird um 1 erhöht
		setLevel(level);						// Level nr level wird gestartet
		Player.resetAllPlayerPositions();		// Position der Spieler wird neu gesetzt
	}
	

	public static void setFinalGoal() {			// wird gestartet wenn das Ziel des Spiels erreicht wurde
		shutdownLevel();						// das ganze Spiel wird zurückgesetzt und beendet
		WinWindow.main(null);					// das "GewonnenFenster" öffnet sich
	}
	
	public static void resetLevel() {
		settingData();
		setLevel(level);						// Level nr level wird gestartet (Das gleiche Level)
		Player.resetAllPlayerPositions();		// Position der Spieeler wird neu gesetzt.
	}
	
	public static void shutdownLevel() {
		Player.codeRunning = false;				// Flag, die verhindert, dass Codereste ausgeführt werden, wenn bereits keine Objekte mehr existieren
		Enemy.codeRunning = false;
		settingData();
		Player.playerList.clear();				// Position der Spieler wird neu gesetzt
		Weapon.weaponList.clear();				// Alle Waffen löschen
		WeaponManager.weaponManagerList.clear();// Alle eingesammelten Waffen werden gelöscht
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
	
	/**
	 *  Level 1 - Raum 1
	 */
	private static void setLevel1(){
		
		/*
		 * Default Player Position
		 */
		setPlayerDefaultPos(0, 3);
		
		/*
		 * Items
		 */
		//LevelCreator.createHealthPoint(18, 24);
		//LevelCreator.createShield(6, 24);
		
		/*
		 * Monster
		 */
				//horizontal
		Enemy.createMonster(22*20,2*20);
		Enemy.createMonster(27*20,3*20);
		Enemy.createMonster(23*20,7*20);
		Enemy.createMonster(14*20,8*20);
				//vertikal
		Enemy.createMonster(20*20, 2*20, 0, 1);
		Enemy.createMonster(26*20, 5*20, 0, 1);
		Enemy.createMonster(25*20, 10*20, 0, 1);
		Enemy.createMonster(24*20, 9*20, 0, 1);
		Enemy.createMonster(17*20, 9*20, 0, 1);
		Enemy.createMonster(16*20, 10*20, 0, 1);
				//right-up
		Enemy.createMonster(30*20, 2*20, 1, -1);
				//left-up
		Enemy.createMonster(16*20, 2*20, -1, -1);
				// Tracker
		Tracker.createTracker(31*20, 23*20);
		Tracker.createTracker(34*20, 20*20);
				// Bouncy
		Enemy.createMonster(46*20, 20*20, -1, -1, 1);
		Enemy.createMonster(37*20, 5*20, -1, -1, 1);
		
		/*
		 * Goal
		 */
		LevelCreator.createGoal(47,21,1,2);
		
		/*
		 * Traps
		 */
		LevelCreator.createTrap(1, 4);
		LevelCreator.createTrap(2, 4);
		LevelCreator.createTrap(3, 4);
		LevelCreator.createTrap(4, 4);
		LevelCreator.createTrap(5, 4);
		LevelCreator.createTrap(6, 4);
		LevelCreator.createTrap(7, 4);
		LevelCreator.createTrap(8, 4);
		LevelCreator.createTrap(9, 4);

		LevelCreator.createTrap(2, 7);
		LevelCreator.createTrap(3, 7);
		LevelCreator.createTrap(4, 7);
		LevelCreator.createTrap(5, 7);
		LevelCreator.createTrap(6, 7);
		LevelCreator.createTrap(7, 7);
		LevelCreator.createTrap(8, 7);
		LevelCreator.createTrap(9, 7);
		LevelCreator.createTrap(10, 7);

		LevelCreator.createTrap(1, 9);
		LevelCreator.createTrap(2, 9);
		LevelCreator.createTrap(3, 9);
		LevelCreator.createTrap(4, 9);
		LevelCreator.createTrap(5, 9);
		LevelCreator.createTrap(6, 9);
		LevelCreator.createTrap(7, 9);
		LevelCreator.createTrap(8, 9);
		LevelCreator.createTrap(9, 9);

		LevelCreator.createTrap(9, 5);
		LevelCreator.createTrap(5, 5);
		LevelCreator.createTrap(7, 6);
		LevelCreator.createTrap(3, 6);
		
		/*
		 * Walls
		 */
		LevelCreator.createWall(0, 0, 1, 2);
		LevelCreator.createWall(1, 0, 47, 1);
		LevelCreator.createWall(47, 1, 1, 20);
		LevelCreator.createWall(47, 23, 1, 2);
		LevelCreator.createWall(0, 4, 1, 21);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(11, 1, 1, 22);
		LevelCreator.createWall(36, 2, 1, 22);
		LevelCreator.createWall(14, 6, 22, 1);
		LevelCreator.createWall(12, 12, 22, 1);
	}
	/**
	 * Level 1 - Raum 2
	 */
	private static void setLevel2(){
		
		/*
		 * Default Player Position
		 */
		setPlayerDefaultPos(0, 3);
		
		/*
		 * Goal
		 */
		LevelCreator.createGoal(47, 22, 1, 2);
		
		/*
		 * Items
		 */
		LevelCreator.createHealthPoint(18, 2);
		LevelCreator.createShield(6, 17);
		
		/*
		 * Traps
		 */
		LevelCreator.createTrap(2, 2);
		LevelCreator.createTrap(2, 3);
		LevelCreator.createTrap(4, 4);
		LevelCreator.createTrap(4, 1);
		LevelCreator.createTrap(6, 2);
		LevelCreator.createTrap(8, 4);
		LevelCreator.createTrap(8, 1);
		LevelCreator.createTrap(10, 2);
		LevelCreator.createTrap(11, 2);
		LevelCreator.createTrap(12, 2);
		LevelCreator.createTrap(13, 2);
		LevelCreator.createTrap(10, 3);
		LevelCreator.createTrap(11, 3);
		LevelCreator.createTrap(12, 3);
		LevelCreator.createTrap(13, 3);
		LevelCreator.createTrap(14, 3);
		LevelCreator.createTrap(15, 3);
		LevelCreator.createTrap(15, 2);
		LevelCreator.createTrap(16, 2);
		LevelCreator.createTrap(17, 2);
		LevelCreator.createTrap(17, 3);
		LevelCreator.createTrap(18, 3);
		LevelCreator.createTrap(19, 3);
		LevelCreator.createTrap(20, 3);
		LevelCreator.createTrap(21, 3);
		LevelCreator.createTrap(22, 3);
		LevelCreator.createTrap(19, 2);
		LevelCreator.createTrap(20, 2);
		LevelCreator.createTrap(21, 2);
		LevelCreator.createTrap(22, 2);
		
		
		/*
		 * Monster
		 */
				// Monster horizontal
		Enemy.createMonster(11*20, 1*20, 1, 0);
		Enemy.createMonster(21*20, 4*20, 1, 0);
		Enemy.createMonster(14*20, 13*20, 1, 0);
		Enemy.createMonster(14*20, 12*20, 1, 0);
		Enemy.createMonster(14*20, 11*20, 1, 0);
				// Monster vertikal
		Enemy.createMonster(26*20, 1*20, 0, 1);
		Enemy.createMonster(27*20, 1*20, 0, 1);
		Enemy.createMonster(28*20, 1*20, 0, 1);
		Enemy.createMonster(33*20, 1*20, 0, 1);
		Enemy.createMonster(34*20, 1*20, 0, 1);
		Enemy.createMonster(35*20, 1*20, 0, 1);
		Enemy.createMonster(39*20, 1*20, 0, 1);
		Enemy.createMonster(40*20, 1*20, 0, 1);
		Enemy.createMonster(41*20, 1*20, 0, 1);
		Enemy.createMonster(44*20, 1*20, 0, 1);
		Enemy.createMonster(45*20, 1*20, 0, 1);
		Enemy.createMonster(46*20, 1*20, 0, 1);
		Enemy.createMonster(32*20, 16*20, 0, 1);
		Enemy.createMonster(29*20, 18*20, 0, 1);
		Enemy.createMonster(26*20, 19*20, 0, 1);
		Enemy.createMonster(23*20, 17*20, 0, 1);
		Enemy.createMonster(20*20, 16*20, 0, 1);
		Enemy.createMonster(17*20, 18*20, 0, 1);
		Enemy.createMonster(4*20, 21*20, 0, 1);
		Enemy.createMonster(5*20, 21*20, 0, 1);
		Enemy.createMonster(6*20, 21*20, 0, 1);
		Enemy.createMonster(10*20, 21*20, 0, 1);
		Enemy.createMonster(11*20, 21*20, 0, 1);
		Enemy.createMonster(12*20, 21*20, 0, 1);
		Enemy.createMonster(16*20, 21*20, 0, 1);
		Enemy.createMonster(19*20, 23*20, 0, 1);
		Enemy.createMonster(22*20, 24*20, 0, 1);
		Enemy.createMonster(25*20, 22*20, 0, 1);
		Enemy.createMonster(29*20, 21*20, 0, 1);
				// right-up
		Enemy.createMonster(28*20, 9*20, 1, -1);
		Enemy.createMonster(11*20, 6*20, 1, -1);
		Enemy.createMonster(13*20, 9*20, 1, -1);
		Enemy.createMonster(31*20, 24*20, 1, -1);
		Enemy.createMonster(34*20, 24*20, 1, -1);
				// left-up
		Enemy.createMonster(28*20, 11*20, -1, -1);
		Enemy.createMonster(20*20, 9*20, -1, -1);
		Enemy.createMonster(4*20, 6*20, -1, -1);
		Enemy.createMonster(41*20, 24*20, -1, -1);
		Enemy.createMonster(44*20, 24*20, -1, -1);
		
		/*
		 * Walls
		 */
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 1, 1, 1);
		LevelCreator.createWall(0, 4, 1, 21);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(47, 1, 1, 21);
		LevelCreator.createWall(47, 24, 1, 1);
		LevelCreator.createWall(1, 5, 44, 1);
		LevelCreator.createWall(24, 1, 1, 1);
		LevelCreator.createWall(24, 4, 1, 12);
		LevelCreator.createWall(15, 15, 30, 1);
		LevelCreator.createWall(4, 10, 18, 1);
		LevelCreator.createWall(12, 9, 1, 11);
		LevelCreator.createWall(1, 15, 9, 1);
		LevelCreator.createWall(3, 20, 44, 1);
		LevelCreator.createWall(27, 10, 20, 1);
		LevelCreator.createWall(12, 6, 1, 1);
		LevelCreator.createWall(36, 6, 1, 1);
		LevelCreator.createWall(36, 9, 1, 1);
		LevelCreator.createWall(36, 16, 1, 1);
		LevelCreator.createWall(36, 19, 1, 1);
	}	
	/**
	 * Level1 - Raum 3
	 */
	private static void setLevel3(){
		
		/*
		 * Default Player Position
		 */
		setPlayerDefaultPos(0, 24);
		
		/*
		 * Final Goal
		 */
		LevelCreator.createFinalGoal(0, 16, 1, 4);
		
		/*
		 * Items
		 */
		LevelCreator.createHealthPoint(22, 10);
		
		/*
		 * Traps
		 */
		
		/*
		 * Monster
		 */
				// Tracker
		Tracker.createTracker(39*20, 16*20);
		Tracker.createTracker(31*20, 16*20);
		Tracker.createTracker(31*20, 13*20);
		Tracker.createTracker(43*20, 2*20);
		Tracker.createTracker(6*20, 13*20);
		Tracker.createTracker(16*20, 6*20);
		Tracker.createTracker(20*20, 6*20);
		Tracker.createTracker(20*20, 13*20);
		Tracker.createTracker(24*20, 6*20);
		Tracker.createTracker(24*20, 13*20);
		Tracker.createTracker(29*20, 6*20);
		Tracker.createTracker(14*20, 16*20);
		Tracker.createTracker(1*20, 16*20);
		
		/*
		 * Walls
		 */
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 1, 1, 16);
		LevelCreator.createWall(0, 19, 1, 4);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(1, 15, 26, 1);
		LevelCreator.createWall(1, 20, 30, 1);
		LevelCreator.createWall(7, 2, 1, 12);
		LevelCreator.createWall(8, 6, 8, 1);
		LevelCreator.createWall(15, 1, 1, 3);
		LevelCreator.createWall(15, 5, 27, 1);
		LevelCreator.createWall(15, 8, 1, 11);
		LevelCreator.createWall(22, 1, 1, 3);
		LevelCreator.createWall(22, 22, 1, 3);
		LevelCreator.createWall(30, 6, 1, 14);
		LevelCreator.createWall(32, 11, 15, 1);
		LevelCreator.createWall(31, 15, 9, 1);
		LevelCreator.createWall(40, 15, 1, 9);
		LevelCreator.createWall(42, 2, 1, 4);
		LevelCreator.createWall(47, 1, 1, 24);
		
	}
}
