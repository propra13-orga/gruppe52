package the.game.java;


/**
 * 
 *  In dieser Klasse kann der Manastand eines Spielers abgefragt und ver�ndert werden.
 *  Der kleinst-m�gliche Manastand betr�gt 0. Der Maximale Manastand betr�gt 100. 
 *  Wenn gesammeltes Mana eingesetzt wird kann folgendes passieren:
 *	Alle Monster werden f�r 9 Sekunden eingefroren
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
	 * Gibt den aktuellen Manastand zur�ck
	 */
	public int getMana(){						
		return zauberkraft;
	}
	/**
	 * Erh�ht den Manastand um den als Parameter angegebenen Wert
	 * @param wert Der Wert, um den erh�ht werden soll
	 */
	public void setMana(int wert){				// Liest den Wert ein um den der Manastand ver�ndert werden soll und rechnet dementsprechend
		if(zauberkraft+wert>0 && zauberkraft+wert<=100){
			zauberkraft=zauberkraft+wert;
		}else if(zauberkraft+wert>100){
			zauberkraft=100;							// max 100 Manapunkte m�glich
		}else{
			zauberkraft=0;								// nur 0 oder positive Manast�nde m�glich
		}
	}
	/**
	 * Setzt den Manastand auf den angegebenen Wert
	 * @param manaCount Wert, den der Manabestand erhalten soll
	 */
	public void setAbsoluteMana(int manaCount){				// Liest den Wert ein um den der Manastand ver�ndert werden soll und rechnet dementsprechend
		zauberkraft = manaCount;
	}
	
}
