package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle Objekte, die die Eigenschaften einer Waffe erhalten.
 */
public class Weapon {
	/**
	 * LISTEN
	 */
	public static List<Weapon> weaponList = new ArrayList<Weapon>();
	
	/**
	 * ObjektVariablen
	 */
	private static int weaponIDCounter;
	
	public int weaponID;
	public double fireRate;
	public int bulletSpeed;
	public int magSize;
	public int magCount;
	public int reloadTime;
	public int shotsPerRound;
	public int damage;
	public boolean melee;
	public Image img;
	public Image imgProjectile;
	public boolean shotgun;
	public int bulletSpread;
	public String name;
	public int price;
	public int radius;
	public int magPackSize;
	public int magPackPrice;
	public int vigor;
	
	public int currentMagSize;
	public long measureForDelay;
	public long measureForReload;
	public boolean magEmpty;
	public int currentShotOfRound;
	
	public static Image standardBullet = DisplayManager.getImage("projectile.png");
	
	/**
	 * Variablen f�r die Initialisierung
	 */
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
	private static int sRadius;
	private static int sMagPackSize;
	private static int sMagPackPrice;
	private static int sVigor;
	
	/**
	 * Konstruktor der Klasse Weapon
	 * Parameter sind alle Eigenschaften, die eine Waffe haben kann
	 * @param wName
	 * @param wFireRate
	 * @param wBulletSpeed
	 * @param wBulletSpread
	 * @param wMagSize
	 * @param wMagCount
	 * @param wReloadTime
	 * @param wShotsPerRound
	 * @param wDamage
	 * @param wVigor
	 * @param wMelee
	 * @param wRadius
	 * @param wImgPath
	 * @param wImgPathProjectile
	 * @param wShotgun
	 * @param wPrice
	 * @param wMagPackSize
	 * @param wMagPackPrice
	 */
	private Weapon(String wName, int wFireRate, int wBulletSpeed, int wBulletSpread, int wMagSize, int wMagCount, int wReloadTime, int wShotsPerRound, int wDamage, int wVigor, boolean wMelee, int wRadius, String wImgPath, String wImgPathProjectile, boolean wShotgun, int wPrice, int wMagPackSize, int wMagPackPrice) {
		weaponID = weaponIDCounter;
		weaponIDCounter++;
		
		name = wName;
		price = wPrice;
		
		fireRate = 60000 / wFireRate;
		bulletSpeed = wBulletSpeed;
		bulletSpread = wBulletSpread;
		magSize = wMagSize;
		magCount = wMagCount;
		reloadTime = wReloadTime;
		shotsPerRound = wShotsPerRound;
		radius = wRadius;
		
		damage = wDamage;
		vigor = wVigor;
		melee = wMelee;
		shotgun = wShotgun;
		
		magPackSize = wMagPackSize;
		magPackPrice = wMagPackPrice;
		
		img = DisplayManager.getImage(wImgPath);
		if(wImgPathProjectile!=null)
			imgProjectile = DisplayManager.getImage(wImgPathProjectile);
		
		// Vorbereitende Variablen
		currentMagSize = magSize;
		measureForDelay = 0;
		measureForReload = 0;
		magEmpty = false;
		currentShotOfRound = 0;
	}
	/**
	 * Gibt die Waffe zur�ck
	 * @param weaponID ID der Waffe
	 * @return weapon
	 */
	public static Weapon getWeapon(int weaponID) {
		return weaponList.get(weaponID);
	}
	/**
	 * L�dt die gew�nschte Waffe
	 */
	public static void loadWeapons() {
		weaponList.clear();
		weaponIDCounter = 0;
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
	/**
	 * F�gt die Waffe einer weaponList hinzu
	 */
	private static void addWeapon() {
		weaponList.add(new Weapon(sName, sFireRate, sBulletSpeed, sBulletSpread, sMagSize, sMagCount, sReloadTime, sShotsPerRound, sDamage, sVigor, sMelee, sRadius, sImgPath, sImgPathProjectile, sShotgun, sPrice, sMagPackSize, sMagPackPrice));
	}
	
	/**     HIER SIND ALLE WAFFEN AUFGELISTET     */
	
	/**
	 * Waffe: fist
	 */
	private static void fist() {
		sName = "Fists";
		sPrice = 0;
		
		sFireRate = 240;
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
	}
	/**
	 * Waffe: Knife
	 */
	private static void knife() {
		sName = "Knife";
		sPrice = 50;
		
		sFireRate = 240;
		sBulletSpeed = 4;
		sBulletSpread = 0;
		sMagSize = 8;
		sMagCount = 15;
		sReloadTime = 500;
		sShotsPerRound = 4;
		sRadius = 25;
		
		sDamage = 6;
		sMelee = true;
		sShotgun = false;

		sImgPath = "trap.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: Pistol
	 */
	private static void pistol() {
		sName = "Pistol";
		sPrice = 100;
		
		sFireRate = 240;
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
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: MP
	 */
	private static void mp() {
		sName = "MP";
		sPrice = 900;
		
		sFireRate = 1200;
		sBulletSpeed = 6;
		sBulletSpread = 0;
		sMagSize = 32;
		sMagCount = 10;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 12;
		sVigor = 1;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponMP.png";
		sImgPathProjectile = "projectile.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: Rifle
	 */
	private static void rifle() {
		sName = "Rifle";
		sPrice = 1800;
		
		sFireRate = 280;
		sBulletSpeed = 6;
		sBulletSpread = 0;
		sMagSize = 32;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 3;
		
		sDamage = 24;
		sVigor = 1;
		sMelee = false;
		sShotgun = false;
		
		sImgPath = "weapon/weaponRifle.png";
		sImgPathProjectile = "projectile.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: RifleBig
	 */
	private static void rifleBig() {
		sName = "Big Rifle";
		sPrice = 2500;
		
		sFireRate = 420;
		sBulletSpeed = 6;
		sBulletSpread = 0;
		sMagSize = 32;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 26;
		sVigor = 10;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponRifle2.png";
		sImgPathProjectile = "projectile.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: RifleFast
	 */
	private static void rifleFast() {
		sName = "Small Bore Rifle";
		sPrice = 3500;
		
		sFireRate = 1320;
		sBulletSpeed = 7;
		sBulletSpread = 2;
		sMagSize = 42;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 1;
		
		sDamage = 16;
		sVigor = 1;
		sMelee = false;
		sShotgun = false;

		sImgPath = "weapon/weaponRifle3.png";
		sImgPathProjectile = "projectile.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: Shotgun
	 */
	private static void shotgun() {
		sName = "Shotgun";
		sPrice = 5000;
		
		sFireRate = 120;
		sBulletSpeed = 7;
		sBulletSpread = 5;
		sMagSize = 8;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 6;
		
		sDamage = 10;
		sVigor = 1;
		sMelee = false;
		sShotgun = true;

		sImgPath = "weapon/weaponShotgun.png";
		sImgPathProjectile = "projectile.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
	/**
	 * Waffe: ShotgunFast
	 */
	private static void shotgunFast() {
		sName = "The Grim Reaper";
		sPrice = 7000;
		
		sFireRate = 300;
		sBulletSpeed = 7;
		sBulletSpread = 5;
		sMagSize = 20;
		sMagCount = 6;
		sReloadTime = 1350;
		sShotsPerRound = 6;
		
		sDamage = 10;
		sVigor = 5;
		sMelee = false;
		sShotgun = true;

		sImgPath = "weapon/weaponShotgun2.png";
		sImgPathProjectile = "projectile.png";
		
		sMagPackSize = 3;
		sMagPackPrice = 600;
		
		addWeapon();
	}
}
