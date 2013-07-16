package the.game.java;

import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Organisiert alle Veränderungen, die Level betreffen
 * zB Levelwechsel usw.
 *
 */
public class LevelCaller {
	
	private static int level=0;		// Laufvariable für Level
	private static int defaultX=0;	// Startposition (x) für Player
	private static int defaultY=40;	// Startposition (y) für Player
	public static boolean resetting = false;			// Sorgt dafür, dass sie ausgelesenen Daten aus einem Savegame gefiltert/ungefiltert übernommen werden (z.B. für Leben etc.)
	public static boolean nextLevelAlready = false;		// Verhindert doppeltes starten von setNextLevel() (wird von Player gesteuert)
	public static Map<Integer,Point> spawnMap;
	
	/**
	 * Konstruktor der Klasse LevelCaller
	 * sobald ein Objekt der Klasse LevelCaller erstellt wird, wird auch ein Objekt der Klasse LevelCreator erstellt
	 * die beiden Klassen sind von einander abhängig und haben allein keinen Sinn
	 * sie lieben sich und werden viele kleine Methoden haben
	 */
	public LevelCaller(){
		spawnMap = new HashMap<Integer,Point>();	// Für Multiplayer
		new LevelCreator();
		settingData();
		Runner.codeRunning = true; 
	}
	
	/**
	 * Startet ein dem Pfad entsprechendes Level
	 * @param path Pfad zum Level
	 */
	public static void setLevel(String path) {
		settingData();
		level = 0;						// level nimmt den Wert vom Parameter an -> dient als Laufvariable
		
		parseLevel(path, true);
		
		Savegame.savegame();
		DisplayManager.displayMap();
		
	}
	
	/**
	 * Startet der Levelnr entsprechendes Level
	 * @param levelnr Nr des Levels, welches gestartet werden soll
	 */
	public static void setLevel(int levelnr) {
		settingData();
		level = levelnr;						// level nimmt den Wert vom Parameter an -> dient als Laufvariable
		if(Settings.isMultiplayer()) {
			switch(levelnr) {						// Levelnr wird überprüft:
			case 1:
				parseLevel("1");
				//setLevel1();							// Level 1 wird ausgeführt
				break;
			case 2:
				parseLevel("2");
				//setLevel2();							// Level 2 wird ausgeführt
				break;
			case 3:
				parseLevel("3");
				//setLevel3();							// Level 3 wird ausgeführt
				break;
			default:
				NetRanking.run();
			}
		} else {
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
				parseLevel("2.0");
				break;
			case 5:
				parseLevel("2.1");
				//setLevel4();
				break;
			case 6:
				parseLevel("2.2");
				//setLevel5();
				break;
			case 7:
				parseLevel("2.3");
				//setLevel6();
				break;
			case 8:
				parseLevel("3.1");
				//setLevel7();
				break;
			case 9:
				parseLevel("3.2");
				//setLevel8();
				break;
			case 10:
				parseLevel("3.3");
				//setLevel9();
				break;
			default:
				setFinalGoal();
			}
		}
		if(Runner.codeRunning) {
			Savegame.savegame();
			DisplayManager.displayMap();
		}
	}
	
	/**
	 * Lädt einen gespeicherten Spielstand
	 */
	private static void loadSavegame() {
		try {
			SaxParser.parse("savegames/save.xml");
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Lädt ein erstelltes Level
	 * @param levelTag Tag des Levels
	 */
	private static void parseLevel(String levelTag) {
		try {
			if(Settings.isMultiplayer()) {
				SaxParser.parse("maps/multiplayer/level" + levelTag + ".xml");
			} else {
				SaxParser.parse("maps/level" + levelTag + ".xml");
			}
			
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Lädt ein erstelltes Level
	 * @param path Pfad des Levels
	 * @param isAbsolut
	 */
	private static void parseLevel(String path, boolean isAbsolut) {
		if(isAbsolut==false)
			return;
		try {
			SaxParser.parse(path, isAbsolut);
		} catch (IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Löscht die Map und alle maprelevanten Listen
	 */
	private static void settingData() {
		LevelCreator.resetItemMap();			// Array leeren
		Enemy.clearAllLists();					// Monsterbestand wird auf 0 gesetzt
		Weapon.loadWeapons();					// Alle Waffen laden
		NPC.removeAllNPCs();
		Goodies.removeAllGoodies();
		Traps.removeAllTraps();
		Checkpoints.removeAllCheckpoints();
		Door.removeAllDoors();
		spawnMap.clear();
	}
	
	/**
	 * Startet das nächste Level
	 */
	public static void setNextLevel() {
		if(nextLevelAlready)
			return;
		
		if(Settings.isMultiplayer() && NetHandler.nextLevel == true) {		// Wenn Multiplayer UND nextLevel == true (was der Fall ist wenn dieser Client Kartenwechsel veranlasst: Verhindert Doppelnachrichten)
			NetHandler.externalOutputQ.add(new NetMessage(NetHandler.netID, "lvl next " + Player.getPlayer(0).score.getScore()));	// Status für Netzwerk + Score
			NetRanking.scoreMap.put(NetHandler.netID, Player.getPlayer(0).score.getScore());
		}
		
		nextLevelAlready = true;
		level++;								// Laufvariable level wird um 1 erhöht
		setLevel(level);						// Level nr level wird gestartet

		if(Settings.isMultiplayer()) {
			setMPPlayerPositionToDefault();
			NetHandler.updateRequired = true;
		}
	}
	
	/** 
	 * Wird gestartet, wenn das Ziel des Spiels erreicht wurde
	 */
	public static void setFinalGoal() {			// wird gestartet wenn das Ziel des Spiels erreicht wurde
		shutdownLevel();						// das ganze Spiel wird zurückgesetzt und beendet
		WinWindow.main(null);					// das "GewonnenFenster" öffnet sich
	}
	
	/**
	 * Resettet das Level
	 */
	public static void resetLevel() {
		settingData();
		SpriteArmed.resetWeaponIULists();
		Item.resetItems();
		resetting = true;
		loadSavegame();
		resetting = false;
		DisplayManager.displayMap();
		Player.resetPlayerImages();
		//Player.resetAllPlayerPositions();		// Position der Spieeler wird neu gesetzt.
	}
	
	/**
	 * Beendet das Level
	 */
	public static void shutdownLevel() {
		Runner.codeRunning = false;				// Flag, die verhindert, dass Codereste ausgeführt werden, wenn bereits keine Objekte mehr existieren
		settingData();
		Player.playerList.clear();				// Spieler werden gelöscht
		Weapon.weaponList.clear();				// Alle Waffen löschen
		Item.itemList.clear();
		Setter.timer.stop();					// Timer ausschalten
		Runner.shutRunnerDown();				// beendet das Speilfenster
	}
	
	/**
	 * Gibt Levelnummer aus
	 * @return levelnr
	 */
	public static int getLevelNumber() {		// gibt Levelnr aus
		return level;
	}
	/**
	 * Gibt x-Startposition des Spielers aus
	 * @return defaultX
	 */
	public static int getPlayerDefaultPosX() {	// gibt Startposition (x) des Players aus
		return defaultX;
	}
	/**
	 * Gibt y-Startposition des Spielers aus
	 * @return defaultY
	 */
	public static int getPlayerDefaultPosY() {	// gibt Startposition (y) des Players aus
		return defaultY;
	}
	
	/**
	 * 
	 */
	public static void setMPPlayerPositionToDefault() {
		if(Settings.isMultiplayer()) {
			for(int a=0; a<Player.playerList.size(); a++) {
				if(Player.getPlayer(a).isRemote()==false) {
					int teamID = Player.getPlayer(a).getTeamID();
					if(teamID<0)
						continue;
					System.out.println(teamID);
					if(spawnMap.containsKey(teamID))
						Player.playerList.get(a).setPosition(spawnMap.get(teamID).x, spawnMap.get(teamID).y);
					System.out.println("x: " + Player.playerList.get(a).getX() + "  y: " + Player.playerList.get(a).getY());
				}
	    	}
		}
	}
	
	/**
	 * Legt die Startposition des Spielers fest
	 * @param defX x-Startposition des Spielers
	 * @param defY y-Startposition des Spielers
	 */
	public static void setPlayerDefaultPos(int defX, int defY) {	// ändert die Startposition des Players
		defaultX = defX*20;
		defaultY = defY*20;
		if(Settings.isMultiplayer()) {
			setMPPlayerPositionToDefault();
		} else {
			for(int a=0; a<Player.playerList.size(); a++) {
				Player.playerList.get(a).setPosition(defaultX, defaultY);
	    	}
		}
	}
	
	public static void setPlayerDefaultPos(int defX, int defY, int teamID) {
		if(spawnMap.containsKey(teamID)==false) {
			spawnMap.put(teamID, new Point(defX*20, defY*20));
		} else {
			spawnMap.get(teamID).setLocation(defX*20, defY*20);
		}
		
		setPlayerDefaultPos(defX, defY);
	}
}