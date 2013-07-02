package the.game.java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
	public static boolean darkness = false;
	private static Image dark = getImage("misc/darkLayer.png");
	
	
	// Initialisieren der benötigten Bilder
    private static Image wall = getImage("wall.png");
    private static Image trap = getImage("mine.gif");
    //private static Image monster = getImage("enemy.gif");
    private static Image heart = getImage("heart.png");
    //private static Image background = getImage("bg.png");
    //private Image explosion = getImage("explosion.gif");
    //private static Image menu = getImage("headmenu.png");
    private static Image finalGoal = getImage("goal.png");
    //private static Image layer = getImage("transparentLayer.png");
    //private static Image youDiedMenu = getImage("youdied.png");
    private static Image shield = getImage("shield.png");
    private static Image menu = getImage("misc/headmenu.png");
	
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
	
	
	public static void draw(Graphics2D g2d) {
		JPanel jp = Setter.getJPanel();
		
		// ChangeableList
		for(int a=0; a<changeableList.size(); a++) {
    		g2d.drawImage(changeableList.get(a).img, changeableList.get(a).x, changeableList.get(a).y, jp);
		}
		
		// Player
	    for(int a=0; a<Player.playerList.size(); a++) {	// Setzt ein Icon für jeden erstellten Spieler
	    	g2d.drawImage(Player.playerList.get(a).getImg(), Player.playerList.get(a).getX(), Player.playerList.get(a).getY(), jp);
	    }
	    
	    // Projectiles
	    for(int a=0; a<Projectile.projectileList.size(); a++) {	// Setzt ein Icon für jedes erstellte Projektil
	    	if(Projectile.projectileList.get(a).isValid())
	    		g2d.drawImage(Projectile.projectileList.get(a).projectile, Projectile.projectileList.get(a).getX(), Projectile.projectileList.get(a).getY(), jp);
	    }
	    
	    // AnimationList
	    for(int a=0; a<DisplayManager.animationList.size(); a++) {
 			g2d.drawImage(DisplayManager.animationList.get(a).img, DisplayManager.animationList.get(a).x, DisplayManager.animationList.get(a).y, jp);
 		}
	    
	    if(darkness) {
	    	g2d.drawImage(dark, Player.playerList.get(0).getX()-970, Player.playerList.get(0).getY()-574, jp);
	    }
	    
	    for(int a=0; a<((Runner.getWidthF()-(Runner.getWidthF()%20))/20); a++) {	// Titelmenü - Hintergrund darstellen
        	g2d.drawImage(menu, a*20, 0, jp);
        }
	    
	    g2d.setColor(Color.red);
	    g2d.fill(new Rectangle2D.Double(480, 2, Player.playerList.get(0).getHealthPoints(), 7));
	    g2d.setColor(Color.blue);
	    g2d.fill(new Rectangle2D.Double(480, 10, Player.getMana(0).getMana(), 7));
	    g2d.setColor(Color.DARK_GRAY);
	    
	    // Display Line
	    for(int a=0; a<DisplayLine.displayLineList.size(); a++) {
 			if(DisplayLine.displayLineList.get(a).isString) {
 				g2d.drawString(DisplayLine.displayLineList.get(a).magCount + "x", DisplayLine.displayLineList.get(a).x, DisplayLine.displayLineList.get(a).y);
 			} else {
 				g2d.drawImage(DisplayLine.displayLineList.get(a).img, DisplayLine.displayLineList.get(a).x, DisplayLine.displayLineList.get(a).y, jp);
 			}
 		}
	    
		// FrontList
	    for(int a=0; a<frontList.size(); a++) {
			g2d.drawImage(frontList.get(a).img, frontList.get(a).x, frontList.get(a).y, jp);
		}
	    
		// ChangeableStringList
		for(int a=0; a<stringList.size(); a++) {
		        g2d.drawString(stringList.get(a).text, stringList.get(a).x, stringList.get(a).y);
		        //System.out.println(stringList.get(a).text+"   "+stringList.get(a).x+"   "+stringList.get(a).y);
		}
		
		
	}
	
	
	
	
	// Display
	public static void displayImage(Image image, int posx, int posy) {
		displayList.add(new DisplayManager(image, posx, posy));
	}
	public static void displayImage(String imagePath, int posx, int posy) {
		displayList.add(new DisplayManager(getImage(imagePath), posx, posy));
	}
	// Animation
	public static void displayImage(Image image, int posx, int posy, long timeMillis) {
		animationList.add(new DisplayManager(image, posx, posy, timeMillis));
	}
	public static void displayImage(String imagePath, int posx, int posy, long timeMillis) {
		animationList.add(new DisplayManager(getImage(imagePath), posx, posy, timeMillis));
	}
	// Veränderbar
	public static void displayImage(Image image, int posx, int posy, String imageTag) {
		changeableList.add(new DisplayManager(image, posx, posy, imageTag));
	}
	public static void displayImage(String imagePath, int posx, int posy, String imageTag) {
		changeableList.add(new DisplayManager(getImage(imagePath), posx, posy, imageTag));
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
			frontList.add(new DisplayManager(getImage(imagePath), posx, posy, imageTag));
		else
			changeableList.add(new DisplayManager(getImage(imagePath), posx, posy, imageTag));
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
	
	/**     HILFSMETHODEN     */
	public static Image getImage(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
		Image img = null;
		try {
			img = ImageIO.read(new File("src/the/game/java/" + path));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Pfad: src/the/game/java/" + path);
		}
		return img;
	}
}
