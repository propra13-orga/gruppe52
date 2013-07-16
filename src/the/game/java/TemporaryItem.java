package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class TemporaryItem {
	
	public static List<TemporaryItem> tempItemList = new ArrayList<TemporaryItem>();
	private long timeStamp;
	private long timeLimit;
	private long timeLeft;
	private int playerID;
	private int itemID;
	private boolean isTimed;
	private Image img;
	
	public static boolean flagEnemyFreezed = false;
	private boolean isEnemyFreezed = false;
	private long DMGTYPEactionTimeStamp = 0;
	
	private static Image tempIt0 = DisplayManager.getImage("goodies/shield.png");
	private static Image tempIt1 = DisplayManager.getImage("mana/freeze.png");
	//private static Image tempIt2 = ManaManager.getCurrentImg();
	
	/**
	 * Konstruktor der Klasse TemporaryItem
	 * @param iID ID des Items
	 * @param timed true: zeitlich begrenzt, false: zeitlich unbegrenzt
	 * @param limit Zeitlimit
	 * @param pID ID des Players
	 */
	private TemporaryItem(int iID, boolean timed, long limit, int pID) {
		itemID = iID;
		isTimed = timed;
		timeLimit = limit;
		timeStamp = System.currentTimeMillis();
		playerID = pID;
		doOnActivation();
	}
	
	/**
	 * Macht Player für Gegner Unsichtbar
	 * @param playerID ID des Players
	 */
	public static void activateInvincibility(int playerID) {
		tempItemList.add(new TemporaryItem(0, true, 8000, playerID));
	}
	/**
	 * Gefriert Enemys
	 * @param playerID ID des Players
	 */
	public static void activateEnemyFreezing(int playerID) {
		tempItemList.add(new TemporaryItem(1, true, 8000, playerID));
	}
	/**
	 * Aktiviert einen Schadenstyp
	 * @param playerID ID des Players
	 */
	public static void activateDmgType(int playerID) {
		tempItemList.add(new TemporaryItem(2, true, 100000, playerID));
	}
	/**
	 * Updated, ob Items noch existent sind
	 */
	public static void updater() {
		resetVars();
		for(int a=0; a<tempItemList.size(); a++) {
			tempItemList.get(a).initiateUpdate(a);
		}
		displayTempItems();
	}
	/**
	 * 'Entfreezed' Enemys
	 */
	private static void resetVars() {
		flagEnemyFreezed = false;
	}
	/**
	 * Leitet updaten ein
	 * @param index
	 */
	private void initiateUpdate(int index) {
		if(isTimed) {
			if(System.currentTimeMillis() - timeStamp < timeLimit) {
				timeLeft = getTimeLeft();	// verbleibende Zeit aktualisieren (nur für anzeigezwecke)
				doOnUpdate();
				updateVars();
				DisplayLine.setDisplay();
			} else {
				doOnDeactivation(index);
			}
		}
	}
	/**
	 * Setzt ein Flag, dass Enemys gefreezed ist
	 */
	private void updateVars() {
		if(isEnemyFreezed)
			flagEnemyFreezed = true;
	}
	/**
	 * Gibt die Zeit zurück, die die Feinde noch gefroren sind
	 * @return restliche Zeit
	 */
	private long getTimeLeft() {
		return timeLimit/1000-((System.currentTimeMillis() - timeStamp-((System.currentTimeMillis() - timeStamp)%1000))/1000);
	}
	/**
	 * Stellt zeitlich begrenzte Items dar
	 */
	private static void displayTempItems() {
		DisplayManager.removeChangeableStrings("tempItemTime");
		DisplayManager.removeChangeableImages("tempItemIcon");
		
		int b = 0;
		for(int a=0; a<tempItemList.size() && a<6; a++) {
			b += 2;
			DisplayManager.displayImage(getImg(a), Runner.borderRi-(b+1)*20, 0, "tempItemIcon", true);
			DisplayManager.displayString(String.valueOf(getTimeLeft(a)), Runner.borderRi-(b)*20, 15, "tempItemTime");
		}
	}
	
	
	/**     HIER ITEMSPEZIFISCH     */
	/**
	 * Itemspezifisch: Was passiert nach aktivierung?
	 */
	private void doOnActivation() {
		switch(itemID) {
		case 0:		// Shield
			Player.playerList.get(playerID).invincible = true;
			img = tempIt0;
			timeLeft = getTimeLeft();
			break;
		case 1:
			isEnemyFreezed = true;
			img = tempIt1;
			timeLeft = getTimeLeft();
			break;
		case 2:		// DamageType
			img = ManaManager.getCurrentImg();
			int mana = Player.getPlayer(playerID).mana.getMana();
			timeLimit = ((mana - (mana % 10)) / 10) * 1000;	// Zeit ausrechnen
			break;
		}
	}
	/**
	 * Was passiert wenn geupdated wird
	 */
	private void doOnUpdate() {
		switch(itemID) {
		case 0:		// Shield
			return;
		case 1:		// Freeze
			return;
		case 2:		// DamageType
			if(System.currentTimeMillis() - DMGTYPEactionTimeStamp >= 1000) {
				if(Player.getPlayer(playerID).mana.getMana()>=10) {
					Player.getPlayer(playerID).mana.setMana(-10);
					DMGTYPEactionTimeStamp = System.currentTimeMillis();
				} else {
					timeLimit = 0;	// sorgt dafür, dass diese Instanz terminiert wird
				}
			}
			break;
		}
	}
	/**
	 * Was passiert, wenn Item deaktiviert ist
	 * @param index ID des Items
	 */
	private void doOnDeactivation(int index) {
		switch(itemID) {
		case 0:		// Shield
			Player.playerList.get(playerID).invincible = false;
			tempItemList.remove(index);
			break;
		case 1:
			isEnemyFreezed = false;
			tempItemList.remove(index);
			break;
		case 2:		// DamageType
			Player.getPlayer(playerID).damageType = 0;
			tempItemList.remove(index);
			break;
		}
	}
	/**     HIER ITEMSPEZIFISCH ENDE     */
	
	
	/**     GETS     */
	/**
	 * Gibt Image zurück
	 * @param index ID des Items
	 * @return Image
	 */
	public static Image getImg(int index) {
		return tempItemList.get(index).img;
	}
	/**
	 * Gibt restliche Zeit zurück
	 * @param index ID des Items
	 * @return restliche Zeit
	 */
	public static long getTimeLeft(int index) {
		return tempItemList.get(index).timeLeft;
	}
	/**     GETS ENDE     */
	
}
