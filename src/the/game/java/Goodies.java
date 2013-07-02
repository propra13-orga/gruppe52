package the.game.java;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
	
	public static void createGoodie(int posx, int posy, String goodieType) {
		goodiesList.add(new Goodies(posx, posy, goodieType, 1));
	}
	public static void createCredits(int posx, int posy, int amount) {
		goodiesList.add(new Goodies(posx, posy, goodie0, amount));
	}
	
	/**
	public static void createGoodie(int posx, int posy, int goodieType, boolean randomPos) {
		if(randomPos) {
			posx += getRandomPos();
			posy += getRandomPos();
		}	
		goodiesList.add(new Goodies(posx, posy, goodieType));
	}
	
	
	private int getRandomPos() {
		int
		
		return 
	}
	*/
	
	public static void checkPlayerCollideWithGoodie(int playerID, int goodieID) {
		if(Intersect.isPlayerCollidingWithGoodies(playerID, goodieID)) {
			receiveAcquirement(goodieID, playerID);
		}
	}
	/*
	private static void checkGoodieStatus() {
		for(int a=0; a<goodiesList.size(); a++) {
        	if(goodiesList.get(a).valid==false) {
        		DisplayManager.removeChangeableImages(goodiesList.get(a).tag);
        		a--;
        	}
        }
	}
	*/
	public static void receiveAcquirement(int goodieID, int playerID) {
		goodiesList.get(goodieID).setAcquirementConsequence(playerID);
		goodiesList.get(goodieID).removeGoodie(goodieID);
	}
	
	private void removeGoodie(int index) {
		//valid = false;
    	DisplayManager.removeChangeableImages(goodiesList.get(index).tag);
    	goodiesList.remove(index);
	}
	public static void removeAllGoodies() {
		for(int a=0; a<goodiesList.size(); a++) {
			DisplayManager.removeChangeableImages(goodiesList.get(a).tag);
		}
    	goodiesList.clear();
	}
	
	
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
	
	
	
	private Image getImage(String path) {
		Image img = null;
		try {
			img = ImageIO.read(new File("src/the/game/java/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static int getX(int goodieID) {
		return goodiesList.get(goodieID).x;
	}
	public static int getY(int goodieID) {
		return goodiesList.get(goodieID).y;
	}
	public static int getImgSizeX(int goodieID) {
		return goodiesList.get(goodieID).imgSizeX;
	}
	public static int getImgSizeY(int goodieID) {
		return goodiesList.get(goodieID).imgSizeY;
	}
	public static String getType(int goodieID) {
		return goodiesList.get(goodieID).type;
	}
}
