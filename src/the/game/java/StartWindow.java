package the.game.java;

import javax.swing.*;
//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame implements ActionListener {

     /**
	 * Mit dieser Klasse wird das gesamte Spiel gestertet. Sie stellt das Hauptmenü dar.
	 */
	
	private static final long serialVersionUID = 1L;
		// Buttons werden deklariert
		private JButton starten;
		private JButton levwahl;
		private JButton einstellung;
		private JButton info;
		private JButton ende;
		private static StartWindow frame = new StartWindow("Menü");
		private static JFrame vendor;
		
		public StartWindow(String title){ //Konstruktor
			// Titel einstellen
			super(title);
			
			// Buttons werden erzeugt
			starten = new JButton("Neues Spiel");	// neuer Button (starten) mit Aufschrift "Neues Spiel"
			starten.setBounds(120,40,160,40);		// x1=x-Koordinate, x2=y-Koordinate, x3=Breite, x4=Höhe
			starten.addActionListener(this);		// ActionListener wird mit Button verknüpft
			add(starten); 							// Button schliessen hinzufügen
			
			levwahl = new JButton("Level auswählen");
			levwahl.setBounds(120,120,160,40);
			levwahl.addActionListener(this);
			add(levwahl);
			
			einstellung = new JButton("Einstellungen");
			einstellung.setBounds(120,200,160,40);
			einstellung.addActionListener(this);
			add(einstellung);
			
			info = new JButton("Informationen");
			info.setBounds(120,280,160,40);
			info.addActionListener(this);
			add(info);
			
			ende = new JButton("Beenden");
			ende.setBounds(120,360,160,40);
			ende.addActionListener(this);
			add(ende);
			
		}
		
		public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und löst methode aus
			
			if (e.getSource()==starten){			// Wird "Neues Spiel" gedrückt?
				fenster();								// Das Fenster für das Spiel wird geöffnet -> Level 1
			}
			if (e.getSource()==levwahl){			// Wird "Level auswählen" gedrückt?
				LevChooWindow.main(null);				// LevChooWindow wird gestartet
			}
			//if (e.getSource()==info){				// Wird "Informationen" gedrückt?
			//	Object[] options = {"OK"};
			//	//Message Box erstellen
			//	JOptionPane.showOptionDialog(null,  "Sommer ist schöner als Winter", "Information", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			//}
			if (e.getSource()==einstellung){		// Wird "Einstellungen" gedrückt
				auswahl();								// Das Fenster auswahl() wird geöffnet
			}
			if (e.getSource()==ende){				// Wird "Beenden" gedrückt?
				System.exit(0);							// Das Menü/Spiel wird beendet
			}
	    }
		
		public static void fenster(){		// Ohne Parameter - startet level 1
			Runner runner = new Runner();	// Neues Objekt der Klasse Runner wird erstellt
			vendor = runner;				// runner bekommt Namen vendor (Wird später benötigt)
			frame.dispose();				// Menü schließt sich
		}
		
		public static void fenster(int levelnummer){	// Mit Parameter - startet Level mit der nummer "levelnummer"
			Runner runner = new Runner(levelnummer);	// Neues Objekt der Klasse Runner wird erstellt
			vendor = runner;							// runner bekommt Namen vendor (Wird später benötigt)
			frame.dispose();							// Menü schließt sich
		}
		
		public static JFrame getRunner() {				// ???
			return vendor;								// ???
		}
		
		public static void auswahl(){
			JFrame auswahl= new JFrame("Hier sind bald die Einstellungen"); 
			auswahl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		// Fenster wird durch X geschlossen
			auswahl.setSize(400,400);										// Größe des Fensters
			auswahl.setVisible(true);
		}
		
		public static void main(String[] args) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//Spiel beenden mit x
			frame.setSize(400,480); 								//Größe des Fensters, x1=Beite, x2=Höhe
			
			frame.setLayout(null); 									//Kein vorgefertigtes Layout vewenden
			frame.setVisible(true); 								//Frame ist sichtbar
			frame.setLocationRelativeTo(null);						// Fenster startet in der Mitte
			frame.setResizable(false);								// Fenstergröße manuell nicht veränderbar
		}

}
