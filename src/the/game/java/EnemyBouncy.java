package the.game.java;

/**
 * Erzeugt einen Enemy vom Typ Bouncy
 * Erbt von Enemy
 */
public class EnemyBouncy extends Enemy {
	
	/**
	 * Konstruktor der Klasse EnemyBouncy
	 * Erzeugt ein Enemy mit folgenden Eigenschaften:
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param moveX Laufvariable in x-Richtung
	 * @param moveY Laufvariable in y-Richtung
	 * @param imgPathAl Pfad zum Bild eines lebendigen Enemys
	 * @param imgPathDe Pfad zum Bild eines besiegten Enemys
	 * @param killMoney Credits, die das Monster zurücklässt, nachdem es besiegt wurde
	 */
	public EnemyBouncy(double posx, double posy, double moveX, double moveY, String imgPathAl, String imgPathDe, int killMoney) {
		super(posx, posy, moveX, moveY, "enemies/bouncy.png", imgPathDe, killMoney, "bouncy");
		damageType = 2;
	}
	
	/**
	 * Regelt die Flummi-artige Bewegung eines Bouncys
	 */
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
