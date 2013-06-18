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

public class Savegame {
	public static void savegame() {
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
			Element rootElement = doc.createElement("sav");
			doc.appendChild(rootElement);
			
			// SPAWN
			{
				// ELEMENT
				Element element = doc.createElement("spawn");								// Element erstellen (mit Namen)
				rootElement.appendChild(element);											// Element übergeben als Kind des RootElementes
				
				// ATTRIBUTES
				Attr x = doc.createAttribute("x");											// Attribut erstellen (mit Namen)
				x.setValue(String.valueOf(Player.playerList.get(0).getXAsFieldPos()));		// Wert an obiges Attribut übergeben
				element.setAttributeNode(x);												// Attribut an obiges Element übergeben
				
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
				mana.setValue(String.valueOf(Mana.manaList.get(0).getMana()));
				element.setAttributeNode(mana);
				
				Attr score = doc.createAttribute("score");
				score.setValue(String.valueOf(Score.scoreList.get(0).getScore()));
				element.setAttributeNode(score);
			}
			
			// Weapon
			// Alle Spieler durchgehen
			for(int playerID=0; playerID<Player.playerList.size(); playerID++) {
				// Alle Waffen im Inventar (alle Waffen die in Benutzung sind) durchgehen
				for(int weaponInUseID=0; weaponInUseID<WeaponManager.weaponManagerList.get(playerID).weaponInUseList.size(); weaponInUseID++) {
					// ELEMENT
					Element element = doc.createElement("weapon");
					rootElement.appendChild(element);
					
					// ATTRIBUTES
					Attr playerid = doc.createAttribute("playerid");
					playerid.setValue(String.valueOf(playerID));
					element.setAttributeNode(playerid);
					
					Attr weaponid = doc.createAttribute("weaponid");
					weaponid.setValue(String.valueOf(WeaponManager.getWeaponID(playerID, weaponInUseID)));
					element.setAttributeNode(weaponid);
					
					Attr magcount = doc.createAttribute("magcount");
					magcount.setValue(String.valueOf(WeaponManager.getMagCount(playerID, weaponInUseID)));
					element.setAttributeNode(magcount);
					
					Attr magsize = doc.createAttribute("magsize");
					magsize.setValue(String.valueOf(WeaponManager.getCurrentMagSize(playerID, weaponInUseID)));
					element.setAttributeNode(magsize);
				}
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
			
			// TRACKER
			// alle Tracker durchgehen
			for(int a=0; a<Tracker.trackerList.size(); a++) {
				if(Tracker.isAlive(a)) {		// nur speichern, wenn noch am Leben. Leichen werden also nicht übernommen !
					// ELEMENT
					Element element = doc.createElement("tracker");
					rootElement.appendChild(element);
					
					// ATTRIBUTES
					Attr x = doc.createAttribute("x");
					x.setValue(String.valueOf(Tracker.getX(a)));
					element.setAttributeNode(x);
					
					Attr y = doc.createAttribute("y");
					y.setValue(String.valueOf(Tracker.getY(a)));
					element.setAttributeNode(y);
				}
			}
			
			// ENEMY
			// alle Enemies durchgehen
			for(int a=0; a<Enemy.monsterList.size(); a++) {
				if(Enemy.isAlive(a)) {
					// ELEMENT
					Element enemy = doc.createElement("enemy");
					if(Enemy.getType(a)==1)
						enemy = doc.createElement("bouncy");
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
						y.setValue(String.valueOf(b));
						element.setAttributeNode(y);
					}
						break;
					case 4:		// GOAL
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
			StreamResult result = new StreamResult(new File("src/the/game/java/savegames/save.xml"));
			
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
