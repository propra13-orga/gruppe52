package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class DisplayManager {
	
	public static List<DisplayManager> displayList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> animationList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> changeableList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> frontList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> stringList = new ArrayList<DisplayManager>();
	public boolean valid;
	public Image img;
	public int x;
	public int y;
	private long time;
	private long timeStamp;
	private String tag;
	public String text;
	
	
	// Initialisieren der benötigten Bilder
    private static Image wall = setImagePath("wall.png");
    private static Image trap = setImagePath("mine.gif");
    //private static Image monster = setImagePath("enemy.gif");
    private static Image heart = setImagePath("heart.png");
    //private static Image background = setImagePath("bg.png");
    //private Image explosion = setImagePath("explosion.gif");
    //private static Image menu = setImagePath("headmenu.png");
    private static Image finalGoal = setImagePath("goal.png");
    //private static Image layer = setImagePath("transparentLayer.png");
    //private static Image youDiedMenu = setImagePath("youdied.png");
    private static Image shield = setImagePath("shield.png");
	
	
	
	private static ImageIcon ii;
	
	private DisplayManager(Image image, int posx, int posy) {
		valid = true;
		img = image;
		x = posx;
		y = posy;
	}
	private DisplayManager(Image image, int posx, int posy, long timeMillis) {
		valid = true;
		img = image;
		x = posx;
		y = posy;
		time = timeMillis;
		timeStamp = System.currentTimeMillis();
	}
	private DisplayManager(Image image, int posx, int posy, String imageTag) {
		valid = true;
		img = image;
		x = posx;
		y = posy;
		tag = imageTag;
	}
	private DisplayManager(String displayText, int posx, int posy, String imageTag) {
		valid = true;
		text = displayText;
		x = posx;
		y = posy;
		tag = imageTag;
	}
	
	// Display
	public static void displayImage(Image image, int posx, int posy) {
		displayList.add(new DisplayManager(image, posx, posy));
	}
	public static void displayImage(String imagePath, int posx, int posy) {
		displayList.add(new DisplayManager(setImagePath(imagePath), posx, posy));
	}
	// Animation
	public static void displayImage(Image image, int posx, int posy, long timeMillis) {
		animationList.add(new DisplayManager(image, posx, posy, timeMillis));
	}
	public static void displayImage(String imagePath, int posx, int posy, long timeMillis) {
		animationList.add(new DisplayManager(setImagePath(imagePath), posx, posy, timeMillis));
	}
	// Veränderbar
	public static void displayImage(Image image, int posx, int posy, String imageTag) {
		changeableList.add(new DisplayManager(image, posx, posy, imageTag));
	}
	public static void displayImage(String imagePath, int posx, int posy, String imageTag) {
		changeableList.add(new DisplayManager(setImagePath(imagePath), posx, posy, imageTag));
	}
	// Menus (Oberste Ebene)
	public static void displayImage(Image image, int posx, int posy, String imageTag, boolean isMenu) {
		if(isMenu)
			frontList.add(new DisplayManager(image, posx, posy, imageTag));
		else
			changeableList.add(new DisplayManager(image, posx, posy, imageTag));
	}
	public static void displayImage(String imagePath, int posx, int posy, String imageTag, boolean isMenu) {
		if(isMenu)
			frontList.add(new DisplayManager(setImagePath(imagePath), posx, posy, imageTag));
		else
			changeableList.add(new DisplayManager(setImagePath(imagePath), posx, posy, imageTag));
	}
	// Strings
	public static void displayString(String text, int posx, int posy, String imageTag) {
		stringList.add(new DisplayManager(text, posx, posy, imageTag));
	}
	
	public static void updateDisplayTimers() {
		for(int a=0; a<animationList.size(); a++) {
        	if(System.currentTimeMillis() - animationList.get(a).timeStamp > animationList.get(a).time) {
        		animationList.remove(a);	// Eintrag löschen
        		a--;						// gleicher Wert noch einmal, da Einträge gelöscht wurden
        	}
        }
	}
	
	public static void removeChangeableImages(String imageTag) {	// löscht alle Bilder, die einen der Vorgabe entsprechenden Tag haben
		for(int a=0; a<changeableList.size(); a++) {
        	if(changeableList.get(a).tag==imageTag) {
        		changeableList.remove(a);	// Eintrag löschen
        		a--;						// gleicher Wert noch einmal, da Einträge gelöscht wurden
        	}
        }
		for(int a=0; a<frontList.size(); a++) {
        	if(frontList.get(a).tag==imageTag) {
        		frontList.remove(a);	// Eintrag löschen
        		a--;						// gleicher Wert noch einmal, da Einträge gelöscht wurden
        	}
        }
	}
	public static void removeChangeableStrings(String imageTag) {	// löscht alle Bilder, die einen der Vorgabe entsprechenden Tag haben
		for(int a=0; a<stringList.size(); a++) {
        	if(stringList.get(a).tag==imageTag) {
        		stringList.remove(a);	// Eintrag löschen
        		a--;						// gleicher Wert noch einmal, da Einträge gelöscht wurden
        	}
        }
	}
	public static void removeMenu() {
		frontList.clear();
	}
	
	
	/**     Methoden zur dauerhaften Darstellung     */
	public static void displayMap() {
		DisplayManager.removeChangeableImages("map");
		// Geht die gesamte itemMap durch und stellt alles dar, was dort angegeben wurde; Jedes darzustellende Objekt hat eine ID
	    for(int a=0; a < LevelCreator.getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<LevelCreator.getItemMapDimensions(1); b++) {		// Zeilenweise
				switch(LevelCreator.getItemMapData(a, b)) {
				case 0:
					break;
				case 1:
					displayImage(wall, a*20, b*20, "map");
					//g2d.drawImage(wall, a*20, b*20, this);		// Wall
					break;
				case 2:
					displayImage(trap, a*20, b*20, "map");
					//g2d.drawImage(trap, a*20, b*20, this);		// Trap
					break;
				case 10:
					displayImage(heart, a*20, b*20, "map");
					//g2d.drawImage(heart, a*20, b*20, this);		// Hearts
					break;
				case 5:
					displayImage(finalGoal, a*20, b*20, "map");
					//g2d.drawImage(finalGoal, a*20, b*20, this);	// Final Goal
					break;
				case 11:
					displayImage(shield, a*20, b*20, "map");
					//g2d.drawImage(shield, a*20, b*20, this);	// Shield
					break;
				}
				if(LevelCreator.getItemMapData(a, b)>100 && LevelCreator.getItemMapData(a, b)<200) {
					String str = (LevelCreator.getItemMapData(a, b)-100) + "s";	// verbleibende sekunden
					//g2d.drawString(str, a*20, 15);	// Timer
					displayString(str, a*20, b*20, "time");
				}
			}
		}
	    
	    NPC.displayNPC();
	}
	/**     Methoden zur dauerhaften Darstellung ENDE */
	// HILSMETHODEN
    private static Image setImagePath(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(DisplayManager.class.getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
	
}
