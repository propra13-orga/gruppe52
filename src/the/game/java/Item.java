package the.game.java;


import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Item {
	
	public static List<Item> itemList = new ArrayList<Item>();
	private int itemID;
	private int playerID;
		
	private Item(int pID, int iID) {	// Konstruktor
		itemID = iID;
		playerID = pID;
		setConsequence();
	}
	
	public static void addItem(int itemID, int playerID) {
		if(isExistent(itemID))
			return;
		else
			itemList.add(new Item(playerID, itemID));
	}
	
	private static boolean isExistent(int iID) {
		for(int a=0; a<itemList.size(); a++) {
			if(itemList.get(a).itemID==iID)
				return true;
		}
		return false;
	}
	
	private void setConsequence() {
		switch(itemID) {
		case 0:	// Rüstung
			Player.playerList.get(playerID).setResistance(0.5);
			break;
		}
	}
	
	public static Image getImg(int itemID) {
		switch(itemID) {
		case 0:	// Rüstung
			return DisplayManager.getImage("items/bulletproofVest.png");
		}
		return null;
	}
	
}