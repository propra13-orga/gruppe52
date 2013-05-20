package the.game.java;

import javax.swing.*;
//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame implements ActionListener {

     /**
	 * Mit dieser Klasse wird das gesamte Spiel gestertet. Sie stellt das Hauptmen� dar.
	 */
	
	private static final long serialVersionUID = 1L;
		// Buttons werden deklariert
		private JButton starten;
		private JButton levwahl;
		private JButton einstellung;
		private JButton info;
		private JButton ende;
		private static StartWindow frame = new StartWindow("Men�");
		private static JFrame vendor;
		
		public StartWindow(String title){ //Konstruktor
			// Titel einstellen
			super(title);
			
			// Buttons werden erzeugt
			starten = new JButton("Neues Spiel");	// neuer Button (starten) mit Aufschrift "Neues Spiel"
			starten.setBounds(120,40,160,40);		// x1=x-Koordinate, x2=y-Koordinate, x3=Breite, x4=H�he
			starten.addActionListener(this);		// ActionListener wird mit Button verkn�pft
			add(starten); 							// Button schliessen hinzuf�gen
			
			levwahl = new JButton("Level ausw�hlen");
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
		
		public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und l�st methode aus
			
			if (e.getSource()==starten){			// Wird "Neues Spiel" gedr�ckt?
				fenster();								// Das Fenster f�r das Spiel wird ge�ffnet -> Level 1
			}
			if (e.getSource()==levwahl){			// Wird "Level ausw�hlen" gedr�ckt?
				LevChooWindow.main(null);				// LevChooWindow wird gestartet
			}
			//if (e.getSource()==info){				// Wird "Informationen" gedr�ckt?
			//	Object[] options = {"OK"};
			//	//Message Box erstellen
			//	JOptionPane.showOptionDialog(null,  "Sommer ist sch�ner als Winter", "Information", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			//}
			if (e.getSource()==einstellung){		// Wird "Einstellungen" gedr�ckt
				auswahl();								// Das Fenster auswahl() wird ge�ffnet
			}
			if (e.getSource()==ende){				// Wird "Beenden" gedr�ckt?
				System.exit(0);							// Das Men�/Spiel wird beendet
			}
	    }
		
		public static void fenster(){		// Ohne Parameter - startet level 1
			Runner runner = new Runner();	// Neues Objekt der Klasse Runner wird erstellt
			vendor = runner;				// runner bekommt Namen vendor (Wird sp�ter ben�tigt)
			frame.dispose();				// Men� schlie�t sich
		}
		
		public static void fenster(int levelnummer){	// Mit Parameter - startet Level mit der nummer "levelnummer"
			Runner runner = new Runner(levelnummer);	// Neues Objekt der Klasse Runner wird erstellt
			vendor = runner;							// runner bekommt Namen vendor (Wird sp�ter ben�tigt)
			frame.dispose();							// Men� schlie�t sich
		}
		
		public static JFrame getRunner() {				// ???
			return vendor;								// ???
		}
		
		public static void auswahl(){
			JFrame auswahl= new JFrame("Hier sind bald die Einstellungen"); 
			auswahl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		// Fenster wird durch X geschlossen
			auswahl.setSize(400,400);										// Gr��e des Fensters
			auswahl.setVisible(true);
		}
		
		public static void main(String[] args) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//Spiel beenden mit x
			frame.setSize(400,480); 								//Gr��e des Fensters, x1=Beite, x2=H�he
			
			frame.setLayout(null); 									//Kein vorgefertigtes Layout vewenden
			frame.setVisible(true); 								//Frame ist sichtbar
			frame.setLocationRelativeTo(null);						// Fenster startet in der Mitte
			frame.setResizable(false);								// Fenstergr��e manuell nicht ver�nderbar
		}

}
