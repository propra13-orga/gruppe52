package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// eventuell setDirection der Tracker mitverwenden falls Ziel gegeben

public class Projectile {		// steuert alle einzelnen Projektile und überprüft Treffer
	
	public static List<Projectile> projectileList = new ArrayList<Projectile>();
	private int x;
	private int y;
	//private int speedx;
	//private int speedy;
	private int damage;
	private int bulletSpread;
	//private int currentSpread;
	//private int currentSpreadDir;
	private int vigor;
	private boolean valid;
    public Image projectile;	// playericon
    private int imgSizeX;
    private int imgSizeY;
    private static int borderUp = Runner.borderUp;
    private static int borderDo = Runner.borderDo;
    private static int borderLe = Runner.borderLe;
    private static int borderRi = Runner.borderRi;
    
    private double gradient;
    private boolean xIsRelevantAxis;
    private boolean isRight;
    private boolean isUp;
    private int mx;
    private int my;
    private double gradientCounter;
    private int speed;
    private boolean directAxisFlag;
	private int currentSpreadDir;
	private int currentSpreadCounter;
    
    private static Random random = new Random();
    
    // Bilder laden
    // TODO: Bilder hier vorladen, damit sie nicht jedesmal in displayManager neu geladen werden müssen
    
    /*
    private Projectile(int startx, int starty, int spdx, int spdy, int sDamage, int sVigor, int spread, Image imgProjectile) {
		valid = true;
		speedx = spdx;
		speedy = spdy;
		damage = sDamage;	// Schadenspunkte
		bulletSpread = spread;
		if(bulletSpread>0)
			setBulletSpread();	// verarbeitet bulletSpread, um es kompatibel zu machen
		currentSpread = 0;
		// virgor korrigieren ! Es werden pro Objekt mehrere Einheiten von virgor abgezogen (pro Überprüfung 1)!
		vigor = sVigor;		// Anzahl an Objekten, die durchschlagen werden können	// sollte besser aus Weapon abfragen	// TODO Durchschlagskraft
		// TODO bool hitanimation = true
		// TODO hitanimation festlegen
		// TODO bool friendlyfire = true
		projectile = imgProjectile;
		imgSizeX = projectile.getWidth(null);
		imgSizeY = projectile.getHeight(null);
		x = startx;
		y = starty - imgSizeY / 2;	// korrigiert zusätzlich den Startpunkt des Projektils
	}
    */
    
	private Projectile(int startx, int starty, int bulletSpeed, int bulletDamage, int bulletVigor, int spread, Image imgProjectile, int gradX, int gradY) {
		valid = true;
		speed = bulletSpeed;
		damage = bulletDamage;	// Schadenspunkte
		vigor = bulletVigor;		// Anzahl an Objekten, die durchschlagen werden können	// sollte besser aus Weapon abfragen	// TODO Durchschlagskraft
		
		projectile = imgProjectile;
		imgSizeX = projectile.getWidth(null);
		imgSizeY = projectile.getHeight(null);
		
		x = startx;
		y = starty - imgSizeY / 2;	// korrigiert zusätzlich den Startpunkt des Projektils
		
		gradient = getGradient(gradX, gradY);
		mx = 0;
		my = 0;
		initializeMovementVars();
		
		bulletSpread = spread;
		if(bulletSpread>0)
			setBulletSpread();
		
		gradientCounter = 0;
	}
	// TODO bool hitanimation = true
	// TODO hitanimation festlegen
	// TODO bool friendlyfire = true
	
	/*
	public static void createProjectile(int startx, int starty, int spdx, int spdy, int sDamage, int sVigor, int spread, Image imgProjectile) {
		projectileList.add(new Projectile(startx, starty, spdx, spdy, sDamage, sVigor, spread, imgProjectile));
	}
	*/
	public static void createProjectile(int startx, int starty, int speed, int sDamage, int sVigor, int spread, Image imgProjectile, int dirX, int dirY) {
		projectileList.add(new Projectile(startx, starty, speed, sDamage, sVigor, spread, imgProjectile, dirX, dirY));
	}

	/*
    private Image setImage(String path) {
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
    */
	
	public static void move() {
		int counter = 0;
		for(int a=0; a<projectileList.size(); a++) {
			projectileList.get(a).doMove();
			counter++;
		}
		
		if(counter==0 && projectileList.size()>0)				// Wenn Liste keine gültigen Projektile mehr enthält:
			projectileList.clear();	// Liste leeren
	}

	
	private void doMove() {
		if(valid) {
			if(checkCollideEnvironment()==false) {	// wenn etwas aus itemMap getroffen wird, nicht weiter machen
				
				updateMovementVars();	// MoveVariablen aktualisieren
				updatePosition();		// Berechnet neue Position (mit Spread!)	
				checkCollide();			// Überprüfen, ob irgendetwas mit sterbliches getroffen wurde
			}
		}
	}
	
	private void updatePosition() {
		if(bulletSpread<=0) {									// Projektile ohne Spread
			x += mx;												// Position X mit speed verrechnen
			y += my;												// Position Y mit speed verrechnen
		} else {												// Projektile mit Spread
			currentSpreadCounter++;									// Zähler +1
			if(currentSpreadCounter>bulletSpread) {					// Wenn Zähler > Spread, dann einmalige Richtungsabweichung
				currentSpreadCounter = 0;							// Zähler zurücksetzen
				int generalSpread = 2;
				if(currentSpreadDir<1) {						// Wenn Abweichung in Richtung 0:
					if(mx==0) {										// Wenn mx 0:
						x += generalSpread;							// Abweichung anpassen
						y += my;
					} else if(my==0) {								// Wenn my 0:
						y += generalSpread;							// Abweichung anpassen
						x += mx;									// Position X mit speed verrechnen
					} else {										// Wenn diagonal:
						if(mx>0)
							x += mx + generalSpread+1;				// Abweichung anpassen (für positiven speed) (+1 da diagonal mehr Strecke zurückgelegt wird! Der SpreadWinkel würde dadurch sonst zu klein werden.
						else
							x += mx - generalSpread+1;				// Abweichung anpassen (für negativen speed)
						y += my;									// Abweichung anpassen
					}
				} else {										// Wenn Abweichung in Richtung 1:
					if(mx==0) {										// Wenn mx 0:
						x -= generalSpread;							// Abweichung anpassen
						y += my;
					} else if(my==0) {								// Wenn my 0:
						y -= generalSpread;							// Abweichung anpassen
						x += mx;
					} else {										// Wenn diagonal:
						if(my>0)
							y += my + generalSpread+1;				// Abweichung anpassen (für positiven speed)
						else
							y += my - generalSpread+1;				// Abweichung anpassen (für negativen speed)
						x += mx;									// Position X mit speed verrechnen
					}
				}								
			} else {																		// Wenn Zähler <= Spread, dann keine Abweichung
				x += mx;						// Position X mit speed verrechnen
				y += my;						// Position Y mit speed verrechnen
			}
		}
	}
	
	private void updateMovementVars() {
		//Laufrichtung
		if(directAxisFlag==false) {
			gradientCounter++; //(EHEMALS)
			//gradientCounter += (double)speed;	// TODO: Anpassen, so dass die Abstände nicht = speed sind pro änderung
			
			if(isRight) {					// RECHTS
				if(xIsRelevantAxis) {		// X IST HAUPTACHSE
					
					if(isUp) {			// UP (right)
						if(gradientCounter>=gradient*(-1)) {
							my = (-1) * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {
							my = 0;
						}
					} else {									// DOWN (right)
						if(gradientCounter>=gradient) {
							my = 1 * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {
							my = 0;
						}
					}
					
				} else {											// Y IST HAUPTACHSE
					
					if(isUp) {			// UP (right)
						if(gradientCounter>=gradient*(-1)) {
							mx = 1 * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {
							mx = 0;
						}
					} else {									// DOWN (right)
						if(gradientCounter>=gradient) {
							mx = 1 * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {
							mx = 0;
						}
					}
					
				}
				
			} else {											// LINKS
				if(xIsRelevantAxis) {			// X IST HAUPTACHSE
					
					if(isUp) {			// UP (left)
						if(gradientCounter>=gradient) {
							my = (-1) * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {
							my = 0;
						}
					} else {									// DOWN (left)
						if(gradientCounter>=gradient*(-1)) {
							my = 1 * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {
							my = 0;
						}
					}
					
				} else {											// Y IST HAUPTACHSE
					
					if(isUp) {			// UP (left)
						if(gradientCounter>=gradient) {
							mx = (-1) * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {	
							mx = 0;
						}
					} else {									// DOWN (left)
						if(gradientCounter>=gradient*(-1)) {
							mx = (-1) * speed;
							gradientCounter -= Math.abs(gradientCounter);
						} else {	
							mx = 0;
						}
					}
					
				}
			}
		}
	}
	
	private void initializeMovementVars() {
		//Laufrichtung
		if(directAxisFlag) {
			
			if(isUp)
				my = (-1) * speed;
			else
				my = 1 * speed;
			
			if(isRight)
				mx = 1 * speed;
			else
				mx = (-1) * speed;
			
		} else {
			gradientCounter++;
			if(isRight) {				// RECHTS
				if(xIsRelevantAxis) {		// X IST HAUPTACHSE
					
					if(isUp) {				// UP (right)
						mx = 1 * speed;						
						my = 0;
					} else {				// DOWN (right)
						mx = 1 * speed;
						my = 0;
					}
				} else {					// Y IST HAUPTACHSE
					if(isUp) {				// UP (right)
						my = (-1) * speed;
						mx = 0;
					} else {				// DOWN (right)
						my = 1 * speed;
						mx = 0;
					}
				}
			} else {					// LINKS
				if(xIsRelevantAxis) {		// X IST HAUPTACHSE
					
					if(isUp) {				// UP (left)
						mx = (-1) * speed;
						my = 0;
					} else {				// DOWN (left)
						mx = (-1) * speed;
						my = 0;
					}
				} else {					// Y IST HAUPTACHSE
					if(isUp) {				// UP (left)
						my = (-1) * speed;
						mx = 0;
					} else {				// DOWN (left)
						my = 1 * speed;
						mx = 0;
					}
				}
			}
		}
	}
	
	
	
	private void setBulletSpread() {
		if(bulletSpread>10)
			bulletSpread = 1;
		else
			bulletSpread = (10 - random.nextInt(bulletSpread));	// Zufallszahl von 0 bis (exklusive) bulletSpread
		currentSpreadDir = random.nextInt(2);					// Zufallszahl entscheidet Richtung für Spread
	}
	
	/*
	private double setBulletSpread() {
		double spread = 1;
		
		if(bulletSpread>=100)
			bulletSpread = 99;
		
		if(bulletSpread>0) {
			//bulletSpread = (random.nextInt(bulletSpread));		// Zufallszahl von 0 bis (exklusive) bulletSpread
			
			bulletSpread = 100 - random.nextInt(bulletSpread);
				
			spread = (double)bulletSpread * 0.01;
		
			if(random.nextInt(2)<=0)
				spread++;
			else
				spread = 1 - spread;
		}
		return spread;
	}
	*/
	
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
		if(LevelCreator.getItemMapDataUsingPixels(x+mx, y+my)>0) {
			colliding = true;
			DisplayManager.displayImage("hit_wall.png", x, y, 190);
		}
		// Wenn kollidiert, dann nicht mehr gültig
		if(colliding)
			valid = false;
		return colliding;
	}
	
	private void checkCollide() {
		// Überprüfen der Gegner
		for(int a=0; a<Enemy.monsterList.size(); a++) {	// Wird für alle Gegner nacheinander überprüft
			
			boolean colliding = Intersect.isCollidingWithEnemy(a, x, y, imgSizeX, imgSizeY);
			
			if(colliding) {										// Wenn Gegner mit Gegner kollidiert:
				int enemyMapPosLeft = Enemy.getX(a);
				int enemyMapPosRight = Enemy.getX(a) + Enemy.getImgSizeX(a);
				int enemyMapPosUp = Enemy.getY(a);
				int enemyMapPosDown = Enemy.getY(a) + Enemy.getImgSizeY(a);
				
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
			
			boolean colliding = Intersect.isCollidingWithTracker(a, x, y, imgSizeX, imgSizeY);
			
			if(colliding) {										// Wenn Gegner mit Gegner kollidiert:
				int enemyMapPosLeft = Tracker.getX(a);
				//int enemyMapPosRight = Tracker.getX(a) + Tracker.getImgSizeX(a);
				int enemyMapPosUp = Tracker.getY(a);
				//int enemyMapPosDown = Tracker.getY(a) + Enemy.getImgSizeY(a);
				
				Tracker.trackerList.get(a).reduceHealthPoints(damage);	// Healthpoints - Schaden
				DisplayManager.displayImage("hit_blood.png", enemyMapPosLeft, enemyMapPosUp, 190);
				vigor--;
				if(vigor<=0)
					valid = false;
			}
		}
		// TODO Überprüfen der Player
		
	}
	
	private double getGradient(double dirx, double diry) {
		
		double gradient = 0;
		xIsRelevantAxis = false;
		if(dirx==0 || diry==0) {
			directAxisFlag = true;
		} else {
			gradient = diry / dirx;					// Steigungsberechnung (y/x)
			if(gradient<1 && gradient>(-1)) {		// Steigungsberechnung (x/y)
				gradient = dirx / diry;	
				xIsRelevantAxis = true;
			}
		}
		
		if(dirx<0)
			isRight = false;
		else
			isRight = true;
		if(diry<0)
			isUp = true;
		else
			isUp = false;
		
		System.out.println(gradient);
		
		return gradient;
	}
}
