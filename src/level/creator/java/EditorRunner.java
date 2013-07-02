package level.creator.java;

import javax.swing.JFrame;



public class EditorRunner extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditorRunner() {	// Konstruktor #1 mit Levelwahl
        add(new EditorSetter());							// startet setter, Parameter 'level' gibt die Nummer des zu startenden Levels weiter

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Schlie�en, wenn auf X geklick wird
        setSize(971, 900);							// setzt die gr��e des Fensters fest
        setLocationRelativeTo(null);					// Fenster startet in der Mitte
        setTitle("Level Creator");							// Titel des Fensters
        setResizable(false);							// Fenstergr��e manuell nicht ver�nderbar
        //setUndecorated(true);
        setVisible(true);
		setLayout(null);
		//setFocusable(true);		
    }
	
	public static void main(String[] args){
		new EditorRunner();
	}
}
