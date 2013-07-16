package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * Regelt die Darstellung der Informationsleiste oberhalb des Spielfeldes
 */
public class DisplayLine {
	public static List<DisplayLine> displayLineList = new ArrayList<DisplayLine>();
	public int x;
	public int y;
	public boolean isString;
	public int magCount;
	public Image img;
	private String txt;
	
	/**
	 * Konstruktor(1) der Klasse DisplayLine.
	 * Wird verwendet, wenn ein Bildpfad übergeben wird.
	 * @param posx x-Position der jeweiligen Angabe in der Informationsleiste
	 * @param posy y-Position der jeweiligen Angabe in der Informationsleiste
	 * @param path Pfad zum jeweiligen Bild das dargestellt werden soll
	 */
	private DisplayLine(int posx, int posy, String path) {
		x = posx;
		y = posy;
		img = DisplayManager.getImage(path);
		isString = false;
	}
	/**
	 * Konstruktor(2) der Klasse DisplayLine.
	 * Wird verwendet, wenn ein Bild übergeben wird.
	 * @param posx x-Position der jeweiligen Angabe in der Informationsleiste
	 * @param posy y-Position der jeweiligen Angabe in der Informationsleiste
	 * @param i Bild das dargestellt werden soll
	 */
	private DisplayLine(int posx, int posy, Image i) {
		x = posx;
		y = posy;
		img = i;
		isString = false;
	}
	/**
	 * Konstruktor(3) der Klasse DisplayLine.
	 * Wird verwendet, wenn ein String dargestellt werden soll.
	 * @param posx x-Position der jeweiligen Angabe in der Informationsleiste
	 * @param posy y-Position der jeweiligen Angabe in der Informationsleiste
	 * @param dIsString	Unterscheidet Konstruktor 3 von Konstruktor 1
	 * @param msg Text der Dargestellt werden soll
	 */
	private DisplayLine(int posx, int posy, boolean dIsString, String msg) {
		x = posx;
		y = posy;
		isString = true;
		txt = msg;
	}
	/**
	 * Konstruktor(4) der Klasse DisplayLine.
	 * Wird verwendet, wenn die Magazinzahl dargestellt werden soll.
	 * @param posx x-Position der jeweiligen Angabe in der Informationsleiste
	 * @param posy y-Position der jeweiligen Angabe in der Informationsleiste
	 * @param dIsString Da ein Integer-Wert schriftlich dargestellt werden soll
	 * @param dMagCount	Magazinanzahl, wird vor ausgewählter Waffe dargestellt
	 */
	private DisplayLine(int posx, int posy, boolean dIsString, int dMagCount) {
		x = posx;
		y = posy;
		isString = true;
		magCount = dMagCount;
	}
	
	/**
	 * Stellt die DisplayLine dar.
	 * Die Methoden setWeapon(), setBullets() und setHearts() werden aufgerufen.
	 */
	public static void setDisplay() {
		displayLineList.clear();
		
		if(Runner.codeRunning==false)
			return;
		if(Player.playerList.get(0).getWeaponInUseCount()>0) {
			setWeapon();
			setBullets();
		}
		setMana();
		setHearts();
	}
	
	/**
	 * Stellt Waffenangabe dar.
	 */
	private static void setWeapon() {
		int posx = 200;
		int posy = 0;
		displayLineList.add(new DisplayLine(posx, posy, Player.playerList.get(0).getCurrentWeapon().img));
	}
	
	/**
	 * Stellt Magazinangabe dar.
	 * Dies bezieht sich sowohl auf die Magazinzahl, als auch auf die Magazingröße
	 */
	private static void setBullets() {
		int count = Player.playerList.get(0).getCurrentWeapon().currentMagSize;
		int magCount = Player.playerList.get(0).getCurrentWeapon().magCount - 1; 	// -1, da ein Magazin bereits in Benutzung
		if(count>50)																// falls magazin überaus groß
			count=50;
		boolean bool=true;
		int posx = 185;
		int posy = 13;
		if(magCount>9)																// wenn magazine zweistellig, weiter nach links rücken
			posx = 178;
		displayLineList.add(new DisplayLine(posx, posy, bool, magCount));
		posx = 262;
		posy = 0;
		for(int a=0; a<count; a++) {
			displayLineList.add(new DisplayLine(posx, posy, "weaponBullet.png"));
			posx += 4;
		}
	}
	
	private static void setMana() {
		displayLineList.add(new DisplayLine(600, 0, ManaManager.getCurrentImg()));
	}
	
	/**
	 * Stellt verbliebene Leben dar.
	 */
	private static void setHearts() {
		for(int a=0; a<Player.playerList.get(0).getLives() && a<6; a++) {
			displayLineList.add(new DisplayLine(a*20, 0, "heart.png"));
		}
	}

}
