package the.game.java;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  In dieser Klasse kann der Manastand eines Spielers abgefragt und verändert werden.
 *  Der kleinst-mögliche Manastand beträgt 0.
 *
 */

public class Mana {

	public int zauberkraft;  					//Zauberkraft des Spielers
	
	private Mana() {
		zauberkraft = 0;
	}
	
	public static List<Mana> manaList = new ArrayList<Mana>();
	
	public static void createMana() {			// für jeden Spieler einen
		manaList.add(new Mana());
	}
	
	public int getManaStatusQuo(){						// Gibt den aktuellen Manastand zurück
		return zauberkraft;
	}
	
	public void setManaStatusQuo(int wert){				// Liest den Wert ein um den der Manastand verändert werden soll und rechnet dementsprechend
		if(zauberkraft+wert>0){
			zauberkraft=zauberkraft+wert;
		}else if(zauberkraft+wert>100){
			zauberkraft=100;							// max 100 Manapunkte möglich
		}else{
			zauberkraft=0;								// nur 0 oder positive Manastände möglich
		}
	}
	
}
