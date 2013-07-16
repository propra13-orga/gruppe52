package the.game.java;

import java.awt.Point;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class NetHandler {
	
	protected static int netID = -1;
	private int counter = 0;
	private int teamID = -1;
	public static  boolean updateRequired = false;
	public static boolean nextLevel = false;
	private long timeStampPlayer = 0;
	private long timeStampProjectile = 0;
	public static Queue<NetMessage> externalOutputQ = new LinkedList<NetMessage>();
	
	NetHandler() {
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * VIELLEICHT MIT NEXT LINE LANGEN CODE AUFTEILEN
	 * ...anstatt auf länge zu prüfen
	 * 
	 * 
	 */
	
	
	protected void handleInput(String input) {
		// Wenn input leer: return
		if(input.isEmpty())
			return;
		
		if(input.length()>NetMessage.LENGTH)
			System.out.println(input);
		
		// Wenn input länger als ein Paket: zerstückeln
		if(input.length()>NetMessage.LENGTH) {
			int start = 0;
			int end = NetMessage.LENGTH;
			// input in n Teile schneiden und an handler übergeben (n=Paketlänge)
			while(input.length()>=end) {
				doHandleInput(input.substring(start, end));
				start += end;
				end += end;
			}
		// Wenn input regulär: an handler übergeben
		} else {
			doHandleInput(input);
		}
		
	}
	
	private void doHandleInput(String input) {
		
		System.out.println(input);
		
		// Scanner erzeugen, um Message-String aus Buffer zu zerlegen
				Scanner s = new Scanner(input); //.useDelimiter( "" );
				
				// VORLAGE
				// _i 00 _t pos _n pla _x 0000 _y 0060
				
				// Erste Nachricht nach Verbindungsaufbau: ID-Vergabe durch Server
				if(input.startsWith("_id")) {
					try {
						
						s.findInLine("_id");
						netID = Integer.valueOf(s.next());
						Player.playerList.get(0).remoteID = netID;
						teamID = Integer.valueOf(s.next());
						Player.getPlayer(0).setTeamID(teamID);
						LevelCaller.setMPPlayerPositionToDefault();
						
					} catch (Exception ex) {
						System.err.println("ID VERGABE FEHLGESCHLAGEN: " + input);
					}
					
				} else if(input.startsWith("_new")) {
					// Neuen Spieler erstellen
					System.out.println("CREATE PLAYER!");
					
					s.findInLine("_new");
					int remoteID;
					while(s.hasNextInt()) {
						remoteID = Integer.valueOf(s.next());	// remoteID
						
						boolean existent = false;
						for(int a=0; a<Player.playerList.size(); a++) {
							if(Player.getPlayer(a).remoteID == remoteID) {
								existent = true;
								break;
							}
						}
						if(existent==false) {
							Settings.setPlayerCountPlusOne();
							Player.createPlayer(true, remoteID);
							System.out.println("PLAYER CREATED!");
							updateRequired = true;
						}
					}
					
					
				// Wenn es sich nicht um Updates der eigenen Daten oder eines ungültigen Clienten handelt
				} else if(input.startsWith("_i " + String.format("%02d", netID))==false && input.startsWith("_i -1")==false) {
					
					// ID ermitteln
					s.findInLine("_i");
					int ID = Integer.valueOf(s.next());
					
					// Handlung ermitteln
					s.findInLine("_t");
					
					// Handlungstag überprüfen
					switch(s.next()) {
					case "pos":		// Position
						try {
							s.findInLine("_n");							// OBJEKTTYP
							String type = s.next();						// typ
							int remoteID = Integer.valueOf(s.next());	// remoteID
							
							s.findInLine("_d");							// DATEN
							int x = Integer.valueOf(s.next());			// x-Position
							int y = Integer.valueOf(s.next());			// y-Position
							int a = Integer.valueOf(s.next());			// alive/dead
							if(s.hasNextInt()) {
								int mx = Integer.valueOf(s.next());
								if(s.hasNextInt()) {
									int my = Integer.valueOf(s.next());
									processPositionUpdate(type, remoteID, x, y, mx, my);
								} else {
									System.err.println("Error: doHandleInput(): my missing");
								}
							} else {
								if(s.findInLine("_a") != null) {
									processPositionUpdate(type, remoteID, x, y, Integer.valueOf(s.next()), a);			// weiterleiten
								} else {
									processPositionUpdate(type, remoteID, x, y, a);			// weiterleiten
								}
							}
							//System.out.println("Typ: " + type + "  x: " + x + "  y: " + y);
						} catch (Exception ex) {
							System.err.println("Paket verloren: " + input);
							ex.printStackTrace();
						}
						break;
					case "lvl":		// Level
						if(s.hasNext()) {
							switch(s.next()) {
							case "next": {
								int score = s.nextInt();
								NetRanking.scoreMap.put(ID, score);
								LevelCaller.setNextLevel();
								NetRanking.scoreMap.put(netID, Player.getPlayer(0).score.getScore());
								externalOutputQ.add(new NetMessage(NetHandler.netID, "lvl score " + Player.getPlayer(0).score.getScore()));
							}
								break;
							case "score": {
								int score = s.nextInt();
								NetRanking.scoreMap.put(ID, score);
							}
								break;
							}
						}
						break;
					case "pro":		// Projectile
						try {
							s.findInLine("_n");
							switch(s.next()) {
							case "bul":	// Standardkugel
								
								int remoteID = s.nextInt();
								s.findInLine("_d");
								
								int x = s.nextInt();
								int y = s.nextInt();
								int speed = s.nextInt();
								int vigor = s.nextInt();
								int spread = s.nextInt();
								int angle = s.nextInt();
								System.out.println("DO NOW");
								Projectile.createProjectile(x, y, speed, 0, vigor, spread, 
										Weapon.standardBullet, angle, "s" + getPlayerViaRemoteID(remoteID).spriteArmedID);
								
								break;
							}
						} catch (Exception ex) {
							System.err.println("Paket verloren: " + input + " // [Projektil]");
							ex.printStackTrace();
						}
						break;
					case "hit":		// Hit-Report
						try {
							s.findInLine("_n");
							switch(s.next()) {
							case "pla":	// PLAYER
								
								int remoteID = s.nextInt();
								s.findInLine("_d");
								
								int damage = s.nextInt();
								int damageType = s.nextInt();
								
								System.out.println("Schaden: " + damage + "    (aus hitreport)");
								
								Projectile.addPlayerHitAnimationViaRemoteID(remoteID);
								getPlayerViaRemoteID(remoteID).reduceHealthPoints(damage, damageType);
								
								break;
							}
						} catch (Exception ex) {
							System.err.println("Paket verloren: " + input + " // [Projektil]");
							ex.printStackTrace();
						}
						break;
					case "eve":		// Event
						
						break;
					}
				}
				s.close();
	}
	
	public static Player getPlayerViaRemoteID(int remoteID) {
		for(int a=0; a<Player.playerList.size(); a++) {
			if(Player.getPlayer(a).remoteID==remoteID) {
				return Player.getPlayer(a);
			}
		}
		return null;
	}
	
	protected void processPositionUpdate(String obj, int remoteID, int x, int y, int alive) {
		switch(obj) {
		case "pla":
			for(int playerID=0; playerID<Player.playerList.size(); playerID++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
	    		if(Player.getPlayer(playerID).isRemote()) {
	    			if(Player.getPlayer(playerID).remoteID == remoteID) {
	    				System.out.println("SET: " + remoteID);
	    				Player.getPlayer(playerID).setPosition(x, y);
	    				if(alive<=0)
	    					Player.getPlayer(playerID).setLifeStatusFromNetwork(false);
	    				else
	    					Player.getPlayer(playerID).setLifeStatusFromNetwork(true);
	    				break;
	    			}
	    		}
	    	}
			break;
		case "ene":
			if(Enemy.enemyList.size()>remoteID) 			// Wenn remoteID nicht ungültig:
				Enemy.getEnemy(remoteID).setPosition(x, y);	// Position des Feindes updaten
			break;
		}
	}
	
	protected void processPositionUpdate(String obj, int remoteID, int x, int y, int angle, int alive) {
		switch(obj) {
		case "pla":
			for(int playerID=0; playerID<Player.playerList.size(); playerID++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
	    		if(Player.getPlayer(playerID).isRemote()) {
	    			if(Player.getPlayer(playerID).remoteID == remoteID) {
	    				System.out.println("SET: " + remoteID);
	    				Player.getPlayer(playerID).setPosition(x, y);
	    				Player.getPlayer(playerID).angle = angle;
	    				Player.getPlayer(playerID).rotatePlayerImg();
	    				if(alive<=0)
	    					Player.getPlayer(playerID).setLifeStatusFromNetwork(false);
	    				else
	    					Player.getPlayer(playerID).setLifeStatusFromNetwork(true);
	    				break;
	    			}
	    		}
	    	}
			break;
		case "ene":
			if(Enemy.enemyList.size()>remoteID) 			// Wenn remoteID nicht ungültig:
				Enemy.getEnemy(remoteID).setPosition(x, y);	// Position des Feindes updaten
			break;
		}
	}
	
	protected void processPositionUpdate(String obj, int remoteID, int x, int y, int mx, int my, int alive) {
		switch(obj) {
		case "pla":
			
			break;
		case "ene":
			// ....
			break;
		}
	}

	
	protected Queue<NetMessage> getOutputSchedule() {
		Queue<NetMessage> q = new LinkedList<NetMessage>();
		
		if(netID<0)
			return q;
		
		int x = 0;
		int y = 0;
		int mx = 0;
		int my = 0;
		int r = -1;
		int a = 0;
		boolean al;
		
		/**		PLAYER		*/
		if(System.currentTimeMillis() - timeStampPlayer > 50) {
			for(int playerID=0; playerID<Player.playerList.size(); playerID++) {									// Für jedes Element der PlayerListe, von 0 bis Ende
	    		Player current = Player.getPlayer(playerID);
				if(current.isRemote()==false) {
	    			x = current.getX();
	    			y = current.getY();
	    			r = current.remoteID;
	    			a = (int) Controls.getAngle();
	    			al = current.alive;
	    			if(current.lastx!=x || current.lasty!=y || updateRequired) {
	    				q.add(new NetMessage(netID, "pla",r , x, y, a, al));
	    				current.lastx = x;
	    				current.lasty = y;
	    				updateRequired = false;
	    			}
	    		}
	    	}
			timeStampPlayer = System.currentTimeMillis();
		}
		
		
		/**		ENEMY		*/
		if(netID==0) {	// NUR DATEN VOM HOST!!
			for(int enemyID=0; enemyID<Enemy.enemyList.size(); enemyID++) {									// Für jedes Element der EnemyListe, von 0 bis Ende
	    		
	    			x = Enemy.getEnemy(enemyID).getX();
	    			y = Enemy.getEnemy(enemyID).getY();
	    			if(Enemy.getEnemy(enemyID).lastx!=x || Enemy.getEnemy(enemyID).lasty!=y) {
	    				q.add(new NetMessage(netID, "ene", enemyID , x, y));
	    				Enemy.getEnemy(enemyID).lastx = x;
	    				Enemy.getEnemy(enemyID).lasty = y;
	    			}
	    	}
		}
				
		/**		PROJEKTILE		*/
		for(int projID=0; projID<Projectile.projectileList.size(); projID++) {									// Für jedes Element der EnemyListe, von 0 bis Ende
    		Projectile current = Projectile.getProjectile(projID);
			if(current.isValid()) {
    			if(current.origin.equals("s" + Player.getPlayer(0).spriteArmedID)) {
    				if(current.timeStamp >= timeStampProjectile) {
    					x = current.getX();
    					y = current.getY();
    					q.add(new NetMessage(netID, "bul", Player.getPlayer(0).remoteID , x, y, 
    							current.speed,
    							current.vigor,
    							current.bulletSpread,
    							current.angle));
    				}
    			}
    		}
		}
		timeStampProjectile = System.currentTimeMillis();
		
		/**		EXTERNE NACHRICHTEN (hitreport,...)		*/
		while(externalOutputQ.size()>0) {
			q.add(externalOutputQ.poll());
		}

		/**		LEVELSTATUS		*/
		if(nextLevel) {
			nextLevel = false;
		}
		
		
		
		counter++;
		return q;
	}
	
}
