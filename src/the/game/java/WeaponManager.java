package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class WeaponManager {
	// UMSCHREIBEN FÜR JEDEN SPIELER EIN OBJEKT !
	// EINZELNE LISTE FÜR JEDE WAFFE; FLAG OB VORHANDEN; MIT CURRENT WERDEN!
	public static List<WeaponManager> weaponManagerList = new ArrayList<WeaponManager>();
	public List<WeaponManager> weaponInUseList = new ArrayList<WeaponManager>();
	public int playerID;
	public int weaponID;
	public int fireRate;	// Schüsse pro Sekunde
	public int bulletSpeed;
	public int bulletSpread;
	public int magSize;
	public int magCount;
	public int reloadTime;
	public int shotsPerRound;
	public int damage;
	public int vigor;
	public boolean melee;
	public String imgPath;
	public String imgPathProjectile;
	public int weaponInUseID;
	public int currentMagSize;
	public boolean shotgun;
	
	private WeaponManager() {	// Keine Waffe (für weaponManagerList)
		weaponID = 3;	// weaponID der Startwaffe
		
		weaponInUseList.add(new WeaponManager(weaponID));
		weaponInUseID = 0;
	}
	
	private WeaponManager(int sWeaponID) {	// Mit Waffe (für weaponInUseList)
		weaponID = sWeaponID;
		
		fireRate = Weapon.weaponList.get(weaponID).fireRate;
		bulletSpeed = Weapon.weaponList.get(weaponID).bulletSpeed;
		bulletSpread = Weapon.weaponList.get(weaponID).bulletSpread;
		magSize = Weapon.weaponList.get(weaponID).magSize;
		magCount = Weapon.weaponList.get(weaponID).magCount;
		reloadTime = Weapon.weaponList.get(weaponID).reloadTime;
		shotsPerRound = Weapon.weaponList.get(weaponID).shotsPerRound;
		currentMagSize = magSize;
		
		damage = Weapon.weaponList.get(weaponID).damage;
		vigor = Weapon.weaponList.get(weaponID).vigor;
		melee = Weapon.weaponList.get(weaponID).melee;
		shotgun = Weapon.weaponList.get(weaponID).shotgun;
		imgPath = Weapon.weaponList.get(weaponID).imgPath;
		imgPathProjectile = Weapon.weaponList.get(weaponID).imgPathProjectile;
	}
	
	
	public static void createWeaponManager() {	// für jeden Spieler einen
		weaponManagerList.add(new WeaponManager());
	}
	
	public void chooseNextWeapon() {
		if((weaponInUseID+1)<weaponInUseList.size()) {
			chooseWeapon(weaponInUseList.get(weaponInUseID+1).weaponID);
		}
	}
	
	public void choosePrevWeapon() {
		if(weaponInUseID>0) {
			chooseWeapon(weaponInUseList.get(weaponInUseID-1).weaponID);
		}
	}
	
	public void chooseWeapon(int sWeaponID) {		
		// Neue Waffe wählen
		boolean success = false;
		for(int a=0; a<weaponInUseList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> success=true
			if(weaponInUseList.get(a).weaponID==sWeaponID) {
				weaponInUseID = a;
				//loadWeaponData(sWeaponID);
				success = true;
			}
		}
		if(success==false) {
			weaponInUseID = weaponInUseList.size();
			weaponInUseList.add(new WeaponManager(sWeaponID));
			//loadWeaponData(sWeaponID);
		}
		// Änderungen weiterleiten
		updateData();
	}
	
	public void addAmmo(int sWeaponID, int magCountIncrease) {		
		// Waffe suchen und Munition hinzufügen
		for(int a=0; a<weaponInUseList.size(); a++) {	// Waffe im Inventar suchen, wenn vorhanden -> success=true
			if(weaponInUseList.get(a).weaponID==sWeaponID) {
				weaponInUseList.get(a).magCount += magCountIncrease;
				// Änderungen weiterleiten
				updateData();
			}
		}
	}
	
	private static void updateData() {
		// Variablen für Projektile aktualisieren
		ProjectileManager.updateData();
		// Display aktualisieren
		DisplayLine.setDisplay();
	}
}
