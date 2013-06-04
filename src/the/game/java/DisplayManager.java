package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class DisplayManager {
	
	public static List<DisplayManager> displayList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> animationList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> changeableList = new ArrayList<DisplayManager>();
	public static List<DisplayManager> stringList = new ArrayList<DisplayManager>();
	public boolean valid;
	public Image img;
	public int x;
	public int y;
	private long time;
	private long timeStamp;
	private String tag;
	public String text;
	
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
	}
	public static void removeChangeableStrings(String imageTag) {	// löscht alle Bilder, die einen der Vorgabe entsprechenden Tag haben
		for(int a=0; a<stringList.size(); a++) {
        	if(stringList.get(a).tag==imageTag) {
        		stringList.remove(a);	// Eintrag löschen
        		a--;						// gleicher Wert noch einmal, da Einträge gelöscht wurden
        	}
        }
	}
	
	// HILSMETHODEN
    private static Image setImagePath(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(DisplayManager.class.getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
	
}
