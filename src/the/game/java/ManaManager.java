package the.game.java;

public class ManaManager {
	
	private ManaManager() {
		
	}
	
	/**     SPRUCH AUSFÜHREN     */
	public static void useMana(int spellID, int playerID) {
		int manaAmount = getManaAmount(spellID);						// Kosten abfragen
		if(isEnoughMana(playerID, manaAmount)==false)					// Überprüfen ob Guthaben ausreicht
			return;														// Wenn nicht, beenden
		if(startProcess(spellID, playerID))								// Spruch ausführen
			Player.getMana(playerID).setMana(manaAmount * (-1));		// Wenn erfolgreich, Kosten von Mana abziehen
	}
	
	private static boolean isEnoughMana(int playerID, int amount) {
		if(Player.getMana(playerID).zauberkraft-amount>=0)
			return true;
		else
			return false;
	}
	
	private static int getManaAmount(int spellID) {
		switch(spellID) {
		case 0:
			return 25;
		default:
			return 0;
		}
	}
	
	private static boolean startProcess(int spellID, int playerID) {
		switch(spellID) {
		case 0:		// Freeze Enemies
			TemporaryItem.activateEnemyFreezing(playerID);
			break;
		default:
			return false;
		}
		return true;
	}
}
