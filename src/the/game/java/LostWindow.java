package the.game.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JOptionPane;

/**
 * Stellt das Fenster dar, dass sich �ffnet, wenn man endg�ltig verloren hat.
 * @author Kapie
 *
 */
public class LostWindow extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	//Buttons werden deklariert
	private JButton zurueck;
	private JButton nochmal;
	private JButton choose;		
			
	/**
	 * Konstruktor der Klasse LostWindow
	 * @param title Titel des Fensters
	 */
	public LostWindow(String title){ //Konstruktor
		//Titel einstellen
		super(title);
				
	    //Buttons werden erzeugt
		zurueck= new JButton("Zur�ck zum Hauptmen�");
		zurueck.setBounds(100, 20, 200, 40);
		zurueck.addActionListener(this);					// ActionListener wird mit Button verkn�pft
		add(zurueck);
				
		nochmal= new JButton("Nochmal von Anfang");
		nochmal.setBounds(340, 20, 200, 40);
		nochmal.addActionListener(this);					// ActionListener wird mit Button verkn�pft
		add(nochmal);
		
		
		choose= new JButton("Level ausw�hlen");
		choose.setBounds(580, 20, 200, 40);
		choose.addActionListener(this);						// // ActionListener wird mit Button verkn�pft
		add(choose);
		 
				
		}
		
	    //Was passiert wenn ein Button gedr�ckt wird?
	/**
	 * Was pasiiert, wenn ein Button gedr�ckt wird?
	 */
	public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und l�st methode aus
				
		if (e.getSource()==zurueck){
			this.dispose();
			StartWindow.main(null); 		// Hauptmen�
		}
		if (e.getSource()==nochmal){
			this.dispose();
			StartWindow.fenster(); 			// Spiel
		}
		if (e.getSource()==choose){
			this.dispose();
			LevChooWindow.main(null); 		// Levelwahl
		}
	}
			
	/**
	 * Main-Methode der Klasse. Erstellt das entsprechende Fenster.		
	 */
	public static void main(String[] args) {
		LostWindow frame = new LostWindow("You Lost! HAHAHAHA. Was m�chtest du jetzt tun?"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  			// Schliessen mit x
		frame.setSize(880, 120);    
				
		frame.setLayout(null); 											// Kein vorgefertigtes Layout vewenden
		frame.setVisible(true); 										// Frame ist sichtbar
		frame.setLocationRelativeTo(null);								// Fenster startet in der Mitte
		frame.setResizable(false);										// Fenstergr��e manuell nicht ver�nderbar
	}
}
