package the.game.java;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;*/
import javax.swing.*;

public class LevChooWindow extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//RadioButtons werden zur Levelauswahl deklariert
	private static JRadioButton eins;
	private static JRadioButton zwei;
	private static JRadioButton drei;
	private static LevChooWindow frame = new LevChooWindow("W�hle ein Level"); //Neues Objekt der Klasse LevChooWindow wird erzeugt

	
	//Buttons werden deklariert
	private JButton start;
	private JButton zurueck;
	
	//Konstruktor
	public LevChooWindow(String title){
		super(title);
		
		//RadioButtons werden erzeugt; Level 1 ist gesetzt
		eins= new JRadioButton("Level 1", true);
		add(eins);
		
		zwei= new JRadioButton("Level 2");
	    add(zwei);
		
		drei= new JRadioButton("Level 3");
		add(drei);
		
		//RadioButtons werden in Gruppe levchoose zusammengefasst -> nur einer kann gesetzt sein
		ButtonGroup levchoose=new ButtonGroup();
		levchoose.add(eins);
		levchoose.add(zwei);
		levchoose.add(drei);
		
		//Buttons werden hinzugef�gt
		start= new JButton("Los geht's");
		//start.setBounds(100, 20, 400, 40); nicht n�tig wegen Layout
		start.addActionListener(this);
		add(start);
		
		zurueck=new JButton("Zum Hauptmen�");
		//zurueck.setBounds(100, 200, 200, 40); nicht n�tig wegen Layout
		zurueck.addActionListener(this);
		add(zurueck);
		
	}
	
	//Was passiert bei Dr�cken der Buttons?
	public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und l�st methode aus
		
		//Wurde der Los gehts Button gedr�ckt?
		if (e.getSource()==start){
			//Welches Level wurde ausgew�hlt?
			if(eins.isSelected()==true){
				StartWindow.fenster(1);  //Level 1
			}else if(zwei.isSelected()==true){
				StartWindow.fenster(2);  //Level 2
			}else{
				StartWindow.fenster(3);  //Level 3
			}
			frame.dispose();
		}
		//Wurde der Hauptmen�Butten gedr�ckt?
		if (e.getSource()==zurueck){
			StartWindow.main(null); //Zur�ck zum Hauptmen�
		}

   }
	
	public static void main(String[] args) {
			
		frame.setSize(300, 120);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	    frame.setLayout(new FlowLayout());
		

	}

}
