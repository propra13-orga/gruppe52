package the.game.java;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *	In dieser Klasse werden NPC's erzeugt. Jedem NPC wird eine Nummer zugeordnet. Jede Nummer hat ein zugehöriges Bild
 *  mit dem Text, den der entsprechende NPC sagen soll. Wenn der Spieler und ein NPC kollidieren, wird das jeweilige
 *  Bild dargestellt. Durch Bewegung der Spielfigur, sodass der Spieler sich vom NPC entfernt, verschwindet das Bild
 *  sobald sich Spieler und NPC nicht mehr berühren.
 *  NPC's erklären das Spiel, geben Ratschläge oder machen doofe Witze.
 */
public class NPC {

	private Image npc_pic_up = DisplayManager.getImage("npcLookingUp.png");				// NPC Bild auf Map
	private Image npc_pic_right = DisplayManager.getImage("npcLookingRight.png");	
	private Image npc_pic_down = DisplayManager.getImage("npcLookingDown.png");	
	private Image npc_pic_left = DisplayManager.getImage("npcLookingLeft.png");
	
	private Image npc_shop = DisplayManager.getImage("npc/npcShop.png");
	
	public static List<NPC> npcList = new ArrayList<NPC>(); 				// NPC's werden 'archiviert'
	public static List<NPC> npcShopList = new ArrayList<NPC>();
	
	private int x=0;														// x-Position
	private int y=0;														// y-Position
	
	private static int width_text=565;
	private static int height_text=325;
	
	private int npc_nr;														// nr des NPC's (wichtig um den passenden Text darzustellen)
	private static int collidedNPC=0;										// mit welchem NPC wurde collidiert?
	
	private static boolean already=false;									// wird das Text-Bild bereits dargestellt? (Damit nicht immer wieder neu gezeichnet)
	private static boolean hilfCollide=false;								// will Java haben weil in schleifen true und false nicht erkannt werden
	public static boolean shopPermission = false;
	public static boolean npcPermission = false;
	
	private String npc_direction = "down";
	
	/**
	 * Komplexer Konstruktor der Klasse NPC
	 * @param nr Nr des NPCs. Nötig, damit nach Kollision das passende Bild dargestellt werden kann
	 * @param posx x-Position des NPCs
	 * @param posy y-Position des NPCs
	 * @param lookingDirection Richtung in die der NPC schaut
	 */
	public NPC(int nr, int posx, int posy, String lookingDirection){
		x=posx;
		y=posy;
		npc_nr=nr;
		npc_direction = lookingDirection;
	}
	/**
	 * Einfacher Konstruktor der Klasse NPC
	 * NPC blickt immer nach unten.
	 * @param posx x-Position des NPCs
	 * @param posy y-Position des NPCs
	 */
	public NPC(int posx, int posy){
		x=posx;
		y=posy;
	}

	/**
	 * Stellt Bild des entsprechenden NPC's auf Map dar
	 * Es muss unterschieden werden, in welche Richtung der NPC schauen soll
	 */
	public static void displayNPC(){
		for(int i=0; i<npcList.size(); i++){			// so viele NPC's existieren
			//npcList.get(i);
			
			switch(npcList.get(i).npc_direction) {
			case "down": 
				DisplayManager.displayImage(npcList.get(i).npc_pic_down, npcList.get(i).x, npcList.get(i).y, "NPC"); // NPC ist der Tag - siehe Display Manager
				break;
			case "up":
				// 							Bild jedes NPC's		X-Position		  Y-Position		Tag	
				DisplayManager.displayImage(npcList.get(i).npc_pic_up, npcList.get(i).x, npcList.get(i).y, "NPC"); // NPC ist der Tag - siehe Display Manager
				break;
			case "right":
				DisplayManager.displayImage(npcList.get(i).npc_pic_right, npcList.get(i).x, npcList.get(i).y, "NPC"); // NPC ist der Tag - siehe Display Manager
				break;
			case "left":
				DisplayManager.displayImage(npcList.get(i).npc_pic_left, npcList.get(i).x, npcList.get(i).y, "NPC"); // NPC ist der Tag - siehe Display Manager
				break;
			}
		}
		for(int s=0; s<npcShopList.size(); s++){
			DisplayManager.displayImage(npcShopList.get(s).npc_shop, npcShopList.get(s).x, npcShopList.get(s).y, "NPC"); // NPC ist der Tag - siehe Display Manager
		}
	}
	
	/**
	 * Entfernt alle NPCs von der Map
	 */
	public static void removeAllNPCs() {
		DisplayManager.removeChangeableImages("NPC");
		npcList.clear();
		npcShopList.clear();
	}
	
	/**
	 * Stellt das der Nummer entsprechende Bild mit zugehörigem Text dar
	 */
	public static void setCollidedPicture(){
		// Wenn Spieler mit einem NPC kollidiert & das Bild nicht bereits gemalt wird
		if(collidedNPC>0 && already==false){
			DisplayManager.displayImage("transparentLayer.png", 0, 0, "menu", true);
			//							Bild des Textes eines NPC's		 x-Position			   y-Position	          Tag
			DisplayManager.displayImage("npc/npcMsg"+collidedNPC+".png", Runner.getWidthF()/2-width_text/2, Runner.getHeightF()/2-height_text/2, "menu", true); // menu -> das Bild kann geschlossen werden
			already=true;
		}
	}
	
	/**
	 * Prüft, ob der Spieler mit einem NPC kollidiert
	 * @return true, wenn er kollidiert, false, wenn nicht
	 */
	public static boolean checkPlayerCollide(){
		// Jeder Spieler muss zusammen mit jedem NPC überprüft werden
		npcPermission = false;
		shopPermission = false;
		for(int i=0; i<Player.playerList.size(); i++){
			for(int z=0; z<npcList.size(); z++){
				if(Intersect.isCollidingWithPlayer(i, npcList.get(z).getX(), npcList.get(z).getY(), 20, 20)){
					hilfCollide=true;
					collidedNPC=npcList.get(z).getNr();				// collidedNPC nimmt die Nummer des ber+hrten NPCs an
					//setCollidedPicture();							// malt das Bild mit Text
					npcPermission = true;
				}else{
					if(hilfCollide) {								// wenn gerade eine Nachricht angezeigt wird:
						if(collidedNPC==npcList.get(z).getNr()) {	// wenn gerade der NPC überprüft wird, dessen Nachricht angezeigt wird:
							hilfCollide=false;						// flag zurücksetzen
							already=false;							// flag zurücksetzen
							collidedNPC=0;							// damit aktualisiert wird, wenn sie nicht mehr kollidieren
							DisplayManager.removeChangeableImages("menu");	// Das Bild wird entfernt
						}
					}
				}
			}
			for(int h=0; h<npcShopList.size(); h++){
				if(Intersect.isCollidingWithPlayer(i, npcShopList.get(h).getX(), npcShopList.get(h).getY(), 20, 20)){
					if(Shop.show==false){
						shopPermission = true;
					}
				}
			}
		}
		return hilfCollide;
	}
	

	/**
	 * Erstellt einen komplexen NPC
	 * @param nr Nr des NPCs. Nötig, damit nach Kollision das passende Bild dargestellt werden kann
	 * @param posx x-Position des NPCs
	 * @param posy y-Position des NPCs
	 * @param lookingDirection Richtung in die der NPC schaut
	 */
	public static void createNPC(int nr, int posx, int posy, String lookingDirection) {
		posy += LevelCreator.distancePix;							// Wegen Menuleiste oben
		npcList.add(new NPC(nr, posx, posy, lookingDirection));
	}
	/**
	 * Erstellt einen einfachen NPC
	 * @param posx x-Position des NPCs
	 * @param posy y-Position des NPCs
	 */
	public static void createShopNPC(int posx, int posy){
		posy += LevelCreator.distancePix;
		npcShopList.add(new NPC(posx, posy));
	}
	    
    /**
     * gibt die x-Position zurück
     */
    public int getX(){
    	return x;
    }
    /**
     * gibt die y-Position zurück
     */
    public int getY(){
    	return y;
    }
    /**
     * gibt die Nr im Spielverlauf des NPCs zurück
     */
    public int getNr(){
    	return npc_nr;
    }
    /**
     * gibt die Richtung zurück, in welche der NPC schauen soll
     */
    public String getDirection(){
    	return npc_direction;
    }
}
