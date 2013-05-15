package the.game.java;

public class Enemy {
	
	private static int x;
	private static int y;
	private static int mx=0;
	private static int my=0;
	//private static int counter=0;
	
	public Enemy(int posx, int posy) {
		x=posx;
		y=posy;
		mx=1;
	}
	public Enemy(int posx, int posy, int moveX, int moveY) {
		x = posx;
		y = posy;
		mx = moveX;
		my = moveY;
	}
	
	//public static void createMonster(int pointX, int pointY){
	//	Enemy[] en[counter] = new Enemy[5](pointX,pointY);
	//}
	
	public static void itemMapMonsterReset() {
		for(int a=0; a<(Runner.getWidthF()-(Runner.getWidthF()%20))/20; a++) {	// Spaltenweise
			for(int b=0; b<(Runner.getHeightF()-(Runner.getHeightF()%20))/20; b++) {
				if(LevelCreator.itemMap[a][b] == 3)
					LevelCreator.itemMap[a][b] = 0;
			}
		}
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static void move() {	// CHECK ENVIRONMENT EINBINDEN!
		int faktor=2;	// Setzt Geschwindigkeit
		if(LevelCreator.itemMap[(x+mx-((x+mx)%20))/20][(y+my-((y+my)%20))/20]<=0) {
			x += mx*faktor;
			y += my*faktor;
		} else {
			mx = mx * (-1);
		}
	}
	
}
