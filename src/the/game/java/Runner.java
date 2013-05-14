package the.game.java;

import javax.swing.JFrame;


public class Runner extends JFrame {
    /**
	 * Notiz zur Klasse:
	 * Diese Klasse startet das Spielfenster. Wird ein Objekt dieser Klasse mit dem
	 * 1. Parameter Runner() erstellt, so wird das erste Level gestartet.
	 * 2. Parameter Runner(n) erstellt, so wird das n-te Level gestartet.
	 * Gestartet wird diese Klasse (erstellt wird ein Objekt dieser Klasse) in 'StartWindow.java', Methode: 'fenster()'
	 */
	private static final long serialVersionUID = 1L;
	private static int height = 540;
	private static int width = 960;

	public Runner(int level) {	// Konstruktor #1 mit Levelwahl
        add(new Setter(level));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setTitle("THE GAME");
        setResizable(false);
        setVisible(true);
    }
	
	public Runner() {			// Konstruktor #2 ohne Levelwahl
        add(new Setter(1));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setTitle("THE GAME");
        setResizable(false);
        setVisible(true);
    }
	
	public static int getHeightF() {
		return height;
	}
	
	public static int getWidthF() {
		return width;
	}
	
	public static void shutRunnerDown() {
		StartWindow.getRunner().dispose();
	}

	// Gestartet wird nun über das menu
    //public static void main(String[] args) {
    //    new Runner();
    //}
}







