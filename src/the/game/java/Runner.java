package the.game.java;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Runner extends JFrame {
    /**
	 * Notiz zur Klasse:
	 * Diese Klasse startet das Spielfenster. Wird ein Objekt dieser Klasse mit dem
	 * 	- Konstruktor Runner() erstellt, so wird das erste Level gestartet.
	 * 	- Konstruktor Runner(n) erstellt, so wird das n-te Level gestartet.
	 * Gestartet wird diese Klasse (erstellt wird ein Objekt dieser Klasse) in 'StartWindow.java', Methode: 'fenster()'
	 */
	private static final long serialVersionUID = 1L;
	private static int width = 960;
	private static int height = 564;
	private static int centerX = width/2;
	private static int centerY = height/2;
	public static int borderLe = 0;
	public static int borderRi = width;
	public static int borderUp = 20;
	public static int borderDo = height;
	public static boolean codeRunning = true;
	//private static Cursor defaultCursor = StartWindow.getRunner().getCursor();

	public Runner(int level) {	// Konstruktor #1 mit Levelwahl
        add(new Setter(level));							// startet setter, Parameter 'level' gibt die Nummer des zu startenden Levels weiter

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Schließen, wenn auf X geklick wird
        setSize(width, height);							// setzt die größe des Fensters fest
        setLocationRelativeTo(null);					// Fenster startet in der Mitte
        setTitle("THE GAME");							// Titel des Fensters
        setResizable(false);							// Fenstergröße manuell nicht veränderbar
        //setUndecorated(true);
        setVisible(true);
        
    }
	
	public Runner() {			// Konstruktor #2 ohne Levelwahl
        add(new Setter(1));								// startet setter, Parameter '1' lässt das erste Level starten

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Schließen, wenn auf X geklick wird
        setSize(width, height);							// setzt die größe des Fensters fest
        setLocationRelativeTo(null);					// Fenster startet in der Mitte
        setTitle("THE GAME");							// Titel des Fensters
        setResizable(false);							// Fenstergröße manuell nicht veränderbar
        //setUndecorated(true);
        setVisible(true);
        
    }
	
	public static int getHeightF() {	// gibt Höhe des Fenster aus
		return height;
	}
	
	public static int getWidthF() {		// gibt Breite des Fensters aus
		return width;
	}
	
	public static int getCenterX() {
		return centerX;
	}
	
	public static int getCenterY() {
		return centerY;
	}
	
	public static int getOnScreenFrameCenterX() {
		return centerX + getFrameLocationOnScreen().x;
	}
	
	public static int getOnScreenFrameCenterY() {
		return centerY + getFrameLocationOnScreen().y;
	}
	
	public static Point getFrameLocationOnScreen() {
		return StartWindow.getRunner().getLocationOnScreen();
	}
	
	public static void shutRunnerDown() {	// schließt das Fenster
		StartWindow.getRunner().dispose();
	}
	
	public static void setMouseVisibility(boolean arg0) {
		if(arg0) {
			// Sets the JPanel's cursor to the system default.
			StartWindow.getRunner().getContentPane().setCursor(Cursor.getDefaultCursor());
		} else {
			// Transparent 16 x 16 pixel cursor image.
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

			// Create a new blank cursor.
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			    cursorImg, new Point(0, 0), "blank cursor");

			// Set the blank cursor to the JFrame.
			StartWindow.getRunner().getContentPane().setCursor(blankCursor);
		}
	}
}







