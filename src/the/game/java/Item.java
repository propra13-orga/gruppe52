package the.game.java;


import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle Objekte, die einem Item entsprechen (zB Rüstung)
 */
public class Item {
	
	public static List<Item> itemList = new ArrayList<Item>();
	private int itemID;
	private int playerID;
	
	/**
	 * Konstruktor der Klasse Item
	 * @param pID playerID
	 * @param iID itemID
	 */
	private Item(int pID, int iID) {	// Konstruktor
		itemID = iID;
		playerID = pID;
		setConsequence();
	}
	
	/**
	 * Fügt ein neues Objekt der itemList hinzu
	 * @param itemID ID des Items
	 * @param playerID ID des Players
	 */
	public static void addItem(int itemID, int playerID) {
		if(isExistent(itemID))
			return;
		else
			itemList.add(new Item(playerID, itemID));
	}
	
	/**
	 * Prüft, ob ein Item bereits in itemList existiert
	 * @param iID ID des Items
	 * @return true = es existiert bereits, false = es existiert noch nicht.
	 */
	private static boolean isExistent(int iID) {
		for(int a=0; a<itemList.size(); a++) {
			if(itemList.get(a).itemID==iID)
				return true;
		}
		return false;
	}
	
	/**
	 * Legt den Kollisionseffekt fest
	 */
	private void setConsequence() {
		switch(itemID) {
		case 0:	// Rüstung (Vest)
			Player.playerList.get(playerID).setResistance(0.5);
			break;
		}
	}
	
	/**
	 * Macht den Kollisionseffekt rückgängig
	 */
	private void resetConsequence() {
		switch(itemID) {
		case 0:	// Rüstung (Vest)
			Player.playerList.get(playerID).setResistance(1);
			break;
		}
	}
	
	/**
	 * Stellt das dem Item entsprechende Bild dar.
	 * @param itemID
	 * @return
	 */
	public static Image getImg(int itemID) {
		switch(itemID) {
		case 0:	// Rüstung (Vest)
			return DisplayManager.getImage("items/bulletproofVest.png");
		}
		return null;
	}
	
	/**
	 * Löscht alle existierenden Items
	 */
	public static void resetItems() {
		for(int a=0; a<itemList.size(); a++) {
			itemList.get(a).resetConsequence();
		}
		itemList.clear();
	}
	
	/**
	 * Gibt die ItemID zurück
	 * @return itemID
	 */
	public int getItemID() {
		return itemID;
	}
}