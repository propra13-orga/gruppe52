package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * 
 *  In dieser Klasse kann der Manastand eines Spielers abgefragt und ver�ndert werden.
 *  Der kleinst-m�gliche Manastand betr�gt 0. Der Maximale Manastand betr�gt 100. 
 *  Wenn gesammeltes Mana eingesetzt wird kann folgendes passieren:
 *
 */

public class Mana {

	public int zauberkraft;  									// Zauberkraft des Spielers
	
	private Image manaSymbol = DisplayManager.getImage("mana.png");		// Mana-Symbol, das auf der Karte eingesammelt werden kann
	
	int x;
	int y;
	private static boolean hilfCollide;
	
	public Mana(){
		zauberkraft = 20;
	}
	
	//public static List<Mana> manaList = new ArrayList<Mana>();	// Der Manastand wird in einer Liste verwaltet
	
	/*
	 * Ein Objekt der Klasse Mana kann f�r jeden Spieler erstellt werden
	 
	public static void createMana() {			
		manaList.add(new Mana());
	}*/
	
	/*
	 * Gibt den aktuellen Manastand zur�ck
	 */
	public int getMana(){						
		return zauberkraft;
	}
	/*
	 * Erh�ht den Manastand um den als Parameter angegebenen Wert
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
	public void setAbsoluteMana(int manaCount){				// Liest den Wert ein um den der Manastand ver�ndert werden soll und rechnet dementsprechend
		zauberkraft = manaCount;
	}
	
	/*
	 * bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zur�ck
	 
    public Image setImagePath(String path) {						
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }*/
	
}
