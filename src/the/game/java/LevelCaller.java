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
		case 4:
			setLevel4();
			break;
		case 5:
			setLevel5();
			break;
		case 6:
			setLevel6();
			break;
		case 7:
			setLevel7();
			break;
		case 8:
			setLevel8();
			break;
		case 9:
			setLevel9();
			break;
		}
		DisplayManager.displayMap();
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
		 * NPC
		 */
		
		NPC.createNPC(1, 3*20, 0*20+LevelCreator.distancePix, "down");
		NPC.createNPC(2, 10*20, 10*20+LevelCreator.distancePix, "left");
		NPC.createNPC(3, 35*20, 4*20+LevelCreator.distancePix, "up");

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
		LevelCreator.createWall(36, 2, 1, 23);
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
		 * NPC's
		 */
		
		NPC.createNPC(4, 40*20, 7*20+LevelCreator.distancePix, "down");
		NPC.createNPC(5, 6*20, 18*20+LevelCreator.distancePix, "down");
		
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
	 * Endgegner: Trackerhöhle
	 */
	private static void setLevel3(){
		
		/*
		 * Default Player Position
		 */
		setPlayerDefaultPos(0, 24);
		
		/*
		 * Final Goal
		 */
		LevelCreator.createGoal(0, 16, 1, 4);
		
		/*
		 * Items
		 */
		LevelCreator.createHealthPoint(22, 10);
		
		/*
		 *  NPC
		 */
		
		NPC.createNPC(6, 21*20, 1*20+LevelCreator.distancePix, "down");
		
		/*
		 * Traps
		 */
		
		/*
		 * Monster
		 */
				// Tracker
		Tracker.createTracker(39*20, 19*20);
		Tracker.createTracker(31*20, 19*20);
		Tracker.createTracker(31*20, 16*20);
		Tracker.createTracker(43*20, 3*20);
		Tracker.createTracker(6*20, 14*20);
		Tracker.createTracker(16*20, 7*20);
		Tracker.createTracker(20*20, 7*20);
		Tracker.createTracker(20*20, 14*20);
		Tracker.createTracker(24*20, 7*20);
		Tracker.createTracker(24*20, 14*20);
		Tracker.createTracker(29*20, 7*20);
		Tracker.createTracker(14*20, 17*20);
		Tracker.createTracker(1*20, 17*20);
		
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
	
	/**
	 * Level2 - Raum 1
	 */
	
	public static void setLevel4(){
		
		/*
		 *  Default-Position
		 */
		
		setPlayerDefaultPos(0, 25);
		
		/*
		 * Goal
		 */
		
		LevelCreator.createGoal(47, 22, 1, 4);
		
		/*
		 * Monster
		 */
		
			// Tracker
		Tracker.createTracker(22*20, 13*20);
			// Horizontal
		Enemy.createMonster(42*20,11*20);
		Enemy.createMonster(37*20,4*20);
		Enemy.createMonster(31*20,20*20);
		Enemy.createMonster(26*20,22*20);
		Enemy.createMonster(19*20,20*20);
		Enemy.createMonster(17*20,22*20);
		Enemy.createMonster(20*20,3*20);
		Enemy.createMonster(14*20,5*20);
		Enemy.createMonster(11*20,13*20);
		Enemy.createMonster(1*20,12*20);
			// vertikal
		Enemy.createMonster(45*20,6*20, 0, 1);
		
		/*
		 * Wände
		 */
		
		LevelCreator.createWall(0, 0, 1, 24);
		LevelCreator.createWall(1, 0, 10, 3);
		LevelCreator.createWall(3, 3, 7, 9);
		LevelCreator.createWall(3, 14, 7, 9);
		LevelCreator.createWall(3, 23, 19, 2);
		LevelCreator.createWall(1, 23, 1, 1);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(12, 2, 2, 21);
		LevelCreator.createWall(11, 0, 37, 1);
		LevelCreator.createWall(14, 2, 8, 1);
		LevelCreator.createWall(16, 6, 7, 6);
		LevelCreator.createWall(16, 14, 7, 6);
		LevelCreator.createWall(23, 1, 2, 23);
		LevelCreator.createWall(25, 1, 1, 2);
		LevelCreator.createWall(25, 23, 8, 1);
		LevelCreator.createWall(27, 2, 9, 18);
		LevelCreator.createWall(34, 20, 2, 5);
		LevelCreator.createWall(36, 2, 9, 1);
		LevelCreator.createWall(46, 1, 2, 4);
		LevelCreator.createWall(38, 5, 7, 6);
		LevelCreator.createWall(38, 13, 10, 10);
		LevelCreator.createWall(36, 23, 1, 2);
		LevelCreator.createWall(38, 23, 8, 1);
		LevelCreator.createWall(47, 5, 1, 8);
		LevelCreator.createWall(46, 11, 1, 2);
		LevelCreator.createWall(45, 12, 1, 1);
	}
	
	/**
	 * Level 2 - Raum 2
	 */
	
	public static void setLevel5(){
		
		/*
		 *  Default-Position
		 */
		
		setPlayerDefaultPos(0, 3);
		
		/*
		 * Goal
		 */
		
		LevelCreator.createGoal(47, 20, 1, 4);
		
		/*
		 * Items
		 */
		
		LevelCreator.createHealthPoint(25, 24);
		LevelCreator.createShield(6, 14);
		
		/*
		 * Monster
		 */
		
			// Bouncy
		Enemy.createMonster(38*20, 3*20, -1, -1, 1);
		Enemy.createMonster(44*20, 8*20, -1, -1, 1);
			// Horizontal
		Enemy.createMonster(28*20,8*20);
		Enemy.createMonster(22*20,8*20);
		Enemy.createMonster(25*20,10*20);
		Enemy.createMonster(25*20,13*20);
		Enemy.createMonster(41*20,21*20);
		Enemy.createMonster(7*20,22*20);
		Enemy.createMonster(5*20,23*20);
		Enemy.createMonster(13*20,12*20);
		Enemy.createMonster(13*20,10*20);
		Enemy.createMonster(15*20,5*20);
		
		/*
		 * Traps
		 */
		
		LevelCreator.createTrap(35, 1);
		LevelCreator.createTrap(40, 7);
		LevelCreator.createTrap(45, 12);
		LevelCreator.createTrap(45, 11);
		LevelCreator.createTrap(45, 10);
		LevelCreator.createTrap(45, 9);
		LevelCreator.createTrap(45, 8);
		LevelCreator.createTrap(34, 11);
		LevelCreator.createTrap(32, 11);
		LevelCreator.createTrap(32, 15);
		LevelCreator.createTrap(31, 15);
		LevelCreator.createTrap(25, 21);
		LevelCreator.createTrap(21, 21);
		LevelCreator.createTrap(23, 22);
		LevelCreator.createTrap(19, 22);
		LevelCreator.createTrap(21, 23);
		LevelCreator.createTrap(23, 24);
		LevelCreator.createTrap(19, 24);
		
		/*
		 * Wände
		 */
		
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 1, 1, 1);
		LevelCreator.createWall(5, 1, 9, 2);
		LevelCreator.createWall(7, 3, 5, 1);
		LevelCreator.createWall(8, 4, 2, 1);
		LevelCreator.createWall(9, 5, 1, 1);	
		LevelCreator.createWall(13, 3, 1, 1);
		LevelCreator.createWall(15, 2, 5, 2);
		LevelCreator.createWall(16, 4, 4, 1);
		LevelCreator.createWall(17, 5, 3, 1);
		LevelCreator.createWall(20, 1, 4, 1);
		LevelCreator.createWall(21, 2, 3, 4);
		LevelCreator.createWall(21, 6, 2, 1);
		LevelCreator.createWall(24, 4, 5, 1);
		LevelCreator.createWall(27, 5, 2, 1);
		LevelCreator.createWall(28, 6, 1, 1);
		LevelCreator.createWall(18, 7, 4, 1);
		LevelCreator.createWall(16, 8, 5, 3);
		LevelCreator.createWall(17, 11, 5, 7);
		LevelCreator.createWall(22, 13, 2, 3);
		LevelCreator.createWall(22, 12, 1, 1);
		LevelCreator.createWall(24, 14, 1, 1);
		LevelCreator.createWall(22, 16, 1, 1);
		LevelCreator.createWall(16, 18, 4, 3);
		LevelCreator.createWall(20, 18, 1, 1);
		LevelCreator.createWall(17, 21, 2, 1);
		LevelCreator.createWall(18, 22, 1, 1);
		LevelCreator.createWall(10, 14, 7, 3);
		LevelCreator.createWall(14, 17, 3, 2);
		LevelCreator.createWall(16, 13, 1, 1);
		LevelCreator.createWall(15, 19, 1, 1);
		LevelCreator.createWall(13, 17, 1, 1);
		LevelCreator.createWall(4, 13, 9, 1);
		LevelCreator.createWall(9, 14, 1, 1);
		LevelCreator.createWall(4, 14, 2, 8);
		LevelCreator.createWall(6, 19, 1, 3);
		LevelCreator.createWall(7, 15, 1, 3);
		LevelCreator.createWall(8, 17, 1, 1);
		LevelCreator.createWall(0, 4, 1, 22);
		LevelCreator.createWall(1, 5, 1, 4);
		LevelCreator.createWall(2, 5, 5, 1);
		LevelCreator.createWall(4, 6, 4, 2);
		LevelCreator.createWall(5, 4, 1, 1);
		LevelCreator.createWall(7, 8, 3, 3);
		LevelCreator.createWall(8, 7, 1, 1);
		LevelCreator.createWall(6, 8, 1, 1);
		LevelCreator.createWall(6, 11, 5, 1);
		LevelCreator.createWall(11, 7, 3, 2);
		LevelCreator.createWall(12, 6, 2, 1);
		LevelCreator.createWall(11, 9, 2, 1);
		LevelCreator.createWall(2, 6, 1, 1);
		LevelCreator.createWall(1, 12, 1, 2);
		LevelCreator.createWall(2, 16, 2, 1);
		LevelCreator.createWall(1, 18, 2, 2);
		LevelCreator.createWall(2, 21, 2, 1);
		LevelCreator.createWall(1, 23, 1, 3);
		LevelCreator.createWall(2, 25, 46, 1);
		LevelCreator.createWall(11, 21, 4, 4);
		LevelCreator.createWall(11, 19, 2, 2);
		LevelCreator.createWall(11, 18, 1, 1);
		LevelCreator.createWall(13, 20, 1, 1);
		LevelCreator.createWall(8, 24, 3, 1);
		LevelCreator.createWall(10, 23, 1, 1);
		LevelCreator.createWall(15, 22, 1, 3);
		LevelCreator.createWall(16, 23, 1, 2);
		LevelCreator.createWall(17, 24, 1, 1);
		LevelCreator.createWall(24, 8, 2, 2);
		LevelCreator.createWall(23, 8, 1, 1);
		LevelCreator.createWall(25, 2, 9, 1);
		LevelCreator.createWall(31, 3, 5, 5);
		LevelCreator.createWall(36, 5, 2, 3);
		LevelCreator.createWall(36, 4, 1, 1);
		LevelCreator.createWall(38, 6, 1, 2);
		LevelCreator.createWall(30, 8, 3, 3);
		LevelCreator.createWall(33, 8, 2, 1);
		LevelCreator.createWall(33, 9, 1, 1);
		LevelCreator.createWall(27, 13, 3, 3);
		LevelCreator.createWall(29, 11, 3, 2);
		LevelCreator.createWall(30, 13, 1, 1);
		LevelCreator.createWall(28, 12, 1, 1);
		LevelCreator.createWall(23, 18, 4, 3);
		LevelCreator.createWall(25, 16, 2, 2);
		LevelCreator.createWall(26, 15, 1, 1);
		LevelCreator.createWall(27, 16, 1, 1);
		LevelCreator.createWall(24, 17, 1, 1);
		LevelCreator.createWall(21, 20, 7, 1);
		LevelCreator.createWall(22, 19, 1, 1);
		LevelCreator.createWall(26, 21, 1, 2);
		LevelCreator.createWall(27, 21, 1, 1);
		LevelCreator.createWall(25, 22, 1, 2);
		LevelCreator.createWall(24, 23, 1, 2);
		LevelCreator.createWall(29, 17, 7, 4);
		LevelCreator.createWall(28, 18, 1, 1);
		LevelCreator.createWall(29, 21, 2, 1);
		LevelCreator.createWall(34, 21, 2, 1);
		LevelCreator.createWall(36, 9, 6, 5);
		LevelCreator.createWall(37, 13, 9, 2);
		LevelCreator.createWall(42, 11, 2, 6);
		LevelCreator.createWall(40, 8, 1, 1);
		LevelCreator.createWall(42, 10, 1, 1);
		LevelCreator.createWall(44, 12, 1, 1);
		LevelCreator.createWall(33, 13, 2, 3);
		LevelCreator.createWall(32, 14, 1, 1);
		LevelCreator.createWall(35, 10, 1, 4);
		LevelCreator.createWall(34, 12, 1, 1);
		LevelCreator.createWall(41, 15, 5, 1);
		LevelCreator.createWall(45, 16, 1, 1);
		LevelCreator.createWall(38, 16, 1, 1);
		LevelCreator.createWall(36, 16, 1, 4);
		LevelCreator.createWall(37, 18, 9, 2);
		LevelCreator.createWall(40, 17, 1, 1);
		LevelCreator.createWall(43, 20, 3, 2);
		LevelCreator.createWall(42, 20, 1, 1);
		LevelCreator.createWall(28, 23, 20, 2);
		LevelCreator.createWall(27, 24, 1, 1);
		LevelCreator.createWall(37, 22, 4, 1);
		LevelCreator.createWall(43, 3, 2, 3);
		LevelCreator.createWall(45, 3, 1, 1);
		LevelCreator.createWall(41, 2, 1, 3);
		LevelCreator.createWall(42, 2, 1, 1);
		LevelCreator.createWall(42, 4, 1, 1);
		LevelCreator.createWall(47, 1, 1, 20);
	}
	
	/**
	 * Level 2 - Raum 3
	 * Endgegner: Dalek
	 * "eliminiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiieren"
	 */
	
	public static void setLevel6(){
		/*
		 *  Default-Position
		 */
		
		setPlayerDefaultPos(44, 1);
		
		/*
		 * Goal
		 */
		
		LevelCreator.createGoal(0, 21, 1, 4);
		
		/*
		 * Monster
		 */
				// Horizontal
		Enemy.createMonster(46*20,8*20);
		Enemy.createMonster(46*20,13*20);
		Enemy.createMonster(45*20,9*20);
		Enemy.createMonster(45*20,12*20);
				// Vertikal
		Enemy.createMonster(6*20,18*20, 0, 1);
		Enemy.createMonster(5*20,18*20, 0, 1);
		Enemy.createMonster(3*20,24*20, 0, 1);
		Enemy.createMonster(2*20,24*20, 0, 1);
				// Dalek
		/*
		 (9, 9)
		 (23, 10)
		 (35, 9)
		 (20, 42)
		 (20, 30)
		 (20, 25)
		 (20, 20)
		 (12, 21)
		 */
		
		/*
		 * NPC
		 */
		//(43, 2);
		
		/*
		 * Wände
		 */
		
		LevelCreator.createWall(0, 0, 44, 1);
		LevelCreator.createWall(42, 1, 1, 2);
		LevelCreator.createWall(0, 1, 1, 21);
		LevelCreator.createWall(1, 15, 1, 7);
		LevelCreator.createWall(2, 2, 1, 12);
		LevelCreator.createWall(3, 2, 38, 1);
		LevelCreator.createWall(2, 16, 17, 2);
		LevelCreator.createWall(16, 4, 2, 19);
		LevelCreator.createWall(18, 18, 15, 2);
		LevelCreator.createWall(28, 3, 4, 2);
		LevelCreator.createWall(29, 5, 2, 11);
		LevelCreator.createWall(20, 16, 12, 1);
		LevelCreator.createWall(18, 4, 9, 1);
		LevelCreator.createWall(39, 3, 2, 2);
		LevelCreator.createWall(40, 4, 7, 2);
		LevelCreator.createWall(43, 6, 4, 2);
		LevelCreator.createWall(46, 0, 2, 1);
		LevelCreator.createWall(47, 0, 1, 26);
		LevelCreator.createWall(33, 4, 5, 1);
		LevelCreator.createWall(40, 7, 1, 7);
		LevelCreator.createWall(41, 7, 1, 1);
		LevelCreator.createWall(31, 14, 1, 2);
		LevelCreator.createWall(33, 14, 12, 2);
		LevelCreator.createWall(46, 14, 1, 2);
		LevelCreator.createWall(33, 16, 1, 7);
		LevelCreator.createWall(37, 16, 1, 7);
		LevelCreator.createWall(35, 17, 1, 7);
		LevelCreator.createWall(33, 24, 5, 1);
		LevelCreator.createWall(0, 25, 47, 1);
		LevelCreator.createWall(0, 24, 2, 1);
		LevelCreator.createWall(16, 24, 2, 1);
		LevelCreator.createWall(7, 18, 2, 1);
		LevelCreator.createWall(7, 20, 2, 5);
		LevelCreator.createWall(5, 3, 1, 2);
		LevelCreator.createWall(2, 15, 1, 1);
		LevelCreator.createWall(5, 9, 1, 1);
		LevelCreator.createWall(5, 12, 1, 2);
		LevelCreator.createWall(6, 13, 3, 1);
		LevelCreator.createWall(13, 13, 3, 1);
		LevelCreator.createWall(12, 10, 1, 2);
		LevelCreator.createWall(8, 6, 2, 1);
		LevelCreator.createWall(12, 6, 1, 1);
		LevelCreator.createWall(13, 7, 1, 1);
		LevelCreator.createWall(25, 7, 2, 1);
		LevelCreator.createWall(21, 7, 1, 1);
		LevelCreator.createWall(21, 9, 1, 1);
		LevelCreator.createWall(19, 9, 1, 1);
		LevelCreator.createWall(27, 11, 1, 2);
		LevelCreator.createWall(20, 13, 1, 1);
		LevelCreator.createWall(22, 13, 1, 1);
		LevelCreator.createWall(24, 13, 1, 1);
		LevelCreator.createWall(32, 12, 2, 1);
		LevelCreator.createWall(33, 7, 1, 1);
		LevelCreator.createWall(36, 6, 2, 1);
		LevelCreator.createWall(37, 10, 1, 1);
		LevelCreator.createWall(36, 11, 1, 1);
		LevelCreator.createWall(44, 17, 1, 1);
		LevelCreator.createWall(44, 23, 1, 1);
		LevelCreator.createWall(45, 18, 1, 1);
		LevelCreator.createWall(45, 22, 1, 1);
		LevelCreator.createWall(40, 17, 1, 1);
		LevelCreator.createWall(40, 23, 1, 1);
		LevelCreator.createWall(39, 18, 1, 1);
		LevelCreator.createWall(39, 22, 1, 1);
		LevelCreator.createWall(32, 22, 1, 1);
		LevelCreator.createWall(27, 23, 2, 1);
		LevelCreator.createWall(22, 23, 2, 1);
		LevelCreator.createWall(18, 22, 1, 1);
		LevelCreator.createWall(14, 23, 1, 1);
		LevelCreator.createWall(10, 23, 1, 1);
		LevelCreator.createWall(14, 19, 1, 1);
		LevelCreator.createWall(10, 19, 1, 1);
	}
	
	/**
	 * Level 3 - Raum 1
	 */
	public static void setLevel7(){
		
		/*
		 *  Default-Position
		 */
		
		setPlayerDefaultPos(0, 13);
		
		/*
		 * Goal
		 */
		
		LevelCreator.createGoal(47, 1, 1, 4);
		
		/*
		 * Monster
		 */
		
				// Horizontal
		Enemy.createMonster(5*20,2*20);
		Enemy.createMonster(8*20,2*20);
		Enemy.createMonster(18*20,2*20);
		Enemy.createMonster(32*20,2*20);
		Enemy.createMonster(40*20,2*20);
		Enemy.createMonster(17*20,5*20);
		Enemy.createMonster(32*20,5*20);
		Enemy.createMonster(5*20,23*20);
		Enemy.createMonster(14*20,23*20);
		Enemy.createMonster(27*20,23*20);
		Enemy.createMonster(39*20,23*20);
		Enemy.createMonster(34*20,12*20);
		Enemy.createMonster(19*20,12*20);
		Enemy.createMonster(44*20,20*20);
		Enemy.createMonster(44*20,18*20);
		Enemy.createMonster(44*20,14*20);
		Enemy.createMonster(44*20,12*20);
				// Vertikal
		Enemy.createMonster(7*20,14*20, 0, 1);
		Enemy.createMonster(16*20,20*20, 0, 1);
		Enemy.createMonster(19*20,20*20, 0, 1);
		Enemy.createMonster(22*20,24*20, 0, 1);
		Enemy.createMonster(23*20,11*20, 0, 1);
		Enemy.createMonster(45*20,22*20, 0, 1);
		Enemy.createMonster(45*20,16*20, 0, 1);
		Enemy.createMonster(45*20,10*20, 0, 1);
		Enemy.createMonster(23*20,1*20, 0, 1);
		Enemy.createMonster(25*20,6*20, 0, 1);
		Enemy.createMonster(42*20,5*20, 0, 1);

		/*
		 * Items
		 */
		
		LevelCreator.createHealthPoint(32, 15);
		LevelCreator.createShield(20, 1);
		LevelCreator.createShield(28, 1);
		
		/*
		 * Wände
		 */
		
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 1, 1, 11);
		LevelCreator.createWall(1, 11, 6, 1);
		LevelCreator.createWall(6, 1, 1, 5);
		LevelCreator.createWall(3, 3, 1, 6);
		LevelCreator.createWall(4, 8, 5, 1);
		LevelCreator.createWall(0, 14, 1, 11);
		LevelCreator.createWall(0, 25, 48, 1);
		LevelCreator.createWall(1, 14, 6, 1);
		LevelCreator.createWall(3, 17, 1, 6);
		LevelCreator.createWall(4, 17, 5, 1);
		LevelCreator.createWall(6, 20, 1, 5);
		LevelCreator.createWall(9, 3, 1, 20);
		LevelCreator.createWall(10, 7, 35, 1);
		LevelCreator.createWall(12, 1, 1, 4);
		LevelCreator.createWall(18, 1, 1, 1);
		LevelCreator.createWall(15, 3, 1, 1);
		LevelCreator.createWall(15, 4, 7, 1);
		LevelCreator.createWall(21, 1, 1, 3);
		LevelCreator.createWall(24, 3, 1, 4);
		LevelCreator.createWall(33, 1, 1, 1);
		LevelCreator.createWall(30, 3, 1, 1);
		LevelCreator.createWall(27, 1, 1, 4);
		LevelCreator.createWall(28, 4, 9, 1);
		LevelCreator.createWall(36, 3, 1, 1);
		LevelCreator.createWall(39, 3, 3, 1);
		LevelCreator.createWall(39, 4, 1, 3);
		LevelCreator.createWall(44, 1, 1, 6);
		LevelCreator.createWall(47, 1, 1, 1);
		LevelCreator.createWall(47, 4, 1, 21);
		LevelCreator.createWall(12, 10, 1, 13);
		LevelCreator.createWall(13, 10, 12, 1);
		LevelCreator.createWall(24, 11, 1, 12);
		LevelCreator.createWall(15, 13, 7, 1);
		LevelCreator.createWall(21, 14, 1, 11);
		LevelCreator.createWall(13, 16, 6, 1);
		LevelCreator.createWall(18, 17, 1, 6);
		LevelCreator.createWall(13, 22, 3, 1);
		LevelCreator.createWall(15, 19, 1, 3);
		LevelCreator.createWall(25, 22, 20, 1);
		LevelCreator.createWall(27, 8, 1, 12);
		LevelCreator.createWall(28, 19, 9, 1);
		LevelCreator.createWall(36, 10, 1, 9);
		LevelCreator.createWall(30, 10, 6, 1);
		LevelCreator.createWall(30, 11, 1, 6);
		LevelCreator.createWall(31, 16, 3, 1);
		LevelCreator.createWall(33, 13, 1, 3);
		LevelCreator.createWall(39, 8, 1, 14);
		LevelCreator.createWall(40, 10, 5, 1);
		LevelCreator.createWall(42, 13, 5, 1);
		LevelCreator.createWall(40, 16, 5, 1);
		LevelCreator.createWall(42, 19, 5, 1);
	}
	
	public static void setLevel8(){
		
		/*
		 *  Default-Position
		 */
		
		setPlayerDefaultPos(21, 1);
		
		/*
		 * Goal
		 */
		
		LevelCreator.createGoal(0, 25, 3, 2);
		
		/*
		 * NPC
		 */
		
		//(22,7);
		
		/*
		 * Items
		 */
		
		LevelCreator.createHealthPoint(36, 8);
		LevelCreator.createShield(12, 8);
		
		/*
		 * Monster
		 */
				//Horizontal
		Enemy.createMonster(40*20,1*20);
		Enemy.createMonster(13*20,3*20);
		Enemy.createMonster(21*20,12*20);
		Enemy.createMonster(26*20,14*20);
		Enemy.createMonster(18*20,18*20);
		Enemy.createMonster(38*20,18*20);
		Enemy.createMonster(28*20,20*20);
		Enemy.createMonster(30*20,24*20);
				//Vertikal
		Enemy.createMonster(6*20,5*20, 0, 1);
		Enemy.createMonster(42*20,7*20, 0, 1);
		Enemy.createMonster(15*20,9*20, 0, 1);
		Enemy.createMonster(33*20,10*20, 0, 1);
		Enemy.createMonster(44*20,13*20, 0, 1);
		Enemy.createMonster(1*20,15*20, 0, 1);
		
		/*
		 * Wände
		 */
		
		LevelCreator.createWall(0, 0, 21, 1);
		LevelCreator.createWall(22, 0, 26, 1);
		LevelCreator.createWall(0, 1, 1, 25);
		LevelCreator.createWall(5, 2, 1, 18);
		LevelCreator.createWall(2, 2, 3, 1);
		LevelCreator.createWall(1, 4, 3, 1);
		LevelCreator.createWall(2, 6, 3, 1);
		LevelCreator.createWall(1, 8, 3, 1);
		LevelCreator.createWall(2, 10, 3, 1);
		LevelCreator.createWall(1, 12, 3, 1);
		LevelCreator.createWall(2, 14, 3, 1);
		LevelCreator.createWall(2, 15, 1, 3);
		LevelCreator.createWall(3, 17, 1, 1);
		LevelCreator.createWall(1, 19, 4, 1);
		LevelCreator.createWall(1, 21, 16, 1);
		LevelCreator.createWall(2, 25, 46, 1);
		LevelCreator.createWall(2, 23, 1, 2);
		LevelCreator.createWall(4, 22, 1, 2);
		LevelCreator.createWall(6, 23, 1, 2);
		LevelCreator.createWall(10, 23, 1, 2);
		LevelCreator.createWall(14, 23, 1, 2);
		LevelCreator.createWall(8, 22, 1, 2);
		LevelCreator.createWall(12, 22, 1, 2);
		LevelCreator.createWall(16, 23, 26, 1);
		LevelCreator.createWall(16, 22, 1, 1);
		LevelCreator.createWall(18, 21, 28, 1);
		LevelCreator.createWall(41, 22, 1, 1);
		LevelCreator.createWall(43, 23, 1, 2);
		LevelCreator.createWall(45, 22, 1, 2);
		LevelCreator.createWall(47, 1, 1, 24);
		LevelCreator.createWall(6, 15, 13, 1);
		LevelCreator.createWall(20, 15, 7, 1);
		LevelCreator.createWall(28, 15, 16, 1);
		LevelCreator.createWall(7, 19, 16, 1);
		LevelCreator.createWall(13, 17, 4, 1);
		LevelCreator.createWall(18, 17, 5, 1);
		LevelCreator.createWall(7, 17, 1, 2);
		LevelCreator.createWall(11, 17, 1, 2);
		LevelCreator.createWall(9, 16, 1, 2);
		LevelCreator.createWall(13, 16, 1, 1);
		LevelCreator.createWall(18, 16, 1, 1);
		LevelCreator.createWall(18, 2, 1, 13);
		LevelCreator.createWall(22, 18, 1, 1);
		LevelCreator.createWall(24, 17, 1, 4);
		LevelCreator.createWall(26, 19, 21, 1);
		LevelCreator.createWall(30, 17, 16, 1);
		LevelCreator.createWall(28, 16, 1, 2);
		LevelCreator.createWall(26, 17, 1, 2);
		LevelCreator.createWall(7, 2, 11, 1);
		LevelCreator.createWall(7, 3, 1, 11);
		LevelCreator.createWall(8, 13, 9, 1);
		LevelCreator.createWall(16, 4, 1, 9);
		LevelCreator.createWall(9, 4, 7, 1);
		LevelCreator.createWall(9, 5, 1, 7);
		LevelCreator.createWall(10, 11, 5, 1);
		LevelCreator.createWall(14, 6, 1, 5);
		LevelCreator.createWall(11, 6, 3, 1);
		LevelCreator.createWall(11, 7, 1, 3);
		LevelCreator.createWall(12, 9, 1, 1);
		LevelCreator.createWall(20, 1, 1, 11);
		LevelCreator.createWall(19, 13, 2, 1);
		LevelCreator.createWall(20, 14, 1, 1);
		LevelCreator.createWall(22, 1, 1, 6);
		LevelCreator.createWall(22, 8, 1, 4); 
		LevelCreator.createWall(28, 1, 1, 13);
		LevelCreator.createWall(22, 13, 6, 1);
		LevelCreator.createWall(23, 7, 4, 1);
		LevelCreator.createWall(26, 8, 1, 4);
		LevelCreator.createWall(24, 9, 1, 4);
		LevelCreator.createWall(26, 1, 1, 3);
		LevelCreator.createWall(24, 2, 1, 4);
		LevelCreator.createWall(25, 5, 3, 1);
		LevelCreator.createWall(30, 2, 1, 13);
		LevelCreator.createWall(31, 2, 11, 1);
		LevelCreator.createWall(41, 3, 1, 11);
		LevelCreator.createWall(32, 4, 1, 10);
		LevelCreator.createWall(33, 13, 8, 1);
		LevelCreator.createWall(33, 4, 7, 1);
		LevelCreator.createWall(39, 5, 1, 7);
		LevelCreator.createWall(34, 11, 5, 1);
		LevelCreator.createWall(34, 6, 1, 5);
		LevelCreator.createWall(35, 6, 3, 1);
		LevelCreator.createWall(37, 7, 1, 3);
		LevelCreator.createWall(36, 9, 1, 1);
		LevelCreator.createWall(43, 2, 1, 13);
		LevelCreator.createWall(44, 2, 2, 1);
		LevelCreator.createWall(45, 4, 2, 1);
		LevelCreator.createWall(44, 6, 2, 1);
		LevelCreator.createWall(45, 8, 2, 1);
		LevelCreator.createWall(44, 10, 2, 1);
		LevelCreator.createWall(45, 12, 1, 5);
		LevelCreator.createWall(46, 12, 1, 1);
	}
	
	/**
	 * Level 3 - Raum 3
	 * Endgegner: Dunkelheit
	 */
	
	public static void setLevel9(){
		
		/*
		 *  Default-Position
		 */
		
		setPlayerDefaultPos(47, 13);
		
		/*
		 * Goal
		 */
		
		LevelCreator.createGoal(14, 25, 3, 2);
		
		/*
		 * Checkpoint
		 */
		
		Checkpoints.createCheckpoint(37*20, 12*20);
		
		/*
		 * Wände
		 */
		
		LevelCreator.createWall(0, 0, 48, 1);
		LevelCreator.createWall(0, 1, 1, 25);
		LevelCreator.createWall(2, 2, 1, 14);
		LevelCreator.createWall(3, 2, 15, 1);
		LevelCreator.createWall(3, 5, 6, 1);
		LevelCreator.createWall(4, 7, 5, 1);
		LevelCreator.createWall(3, 9, 6, 1);
		LevelCreator.createWall(8, 4, 1, 3);
		LevelCreator.createWall(6, 3, 1, 1);
		LevelCreator.createWall(4, 4, 1, 1);
		LevelCreator.createWall(10, 3, 1, 8);
		LevelCreator.createWall(4, 11, 12, 1);
		LevelCreator.createWall(4, 12, 1, 2);
		LevelCreator.createWall(17, 3, 1, 5);
		LevelCreator.createWall(11, 7, 5, 1);
		LevelCreator.createWall(15, 4, 1, 3);
		LevelCreator.createWall(12, 4, 3, 1);
		LevelCreator.createWall(12, 5, 1, 1);
		LevelCreator.createWall(12, 9, 6, 1);
		LevelCreator.createWall(17, 10, 1, 1);
		LevelCreator.createWall(17, 11, 19, 1);
		LevelCreator.createWall(18, 7, 5, 1);
		LevelCreator.createWall(19, 2, 1, 8);
		LevelCreator.createWall(21, 1, 1, 5);
		LevelCreator.createWall(23, 2, 1, 8);
		LevelCreator.createWall(21, 9, 1, 2);
		LevelCreator.createWall(25, 2, 1, 9);
		LevelCreator.createWall(27, 1, 1, 4);
		LevelCreator.createWall(26, 6, 12, 1);
		LevelCreator.createWall(27, 8, 1, 3);
		LevelCreator.createWall(29, 2, 1, 8);
		LevelCreator.createWall(30, 2, 6, 1);
		LevelCreator.createWall(32, 4, 5, 1);
		LevelCreator.createWall(37, 2, 1, 9);
		LevelCreator.createWall(31, 8, 1, 3);
		LevelCreator.createWall(33, 7, 1, 3);
		LevelCreator.createWall(35, 8, 1, 3);
		LevelCreator.createWall(37, 11, 5, 1);
		LevelCreator.createWall(38, 5, 4, 1);
		LevelCreator.createWall(37, 1, 1, 1);
		LevelCreator.createWall(39, 1, 1, 3);
		LevelCreator.createWall(41, 2, 1, 3);
		LevelCreator.createWall(43, 1, 1, 5);
		LevelCreator.createWall(44, 5, 2, 1);
		LevelCreator.createWall(47, 1, 1, 11);
		LevelCreator.createWall(45, 2, 1, 2);
		LevelCreator.createWall(46, 3, 1, 1);
		LevelCreator.createWall(39, 7, 8, 1);
		LevelCreator.createWall(39, 8, 1, 2);
		LevelCreator.createWall(41, 10, 1, 1);
		LevelCreator.createWall(41, 9, 5, 1);
		LevelCreator.createWall(43, 11, 4, 1);
		LevelCreator.createWall(1, 17, 4, 1);
		LevelCreator.createWall(3, 15, 16, 1);
		LevelCreator.createWall(6, 13, 1, 11);
		LevelCreator.createWall(2, 19, 1, 5);
		LevelCreator.createWall(4, 21, 1, 3);
		LevelCreator.createWall(3, 19, 3, 1);
		LevelCreator.createWall(3, 23, 1, 1);
		LevelCreator.createWall(1, 25, 14, 1);
		LevelCreator.createWall(8, 13, 17, 1);
		LevelCreator.createWall(8, 19, 9, 1);
		LevelCreator.createWall(18, 16, 1, 9);
		LevelCreator.createWall(8, 17, 1, 2);
		LevelCreator.createWall(10, 16, 1, 2);
		LevelCreator.createWall(12, 17, 1, 2);
		LevelCreator.createWall(16, 17, 1, 2);
		LevelCreator.createWall(14, 16, 1, 2);
		LevelCreator.createWall(14, 20, 1, 5);
		LevelCreator.createWall(10, 23, 7, 1);
		LevelCreator.createWall(8, 21, 1, 4);
		LevelCreator.createWall(9, 21, 4, 1);
		LevelCreator.createWall(16, 25, 32, 1);
		LevelCreator.createWall(20, 14, 1, 6);
		LevelCreator.createWall(22, 15, 1, 6);
		LevelCreator.createWall(16, 21, 7, 1);
		LevelCreator.createWall(24, 14, 1, 11);
		LevelCreator.createWall(20, 23, 4, 1);
		LevelCreator.createWall(26, 13, 11, 1);
		LevelCreator.createWall(25, 15, 4, 1);
		LevelCreator.createWall(28, 16, 1, 8);
		LevelCreator.createWall(26, 17, 1, 7);
		LevelCreator.createWall(27, 23, 1, 1);
		LevelCreator.createWall(30, 14, 1, 11);
		LevelCreator.createWall(36, 14, 1, 10);
		LevelCreator.createWall(31, 18, 4, 1);
		LevelCreator.createWall(32, 14, 1, 3);
		LevelCreator.createWall(34, 15, 1, 3);
		LevelCreator.createWall(32, 20, 4, 1);
		LevelCreator.createWall(32, 21, 1, 3);
		LevelCreator.createWall(34, 22, 1, 3);
		LevelCreator.createWall(38, 13, 1, 11);
		LevelCreator.createWall(39, 13, 8, 1);
		LevelCreator.createWall(47, 13, 1, 12);
		LevelCreator.createWall(39, 23, 7, 1);
		LevelCreator.createWall(40, 15, 6, 1);
		LevelCreator.createWall(40, 16, 1, 6);
		LevelCreator.createWall(45, 16, 1, 4);
		LevelCreator.createWall(41, 21, 6, 1);
		LevelCreator.createWall(41, 17, 3, 1);
		LevelCreator.createWall(42, 19, 3, 1);
	}
}
