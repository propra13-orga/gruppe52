package the.game.java;

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
	private static int height = 564;
	private static int width = 960;
	public static int borderLe = 0;
	public static int borderRi = width;
	public static int borderUp = 20;
	public static int borderDo = height;

	public Runner(int level) {	// Konstruktor #1 mit Levelwahl
        add(new Setter(level));							// startet setter, Parameter 'level' gibt die Nummer des zu startenden Levels weiter

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Schlie�en, wenn auf X geklick wird
        setSize(width, height);							// setzt die gr��e des Fensters fest
        setLocationRelativeTo(null);					// Fenster startet in der Mitte
        setTitle("THE GAME");							// Titel des Fensters
        setResizable(false);							// Fenstergr��e manuell nicht ver�nderbar
        //setUndecorated(true);
        setVisible(true);
    }
	
	public Runner() {			// Konstruktor #2 ohne Levelwahl
        add(new Setter(1));								// startet setter, Parameter '1' l�sst das erste Level starten

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Schlie�en, wenn auf X geklick wird
        setSize(width, height);							// setzt die gr��e des Fensters fest
        setLocationRelativeTo(null);					// Fenster startet in der Mitte
        setTitle("THE GAME");							// Titel des Fensters
        setResizable(false);							// Fenstergr��e manuell nicht ver�nderbar
        //setUndecorated(true);
        setVisible(true);
    }
	
	public static int getHeightF() {	// gibt H�he des Fenster aus
		return height;
	}
	
	public static int getWidthF() {		// gibt Breite des Fensters aus
		return width;
	}
	
	public static void shutRunnerDown() {	// schlie�t das Fenster
		StartWindow.getRunner().dispose();
	}
}







