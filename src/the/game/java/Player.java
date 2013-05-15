package the.game.java;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {
	
    private String playerIconPath = "player.png";	// Startbild

    private int mx;		// move x
    private int my;		// move y
    private static int x;		// x position
    private static int y;		// y position
    private int imageSizeX=15;
    private int imageSizeY=15;
    private Image playerIcon;	// playericon
    private ImageIcon ii;
    private static boolean alive;

    public Player() {	// Ini: playericon, default position
        ii = new ImageIcon(this.getClass().getResource(playerIconPath));
        playerIcon = ii.getImage();
        x = LevelCaller.getPlayerDefaultPosX();		// default x position
        y = LevelCaller.getPlayerDefaultPosY();		// default y position
        alive = true;
    }
    
    public static void resetPlayerPosition() {
        x = LevelCaller.getPlayerDefaultPosX();		// default x position
        y = LevelCaller.getPlayerDefaultPosY();		// default y position
    }
    
    // BILD ANIMATION
    private void setPlayerIconPath(String path) {
    	ii = new ImageIcon(this.getClass().getResource(path));
    	playerIcon = ii.getImage();
    }
    
    private void setPlayerIcon(int flag) {	// L, R, U, D
    		switch(flag) {
    		// gerade  MOVING
	    	case 7:	// Moving - left
	    		setPlayerIconPath("left.png");
		    	break;
	    	case 3:	// Moving - right
	    		setPlayerIconPath("right.png");
		    	break;
	    	case 1:	// Moving - up
		    	playerIconPath = "up.png";
		    	ii = new ImageIcon(this.getClass().getResource(playerIconPath));
		    	playerIcon = ii.getImage();
		    	break;
	    	case 5:	// Moving - down
		    	playerIconPath = "down.png";
		    	ii = new ImageIcon(this.getClass().getResource(playerIconPath));
		    	playerIcon = ii.getImage();
		    	break;  
		    // schraeg MOVING
	    	case 2:	// Moving - up-right
		    	playerIconPath = "upright.png";
		    	ii = new ImageIcon(this.getClass().getResource(playerIconPath));
		    	playerIcon = ii.getImage();
		    	break;
	    	case 4:	// Moving - down-right
		    	playerIconPath = "downright.png";
		    	ii = new ImageIcon(this.getClass().getResource(playerIconPath));
		    	playerIcon = ii.getImage();
		    	break;
	    	case 6:	// Moving - down-left
		    	playerIconPath = "downleft.png";
		    	ii = new ImageIcon(this.getClass().getResource(playerIconPath));
		    	playerIcon = ii.getImage();
		    	break;
	    	case 8:	// Moving - up-left
		    	playerIconPath = "upleft.png";
		    	ii = new ImageIcon(this.getClass().getResource(playerIconPath));
		    	playerIcon = ii.getImage();
		    	break;
	    	// gerade	   STANDING STILL
		    case 17:	// Standing still - left
			    playerIconPath = "standing_left.png";
			    ii = new ImageIcon(this.getClass().getResource(playerIconPath));
			    playerIcon = ii.getImage();
			    break;
		    case 13:	// Standing still - right
			    playerIconPath = "standing_right.png";
			    ii = new ImageIcon(this.getClass().getResource(playerIconPath));
			    playerIcon = ii.getImage();
			    break;
		    case 11:	// Standing still - up
			    playerIconPath = "standing_up.png";
			    ii = new ImageIcon(this.getClass().getResource(playerIconPath));
			    playerIcon = ii.getImage();
			    break;
		    case 15:	// Standing still - down
			    playerIconPath = "standing_down.png";
			    ii = new ImageIcon(this.getClass().getResource(playerIconPath));
			    playerIcon = ii.getImage();
			    break;
    		}
    }
    // BILD ANIMATION END

    // REQUESTS
    public int getX() {		// returns actual x-position
        return x;
    }
    public int getY() {		// returns actual y-position
        return y;
    }
    public Image getPlayerIcon() {		// returns playericon
        return playerIcon;
    }
    // REQUESTS END
    
    // MOVEMENT RELEVANT REQUEST
    public void move() {	// sets the new position
    	int permission = checkMovementPermission();
    	if(permission<2){	// >=2 = Bewegen nicht erlaubt
	    	if(permission<=0) // seperated axes: if only one direction is affected
	    		x += mx;
	    	if(permission>=0)
	    		y += my;
    	}
    }    
    
    public int checkMovementPermission() {	//int axis) {
    	int permission;
    	//permission=checkBorders(axis);
		//if(permission)
			//if(axis==1)	// damit nur einmal überprüft wird
				permission = checkEnvironment(x, mx, y, my);
    	return permission;
    }
    
    private int checkEnvironment(int posx, int movx, int posy, int movy) {
    	boolean permissionX = true;
    	boolean permissionY = true;
    	boolean permission = true;
    	int perm = 0;
    	int nextx = x + mx;
    	int nexty = y + my;
    	int borderXL = 0;			// Border left
    	int borderXR = Runner.getWidthF()-21;			// Border right
    	int borderYU = 0;			// Border up
    	int borderYD = Runner.getHeightF()-43;			// Border down
		
    	// Check Borders if player moves in that particular direction
    	// ALSO: Check any Item that sets permission = false here!

		posx=nextx;
		posy=nexty;
		// Border und Kontrollpixel set
    	if(mx==-1) {					// Check Border: left
    		//if(posx<borderXL)
    			//	posx = 0;
    		if(x<=borderXL)
    			permissionX=false;
    	} else if(mx==1) {				// Check Border: right
    		//posx += imageSizeX;	// damit Kontrolle am richtigen Punkt ist
    		//if(posx>borderXR)
    			//	posx--;
    		if(x>=borderXR)
	    		permissionX=false;
    	}
    	if(my==-1) {				// Check Border: up
    		//if(posy<borderYU)
    			//	posy = 0;
    		if(y<=borderYU)
	    		permissionY=false;
    	} else if(my==1) {				// Check Border: down
    		//posy += imageSizeY;
    		//if(posy>borderYD)
    		//	posy--;
    		if(y>=borderYD)
	    		permissionY=false;
    	}
    	
    	// IF PERMISSIONX/Y == TRUE ... NICHT VERGESSEN!
    	// AUSGANG: PERM = TRUE
    	if(mx==-1) {
    		if(my==-1){				// left, up	(Hiermit wurden alle anderen erstellt)
    			// 1. Fixpunkt
    			if(checkItemMap(posx, posy)==false) {					// 1. Beide blockiert
    				if(checkItemMap(x, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten links
    			if(checkItemMap(posx, posy + imageSizeY)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x, posy + imageSizeY)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx, y + imageSizeY)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Oben rechts
    			if(checkItemMap(posx + imageSizeX, posy)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx + imageSizeX, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else if(my==1) {		// left, down
    			// 1. Fixpunkt
    			if(checkItemMap(posx, posy)==false) {					// 1. Beide blockiert
    				if(checkItemMap(x, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten links
    			if(checkItemMap(posx, posy + imageSizeY)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x, posy + imageSizeY)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx, y + imageSizeY)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten rechts
    			if(checkItemMap(posx + imageSizeX, posy + imageSizeY)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, posy + imageSizeY)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx + imageSizeX, y + imageSizeY)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else {				// left
    			// 1. Fixpunkt
    			if(checkItemMap(posx, posy)==false) {					// 1. Links blockiert
    				permissionX = false;
    			}
    			// 2. Unten links
    			if(checkItemMap(posx, posy + imageSizeY)==false) {		// 1. Links blockiert
    				permissionX = false;
    			}
    		}	
    	} else if(mx==1) {
    		if(my==-1){				// right, up
    			// 1. Fixpunkt
    			if(checkItemMap(posx, posy)==false) {					// 1. Beide blockiert
    				if(checkItemMap(x, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionY = false;	// Y wegen fehler beim Annähern an ecken von unten nach oben
    				}
    			}
    			// 2. Oben rechts
    			if(checkItemMap(posx + imageSizeX, posy)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx + imageSizeX, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten rechts
    			if(checkItemMap(posx + imageSizeX, posy + imageSizeY)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx + imageSizeX, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else if(my==1) {		// right, down
    			// 1. Oben rechts
    			if(checkItemMap(posx + imageSizeX, posy)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, posy)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx + imageSizeX, y)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 2. Unten rechts
    			if(checkItemMap(posx + imageSizeX, posy + imageSizeY)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x + imageSizeX, posy + imageSizeY)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx + imageSizeX, y + imageSizeY)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    			// 3. Unten links
    			if(checkItemMap(posx, posy + imageSizeY)==false) {		// 1. Beide blockiert
    				if(checkItemMap(x, posy + imageSizeY)==false) {						// 1.2 Y bei gleichem X blockiert?
    					permissionY = false;
    				}
    				if(checkItemMap(posx, y + imageSizeY)==false) {						// 1.1 X bei gleichem Y blockiert?
    	    			permissionX = false;
    	    		}
    				if(permissionX && permissionY) {
    					permissionX = false;
    				}
    			}
    		} else {				// right
    			// 1. Oben rechts (nach rechts)
    			if(checkItemMap(posx + imageSizeX, posy)==false) {					// 1. Rechts blockiert
    				permissionX = false;
    			}
    			// 2. Unten rechts
    			if(checkItemMap(posx + imageSizeX, posy + imageSizeY)==false) {		// 1. Rechts blockiert
    				permissionX = false;
    			}
    		}	
    	} else if(my==-1) {   		// up
    		// 1. Fixpunkt (nach oben)
			if(checkItemMap(posx, posy)==false) {									// 1. Oben blockiert
				permissionY = false;
			}
			// 2. Oben rechts
			if(checkItemMap(posx + imageSizeX, posy)==false) {						// 1. Rechts blockiert
				permissionY = false;
			}
    	} else if(my==1) {			// down
    		// 1. Unten links (nach unten)
			if(checkItemMap(posx, posy + imageSizeY)==false) {						// 1. Unten blockiert
				permissionY = false;
			}
			// 2. Unten rechts
			if(checkItemMap(posx + imageSizeX, posy + imageSizeY)==false) {			// 1. Unten blockiert
				permissionY = false;
			}
    	}

    	if(permission==false)
    		perm = 2;
    	else if(permissionX==false && permissionY==false)
    		perm = 3;
    	else if(permissionX==false)
    		perm = 1;
    	else if(permissionY==false)
    		perm = -1;
    	else
    		perm = 0;
    	return perm;
	}
    
    
    private boolean checkItemMap(int posx, int posy) {
		posx=(posx - (posx % 20 )) / 20;
		posy=(posy - (posy % 20 )) / 20;
		//System.out.println(posx + "   " + posy);
    	boolean permission=true;
    	switch(LevelCreator.itemMap[posx][posy]) {
    	case 1:					// wall
    		permission=false;
    		break;
    	case 2:					// trap
    		permission=false;
    		alive=false;
    		break;
    	case 3:					// enemy
    		permission=true;
    		alive=false;
    		break;
    	case 4:
    		LevelCaller.setNextLevel();
    		break;
    	}
    	checkLifeStatus();
    	return permission;
    }
    

    
    //private boolean checkBorders(int axis) {	// evtl größe des Bildes beachten!!
    //	boolean permission=true;
    //	int borderXL = 0;			// Border left
    //	int borderXR = Runner.getWidthF()-21;			// Border right
    //	int borderYU = 0;			// Border up
    //	int borderYD = Runner.getHeightF()-43;			// Border down

	//	return permission;			// return: false/true for movement permission
    //}
    // MOVEMENT RELEVANT REQUEST END
    
    // LIFE STATUS
    private void checkLifeStatus() {
    	if(alive==false) {
    		setPlayerIconPath("dead.png");
    	}
    }
    public boolean getLifeStatus() {
    	return alive;
    }
    // LIFE STATUS END

    // KEY BINDINGS
    public void keyPressed(KeyEvent event) {	// sets movement until key is released
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {			// <
        	mx = -1;
        	checkDirection(1);
        }

        if (key == KeyEvent.VK_RIGHT) {			// >
        	mx = 1;
        	checkDirection(2);
        }

        if (key == KeyEvent.VK_UP) {			// ^
        	my = -1;
        	checkDirection(3);
        }

        if (key == KeyEvent.VK_DOWN) {			// v
        	my = 1;
        	checkDirection(4);
        }
    }
    public void keyReleased(KeyEvent event) {	// sets former movement back to 0
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            mx = 0;
            checkDirection(-1);
        }

        if (key == KeyEvent.VK_RIGHT) {
            mx = 0;
            checkDirection(-2);
        }

        if (key == KeyEvent.VK_UP) {
            my = 0;
            checkDirection(-3);
        }

        if (key == KeyEvent.VK_DOWN) {
            my = 0;
            checkDirection(-4);
        }
    }

    private void checkDirection(int flag) {
    	int checkSum;
	    boolean k1=false;
	    boolean k2=false;
	    boolean k3=false;
	    boolean k4=false;
	    boolean ks1=false;
	    boolean ks2=false;
	    boolean ks3=false;
	    boolean ks4=false;
    	
	    if(flag==1)
    		k1=true;
    	else if(flag==-1)
    		ks1=true;
    	if(flag==2)
    		k2=true;
    	else if(flag==-2)
    		ks2=true;
    	if(flag==3)
    		k3=true;
    	else if(flag==-3)
    		ks3=true;
    	if(flag==4)
    		k4=true;
    	else if(flag==-4)
    		ks4=true;

    	checkSum = mx + my;
    	// MOVING
    	if(k1) {
    		if(checkSum>-1) 		// down-left
    			flag=6;
    		else if(checkSum<-1)	// up-left
    			flag=8;
    		else				// left
    			flag=7;
    	} else if(k2) {
    		if(checkSum>1) 		// down-right
    			flag=4;
    		else if(checkSum<1)	// up-right
    			flag=2;
    		else				// right
    			flag=3;
    	} else if(k3) {
    		if(checkSum>-1) 		// up-right
    			flag=2;
    		else if(checkSum<-1)	// up-left
    			flag=8;
    		else				// up
    			flag=1;
    	} else if(k4) {
    		if(checkSum>1) 		// down-right
    			flag=4;
    		else if(checkSum<1)	// down-left
    			flag=6;
    		else				// down
    			flag=5;
    	// STANDING STILL
    	} else if(ks1) {
    		if(checkSum==0)
    			flag=17;	// left
    		else if(checkSum>0)	// down
    			flag=5;			
    		else if(checkSum<0)	// up
    			flag=1;
    	} else if(ks2) {
    		if(checkSum==0)
    			flag=13;			// right
    		else if(checkSum>0)		// down
    			flag=5;
    		else if(checkSum<0)		// up
    			flag=1;
    	} else if(ks3) {
    		if(checkSum==0)
    			flag=11;		// up
    		else if(checkSum>0)		// right
    			flag=3;
    		else if(checkSum<0)		// left
    			flag=7;
    	} else if(ks4) {
    		if(checkSum==0)
    			flag=15;		// down
    		else if(checkSum>0)		// right
    			flag=3;
    		else if(checkSum<0)		// left
    			flag=7;
    	}
    	setPlayerIcon(flag);
    }
    // KEY BINDINGS END	
}
