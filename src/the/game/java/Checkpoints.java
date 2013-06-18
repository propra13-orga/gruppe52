package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * In dieser Klasse werden Checkpoints alle n�tigen Eigenschaften zugewiesen.
 * Ein Checkpoint kann zwei zust�nde haben. Entweder er ist unaktiviert, oder aktiviert.
 * Wenn ein Checkpoint aktiviert ist und der Spieler stirbt, startet der Spieler an der position dieses Checkpoints.
 * Wenn kein Checkpoint nach der Level-Startposition aktiviert wurde, dann startet der Spieler am Levelanfang
 *
 */
public class Checkpoints {
	private ImageIcon ii;
	private Image check = DisplayManager.getImage("checkpoint.png");							// Bild eines unaktiviertem Checkpoints auf map				
	private Image checkActivated = DisplayManager.getImage("checkpoint_activated.png");		// Bild eines aktivierten Checkpoints auf map
	private int imgSizeX = check.getWidth(null);									// Breite des Checkpoints (wird ben�tigt um zu �berpr�fen, ob Player und Checkpoint kollidieren)
	private int imgSizeY = check.getHeight(null);									// H�he des Checkpoints (wird ben�tigt um zu �berpr�fen, ob Player und Checkpoint kollidieren)
	
	public static List<Checkpoints> checkList = new ArrayList<Checkpoints>(); 		// Checkpoints werden 'archiviert'

	private int x = 0;																// xPosition eines Checkpoints
	private int y = 0;																// yPosition eines Checkpoints
	private boolean isActivated;
	private String tag;
	private static int marker = 0;
	
	/*
	 * Konstruktor der Klasse Checkpoints. Wenn ein Objekt der Klasse Checkpoint erstellt wird, wird dieses an position (posx, posy) mit dem
	 * Bild eines unaktivierten Checkpoints dargestellt und die boolean-Variable isActivated wird vorerst auf false gesetzt
	 */
	public Checkpoints(int posx, int posy){
		x=posx;
		y=posy;
		isActivated=false;
		marker++;
		tag = "checkpoint" + marker;
		DisplayManager.displayImage(check, x, y, tag);
	}
	public Checkpoints(int posx, int posy, boolean activated){
		x=posx;
		y=posy;
		isActivated=activated;
		marker++;
		tag = "checkpoint" + marker;
		if(isActivated)
			DisplayManager.displayImage(checkActivated, x, y, tag);
		else
			DisplayManager.displayImage(check, x, y, tag);
	}
	
	/*
	 *  F�gt ein Objekt der Klasse Checkpoints der Liste aller Checkpoints hinzu
	 */
	public static void createCheckpoint(int posx, int posy) {
		posy += LevelCreator.distancePix;							// Wegen Menuleiste oben
		checkList.add(new Checkpoints(posx, posy));
	}
	public static void createCheckpoint(int posx, int posy, int isActivated) {
		posy += LevelCreator.distancePix;							// Wegen Menuleiste oben
		if(isActivated>0)
			checkList.add(new Checkpoints(posx, posy, true));
		else
			checkList.add(new Checkpoints(posx, posy, false));
	}
	
	/*
	 * Stellt Bild des Checkpoints dar, nachdem dieser aktiviert wurde.
	 * Die Startposition des Spielers wird auf die Position des Checkpoints ge�ndert
	 */
	public void doOnTouch(){
		if(isActivated==false){
			isActivated=true;													// Boolean isActivated = true
			Savegame.savegame();
			DisplayManager.removeChangeableImages(tag);				// alle Checkpoint-Bilder werden gel�scht
			DisplayManager.displayImage(checkActivated, x, y, tag); 	// Bild eines aktiviertem Checkpoints wird dargestellt
		}
	}
	
	/*
	 *  �berpr�ft, ob der Spieler mit einem Checkpoint kollidiert
	 */
	public static void checkPlayerCollideWithCheckpoint(int playerID){
		for(int z=0; z<checkList.size(); z++){									// Jedes Objekt der Checkpoint-Liste muss �berpr�ft werden
			// In der Klasse Intersect kann auf Kollision mit Player �berpr�ft werden. Folgende Parameter werden gebraucht:
			// player ID, xPosition des Checkpoints, yPosition des Checkpoints, Breite des Checkpoints, H�he des Checkpoints
			if(Intersect.isCollidingWithPlayer(playerID, checkList.get(z).getX(), checkList.get(z).getY(), checkList.get(z).imgSizeX, checkList.get(z).imgSizeY)) {
				checkList.get(z).doOnTouch();									// Wenn Kollision -> public void doOnTouch
			}						
		}
	}
	    
    public static void removeAllCheckpoints() {
    	for(int a=0; a<checkList.size(); a++) {
			DisplayManager.removeChangeableImages(checkList.get(a).tag);
		}
    	checkList.clear();
    }
    
    /*
     * gibt xPosition zur�ck
     */
    public int getX(){
    	return x;
    }
    
    /*
     * gibt yPosition zur�ck
     */
    public int getY(){
    	return y;
    }
    public int getIsActivated(){
    	if(isActivated)
    		return 1;
    	else
    		return 0;
    }
    
}
