package the.game.java;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 *  Diese Klasse erstellt das Fenster, in welchem mit Hilfe von RadioButtons ein Level ausgewählt werden kann.
 */
public class SettingWindow extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	//RadioButtons werden zur Levelauswahl deklariert
	private static JRadioButton keys;											// RadioButton Level 1
	private static JRadioButton controller;											// RadioButton Level 2 

	private static SettingWindow frame = new SettingWindow("Einstellungen"); 	//Neues Objekt der Klasse SettingWindow wird erzeugt

	//Buttons werden deklariert
	private JButton ok;
	
	/**
	 * Konstruktor der Klasse SettingWindow
	 * @param title Titel des Fensters
	 */
	public SettingWindow(String title){
		super(title);
		
		//RadioButtons werden erzeugt; Level 1 ist gesetzt
		keys= new JRadioButton("Tastatur", true);
		//keys.setBounds(x, y, width, height)
		add(keys);
		
		controller= new JRadioButton("Controller");
	    add(controller);
		
		//RadioButtons werden in Gruppe levchoose zusammengefasst -> nur einer kann gesetzt sein
		ButtonGroup controls=new ButtonGroup();
		controls.add(keys);
		controls.add(controller);
		
		
		//Buttons werden hinzugefügt
		ok= new JButton("Fertig");
		ok.setBounds(100, 100, 400, 40); 
		ok.addActionListener(this);						// ActionListener wird mit Button verknüpft
		add(ok);
		
	}
	
	/**
	 * Was passiert bei Drücken der Buttons?
	 */
	public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und löst methode aus
		
		//Wurde der Los gehts Button gedrückt?
		if (e.getSource()==ok){
			
			//Welches Level wurde ausgewählt?
			if(keys.isSelected()==true){
				Settings.setControlsToMouse();
			}else if(controller.isSelected()==true){
				Settings.setControlsToGamePad();
			}
			frame.dispose();							// SettingWindow wird geschlossen
		}

	}
	
	/**
	 * Main-Methode der Klasse SettingWindow
	 */
	public static void main(String[] args) {
			
		frame.setSize(200, 100);									// Fenstergröße wird festgelegt
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// Fenster wird geschlossen durch X
		frame.setVisible(true);				
		frame.setLocationRelativeTo(null);							// Fenster startet in der Mitte
		frame.setLayout(new FlowLayout());							// Layout
	    frame.setResizable(false);									// Fenstergröße manuell nicht veränderbar	
	}

}
