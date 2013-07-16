package the.game.java;


/**
 * 
 *  In dieser Klasse kann der Manastand eines Spielers abgefragt und verändert werden.
 *  Der kleinst-mögliche Manastand beträgt 0. Der Maximale Manastand beträgt 100. 
 *  Wenn gesammeltes Mana eingesetzt wird kann folgendes passieren:
 *	Alle Monster werden für 9 Sekunden eingefroren
 */
public class Mana {

	public int zauberkraft;  									// Zauberkraft des Spielers
	
	int x;
	int y;
	
	/**
	 * Konstruktor der Klasse Mana
	 */
	public Mana(){
		zauberkraft = 100;//20;
	}
	
	/**
	 * Gibt den aktuellen Manastand zurück
	 */
	public int getMana(){						
		return zauberkraft;
	}
	/**
	 * Erhöht den Manastand um den als Parameter angegebenen Wert
	 * @param wert Der Wert, um den erhöht werden soll
	 */
	public void setMana(int wert){				// Liest den Wert ein um den der Manastand verändert werden soll und rechnet dementsprechend
		if(zauberkraft+wert>0 && zauberkraft+wert<=100){
			zauberkraft=zauberkraft+wert;
		}else if(zauberkraft+wert>100){
			zauberkraft=100;							// max 100 Manapunkte möglich
		}else{
			zauberkraft=0;								// nur 0 oder positive Manastände möglich
		}
	}
	/**
	 * Setzt den Manastand auf den angegebenen Wert
	 * @param manaCount Wert, den der Manabestand erhalten soll
	 */
	public void setAbsoluteMana(int manaCount){				// Liest den Wert ein um den der Manastand verändert werden soll und rechnet dementsprechend
		zauberkraft = manaCount;
	}
	
}
