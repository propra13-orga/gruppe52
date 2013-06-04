package level.creator.java;

import java.awt.event.KeyEvent;


public class Current {
/**
 *  In dieser Klasse wird alles verwaltet, was von dem aktuell gewählten Feld im Editor abhängt.
 */
	int currentX=4;																// aktuelle Position der Auswahl
	int currentY=101;
	
	int moveX;																	// Bewegung der Auswahl
	int moveY;
	
	/**
	 * Die aktuelle x-Position wird geändert	
	 * @param chosenx = aktuelle x-Position in Pixeln. Beachte: immer +4 wegen Fensterrand
	 */
	public void setCurrentX(int chosenx){										
		currentX=chosenx;
	}
	/**
	 * Die aktuelle y-Position wird geändert
	 * @param choseny = aktuelle y-Position in Pixeln. Beachte: immer +101 wegen Fensterrand and Überschrift
	 */
	public void setCurrentY(int choseny){
		currentY=choseny;
	}
	/** 
	 * currentX und currentY können zurückgegeben werden
	 */
	public int getCurrentX(){
		return currentX;
	}
	
	public int getCurrentY(){
		return currentY;
	}
	
	int imageType=0;
	/**
	 * Der Typ (bzw der Wert im Array an aktueller Position) kann mit type festgelegt werden
	 * @param type = der jeweilige Item-Map-Typ. Jedes Objekt (zB Wand, Leben) bekommt einen integer Wert um den Typ festzulegen. Einzelheiten siehe unten.
	 */
	public void setLoadingImage(int type){
		imageType=type;
		Editor.map[(currentX-4)/20][(currentY-101)/20]=imageType;
	}
	/**
	 *  gibt den imageType zurück
	 */
	public int getLoadingImage(){
		return imageType;
	}
	
	
	/**
	 * 	Key Bindings
	 */
	
	public void keyPressend(KeyEvent event){
		int key = event.getKeyCode();
	    
		switch(key){
		case KeyEvent.VK_LEFT:						// <
	    	moveX = -1;								// MoveVariable x-Achse (gibt an wie viele Felder (20 Pixel) sich die Auswahl bewegt)
	    	Editor.stabilisation++;
	    	break;
		case KeyEvent.VK_RIGHT:						// >
	        moveX = 1;								// MoveVariable x-Achse
	        Editor.stabilisation++;
	        break;
		case KeyEvent.VK_UP:						// ^
	        moveY = -1;								// MoveVariable y-Achse
	        Editor.stabilisation++;
	        break;
		case KeyEvent.VK_DOWN:						// v
	        moveY = 1;								// MoveVariable y-Achse
	        Editor.stabilisation++;
	        break;
		}
	}
	
	public void keyReleased(KeyEvent event){
		int key = event.getKeyCode();

		switch(key){
		case KeyEvent.VK_LEFT:						// <
	    	moveX = 0;								// MoveVariable x-Achse (gibt an wie viele Felder (20 Pixel) sich die Auswahl bewegt)
	    	Editor.stabilisation = 1;
	    	break;
		case KeyEvent.VK_RIGHT:
	        moveX = 0;								// MoveVariable x-Achse
	        Editor.stabilisation = 1;
	        break;
		case KeyEvent.VK_UP:
	        moveY = 0;								// MoveVariable y-Achse
	        Editor.stabilisation = 1;
	        break;
		case KeyEvent.VK_DOWN:
	        moveY = 0;								// MoveVariable y-Achse
	        Editor.stabilisation = 1;
	        break;
		case KeyEvent.VK_1:
			setLoadingImage(1);			// :== Wand
			break;
		case KeyEvent.VK_2:
			setLoadingImage(2);			// :== Falle
			break;
		case KeyEvent.VK_3:				
			setLoadingImage(3);			// :== Leben
			break;
		case KeyEvent.VK_4:
			setLoadingImage(4);			// :== Ziel
			break;
		case KeyEvent.VK_5:
			setLoadingImage(5);			// :== Rüstung
			break;
		/*case KeyEvent.VK_6:
			setLoadingImage(6);			// :== Zaubertrank
			break;*/
		case KeyEvent.VK_Q:
			setLoadingImage(11);		// :== horizontales Monster
			break;
		case KeyEvent.VK_W:
			setLoadingImage(12);		// :== vertikales Monster
			break;
		case KeyEvent.VK_E:
			setLoadingImage(13);		// :== right-up Monster
			break;
		case KeyEvent.VK_R:
			setLoadingImage(14);		// :== left-up Monster
			break;
		case KeyEvent.VK_T:
			setLoadingImage(15);		// :== Bouncy
			break;
		case KeyEvent.VK_Z:
			setLoadingImage(16);		// :== Tracker
			break;
		}
		
	}
	/**
	 * Legt die Bewegung der aktuellen Auswahl fest
	 */
	public void move(){
		currentX=currentX+moveX*20;
		currentY=currentY+moveY*20;
	}
	
	
	
	
	
	

}
