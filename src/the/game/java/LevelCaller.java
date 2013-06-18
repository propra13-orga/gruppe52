package the.game.java;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class LevelCaller {
	
	private static int level=0;		// Laufvariable für Level
	private static int defaultX;	// Startposition (x) für Player
	private static int defaultY;	// Startposition (y) für Player
	public static boolean resetting = false;			// Sorgt dafür, dass sie ausgelesenen Daten aus einem Savegame gefiltert/ungefiltert übernommen werden (z.B. für Leben etc.)
	public static boolean nextLevelAlready = false;		// Verhindert doppeltes starten von setNextLevel() (wird von Player gesteuert)
	
	public LevelCaller(){
		// sobald ein Objekt der Klasse LevelCaller erstellt wird, wird auch ein Objekt der Klasse LevelCreator erstellt
		// die beiden Klassen sind von einander abhängig und haben allein keinen Sinn
		// sie lieben sich und werden viele kleine Methoden haben
		new LevelCreator();
		settingData();
		Runner.codeRunning = true; 
	}
	
	public static void setLevel(int levelnr) {
		settingData();
		level = levelnr;						// level nimmt den Wert vom Parameter an -> dient als Laufvariable
		switch(levelnr) {						// Levelnr wird überprüft:
		case 1:
			parseLevel("1.1");
			//setLevel1();							// Level 1 wird ausgeführt
			break;
		case 2:
			parseLevel("1.2");
			//setLevel2();							// Level 2 wird ausgeführt
			break;
		case 3:
			parseLevel("1.3");
			//setLevel3();							// Level 3 wird ausgeführt
			break;
		case 4:
			parseLevel("2.1");
			//setLevel4();
			break;
		case 5:
			parseLevel("2.2");
			//setLevel5();
			break;
		case 6:
			parseLevel("2.3");
			//setLevel6();
			break;
		case 7:
			parseLevel("3.1");
			//setLevel7();
			break;
		case 8:
			parseLevel("3.2");
			//setLevel8();
			break;
		case 9:
			parseLevel("3.3");
			//setLevel9();
			break;
		}
		Savegame.savegame();
		DisplayManager.displayMap();
	}
	
	private static void loadSavegame() {
		try {
			SaxParser.parse("savegames/save.xml");
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void parseLevel(String levelTag) {
		try {
			SaxParser.parse("maps/level" + levelTag + ".xml");
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void settingData() {
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.monsterList.clear();				// Monsterbestand wird auf 0 gesetzt
		Tracker.trackerList.clear();			// Trackerbestand wird auf 0 gesetzt
		Weapon.loadWeapons();					// Alle Waffen laden
		NPC.removeAllNPCs();
		Goodies.removeAllGoodies();
		Traps.removeAllTraps();
		Checkpoints.removeAllCheckpoints();
	}
	
	public static void setNextLevel() {
		if(nextLevelAlready)
			return;
		nextLevelAlready = true;
		settingData();
		level++;								// Laufvariable level wird um 1 erhöht
		setLevel(level);						// Level nr level wird gestartet
	}
	

	public static void setFinalGoal() {			// wird gestartet wenn das Ziel des Spiels erreicht wurde
		shutdownLevel();						// das ganze Spiel wird zurückgesetzt und beendet
		WinWindow.main(null);					// das "GewonnenFenster" öffnet sich
	}
	
	public static void resetLevel() {
		settingData();
		WeaponManager.resetWeaponInUseLists();
		//setLevel(level);						// Level nr level wird gestartet (Das gleiche Level)
		resetting = true;
		loadSavegame();
		resetting = false;
		DisplayManager.displayMap();
		Player.resetPlayerImages();
		//Player.resetAllPlayerPositions();		// Position der Spieeler wird neu gesetzt.
	}
	
	public static void shutdownLevel() {
		Runner.codeRunning = false;				// Flag, die verhindert, dass Codereste ausgeführt werden, wenn bereits keine Objekte mehr existieren
		settingData();
		Player.playerList.clear();				// Position der Spieler wird neu gesetzt
		Weapon.weaponList.clear();				// Alle Waffen löschen
		WeaponManager.weaponManagerList.clear();// Alle eingesammelten Waffen werden gelöscht
		Item.itemList.clear();
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
	
	public static void setPlayerDefaultPos(int defX, int defY) {	// ändert die Startposition des Players
		defaultX = defX*20;
		defaultY = defY*20;
		Player.playerList.get(0).setDefaultStartPos(defaultX, defaultY);
	}
}