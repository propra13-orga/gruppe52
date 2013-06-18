package the.game.java;

import java.awt.Image;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Traps {
	
	public static List<Traps> trapList = new ArrayList<Traps>();
	private int x;
	private int y;
	private int damageOnTouch;
	private int hp;
	private int type;
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
	
	private Traps(int posx, int posy, int trapType, boolean isUpdatable) {
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
	}
	
	public static void createMine(int posx, int posy) {
		trapList.add(new Traps(posx, posy, 0, false));
	}
	public static void createDalek(int posx, int posy) {
		trapList.add(new Traps(posx, posy, 1, true));
	}
	
	private void setOffset() {
		offsetX = 0;
		offsetY = 0;
		switch(type) {
		case 0:	// Mine
			offsetX = 8;
			offsetY = 8;
		}
	}
	
	public static void removeAllTraps() {
		for(int a=0; a<trapList.size(); a++) {
			if(trapList.get(a).valid) {
				DisplayManager.removeChangeableImages(trapList.get(a).tag);
			}
		}
		trapList.clear();
	}
	
	public static void checkPlayerCollideWithTraps(int playerID, int trapID) {
		if(Intersect.isPlayerCollidingWithTrap(playerID, trapID)) {
			if(trapList.get(trapID).valid)
				trapList.get(trapID).doOnTouch(playerID);
		}
	}
	
	public void reduceHealthPoints(int damage) {
		hp -= damage;
		if(hp<0)
			doOnDeath();
	}
	
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
	
	
	private void doOnUpdate() {
		if(updatable==false)
			return;
		switch(type) {
		case 1:	// Dalek
			//setFireRound();
			//setFireCirle();
			setRandomDirectionShotDOWN();
			break;
		}
	}
	
	private void doOnTouch(int playerID) {
		switch(type) {
		case 0:	// Mine
			doOnDeath();
			Player.playerList.get(playerID).reduceHealthPoints(damageOnTouch);
			break;
		case 1:	// Dalek
			Player.playerList.get(playerID).reduceHealthPoints(damageOnTouch);
			break;
		}
	}
	
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
	
	
	/**     SPECIALS     */
	private void setFireCirle() {
		for(int a=0; a<360; a+=15) {
			createProjectile(a);
		}
	}
	private void setFireRound() {
		fireRoundCounter += 10;
		if(fireRoundCounter>=360)
			fireRoundCounter -= 360;
		createProjectile(fireRoundCounter);
	}
	private void setRandomDirectionShot() {
		createProjectile(Projectile.random.nextInt(360));
	}
	private void setRandomDirectionShotDOWN() {
		createProjectile(Projectile.random.nextInt(180)+180);
	}
	
	private void createProjectile(int angle) {
		Projectile.createProjectile(x+(imgSizeX/2), y+(imgSizeY/2), 6, 95, 1, 0, projectile, angle, "trap");
	}
	/**     SPECIALS END     */
	
	
	public static int getX(int trapID) {
		return trapList.get(trapID).x;
	}
	public static int getY(int trapID) {
		return trapList.get(trapID).y;
	}
	public static int getImgSizeX(int trapID) {
		return trapList.get(trapID).imgSizeX;
	}
	public static int getImgSizeY(int trapID) {
		return trapList.get(trapID).imgSizeY;
	}
	public static int getOffsetX(int trapID) {
		return trapList.get(trapID).offsetX;
	}
	public static int getOffsetY(int trapID) {
		return trapList.get(trapID).offsetY;
	}
	public static int getType(int trapID) {
		return trapList.get(trapID).type;
	}
	public static boolean getValid(int trapID) {
		return trapList.get(trapID).valid;
	}
}
