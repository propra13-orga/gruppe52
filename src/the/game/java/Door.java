package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/** 
 * Verwaltet alle T�rportale. Bei Kollision mit einer T�r, wird man zu einer anderen T�r teleportiert.
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
	 * @param posx x-Pos der Eingangst�r
	 * @param posy y-Pos der Eingangst�r
	 * @param goalx x-Pos der Zielt�r
	 * @param goaly y-Pos der Zielt�r
	 * @param alignmentDoor	Ausrichtung der T�r (horizontal/vertikal)
	 * @param walkingDirection Laufrichtung der T�r nach (up, right, down, left)
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
	 * Es wird angenommen, dass die T�r vertikal nach rechts gerichtet ist.
	 * @param posx x-Pos der Eingangst�r
	 * @param posy y-Pos der Eingangst�r
	 * @param goalx x-Pos der Zielt�r
	 * @param goaly y-Pos der Zielt�r
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
	 * Stellt die T�r dem alignment entsprechend dar.
	 */
	public void displayDoor(){
		if(alignment.equals("horizontal")){
			DisplayManager.displayImage(door_hori, getX(), getY(), "door");
		}else{
			DisplayManager.displayImage(door_verti, getX(), getY(), "door");
		}
	}
	
	/**
	 * Erstellt ein komplexes Objekt der Klasse Door und f�gt door in doorList hinzu.
	 * @param posx x-Pos der Eingangst�r
	 * @param posy y-Pos der Eingangst�r
	 * @param goalx x-Pos der Zielt�r
	 * @param goaly y-Pos der Zielt�r
	 * @param alignmentDoor	Ausrichtung der T�r (horizontal/vertikal)
	 * @param walkingDirection Laufrichtung der T�r nach (up, right, down, left)
	 */
	public static void createDoor(int posx, int posy, int goalx, int goaly, String alignmentDoor, String walkingDirection){
		doorList.add(new Door(posx, posy, goalx, goaly, alignmentDoor, walkingDirection));
	}
	
	/**
	 * Erstellt ein einfaches Objekt der Klasse Door und f�gt door in doorList hinzu.
	 * @param posx x-Pos der Eingangst�r
	 * @param posy y-Pos der Eingangst�r
	 * @param goalx x-Pos der Zielt�r
	 * @param goaly y-Pos der Zielt�r
	 */
	public static void createDoor(int posx, int posy, int goalx, int goaly){
		doorList.add(new Door(posx, posy, goalx, goaly));
	}
	/**
	 * Entfernt alle T�ren von der Map
	 * DoorList wird geleert
	 */
    public static void removeAllDoors() {
    	for(int a=0; a<doorList.size(); a++) {
			DisplayManager.removeChangeableImages("door");
		}
    	doorList.clear();
    }
	
	/**
	 * �berpr�ft ob ein Spieler mit einer T�r kollidiert.
	 * @param playerID Nr des zu �berpr�fenden Spielers
	 */
	public void checkPlayerCollideWithDoor(int playerID){
		if(Intersect.isCollidingWithPlayer(playerID, getX(), getY(), width, height)){
			gotoNextDoor(playerID);
		}
	}
	
	/**
	 * Nach kollision mit einer T�r wird ein Spieler zu einer anderen T�r teleportiert.
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
	 * �berpr�ft, ob Spieler zur n�chsten T�r gehen darf.
	 * @param index ID der T�r
	 * @param goalx x-Pos der Zielt�r
	 * @param goaly y-Pos der Zielt�r
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
	 * Gibt x zur�ck
	 * @return x
	 */
	public int getX(){
		return x;
	}
	/**
	 * Gibt y zur�ck
	 * @return y
	 */
	public int getY(){
		return y;
	}
	/**
	 * Gibt die Ausrichtung zur�ck
	 * @return alignment
	 */
	public String getAlignment(){
		return alignment;
	}
	/**
	 * Gibt die Laufrichtung zur�ck
	 * @return walkingDirection
	 */
	public String getWalkingDirection(){
		return walking;
	}
	/**
	 * Gibt x-Pos der Zielt�r zur�ck
	 * @return goalPosX
	 */
	public int getGoalX(){
		return goalPosX;
	}
	/**
	 * Gibt y-Pos der Zielt�r zur�ck
	 * @return goalPosY
	 */
	public int getGoalY(){
		return goalPosY;
	}
}
