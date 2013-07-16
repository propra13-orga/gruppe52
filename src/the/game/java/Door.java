package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/** 
 * Verwaltet alle Türportale. Bei Kollision mit einer Tür, wird man zu einer anderen Tür teleportiert.
 */
public class Door {
	public static List<Door> doorList = new ArrayList<Door>();
	
	private int x;
	private int y;
	private int goalPosX;
	private int goalPosY;
	private String alignment = "vertikal";
	private String walking = "right";
	
	private int height;
	private int width;
	
	private Image door_hori = DisplayManager.getImage("door/doorHorizontal.png");
	private Image door_verti = DisplayManager.getImage("door/doorVertikal.png");
	
	private int doorwidth_verti = door_verti.getWidth(null);
	private int doorheight_verti = door_verti.getHeight(null);
	private int doorwidth_hori = door_hori.getWidth(null);
	private int doorheight_hori = door_hori.getHeight(null);
	
	/**
	 * Komplexer Konstruktor der Klasse Door.
	 * @param posx x-Pos der Eingangstür
	 * @param posy y-Pos der Eingangstür
	 * @param goalx x-Pos der Zieltür
	 * @param goaly y-Pos der Zieltür
	 * @param alignmentDoor	Ausrichtung der Tür (horizontal/vertikal)
	 * @param walkingDirection Laufrichtung der Tür nach (up, right, down, left)
	 */
	private Door(int posx, int posy, int goalx, int goaly, String alignmentDoor, String walkingDirection){
		if(alignmentDoor.equals("horizontal")){
			goalPosX=goalx;
			goalPosY=goaly;
			
			x=posx;
			y=posy+7;
			
			height=doorheight_hori;
			width=doorwidth_hori;
		}else{									
			goalPosX=goalx;
			goalPosY=goaly;
			
			x=posx+7;
			y=posy;
			
			height=doorheight_verti;
			width=doorwidth_verti;
		}
		
		alignment = alignmentDoor;
		walking = walkingDirection;
		displayDoor();
	}
	
	/**
	 * Einfacher Konstruktor der Klasse Door.
	 * Es wird angenommen, dass die Tür vertikal nach rechts gerichtet ist.
	 * @param posx x-Pos der Eingangstür
	 * @param posy y-Pos der Eingangstür
	 * @param goalx x-Pos der Zieltür
	 * @param goaly y-Pos der Zieltür
	 */
	private Door(int posx, int posy, int goalx, int goaly){
		x = posx+7;											// Bild-Breite=6 -> (20-6)/2=7
		y = posy;
		goalPosX=goalx+20;
		goalPosY=goaly;
		height=doorheight_verti;
		width=doorwidth_verti;
		alignment = "vertikal";
		walking = "right";
		displayDoor();
	}
	
	/**
	 * Stellt die Tür dem alignment entsprechend dar.
	 */
	public void displayDoor(){
		if(alignment.equals("horizontal")){
			DisplayManager.displayImage(door_hori, getX(), getY(), "door");
		}else{
			DisplayManager.displayImage(door_verti, getX(), getY(), "door");
		}
	}
	
	/**
	 * Erstellt ein komplexes Objekt der Klasse Door und fügt door in doorList hinzu.
	 * @param posx x-Pos der Eingangstür
	 * @param posy y-Pos der Eingangstür
	 * @param goalx x-Pos der Zieltür
	 * @param goaly y-Pos der Zieltür
	 * @param alignmentDoor	Ausrichtung der Tür (horizontal/vertikal)
	 * @param walkingDirection Laufrichtung der Tür nach (up, right, down, left)
	 */
	public static void createDoor(int posx, int posy, int goalx, int goaly, String alignmentDoor, String walkingDirection){
		doorList.add(new Door(posx, posy, goalx, goaly, alignmentDoor, walkingDirection));
	}
	
	/**
	 * Erstellt ein einfaches Objekt der Klasse Door und fügt door in doorList hinzu.
	 * @param posx x-Pos der Eingangstür
	 * @param posy y-Pos der Eingangstür
	 * @param goalx x-Pos der Zieltür
	 * @param goaly y-Pos der Zieltür
	 */
	public static void createDoor(int posx, int posy, int goalx, int goaly){
		doorList.add(new Door(posx, posy, goalx, goaly));
	}
	/**
	 * Entfernt alle Türen von der Map
	 * DoorList wird geleert
	 */
    public static void removeAllDoors() {
    	for(int a=0; a<doorList.size(); a++) {
			DisplayManager.removeChangeableImages("door");
		}
    	doorList.clear();
    }
	
	/**
	 * Überprüft ob ein Spieler mit einer Tür kollidiert.
	 * @param playerID Nr des zu überprüfenden Spielers
	 */
	public void checkPlayerCollideWithDoor(int playerID){
		if(Intersect.isCollidingWithPlayer(playerID, getX(), getY(), width, height)){
			gotoNextDoor(playerID);
		}
	}
	
	/**
	 * Nach kollision mit einer Tür wird ein Spieler zu einer anderen Tür teleportiert.
	 * @param playerID Nr des betroffenen Spielers
	 */
	public void gotoNextDoor(int playerID){
		for(int a=0; a<doorList.size(); a++) {
			if(gotoNextDoorCheckVals(a, goalPosX, goalPosY)) {
				if(doorList.get(a).alignment.equals("horizontal")){	// --
					if(doorList.get(a).walking.equals("up")) {		// -- & ^
						Player.getPlayer(playerID).setPosition(getGoalX(), getGoalY()-20);
						return;
					} else {						// -- & v
						Player.getPlayer(playerID).setPosition(getGoalX(), getGoalY()+20);
						return;
					}
					
				}else{								//  |
					if(doorList.get(a).walking.equals("right")) {	//  | & >
						Player.getPlayer(playerID).setPosition(getGoalX()+20, getGoalY());
						return;
					} else {						//  | & <
						Player.getPlayer(playerID).setPosition(getGoalX()-20, getGoalY());
						return;
					}
					
				}
			}
		}
		// Wenn nicht gefunden, dann gegebene Werte benutzen
		Player.getPlayer(playerID).setPosition(getGoalX(), getGoalY());
	}
	
	/** 
	 * überprüft, ob Spieler zur nächsten Tür gehen darf.
	 * @param index ID der Tür
	 * @param goalx x-Pos der Zieltür
	 * @param goaly y-Pos der Zieltür
	 * @return true, wenn ja, false, wenn nein
	 */
	private boolean gotoNextDoorCheckVals(int index, int goalx, int goaly) {
		if(getP2A(doorList.get(index).x)==getP2A(goalx) && getP2A(doorList.get(index).y)==getP2A(goaly)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Wandelt einen Pixel- in einen Arraywert um.
	 * @param val Wert, der in ArrayAngaben umgewandelt werden soll
	 * @return den entsprechenden Arraywert.
	 */
	public int getP2A(int val){
		return (val-(val%20))/20;
	}
	
	/**
	 * Gibt x zurück
	 * @return x
	 */
	public int getX(){
		return x;
	}
	/**
	 * Gibt y zurück
	 * @return y
	 */
	public int getY(){
		return y;
	}
	/**
	 * Gibt die Ausrichtung zurück
	 * @return alignment
	 */
	public String getAlignment(){
		return alignment;
	}
	/**
	 * Gibt die Laufrichtung zurück
	 * @return walkingDirection
	 */
	public String getWalkingDirection(){
		return walking;
	}
	/**
	 * Gibt x-Pos der Zieltür zurück
	 * @return goalPosX
	 */
	public int getGoalX(){
		return goalPosX;
	}
	/**
	 * Gibt y-Pos der Zieltür zurück
	 * @return goalPosY
	 */
	public int getGoalY(){
		return goalPosY;
	}
}
