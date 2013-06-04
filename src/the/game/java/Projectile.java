package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

// eventuell setDirection der Tracker mitverwenden falls Ziel gegeben

public class Projectile {		// steuert alle einzelnen Projektile und überprüft Treffer
	
	public static List<Projectile> projectileList = new ArrayList<Projectile>();
	private int x;
	private int y;
	private int speedx;
	private int speedy;
	private int damage;
	private int bulletSpread;
	private int currentSpread;
	private int currentSpreadDir;
	private int vigor;
	private boolean valid;
    public Image projectile;	// playericon
    private ImageIcon ii;
    private int imgSizeX;
    private int imgSizeY;
    private static int borderUp = Runner.borderUp;
    private static int borderDo = Runner.borderDo;
    private static int borderLe = Runner.borderLe;
    private static int borderRi = Runner.borderRi;
    
    private static Random random = new Random();
    
    // Bilder laden
    // TODO: Bilder hier vorladen, damit sie nicht jedesmal in displayManager neu geladen werden müssen
    
	
	private Projectile(int startx, int starty, int spdx, int spdy, int sDamage, int spread, Image imgProjectile) {
		valid = true;
		speedx = spdx;
		speedy = spdy;
		damage = sDamage;	// Schadenspunkte
		bulletSpread = spread;
		if(bulletSpread>0)
			setBulletSpread();	// verarbeitet bulletSpread, um es kompatibel zu machen
		currentSpread = 0;
		// virgor korrigieren ! Es werden pro Objekt mehrere Einheiten von virgor abgezogen (pro Überprüfung 1)!
		vigor = 1;		// Anzahl an Objekten, die durchschlagen werden können	// sollte besser aus Weapon abfragen	// TODO Durchschlagskraft
		// TODO bool hitanimation = true
		// TODO hitanimation festlegen
		// TODO bool friendlyfire = true
		projectile = imgProjectile;
		imgSizeX = projectile.getWidth(null);
		imgSizeY = projectile.getHeight(null);
		x = startx;
		y = starty - imgSizeY / 2;	// korrigiert zusätzlich den Startpunkt des Projektils
	}
	public static void createProjectile(int startx, int starty, int spdx, int spdy, int sDamage, int spread, Image imgProjectile) {
		projectileList.add(new Projectile(startx, starty, spdx, spdy, sDamage, spread, imgProjectile));
	}

    private Image setImage(String path) {
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
    

	
	public static void move() {
		int counter = 0;
		for(int a=0; a<projectileList.size(); a++) {
			if(projectileList.get(a).valid) {
				if(projectileList.get(a).checkCollideEnvironment()==false) {	// wenn etwas aus itemMap getroffen wird, nicht weiter machen
					if(projectileList.get(a).bulletSpread<=0) {										// Projektile ohne Spread
						projectileList.get(a).x += projectileList.get(a).speedx;						// Position X mit speed verrechnen
						projectileList.get(a).y += projectileList.get(a).speedy;						// Position Y mit speed verrechnen
					} else {																		// Projektile mit Spread
						projectileList.get(a).currentSpread++;											// Zähler +1
						if(projectileList.get(a).currentSpread>projectileList.get(a).bulletSpread) {	// Wenn Zähler > Spread, dann einmalige Richtungsabweichung
							projectileList.get(a).currentSpread = 0;									// Zähler zurücksetzen
							int generalSpread = 2;
							if(projectileList.get(a).currentSpreadDir<1) {								// Wenn Abweichung in Richtung 0:
								if(projectileList.get(a).speedx==0) {										// Wenn speedx 0:
									projectileList.get(a).x += generalSpread;											// Abweichung anpassen
									projectileList.get(a).y += projectileList.get(a).speedy;
								} else if(projectileList.get(a).speedy==0) {								// Wenn speedy 0:
									projectileList.get(a).y += generalSpread;											// Abweichung anpassen
									projectileList.get(a).x += projectileList.get(a).speedx;				// Position X mit speed verrechnen
								} else {																	// Wenn diagonal:
									if(projectileList.get(a).speedx>0)
										projectileList.get(a).x += projectileList.get(a).speedx + generalSpread+1;		// Abweichung anpassen (für positiven speed) (+1 da diagonal mehr Strecke zurückgelegt wird! Der SpreadWinkel würde dadurch sonst zu klein werden.
									else
										projectileList.get(a).x += projectileList.get(a).speedx - generalSpread+1;		// Abweichung anpassen (für negativen speed)
									projectileList.get(a).y += projectileList.get(a).speedy;			// Abweichung anpassen
								}
							} else {																	// Wenn Abweichung in Richtung 1:
								if(projectileList.get(a).speedx==0) {										// Wenn speedx 0:
									projectileList.get(a).x -= generalSpread;											// Abweichung anpassen
									projectileList.get(a).y += projectileList.get(a).speedy;
								} else if(projectileList.get(a).speedy==0) {								// Wenn speedy 0:
									projectileList.get(a).y -= generalSpread;											// Abweichung anpassen
									projectileList.get(a).x += projectileList.get(a).speedx;
								} else {																	// Wenn diagonal:
									if(projectileList.get(a).speedy>0)
										projectileList.get(a).y += projectileList.get(a).speedy + generalSpread+1;		// Abweichung anpassen (für positiven speed)
									else
										projectileList.get(a).y += projectileList.get(a).speedy - generalSpread+1;		// Abweichung anpassen (für negativen speed)
									projectileList.get(a).x += projectileList.get(a).speedx;				// Position X mit speed verrechnen
								}
							}								
						} else {																		// Wenn Zähler <= Spread, dann keine Abweichung
							projectileList.get(a).x += projectileList.get(a).speedx;						// Position X mit speed verrechnen
							projectileList.get(a).y += projectileList.get(a).speedy;						// Position Y mit speed verrechnen
						}
					}
					projectileList.get(a).checkCollideEnemy();
					counter++;
				}
			}
		}
		if(counter==0)				// Wenn Liste keine gültigen Projektile mehr enthält:
			projectileList.clear();	// Liste leeren
	}
	
	private void setBulletSpread() {
		if(bulletSpread>10)
			bulletSpread = 1;
		else
			bulletSpread = (10 - random.nextInt(bulletSpread));	// Zufallszahl von 0 bis (exklusive) bulletSpread
		currentSpreadDir = random.nextInt(2);					// Zufallszahl entscheidet Richtung für Spread
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isValid() {
		return valid;
	}
	
	private boolean checkCollideEnvironment() {
		boolean colliding = false;
		// Überprüft Border
		if(x<=borderLe || x>=borderRi || y<=borderUp || y>=borderDo) {
			colliding = true;
		}
		// Überprüfen in itemMap
		if(LevelCreator.getItemMapDataUsingPixels(x+speedx, y+speedy)>0) {
			colliding = true;
			DisplayManager.displayImage("hit_wall.png", x, y, 190);
		}
		// Wenn kollidiert, dann nicht mehr gültig
		if(colliding)
			valid = false;
		return colliding;
	}
	
	private void checkCollideEnemy() {
		// Überprüfen der Gegner
		for(int a=0; a<Enemy.monsterList.size(); a++) {	// Wird für alle Gegner nacheinander überprüft
			boolean colliding=false;
			// Positionen der Kanten initialisieren
			int mapPosLeft = x;
			int mapPosRight = x + imgSizeX;
			int mapPosUp = y;
			int mapPosDown = y + imgSizeY;
			int enemyMapPosLeft = Enemy.getX(a);
			int enemyMapPosRight = Enemy.getX(a) + Enemy.getImgSizeX(a);
			int enemyMapPosUp = Enemy.getY(a);
			int enemyMapPosDown = Enemy.getY(a) + Enemy.getImgSizeY(a);
			
			// Ecken überprüfen (Überprüft wird die "aktuelle" Position)
			// Es wird überprüft ob mind. eine der 4 Ecken des Projektiles innerhalb des von dem Gegner belegten Intervalls liegt
			// wenn ja, stirbt der Spieler
			if((enemyMapPosLeft<=mapPosLeft) && (mapPosLeft <= enemyMapPosRight) && (enemyMapPosUp <= mapPosUp) && (mapPosUp <= enemyMapPosDown))		// 1. Fixpunkt
				colliding = true;
			if((enemyMapPosLeft<=mapPosRight) && (mapPosRight <= enemyMapPosRight) && (enemyMapPosUp <= mapPosUp) && (mapPosUp <= enemyMapPosDown))		// 2. Oben rechts
				colliding = true;
			if((enemyMapPosLeft<=mapPosRight) && (mapPosRight <= enemyMapPosRight) && (enemyMapPosUp <= mapPosDown) && (mapPosDown <= enemyMapPosDown))	// 3. Unten rechts
				colliding = true;
			if((enemyMapPosLeft<=mapPosLeft) && (mapPosLeft <= enemyMapPosRight) && (enemyMapPosUp <= mapPosDown) && (mapPosDown <= enemyMapPosDown))	// 4. Unten links
				colliding = true;
			if((enemyMapPosLeft<=(mapPosLeft+(mapPosRight-mapPosLeft)/2)) && ((mapPosLeft+(mapPosRight-mapPosLeft)/2) <= enemyMapPosRight) && (enemyMapPosUp <= (mapPosUp+(mapPosDown-mapPosUp)/2)) && ((mapPosUp+(mapPosDown-mapPosUp)/2) <= enemyMapPosDown))		// 5. Mittelpunkt
				colliding = true;
	
			if(colliding) {										// Wenn Gegner mit Gegner kollidiert:
				Enemy.monsterList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
				// TODO: Blutposition korrigieren
				DisplayManager.displayImage("hit_blood.png", (enemyMapPosLeft-4+(enemyMapPosRight-enemyMapPosLeft)/2), (enemyMapPosUp-4+(enemyMapPosDown-enemyMapPosUp)/2), 190);
				vigor--;
				if(vigor<=0)
					valid = false;
			}
		}
		
		// Überprüfen der Tracker
		for(int a=0; a<Tracker.trackerList.size(); a++) {	// Wird für alle Tracker nacheinander überprüft
			boolean colliding=false;
			// Positionen der Kanten initialisieren
			int mapPosLeft = x;
			int mapPosRight = x + imgSizeX;
			int mapPosUp = y;
			int mapPosDown = y + imgSizeY;
			int enemyMapPosLeft = Tracker.getX(a);
			int enemyMapPosRight = Tracker.getX(a) + Tracker.getImgSizeX(a);
			int enemyMapPosUp = Tracker.getY(a);
			int enemyMapPosDown = Tracker.getY(a) + Enemy.getImgSizeY(a);
			
			// Ecken überprüfen (Überprüft wird die "aktuelle" Position)
			// Es wird überprüft ob mind. eine der 4 Ecken des Projektiles innerhalb des von dem Gegner belegten Intervalls liegt
			// wenn ja, stirbt der Spieler
			if((enemyMapPosLeft<=mapPosLeft) && (mapPosLeft <= enemyMapPosRight) && (enemyMapPosUp <= mapPosUp) && (mapPosUp <= enemyMapPosDown))		// 1. Fixpunkt
				colliding = true;
			if((enemyMapPosLeft<=mapPosRight) && (mapPosRight <= enemyMapPosRight) && (enemyMapPosUp <= mapPosUp) && (mapPosUp <= enemyMapPosDown))		// 2. Oben rechts
				colliding = true;
			if((enemyMapPosLeft<=mapPosRight) && (mapPosRight <= enemyMapPosRight) && (enemyMapPosUp <= mapPosDown) && (mapPosDown <= enemyMapPosDown))	// 3. Unten rechts
				colliding = true;
			if((enemyMapPosLeft<=mapPosLeft) && (mapPosLeft <= enemyMapPosRight) && (enemyMapPosUp <= mapPosDown) && (mapPosDown <= enemyMapPosDown))	// 4. Unten links
				colliding = true;
			if((enemyMapPosLeft<=(mapPosLeft+(mapPosRight-mapPosLeft)/2)) && ((mapPosLeft+(mapPosRight-mapPosLeft)/2) <= enemyMapPosRight) && (enemyMapPosUp <= (mapPosUp+(mapPosDown-mapPosUp)/2)) && ((mapPosUp+(mapPosDown-mapPosUp)/2) <= enemyMapPosDown))		// 5. Mittelpunkt
				colliding = true;
	
			if(colliding) {										// Wenn Gegner mit Gegner kollidiert:
				Tracker.trackerList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
				DisplayManager.displayImage("hit_blood.png", enemyMapPosLeft, enemyMapPosUp, 190);
				vigor--;
				if(vigor<=0)
					valid = false;
			}
		}
		// TODO Überprüfen der Player
	}
}
