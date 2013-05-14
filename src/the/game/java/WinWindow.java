package the.game.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class WinWindow extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Buttons werden deklariert
	private JButton zurueck;
	private JButton nochmal;
	private JButton choose;		
			
	public WinWindow(String title){ //Konstruktor
		//Titel einstellen
		super(title);
				
	    //Buttons werden erzeugt
				
		zurueck= new JButton("Zurück zum Hauptmenü");
		zurueck.setBounds(100, 20, 200, 40);
		zurueck.addActionListener(this);
		add(zurueck);
				
		nochmal= new JButton("Nochmal von Anfang");
		nochmal.setBounds(340, 20, 200, 40);
		nochmal.addActionListener(this);
		add(nochmal);
		
		
		choose= new JButton("Level wählen"); 
		choose.setBounds(580, 20, 200, 40);
		choose.addActionListener(this);
		add(choose);
		 
				
		}
		
	      //Was passiert wenn Butten gedrückt wird?
		  public void actionPerformed (ActionEvent e){ //wenn eine Aktion passiert bekommt ActionListener das mit und löst methode aus
				
				if (e.getSource()==zurueck){
					StartWindow.main(null); //Hauptmenü
				}
				if (e.getSource()==nochmal){
					StartWindow.fenster(); //Spiel
				}
				if (e.getSource()==choose){  
					LevChooWindow.main(null); //Levelwahl#
				}
		   }
			
			
			
			public static void main(String[] args) {
				WinWindow frame = new WinWindow("You Won! JUHUUUUUUUU! Was möchtest du jetzt tun?"); 
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Schliessen mit x?
				frame.setSize(880, 120);                       
				
				frame.setLayout(null); //Kein vorgefertigtes Layout vewenden
				frame.setVisible(true); //Frame ist sichtbar

			}

}
