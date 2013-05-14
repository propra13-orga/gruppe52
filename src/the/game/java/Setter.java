package the.game.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Setter extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Timer timer;
    private Player player;
    private ImageIcon ii;

    public Setter(int levelNumber) {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.GRAY);
        setDoubleBuffered(true);	// verhindert Flackern

        player = new Player();

        timer = new Timer(5, this);
        timer.start();
        
        new LevelCaller();
        LevelCaller.setLevel(levelNumber);
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(player.getPlayerIcon(), player.getX(), player.getY(), this);

        Image wall = setImagePath("wand.jpg");
        Image trap = setImagePath("trap.png");
        Image monster = setImagePath("Totenkopf.gif");
        // Walls     

        for(int a=0; a < LevelCreator.getItemMapDimensions(0); a++) {	// Spaltenweise
			for(int b=0; b<LevelCreator.getItemMapDimensions(1); b++) {
				if(LevelCreator.getItemMapData(a, b)==1) {
					g2d.drawImage(wall, a*20, b*20, this);
				}
				if(LevelCreator.getItemMapData(a, b)==2) {
					g2d.drawImage(trap, a*20, b*20, this);
				}
				if(LevelCreator.getItemMapData(a, b)==3) {
					g2d.drawImage(monster, a*20, b*20, this);
				}
			}
			//System.out.println("");
		}

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    public Image setImagePath(String path) {
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }

    public void actionPerformed(ActionEvent event) {
    	Enemy.move();
    	if(player.getLifeStatus()) {
	    	player.move();
    	}
    	repaint();
    }


    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent event) {
            player.keyReleased(event);
        }

        public void keyPressed(KeyEvent event) {
            player.keyPressed(event);
        }
    }
}