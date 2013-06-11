package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class DisplayLine {
	public static List<DisplayLine> displayLineList = new ArrayList<DisplayLine>();
	public int x;
	public int y;
	public boolean isString;
	public int magCount;
	public Image img;
	private ImageIcon ii;
	
	private DisplayLine(int posx, int posy, String path) {
		x = posx;
		y = posy;
		img = setImagePath(path);
		isString = false;
	}
	private DisplayLine(int posx, int posy, boolean dIsString, int dMagCount) {
		x = posx;
		y = posy;
		isString = true;
		magCount = dMagCount;
	}
	
	public static void setDisplay() {
		displayLineList.clear();
		if(WeaponManager.weaponManagerList.get(0).weaponInUseList.get(WeaponManager.weaponManagerList.get(0).weaponInUseID).weaponID>0) {
			setWeapon();
			setBullets();
		}
		setHearts();
	}
	
	private static void setWeapon() {
		int posx = 200;
		int posy = 0;
		String path = WeaponManager.weaponManagerList.get(0).weaponInUseList.get(WeaponManager.weaponManagerList.get(0).weaponInUseID).imgPath;
		
		displayLineList.add(new DisplayLine(posx, posy, path));
	}
	
	private static void setBullets() {
		int count = WeaponManager.weaponManagerList.get(0).weaponInUseList.get(WeaponManager.weaponManagerList.get(0).weaponInUseID).currentMagSize;
		int magCount = WeaponManager.weaponManagerList.get(0).weaponInUseList.get(WeaponManager.weaponManagerList.get(0).weaponInUseID).magCount - 1; // -1, da ein Magazin bereits in Benutzung
		if(count>50)	// falls magazin überaus groß
			count=50;
		boolean bool=true;
		int posx = 185;
		int posy = 13;
		if(magCount>9)	// wenn magazine zweistellig, weiter nach links rücken
			posx = 178;
		displayLineList.add(new DisplayLine(posx, posy, bool, magCount));
		posx = 262;
		posy = 0;
		for(int a=0; a<count; a++) {
			displayLineList.add(new DisplayLine(posx, posy, "weaponBullet.png"));
			posx += 4;
		}
	}
	
	private static void setHearts() {
		for(int a=0; a<Player.playerList.get(0).getLives(); a++) {
			displayLineList.add(new DisplayLine(a*20, 0, "heart.png"));
		}
	}
	// TODO: HEARTS Lebensanzeige
	
    private Image setImagePath(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
}
