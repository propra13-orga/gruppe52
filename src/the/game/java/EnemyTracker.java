package the.game.java;

public class EnemyTracker extends Enemy {

	public EnemyTracker(double posx, double posy, String imgPathAl, String imgPathDe, int killMoney) {
		super(posx, posy, imgPathAl, imgPathDe, killMoney, "tracker");
		speed = 0.55;
	}
	
	protected void doBeforeMove() {
		// Spielerposition ermitteln
		int targetX = Player.playerList.get(0).getX();
		int targetY = Player.playerList.get(0).getY();
		
		// Laufvariablen ermitteln
		// x-Achse
		if(targetX-x>0)
			mx = 1;
		else if(targetX-x<0)
			mx = -1;
		else
			mx = 0;
		// y-Achse
		if(targetY-y>0)
			my = 1;
		else if(targetY-y<0)
			my = -1;
		else
			my = 0;
	}
}
