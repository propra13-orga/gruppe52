package the.game.java;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

/**
 * Diese Klasse startet das Spielfenster. 
 * Wird ein Objekt dieser Klasse mit dem
 * 	- Konstruktor Runner() erstellt, so wird das erste Level gestartet.
 * 	- Konstruktor Runner(n) erstellt, so wird das n-te Level gestartet.
 * Gestartet wird diese Klasse (erstellt wird ein Objekt dieser Klasse) in 'StartWindow.java', Methode: 'fenster()'
 */
public class Runner extends JFrame {
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
	
	public static Random random = new Random();
	//private static Cursor defaultCursor = StartWindow.getRunner().getCursor();

	/**
	 * Konstruktor(1) der Klasse Runner. Ruft einen Raum mithilfe der Levelnr auf
	 * @param level Levelnr im Spielverlauf
	 */
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
	/**
	 * Konstruktor(2) der Klasse Runner. Ruft einen Raum mithilfe des Pfades auf
	 * @param path Pfad zur LevelDatei
	 */
	public Runner(String path) {			// Konstruktor #2 ohne Levelwahl
        add(new Setter(path));								// startet setter, Parameter '1' lässt das erste Level starten

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Schließen, wenn auf X geklick wird
        setSize(width, height);							// setzt die größe des Fensters fest
        setLocationRelativeTo(null);					// Fenster startet in der Mitte
        setTitle("THE GAME");							// Titel des Fensters
        setResizable(false);							// Fenstergröße manuell nicht veränderbar
        //setUndecorated(true);
        setVisible(true);
        
    }
	/**
	 * Konstruktor(3) der Klasse Runner. Ruft den ersten Raum auf
	 */
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
	/**
	 * Gibt Höhe des Fensters aus
	 * @return height
	 */
	public static int getHeightF() {	// gibt Höhe des Fenster aus
		return height;
	}
	/** 
	 * Gibt Breite des Fenster aus
	 * @return width
	 */
	public static int getWidthF() {		// gibt Breite des Fensters aus
		return width;
	}
	/**
	 * Gibt die x-Koordinate des Mittelpunktes des Fensters aus
	 * @return x
	 */
	public static int getCenterX() {
		return centerX;
	}
	/**
	 * Gibt die y-Koordinate des Mittelpunktes des Fensters aus
	 * @return y
	 */
	public static int getCenterY() {
		return centerY;
	}
	/**
	 * Gibt x-Koordinate derMitte des Spielfeldes auf Bildschirm an
	 * @return x
	 */
	public static int getOnScreenFrameCenterX() {
		return centerX + getFrameLocationOnScreen().x;
	}
	/**
	 * Gibt y-Koordinate derMitte des Spielfeldes auf Bildschirm an
	 * @return y
	 */
	public static int getOnScreenFrameCenterY() {
		return centerY + getFrameLocationOnScreen().y;
	}
	/**
	 * Gibt Punkt der oberen, rechten Ecke des Fensters auf Bildschirm an
	 * @return Punkt(x,y)
	 */
	public static Point getFrameLocationOnScreen() {
		return StartWindow.getRunner().getLocationOnScreen();
	}
	/**
	 * Schließt das Fenster
	 */
	public static void shutRunnerDown() {	// schließt das Fenster
		StartWindow.getRunner().dispose();
	}
	/**
	 * Ändert die Sichtbarkeit der Maus
	 * @param arg0 true für Sichtbar, false für Unsichtbar
	 */
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