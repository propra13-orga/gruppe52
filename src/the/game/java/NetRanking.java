package the.game.java;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class NetRanking {

	public static boolean show = false;
	private static int fixPointX;
	private static int fixPointY;
	private static int frameWidth;
	private static int frameHeight;
	private static String imageTag;
	public static Map<Integer,Integer> scoreMap = new HashMap<Integer,Integer>();
	
	protected static void run() {
		if(Settings.isMultiplayer()) {
			show = true;
			ini();
			setShopDisplay();
		}
	}
	
	private static void ini() {
		imageTag = "ranking";
		frameWidth = 560;
		frameHeight = 320;
		fixPointX = (Runner.getWidthF() / 2) - (frameWidth / 2);
		fixPointY = (Runner.getHeightF() / 2) - (frameHeight / 2);
	}
	
	private static void setShopDisplay() {
		int counter = 0;
		if(show) {
			DisplayManager.displayImage("transparentLayer.png", 0, 0, imageTag, true);
			DisplayManager.displayImage("shop/shopBackground.png", fixPointX-5, fixPointY-5, imageTag, true);
			DisplayManager.displayImage("misc/ranking.png", fixPointX+20, fixPointY+20, imageTag, true);
			for(int a=0; a<Player.playerList.size(); a++) {
				if(scoreMap.containsKey(a)) {
					DisplayManager.displayString("Spieler "+a+":", fixPointX + 50, fixPointY + 75 + counter*18, imageTag);
					DisplayManager.displayString(String.valueOf(scoreMap.get(a)), fixPointX + 200, fixPointY + 75 + counter*18, imageTag);
					counter++;
				}
			}
		}
	}
	
	protected static void close() {
		show = false;
		DisplayManager.removeChangeableImages(imageTag);
		DisplayManager.removeChangeableStrings(imageTag);
	}
}
