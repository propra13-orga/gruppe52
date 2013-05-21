package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class Item {
	
	public static List<Item> itemList = new ArrayList<Item>();
	public boolean valid;
	private int timer;
	private int limit;
	public int playerID;
	public int itemID;
	
	private Item(int itemLifeTime, int pID, int iID) {	// Konstruktor: benötigt: Lebensdauer des Items; ID des Players, der es eingesammelt hat; ID des Items
		valid=true;
		timer=0;
		limit = itemLifeTime * 1000;
		playerID = pID;
		itemID = iID;
	}
	
	public static void activateInvincibility(int pID) {
		int itemLifeTime = 8;
		int iID = 11;
		itemList.add(new Item(itemLifeTime, pID, iID));		// Objekt wird in Liste geschrieben
	}
	
	public static void setTime(int interval) {		// aktualisiert den Timer und überprüft die Lebenszeit aller Items und aktualisiert die Anzeige oben
		int posx = LevelCreator.getItemMapDimensions(0) - 5;	// ausgangsposition ganz rechts für die Anzeige
		int counter = 0;
		for(int a=0; a<itemList.size(); a++) {		// geht Item für Item durch
			if(itemList.get(a).valid) {				// nur überprüfen, wenn noch gültig
				itemList.get(a).timer += interval;	// timer aktualisieren
				//System.out.println(itemList.get(a).timer);
				if(itemList.get(a).timer>=itemList.get(a).limit) {	// wenn Lebensdauer vorrüber, dann ungültig
					itemList.get(a).valid = false;
					itemList.get(a).setPlayerStatus(a);
					LevelCreator.itemMap[posx-(3*counter)][0] = 0;
					LevelCreator.itemMap[posx+1-(3*counter)][0] = 0;
				} else {
					LevelCreator.itemMap[posx-(3*counter)][0] = itemList.get(a).itemID;	// eintragen der ItemID in die itemMap für das HeadMenu
					// speichert die verbleibenden sekunden + 100 in itemList, wird von setter ausgelesen und richtig interpretiert
					LevelCreator.itemMap[posx+1-(3*counter)][0] = itemList.get(a).limit/1000-((itemList.get(a).timer-(itemList.get(a).timer%1000))/1000)+100;
					counter++;
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

