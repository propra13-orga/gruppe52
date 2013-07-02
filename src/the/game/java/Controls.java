package the.game.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

public class Controls {
	
	// TODO: Angle berechnung für Controller und Maus verknüpfen
	// TODO: isMouseControlled durch Settings ersetzen
	
	public static Controls controls;
	
	// Steuerung
	private int p1_Up;
	private int p1_Le;
	private int p1_Do;
	private int p1_Ri;
	
	private int p1_Fire;
	private int p1_NextW;
	private int p1_PrevW;
	
	// Steuerungsmechanik
	private Robot robot;
	private boolean isMouseControlled = true;
	private double mx;
	private double my;
	private double px;
	private double py;
	private double mousex = 10;
	private double mousey = 0;
	private int startCounter = 0;
	private boolean isUp = false;
	private boolean isRight = false;
	
	private double angle = 0;
	
	private Controller joystick;
	private Event event;
	public boolean isJoystickAvailable = false;
	
	private Controls() {
		// Steuerung - Bewegung
		p1_Up = KeyEvent.VK_W;
		p1_Le = KeyEvent.VK_A;
		p1_Do = KeyEvent.VK_S;
		p1_Ri = KeyEvent.VK_D;
		
		// Steuerung - Waffen
		p1_Fire = KeyEvent.VK_SPACE;
		p1_PrevW = KeyEvent.VK_Q;
		p1_NextW = KeyEvent.VK_E;
		
		// Robot erstellen
		try {
			robot = new Robot();
		} catch (AWTException e) {
	    	e.printStackTrace();
	    }
		
		// GAMEPAD
		joystick = null;																			// joystick auf Null setzen
		event = new Event();																		// neues Event für updateGamePad()
		for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {		// Alle Steuerungsgeräte des Computers durchgehen und jeweils in variable c schreiben
			//System.out.println("I tried this one: " + c + " and it's a: " + c.getType());
			if (c.getType() == Controller.Type.STICK || c.getType() == Controller.Type.GAMEPAD) {	// Wenn der Controller in c ein Joystick oder ein Gamepad ist:
				joystick = c;																		// c wird in joystick geschrieben
				System.out.println(joystick.getName());												
				break;																				// wenn Gerät gefunden, Schleife abbrechen, da ohnehin nur ein Gerät verwendet werden kann (zurzeit)
			}
        }
		
		if (joystick == null) {																		// Wenn kein Joystick gefunden wurde:
			System.err.println("No joystick was found.");											// Meldung printen
			isJoystickAvailable = false;															// Status setzen
			Settings.setControlsToMouse();															// Mouse als Standardsteuerung bezeichnen
		} else {
			isJoystickAvailable = true;																// Status setzen
			Settings.setControlsToGamePad();														// Gamepad als Standardsteuerung bezeichnen
        }
        
	}
	
	public static void createControls() {
		controls = new Controls();
	}
	
	/**     GAMEPAD     */
	public void updateGamePad() {
		if(Settings.isControledByGamePad()==false || isJoystickAvailable==false)
			return;
		
		joystick.poll();
		
		while(joystick.getEventQueue().getNextEvent(event)) {
    		//System.out.println(event.getComponent().getIdentifier());
			//System.out.println(joystick.getComponent(Identifier.Axis.RX));
    		
    		if(event.getComponent().getIdentifier().equals(Identifier.Axis.X)) {
    			px = event.getValue();
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Axis.Y)) {
    			py = event.getValue();
    			
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Axis.Z)) {	// entspricht X
    			mx = event.getValue();
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Axis.RZ)) { // entspricht Y
    			my = event.getValue();
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Button._5)) { // FEUERN
    			if(joystick.getComponent(Identifier.Button._5).getPollData()>0)	// Wenn gedrückt, feuer
    				Player.playerList.get(0).setFireStatus(true);
    			else															// Wenn losgelassen, feuer stoppen
    				Player.playerList.get(0).setFireStatus(false);
    			
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Axis.POV)) { // FEUERN
    			double val = joystick.getComponent(Identifier.Axis.POV).getPollData();
    			if(val==0.25) {				// oben
    				
    			} else if(val==0.50) {		// rechts
    				Player.playerList.get(0).chooseNextWeapon();		// Nächste Waffe
    			} else if(val==0.75) {		// unten
    				
    			} else if(val==1.00) {		// links
    				Player.playerList.get(0).choosePrevWeapon();		// Vorherige Waffe
    			}
    			
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Button._2)) { // USE (Für NPC, YouDiedScreen, ...)
    			exeUse();
    		} else if(event.getComponent().getIdentifier().equals(Identifier.Button._7)) { // MANA
    			ManaManager.useMana(0, 0);
    		}
    		
    		//System.out.println(joystick.getComponent(Identifier.Button._5).getPollData());
    		if(mx>-0.01 && mx<0.01)
    			mx = 0.0;
    		if(my>-0.01 && my<0.01)
    			my = 0.0;
    		if(px>-0.01 && px<0.01)
    			px = 0.0;
    		if(py>-0.01 && py<0.01)
    			py = 0.0;
    		
    		//System.out.println(mx + "  und  "+ my);
    		Player.setPlayerMovementX(0, px);
    		Player.setPlayerMovementY(0, py);	
    	}
		
    	captureDirection();
    	setAngle();
    	Player.playerList.get(0).rotatePlayerImg();
    	//System.out.println(angle);    	
    }

	
	private static double getDistance(double posx, double posy) {
		return Math.sqrt(Math.pow(posx, 2) + Math.pow(posy, 2));
    }
	
	private void captureDirection() {
		int speed = 4;
		mousex += mx*speed;
		mousey += my*speed;
		
		// Zeigerkontrolle: Wenn Zeiger außerhalb des erlaubten Kreises ODER innerhalb des nicht erlaubten Mittelkreises, dann: (Kontrolle durch Radius)
		if(getDistance(mousex, mousey)>35 || getDistance(mousex, mousey)<4) {					
			double dis = getDistance(mousex, mousey);
					//Math.sqrt(Math.pow(mousex, 2) + Math.pow(mousey, 2));	// Aktuellen Radius bestimmen
			mousex = mousex / dis * 35;											// Solldistanz anpassen	(Achse / Radius * Sollradius)
			mousey = mousey / dis * 35;											// Solldistanz anpassen	// TODO nachrechnen, ob Quadrat weggelassen werden darf / ob sich Quadrat lohnt
		}
		
		// Startdelay
		if(startCounter<1) {
			angle = 0;
			mousex = 10;
			mousey = 0;
			startCounter++;
		}
		
	}
	
	private void setAngle() {
		if(mousey==0 && mousex==0)
			return;
		angle = Math.toDegrees(Math.atan((double)(mousey*(-1)) / mousex));
		
		// Richtung ermitteln
		if(mousex<0)
			isRight = false;
		else
			isRight = true;
		if(mousey<0)
			isUp = true;
		else
			isUp = false;
		
		// Grad errechnen
		if(isRight==false && isUp) {
			angle += 180;
		} else if(isRight==false && isUp==false) {
			angle += 180;
		} else if(isRight && isUp==false) {
			angle += 360;
		}
		
		if(Double.isNaN(angle)) {
			angle = 0;
		}
	}
	
	/**     KEYS		*/
	public void keyPressed(KeyEvent event) {	// Wenn Tastatur benutzt wird folgende Tasten überprüfen:
        int key = event.getKeyCode();
        
        
        if(Player.playerList.get(0).getLifeStatus()) {
        	        	
        	/**		BEWEGUNG	*/
        	// x-Achse
        	if(key==p1_Le)
	        	Player.setPlayerMovementLe(0);
        	else if(key==p1_Ri)
	        	Player.setPlayerMovementRi(0);
        	// y-Achse
        	if(key==p1_Up)
	        	Player.setPlayerMovementUp(0);
        	else if(key==p1_Do)
	        	Player.setPlayerMovementDo(0);
        	
        	/**		WAFFEN		*/
        	if(key==p1_Fire)
	        	Player.playerList.get(0).setFireStatus(true);
	        if(key==p1_NextW)
	        	Player.playerList.get(0).chooseNextWeapon();
	        else if(key==p1_PrevW)
	        	Player.playerList.get(0).choosePrevWeapon();       	
        	
        	/**		WAFFEN BEKOMMEN (NUR ZUM TESTEN !!!)		*/
        	switch(key) {
	        	case KeyEvent.VK_NUMPAD1:
	        		Player.playerList.get(0).chooseWeapon(2);
	        		break;
	        	case KeyEvent.VK_NUMPAD2:
	            	Player.playerList.get(0).chooseWeapon(3);
	            	break;
	            case KeyEvent.VK_NUMPAD3:
	            	Player.playerList.get(0).chooseWeapon(4);
	            	break;
	            case KeyEvent.VK_NUMPAD4:
	            	Player.playerList.get(0).chooseWeapon(5);
	            	break;
            	case KeyEvent.VK_NUMPAD5:
            		Player.playerList.get(0).chooseWeapon(6);
            		break;
            	case KeyEvent.VK_NUMPAD6:
                	Player.playerList.get(0).chooseWeapon(7);
                	break;
                case KeyEvent.VK_NUMPAD7:
                	Player.playerList.get(0).chooseWeapon(8);
                	break;
        	}
        }      
    }
	
    public void keyReleased(KeyEvent event) {	// Wenn Taste nach Drücken wieder losgelassen wird, sonst wie oben
        int key = event.getKeyCode();

        /**		BEWEGUNG	*/
    	// x-Achse
    	if(key==p1_Le)
        	Player.playerList.get(0).setMoveStatusX(0);
    	else if(key==p1_Ri)
        	Player.playerList.get(0).setMoveStatusX(0);
    	// y-Achse
    	if(key==p1_Up)
        	Player.playerList.get(0).setMoveStatusY(0);
    	else if(key==p1_Do)
        	Player.playerList.get(0).setMoveStatusY(0);
    	
    	/**		WAFFEN		*/
    	if(key==p1_Fire) {
        	Player.playerList.get(0).setFireStatus(false);
        	Player.setYouDiedScreenStatus(false);
    	}
    	
    	/**		FENSTER		*/
    	if(key==KeyEvent.VK_ESCAPE) {
    		if(Shop.show) {				// Shop schließen
    			Shop.closeShop();
    			setMouseIsController(true);	// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
    		} else
    			setMouseIsController(!isMouseControlled);	// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
    	} else if(key==KeyEvent.VK_ENTER) {
    		//System.out.println(NPC.npcPermission);
    		exeUse();
    	} else if(key==KeyEvent.VK_M) {
    		setMouseIsController(false);		// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
    		new Shop();
    	} else if(key==KeyEvent.VK_B) {
    		Savegame.savegame();
    	} else if(key==KeyEvent.VK_G) {
    		ManaManager.useMana(0, 0);
    	}
    }
    
    
    /**     AKTIVITÄTEN     */
    private void exeUse() {
    	if(NPC.shopPermission) {
			setMouseIsController(false);		// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
			new Shop();
		} else if(Player.showYouDiedImage) {
			Player.setYouDiedScreenStatus(false);
		} else if(NPC.npcPermission) {
			NPC.setCollidedPicture();
		}
    }
	
    
    /**     MOUSE     */
    private void setMouseIsController(boolean arg0) {
    	if(arg0) {
    		isMouseControlled = true;
    		Runner.setMouseVisibility(false);
    	} else {
    		isMouseControlled = false;
    		Runner.setMouseVisibility(true);
    	}
    }
   
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(Shop.show)
			Shop.receiveMouseKlick(e.getX(), e.getY());
	}
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		// TODO Auto-generated method stub
		if(button == MouseEvent.BUTTON1) {
			if(isMouseControlled)
				Player.playerList.get(0).setFireStatus(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Player.playerList.get(0).setFireStatus(false);
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//if(Settings.isControledByMouse())
			updatePlayerControlsData(e);
	}

	public void mouseMoved(MouseEvent e) {
		if(Shop.show)
			Shop.receiveMouseMovement(e.getX(), e.getY());
		updatePlayerControlsData(e);
	}
	
	private void updatePlayerControlsData(MouseEvent e) {
		if(Settings.isControledByMouse() && isMouseControlled) {
			setMoveMYFromMouse(e);
			captureDirection();
			setAngle();
			mousePositionReset();
			Player.playerList.get(0).rotatePlayerImg();
		}
	}
	
	private void setMoveMYFromMouse(MouseEvent e) {
		int stax = Runner.getOnScreenFrameCenterX();
		int stay = Runner.getOnScreenFrameCenterY();
		int tarx = e.getXOnScreen();
		int tary = e.getYOnScreen();
		
		mx = (tarx - stax) * 0.25;
		my = (tary - stay) * 0.25;
	}
	
	private void mousePositionReset() {
		robot.mouseMove(Runner.getOnScreenFrameCenterX(), Runner.getOnScreenFrameCenterY());
	}
	public static int getDirectionMarkerX() {
		return (int)controls.mousex;
	}
	public static int getDirectionMarkerY() {
		return (int)controls.mousey;
	}
	public static boolean getIsUp() {
		return controls.isUp;
	}
	public static boolean getIsRight() {
		return controls.isRight;
	}
	public static double getAngle() {
        return controls.angle;
	}
}
