package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class ProjectileManager {	// Managed den Vorgang des Schie�ens
    /**
	 * Notiz zur Klasse:
	 * Diese Klasse ist f�r die Kontrolle der Projektilerzeugung zust�ndig
	 * Jeder Spieler bekommt ein Objekt dieser Klasse zugewiesen
	 * Hauptaufgabe ist das zeitlich korrekte Erstellen von Projektilen
	 * unter Beachtung der vorliegenden Parameter wie: fireRate, shotsPerRound, bulletSpeed, ...
	 * Somit wird ebenfalls Magazinkapazit�t und -anzahl kontrolliert
	 */
	
	public static List<ProjectileManager> projectileManagerList = new ArrayList<ProjectileManager>();
	private int playerID;
	private int fireRate;	// Sch�sse pro Sekunde
	private long measureForDelay;
	private long measureForReload;
	private int bulletSpeed;
	private int bulletSpread;
	private int magSize;
	private int magCount;
	private int reloadTime;
	private int currentMagSize;
	private boolean magEmpty;
	private int shotsPerRound;
	private int currentShotOfRound;
	private boolean melee;
	private int damage;
	private ImageIcon ii;
	private Image imgProjectile;
	private boolean shotgun;
	private int vigor;
	
	// TODO: measureForReload
	
	private ProjectileManager(int PID) {
		playerID = PID;
		// Variablen	
		fireRate = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).fireRate;				// Sch�sse pro Sekunde
		shotsPerRound = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).shotsPerRound;	// Alles > 1 erzeugt Schusssalven
		bulletSpeed = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).bulletSpeed;		// Projektil-Geschwindigkeit
		bulletSpread = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).bulletSpread;		// Streuweite der Projektile
		magSize = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).magSize;				// Sch�sse pro Magazin
		magCount = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).magCount;				// Anzahl Magazine
		reloadTime = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).reloadTime;			// Zeit in ms f�r Nachladevorgang
		melee = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).melee;					// Nahkampf oder nicht
		damage = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).damage;					// Schadenspunkte
		vigor = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).vigor;					// Durchschlagskraft
		imgProjectile = setProjectile(WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).imgPathProjectile);
		currentMagSize = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).currentMagSize;
		shotgun = WeaponManager.weaponManagerList.get(playerID).weaponInUseList.get(WeaponManager.weaponManagerList.get(playerID).weaponInUseID).shotgun;
		
		// Start- oder Laufwerte
		measureForDelay = System.currentTimeMillis();
		magEmpty = false;
		currentShotOfRound = 0;
	}
	
	public static void createProjectileManager(int PID) {
		projectileManagerList.add(new ProjectileManager(PID));
	}
	
	public static void updateData() {
		for(int a=0; a<WeaponManager.weaponManagerList.size(); a++) {
			projectileManagerList.get(a).fireRate = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).fireRate;				// Sch�sse pro Sekunde
			projectileManagerList.get(a).shotsPerRound = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).shotsPerRound;	// Alles > 1 erzeugt Schusssalven
			projectileManagerList.get(a).bulletSpeed = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).bulletSpeed;		// Projektil-Geschwindigkeit
			projectileManagerList.get(a).bulletSpread = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).bulletSpread;		// Streuweite der Projektile
			projectileManagerList.get(a).magSize = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).magSize;				// Sch�sse pro Magazin
			projectileManagerList.get(a).magCount = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).magCount;				// Anzahl Magazine
			projectileManagerList.get(a).reloadTime = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).reloadTime;			// Zeit in ms f�r Nachladevorgang
			projectileManagerList.get(a).melee = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).melee;					// Nahkampf oder nicht
			projectileManagerList.get(a).damage = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).damage;					// Schadenspunkte
			projectileManagerList.get(a).vigor = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).vigor;					// Durchschlagskraft
			projectileManagerList.get(a).imgProjectile = projectileManagerList.get(a).setProjectile(WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).imgPathProjectile);					// Schadenspunkte
			projectileManagerList.get(a).currentMagSize = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).currentMagSize;	// Kugeln im Magazin
			projectileManagerList.get(a).shotgun = WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).shotgun;				// Waffe ist eine Shotgun, ja/nein
			
			if(projectileManagerList.get(a).currentMagSize>0) {
				projectileManagerList.get(a).magEmpty = false;
			} else {
				projectileManagerList.get(a).magEmpty = true;
			}
				
				
				
			//projectileManagerList.get(a).currentMagSize = projectileManagerList.get(a).magSize;
			//System.out.println(WeaponManager.weaponManagerList.get(a).weaponInUseList.get(WeaponManager.weaponManagerList.get(a).weaponInUseID).magSize);
			//System.out.println(WeaponManager.weaponManagerList.get(a).weaponInUseID);
		}
	}
	
	private Image setProjectile(String path) {		// 
    	ii = new ImageIcon(this.getClass().getResource(path));
    	return ii.getImage();
    }
	
	// TODO: Einzelabzug oder nicht
	// vielleicht mit fireFlag in player, der immer im Wechsel 1 oder -1 �bergibt. zwei mal hintereinander das selbe, kein schuss f�r einzelschuss
    public static void controlProjectiles() {
    	for(int a=0; a<Player.playerList.size(); a++) {
    		projectileManagerList.get(a).setReload(a);	// Nachladevorgang �berpr�fen und ggf. aktualisieren
    		if(Player.playerList.get(a).getFireStatus()) {
    			projectileManagerList.get(a).setFire(a);
    		}
    	}
    	Projectile.move();	// sorgt f�r die Bewegung
    }
    
    private void setReload(int a) {													// Regelt das Nachladen einer Waffe
		if(magEmpty) {																// Flag: Wenn Magazin leer
			if(magCount>1) {														// Wenn noch Magazine �brig sind:
	    		if(System.currentTimeMillis() - measureForReload >= reloadTime) {	// wenn die erforderliche Nachladezeit verstichen ist:
					magEmpty = false;												// Flag zur�cksetzen
					currentMagSize = magSize;										// Magazin auff�llen
					magCount--;														// Ein Magazin weniger
	    		}
	    		updateChanges(a);													// �nderungen weiterleiten
			}
    	}
    }
    
    public void setFire(int index) {	// Erzeugt ein Projektil
    	
    	if(magEmpty) 	// FLAG Magazin leer:
    		setReload(index);	// Nachladevorgang einleiten (falls reloadTime = 0, kann sofort weitergemacht werden)
    	if(magEmpty==false) {		// FLAG Magazin nicht leer:
        	if(shotgun) {
        		for(int b=0; b<shotsPerRound; b++) {
        			shoot(index);
        		}
        	} else {
        		for(int b=0; b<shotsPerRound; b++) {
        			shoot(index);
        		}
        	}
    	}
    	updateChanges(index);	// �nderungen weiterleiten
    }
    
    private void shoot(int index) {
    	double shotDelay = 1000.00 / fireRate;	// n�tiger Abstand zwischen den Sch�ssen um auf korrekte fireRate zu kommen (in ms)
    	if(System.currentTimeMillis() - measureForDelay >= shotDelay) {	// �berpr�ft ob Abstand zwischen den Sch�ssen bereits erreicht
    		if(melee==false) {
	    		if(currentMagSize>0) {		// Wenn Magazin nicht leer:
	    			
	    			// Munition abziehen
	    			if(shotgun==false)	// wenn kein Schrotgewehr, dann regul�r Munition verbrauchen
	    				currentMagSize--;
	    			
		    		// Projektil erzeugen
	    			createProjectile(index);
	    			
				    // Zur�cksetzen der Delaykontrolle
    				if(shotsPerRound>1 && currentShotOfRound<shotsPerRound-1) {	// Mit Schusssalven:
    					if(shotgun)
    						measureForDelay = 0;	// damit alle Kugeln "gleichzeitig" verschossen werden (Schrot)
    					else
    						measureForDelay = (long) (System.currentTimeMillis() - (double) (shotDelay * 0.8000));	// Zeitpunkt des letzten Schusses - einen Bruchteil des shotDelay (verk�rzt somit die Zeit zwischen Sch�ssen => Salve)
    					currentShotOfRound++;									// F�r Salven: Schussz�hler +1
    				} else {													// Ohne Schusssalven & Schusssalvenende:
    					measureForDelay = System.currentTimeMillis();			// Zeitpunkt des letzten Schusses
    					currentShotOfRound = 0;									// F�r Salven: Schussz�hler zur�cksetzen
    					if(shotgun)												// Wenn Shotgun, dann nun Magazin -1 (erst hier, da ein Schrotschuss mehrere Projektile erzeugen darf)
    						currentMagSize--;
    				}
	    		}
	    		
	    		if(currentMagSize<=0) {		// Wenn Magazin nun leer:
	    			if(magCount>1) {											// nur wenn mehr als 1 Magazin verf�gbar ist (also nur wenn man au�er dem leeren Magazin noch ein weiteres hat)
	    				magEmpty = true;										// Flag: Magazin leer
	    				currentShotOfRound = 0;									// F�r Salven: Schussz�hler zur�cksetzen
	    				measureForReload = System.currentTimeMillis();			// Startzeitpunkt des Nachladevorganges
	    				setReload(index);	// Nachladevorgang einleiten
	    			}
	    		}
    		} else {
    			// TODO: MELEE
    			
    			
    			
    		}
    	}
    }
    

    private void createProjectile(int index) {
    	Projectile.createProjectile((Player.playerList.get(index).getX() + Player.playerList.get(index).imageSizeX / 2), (Player.playerList.get(index).getY() + Player.playerList.get(index).imageSizeY / 2), bulletSpeed, damage, vigor, bulletSpread, imgProjectile, Controls.getDirectionMarkerX(), Controls.getDirectionMarkerY());

    	//Projectile.createProjectile((Player.playerList.get(index).getX() + Player.playerList.get(index).imageSizeX / 2), (Player.playerList.get(index).getY() + Player.playerList.get(index).imageSizeY / 2), Player.playerList.get(index).getLastDirectionX() * bulletSpeed, Player.playerList.get(index).getLastDirectionY() * bulletSpeed, damage, vigor, bulletSpread, imgProjectile);
    }
    
    
    
    private void updateChanges(int index) {
    	// �nderungen weiterleiten
    	WeaponManager.weaponManagerList.get(index).weaponInUseList.get(WeaponManager.weaponManagerList.get(index).weaponInUseID).magCount = magCount;
    	WeaponManager.weaponManagerList.get(index).weaponInUseList.get(WeaponManager.weaponManagerList.get(index).weaponInUseID).currentMagSize = currentMagSize;
    	DisplayLine.setDisplay();
    }
    
}
