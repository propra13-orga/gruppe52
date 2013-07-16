package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Sprite{

	public static List<Enemy> enemyList = new ArrayList<Enemy>();
	public static List<EnemySimple> simpleList = new ArrayList<EnemySimple>();
	public static List<EnemyBouncy> bouncyList = new ArrayList<EnemyBouncy>();
	public static List<EnemyTracker> trackerList = new ArrayList<EnemyTracker>();
	private String type;
	private int credit;
	
	public Enemy(double posx, double posy, double moveX, double moveY, String imgPathAl, String imgPathDe, int killMoney, String typ) {
		super(posx, posy, imgPathAl, imgPathDe);
		
		mx = moveX;
		my = moveY;
		damage = 45;
		credit = killMoney;
		type = typ;
		enemyList.add(this);
	}
	
	public Enemy(double posx, double posy, String imgPathAl, String imgPathDe, int killMoney, String typ) {
		super(posx, posy, imgPathAl, imgPathDe);
		
		damage = 45;
		credit = killMoney;
		type = typ;
		enemyList.add(this);
	}
	
	public static void createEnemy(double pointX, double pointY, double moveX, double moveY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		simpleList.add(new EnemySimple(pointX, pointY, moveX, moveY, "enemy.gif", "trap.png", 20));
	}
	public static void createEnemy(double pointX, double pointY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		simpleList.add(new EnemySimple(pointX, pointY, Runner.random.nextInt(2), Runner.random.nextInt(2), "enemy.gif", "trap.png", 20));
	}
	
	public static void createBouncy(double pointX, double pointY, double moveX, double moveY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		bouncyList.add(new EnemyBouncy(pointX, pointY, moveX, moveY, "enemy.gif", "trap.png", 20));
	}
	public static void createBouncy(double pointX, double pointY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		bouncyList.add(new EnemyBouncy(pointX, pointY, Runner.random.nextInt(2), Runner.random.nextInt(2), "enemy.gif", "trap.png", 20));
	}
	
	public static void createTracker(double pointX, double pointY) {
		pointY += LevelCreator.distancePix;	// Wegen Menuleiste oben
		trackerList.add(new EnemyTracker(pointX, pointY, "enemy.gif", "trap.png", 20));
	}
	
	public static void clearAllLists() {
		enemyList.clear();
		simpleList.clear();
		bouncyList.clear();
		trackerList.clear();
	}
	
	
	
	public static void move() {
		if(TemporaryItem.flagEnemyFreezed)
			return;
		
		for(int a=0; a<simpleList.size(); a++) {
			simpleList.get(a).doMove(a);
		}
		for(int a=0; a<bouncyList.size(); a++) {
			bouncyList.get(a).doMove(a);
		}
		for(int a=0; a<trackerList.size(); a++) {
			trackerList.get(a).doMove(a);
		}
	}
	
	protected void doOnDeath() {
		Goodies.createCredits((int)x, (int)y, credit);
	}
	
	protected void furtherInstPostSetMovemPerm() {
		doBeforeMove();
		if(permissionX==false || permissionY==false)
			doOnCollide();
    }
	
	/**
	 * checkCollideOnMiscObjekts überprüft immer an einen Punkt an einer Ecke, daher Höhe/Breite =1
	 * Eckposition ist den Parametern zu entnehmen
	 */
	protected boolean checkCollideOnMiscObjekts(double posx, double posy) {	// Wenn true, dann nicht passierbares Objekt gefunden
		for(int a=0; a<Traps.trapList.size(); a++) {
			if(Intersect.isCollidingWithTrap(a, (int)posx, (int)posy+1, 1, 1)) {	//posy+1 um festhängen zu vermeiden
				return true;
			}
		}
		return false;
    }
	
	protected void doBeforeMove() {
		
	}

	protected void doOnCollide() {
		
	}
	
	protected void furtherInstPostMove(int enemyID) {	// wird nach bewegung ausgeführt (in doMove)
		// Check Player Collide
		for(int playerID=0; playerID<Player.playerList.size(); playerID++) {
			
			if(Intersect.isCollidingWithPlayer(playerID, getX(), getY(), imgSizeX, imgSizeY)) {										// Wenn Gegner mit Spieler kollidiert:
				Player.playerList.get(playerID).reduceHealthPoints(damage, damageType);	// Lebenspunkte abziehen -> wenn keine mehr übrig Spieler tot
			}
		}
    }
	
	public static int getX(int enemyID) {
		return (int)enemyList.get(enemyID).x;
	}
	public static int getY(int enemyID) {
		return (int)enemyList.get(enemyID).y;
	}
	public static int getImgSizeX(int enemyID) {
		return enemyList.get(enemyID).imgSizeX;
	}
	public static int getImgSizeY(int enemyID) {
		return enemyList.get(enemyID).imgSizeY;
	}
	public static boolean isAlive(int enemyID) {
		return enemyList.get(enemyID).alive;
	}
	public static Image getImg(int enemyID) {
		return enemyList.get(enemyID).currentImg;
	}
	public static double getMX(int enemyID) {
		return enemyList.get(enemyID).mx;
	}
	public static double getMY(int enemyID) {
		return enemyList.get(enemyID).my;
	}
	public static String getType(int enemyID) {
		return enemyList.get(enemyID).type;
	}
	public static Enemy getEnemy(int enemyID) {
		return enemyList.get(enemyID);
	}

}
