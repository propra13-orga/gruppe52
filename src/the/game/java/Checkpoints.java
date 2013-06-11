package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Checkpoints {
	private ImageIcon ii;
	private Image check = setImagePath("checkpoint.png");				
	private Image checkActivated = setImagePath("checkpoint_activated.png");	
	
	public static List<Checkpoints> checkList = new ArrayList<Checkpoints>(); 		// Checkpoints werden 'archiviert'

	int x=0;
	int y=0;
	static boolean isActivated;
	
	public Checkpoints(int posx, int posy){
		x=posx;
		y=posy;
		isActivated=false;
		DisplayManager.displayImage(check, x, y, "Checkpoint");
	}
	
	/*
	 *  erstellt in LevelCaller einen unaktivitierten Checkpoint
	 */
	public static void createCheckpoint(int posx, int posy) {
		posy += LevelCreator.distancePix;							// Wegen Menuleiste oben
		checkList.add(new Checkpoints(posx, posy));
	}
	
	/*
	 * Stellt Bild des Checkpoints dar
	 */
	public static void displayCheckpoint(){
		for(int i=0; i<checkList.size(); i++){			// so viele Checkpoint's existieren
			if(isActivated == true){
				// 							Bild jedes Checkpoint's		X-Position		  Y-Position		Tag	
				DisplayManager.removeChangeableImages("Checkpoint");
				DisplayManager.displayImage(checkList.get(i).checkActivated, checkList.get(i).x, checkList.get(i).y, "Checkpoint"); // Checkpoint ist der Tag - siehe Display Manager
				//DisplayManager.displayImage(checkList.get(i).checkActivated, checkList.get(i).x, checkList.get(i).y, "Checkpoint"); // Checkpoint ist der Tag - siehe Display Manager
			}else{
				
				
			}
		}
	}
	
	/*
	 *  Überprüft, ob der Spieler mit einem Checkpoint kollidiert
	 */
	public static boolean checkPlayerCollide(){
		// Jeder Spieler muss zusammen mit jedem Checkpoint überprüft werden
		for(int i=0; i<Player.playerList.size(); i++){
			for(int z=0; z<checkList.size(); z++){
				// wenn: x<=PlayerX+20 && PlayerX<=x+20		&&		y<=PlayerY+20 && PlayerY<=y+20
				// dh:   Player kollidiert vertikal					Player kollidiert horizontal
				if(Player.playerList.get(i).getX()+Player.playerList.get(i).imageSizeX/*-*/>=checkList.get(z).getX() && Player.playerList.get(i).getX()<=checkList.get(z).getX()+20/*-*/ && Player.playerList.get(i).getY()+Player.playerList.get(i).imageSizeX/*-*/>=checkList.get(z).getY() && Player.playerList.get(i).getY()<=checkList.get(z).getY()+20/*-*/){
					isActivated=true;
					displayCheckpoint();							// malt das Bild mit Text
				}
			}
		}
		return isActivated;
	}
	
    public Image setImagePath(String path) {						
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
    
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
}
