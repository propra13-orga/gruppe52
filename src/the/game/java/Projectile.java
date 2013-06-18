package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// eventuell setDirection der Tracker mitverwenden falls Ziel gegeben

public class Projectile {		// steuert alle einzelnen Projektile und �berpr�ft Treffer
	
	public static List<Projectile> projectileList = new ArrayList<Projectile>();
	public List<String> alreadyList = new ArrayList<String>();
	private double x;
	private double y;
	private int damage;
	private int bulletSpread;
	private int vigor;
	private boolean valid;
    public Image projectile;
    private int imgSizeX;
    private int imgSizeY;
    private String origin;
    private static int borderUp = Runner.borderUp;
    private static int borderDo = Runner.borderDo;
    private static int borderLe = Runner.borderLe;
    private static int borderRi = Runner.borderRi;
    
    private double mx;
    private double my;
    private int speed;
	private int angle;
	
	private Image hitWall = DisplayManager.getImage("hit_wall.png");
	private Image hitBlood = DisplayManager.getImage("hit_blood.png");
	private Image hitColorRed = DisplayManager.getImage("hit_color_red.png");
	private Image hitColorPink = DisplayManager.getImage("hit_color_pink.png");
	private Image hitColorYellow = DisplayManager.getImage("hit_color_yellow.png");
	private Image hitColorBlue = DisplayManager.getImage("hit_color_blue.png");
	private Image hitColorGreen = DisplayManager.getImage("hit_color_green.png");
    
    public static Random random = new Random();
    
    /**     KONSTRUKTOR     */
	private Projectile(int startx, int starty, int bulletSpeed, int bulletDamage, int bulletVigor, int spread, Image imgProjectile, int bulletAngle, String bulletOrigin) {
		valid = true;							// G�ltigkeit
		speed = bulletSpeed;					// Geschwindikeit (Multiplikator)
		damage = bulletDamage;					// Schadenspunkte
		vigor = bulletVigor;					// Anzahl an Objekten, die durchschlagen werden k�nnen
		origin = bulletOrigin;					// Tag f�r Herkunft um FriendlyFire auszuschlie�en
		
		projectile = imgProjectile;				// Projektilbild
		imgSizeX = projectile.getWidth(null);	// Projektilbreite
		imgSizeY = projectile.getHeight(null);	// Projektilh�he
		
		x = startx - imgSizeX / 2;				// Startposition x
		y = starty - imgSizeY / 2;				// Startposition y		
		angle = bulletAngle;					// Flugrichtung in Grad (0-359)
		bulletSpread = spread;					// Spread
		if(bulletSpread>0)						// Wenn Spread vorhanden:
			setBulletSpread();					// Flugrichtung mit Spread verrechnen
		
		mx = Math.cos(angle*Math.PI/180) * speed;			// X-Steigung aus der Gradzahl berechnen: (Winkel*Pi/180) um Grad in Bogenma� umzurechnen und f�r cos() kompatibel zu machen
		my = Math.sin(angle*Math.PI/180) * (-1) * speed;	// Y-Steigung aus der Gradzahl berechnen: (Winkel*Pi/180) um Grad in Bogenma� umzurechnen und f�r sin() kompatibel zu machen, *(-1) da y-Achse verkehrt herum
	}
	// TODO bool hitanimation = true
	// TODO hitanimation festlegen
	// TODO bool friendlyfire = true

	private Image getHitImage(){
		int randomColor = random.nextInt(5);// = (int) (Math.random()*10);
		switch (randomColor){
		case 0:
			return hitColorRed;
		case 1:
			return hitColorPink;
		case 2:
			return hitColorBlue;
		case 3:
			return hitColorYellow;
		case 4:
			return hitColorGreen;
		default:
			return hitBlood;
		}
	}
	
	/**     PROJEKTIL ERZEUGEN     */
	public static void createProjectile(int startx, int starty, int speed, int sDamage, int sVigor, int spread, Image imgProjectile, int angle, String bulletOrigin) {
		projectileList.add(new Projectile(startx, starty, speed, sDamage, sVigor, spread, imgProjectile, angle, bulletOrigin));
	}
	
	/**     MOVE-METHODEN     */
	public static void move() {
		int counter = 0;								// Projektil-Z�hler
		for(int a=0; a<projectileList.size(); a++) {	// Alle Projektile in der Liste durchgehen
			counter += projectileList.get(a).doMove();				// Ausgew�hltes Projektil bewegen und bei Erfolg den Z�hler inkrementieren
		}
		
		if(counter==0 && projectileList.size()>0)		// Wenn Projektile vorhanden, aber keines mehr G�ltig ist:
			projectileList.clear();						// Liste leeren
	}

	private int doMove() {
		if(valid) {
			if(checkCollideEnvironment()==false) {	// wenn etwas aus itemMap getroffen wird, nicht weiter machen
				
				x += mx;				// x-Position verrechnen
				y += my;				// y-Position verrechnen
				checkCollide();			// �berpr�fen, ob irgendetwas mit sterbliches getroffen wurde
			}
			return 1;
		}
		return 0;
	}
	
	/**     SPREAD     */
	private void setBulletSpread() {
		// Falsche Werte abfangen
		if(bulletSpread>360)
			bulletSpread = 360;
		else if(bulletSpread<0)
			bulletSpread = 1;
		
		// Spread bestimmen
		if(random.nextInt(2)>0) {					// Richtung entscheiden (Zahl 1 oder 0)
			angle += random.nextInt(bulletSpread);	// Winkel mit Spread verrechnen
		} else {
			angle -= random.nextInt(bulletSpread);	// Winkel mit Spread verrechnen
		}
			
	}
	
	/**     COLLIDE-METHODEN     */
	private boolean checkCollideEnvironment() {
		boolean colliding = false;
		// �berpr�ft Border
		if(x<=borderLe || x>=borderRi || y<=borderUp || y>=borderDo) {
			colliding = true;
		}
		// �berpr�fen in itemMap
		if(LevelCreator.getItemMapDataUsingPixels((int)(x+mx), (int)(y+my))>0) {
			colliding = true;
			DisplayManager.displayImage(hitWall, getX(), getY(), 190);
		}
		// Wenn kollidiert, dann nicht mehr g�ltig
		if(colliding)
			valid = false;
		return colliding;
	}
	
	private void checkCollide() {
		// �berpr�fen der Gegner
		if(origin!="enemy" && valid) {
			for(int a=0; a<Enemy.monsterList.size(); a++) {	// Wird f�r alle Gegner nacheinander �berpr�ft
				if(Enemy.isAlive(a) && isHitAlreadyReported("enemy" + a)==false) {
					boolean colliding = Intersect.isCollidingWithEnemy(a, getX(), getY(), imgSizeX, imgSizeY);
					
					if(colliding) {										// Wenn Gegner mit Gegner kollidiert:
						int enemyMapPosLeft = Enemy.getX(a);
						int enemyMapPosRight = Enemy.getX(a) + Enemy.getImgSizeX(a);
						int enemyMapPosUp = Enemy.getY(a);
						int enemyMapPosDown = Enemy.getY(a) + Enemy.getImgSizeY(a);
						
						Enemy.monsterList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
						
						// TODO: Blutposition korrigieren
						DisplayManager.displayImage(getHitImage(), (enemyMapPosLeft-4+(enemyMapPosRight-enemyMapPosLeft)/2), (enemyMapPosUp-4+(enemyMapPosDown-enemyMapPosUp)/2), 190);
						vigor--;
						if(vigor<=0)
							valid = false;
						else
							addHitReport("enemy" + a);
					}
				}
			}
		}
		
		// �berpr�fen der Tracker
		if(origin!="tracker" && valid) {
			for(int a=0; a<Tracker.trackerList.size(); a++) {	// Wird f�r alle Tracker nacheinander �berpr�ft
				if(Tracker.isAlive(a) && isHitAlreadyReported("tracker" + a)==false) {
					boolean colliding = Intersect.isCollidingWithTracker(a, getX(), getY(), imgSizeX, imgSizeY);
					
					if(colliding) {										// Wenn Gegner mit Gegner kollidiert:
						int enemyMapPosLeft = Tracker.getX(a);
						//int enemyMapPosRight = Tracker.getX(a) + Tracker.getImgSizeX(a);
						int enemyMapPosUp = Tracker.getY(a);
						//int enemyMapPosDown = Tracker.getY(a) + Enemy.getImgSizeY(a);
						
						Tracker.trackerList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
						DisplayManager.displayImage(getHitImage(), enemyMapPosLeft, enemyMapPosUp, 190);
						vigor--;
						if(vigor<=0)
							valid = false;
						else
							addHitReport("tracker" + a);
					}
				}
			}
		}
		
		// �berpr�fen der Player
		if(origin!="player" && valid) {
			for(int a=0; a<Player.playerList.size(); a++) {	// Wird f�r alle Spieler nacheinander �berpr�ft
				
				if(isHitAlreadyReported("player" + a)==false) {
					boolean colliding = Intersect.isCollidingWithPlayer(a, getX(), getY(), imgSizeX, imgSizeY);
					
					if(colliding) {										// Wenn Gegner mit Gegner kollidiert:				
						Player.playerList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
						DisplayManager.displayImage(getHitImage(), Player.playerList.get(a).getX(), Player.playerList.get(a).getY(), 190);
						vigor--;
						if(vigor<=0)
							valid = false;
						else
							addHitReport("player" + a);
					}
				}
			}
		}
		
		// �berpr�fen der Traps
		if(origin!="trap" && valid) {
			for(int a=0; a<Traps.trapList.size(); a++) {	// Wird f�r alle Spieler nacheinander �berpr�ft
				if(Traps.getType(a)>0 && Traps.getValid(a) && isHitAlreadyReported("traps" + a)==false) {	// Minen sind somit ausgeschlossen und alle Traps, die nicht mehr g�ltig sind
					boolean colliding = Intersect.isCollidingWithTrap(a, getX(), getY(), imgSizeX, imgSizeY);
					
					if(colliding) {										// Wenn Gegner mit Gegner kollidiert:				
						Traps.trapList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
						DisplayManager.displayImage(getHitImage(), Traps.getX(a), Traps.getY(a), 190);
						vigor--;
						if(vigor<=0)
							valid = false;
						else
							addHitReport("trap" + a);
					}
				}
			}
		}
	}
	
	private void addHitReport(String tag) {
		alreadyList.add(tag);
	}
	
	private boolean isHitAlreadyReported(String tag) {	// �berpr�ft, ob Objekt bereits getroffen wurde, um doppelten Schadensabzug zu verhindern
		for(int a=0; a<alreadyList.size(); a++) {
			if(alreadyList.get(a).equals(tag))
				return true;
		}
		return false;
	}
	
	/**     REQUESTS     */
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	public boolean isValid() {
		return valid;
	}
	
}
