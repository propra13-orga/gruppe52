package the.game.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Von dieser Klasse Erben alle Waffen
 */
public class SpriteArmed extends Sprite{
	
	private static List<SpriteArmed> spriteArmedList = new ArrayList<SpriteArmed>();
	private List<Weapon> weaponInUseList;
	protected int spriteArmedID;
	private static int spriteArmedIDCounter = 0;
	
	private int weaponInUseID;
	
	/*
	private long measureForDelay;
	private long measureForReload;
	private boolean magEmpty;
	private int currentShotOfRound;
	*/
	
	/**
	 * Konstruktor(1) der Klasse SpriteArmed
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Objekts
	 * @param imgPathDe Pfad zum Bild eines besiegten Objekts
     * @param lifeCount Lebenspunkte
	 */
	public SpriteArmed(double posx, double posy, String imgPathAl, String imgPathDe, int lifeCount) {
		super(posx, posy, imgPathAl, imgPathDe, lifeCount);
		ini();
	}
	/**
	 * Konstruktor(2) der Klasse SpriteArmed
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Objekts
	 * @param imgPathDe Pfad zum Bild eines besiegten Objekts
	 */
	public SpriteArmed(double posx, double posy, String imgPathAl, String imgPathDe) {
		super(posx, posy, imgPathAl, imgPathDe);
		ini();
	}
	/**
	 * Initialisiert ALLES
	 */
	private void ini() {
		spriteArmedList.add(this);
		spriteArmedID = spriteArmedIDCounter;
		spriteArmedIDCounter++;
		weaponInUseList = new ArrayList<Weapon>();
		chooseWeapon(3);
	}
	
	
	/********************************************************
     * 
     * MANAGE WEAPONS
     * 
    /********************************************************/
	
	/**
	 * Resettet weaponInUseList
	 */
	public static void resetWeaponIULists() {
		for(int a=0; a<spriteArmedList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> success=true
			spriteArmedList.get(a).weaponInUseList.clear();
		}
	}
	
	/** CHOOSE & ADD WEAPONS */
	/**
	 * Wählt Waffe im Inventar aus
	 * @param weaponID ID der Waffe
	 */
	public void chooseWeapon(int weaponID) {
		for(int a=0; a<weaponInUseList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> success=true
			if(weaponInUseList.get(a).weaponID==weaponID) {
				weaponInUseID = a;
				return;
			}
		}
		weaponInUseID = weaponInUseList.size();
		addWeapon(weaponID);
	}
	/**
	 * Fügt Waffe dem Inventar hinzu
	 * @param weaponID ID der Waffe
	 */
	private void addWeapon(int weaponID) {
		weaponInUseList.add(Weapon.getWeapon(weaponID));
	}
	/**
	 * Fügt Waffe dem Inventar hinzu (komplex)
	 * @param sWeaponID ID der Waffe
	 * @param magCount Magazinzahl
	 * @param magSize Magazingröße
	 */
	public void addWeaponPrecisely(int sWeaponID, int magCount, int magSize) {
		chooseWeapon(sWeaponID);
		weaponInUseList.get(weaponInUseID).magCount = magCount;
		weaponInUseList.get(weaponInUseID).currentMagSize = magSize;
	}
	/**
	 * Fügt Munition hinzu
	 * @param weaponID ID der Waffe
	 * @param magCountIncrease Wert um den die Magazinzahl erhöht werden soll
	 */
	public void addAmmo(int weaponID, int magCountIncrease) { // Waffe suchen und Munition hinzufügen	
		for(int a=0; a<weaponInUseList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> Monition hinzufügen
			if(weaponInUseList.get(a).weaponID==weaponID) {
				weaponInUseList.get(a).magCount += magCountIncrease;
				if(getCurrentMagSize(weaponInUseID)<=0){
					getCurrentWeapon().magEmpty = true;
					reload();
				}
			}
		}
	}
	/**
	 * Wählt nächste Waffe in Liste
	 */
	public void chooseNextWeapon() {
		if((weaponInUseID+1)<weaponInUseList.size()) {
			weaponInUseID++;
			DisplayLine.setDisplay();
		}
	}
	/**
	 * Wählt vorherige Waffe in Liste
	 */
	public void choosePrevWeapon() {
		if(weaponInUseID>0) {
			weaponInUseID--;
			DisplayLine.setDisplay();
		}
	}
	
	/** GETTER WEAPONS */
	/**
	 * Gibt die Magazinzahl zurück
	 * @param weaponInUseID aktuell gewählte Waffe
	 * @return Magazinzahl
	 */
	public int getMagCount(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID).magCount;
	}
	/**
	 * Gibt die Magazingröße zurück
	 * @param weaponInUseID aktuell gewählte Waffe
	 * @return Magazingröße
	 */
	public int getCurrentMagSize(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID).currentMagSize;
	}
	/**
	 * Gibt ID der aktuellen Waffe zurück
	 * @return ID der Waffe
	 */
	public int getCurrentWeaponID() {
		return weaponInUseList.get(weaponInUseID).weaponID;
	}
	/**
	 * Gibt die ID einer Waffe im Inventar zurück
	 * @param weaponInUseID ID der Waffe im Inventar
	 * @return ID der Waffe
	 */
	public int getWeaponID(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID).weaponID;
	}
	/**
	 * Gibt Waffe zurück
	 * @param weaponInUseID ID der Waffe im Inventar
	 * @return Waffe
	 */
	public Weapon getWeapon(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID);
	}
	/**
	 * Gibt aktuell gewählte Waffe zurück
	 * @return currentWeapon
	 */
	public Weapon getCurrentWeapon() {
		return weaponInUseList.get(weaponInUseID);
	}
	/**
	 * Gibt die Zahl der Waffen im Inventar zurück
	 * @return Zahl der Waffen im Inventar
	 */
	public int getWeaponInUseCount() {
		return weaponInUseList.size();
	}
	/**
	 * Gibt an, ob Waffe gerade in Benutzung ist
	 * @param weaponID ID der Waffe
	 * @return true: in Benutzung; false: nicht in Benutzung
	 */
	public boolean isInUse(int weaponID) {
		for(int a=0; a<weaponInUseList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> return true
			if(weaponInUseList.get(a).weaponID==weaponID) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/********************************************************
     * 
     * SHOOT CONTROL
     * 
    /********************************************************/
	/**
	 * Ruft Methoden checkFire und doAfterMove auf
	 */
	protected void furtherInstPostMove(int spriteID) {
		checkFire(spriteID);
		doAfterMove(spriteID);
	}
	/**
	 * Was oassiert nach dem Move
	 * @param spriteID
	 */
	protected void doAfterMove(int spriteID) {
		
	}
	/**
	 * Überprüft ob gefeiert wird
	 * @param spriteID ID des Sprites
	 */
	private void checkFire(int spriteID) {
		if(getCurrentWeapon().magEmpty)
			reload();
		if(fire)
			doFire(spriteID);
	}
	/**
	 * Sorgt für das feuern
	 * @param spriteID ID des Sprites
	 */
	private void doFire(int spriteID) {
		if(System.currentTimeMillis() - getWeapon(weaponInUseID).measureForDelay >= getWeapon(weaponInUseID).fireRate) {
			if(getWeapon(weaponInUseID).melee) {
				
			} else {
				if(getWeapon(weaponInUseID).currentMagSize>0) {
					
					// Schuss ausführen
					if(getWeapon(weaponInUseID).shotgun)
						shootShotgun(spriteID);
					else if(getWeapon(weaponInUseID).shotsPerRound>1)
						shootSalve(spriteID);
					else
						shootSingleShot(spriteID);
					
					// Wenn Magazin leer...
					if(getWeapon(weaponInUseID).currentMagSize<=0 && getWeapon(weaponInUseID).magCount>1) {		// Wenn Magazin nun leer && nur wenn mehr als 1 Magazin verfügbar ist (also nur wenn man außer dem leeren Magazin noch ein weiteres hat)
						getWeapon(weaponInUseID).magEmpty = true;										// Flag: Magazin leer
						getWeapon(weaponInUseID).currentShotOfRound = 0;									// Für Salven: Schusszähler zurücksetzen
						getWeapon(weaponInUseID).measureForReload = System.currentTimeMillis();			// Startzeitpunkt des Nachladevorganges
		    			reload();	// Nachladevorgang einleiten
		    		}
					//DisplayLine.setDisplay();
				}
			}
		}
	}
	
	/**
	 * Schuss einer Shotgun
	 * @param spriteID ID des Sprites
	 */
	private void shootShotgun(int spriteID) {
		for(int counter=0; counter<getWeapon(weaponInUseID).shotsPerRound; counter++) {
			createProjectile(spriteID);
		}
		getWeapon(weaponInUseID).currentMagSize--;
		getWeapon(weaponInUseID).measureForDelay = System.currentTimeMillis();
	}
	/**
	 * Schuss eines EinsgleShots
	 * @param spriteID ID des Sprites
	 */
	private void shootSingleShot(int spriteID) {
		createProjectile(spriteID);
		getWeapon(weaponInUseID).currentMagSize--;
		getWeapon(weaponInUseID).measureForDelay = System.currentTimeMillis();
	}
	/**
	 * Schuss eines Salves
	 * @param spriteID ID des Sprites
	 */
	private void shootSalve(int spriteID) {
		createProjectile(spriteID);
		getWeapon(weaponInUseID).currentMagSize--;
		getWeapon(weaponInUseID).currentShotOfRound++;
		if(getWeapon(weaponInUseID).currentShotOfRound < getWeapon(weaponInUseID).shotsPerRound)
			getWeapon(weaponInUseID).measureForDelay = (long) (System.currentTimeMillis() - (double) (getWeapon(weaponInUseID).fireRate * 0.8000));
		else {
			getWeapon(weaponInUseID).measureForDelay = System.currentTimeMillis();
			getWeapon(weaponInUseID).currentShotOfRound = 0;
		}
	}
	/**
	 * Erzeugt ein Projektil
	 * @param spriteID ID des Sprites
	 */
	private void createProjectile(int spriteID) {
    	Projectile.createProjectile(
    			(getX() + imgSizeX / 2),
    			(getY() + imgSizeY / 2),
    			getWeapon(weaponInUseID).bulletSpeed, getWeapon(weaponInUseID).damage,
    			getWeapon(weaponInUseID).vigor, getWeapon(weaponInUseID).bulletSpread,
    			getWeapon(weaponInUseID).imgProjectile, Controls.getAngle(), "s" + spriteArmedID);
	}
	/**
	 * Reloaded den Waffenbestand
	 */
	private void reload() {													// Regelt das Nachladen einer Waffe
		if(getWeapon(weaponInUseID).magEmpty && getWeapon(weaponInUseID).magCount>1) {
			if(System.currentTimeMillis() - getWeapon(weaponInUseID).measureForReload >= getWeapon(weaponInUseID).reloadTime) {
				getWeapon(weaponInUseID).magEmpty = false;
				getWeapon(weaponInUseID).currentMagSize = getWeapon(weaponInUseID).magSize;
				getWeapon(weaponInUseID).magCount--;
				DisplayLine.setDisplay();
			}
		}
    }
}
