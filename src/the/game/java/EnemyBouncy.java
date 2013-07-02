package the.game.java;

public class EnemyBouncy extends Enemy {

	public EnemyBouncy(double posx, double posy, double moveX, double moveY, String imgPathAl, String imgPathDe, int killMoney) {
		super(posx, posy, moveX, moveY, imgPathAl, imgPathDe, killMoney, "bouncy");
	}
	
	protected void doOnCollide() {
		if(permissionX==false && permissionY==false) {	// Beide Achsen versperrt
			mx *= -1;										// Beide invertieren
			my *= -1;
		} else if(permissionX==false)					// X-Achse versperrt (vertikale Wand)
			mx *= -1;										// X-Achse invertieren
		else if(permissionY==false)						// Y-Achse versperrt (horizontale Wand)
			my *= -1;										// Y-Achse invertieren
	}
}
