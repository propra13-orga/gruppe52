package the.game.java;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Regelt alles, was den Player betrifft.
 * Dh Bewegung, Permissions usw.
 */
public class Player extends SpriteArmed {

	public static List<Player> playerList = new ArrayList<Player>();
	
    public static boolean showYouDiedImage = false;
    private BufferedImage imgAl, imgDe, currentImg;
    private static String imgAlPath="right.png", imgDePath="dead.png";
    public Score score;
    public Mana mana;
    private boolean remote = false;
    public int remoteID = 0;
    private int playerID = 0;
    private static int playerIDCounter = 0;
    private int teamID = -1;
    public double angle = 0; // Füe Netzwerk
    private static long timeStampRespawn = 0;
    
    /**
     * Konstruktor (einfach) der Klasse Player
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Players
	 * @param imgPathDe Pfad zum Bild eines besiegten Players
     * @param lifeCount Lebenspunkte
     */
	public Player(double posx, double posy, String imgPathAl, String imgPathDe, int lifeCount) {
		super(posx, posy, imgPathAl, imgPathDe, lifeCount);
		
		score = new Score();
		mana = new Mana();
		
		if(Settings.isMultiplayer()) {
			if(playerList.size()>0)
				remote = true;
		}
		
		playerID = playerIDCounter;
		playerIDCounter++;
	}
	/**
	 * Konstruktor (komplex) der Klasse Player
	 * @param posx x-Position
	 * @param posy y-Position
	 * @param imgPathAl Pfad zum Bild eines lebendigen Players
	 * @param imgPathDe Pfad zum Bild eines besiegten Players
     * @param lifeCount Lebenspunkte
	 * @param remoted true: Spieler aus Netzwerk, false: 'echter' spieler
	 * @param remoteid ID des remoted players
	 */
	public Player(double posx, double posy, String imgPathAl, String imgPathDe, int lifeCount, boolean remoted, int remoteid) {
		super(posx, posy, imgPathAl, imgPathDe, lifeCount);
		
		score = new Score();
		mana = new Mana();
		
		remote = remoted;
		remoteID = remoteid;
		
		playerID = playerIDCounter;
		playerIDCounter++;
	}
	
	
	/********************************************************
     * 
     * CREATE PLAYER
     * 
    /********************************************************/
	
	/**
	 * ERzeugt einen Player(1)
	 */
	public static void createPlayer() {
    	playerList.add(new Player(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY(), imgAlPath, imgDePath, 3));
    	setDisplayLives();							// Lebensanzeige wird aktualisiert
    	DisplayLine.setDisplay();
    	resetPlayerImages();
	}
	/**
	 * Erzeugt einen Player(2)
	 * @param remoted true: Spieler aus Netzwerk, false: 'echter' spieler
	 * @param remoteid ID des remoted players
	 */
	public static void createPlayer(boolean remoted, int remoteID) {
    	playerList.add(new Player(LevelCaller.getPlayerDefaultPosX(), LevelCaller.getPlayerDefaultPosY(), imgAlPath, imgDePath, 3, remoted, remoteID));
    	setDisplayLives();							// Lebensanzeige wird aktualisiert
    	DisplayLine.setDisplay();
    	resetPlayerImages();
	}
	/**
	 * Erzeugt einen Player(3)
	 * @param x x-Position
	 * @param y y-Position
	 */
	public static void createPlayer(double x, double y) {
    	playerList.add(new Player(x, y, imgAlPath, imgDePath, 3));
    	setDisplayLives();							// Lebensanzeige wird aktualisiert
    	DisplayLine.setDisplay();
    	resetPlayerImages();
	}

	
	/********************************************************
     * 
     * MOVEMENT
     * 
    /********************************************************/
    
	/**
	 * Sorgt für die Bewegung des Players
	 */
	public static void move() {
		if(Shop.show)
			return;
		for(int playerID=0; playerID<playerList.size(); playerID++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
    		playerList.get(playerID).doMove(playerID);
    	}
	}
	
	@Override
	/**
	 * Überprüft Kollision und deren Folgen
	 * Wird nach Bewegung ausgeführt
	 */
	protected void doAfterMove(int playerID) {	// wird nach bewegung ausgeführt (in doMove)
		checkCollideWithMiscObjekts(playerID);
		setDisplayLives();
    }
	
	
	/********************************************************
     * 
     * MOVEMENTPERMISSION AND COLLIDE
     * 
    /********************************************************/
	
	/**
	 * Prüft ob Player mit anderen Objekten kollidiert
	 * @param playerID ID des Players
	 */
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
		
		//Door
		for(int goalID=0; goalID<Door.doorList.size(); goalID++){
			Door.doorList.get(goalID).checkPlayerCollideWithDoor(playerID);
		}
	}
	
	@Override
	/**
	 * Auf goal wird nur ein Level weiter gesetzt
	 */
	protected void furtherInstPostSetMovemPerm() {
		LevelCaller.nextLevelAlready = false;
	}
	
	@Override
	/**
	 * Geht alle möglichen gespeicherten Tags in dem Arraybereich durch und aktualisiert permission und ggf. 'alive'
	 */
	protected boolean checkItemMapInstructions(double posx, double posy) {
		switch(LevelCreator.itemMap[(int)posx][(int)posy]) {	// Geht alle möglichen gespeicherten Tags in dem Arraybereich durch und aktualisiert permission und ggf. 'alive'
    	case 1:					// Wall
    		return false;
    	case 3:					// Goal (Team1) (default)
			LevelCaller.setNextLevel();
    		break;
    	case 5:					// Final Goal
			LevelCaller.setFinalGoal();
    		break;
    	default:
    		if(LevelCreator.itemMap[(int)posx][(int)posy]==50+teamID) {
    			for(int a=0; a<Player.playerList.size(); a++) {
    				if(getPlayer(a).remote==false && getPlayer(a).teamID==teamID)
    					getPlayer(a).score.setScore(4000);
    					NetHandler.nextLevel = true;
    			}
    			LevelCaller.setNextLevel();
    		}
    	}
		
    	return true;
    }
	

	
	/********************************************************
     * 
     * LIFE, HP AND DEATH
     * 
    /********************************************************/
    
	@Override
	/**
	 * Verringert Leben um 1
	 */
	protected void decrementLive() {
    	if(Settings.isMultiplayer()==false)	// Wenn kein Multiplayer:
			lives--;						// Leben -1
    }
	
	@Override
	/**
	 * Aktualisiert die DisplayLives
	 */
	protected void furtherInstPostHealthReductionAndAlive() {
		setDisplayLives();
    }
	
	@Override
	/**
	 * Sorgt für den Effekt, den der Tod eines Players hat
	 */
	protected void doOnDeath() {
		// Multiplayer
		if(Settings.isMultiplayer()) {
			showYouDiedImage = true;
			timeStampRespawn = System.currentTimeMillis();
			rotatePlayerImg();
			NetHandler.updateRequired = true;
		}
		// Singleplayer
		else {
			if(checkAllLivesGone()) {					// wenn alle Spieler tot sind, dann:
				LevelCaller.shutdownLevel();	// Löschen aller Levelparameter
	    		Runner.shutRunnerDown();
				LostWindow.main(null);
			} else {
				showYouDiedImage = true;	// Meldung wird angezeigt und es wird angeboten das Level neu zu starten
			}
		}
    }
	
	/**
	 * Sorgt für rotiertes Player-Bild
	 */
	protected void furtherInstPostLifeStatusUpdateViaNetwork(boolean isAlive) {
    	rotatePlayerImg();
    }
	/**
	 * Legt die DisplayLives fest
	 */
	private static void setDisplayLives() {
    	DisplayLine.setDisplay();
    }
	/**
	 * Prüft ob alle Spieler Tod sind
	 * @return true: alle tot, false: mind. einer lebendig
	 */
	private static boolean checkAllDead() {	// überprüft ob alle Spieler tot sind
		boolean allDead = true;		// Ausgangsposition
		for(int a=0; a<Player.playerList.size(); a++) {	// Überprüfen ob alle Spieler tot sind
			if(playerList.get(a).alive)			// wenn auch nur ein Spieler noch lebt, check=false
				allDead = false;
		}
    	return allDead;
    }
    /**
     * Prüft ob der Spieler noch mind 1 leben hat
     * @return true: keine Leben mehr, false: mind. 1 Leben
     */
    private static boolean checkAllLivesGone() {	// überprüft ob alle Spieler ohne Leben sind
		boolean allLivesGones = true;		// Ausgangsposition
		for(int a=0; a<Player.playerList.size(); a++) {	// Überprüfen ob alle Spieler ohne Leben sind
			if(playerList.get(a).lives>0)			// wenn auch nur ein Spieler noch lebt, check=false
				allLivesGones = false;
		}
    	return allLivesGones;
    }
	/**
	 * Zeigt den "YouDied" Screen an
	 * @param display
	 */
    public static void setYouDiedScreenStatus(boolean display) {
    	if(showYouDiedImage) {
    		if(display==false) {
    			// Multiplayer
    			if(Settings.isMultiplayer()) {
    				if(System.currentTimeMillis() - timeStampRespawn >= 5000) {	// 5 Sekunden Wartezeit
        				showYouDiedImage=display;	// somit = false
        				resetLevel();
        			}
    			}
    			// Singleplayer
    			else {
    				showYouDiedImage=display;	// somit = false
    				resetLevel();
    			}
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
    
    /**
     * Resettet das Level
     */
    private static void resetLevel() {
    	resetAllPlayerPositionsToDefault();	// NUR FÜR MULTIPLAYER
		for(int a=0; a<Player.playerList.size(); a++) {	// setzt alle spielerrelevanten Laufvariablen zurück
			if(playerList.get(a).lives>0) {				// nur zurücksetzen, wenn Spieler noch Leben hat
				playerList.get(a).healthPoints = playerList.get(a).healthPointsMax;
				playerList.get(a).alive = true;
				playerList.get(a).alreadyPunished = false;
			}
		}
		if(Settings.isMultiplayer()==false) {
			LevelCaller.resetLevel();
			setDisplayLives();
		} else {
			resetPlayerImages();
			NetHandler.updateRequired = true;
		}
    }
    
    /**
     * Setzt alle Player an die Startposition
     */
    public static void resetAllPlayerPositionsToDefault() {
    	if(Settings.isMultiplayer()) {
    		for(int playerID=0; playerID<playerList.size(); playerID++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
        		if(playerList.get(playerID).alive==false) {
        			// Zurücksetzen
        			if(LevelCaller.spawnMap.containsKey(playerList.get(playerID).teamID)) {
        				playerList.get(playerID).setPosition(LevelCaller.spawnMap.get(playerList.get(playerID).teamID).x, 
        					LevelCaller.spawnMap.get(playerList.get(playerID).teamID).y);
        			}
        		}
        	}
    	}
	}
	
	
    /********************************************************
     * 
     * DARSTELLUNG UND BILDER
     * 
    /********************************************************/
    
	@Override
	/**
	 * Legt die PlayerBilder an
	 */
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
    /**
     * Rotiert das Bild mit Bewegung der Maus
     */
    public void rotatePlayerImg() {
    	if(alive) {
	    	if(remote==false || Settings.isMultiplayer()==false) {
	    		if(Double.isNaN(Controls.getAngle()))
	        		return;
	    		else
	    			angle = Controls.getAngle();
	    	}
			
	        AffineTransformOp op = new AffineTransformOp(AffineTransform.getRotateInstance(
	        		Math.toRadians(-angle),
					(double)imgSizeX*0.5, 
					(double)imgSizeY*0.5), 
					AffineTransformOp.TYPE_BILINEAR);
	        
        	currentImg = op.filter(imgAl, null);
        } else
        	currentImg = imgDe;
	}
    /**
     * resettet alle Bilder der Spieler
     */
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
    
    /**
     * Player Bewegung: oben
     * @param playerID ID des Players
     */
    public static void setPlayerMovementUp(int playerID) {
		playerList.get(playerID).my = -1;
	}
    /**
     * Player Bewegung: rechts
     * @param playerID ID des Players
     */
	public static void setPlayerMovementRi(int playerID) {
		playerList.get(playerID).mx = 1;
	}
    /**
     * Player Bewegung: unten
     * @param playerID ID des Players
     */
	public static void setPlayerMovementDo(int playerID) {
		playerList.get(playerID).my = 1;
	}
    /**
     * Player Bewegung: links
     * @param playerID ID des Players
     */
	public static void setPlayerMovementLe(int playerID) {
		playerList.get(playerID).mx = -1;
	}
	/**
	 * Für Steuerung: Movenemt Variable nach x
	 * @param playerID ID des Players
	 * @param moveX Laufvariable in x-Richtung
	 */
	public static void setPlayerMovementX(int playerID, double moveX) {	// Für Steuerung
		playerList.get(playerID).mx = moveX;
    }
	/**
	 * Für Steuerung: Movenemt Variable nach y
	 * @param playerID ID des Players
	 * @param moveX Laufvariable in y-Richtung
	 */
    public static void setPlayerMovementY(int playerID, double moveY) {	// Für Steuerung
    	playerList.get(playerID).my = moveY;
    }
    
    
    /********************************************************
     * 
     * GETTER DARSTELLUNG UND BILDER
     * 
    /********************************************************/
    
    @Override
    /**
     * Gibt das current Image zurück
     */
    public BufferedImage getImg() {
    	return currentImg;
    }
    
    /********************************************************
     * 
     * GETTER PLAYER
     * 
    /********************************************************/
    
    /**
     * Gibt den Player zurück
     * @param playerID ID des Players
     * @return Player
     */
    public static Player getPlayer(int playerID) {
    	return playerList.get(playerID);
    }
    /**
     * Gibt die eingesetzte Mana zurück
     * @param playerID ID des Players
     * @return mana
     */
    public static Mana getMana(int playerID) {
    	return playerList.get(playerID).mana;
    }
    
    /********************************************************
     * 
     * GETTER NETWORK
     * 
    /********************************************************/
    
    /**
     * Gibt zurück, ob Spieler aus dem Netzwerk kommt
     * @return true: aus Netzwerk, false: 'echter' Player
     */
    public boolean isRemote() {
    	return remote;
    }
    /**
     * Gibt die ID des Teams zurück
     * @return teamID
     */
    public int getTeamID() {
    	return teamID;
    }
    
    /********************************************************
     * 
     * SETTER NETWORK
     * 
    /********************************************************/
    
    /**
     * Legt die ID des Teams fest
     * @param val
     */
    public void setTeamID(int val) {
    	teamID = val;
    }
}
