package the.game.java;

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
 * Sorgt daf�r, dass Spielst�nde gespeichert und geladen werden k�nnen.
 *
 */
public class Savegame {
	
	/**
	 * Methode zum speichern des Spielstandes
	 */
	public static void savegame() {
		try {
			
			/** VORBEREITEN */
			
			// Factory erstellen
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			// Builder in Factory erstellen
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// Mit Builder ein neues Dokument erstellen
			Document doc = docBuilder.newDocument();
			
			/** DOKUMENT F�LLEN */
			
			// Root Element
			Element rootElement = doc.createElement("sav");
			doc.appendChild(rootElement);
			
			// SPAWN
			{
				// ELEMENT
				Element element = doc.createElement("spawn");								// Element erstellen (mit Namen)
				rootElement.appendChild(element);											// Element �bergeben als Kind des RootElementes
				
				// ATTRIBUTES
				Attr x = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
				x.setValue(String.valueOf(Player.playerList.get(0).getXAsFieldPos()));		// Wert an obiges Attribut �bergeben
				element.setAttributeNode(x);												// Attribut an obiges Element �bergeben
				
				Attr y = doc.createAttribute("y");											// REST IST ANALOG !
				y.setValue(String.valueOf(Player.playerList.get(0).getYAsFieldPos()));
				element.setAttributeNode(y);
				
				Attr life = doc.createAttribute("life");
				life.setValue(String.valueOf(Player.playerList.get(0).getLives()));
				element.setAttributeNode(life);
				
				Attr hp = doc.createAttribute("hp");
				hp.setValue(String.valueOf(Player.playerList.get(0).getHealthPoints()));
				element.setAttributeNode(hp);
				
				Attr mana = doc.createAttribute("mana");
				mana.setValue(String.valueOf(Player.getMana(0).getMana()));
				element.setAttributeNode(mana);
				
				Attr score = doc.createAttribute("score");
				score.setValue(String.valueOf(Player.playerList.get(0).score.getScore()));
				element.setAttributeNode(score);
			}
			
			if(Settings.isMultiplayer()) {
				for(int a=0; a<LevelCaller.spawnMap.size(); a++) {
					
				}
			}
			
			// Weapon
			// Alle Spieler durchgehen
			for(int playerID=0; playerID<Player.playerList.size(); playerID++) {
				// Alle Waffen im Inventar (alle Waffen die in Benutzung sind) durchgehen
				for(int weaponInUseID=0; weaponInUseID<Player.playerList.get(playerID).getWeaponInUseCount(); weaponInUseID++) {
					// ELEMENT
					Element element = doc.createElement("weapon");
					rootElement.appendChild(element);
					
					// ATTRIBUTES
					Attr playerid = doc.createAttribute("playerid");
					playerid.setValue(String.valueOf(playerID));
					element.setAttributeNode(playerid);
					
					Attr weaponid = doc.createAttribute("weaponid");
					weaponid.setValue(String.valueOf(Player.playerList.get(playerID).getWeaponID(weaponInUseID)));
					element.setAttributeNode(weaponid);
					
					Attr magcount = doc.createAttribute("magcount");
					magcount.setValue(String.valueOf(Player.playerList.get(playerID).getMagCount(weaponInUseID)));
					element.setAttributeNode(magcount);
					
					Attr magsize = doc.createAttribute("magsize");
					magsize.setValue(String.valueOf(Player.playerList.get(playerID).getCurrentMagSize(weaponInUseID)));
					element.setAttributeNode(magsize);
				}
			}
			
			// Items
			// Alle Spieler durchgehen
			for(int playerID=0; playerID<Player.playerList.size(); playerID++) {
				for(int index=0; index<Item.itemList.size(); index++) {
					// ELEMENT
					Element element = doc.createElement("item");
					rootElement.appendChild(element);
					
					// ATTRIBUTES
					Attr playerid = doc.createAttribute("playerid");
					playerid.setValue(String.valueOf(playerID));
					element.setAttributeNode(playerid);
					
					Attr itemid = doc.createAttribute("itemid");
					itemid.setValue(String.valueOf(Item.itemList.get(index).getItemID()));
					element.setAttributeNode(itemid);
				}
			}
			
			// DOOR
			for(int a=0; a<Door.doorList.size(); a++){
				// ELEMENT
				Element element = doc.createElement("door");
				rootElement.appendChild(element);
				
				// ATTRIBUTES
				Attr x = doc.createAttribute("x");
				x.setValue(String.valueOf(Door.doorList.get(a).getX()));
				element.setAttributeNode(x);
				
				Attr y = doc.createAttribute("y");
				y.setValue(String.valueOf(Door.doorList.get(a).getY()));
				element.setAttributeNode(y);
				
				Attr goalX = doc.createAttribute("goalX");
				goalX.setValue(String.valueOf(Door.doorList.get(a).getGoalX()));
				element.setAttributeNode(goalX);
				
				Attr goalY = doc.createAttribute("goalY"); 
				goalY.setValue(String.valueOf(Door.doorList.get(a).getGoalY()));
				element.setAttributeNode(goalY);
				
				Attr alignment = doc.createAttribute("alignment");
				alignment.setValue(Door.doorList.get(a).getAlignment());
				element.setAttributeNode(alignment);
				
				Attr walking = doc.createAttribute("walkingDirection");
				walking.setValue(Door.doorList.get(a).getWalkingDirection());
				element. setAttributeNode(walking);
			}
			// NPC
			// alle NPCs durchgehen
			for(int a=0; a<NPC.npcList.size(); a++) {
				// ELEMENT
				Element element = doc.createElement("npc");
				rootElement.appendChild(element);
				
				// ATTRIBUTES
				Attr id = doc.createAttribute("id");
				id.setValue(String.valueOf(NPC.npcList.get(a).getNr()));
				element.setAttributeNode(id);
				
				Attr x = doc.createAttribute("x");
				x.setValue(String.valueOf(NPC.npcList.get(a).getX()));
				element.setAttributeNode(x);
				
				Attr y = doc.createAttribute("y");
				y.setValue(String.valueOf(NPC.npcList.get(a).getY()-LevelCreator.distancePix));
				element.setAttributeNode(y);
				
				Attr look = doc.createAttribute("look");
				look.setValue(NPC.npcList.get(a).getDirection());
				element.setAttributeNode(look);
			}
			
			// NPC SHOP
			// alle ShopNPCs durchgehen
			for(int a=0; a<NPC.npcShopList.size(); a++) {
				// ELEMENT
				Element element = doc.createElement("shop");
				rootElement.appendChild(element);
				
				// ATTRIBUTES			
				Attr x = doc.createAttribute("x");
				x.setValue(String.valueOf(NPC.npcShopList.get(a).getX()));
				element.setAttributeNode(x);
				
				Attr y = doc.createAttribute("y");
				y.setValue(String.valueOf(NPC.npcShopList.get(a).getY()-LevelCreator.distancePix));
				element.setAttributeNode(y);
			}
			
			// GOODIES
			// alle goodies durchgehen
			for(int a=0; a<Goodies.goodiesList.size(); a++) {
				// ELEMENT
				Element element = doc.createElement("goodie");
				rootElement.appendChild(element);
				
				// ATTRIBUTES
				Attr x = doc.createAttribute("x");
				x.setValue(String.valueOf(Goodies.getX(a)));
				element.setAttributeNode(x);
				
				Attr y = doc.createAttribute("y");
				y.setValue(String.valueOf(Goodies.getY(a)));
				element.setAttributeNode(y);
				
				Attr type = doc.createAttribute("type");
				type.setValue(String.valueOf(Goodies.getType(a)));
				element.setAttributeNode(type);
			}
			
			// Checkpoints
			// alle Checkpoints durchgehen
			for(int a=0; a<Checkpoints.checkList.size(); a++) {
				// ELEMENT
				Element element = doc.createElement("checkpoint");
				rootElement.appendChild(element);
				
				// ATTRIBUTES
				Attr x = doc.createAttribute("x");
				x.setValue(String.valueOf(Checkpoints.checkList.get(a).getX()));
				element.setAttributeNode(x);
				
				Attr y = doc.createAttribute("y");
				y.setValue(String.valueOf(Checkpoints.checkList.get(a).getY()-LevelCreator.distancePix));
				element.setAttributeNode(y);
				
				Attr type = doc.createAttribute("activated");
				type.setValue(String.valueOf(Checkpoints.checkList.get(a).getIsActivated()));
				element.setAttributeNode(type);
			}
						
			// ENEMY
			// alle Enemies durchgehen
			for(int a=0; a<Enemy.enemyList.size(); a++) {
				if(Enemy.isAlive(a)) {
					
					// ELEMENT
					Element enemy = doc.createElement(Enemy.getType(a));
					rootElement.appendChild(enemy);
					
					// ATTRIBUTES
					Attr x = doc.createAttribute("x");
					x.setValue(String.valueOf(Enemy.getX(a)));
					enemy.setAttributeNode(x);
					
					Attr y = doc.createAttribute("y");
					y.setValue(String.valueOf(Enemy.getY(a)-LevelCreator.distancePix));
					enemy.setAttributeNode(y);
					
					Attr mx = doc.createAttribute("movex");
					mx.setValue(String.valueOf(Enemy.getMX(a)));
					enemy.setAttributeNode(mx);
					
					Attr my = doc.createAttribute("movey");
					my.setValue(String.valueOf(Enemy.getMY(a)));
					enemy.setAttributeNode(my);
					
				}
			}
			
			// TRAPS
			// alle Traps durchgehen
			for(int a=0; a<Traps.trapList.size(); a++) {
				if(Traps.getValid(a)) {		// nur intakte Fallen speichern !
					// ELEMENT
					Element element = doc.createElement("mine");
					if(Traps.getType(a)==1)
						element = doc.createElement("dalek");
					rootElement.appendChild(element);
					
					// ATTRIBUTES
					Attr x = doc.createAttribute("x");
					x.setValue(String.valueOf((Traps.getX(a)/20)));
					element.setAttributeNode(x);
					
					Attr y = doc.createAttribute("y");
					y.setValue(String.valueOf(((Traps.getY(a)/20)-1)));
					element.setAttributeNode(y);
				}
			}
			
			// DARKNESS
			if(DisplayManager.darkness) {	// wenn dunkler Layer aktiv, dann speichern
				// ELEMENT
				Element element = doc.createElement("darkness");
				rootElement.appendChild(element);
			}
			
			// MAP
			// itemMapArray Spaltenweise durchgehen
			for(int a=0; a<LevelCreator.getItemMapDimensions(0); a++) {	// Spaltenweise
				// itemMapArray Zeilenweise durchgehen
				for(int b=0; b<LevelCreator.getItemMapDimensions(1); b++) {	// Zeilenweise
					switch(LevelCreator.itemMap[a][b]) {
					case 0:
						break;
					case 1:		// WALL
					{
						// ELEMENT
						Element element = doc.createElement("wall");
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b-1));
						element.setAttributeNode(y);
					}
						break;
					case 3:		// GOAL
					{
						// ELEMENT
						Element element = doc.createElement("goal");
						rootElement.appendChild(element);
						
						// ATTRIBUTES
						Attr x = doc.createAttribute("x");
						x.setValue(String.valueOf(a));
						element.setAttributeNode(x);
						
						Attr y = doc.createAttribute("y");
						y.setValue(String.valueOf(b));
						element.setAttributeNode(y);
					}
						break;
					default:
						// MULTIPLAYER GOAL
						for(int c=0; c<10; c++) {
							if(LevelCreator.itemMap[a][b]>=50 && LevelCreator.itemMap[a][b]<=59) {
								// ELEMENT
								Element element = doc.createElement("mpgoal");
								rootElement.appendChild(element);
								
								// ATTRIBUTES
								Attr x = doc.createAttribute("x");
								x.setValue(String.valueOf(a));
								element.setAttributeNode(x);
								
								Attr y = doc.createAttribute("y");
								y.setValue(String.valueOf(b));
								element.setAttributeNode(y);
								
								Attr team = doc.createAttribute("team");
								team.setValue(String.valueOf(LevelCreator.itemMap[a][b]-50));
								element.setAttributeNode(team);
							}
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
			
			// StreamResult erstellen mit Zielpfad f�r die Zieldatei
			StreamResult result = new StreamResult(new File("src/the/game/java/savegames/save.xml"));
			
			/** AUSGEBEN */
			
			// DomSource und StreamResult an Transformer �bergeben
			transformer.transform(source, result);
			
			System.out.println("Gespeichert!");
	 
		} catch (ParserConfigurationException pce) {	// Konfigurationsfehler abfangen
			pce.printStackTrace();
		} catch (TransformerException tfe) {			// TransformationsException abfangen
			tfe.printStackTrace();
		}
	}
}
