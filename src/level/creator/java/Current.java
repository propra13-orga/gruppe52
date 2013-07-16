package level.creator.java;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *  In dieser Klasse wird alles verwaltet, was von dem aktuell gewählten Feld im Editor abhängt.
 */
public class Current {
	public static int currentXPixel=4;																// aktuelle Position der Auswahl
	public static int currentYPixel=101;
	
	static int defaultX=4;
	static int defaultY=101;
	
	int currentXHilf;
	int currentYHilf;
	
	int firstDoorX;
	int firstDoorY;
	
	static boolean doorChooserActivated = false;
	
	public static List<Current> currentListX = new ArrayList<Current>();
	public static List<Current> currentListY = new ArrayList<Current>();
	
	int moveX=0;																	// Bewegung der Auswahl
	int moveY=0;
	
	boolean startAlreadyExists=false;
	
	/**
	 * Die aktuelle x-Position wird geändert	
	 * @param chosenx = aktuelle x-Position in Pixeln. Beachte: immer +4 wegen Fensterrand
	 */
	public void setcurrentXPixel(int chosenx){										
		currentXPixel=chosenx;
	}
	/**
	 * Die aktuelle y-Position wird geändert
	 * @param choseny = aktuelle y-Position in Pixeln. Beachte: immer +101 wegen Fensterrand and Überschrift
	 */
	public void setcurrentYPixel(int choseny){
		currentYPixel=choseny;
	}
	/**
	 * currentXPixel wird zurückgegeben
	 * @return currentXPixel
	 */
	public int getcurrentXPixel(){
		return currentXPixel;
	}
	/**
	 * currentYPixel wird zurückgegeben
	 * @return currentYPixel
	 */
	public int getcurrentYPixel(){
		return currentYPixel;
	}
	
	int imageType=0;
	/**
	 * Der Typ (bzw der Wert im Array an aktueller Position) kann mit type festgelegt werden
	 * @param type = der jeweilige Item-Map-Typ. Jedes Objekt (zB Wand, Leben) bekommt einen integer Wert um den Typ festzulegen. Einzelheiten siehe unten.
	 */
	public void setLoadingImage(int type){
		imageType=type;
		//if(0<=(currentXPixel-4)/20 && (currentXPixel-4)/20<=48 && 0<=(currentYPixel-101)/20 && (currentYPixel-101)/20<=25){
			EditorSetter.map[(currentXPixel-4)/20][(currentYPixel-101)/20]=imageType;
		//}
	}
	/**
	 * gibt den imageType zurück
	 * @return imageType
	 */
	public int getLoadingImage(){
		return imageType;
	}
	
	
	/**
	 * 	Key Bindings
	 */
	/**
	 * KeyBinding : Pressed
	 * @param event
	 */
	public void keyPressend(KeyEvent event){
		int key = event.getKeyCode();
	    //System.err.println("HEEE HIER BIN ICH !!");
		switch(key){
		case KeyEvent.VK_LEFT:						// <
	    	moveX = -1;								// MoveVariable x-Achse (gibt an wie viele Felder (20 Pixel) sich die Auswahl bewegt)
	    	EditorSetter.stabilisation++;
	    	break;
		case KeyEvent.VK_RIGHT:						// >
	        moveX = 1;								// MoveVariable x-Achse
	        EditorSetter.stabilisation++;
	        break;
		case KeyEvent.VK_UP:						// ^
	        moveY = -1;								// MoveVariable y-Achse
	        EditorSetter.stabilisation++;
	        break;
		case KeyEvent.VK_DOWN:						// v
	        moveY = 1;								// MoveVariable y-Achse
	        EditorSetter.stabilisation++;
	        break;
	        
		}
	}
	/**
	 * KeyBinding : Released
	 * @param event
	 */
	public void keyReleased(KeyEvent event){
		int key = event.getKeyCode();

		switch(key){
		case KeyEvent.VK_ENTER:
			if(doorChooserActivated==false){
				System.out.println("DoorChooser ist deaktiviert");
				currentXHilf = ((currentXPixel-defaultX)-((currentXPixel-defaultX)%20))/20;
				currentYHilf = ((currentYPixel-defaultY)-((currentXPixel-defaultX)%20))/20;
				if(EditorSetter.map[currentXHilf][currentYHilf]==20 || EditorSetter.map[currentXHilf][currentYHilf]==21 || EditorSetter.map[currentXHilf][currentYHilf]==22 || EditorSetter.map[currentXHilf][currentYHilf]==23){
					System.out.println("Zahl entspricht einer Tür");
					doorChooserActivated=true;
					//Door.createDoor(currentXPixel, currentYPixel, -1, -1);
					firstDoorX=currentXPixel-defaultX;
					firstDoorY=currentYPixel-defaultY;
					System.out.println("Du verbindest Tür in " +currentXPixel+ "/" +currentYPixel);
				}else{
					System.out.println("Zahl entspricht NICHT einer Tür, sondern: "+EditorSetter.map[currentXHilf][currentYHilf]);
				}
			}else{
				System.out.println("DoorChooser ist aktiviert");
				currentXHilf = ((currentXPixel-defaultX)-((currentXPixel-defaultX)%20))/20;
				currentYHilf = ((currentYPixel-defaultY)-((currentXPixel-defaultX)%20))/20;
				if(EditorSetter.map[currentXHilf][currentYHilf]==20 || EditorSetter.map[currentXHilf][currentYHilf]==21 || EditorSetter.map[currentXHilf][currentYHilf]==22 || EditorSetter.map[currentXHilf][currentYHilf]==23){
					System.out.println("Zahl entspricht einer Tür");
					if(firstDoorX!=currentXPixel || firstDoorY!=currentYPixel){
						System.out.println("Türen sind unterschiedlich");
						Door.createDoor(firstDoorX, firstDoorY, currentXPixel-defaultX, currentYPixel-defaultY);
						System.out.println("mit Tür in " +currentXPixel+ "/" +currentYPixel);
					}else{
						System.out.println("Türen sind gleich:" + firstDoorX +"/"+ currentXPixel +" "+ firstDoorY +"/"+ currentYPixel );
					}
					doorChooserActivated=false; //TODO: richtig?
				}

			}
			break;
		case KeyEvent.VK_ESCAPE:
			EditorSetter.currentList.clear();
			break;
		case KeyEvent.VK_DELETE:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					EditorSetter.map[(int)EditorSetter.currentList.get(y).getX()][(int)EditorSetter.currentList.get(y).getY()]=0;
				}
				EditorSetter.currentList.clear();
			}
		case KeyEvent.VK_LEFT:						// <
	    	moveX = 0;								// MoveVariable x-Achse (gibt an wie viele Felder (20 Pixel) sich die Auswahl bewegt)
	    	EditorSetter.stabilisation = 1;
	    	break;
		case KeyEvent.VK_RIGHT:
	        moveX = 0;								// MoveVariable x-Achse
	        EditorSetter.stabilisation = 1;
	        break;
		case KeyEvent.VK_UP:
	        moveY = 0;								// MoveVariable y-Achse
	        EditorSetter.stabilisation = 1;
	        break;
		case KeyEvent.VK_DOWN:
	        moveY = 0;								// MoveVariable y-Achse
	        EditorSetter.stabilisation = 1;
	        break;
		case KeyEvent.VK_1:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(1);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(1);			// :== Wand
			break;
		case KeyEvent.VK_2:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(2);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(2);			// :== Credit
			break;
		case KeyEvent.VK_3:	
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(3);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(3);			// :== Leben
			break;
		case KeyEvent.VK_4:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(4);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(4);			// :== Ziel
			break;
		case KeyEvent.VK_5:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(5);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(5);			// :== Rüstung
			break;
		case KeyEvent.VK_6:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(6);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(6);			// :== Zaubertrank
			break;
		case KeyEvent.VK_7:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(7);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(7);
			break;
		case KeyEvent.VK_8:
			if(startAlreadyExists==false){
				setLoadingImage(8);
				startAlreadyExists=true;
			}else{
				System.out.println("Wie willst du denn bitte an zwei Stellen gleichzeitig starten?");
			}
			break;
		case KeyEvent.VK_9:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(9);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(9);			// :== Rüstung
			break;
		case KeyEvent.VK_Q:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(11);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(11);		// :== horizontales Monster
			break;
		case KeyEvent.VK_W:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(12);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(12);		// :== vertikales Monster
			break;
		case KeyEvent.VK_E:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(13);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(13);		// :== right-up Monster
			break;
		case KeyEvent.VK_R:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(14);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(14);		// :== left-up Monster
			break;
		case KeyEvent.VK_T:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(15);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(15);		// :== Bouncy
			break;
		case KeyEvent.VK_Z:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(16);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(16);		// :== Tracker
			break;
		case KeyEvent.VK_U:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(17);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(17);		// :== Tracker
			break;
		case KeyEvent.VK_I:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(18);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(18);			// :== Rüstung
			break;
		case KeyEvent.VK_A:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(20);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(20);			// :== Door
			break;
		case KeyEvent.VK_S:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(22);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(22);			// :== Door
			break;
		case KeyEvent.VK_D:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(21);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(21);			// :== Door
			break;
		case KeyEvent.VK_F:
			if(!EditorSetter.currentList.isEmpty()){
				for(int y=0; y<EditorSetter.currentList.size(); y++){
					currentXPixel=(int)EditorSetter.currentList.get(y).getX()*20+EditorSetter.offsetX;
					currentYPixel=(int)EditorSetter.currentList.get(y).getY()*20+EditorSetter.offsetY;
					setLoadingImage(23);
				}
				EditorSetter.currentList.clear();
				return;
			}
			setLoadingImage(23);			// :== Door
			break;
		}
		
		
	}
	/**
	 * Gibt zurück, ob der DoorChosser aktiviert ist
	 * @return doorChooserActivated
	 */
	public static boolean isDoorChooserActivated(){
		return doorChooserActivated;
	}
	/**
	 * Legt die Bewegung der aktuellen Auswahl fest
	 */
	public void move(){
		if(currentXPixel+moveX*20<EditorSetter.offsetX || currentXPixel+moveX*20>EditorSetter.arrayLenghtX*20+EditorSetter.offsetX-20 || currentYPixel+moveY*20<EditorSetter.offsetY-20 || currentYPixel+moveY*20>EditorSetter.arrayLenghtY*20+EditorSetter.offsetY-20){
			return;
		}
		currentXPixel=currentXPixel+moveX*20;
		currentYPixel=currentYPixel+moveY*20;
	}
}
