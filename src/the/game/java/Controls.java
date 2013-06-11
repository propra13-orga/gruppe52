package the.game.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Controls {
	
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
	private int mx;
	private int my;
	private int mousex = 0;
	private int mousey = 0;
	private int startCounter = 0;
	private boolean isUp = false;
	private boolean isRight = false;
	
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
	}
	
	public static void createControls() {
		controls = new Controls();
	}
	
	/**     KEYS		*/
	public void keyPressed(KeyEvent event) {	// Wenn Tastatur benutzt wird folgende Tasten überprüfen:
        int key = event.getKeyCode();
        
        
        if(Player.playerList.get(0).getLifeStatus()) {
        	        	
        	/**		BEWEGUNG	*/
        	// x-Achse
        	if(key==p1_Le)
	        	Player.playerList.get(0).setMoveStatusX(-1, 1);
        	else if(key==p1_Ri)
	        	Player.playerList.get(0).setMoveStatusX(1, 2);
        	// y-Achse
        	if(key==p1_Up)
	        	Player.playerList.get(0).setMoveStatusY(-1, 3);
        	else if(key==p1_Do)
	        	Player.playerList.get(0).setMoveStatusY(1, 4);
        	
        	/**		WAFFEN		*/
        	if(key==p1_Fire)
	        	Player.playerList.get(0).setFireStatus(true);
	        if(key==p1_NextW)
	        	WeaponManager.weaponManagerList.get(0).chooseNextWeapon();
	        else if(key==p1_PrevW)
	        	WeaponManager.weaponManagerList.get(0).choosePrevWeapon();       	
        	
        	/**		WAFFEN BEKOMMEN (NUR ZUM TESTEN !!!)		*/
        	switch(key) {
	        	case KeyEvent.VK_NUMPAD1:
	        		WeaponManager.weaponManagerList.get(0).chooseWeapon(2);
	        		break;
	        	case KeyEvent.VK_NUMPAD2:
	            	WeaponManager.weaponManagerList.get(0).chooseWeapon(3);
	            	break;
	            case KeyEvent.VK_NUMPAD3:
	            	WeaponManager.weaponManagerList.get(0).chooseWeapon(4);
	            	break;
	            case KeyEvent.VK_NUMPAD4:
	            	WeaponManager.weaponManagerList.get(0).chooseWeapon(5);
	            	break;
            	case KeyEvent.VK_NUMPAD5:
            		WeaponManager.weaponManagerList.get(0).chooseWeapon(6);
            		break;
            	case KeyEvent.VK_NUMPAD6:
                	WeaponManager.weaponManagerList.get(0).chooseWeapon(7);
                	break;
                case KeyEvent.VK_NUMPAD7:
                	WeaponManager.weaponManagerList.get(0).chooseWeapon(8);
                	break;
        	}
        }      
    }
	
    public void keyReleased(KeyEvent event) {	// Wenn Taste nach Drücken wieder losgelassen wird, sonst wie oben
        int key = event.getKeyCode();

        /**		BEWEGUNG	*/
    	// x-Achse
    	if(key==p1_Le)
        	Player.playerList.get(0).setMoveStatusX(0, -1);
    	else if(key==p1_Ri)
        	Player.playerList.get(0).setMoveStatusX(0, -2);
    	// y-Achse
    	if(key==p1_Up)
        	Player.playerList.get(0).setMoveStatusY(0, -3);
    	else if(key==p1_Do)
        	Player.playerList.get(0).setMoveStatusY(0, -4);
    	
    	/**		WAFFEN		*/
    	if(key==p1_Fire) {
        	Player.playerList.get(0).setFireStatus(false);
        	Player.playerList.get(0).setYouDiedScreenStatus(false);
    	}
    	
    	/**		FENSTER		*/
    	if(key==KeyEvent.VK_ESCAPE) {
    		if(Shop.show) {				// Shop schließen
    			Shop.closeShop();
    			setMouseIsController(true);	// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
    		} else
    			setMouseIsController(!isMouseControlled);	// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
    	} else if(key==KeyEvent.VK_M) {
    		setMouseIsController(false);		// Maus zentrieren und zur Spielersteuerung benutzen (an/aus)
    		new Shop();
    	} else if(key==KeyEvent.VK_N) {
    		Tracker.trackerList.get(0).pathFinding();
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
		updatePlayerControlsData(e);
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(Shop.isInItemSquare(e.getX(), e.getY()));
		if(Shop.show)
			Shop.receiveMouseMovement(e.getX(), e.getY());
		updatePlayerControlsData(e);
	}
	
	private void updatePlayerControlsData(MouseEvent e) {
		if(isMouseControlled) {
				captureMouseDirection(e);
				mousePositionReset();
		}
	}
	
	private void captureMouseDirection(MouseEvent e) {
		int stax = Runner.getOnScreenFrameCenterX();
		int stay = Runner.getOnScreenFrameCenterY();
		int tarx = e.getXOnScreen();
		int tary = e.getYOnScreen();
		
		//double gradient = getGradient(stax, stay, tarx, tary);
		mx = tarx - stax;
		my = tary - stay;
		
		
		if((mousex+mx)<100 && (mousex+mx)>(-100))
			mousex += mx;
		if((mousey+my)<100 && (mousey+my)>(-100))
			mousey += my;
		
		if(startCounter<1) {
			mousex = 0;
			mousey = 0;
			startCounter++;
		}
		
		//System.out.println(Math.at);
		//System.out.println(mousex +" | "+ mousey);
		
	}
	
	public static double getangle() {
		double getangle;
		getangle = Math.toDegrees(Math.atan((double)controls.mousey / controls.mousex));
		if(controls.mousex<0)
			getangle += 180;
		if(Double.isNaN(getangle))
			getangle = 0;
        return getangle;
	}
/*
	private void setCurrentDirection() {
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getRotateInstance(
				Math.toRadians(degrees),
				(double)originalImage.getWidth()/2.0, 
				(double)originalImage.getHeight()/2.0), 
				AffineTransformOp.TYPE_BILINEAR);
		BufferedImage rotatedImage = op.filter(originalImage, null);  
	}
	*/
	private void mousePositionReset() {
			robot.mouseMove(Runner.getOnScreenFrameCenterX(), Runner.getOnScreenFrameCenterY());
	}
	public static int getDirectionMarkerX() {
		return controls.mousex;
	}
	public static int getDirectionMarkerY() {
		return controls.mousey;
	}
	public static boolean getIsUp() {
		return controls.isUp;
	}
	public static boolean getIsRight() {
		return controls.isRight;
	}
}
