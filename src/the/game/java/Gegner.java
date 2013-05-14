package the.game.java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
//import java.awt.Image;
//import java.awt.Graphics;

public class Gegner {

    private static final Thread MeinThread = null;

	Gegner() {
    	
		
    	//Bild anzeigen
        JFrame win = new JFrame("Gegner");
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        win.setSize(800, 600);
        win.setLocationRelativeTo(null);
        
        JLabel label = new JLabel();
        try {
            BufferedImage bi = ImageIO.read(new File("Totenkopf.gif")); 
            label = new JLabel(new ImageIcon(bi));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(label);
        
        win.add(scrollPane);
        win.setVisible(true);
	}
        
        //Bildanimation
       
  
    
    public static void main(String[] args) {
        Gegner gegner = new Gegner();
    }
}