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
	
	private static Image tempIt0 = DisplayManager.getImage("goodies/shield.png");
	private static Image tempIt1 = DisplayManager.getImage("mana/freeze.png");
	
	
	private TemporaryItem(int iID, boolean timed, long limit, int pID) {
		itemID = iID;
		isTimed = timed;
		timeLimit = limit;
		timeStamp = System.currentTimeMillis();
		playerID = pID;
		doOnActivation();
	}
	
	public static void activateInvincibility(int playerID) {
		tempItemList.add(new TemporaryItem(0, true, 8000, playerID));
	}
	public static void activateEnemyFreezing(int playerID) {
		tempItemList.add(new TemporaryItem(1, true, 8000, playerID));
	}
	
	public static void updater() {
		resetVars();
		for(int a=0; a<tempItemList.size(); a++) {
			tempItemList.get(a).initiateUpdate(a);
		}
		displayTempItems();
	}
	private static void resetVars() {
		flagEnemyFreezed = false;
	}
	
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
	private void updateVars() {
		if(isEnemyFreezed)
			flagEnemyFreezed = true;
	}
	
	private long getTimeLeft() {
		return timeLimit/1000-((System.currentTimeMillis() - timeStamp-((System.currentTimeMillis() - timeStamp)%1000))/1000);
	}
	
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
		}
	}
	
	private void doOnUpdate() {
		switch(itemID) {
		case 0:		// Shield
			return;
		case 1:
			return;
		}
	}
	
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
		}
	}
	/**     HIER ITEMSPEZIFISCH ENDE     */
	
	
	/**     GETS     */
	public static Image getImg(int index) {
		return tempItemList.get(index).img;
	}
	public static long getTimeLeft(int index) {
		return tempItemList.get(index).timeLeft;
	}
	/**     GETS ENDE     */
	
}
