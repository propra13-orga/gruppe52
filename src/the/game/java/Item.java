package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class Item {
	
	public static List<Item> itemList = new ArrayList<Item>();
	public boolean valid;
	private long timer;
	private long limit;
	public int playerID;
	public int itemID;
	public boolean isTimeControlled;
	
	private Item(int itemLifeTime, int pID, int iID) {	// Konstruktor: benötigt: Lebensdauer des Items; ID des Players, der es eingesammelt hat; ID des Items
		valid = true;
		isTimeControlled = true;
			timer = System.currentTimeMillis();
			limit = itemLifeTime * 1000;
		playerID = pID;
		itemID = iID;
	}
	private Item(int pID, int iID) {	// Konstruktor: benötigt: Lebensdauer des Items; ID des Players, der es eingesammelt hat; ID des Items
		valid = true;
		isTimeControlled = false;
			timer = 0;
			limit = 0;
		playerID = pID;
		itemID = iID;
	}
	
	public static void activateInvincibility(int pID) {
		int itemLifeTime = 8;
		int iID = 11;
		itemList.add(new Item(itemLifeTime, pID, iID));		// Objekt wird in Liste geschrieben
	}
	
	public static void setTime() {		// aktualisiert den Timer und überprüft die Lebenszeit aller Items und aktualisiert die Anzeige oben
		int posx = LevelCreator.getItemMapDimensions(0) - 5;	// ausgangsposition ganz rechts für die Anzeige
		int counter = 0;
		for(int a=0; a<itemList.size(); a++) {		// geht Item für Item durch
			if(itemList.get(a).valid) {				// nur überprüfen, wenn noch gültig
				if(itemList.get(a).isTimeControlled) {
					if(System.currentTimeMillis() - itemList.get(a).timer>=itemList.get(a).limit) {	// wenn Lebensdauer vorrüber, dann ungültig
						itemList.get(a).valid = false;
						itemList.get(a).setPlayerStatus(a);
						LevelCreator.itemMap[posx-(3*counter)][0] = 0;
						LevelCreator.itemMap[posx+1-(3*counter)][0] = 0;
					} else {
						LevelCreator.itemMap[posx-(3*counter)][0] = itemList.get(a).itemID;	// eintragen der ItemID in die itemMap für das HeadMenu
						// speichert die verbleibenden sekunden + 100 in itemList, wird von setter ausgelesen und richtig interpretiert
						LevelCreator.itemMap[posx+1-(3*counter)][0] = (int) (itemList.get(a).limit/1000-((System.currentTimeMillis() - itemList.get(a).timer-((System.currentTimeMillis() - itemList.get(a).timer)%1000))/1000)+100);
						counter++;
					}
				}
			}
		}
	}
	
	private boolean setPlayerStatus(int itemIndex) {	// Setzt Statusvariablen in Player zurück, falls Timer abgelaufen
		boolean status=false;
		switch(itemID) {
		case 11:	// Shield
			Player.playerList.get(playerID).invincible = false;
			break;
		}
		return status;
	}
}

