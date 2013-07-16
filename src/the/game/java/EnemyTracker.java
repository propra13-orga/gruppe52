package the.game.java;

/**
 * Erzeugt einen Enemy vom Typ Tracker
 * Erbt von Enemy
 */
public class EnemyTracker extends Enemy {

	/**
	 * Konstruktor der Klasse EnemyTracker
	 * Erzeugt ein Enemy mit folgenden Eigenschaften:
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Enemys
	 * @param imgPathDe Pfad zum Bild eines besiegten Enemys
	 * @param killMoney Credits, die das Monster zurücklässt, nachdem es besiegt wurde
	 */
	public EnemyTracker(double posx, double posy, String imgPathAl, String imgPathDe, int killMoney) {
		super(posx, posy, "enemies/tracker.png", imgPathDe, killMoney, "tracker");
		speed = 0.55;
		damageType = 3;
	}
	
	/**
	 * Bestimmt die Laufrichtung des Trackers durch bestimmen der Spielerposition
	 */
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
