package the.game.java;

public class EnemySimple extends Enemy {

	public EnemySimple(double posx, double posy, double moveX, double moveY, String imgPathAl, String imgPathDe, int killMoney) {
		super(posx, posy, moveX, moveY, imgPathAl, imgPathDe, killMoney, "enemy");
	}
	
	protected void doOnCollide() {
		mx *= -1;
    	my *= -1;
	}
}
