package the.game.java;

/**
 * Verwaltet alles, was etwas mit Einstellungen zutun hat
 */
public class Settings {
	private static String controlsType = "mouse";
	private static int playerCount = 1;
	private static boolean isMultiplayer = false;
	private static boolean isCensored = false;
	
	/**
	 * Gibt zurück, ob mit einem Controller gespielt werden soll
	 * @return true wenn Controler, false wenn nicht Controler
	 */
	public static boolean isControledByGamePad() {
		return controlsType.equals("gamepad");
	}
	/**
	 * Gibt zurück, ob mit einer Maus gespielt werden soll
	 * @return true wenn Maus, false wenn nicht Maus
	 */
	public static boolean isControledByMouse() {
		return controlsType.equals("mouse");
	}
	/**
	 * Die Steuerung wird auf das Verwenden eines Controllers gesetzt
	 */
	public static void setControlsToGamePad() {
		controlsType = "gamepad";
	}
	/**
	 * Die Steuerung wird auf das Verwenden einer Maus gesetzt
	 */
	public static void setControlsToMouse() {
		controlsType = "mouse";
	}
	/**
	 * Erhöht PlayerCount um eins
	 */
	public static void setPlayerCountPlusOne() {
		playerCount++;
	}
	/**
	 * Gibt den Playercount zurück
	 * @return playerCount
	 */
	public static int getPlayerCount() {
		return playerCount;
	}
	/**
	 * Gibt an, ob im MultiplayerModus gespielt werden soll
	 * @return true, wenn Multiplayer, false wenn nicht
	 */
	public static boolean isMultiplayer() {
		return isMultiplayer;
	}
	/**
	 * Setzt Flag für Multiplayer = true
	 */
	public static void setGameToMultiplayer() {
		isMultiplayer = true;
	}
	/**
	 * Setzt Flag für Multiplayer = false
	 */
	public static void setGameToSingleplayer() {
		isMultiplayer = false;
	}
	/**
	 * Gibt an ob zensiert werden soll (zB Blut/PaintBall)
	 * @param arg
	 */
	public static void setCensorship(boolean arg) {
		isCensored = arg;
	}
	/**
	 * Gibt zurück, ob zensiert wird
	 * @return isCensored
	 */
	public static boolean isCensored() {
		return isCensored;
	}
}
