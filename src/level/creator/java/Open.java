package level.creator.java;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

	/**
	 * Regelt das Öffnen von LevelDateien
	 */
	public class Open extends DefaultHandler {

	    //private String temp;
	    
		
	    /**     Parsen starten. Parameter legt Dateipfad fest     */
	    public static void open(String path) throws IOException, SAXException, ParserConfigurationException {
	    	
	    	// Map wird resetet
	    	EditorSetter.resetMap();
	    	
	    	// SaxParserFactory erzeugen
	        SAXParserFactory spfac = SAXParserFactory.newInstance();

	        // SaxParser in SaxParserFactory erzeugen
	        SAXParser sp = spfac.newSAXParser();
	        
	        // Objekt dieser Klasse erstellen
	        Open handler = new Open();
	        
	        // Dateipfad und Handler (obiges Objekt) an Parser übergeben und parsen starten
	        sp.parse(path, handler);
	        
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
	     */
	    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	           //temp = "";																			// temp (charbuffer) zurücksetzen
	    	   // geht Zeilen durch und wertet sie aus
	           
	    	switch(qName) {																		// Name des Elementes auf erwartete Tags prüfen
	           case "darkness":
	        	   EditorSetter.darkness.setSelected(true);
	        	   break;
	           case "multiplayer":
	        	   EditorSetter.multi.setSelected(true);
	        	   break;
	           case "wall":																			// Wall
	        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {		// Wenn x, y, breite und höhe vorhanden:
	        		   createWall(																		// Wand erstellen folgenden Attributen:
	            			   Integer.parseInt(attributes.getValue("x")),								// X-Position (string zu int parsen)
	            			   Integer.parseInt(attributes.getValue("y")),								// Y-Position (string zu int parsen)
	            			   Integer.parseInt(attributes.getValue("width")),							// Breite (string zu int parsen)
	            			   Integer.parseInt(attributes.getValue("height")));						// Höhe (string zu int parsen)
	        	   } else {																				// Wenn nur x, y angegeben
	        		   createWall(																		// Wand erstellen mit folgenden Attributen:
	            			   Integer.parseInt(attributes.getValue("x")),								// X-Position (string zu int parsen)
	            			   Integer.parseInt(attributes.getValue("y")));								// Y-Position (string zu int parsen)
	        	   }																					// REST ANALOG ZU WALL!
	        	   break;
	           case "door":
	        	   if(attributes.getValue("alignment")!= null){
	        		   createDoor(
	        				   Integer.parseInt(attributes.getValue("x")),								// X-Position (string zu int parsen)
	            			   Integer.parseInt(attributes.getValue("y"))-20,
	            			   Integer.parseInt(attributes.getValue("goalX")),
	            			   Integer.parseInt(attributes.getValue("goalY"))-20,
	            			   attributes.getValue("alignment"),
	        		   		   attributes.getValue("walkingDirection"));
	        	   } else {
	        		   createDoor(
						   Integer.parseInt(attributes.getValue("x")),					
		    			   Integer.parseInt(attributes.getValue("y"))-20,
		    			   Integer.parseInt(attributes.getValue("goalX")),
		    			   Integer.parseInt(attributes.getValue("goalY"))-20);
	        	   }
	        	   break;
	           case "mine":
	        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {
	        		   createMine(
	            			   Integer.parseInt(attributes.getValue("x")),
	            			   Integer.parseInt(attributes.getValue("y")),
	            			   Integer.parseInt(attributes.getValue("width")),
	            			   Integer.parseInt(attributes.getValue("height")));
	        	   } else {
	        		   createMine(
	            			   Integer.parseInt(attributes.getValue("x")),
	            			   Integer.parseInt(attributes.getValue("y")));
	        	   }
	        	   
	        	   break;
	           case "dalek":
	        	   createDalek(
	            			   Integer.parseInt(attributes.getValue("x")),
	            			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "enemy":
	        	   if(attributes.getValue("movex")!=null && attributes.getValue("movey")!=null) {
	        		   createEnemy(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")),
	        				   Integer.parseInt(attributes.getValue("movex")),
	        				   Integer.parseInt(attributes.getValue("movey")));
	        	   } else {
	        		   createEnemy(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")));
	        	   }
	        	   break;
	           case "bouncy":
	        	   if(attributes.getValue("movex")!=null && attributes.getValue("movey")!=null) {
	        		   createBouncy(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")),
	        				   Integer.parseInt(attributes.getValue("movex")),
	        				   Integer.parseInt(attributes.getValue("movey")));
	        	   } else {
	        		   createBouncy(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")));
	        	   }
	        	   break;
	           case "tracker":
	        	   createTracker(
	        			   Integer.parseInt(attributes.getValue("x")),
	        			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "goodie":
	        	   switch(attributes.getValue("type")){
	        	   case "credits":
	        		   createCredits(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")));
	        		   break;
	        	   case "heart":
	        		   createheart(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")));
	        		   break;
	        	   case "shield":
	        		   createShield(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")));
	        		   break;
	        	   case "mana":
	        		   createMana(
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y")));
	        		   break;
	        	   }	        		
	        	   break;
	           case "credits":
	        	   createCredits(
	        			   Integer.parseInt(attributes.getValue("x")),
	        			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "life":
	        	   createheart(
	        			   Integer.parseInt(attributes.getValue("x")),
	        			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "shield":
	        	   createShield(
	        			   Integer.parseInt(attributes.getValue("x")),
	        			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "mana":
	        	   createMana(
	        			   Integer.parseInt(attributes.getValue("x")), 
	        			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "spawn":
	        	   System.out.println(attributes.getValue("team"));
	        	   if(attributes.getValue("team")==null){						// KEIN MULTIPLAYER
	        		   EditorSetter.multi.setSelected(false);
		        	   createSpawn(										
		        			   Integer.parseInt(attributes.getValue("x")),
		        			   Integer.parseInt(attributes.getValue("y"))-1);
	        	   }else if(attributes.getValue("team").equals("0")){					// MULTIPLAYER: spawn team 0 & goal team 1
	        		   EditorSetter.multi.setSelected(true);
		        	   createSpawn(										
		        			   Integer.parseInt(attributes.getValue("x")),
		        			   Integer.parseInt(attributes.getValue("y"))-1);
		        	   System.out.println("SPAWN TEAM 0");
	        	   }else if(attributes.getValue("team").equals("1")){					// MULTIPLAYER: spawn team 1 & goal team 0
	        		   EditorSetter.multi.setSelected(true);
	        		   createGoal(		        			   
	        				   Integer.parseInt(attributes.getValue("x")),
	        				   Integer.parseInt(attributes.getValue("y"))-1); //TODO
	        	   }
	        	   break;
	           case "checkpoint":
	        	   createCheckpoint(
	        			   Integer.parseInt(attributes.getValue("x")),
	        			   Integer.parseInt(attributes.getValue("y")));
	        	   break;
	           case "shop": 
	        	   createShop(
	        			   Integer.parseInt(attributes.getValue("x"))/20,
	        			   Integer.parseInt(attributes.getValue("y"))/20);
	        	   break;
	           case "goal":
	        	   
	        	   if(attributes.getValue("width")!=null && attributes.getValue("height")!=null) {
	        		   createGoal(
	            			   Integer.parseInt(attributes.getValue("x")),
	            			   Integer.parseInt(attributes.getValue("y"))-1,
	            			   Integer.parseInt(attributes.getValue("width")),
	            			   Integer.parseInt(attributes.getValue("height")));
	        	   } else {
	        		   createGoal(
	            			   Integer.parseInt(attributes.getValue("x")),
	            			   Integer.parseInt(attributes.getValue("y"))-1);
	        	   }
	        	   break;
	           /*case "darkness": //TODO:
	        	   DisplayManager.darkness = true;														// Dark Layer
	        	   break;*/
	           }
	    }

	    /**     
	     * Wird aufgerufen, wenn der Parser das Ende eines Elementes erreicht hat     
	     */
	    public void endElement(String uri, String localName, String qName) throws SAXException {
	    	/*
	    	 * Bislang nicht benötigt, da nur Attribute verwendet werden
	    	 */
	    }
	    
	    /**
	     * Legt im Array map[][] die dem Objekt entsprechende Nummer an
	     * @param arrayx array x-Position
	     * @param arrayy array y-Position
	     * @param number Nummer, die angelegt werden soll
	     */
	    private static void setData(int arrayx, int arrayy, int number){
	    	EditorSetter.map[arrayx][arrayy]=number;
	    }
	    
	    /**
	     * Legt an Position pixelx, pixely die dem Objekt entsprechende Nummer an
	     * @param pixelx x-Position
	     * @param pixely y-Position
	     * @param number Nummer, die angelegt werden soll
	     */
	    private static void setDataPixel(int pixelx, int pixely, int number){
	    	EditorSetter.map[((pixelx-(pixelx%20))/20)][((pixely-(pixely%20))/20)]=number;
	    }
	    
	    /**
	     * Legt im Array die 1 für Wand an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createWall(int x, int y){
	    	setData(x, y, 1);
	    }
	    //Nötig für 'per Hand' erstellte xml-Dateien der Level
	    /**
	     * Legt im Array die 1 für Wand an
	     * @param x x-Position
	     * @param y y-Position 
	     * @param width Breite des Bereiches
	     * @param height Höhe des Bereiches
	     */
	    private static void createWall(int x, int y, int width, int height){
	    	for(int i=x; i<x+width; i++){
	    		for(int b=y; b<y+height; b++){
	    			setData(i, b, 1);
	    		}
	    	}
	    }
	    /**
	     * Legt im Array die 2 für Credit an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createCredits(int x, int y){
	    	setDataPixel(x,y-20,2);
	    }
	    /**
	     * Legt im Array die 3 für Leben an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createheart(int x, int y){
	    	setDataPixel(x,y-20,3);
	    }	
	    /**
	     * Legt im Array die 4 für Ziel an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createGoal(int x, int y){
	    	setData(x,y,4);
	    }
	    // Benötigt für 'von Hand' erstellte Räume
	    /**
	     * Legt im Array die 4 für Ziel an
	     * @param x x-Position
	     * @param y y-Position 
	     * @param width Breite des Bereiches
	     * @param height Höhe des Bereiches
	     */
	    private static void createGoal(int x, int y, int width, int height){
	    	for(int i=x; i<x+width; i++){
	    		for(int b=y; b<y+height; b++){
	    			setData(i, b, 4);
	    		}
	    	}
	    }	
	    /**
	     * Legt im Array die 5 für Schild an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createShield(int x, int y){
	    	setDataPixel(x,y-20,5);
	    }   
	    /**
	     * Legt im Array die 6 für Mana an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createMana(int x, int y){
	    	setDataPixel(x,y-20,6);
	    }	    
	    /**
	     * Legt im Array die 7 für Checkpoint an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createCheckpoint(int x, int y){
	    	setDataPixel(x,y,7);
	    }	
	    /**
	     * Legt im Array die 8 für Spawn an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createSpawn(int x, int y){
	    	setData(x,y,8);
	    }	 
	    /**
	     * Legt im Array die 9 für Shop an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createShop(int x, int y){
	    	setData(x,y,9);
	    }	    
	    /**
	     * Legt im Array eine Zahl von 11 bis 14 an, die einem Enemy mit spezieller Laufrichtung entspricht
	     * @param x x-Position
	     * @param y y-Position
	     * @param mx Laufvariable in x-Richtung
	     * @param my Laufvariable in y-Richtung
	     */
	    private static void createEnemy(int x, int y, int mx, int my){
	    	if(mx==1){
	    		if(my==0){
	    			setDataPixel(x,y,11);
	    		}else if(my==-1){
	    			setDataPixel(x,y,13);
	    		}
	    	}else if(mx==0){
	    		if(my==1){
	    			setDataPixel(x,y,12);
	    		}
	    	}else if(mx==-1){
	    		if(my==-1){
	    			setDataPixel(x,y,14);
	    		}
	    	}
	    }	 
	    /**
	     * Legt im Array die 11 für horizontales Monster an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createEnemy(int x, int y){
	    	setDataPixel(x,y,11);
	    }	
	    /**
	     * Legt im Array die 15 für Bouncy an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createBouncy(int x, int y){
	    	setDataPixel(x,y,15);
	    }	    
	    /**
	     * Legt im Array die 15 für Bouncy an
	     * @param x x-Position
	     * @param y y-Position 
	     * @param mx Laufvariable in x-Richtung
	     * @param my Laufvariable in y-Richtung
	     */
	    private static void createBouncy(int x, int y, int mx, int my){
	    	setDataPixel(x,y,15);
	    }	  
	    /**
	     * Legt im Array die 16 für Tracker an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createTracker(int x, int y){
	    	setDataPixel(x,y,16);
	    }	    
	    /**
	     * Legt im Array die 17 für eine Mine an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createMine(int x, int y){
	    	setData(x,y,17);
	    }	
	    /**
	     * Legt im Array die 17 für Mine an
	     * @param x x-Position
	     * @param y y-Position 
	     * @param width Breite des Bereiches
	     * @param height Höhe des Bereiches
	     */
	    private static void createMine(int x, int y, int width, int height){
	    	for(int i=x; i<x+width; i++){
	    		for(int b=y; b<y+height; b++){
	    			setData(i, b, 17);
	    		}
	    	}
	    }    
	    /**
	     * Legt im Array die 18 für einen Dalek an
	     * @param x x-Position
	     * @param y y-Position 
	     */
	    private static void createDalek(int x, int y){
	    	setData(x,y,18);
	    }	
	    /**
	     * Legt im Array einen wert von 20 bis 23 für eine Door an
	     * @param posx x-Position
	     * @param posy y-Position
	     * @param goalX x-Position der Zieltür
	     * @param goalY y-Position der Zieltür
	     * @param alignmentDoor Ausrichtung der Tür (horizontal/vertikal)
	     * @param walkingDirection Laufrichtung durch die Tür (up/right/down/left)
	     */
	    private static void createDoor(int posx, int posy, int goalX, int goalY, String alignmentDoor, String walkingDirection){
	    	if(alignmentDoor.equals("horizontal")){
	    		if(walkingDirection.equals("up")){
	    			setDataPixel(posx, posy ,20);
	    		}else{
	    			setDataPixel(posx, posy ,22);
	    		}
	    	}else{
	    		if(walkingDirection.equals("right")){
	    			setDataPixel(posx, posy ,21);
	    		}else{
	    			setDataPixel(posx, posy ,23);
	    		}
	    	}
	    	if(goalX>=0 && goalY>=0) {
	    		Door.createDoor(posx, posy, goalX, goalY);
	    	}
	    }
	    /**
	     * Legt im Array einen wert von 21 für eine vertikale nach rechts Tür an
	     * @param posx x-Position
	     * @param posy y-Position
	     * @param goalX x-Position der Zieltür
	     * @param goalY y-Position der Zieltür
	     */
	    private static void createDoor(int posx, int posy, int goalX, int goalY){
	    	setDataPixel(posx, posy, 21);
	    }
	    
	}