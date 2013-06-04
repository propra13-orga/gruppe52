package the.game.java;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
	public static List<Weapon> weaponList = new ArrayList<Weapon>();
	public int weaponIDCounter = 0;
	public int weaponID;
	public int fireRate;
	public int bulletSpeed;
	public int magSize;
	public int magCount;
	public int reloadTime;
	public int shotsPerRound;
	public int damage;
	public boolean melee;
	public String imgPath;
	public String imgPathProjectile;
	public boolean shotgun;
	public int bulletSpread;
	public String name;
	public int price;
	
	private static int sFireRate;
	private static int sBulletSpeed;
	private static int sMagSize;
	private static int sMagCount;
	private static int sReloadTime;
	private static int sShotsPerRound;
	private static int sDamage;
	private static boolean sMelee;
	private static String sImgPath;
	private static String sImgPathProjectile;
	private static boolean sShotgun;
	private static int sBulletSpread;
	private static String sName;
	private static int sPrice;
	
	// Konstruktor
	private Weapon(String wName, int wFireRate, int wBulletSpeed, int wBulletSpread, int wMagSize, int wMagCount, int wReloadTime, int wShotsPerRound, int wDamage, boolean wMelee, String wImgPath, String wImgPathProjectile, boolean wShotgun, int wPrice) {
		weaponID = weaponIDCounter;
		weaponIDCounter++;
		
		name = wName;
		price = wPrice;
		
		fireRate = wFireRate;
		bulletSpeed = wBulletSpeed;
		bulletSpread = wBulletSpread;
		magSize = wMagSize;
		magCount = wMagCount;
		reloadTime = wReloadTime;
		shotsPerRound = wShotsPerRound;
		
		damage = wDamage;
		melee = wMelee;
		shotgun = wShotgun;
		
		imgPath = wImgPath;
		imgPathProjectile = wImgPathProjectile;
	}
	
	
	public static void loadWeapons() {
		weaponList.clear();
		fist();			// #0
		knife();		// #1
		pistol();		// #2
		mp();			// #3
		rifle();		// #4
		rifleBig();		// #5
		rifleFast();	// #6
		shotgun();		// #7
		shotgunFast();	// #8
	}

	private static void addWeapon() {
		weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun, sPrice));
	}
	
	/**     HIER SIND ALLE WAFFEN AUFGELISTET     */
	
	private static void fist() {
		sName = "Fists";
		sPrice = 0;
		
		sFireRate = 4;
		sBulletSpeed = 4;
		sBulletSpread = 0;
		sMagSize = 8;
		sMagCount = 15;
		sReloadTime = 500;
		sShotsPerRound = 4;
		
		sDamage = 6;
		sMelee = true;
		sShotgun = false;
		
		sImgPath = "trap.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void knife() {
		sName = "Knife";
		sPrice = 50;
		
		sFireRate = 4;
		sBulletSpeed = 4;
		sBulletSpread = 0;
		sMagSize = 8;
		sMagCount = 15;
		sReloadTime = 500;
		sShotsPerRound = 4;
		
		sDamage = 6;
		sMelee = true;
		sShotgun = false;

		sImgPath = "trap.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void pistol() {
		sName = "Pistol";
		sPrice = 100;
		
		sFireRate = 4;
		sBulletSpeed = 4;
		sBulletSpread = 0;
		sMagSize = 15;
		sMagCount = 15;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 12;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponPistol.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void mp() {
		sName = "MP";
		sPrice = 1000;
		
		sFireRate = 20;
		sBulletSpeed = 6;
		sBulletSpread = 0;
		sMagSize = 32;
		sMagCount = 10;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 12;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponMP.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void rifle() {
		sName = "Rifle";
		sPrice = 2000;
		
		sFireRate = 8;
		sBulletSpeed = 6;
		sBulletSpread = 0;
		sMagSize = 32;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 3;
		
		sDamage = 24;
		sMelee = false;
		sShotgun = false;
		
		sImgPath = "weapon/weaponRifle.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void rifleBig() {
		sName = "Big Rifle";
		sPrice = 3500;
		
		sFireRate = 7;
		sBulletSpeed = 6;
		sBulletSpread = 0;
		sMagSize = 32;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 26;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponRifle2.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void rifleFast() {
		sName = "Small Bore Rifle";
		sPrice = 4000;
		
		sFireRate = 22;
		sBulletSpeed = 7;
		sBulletSpread = 3;
		sMagSize = 42;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 16;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponRifle3.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void shotgun() {
		sName = "Shotgun";
		sPrice = 3000;
		
		sFireRate = 2;
		sBulletSpeed = 7;
		sBulletSpread = 10;
		sMagSize = 8;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 6;
		
		sDamage = 10;
		sMelee = false;
		sShotgun = true;

		sImgPath = "weapon/weaponShotgun.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
	
	private static void shotgunFast() {
		sName = "The Grim Reaper";
		sPrice = 6000;
		
		sFireRate = 5;
		sBulletSpeed = 7;
		sBulletSpread = 10;
		sMagSize = 20;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 6;
		
		sDamage = 10;
		sMelee = false;
		sShotgun = true;

		sImgPath = "weapon/weaponShotgun2.png";
		sImgPathProjectile = "projectile.png";
		
		addWeapon();
		//weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sMelee, sImgPath, sImgPathProjectile, sShotgun));
	}
}
