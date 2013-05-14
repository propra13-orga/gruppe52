package the.game.java;

public class Enemy {
	
	private static int x;
	private static int y;
	private static int mx=0;
	private static int my=0;
	private static int counter=0;
	
	public Enemy(int posx, int posy) {
		x=posx;
		y=posy;
		LevelCreator.createMonster(x, y);
		mx=1;
	}
	
	private static void itemMapMonsteReset() {
		for(int a=0; a<(Runner.getWidthF()-(Runner.getWidthF()%20))/20; a++) {	// Spaltenweise
			for(int b=0; b<(Runner.getHeightF()-(Runner.getHeightF()%20))/20; b++) {
				if(LevelCreator.itemMap[a][b] == 3)
					LevelCreator.itemMap[a][b] = 0;
			}
		}
	}
	
	public static void move() {
		counter++;
		if(counter>20) {
				counter=0;
			if(LevelCreator.itemMap[x+mx][y+my]<=0) {
				x += mx;
				y += my;
				//itemMapMonsteReset();
				itemMapMonsteReset();
				LevelCreator.createMonster(x, y);
			} else {
				mx = mx * (-1);
			}
		}
	}
	
}
