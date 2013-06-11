package the.game.java;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Goodies {
	
	public static List<Goodies> goodiesList = new ArrayList<Goodies>();
	
	private boolean valid;
	private int x;
	private int y;
	private int type;
	private Image img;
	private int imgSizeX;
	private int imgSizeY;
	private String tag;
	private static int marker = 0;
	
	private Goodies(int posx, int posy, int goodieType) {
		valid = true;
		x = posx;
		y = posy;
		type = goodieType;
		img = getImage(type);
		imgSizeX = img.getWidth(null);
		imgSizeY = img.getHeight(null);
		tag = "goodie" + marker;
		DisplayManager.displayImage(img, x, y, tag);
	}
	
	public static void createGoodie(int posx, int posy, int goodieType) {
		goodiesList.add(new Goodies(posx, posy, goodieType));
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
		valid = false;
    	DisplayManager.removeChangeableImages(goodiesList.get(index).tag);
    	goodiesList.remove(index);
	}
	
	private void setAcquirementConsequence(int playerID) {
		switch(type) {
		case 0:
			Score.scoreList.get(playerID).setScore(150);
			break;
		}
	}
	
	
	private Image getImage(int index) {
		Image img=null;
		switch(index) {
		case 0:
			try {
				img = getImageWithPath("goodies/credits.png");
			} catch (IOException e) { e.printStackTrace();}
			break;
		}
		return img;
	}
	private Image getImageWithPath(String path) throws IOException {
		return ImageIO.read(new File("src/the/game/java/" + path));
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
}
