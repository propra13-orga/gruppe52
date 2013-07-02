package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class SpriteArmed extends Sprite{
	
	private static List<SpriteArmed> spriteArmedList = new ArrayList<SpriteArmed>();
	private List<Weapon> weaponInUseList;
	
	private int weaponInUseID;
	
	/**
	private long measureForDelay;
	private long measureForReload;
	private boolean magEmpty;
	private int currentShotOfRound;
	*/
	public SpriteArmed(double posx, double posy, String imgPathAl, String imgPathDe, int lifeCount) {
		super(posx, posy, imgPathAl, imgPathDe, lifeCount);
		ini();
	}
	public SpriteArmed(double posx, double posy, String imgPathAl, String imgPathDe) {
		super(posx, posy, imgPathAl, imgPathDe);
		ini();
	}
	private void ini() {
		spriteArmedList.add(this);
		weaponInUseList = new ArrayList<Weapon>();
		chooseWeapon(3);
	}

	// TODO: resetWeaponInUseLists()
	
	/********************************************************
     * 
     * MANAGE WEAPONS
     * 
    /********************************************************/
	
	public static void resetWeaponIULists() {
		for(int a=0; a<spriteArmedList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> success=true
			spriteArmedList.get(a).weaponInUseList.clear();
		}
	}
	
	/** CHOOSE & ADD WEAPONS */
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
	
	private void addWeapon(int weaponID) {
		weaponInUseList.add(Weapon.getWeapon(weaponID));
	}
	
	public void addWeaponPrecisely(int sWeaponID, int magCount, int magSize) {
		chooseWeapon(sWeaponID);
		weaponInUseList.get(weaponInUseID).magCount = magCount;
		weaponInUseList.get(weaponInUseID).currentMagSize = magSize;
	}
	
	public void addAmmo(int weaponID, int magCountIncrease) { // Waffe suchen und Munition hinzufügen	
		for(int a=0; a<weaponInUseList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> success=true
			if(weaponInUseList.get(a).weaponID==weaponID) {
				weaponInUseList.get(a).magCount += magCountIncrease;
			}
		}
	}
	
	public void chooseNextWeapon() {
		if((weaponInUseID+1)<weaponInUseList.size()) {
			weaponInUseID++;
			DisplayLine.setDisplay();
		}
	}
	
	public void choosePrevWeapon() {
		if(weaponInUseID>0) {
			weaponInUseID--;
			DisplayLine.setDisplay();
		}
	}
	
	/** GETTER WEAPONS */
	public int getMagCount(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID).magCount;
	}
	public int getCurrentMagSize(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID).currentMagSize;
	}
	public int getCurrentWeaponID() {
		return weaponInUseList.get(weaponInUseID).weaponID;
	}
	public int getWeaponID(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID).weaponID;
	}
	public Weapon getWeapon(int weaponInUseID) {
		return weaponInUseList.get(weaponInUseID);
	}
	public Weapon getCurrentWeapon() {
		return weaponInUseList.get(weaponInUseID);
	}
	public int getWeaponInUseCount() {
		return weaponInUseList.size();
	}
	
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
	
	protected void furtherInstPostMove(int spriteID) {
		checkFire(spriteID);
		doAfterMove(spriteID);
	}
	
	protected void doAfterMove(int spriteID) {
		
	}
	
	private void checkFire(int spriteID) {
		if(getCurrentWeapon().magEmpty)
			reload();
		if(fire)
			doFire(spriteID);
	}
	
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
	
	private void shootShotgun(int spriteID) {
		for(int counter=0; counter<getWeapon(weaponInUseID).shotsPerRound; counter++) {
			createProjectile(spriteID);
		}
		getWeapon(weaponInUseID).currentMagSize--;
		getWeapon(weaponInUseID).measureForDelay = System.currentTimeMillis();
	}
	
	private void shootSingleShot(int spriteID) {
		createProjectile(spriteID);
		getWeapon(weaponInUseID).currentMagSize--;
		getWeapon(weaponInUseID).measureForDelay = System.currentTimeMillis();
	}
	
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
	
	private void createProjectile(int spriteID) {
    	Projectile.createProjectile(
    			(getX() + imgSizeX / 2),
    			(getY() + imgSizeY / 2),
    			getWeapon(weaponInUseID).bulletSpeed, getWeapon(weaponInUseID).damage,
    			getWeapon(weaponInUseID).vigor, getWeapon(weaponInUseID).bulletSpread,
    			getWeapon(weaponInUseID).imgProjectile, Controls.getAngle(), "player");
	}
	
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
