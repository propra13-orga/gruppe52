package level.creator.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle Objekte die einer Door/einem Portal entsprechen
 * Die T�ren m�ssen verbunden werden: Eine Eingans- eine Ausgangst�r
 */
public class Door {
	
	public static List<Door> doorList = new ArrayList<Door>();			//Liste zum archivieren aller Elemente
	
	public static boolean showDoors = true;
	
	//Position in Pixel
	public int x;
	public int y;
	//Position in K�stchen
	public int arrayx;
	public int arrayy;
	//Position der Zielt�r
	public int goalx;
	public int goaly;
	
	/**
	 * Konstruktor der Klasse Door
	 * @param posx = x-Position der Eingangst�r
	 * @param posy = y-Position der Eingangst�r
	 * @param linkx = x-Position der Zielt�r
	 * @param linky = y-Position der Zielt�r
	 */
	private Door(int posx, int posy, int linkx, int linky) {
		x = posx;
		y = posy;
		arrayx = pixelToArray(x);
		arrayy = pixelToArray(y);
		goalx = linkx;
		goaly = linky;
	}
	
	/**
	 * F�gt ein Objekt der Klasse Door der doorList hinzu, wenn die T�r im Array liegt
	 * @param posx = x-Position der Eingangst�r
	 * @param posy = y-Position der Eingangst�r
	 * @param linkx = x-Position der Zielt�r
	 * @param linky = y-Position der Zielt�r
	 */
	public static void createDoor(int posx, int posy, int linkx, int linky) {
		if(isDoorInArray(posx, posy));
			doorList.add(new Door(posx,posy,linkx,linky));
	}
	 /**
	  * Konvertiert einen Wert in Pixel in den entsprechenden Wert in K�stchen
	  * @param val = zu konvertierender Wert
	  * @return konvertierter Wert
	  */
	public static int pixelToArray(int val) {
		return (val-(val%20))/20;
	}
	
	 /**
	  * Konvertiert einen Wert in K�stchen in den entsprechenden Wert in Pixel
	  * @param val = zu konvertierender Wert
	  * @return konvertierter Wert
	  */
	public static int arrayToPixel(int val) {
		return val*20;
	}
	
	/**
	 * Pr�ft ob T�r korrekt bzw in Array ist
	 * @return ob T�r korrekt bzw in Array ist
	 */
	public static boolean isCorrect() {
		for(int a=0; a<doorList.size(); a++) {
			if(doorList.get(a).isDoorInArray()==false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Pr�ft ob Door in Array ist
	 * @return Ist Door in Array?
	 */
	public boolean isDoorInArray() {
		return (EditorSetter.map[arrayx][arrayy] >= 20 && EditorSetter.map[arrayx][arrayy] <= 23);
	}
	/**
	 * Pr�ft ob Door in Pixelangaben im Array ist
	 * @param pixelX pixel-x-Position der T�r
	 * @param pixelY pixel-y-Position der T�r
	 * @return Ist Door im Array?
	 */
	public static boolean isDoorInArray(int pixelX, int pixelY) {
		return (EditorSetter.map[pixelToArray(pixelX)][pixelToArray(pixelY)] >= 20 && EditorSetter.map[pixelToArray(pixelX)][pixelToArray(pixelY)] <= 23);
	}
	/**
	 * Verbindet eine T�r mit einer anderen
	 * @param xD1 x-Position der Einganst�r
	 * @param yD1 y-Position der Einganst�r
	 * @param xD2 x-Position der Ausgangst�r
	 * @param yD2 y-Position der Ausgangst�r
	 */
	public static void linkDoor(int xD1, int yD1, int xD2, int yD2) {
		int door1 = findDoorInList(pixelToArray(xD1), pixelToArray(yD1));
		int door2 = findDoorInList(pixelToArray(xD2), pixelToArray(yD2));
		
		doorList.get(door1).goalx = doorList.get(door2).x;
		doorList.get(door1).goaly = doorList.get(door2).y;
		
		doorList.get(door2).goalx = doorList.get(door1).x;
		doorList.get(door2).goaly = doorList.get(door1).y;
	}
	
	/**
	 * Sucht nach entsprechender T�r in doorList
	 * @param x posX - Eingang
	 * @param y posY - Eingang
	 * @return Position der T�r in doorList oder -1 falls nicht in List
	 */
	public static int findDoorInList(int x, int y) {
		for(int a=0; a<doorList.size(); a++) {
			if(doorList.get(a).arrayx==x && doorList.get(a).arrayy==y) {
				return a;
			}
		}
		return -1;
	}
	/**
	 * Sucht nach entsprechender Zielt�r in doorList
	 * @param x posX - Ausgang
	 * @param y posY - Ausgang
	 * @return Position der T�r in doorList oder -1 falls nicht in List
	 */
	public static int findDoorInListAsGoal(int x, int y) {
		for(int a=0; a<doorList.size(); a++) {
			if(doorList.get(a).goalx==x && doorList.get(a).goaly==y) {
				return a;
			}
		}
		return -1;
	}
	/**
	 * Pr�ft, ob T�r in List existiert
	 * @param x posX - Eingang
	 * @param y posY - Eingang
	 * @return Ist T�r in List?
	 */
	public boolean isDoorInList(int x, int y){
		for(int a=0; a<doorList.size(); a++) {
			if(doorList.get(a).x==x && doorList.get(a).y==y || doorList.get(a).goalx==x && doorList.get(a).goaly==y){
				return true;
			}
		}
		return false;
	}
	/**
	 * Pr�ft ob alle T�ren in der Liste sind
	 * @return Sind alle T�ren in der Liste
	 */
	public static boolean everyDoorIsInList(){
		for(int a=0; a<EditorSetter.arrayLenghtX; a++){
			for(int b=0; b<EditorSetter.arrayLenghtY; b++){
				
				int val = findDoorInList(a, b);
				if(val<0 || Door.doorList.get(val).goalx<=0 || Door.doorList.get(val).goaly<=0) {
					return false;
				}
				
			}
		}
		return true;
	}
	/**
	 * Sucht nach der x-Position einer passenden Zielt�r
	 * @param posxStart x-Pos der Einganst�r
	 * @param posyStart y-Pos der Einganst�r
	 * @return x-Pos der Ausgangst�r / -1 falls T�r nicht existiert
	 */
	public static int findCompatibleGoalPositionX(int posxStart, int posyStart){
		for(int i=0; i<doorList.size(); i++){
			if(posxStart==doorList.get(i).x && posyStart==doorList.get(i).y){
				System.out.println("Ein Element wurde gefunden");
				return doorList.get(i).goalx;
			}
		}
		return -1;
	}
	/**
	 * Sucht nach der y-Position einer passenden Zielt�r
	 * @param posxStart x-Pos der Einganst�r
	 * @param posyStart y-Pos der Einganst�r
	 * @return y-Pos der Ausgangst�r / -1 falls T�r nicht existiert
	 */
	public static int findCompatibleGoalPositionY(int posxStart, int posyStart){
		System.out.println("Best�tigung: Ich versuche ein Element zu finden");
		
		for(int i=0; i<doorList.size(); i++){
			if(posxStart==doorList.get(i).x && posyStart==doorList.get(i).y){
				System.out.println("Best�tigung: Ein Element wurde gefunden");

				return doorList.get(i).goaly;
			}
		}
		return -1;
	}
}
