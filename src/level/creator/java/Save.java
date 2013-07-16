package level.creator.java;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Sorgt dafür, dass erstellte Leveldateien gespeichert werden können
 * Es wird der Array-Inhalt ausgelesen und dementsprechend der xml-Code erstellt
 *
 */
public class Save {
	
	private static boolean multiplayer=false;
	
	/**
	 * Gibt den Inhalt des Arrays an Position a,b an.
	 * @param a = Position in x-Richtung
	 * @param b = Position in y-Richtung
	 * @return gibt den im Array an der entsprechenden Position gespeicherten Wert zurück
	 */
	private static int getData(int a, int b) {
		return EditorSetter.map[a][b];
	}
	
	/**
	 * Eigentliche Methode zum Speichern
	 */
	public static void savegame(String path) {
		try {
			
			/** VORBEREITEN */
			
			// Factory erstellen
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			// Builder in Factory erstellen
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// Mit Builder ein neues Dokument erstellen
			Document doc = docBuilder.newDocument();
			
			/** DOKUMENT FÜLLEN */
			
			// Root Element
			Element rootElement = doc.createElement("map");
			doc.appendChild(rootElement);
			
			if(EditorSetter.dark){
				Element element = doc.createElement("darkness");
				rootElement.appendChild(element);
			}
			
			if(EditorSetter.multiplay){
				Element element = doc.createElement("multiplayer");
				rootElement.appendChild(element);
				multiplayer=true;
			}else{
				multiplayer=false;
			}
			//TODO:
			for(int i=0; i<Door.doorList.size(); i++){
				System.out.println(Door.doorList.get(i).x +" "+ Door.doorList.get(i).y +" "+ Door.doorList.get(i).goalx +" "+ Door.doorList.get(i).goaly);
			}
			
			/**
			 * Spawnposition Spieler 1 / Zielposition Spieler 2
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==8){
						if(multiplayer==false){
							// ELEMENT
							Element element = doc.createElement("spawn");								// Element erstellen (mit Namen)
							rootElement.appendChild(element);											// Element übergeben als Kind des RootElementes
							
							// ATTRIBUTES
							Attr x = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
							x.setValue(String.valueOf(a));												// Wert an obiges Attribut übergeben
							element.setAttributeNode(x);												// Attribut an obiges Element übergeben
							
							Attr y = doc.createAttribute("y");											// REST IST ANALOG !
							y.setValue(String.valueOf(b+1));
							element.setAttributeNode(y);
						}else if(multiplayer==true){
							Element element1 = doc.createElement("spawn");
							rootElement.appendChild(element1);
							
							Attr x1 = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
							x1.setValue(String.valueOf(a));												// Wert an obiges Attribut übergeben
							element1.setAttributeNode(x1);												// Attribut an obiges Element übergeben
							
							Attr y1 = doc.createAttribute("y");											// REST IST ANALOG !
							y1.setValue(String.valueOf(b+1));
							element1.setAttributeNode(y1);
							
							Attr team1 = doc.createAttribute("team");
							team1.setValue("0");
							element1.setAttributeNode(team1);
							
							//---------------------------------------------------------------------------
							Element element2 = doc.createElement("mpgoal");
							rootElement.appendChild(element2);
							
							Attr x2 = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
							x2.setValue(String.valueOf(a));												// Wert an obiges Attribut übergeben
							element2.setAttributeNode(x2);												// Attribut an obiges Element übergeben
							
							Attr y2 = doc.createAttribute("y");											// REST IST ANALOG !
							y2.setValue(String.valueOf(b+1));
							element2.setAttributeNode(y2);
							
							Attr team2 = doc.createAttribute("team");
							team2.setValue("1");
							element2.setAttributeNode(team2);
						}
					}
				}
			}
			
			/**
			 * Door/Portal
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==20 || getData(a, b)==21 || getData(a, b)==22 || getData(a, b)==23){
						// ELEMENT
						Element element = doc.createElement("door");
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a*20));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b*20+20));
						element.setAttributeNode(y);
						
						Attr goalX = doc.createAttribute("goalX");
						goalX.setValue(String.valueOf(Door.findCompatibleGoalPositionX(a*20, b*20)));
						element.setAttributeNode(goalX);
						
						Attr goalY = doc.createAttribute("goalY"); 
						goalY.setValue(String.valueOf(Door.findCompatibleGoalPositionY(a*20, b*20)+20));
						element.setAttributeNode(goalY);
						
						Attr alignment = doc.createAttribute("alignment");
						String ali  = new String();
						switch(getData(a, b)){
						case 20:
						case 22:
							ali = "horizontal";
							break;
						case 21:
						case 23:
							ali = "vertikal";
							break;
						}
						alignment.setValue(ali);
						element.setAttributeNode(alignment);
						
						Attr walking = doc.createAttribute("walkingDirection");
						String walk = new String();
						switch(getData(a, b)){
						case 20:
							walk = "up";
							break;
						case 21:
							walk = "right";
							break;
						case 22:
							walk = "down";
							break;
						case 23:
							walk = "left";
							break;
						}
						walking.setValue(walk);
						element. setAttributeNode(walking);
					}
				}
			}

			/**
			 * Shop
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==9){
						// ELEMENT
						Element element = doc.createElement("shop");
						rootElement.appendChild(element);
						
						// ATTRIBUTES			
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a*20));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b*20));
						element.setAttributeNode(y);
					}
				}
			}
			
			
			/**
			 * Goodies
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==2 || getData(a, b)==3 || getData(a, b)==5 || getData(a, b)==6){
						// ELEMENT
						Element element = doc.createElement("goodie");
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a*20));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b*20+20));
						element.setAttributeNode(y);
						
						Attr type = doc.createAttribute("type");
						String typ = new String();
						switch(getData(a, b)){
						case 2:
							typ = "credits";
							break;
						case 3:
							typ = "heart";
							break;
						case 5:
							typ = "shield";
							break;
						case 6:
							typ = "mana";
							break;
						}
						
						type.setValue(typ);
						element.setAttributeNode(type);
					}
				}
			}
			
			/**
			 * Checkpoints
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==7){
						// ELEMENT
						Element element = doc.createElement("checkpoint");
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a*20));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b*20));
						element.setAttributeNode(y);
					}
				}
			}
						
			/**
			 * Enemy
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==11 || getData(a, b)==12 || getData(a, b)==13 || getData(a, b)==14 || getData(a, b)==15 || getData(a, b)==16){
						
						String typ = new String();
						int movx=0;
						int movy=0;
						
						switch(getData(a, b)){
						case(11):
							typ="enemy";
							movx=1;
							movy=0;
							break;
						case(12):
							typ="enemy";
							movx=0;
							movy=1;
							break;
						case(13):
							typ="enemy";
							movx=1;
							movy=-1;
							break;
						case(14):
							typ="enemy";
							movx=-1;
							movy=-1;
							break;
						case(15):
							typ="bouncy";
							movx=-1;
							movy=1;
							break;
						case(16):
							typ="tracker";
							break;
						}
						
						// ELEMENT
						Element enemy = doc.createElement(typ);
						rootElement.appendChild(enemy);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a*20));
						enemy.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b*20));
						enemy.setAttributeNode(y);
						
						if(typ.equals("tracker")==false){
							Attr mx = doc.createAttribute("movex");
							mx.setValue(String.valueOf(movx));
							enemy.setAttributeNode(mx);
							
							Attr my = doc.createAttribute("movey");
							my.setValue(String.valueOf(movy));
							enemy.setAttributeNode(my);
						}
					}
				}
			}
			
			/**
			 * Traps (Daleks/Minen)
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==17 || getData(a,b)==18){
						// ELEMENT
						Element element = doc.createElement("mine");
						if(getData(a, b)==18){
							element = doc.createElement("dalek");
						}
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b));
						element.setAttributeNode(y);
					}
				}
			}
			
			/**
			 * Wall
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==1){
						// ELEMENT
						Element element = doc.createElement("wall");
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b));
						element.setAttributeNode(y);
					}
				}
			}
			
			/**
			 * Goal
			 */
			for(int a=0; a<EditorSetter.arrayLenghtX; a++){
				for(int b=0; b<EditorSetter.arrayLenghtY; b++){
					if(getData(a, b)==4){
						if(multiplayer==false){
							// ELEMENT
							Element element = doc.createElement("goal");
							rootElement.appendChild(element);
							
							// ATTRIBUTES
							Attr x = doc.createAttribute("x");
							x.setValue(String.valueOf(a));
							element.setAttributeNode(x);
							
							Attr y = doc.createAttribute("y");
							y.setValue(String.valueOf(b+1));
							element.setAttributeNode(y);
						}else if(multiplayer==true){
							Element element1 = doc.createElement("spawn");
							rootElement.appendChild(element1);
							
							Attr x1 = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
							x1.setValue(String.valueOf(a));												// Wert an obiges Attribut übergeben
							element1.setAttributeNode(x1);												// Attribut an obiges Element übergeben
							
							Attr y1 = doc.createAttribute("y");											// REST IST ANALOG !
							y1.setValue(String.valueOf(b+1));
							element1.setAttributeNode(y1);
							
							Attr team1 = doc.createAttribute("team");
							team1.setValue("1");
							element1.setAttributeNode(team1);
							
							//---------------------------------------------------------------------------
														
							Element element2 = doc.createElement("mpgoal");
							rootElement.appendChild(element2);
							
							Attr x2 = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
							x2.setValue(String.valueOf(a));												// Wert an obiges Attribut übergeben
							element2.setAttributeNode(x2);												// Attribut an obiges Element übergeben
							
							Attr y2 = doc.createAttribute("y");											// REST IST ANALOG !
							y2.setValue(String.valueOf(b+1));
							element2.setAttributeNode(y2);
							
							Attr team2 = doc.createAttribute("team");
							team2.setValue("0");
							element2.setAttributeNode(team2);
						}
					}
				}
			}
			
			
			/** AUSGABE VORBEREITEN */
			
			// Transformer Factory erstellen
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			
			// Transformer in Factory erstellen
			Transformer transformer = transformerFactory.newTransformer();
			
			// DOMSource mit dem oben erstellen Dokument erstellen
			DOMSource source = new DOMSource(doc);
			
			// StreamResult erstellen mit Zielpfad für die Zieldatei
			StreamResult result = new StreamResult(new File(path));
			
			/** AUSGEBEN */
			
			// DomSource und StreamResult an Transformer übergeben
			transformer.transform(source, result);
			
			System.out.println("Gespeichert!");
	 
		} catch (ParserConfigurationException pce) {	// Konfigurationsfehler abfangen
			pce.printStackTrace();
		} catch (TransformerException tfe) {			// TransformationsException abfangen
			tfe.printStackTrace();
		}
	}
}
