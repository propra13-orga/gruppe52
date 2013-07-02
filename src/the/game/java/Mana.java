package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * 
 *  In dieser Klasse kann der Manastand eines Spielers abgefragt und verändert werden.
 *  Der kleinst-mögliche Manastand beträgt 0. Der Maximale Manastand beträgt 100. 
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
	 * Ein Objekt der Klasse Mana kann für jeden Spieler erstellt werden
	 
	public static void createMana() {			
		manaList.add(new Mana());
	}*/
	
	/*
	 * Gibt den aktuellen Manastand zurück
	 */
	public int getMana(){						
		return zauberkraft;
	}
	/*
	 * Erhöht den Manastand um den als Parameter angegebenen Wert
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
	public void setAbsoluteMana(int manaCount){				// Liest den Wert ein um den der Manastand verändert werden soll und rechnet dementsprechend
		zauberkraft = manaCount;
	}
	
	/*
	 * bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
	 
    public Image setImagePath(String path) {						
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }*/
	
}
