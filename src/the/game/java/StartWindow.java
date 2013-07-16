package the.game.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import level.creator.java.EditorRunner;

/**
* Mit dieser Klasse wird das gesamte Spiel gestertet. Sie stellt das Hauptmenü dar.
*/
public class StartWindow extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
		// Buttons werden deklariert
		private JButton starten;
		private JButton levwahl;
		private JButton einstellung;
		private JButton create;
		private JButton ende;
		private JButton dateiwahl;
		private JButton multiplayer;
		private static StartWindow frame = new StartWindow("Menü");
		private static JFrame vendor;
		int rueckFileChooser;
		
		/**
		 * Konstruktor der Klasse StartWindow
		 * @param title Titel des Fensters
		 */
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
			
			multiplayer = new JButton("Multiplayer");
			multiplayer.setBounds(120,200,160,40);
			multiplayer.addActionListener(this);
			add(multiplayer);
			
			einstellung = new JButton("Einstellungen");
			einstellung.setBounds(120,280,160,40);
			einstellung.addActionListener(this);
			add(einstellung);
			
			dateiwahl = new JButton("Raum aus Datei");
			dateiwahl.setBounds(120,360,160,40);
			dateiwahl.addActionListener(this);
			add(dateiwahl);
			
			create = new JButton("Level Creator");
			create.setBounds(120,440,160,40);
			create.addActionListener(this);
			add(create);
			
			ende = new JButton("Beenden");
			ende.setBounds(120, 520, 160,40);
			ende.addActionListener(this);
			add(ende);
			
		}
		
		/**
		 * Aktion, die durch Betätigung eines Buttons ausgeführt werden soll
		 */
		public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und löst methode aus
			
			if (e.getSource()==starten){			// Wird "Neues Spiel" gedrückt?
				fenster();								// Das Fenster für das Spiel wird geöffnet -> Level 1
			}
			if (e.getSource()==levwahl){			// Wird "Level auswählen" gedrückt?
				LevChooWindow.main(null);				// LevChooWindow wird gestartet
			}
			if (e.getSource()==dateiwahl){
				JFileChooser chooser = new JFileChooser();
				rueckFileChooser=chooser.showOpenDialog(null);										// Fenster zum 'Öffnen'
				if(rueckFileChooser==JFileChooser.APPROVE_OPTION){
					System.out.println("Die zu öffnende Datei ist: " + chooser.getSelectedFile().getName());
					Runner runner = new Runner(chooser.getSelectedFile().getPath());
					vendor = runner;
				}
			}
			if (e.getSource()==create){
				EditorRunner.main(null);
			}
			if (e.getSource()==einstellung){		// Wird "Einstellungen" gedrückt
				SettingWindow.main(null);								// Das Fenster auswahl() wird geöffnet
			}
			if (e.getSource()==ende){				// Wird "Beenden" gedrückt?
				System.exit(0);							// Das Menü/Spiel wird beendet
			}
			if (e.getSource()==multiplayer){
				MultiplayerWindow.main(null);
			}
	    }
		/**
		 * Fenster, in dem sich das Spielvon Anfang an startet
		 */
		public static void fenster(){		// Ohne Parameter - startet level 1
			Runner runner = new Runner();	// Neues Objekt der Klasse Runner wird erstellt
			vendor = runner;				// runner bekommt Namen vendor (Wird später benötigt)
			frame.dispose();				// Menü schließt sich
		}
		/**
		 * Fenster in dem sich das Spiel von Levelnr an startet
		 * @param levelnummer
		 */
		public static void fenster(int levelnummer){	// Mit Parameter - startet Level mit der nummer "levelnummer"
			Runner runner = new Runner(levelnummer);	// Neues Objekt der Klasse Runner wird erstellt
			vendor = runner;							// runner bekommt Namen vendor (Wird später benötigt)
			frame.dispose();							// Menü schließt sich
		}
		/**
		 * Gibt das Frame zurück
		 * @return Frame
		 */
		public static JFrame getRunner() {				// ???
			return vendor;								// ???
		}
		/**
		 * Fenster in dem sich alle Einstellungen befinden
		 */
		public static void setting(){
			JFrame auswahl= new JFrame("Einstellungen"); 
			
			auswahl.setLocationRelativeTo(null);						// Fenster startet in der Mitte
			auswahl.setResizable(false);								// Fenstergröße manuell nicht veränderbar
			auswahl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		// Fenster wird durch X geschlossen
			auswahl.setSize(400,400);										// Größe des Fensters
			auswahl.setVisible(true);
		}
		/**
		 * Main-Methode der Klasse StartWindow
		 * Ein neues Menü-Fenster wird gestartet
		 */
		public static void main(String[] args) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//Spiel beenden mit x
			frame.setSize(400,650); 								//Größe des Fensters, x1=Beite, x2=Höhe
			
			frame.setLayout(null); 									//Kein vorgefertigtes Layout vewenden
			frame.setVisible(true); 								//Frame ist sichtbar
			frame.setLocationRelativeTo(null);						// Fenster startet in der Mitte
			frame.setResizable(false);								// Fenstergröße manuell nicht veränderbar
		}

}
