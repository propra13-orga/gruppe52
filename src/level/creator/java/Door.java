package level.creator.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle Objekte die einer Door/einem Portal entsprechen
 * Die Türen müssen verbunden werden: Eine Eingans- eine Ausgangstür
 */
public class Door {
	
	public static List<Door> doorList = new ArrayList<Door>();			//Liste zum archivieren aller Elemente
	
	public static boolean showDoors = true;
	
	//Position in Pixel
	public int x;
	public int y;
	//Position in Kästchen
	public int arrayx;
	public int arrayy;
	//Position der Zieltür
	public int goalx;
	public int goaly;
	
	/**
	 * Konstruktor der Klasse Door
	 * @param posx = x-Position der Eingangstür
	 * @param posy = y-Position der Eingangstür
	 * @param linkx = x-Position der Zieltür
	 * @param linky = y-Position der Zieltür
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
	 * Fügt ein Objekt der Klasse Door der doorList hinzu, wenn die Tür im Array liegt
	 * @param posx = x-Position der Eingangstür
	 * @param posy = y-Position der Eingangstür
	 * @param linkx = x-Position der Zieltür
	 * @param linky = y-Position der Zieltür
	 */
	public static void createDoor(int posx, int posy, int linkx, int linky) {
		if(isDoorInArray(posx, posy));
			doorList.add(new Door(posx,posy,linkx,linky));
	}
	 /**
	  * Konvertiert einen Wert in Pixel in den entsprechenden Wert in Kästchen
	  * @param val = zu konvertierender Wert
	  * @return konvertierter Wert
	  */
	public static int pixelToArray(int val) {
		return (val-(val%20))/20;
	}
	
	 /**
	  * Konvertiert einen Wert in Kästchen in den entsprechenden Wert in Pixel
	  * @param val = zu konvertierender Wert
	  * @return konvertierter Wert
	  */
	public static int arrayToPixel(int val) {
		return val*20;
	}
	
	/**
	 * Prüft ob Tür korrekt bzw in Array ist
	 * @return ob Tür korrekt bzw in Array ist
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
	 * Prüft ob Door in Array ist
	 * @return Ist Door in Array?
	 */
	public boolean isDoorInArray() {
		return (EditorSetter.map[arrayx][arrayy] >= 20 && EditorSetter.map[arrayx][arrayy] <= 23);
	}
	/**
	 * Prüft ob Door in Pixelangaben im Array ist
	 * @param pixelX pixel-x-Position der Tür
	 * @param pixelY pixel-y-Position der Tür
	 * @return Ist Door im Array?
	 */
	public static boolean isDoorInArray(int pixelX, int pixelY) {
		return (EditorSetter.map[pixelToArray(pixelX)][pixelToArray(pixelY)] >= 20 && EditorSetter.map[pixelToArray(pixelX)][pixelToArray(pixelY)] <= 23);
	}
	/**
	 * Verbindet eine Tür mit einer anderen
	 * @param xD1 x-Position der Einganstür
	 * @param yD1 y-Position der Einganstür
	 * @param xD2 x-Position der Ausgangstür
	 * @param yD2 y-Position der Ausgangstür
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
	 * Sucht nach entsprechender Tür in doorList
	 * @param x posX - Eingang
	 * @param y posY - Eingang
	 * @return Position der Tür in doorList oder -1 falls nicht in List
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
	 * Sucht nach entsprechender Zieltür in doorList
	 * @param x posX - Ausgang
	 * @param y posY - Ausgang
	 * @return Position der Tür in doorList oder -1 falls nicht in List
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
	 * Prüft, ob Tür in List existiert
	 * @param x posX - Eingang
	 * @param y posY - Eingang
	 * @return Ist Tür in List?
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
	 * Prüft ob alle Türen in der Liste sind
	 * @return Sind alle Türen in der Liste
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
	 * Sucht nach der x-Position einer passenden Zieltür
	 * @param posxStart x-Pos der Einganstür
	 * @param posyStart y-Pos der Einganstür
	 * @return x-Pos der Ausgangstür / -1 falls Tür nicht existiert
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
	 * Sucht nach der y-Position einer passenden Zieltür
	 * @param posxStart x-Pos der Einganstür
	 * @param posyStart y-Pos der Einganstür
	 * @return y-Pos der Ausgangstür / -1 falls Tür nicht existiert
	 */
	public static int findCompatibleGoalPositionY(int posxStart, int posyStart){
		System.out.println("Bestätigung: Ich versuche ein Element zu finden");
		
		for(int i=0; i<doorList.size(); i++){
			if(posxStart==doorList.get(i).x && posyStart==doorList.get(i).y){
				System.out.println("Bestätigung: Ein Element wurde gefunden");

				return doorList.get(i).goaly;
			}
		}
		return -1;
	}
}
