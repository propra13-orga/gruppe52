package the.game.java;

import java.awt.Image;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse verwaltet alle Objekte, die den Eigenschaftel einer Trap entsprechen
 * (Mine, Dalek usw)
 *
 */
public class Traps {
	
	public static List<Traps> trapList = new ArrayList<Traps>();
	private int x;
	private int y;
	private int damageOnTouch;
	private int damageType=0;
	private int hp;
	private int type;
	private String subType = "";
	private Image img;
	private int imgSizeX;
	private int imgSizeY;
	private String tag;
	private static int marker = 0;
	private boolean valid;
	private int offsetX;
	private int offsetY;
	private long timeStamp;
	private long timeDistance;
	private Image projectile;
	private boolean updatable;
	private int fireRoundCounter;
	
	/**
	 * Konstruktor der Klasse Traps
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 * @param trapType Typ der Trap (1=Dalek oder 0=Mine?)
	 * @param isUpdatable true falls Dalek (was immer aktualisiert werden muss), false falls Mine
	 * @param subtype ggf was für ein Dalek?
	 */
	private Traps(int posx, int posy, int trapType, boolean isUpdatable, String subtype) {
		valid = true;
		x = posx;
		y = posy;
		type = trapType;
		img = getImageOfType(type);
		imgSizeX = img.getWidth(null);
		imgSizeY = img.getHeight(null);
		setOffset();
		tag = "trap" + marker;
		hp = 99000;
		damageOnTouch = getDamageOfType(type);
		DisplayManager.displayImage(img, x, y, tag);
		marker++;
		timeStamp = System.currentTimeMillis();
		timeDistance = 300;
		updatable = isUpdatable;
		fireRoundCounter = 0;
		subType = subtype;
		if(trapType==0)
			damageType = 2;
	}
	/**
	 * Erzeugt eine Mine
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 */
	public static void createMine(int posx, int posy) {
		trapList.add(new Traps(posx, posy, 0, false, ""));
	}
	/**
	 * ERzeugt einen Dalek
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 */
	public static void createDalek(int posx, int posy) {
		trapList.add(new Traps(posx, posy, 1, true, ""));
	}
	/**
	 * Erzeugt einen Dalek mit subType
	 * @param posx x-Position des Objekts
	 * @param posy y-Position des Objekts
	 * @param subType ggf was für ein Dalek?
	 */
	public static void createDalek(int posx, int posy, String subType) {
		trapList.add(new Traps(posx, posy, 1, true, subType));
	}
	
	/**
	 * Legt den Offset fest.
	 * Für Mine=8, da nur bei Kollision mit Zentrum Schaden zugefügt wird
	 */
	private void setOffset() {
		offsetX = 0;
		offsetY = 0;
		switch(type) {
		case 0:	// Mine
			offsetX = 8;
			offsetY = 8;
		}
	}
	
	/**
	 * Entfernt alle Traps von der Map
	 */
	public static void removeAllTraps() {
		for(int a=0; a<trapList.size(); a++) {
			if(trapList.get(a).valid) {
				DisplayManager.removeChangeableImages(trapList.get(a).tag);
			}
		}
		trapList.clear();
	}
	
	/**
	 * Prüft, ob Player mit Trap kollidiert
	 * @param playerID ID des Players
	 * @param trapID ID der Trap
	 */
	public static void checkPlayerCollideWithTraps(int playerID, int trapID) {
		if(Intersect.isPlayerCollidingWithTrap(playerID, trapID)) {
			if(trapList.get(trapID).valid)
				trapList.get(trapID).doOnTouch(playerID);
		}
	}
	/**
	 * Fügt dem Player Schadenspunkte zu
	 * @param damage Zahl der Schadenspunkte
	 * @param offenderType Typ der Trap, die Schaden zufügt
	 */
	public void reduceHealthPoints(int damage, int offenderType) {
		damage = Damage.getDamage(damage, offenderType, damageType);
		hp -= damage;
		if(hp<0)
			doOnDeath();
	}
	
	/**
	 * Aktualisierung speziell für Daleks nötig
	 */
	public static void updater() {
		for(int a=0; a<trapList.size(); a++) {
			if(trapList.get(a).valid) {
				if(System.currentTimeMillis() - trapList.get(a).timeStamp >= trapList.get(a).timeDistance) {
					trapList.get(a).timeStamp = System.currentTimeMillis();
					trapList.get(a).doOnUpdate();
				}
			}
		}
	}
	
	/**
	 * Legt fest wie ein Dalek dem subType entsprechend schießen soll
	 */
	private void doOnUpdate() {
		if(updatable==false)
			return;
		switch(type) {
		case 1:	// Dalek
			switch(subType) {
			case "round":
				setFireRound();
				break;
			case "random":
				setRandomDirectionShot();
				break;
			case "down":
				setFireRoundDOWN();
				break;
			}
			break;
		}
	}
	
	/**
	 * Legt fest was bei Kollision mit einer Trap passieren soll
	 * @param playerID ID des Players
	 */
	private void doOnTouch(int playerID) {
		switch(type) {
		case 0:	// Mine
			doOnDeath();
			Player.playerList.get(playerID).reduceHealthPoints(damageOnTouch, damageType);
			break;
		case 1:	// Dalek
			Player.playerList.get(playerID).reduceHealthPoints(damageOnTouch, damageType);
			break;
		}
	}
	
	/**
	 * Legt fest, was passieren soll, wenn der Player so viel Schaden erleidet, dass er stirbt
	 */
	private void doOnDeath() {
		switch(type) {
		case 0:	// Mine
			DisplayManager.removeChangeableImages(tag);
			DisplayManager.displayImage("explosion.gif", x, y, 850);
			valid = false;
			break;
		case 1:	// Dalek
			DisplayManager.removeChangeableImages(tag);
			DisplayManager.displayImage("explosion.gif", x, y, 850);
			valid = false;
			break;
		}
	}
	/**
	 * Lädt das der Trap entsprechende Bild auf die Map
	 * @param type type der Trap
	 * @return Bild
	 */
	private Image getImageOfType(int type) {
		Image img = null;
		switch(type) {
		case 0:
			img = DisplayManager.getImage("traps/mine.gif");
			break;
		case 1:
			img = DisplayManager.getImage("traps/dalek.png");
			projectile = DisplayManager.getImage("projectile.png");
			break;
		}
		return img;
	}
	/**
	 * Gibt zurück, wie viel Schaden eine Trap bei koliision zufügt
	 * @param type type der Trap
	 * @return Schadenszahl
	 */
	private static int getDamageOfType(int type) {
		int damage = 0;
		switch(type) {
		case 0:
			damage = 65;
			break;
		case 1:
			damage = 165;
			break;
		}
		return damage;
	}
	
	
	/**
	 * Schussrichtung Dalek: Kreisförmig
	 */
	private void setFireCirle() {
		for(int a=0; a<360; a+=15) {
			createProjectile(a);
		}
	}
	/**
	 * Schussrichtung Dalek: Rund
	 */
	private void setFireRound() {
		fireRoundCounter += 10;
		if(fireRoundCounter>=360)
			fireRoundCounter -= 360;
		createProjectile(fireRoundCounter);
	}
	/**
	 * Schussrichtung Dalek: Rund in unteren 90° 
	 */
	private void setFireRoundDOWN() {
		fireRoundCounter -= 10;
		if(fireRoundCounter<=225)
			fireRoundCounter = 315;
		createProjectile(fireRoundCounter);
	}
	/**
	 * Schussrichtung Dalek: Zufällig
	 */
	private void setRandomDirectionShot() {
		createProjectile(Projectile.random.nextInt(360));
	}
	/**
	 * Schussrichtung Dalek: Zufällig in untere 90°
	 */
	private void setRandomDirectionShotDOWN() {
		createProjectile(Projectile.random.nextInt(180)+180);
	}
	/**
	 * ERzeugt ein Projektiel, dass als Munition dient
	 * @param angle Winkel
	 */
	private void createProjectile(int angle) {
		Projectile.createProjectile(x+(imgSizeX/2), y+(imgSizeY/2), 6, 95, 1, 0, projectile, angle, "trap");
	}
	
	/**
	 * Gibt x-Position zurück
	 * @param trapID ID der Trap
	 * @return x
	 */
	public static int getX(int trapID) {
		return trapList.get(trapID).x;
	}
	/**
	 * Gibt y-Position zurück
	 * @param trapID ID der Trap
	 * @return y
	 */
	public static int getY(int trapID) {
		return trapList.get(trapID).y;
	}
	/**
	 * Gibt Breite des Bildes zurück
	 * @param trapID ID der Trap
	 * @return width
	 */
	public static int getImgSizeX(int trapID) {
		return trapList.get(trapID).imgSizeX;
	}
	/**
	 * Gibt Höhe des Bildes zurück
	 * @param trapID ID der Trap
	 * @return height
	 */
	public static int getImgSizeY(int trapID) {
		return trapList.get(trapID).imgSizeY;
	}
	/**
	 * Gibt Offset in x-Richtung zurück
	 * @param trapID ID der Trap
	 * @return offsetX
	 */
	public static int getOffsetX(int trapID) {
		return trapList.get(trapID).offsetX;
	}
	/**
	 * Gibt Offset in y-Richtung zurück
	 * @param trapID ID der Trap
	 * @return offsetY
	 */
	public static int getOffsetY(int trapID) {
		return trapList.get(trapID).offsetY;
	}
	/**
	 * Gibt den Type der Trap zurück
	 * @param trapID ID der Trap
	 * @return dem type entsprechende nummer
	 */
	public static int getType(int trapID) {
		return trapList.get(trapID).type;
	}
	/**
	 * Gibt den Wert der Trap zurück
	 * @param trapID ID der Trap
	 * @return Wert
	 */
	public static boolean getValid(int trapID) {
		return trapList.get(trapID).valid;
	}
}
