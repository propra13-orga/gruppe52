package the.game.java;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Player extends SpriteArmed {

	public static List<Player> playerList = new ArrayList<Player>();
	
    public static boolean showYouDiedImage = false;
    private BufferedImage imgAl, imgDe, currentImg;
    private static String imgAlPath="right.png", imgDePath="dead.png";
    public Score score;
    public Mana mana;
    
    
	public Player(double posx, double posy, String imgPathAl, String imgPathDe, int lifeCount) {
		super(posx, posy, imgPathAl, imgPathDe, lifeCount);
		
		score = new Score();
		mana = new Mana();
	}
	
	
	/********************************************************
     * 
     * CREATE PLAYER
     * 
    /********************************************************/
	
	public static void createPlayer() {
		//WeaponManager.createWeaponManager();
    	//ProjectileManager.createProjectileManager(playerList.size());
    	//Score.createScore();
    	playerList.add(new Player(0, 0, imgAlPath, imgDePath, 3));
    	setDisplayLives();							// Lebensanzeige wird aktualisiert
    	DisplayLine.setDisplay();
    	//Mana.createMana();
    	resetPlayerImages();
	}

	
	/********************************************************
     * 
     * MOVEMENT
     * 
    /********************************************************/
    
	public static void move() {
		for(int playerID=0; playerID<playerList.size(); playerID++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
    		playerList.get(playerID).doMove(playerID);
    	}
	}
	
	@Override
	protected void doAfterMove(int playerID) {	// wird nach bewegung ausgeführt (in doMove)
		checkCollideWithMiscObjekts(playerID);
		setDisplayLives();
    }
	
	
	/********************************************************
     * 
     * MOVEMENTPERMISSION AND COLLIDE
     * 
    /********************************************************/
	
	private static void checkCollideWithMiscObjekts(int playerID) {
		// Goodies
		for(int goodieID=0; goodieID<Goodies.goodiesList.size(); goodieID++) {
			Goodies.checkPlayerCollideWithGoodie(playerID, goodieID);
		}
		
		// Checkpoints
		Checkpoints.checkPlayerCollideWithCheckpoint(playerID);
		
		// Traps
		for(int trapID=0; trapID<Traps.trapList.size(); trapID++) {
			Traps.checkPlayerCollideWithTraps(playerID, trapID);
		}
		
		// NPCs
		NPC.checkPlayerCollide();
	}
	
	@Override
	protected void furtherInstPostSetMovemPerm() {
		LevelCaller.nextLevelAlready = false;
	}
	
	@Override
	protected boolean checkItemMapInstructions(double posx, double posy) {
    	switch(LevelCreator.itemMap[(int)posx][(int)posy]) {	// Geht alle möglichen gespeicherten Tags in dem Arraybereich durch und aktualisiert permission und ggf. 'alive'
    	case 1:					// Wall
    		return false;
    	case 4:					// Goal
			LevelCaller.setNextLevel();
    		break;
    	case 5:					// Final Goal
			LevelCaller.setFinalGoal();
    		break;
    	}
    	return true;
    }
	

	
	/********************************************************
     * 
     * LIFE, HP AND DEATH
     * 
    /********************************************************/
    
	@Override
	protected void furtherInstPostHealthReductionAndAlive() {
		setDisplayLives();
    }
	
	@Override
	protected void doOnDeath() {
		if(checkAllLivesGone()) {					// wenn alle Spieler tot sind, dann:
			LevelCaller.shutdownLevel();	// Löschen aller Levelparameter
    		Runner.shutRunnerDown();
			LostWindow.main(null);
		} else {
			if(checkAllDead()) {
				showYouDiedImage = true;	// Meldung wird angezeigt und es wird angeboten das Level neu zu starten
			}
		}
    }
	
	private static void setDisplayLives() {
    	DisplayLine.setDisplay();
    }
	
	private static boolean checkAllDead() {	// überprüft ob alle Spieler tot sind
		boolean allDead = true;		// Ausgangsposition
		for(int a=0; a<Player.playerList.size(); a++) {	// Überprüfen ob alle Spieler tot sind
			if(playerList.get(a).alive)			// wenn auch nur ein Spieler noch lebt, check=false
				allDead = false;
		}
    	return allDead;
    }
    
    private static boolean checkAllLivesGone() {	// überprüft ob alle Spieler ohne Leben sind
		boolean allLivesGones = true;		// Ausgangsposition
		for(int a=0; a<Player.playerList.size(); a++) {	// Überprüfen ob alle Spieler ohne Leben sind
			if(playerList.get(a).lives>0)			// wenn auch nur ein Spieler noch lebt, check=false
				allLivesGones = false;
		}
    	return allLivesGones;
    }
	
    public static void setYouDiedScreenStatus(boolean display) {
    	if(showYouDiedImage) {
    		if(display==false) {
    			showYouDiedImage=display;	// somit = false
    			resetLevel();
    		}
    	} else {
    		if(display) {
    			showYouDiedImage=display;	// somit = true
    		}
    	}	
    }
    
    
    /********************************************************
     * 
     * RESET LEVEL
     * 
    /********************************************************/
    
    private static void resetLevel() {
		for(int a=0; a<Player.playerList.size(); a++) {	// setzt alle spielerrelevanten Laufvariablen zurück
			if(playerList.get(a).lives>0) {				// nur zurücksetzen, wenn Spieler noch Leben hat
				playerList.get(a).healthPoints = playerList.get(a).healthPointsMax;
				playerList.get(a).alive = true;
				playerList.get(a).alreadyPunished = false;
			}
		}
		LevelCaller.resetLevel();
		setDisplayLives();
    }
	
	
    /********************************************************
     * 
     * DARSTELLUNG UND BILDER
     * 
    /********************************************************/
    
	@Override
    protected void prepareSpriteImg() {
    	imgSizeX = 15;
    	imgSizeY = 15;
    	imgAl = new BufferedImage(imgSizeX, imgSizeY, BufferedImage.TYPE_INT_RGB);
    	imgDe = new BufferedImage(imgSizeX, imgSizeY, BufferedImage.TYPE_INT_RGB);
    	currentImg = new BufferedImage(imgSizeX, imgSizeY, BufferedImage.TYPE_INT_RGB);
        try {
       		imgAl = ImageIO.read(new File("src/the/game/java/" + imgAlPath));	
       		imgDe = ImageIO.read(new File("src/the/game/java/" + imgDePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void rotatePlayerImg() {
		if(Double.isNaN(Controls.getAngle()))
    		return;
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getRotateInstance(
        		Math.toRadians(-Controls.getAngle()),
				(double)imgSizeX*0.5, 
				(double)imgSizeY*0.5), 
				AffineTransformOp.TYPE_BILINEAR);
        if(alive)
        	currentImg = op.filter(imgAl, null);
        else
        	currentImg = op.filter(imgDe, null);
	}
    
    public static void resetPlayerImages() {
    	for(int a=0; a<playerList.size(); a++) {
    		playerList.get(a).rotatePlayerImg();
    	}
    }
    
    
    /********************************************************
     * 
     * SETTER MOVEMENT
     * 
    /********************************************************/
    
    public static void setPlayerMovementUp(int playerID) {
		playerList.get(playerID).my = -1;
	}
	public static void setPlayerMovementRi(int playerID) {
		playerList.get(playerID).mx = 1;
	}
	public static void setPlayerMovementDo(int playerID) {
		playerList.get(playerID).my = 1;
	}
	public static void setPlayerMovementLe(int playerID) {
		playerList.get(playerID).mx = -1;
	}
	public static void setPlayerMovementX(int playerID, double moveX) {	// Für Steuerung
		playerList.get(playerID).mx = moveX;
    }
    public static void setPlayerMovementY(int playerID, double moveY) {	// Für Steuerung
    	playerList.get(playerID).my = moveY;
    }
    
    
    /********************************************************
     * 
     * GETTER DARSTELLUNG UND BILDER
     * 
    /********************************************************/
    
    @Override
    public BufferedImage getImg() {
    	return currentImg;
    }
    
    /********************************************************
     * 
     * GETTER PLAYER
     * 
    /********************************************************/
    
    public static Player getPlayer(int playerID) {
    	return playerList.get(playerID);
    }
    public static Mana getMana(int playerID) {
    	return playerList.get(playerID).mana;
    }
}
