package the.game.java;

/**
 * Erzeugt einem simplen Enemy mit stetig gleicher Bewegung
 * Erbt von Enemy
 */
public class EnemySimple extends Enemy {

	/**
	 * Konstruktor der Klasse EnemySimple
	 * Erzeugt ein Enemy mit folgenden Eigenschaften:
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param moveX Laufvariable in x-Richtung
	 * @param moveY Laufvariable in y-Richtung
	 * @param imgPathAl Pfad zum Bild eines lebendigen Enemys
	 * @param imgPathDe Pfad zum Bild eines besiegten Enemys
	 * @param killMoney Credits, die das Monster zurücklässt, nachdem es besiegt wurde
	 */
	public EnemySimple(double posx, double posy, double moveX, double moveY, String imgPathAl, String imgPathDe, int killMoney) {
		super(posx, posy, moveX, moveY, "enemies/simple.png", imgPathDe, killMoney, "enemy");
		damageType = 1;
	}
	
	/**
	 * Legt fest, wie der Enemy sich verhalten soll, nachdem er auf eine Wand getroffen ist
	 */
	protected void doOnCollide() {
		mx *= -1;
    	my *= -1;
	}
}
