package the.game.java;

import java.awt.Image;

/**
 * Verwaltet alle verschiedene Manaspr�che, die ausgef�hrt werden k�nnen und sorgt f�r den jeweiligen Effekt
 * @author Kapie
 *
 */
public class ManaManager {
	
	private static long delay = 750;
	private static long timeStamp = 0;
	
	private static int currentSelection = 0;
	
	private static Image m0 = DisplayManager.getImage("mana/freeze.png");
	private static Image m1 = DisplayManager.getImage("mana/umhang_gruen.png");
	private static Image m2 = DisplayManager.getImage("mana/umhang_gelb.png");
	private static Image m3 = DisplayManager.getImage("mana/umhang_rot.png");
	
	private ManaManager() {
		
	}
	
	public static void selectNext() {
		if(getImg(currentSelection+1)!=null) {
			currentSelection++;
			DisplayLine.setDisplay();
		}
		System.out.println("Select Next: " + currentSelection);
	}
	
	public static void selectPrev() {
		if(getImg(currentSelection-1)!=null) {
			currentSelection--;
			DisplayLine.setDisplay();
		}
		System.out.println("Select Prev: " + currentSelection);
	}
	
	public static Image getCurrentImg() {
		return getImg(currentSelection);
	}
	
	public static void useCurrentSpell(int playerID) {
		useMana(currentSelection, playerID);
		System.out.println("Use Mana Spell: " + currentSelection);
	}
	
	
	/**
	 * F�hrt einen Manaspruch aus
	 * @param spellID ID des Spruches
	 * @param playerID ID des Players
	 */
	public static void useMana(int spellID, int playerID) {
		if(System.currentTimeMillis()-timeStamp < delay)
			return;
		
		timeStamp = System.currentTimeMillis();
		
		int manaAmount = getManaAmount(spellID);						// Kosten abfragen
		if(isEnoughMana(playerID, manaAmount)==false)					// �berpr�fen ob Guthaben ausreicht
			return;														// Wenn nicht, beenden
		if(startProcess(spellID, playerID))								// Spruch ausf�hren
			Player.getMana(playerID).setMana(manaAmount * (-1));		// Wenn erfolgreich, Kosten von Mana abziehen
	}
	
	/**
	 * Pr�ft, ob Spieler genug Mana f�r einen Spruch besitzt
	 * @param playerID ID des Spielers
	 * @param amount Manabetrag
	 * @return true wenn genug Mana f�r den Spruch, false wenn nicht
	 */
	private static boolean isEnoughMana(int playerID, int amount) {
		if(Player.getMana(playerID).zauberkraft-amount>=0)
			return true;
		else
			return false;
	}
	
	/**
	 * Gibt den Manabetrag, den ein Spruch brauch zur�ck
	 * @param spellID ID des Spruches
	 * @return Manazahl, die abgezogen werden soll nach Spruchausf�hrung
	 */
	private static int getManaAmount(int spellID) {
		switch(spellID) {
		case 0:
			return 25;
		case 1:	// DMGTYPE 1
			return 0;
		case 2:	// DMGTYPE 2
			return 0;
		case 3:	// DMGTYPE 3
			return 0;
		default:
			return 0;
		}
	}
	
	/**
	 * Pr�ft ob der Effekt, den ein Spruch nach der ausf�hrung haben soll, ausgef�hrt werden kann und startet ihn
	 * @param spellID ID des Spruches
	 * @param playerID ID des Spielers
	 * @return true, wenn der Spruch ausgef�hrt werden kann, false, wenn nicht
	 */
	private static boolean startProcess(int spellID, int playerID) {
		switch(spellID) {
		case 0:		// Freeze Enemies
			TemporaryItem.activateEnemyFreezing(playerID);
			break;
		case 1:
			Player.getPlayer(playerID).damageType = spellID;
			TemporaryItem.activateDmgType(playerID);
			break;
		case 2:
			Player.getPlayer(playerID).damageType = spellID;
			TemporaryItem.activateDmgType(playerID);
			break;
		case 3:
			Player.getPlayer(playerID).damageType = spellID;
			TemporaryItem.activateDmgType(playerID);
			break;
		default:
			return false;
		}
		return true;
	}
	
	/**
	 * Liefert das Image des jeweiliges Spells
	 * @param spellID	ID des gew�hlten Spells
	 * @return			Image des gew�hlten Spells
	 */
	public static Image getImg(int spellID) {
		switch(spellID) {
		case 0:
			return m0;
		case 1:	// DMGTYPE 1
			return m1;
		case 2:	// DMGTYPE 2
			return m2;
		case 3:	// DMGTYPE 3
			return m3;
		default:
			return null;
		}
	}
}
