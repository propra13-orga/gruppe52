package the.game.java;

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
    		if(Shop.show)				// Shop schließen
    			Shop.closeShop();
    	} else if(key==KeyEvent.VK_M) {
    		new Shop();
    	} else if(key==KeyEvent.VK_N) {
    		Tracker.trackerList.get(0).pathFinding();
    	}
    }
	
    
    /**     MOUSE     */
   
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
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(Shop.isInItemSquare(e.getX(), e.getY()));
		if(Shop.show)
			Shop.receiveMouseMovement(e.getX(), e.getY());
	}
}
