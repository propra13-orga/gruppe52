package the.game.java;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  In dieser Klasse kann der Manastand eines Spielers abgefragt und ver�ndert werden.
 *  Der kleinst-m�gliche Manastand betr�gt 0.
 *
 */

public class Mana {

	public int zauberkraft;  					//Zauberkraft des Spielers
	
	private Mana() {
		zauberkraft = 0;
	}
	
	public static List<Mana> manaList = new ArrayList<Mana>();
	
	public static void createMana() {			// f�r jeden Spieler einen
		manaList.add(new Mana());
	}
	
	public int getManaStatusQuo(){						// Gibt den aktuellen Manastand zur�ck
		return zauberkraft;
	}
	
	public void setManaStatusQuo(int wert){				// Liest den Wert ein um den der Manastand ver�ndert werden soll und rechnet dementsprechend
		if(zauberkraft+wert>0){
			zauberkraft=zauberkraft+wert;
		}else if(zauberkraft+wert>100){
			zauberkraft=100;							// max 100 Manapunkte m�glich
		}else{
			zauberkraft=0;								// nur 0 oder positive Manast�nde m�glich
		}
	}
	
}
