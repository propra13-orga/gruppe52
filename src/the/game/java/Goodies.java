package the.game.java;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Verwaltet das darstellen un die Kollisionseffekte von Goodies
 * Goodies sind credits, hearts, shields, mana und vests.
 *
 */
public class Goodies {
	
	public static List<Goodies> goodiesList = new ArrayList<Goodies>();
	
	//private boolean valid;
	private int x;
	private int y;
	private int amount; // TODO: amount
	private String type;
	private Image img;
	private int imgSizeX;
	private int imgSizeY;
	private String tag;
	private static int marker = 0;
	
	public final static String goodie0 = "credits";
	public final static String goodie1 = "heart";
	public final static String goodie2 = "shield";
	public final static String goodie3 = "mana";
	public final static String goodie4 = "vest";
	
	/**
	 * Konstruktor der Klasse Goodies
	 * @param posx x-Position des Goodies
	 * @param posy y-Position des Goodies
	 * @param goodieType Typ des Goodies (goodie1 = heart, goodie2 = shield, usw)
	 * @param goodieAmount Betrag des Goodies (für Credits)
	 */
	private Goodies(int posx, int posy, String goodieType, int goodieAmount) {
		//valid = true;
		x = posx;
		y = posy;
		type = goodieType;
		img = getImageOfType(type);
		imgSizeX = img.getWidth(null);
		imgSizeY = img.getHeight(null);
		tag = "goodie" + marker;
		amount = goodieAmount;
		DisplayManager.displayImage(img, x, y, tag);
	}
	
	/**
	 * Erstellt ein Goodie
	 * @param posx x-Position des Goodies
	 * @param posy y-Position des Goodies
	 * @param goodieType Typ des Goodies (goodie1 = heart, goodie2 = shield, usw) 
	 */
	public static void createGoodie(int posx, int posy, String goodieType) {
		goodiesList.add(new Goodies(posx, posy, goodieType, 1));
	}
	/**
	 * Erstellt ein Goodie vom Typ credits
	 * @param posx x-Position des Goodies
	 * @param posy y-Position des Goodies
	 * @param amount Betrag den der Spieler beim einsammeln erhalten soll
	 */
	public static void createCredits(int posx, int posy, int amount) {
		goodiesList.add(new Goodies(posx, posy, goodie0, amount));
	}
	
	/**
	 * Prüft, ob ein Spieler mit einem Goodie kollidiert
	 * @param playerID Nr des Spielers
	 * @param goodieID Nr des Goodies
	 */
	public static void checkPlayerCollideWithGoodie(int playerID, int goodieID) {
		if(Intersect.isPlayerCollidingWithGoodies(playerID, goodieID)) {
			receiveAcquirement(goodieID, playerID);
		}
	}

	/**
	 * Sorgt dafür, dass Kollision mit Goodie den gewünschten Effekt hat.
	 * @param goodieID Nr des Goodies
	 * @param playerID Nr des Spielers
	 */
	public static void receiveAcquirement(int goodieID, int playerID) {
		goodiesList.get(goodieID).setAcquirementConsequence(playerID);
		goodiesList.get(goodieID).removeGoodie(goodieID);
	}
	
	/**
	 * Goodie wird von Karte entfernt
	 * @param index ID des Goodies
	 */
	private void removeGoodie(int index) {
    	DisplayManager.removeChangeableImages(goodiesList.get(index).tag);
    	goodiesList.remove(index);
	}
	/**
	 * Alle Goodies werden von der Karte entfernt.
	 */
	public static void removeAllGoodies() {
		for(int a=0; a<goodiesList.size(); a++) {
			DisplayManager.removeChangeableImages(goodiesList.get(a).tag);
		}
    	goodiesList.clear();
	}
	
	/**
	 * Verwaltet die Effekte, die die verschiedenen Goodietypen nach Kollision haben
	 * @param playerID ID des Spielers
	 */
	private void setAcquirementConsequence(int playerID) {
		switch(type) {
		case goodie0:
			Player.playerList.get(playerID).score.setScore(amount);
			break;
		case goodie1:
			Player.playerList.get(playerID).receiveLives(amount);
			break;
		case goodie2:
			TemporaryItem.activateInvincibility(playerID);
			break;
		case goodie3:
			Player.getMana(playerID).setMana(20); // TODO: AMOUNT
			break;
		case goodie4:
			Item.addItem(0, playerID);
			break;
		}
	}
	
	/**
	 * Ist dafür verantworklich, dass das richtige Bild dargestellt wird.
	 * @param goodieType Typ des Goodies
	 * @return das passende Bild
	 */
	private Image getImageOfType(String goodieType) {
		Image img=null;
		switch(goodieType) {
		case goodie0:
			img = getImage("goodies/credits.png");
			break;
		case goodie1:
			img = getImage("goodies/heart.png");
			break;
		case goodie2:
			img = getImage("goodies/shield.png");
			break;
		case goodie3:
			img = getImage("goodies/mana.png");
			break;
		case goodie4:
			img = Item.getImg(0);
			break;
		}
		return img;
	}
	
	
	/**
	 * Stellt das einem Pfad entsprechende Bild dar
	 * @param path Pfad an dem sich das Bild befinden soll
	 * @return das entsprechende Bild
	 */
	private Image getImage(String path) {
		Image img = null;
		try {
			img = ImageIO.read(new File("src/the/game/java/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	/**
	 * Gibt die x-Position zurück
	 * @param goodieID ID des Goodies
	 * @return x
	 */
	public static int getX(int goodieID) {
		return goodiesList.get(goodieID).x;
	}
	/**
	 * Gibt die y-Position zurück
	 * @param goodieID ID des Goodies
	 * @return y
	 */
	public static int getY(int goodieID) {
		return goodiesList.get(goodieID).y;
	}
	/**
	 * Gibt die Breite des Bildes zurück
	 * @param goodieID ID des Goodies
	 * @return width
	 */
	public static int getImgSizeX(int goodieID) {
		return goodiesList.get(goodieID).imgSizeX;
	}
	/**
	 * Gibt die Höhe des Bildes zurück
	 * @param goodieID ID des Goodies
	 * @return height
	 */
	public static int getImgSizeY(int goodieID) {
		return goodiesList.get(goodieID).imgSizeY;
	}
	/**
	 * Gibt den der ID entsprechenden Goodytype zurück
	 * @param goodieID ID des Goodies
	 * @return type
	 */
	public static String getType(int goodieID) {
		return goodiesList.get(goodieID).type;
	}
}
