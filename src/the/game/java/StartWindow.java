package the.game.java;

import javax.swing.*;
//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame implements ActionListener {

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//Buttons werden deklariert
		private JButton starten;
		private JButton levwahl;
		private JButton einstellung;
		private JButton info;
		private JButton ende;
		private static StartWindow frame = new StartWindow("Menü");
		private static JFrame vendor;
		
		public StartWindow(String title){ //Konstruktor
			//Titel einstellen
			super(title);
			
			//Buttons werden erzeugt
			starten = new JButton("Neues Spiel");
			starten.setBounds(120,40,160,40); //x1=x-Koordinate, x2=y-Koordinate, x3=Breite, x4=Höhe
			starten.addActionListener(this);
			add(starten); //Button schliessen hinzufügen
			
			levwahl = new JButton("Level auswählen");
			levwahl.setBounds(120,120,160,40);
			levwahl.addActionListener(this);
			add(levwahl);
			
			einstellung = new JButton("Einstellungen");
			einstellung.setBounds(120,200,160,40);
			einstellung.addActionListener(this);
			add(einstellung);
			
			info = new JButton("Credits");
			info.setBounds(120,280,160,40);
			info.addActionListener(this);
			add(info);
			
			ende = new JButton("Beenden");
			ende.setBounds(120,360,160,40);
			ende.addActionListener(this);
			add(ende);
			
		}
		
	  public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und löst methode aus
			
			if (e.getSource()==starten){
				fenster();
			}
			if (e.getSource()==levwahl){
				LevChooWindow.main(null);
			}
			if (e.getSource()==info){
				Object[] options = {"OK"};
				//Message Box erstellen
				JOptionPane.showOptionDialog(null,  "Sommer ist schöner als Winter", "Information", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			}
			if (e.getSource()==einstellung){
				auswahl();
			}
			if (e.getSource()==ende){
				System.exit(0);
			}
	   }
		
		public static void fenster(){	// Ohne Parameter - startet level 1
			//JFrame fenster= new JFrame("Hier ist bald das Spiel"); //super(titel)
			//fenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //verwirft das Fenster und entfernt es aus dem Speicher
			//fenster.setSize(650,350);
			//fenster.setVisible(true); //Fenster wird angezeigt
			//fenster.add(new LevelErstellen());
			Runner runner = new Runner();
			vendor = runner;
			new Enemy(9, 16);
			frame.dispose();
		}
		
		public static void fenster(int levelnummer){	// Mit Parameter - startet Level mit der nummer "levelnummer"
			//JFrame fenster= new JFrame("Hier ist bald das Spiel"); //super(titel)
			//fenster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //verwirft das Fenster und entfernt es aus dem Speicher
			//fenster.setSize(650,350);
			//fenster.setVisible(true); //Fenster wird angezeigt
			//fenster.add(new LevelErstellen());
			Runner runner = new Runner(levelnummer);
			vendor = runner;
			frame.dispose();
		}
		
		public static JFrame getRunner() {
			return vendor;
		}
		
		public static void auswahl(){
			JFrame auswahl= new JFrame("Hier sind bald die Einstellungen");
			auswahl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			auswahl.setSize(400,400);
			//auswahl.setLocation(50, 50);
			auswahl.setVisible(true);
		}
		

		
		public static void main(String[] args) {
//			StartWindow frame = new StartWindow("Menü"); //Neues Objekt der Klasse StartWindow wird erzeugt
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Schliessen mit x?
			frame.setSize(400,480); //Größe des Fensters, x1=Beite, x2=Höhe
			
			frame.setLayout(null); //Kein vorgefertigtes Layout vewenden
			frame.setVisible(true); //Frame ist sichtbar

		}

	
	

}
