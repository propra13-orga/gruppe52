package the.game.java;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Sorgt dafür, dass Leveldateien im xml-Format ausgelesen werden können
 * Durchsucht Leveldatei nach Schlagworten und belegt die Map dementsprechend.
 */
public class SaxParser extends DefaultHandler {

    //private String temp;
    /**
     * Parsen von festgelegten Dateien
     * @param file Dateiname im Ordner src/the/game/java/ 
     */
	public static void parse(String file) throws IOException, SAXException, ParserConfigurationException {
		doPparse("src/the/game/java/" + file);	
	}
	/**
	 * Parsen von Dateien
	 * @param file Datei, die geparst werden soll
	 */
	public static void parse(String file, boolean isAbsolut) throws IOException, SAXException, ParserConfigurationException {
		doPparse(file);
		
	}
	
	/**
	 * Parsen starten
	 * @param file (Rest vom) Dateipfad
	 */
    public static void doPparse(String file) throws IOException, SAXException, ParserConfigurationException {
        
    	// Flag für Layer zurücksetzen
    	DisplayManager.darkness = false;
    	
    	// SaxParserFactory erzeugen
        SAXParserFactory spfac = SAXParserFactory.newInstance();

        // SaxParser in SaxParserFactory erzeugen
        SAXParser sp = spfac.newSAXParser();
        
        // Objekt dieser Klasse erstellen
        SaxParser handler = new SaxParser();
        
        // Dateipfad und Handler (obiges Objekt) an Parser übergeben und parsen starten
        sp.parse(file, handler);
        
    }

    /**     
     * Wird aufgerufen, wenn Parser außerhalb von XML-Elementen auf Text stößt
     * Der Text wird dann in temp geschrieben
     */
    public void characters(char[] buffer, int start, int length) {
           //temp = new String(buffer, start, length);
    }
   
    /**     
     * Wird aufgerufen, wenn der Parser ein neues Element einliest (z.B. "<wall ...>")     
     * 
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
           //temp = "";																			// temp (charbuffer) zurücksetzen
           switch(qName) {																		// Name des Elementes auf erwartete Tags prüfen
           case "wall":																			// Wall
        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {		// Wenn x, y, breite und höhe vorhanden:
        		   LevelCreator.createWall(															// Wand erstellen folgenden Attributen:
            			   Integer.parseInt(attributes.getValue("x")),								// X-Position (string zu int parsen)
            			   Integer.parseInt(attributes.getValue("y")),								// Y-Position (string zu int parsen)
            			   Integer.parseInt(attributes.getValue("width")),							// Breite (string zu int parsen)
            			   Integer.parseInt(attributes.getValue("height")));						// Höhe (string zu int parsen)
        	   } else {																				// Wenn nur x, y angegeben
        		   LevelCreator.createWall(															// Wand erstellen mit folgenden Attributen:
            			   Integer.parseInt(attributes.getValue("x")),								// X-Position (string zu int parsen)
            			   Integer.parseInt(attributes.getValue("y")));								// Y-Position (string zu int parsen)
        	   }																				// REST ANALOG ZU WALL!
        	   break;
           case "door":
        	   if(attributes.getValue("alignment")!= null){
        		   Door.createDoor(
        				   Integer.parseInt(attributes.getValue("x")),								// X-Position (string zu int parsen)
            			   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("goalX")),
            			   Integer.parseInt(attributes.getValue("goalY")),
            			   attributes.getValue("alignment"),
        		   		   attributes.getValue("walkingDirection"));
        	   } else {
        		   Door.createDoor(
					   Integer.parseInt(attributes.getValue("x")),								
	    			   Integer.parseInt(attributes.getValue("y")),
	    			   Integer.parseInt(attributes.getValue("goalX")),
	    			   Integer.parseInt(attributes.getValue("goalY")));
        	   }
        	   break;
           case "mine":
        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {
        		   LevelCreator.createTrap(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("width")),
            			   Integer.parseInt(attributes.getValue("height")));
        	   } else {
        		   LevelCreator.createTrap(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")));
        	   }
        	   
        	   break;
           case "dalek":	// TODO: EINSTELLEN WELCHER FEUERTYP!
        	   Traps.createDalek(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")),
			   				attributes.getValue("type"));
        	   break;
           case "enemy":
        	   if(attributes.getValue("movex")!=null && attributes.getValue("movey")!=null) { //TODO:
        		   Enemy.createEnemy(
        				   Double.parseDouble(attributes.getValue("x")),
            			   Double.parseDouble(attributes.getValue("y")),
            			   Double.parseDouble(attributes.getValue("movex")),
            			   Double.parseDouble(attributes.getValue("movey")));
        	   } else {
        		   Enemy.createEnemy(
        				   Double.parseDouble(attributes.getValue("x")),
        				   Double.parseDouble(attributes.getValue("y")));
        	   }
        	   break;
           case "bouncy":
        	   if(attributes.getValue("movex")!=null && attributes.getValue("movey")!=null) { //TODO:
        		   Enemy.createBouncy(
        				   Double.parseDouble(attributes.getValue("x")),
        				   Double.parseDouble(attributes.getValue("y")),
        				   Double.parseDouble(attributes.getValue("movex")),
        				   Double.parseDouble(attributes.getValue("movey")));
        	   } else {
        		   Enemy.createBouncy(
        				   Double.parseDouble(attributes.getValue("x")),
        				   Double.parseDouble(attributes.getValue("y")));
        	   }
        	   break;
           case "tracker":
        	   Enemy.createTracker(
        			   Double.parseDouble(attributes.getValue("x")),
        			   Double.parseDouble(attributes.getValue("y")));
        	   break;
           case "npc":
        	   NPC.createNPC(
        			   Integer.parseInt(attributes.getValue("id")),
        			   Integer.parseInt(attributes.getValue("x")),
        			   Integer.parseInt(attributes.getValue("y")),
        			   attributes.getValue("look"));
        	   break;
           case "goodie":
        	   LevelCreator.createGoodie(
        			   Integer.parseInt(attributes.getValue("x")),
        			   Integer.parseInt(attributes.getValue("y")),
        			   attributes.getValue("type"));
        	   break;
           case "credits":
        	   Goodies.createCredits(
        			   Integer.parseInt(attributes.getValue("x")),
        			   Integer.parseInt(attributes.getValue("y")),
        			   Integer.parseInt(attributes.getValue("value")));
        	   break;
           case "life":
        	   LevelCreator.createHealthPoint(
        			   Integer.parseInt(attributes.getValue("x")),
        			   Integer.parseInt(attributes.getValue("y")));
        	   break;
           case "shield":
        	   LevelCreator.createShield(
        			   Integer.parseInt(attributes.getValue("x")),
        			   Integer.parseInt(attributes.getValue("y")));
        	   break;
           case "mana":
        	   LevelCreator.createMana(
        			   Integer.parseInt(attributes.getValue("x")), 
        			   Integer.parseInt(attributes.getValue("y")));
        	   break;
           case "item":
        	   Item.addItem(
        			   Integer.parseInt(attributes.getValue("itemid")), 
        			   Integer.parseInt(attributes.getValue("playerid")));
        	   break;
           case "spawn":														// Eigenschaften des Spielers zum Startzeitpunkt:
        	   if(attributes.getValue("team")!=null) {
        		   LevelCaller.setPlayerDefaultPos(										// Startposition festlegen
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("team")));
        	   } else {
        		   LevelCaller.setPlayerDefaultPos(										// Startposition festlegen
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")));
        	   }
        	   
        	   if(LevelCaller.resetting==false) {								// Wenn das Spiel NICHT resetted wird:
	        	   if(attributes.getValue("life")!=null)							// Wenn Attribut life vorhanden:
	        		   Player.playerList.get(0).setAbsoluteLives(						// Attribut life auslesen, in int parsen und dem
	        				   Integer.parseInt(attributes.getValue("life")));			// Spieler als Lebensanzahl zuweisen
	        	   if(attributes.getValue("hp")!=null)								// Wenn Attribut hp vorhanden:
	        		   Player.playerList.get(0).setAbsoluteHP(							// Attribut life auslesen, in int parsen und dem
	        				   Integer.parseInt(attributes.getValue("hp")));			// Spieler als Healthpoints zuweisen
        	   }
        	   if(attributes.getValue("mana")!=null)							// Mana auslesen und zuweisen
        		   Player.getMana(0).setAbsoluteMana(
        				   Integer.parseInt(attributes.getValue("mana")));
        	   if(attributes.getValue("score")!=null)							// Credits auslesen und zuweisen
        		   Player.playerList.get(0).score.setAbsoluteScore(
        				   Integer.parseInt(attributes.getValue("score")));	   
        	   break;
           case "checkpoint":
        	   if(attributes.getValue("activated")==null) {
	        	   Checkpoints.createCheckpoint(
	        			   Integer.parseInt(attributes.getValue("x")),
	        			   Integer.parseInt(attributes.getValue("y")));
        	   } else {
        		   Checkpoints.createCheckpoint(
        				   Integer.parseInt(attributes.getValue("x")),
        				   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("activated")));
        	   }
        	   break;
           case "shop":
        	   NPC.createShopNPC(
        			   Integer.parseInt(attributes.getValue("x")),
        			   Integer.parseInt(attributes.getValue("y")));
        	   break;
           case "goal":
        	   
        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {
        		   LevelCreator.createGoal(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("width")),
            			   Integer.parseInt(attributes.getValue("height")));
        	   } else {
        		   LevelCreator.createGoal(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")));
        	   }
        	   break;
           case "mpgoal":
        	   
        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {
        		   LevelCreator.createMPGoal(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("width")),
            			   Integer.parseInt(attributes.getValue("height")),
            			   Integer.parseInt(attributes.getValue("team")));
        	   } else {
        		   LevelCreator.createMPGoal(
            			   Integer.parseInt(attributes.getValue("x")),
            			   Integer.parseInt(attributes.getValue("y")),
            			   Integer.parseInt(attributes.getValue("team")));
        	   }
        	   break;
           case "weapon":																			// WAFFEN
        	   Player.playerList.get(Integer.parseInt(attributes.getValue("playerid"))).addWeaponPrecisely(	// dem jeweiligen Spieler ansprechen
        			   Integer.parseInt(attributes.getValue("weaponid")),													// Waffe auswählen
        			   Integer.parseInt(attributes.getValue("magcount")),													// Magazinanzahl angeben
        			   Integer.parseInt(attributes.getValue("magsize")));													// Magazininhalt angeben
        	   break;
           case "darkness":
        	   DisplayManager.darkness = true;														// Dark Layer
        	   break;
           }
    }

    /**     
     * Wird aufgerufen, wenn der Parser das Ende eines Elementes erreicht hat     
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	/**
    	 * Bislang nicht benötigt, da nur Attribute verwendet werden
    	 */
    }
}