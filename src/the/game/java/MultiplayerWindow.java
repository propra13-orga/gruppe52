package the.game.java;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Erstellt das Fenster, dass sich zum Spielen im Mehrspielermodus öffnet.
 *
 */
public class MultiplayerWindow extends JFrame implements ActionListener {
	
	private JButton server;
	private JButton client;
	
	private boolean ipEntered = false;
	
	private static JTextField ipAdr;
	
	JLabel ipText;
	
	private static MultiplayerWindow frame = new MultiplayerWindow("Multiplayer Menü");
	
	/**
	 * Konstruktor der Klasse MultiplayerWindow.
	 * Alle Buttons/TextFilds/Labels werden angelegt.
	 * @param title titel des Fensters
	 */
	public MultiplayerWindow(String title){
		super(title);
		
		server = new JButton("Server starten");
		server.setBounds(40, 40, 200, 30);
		server.addActionListener(this);
		add(server);
		
		client = new JButton("Server beitreten");
		client.setBounds(40, 90, 200, 30);
		client.addActionListener(this);
		add(client);
		
		ipAdr = new JTextField();
		ipAdr.setBounds(100, 140, 140, 30);
		ipAdr.setForeground(Color.RED);
		ipAdr.setBackground(Color.WHITE);
		add(ipAdr);
		
		ipText = new JLabel("IP-Adr.:");
		ipText.setBounds(40, 140, 60, 30);
		add(ipText);
	}
	
	/**
	 * Sorgt dafür, dass nach Buttonklick die gewünschte Aktion ausgeführt wird.
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==server){
			try {
				Settings.setGameToMultiplayer();
				//dispose();
				NetServer.main(null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==client){
			if(!ipAdr.getText().isEmpty()){
				System.out.println(ipAdr.getText());
				ipEntered=true;
				Settings.setGameToMultiplayer();
				StartWindow.fenster();
				
			}else{
				System.out.println("Fehler: Du musst eine IP-Adresse angeben");
			}
		}
	}
	/**
	 * Gibt die eingegebene IP-Adresse zurück
	 * @return ipAdresses
	 */
	public static String getIPAdr(){
		return ipAdr.getText();
	}
	
	/**
	 * Main-Mathode der Klasse MultiplayerWindow: Erstellt das Fenster.
	 */
	public static void main(String[] args) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 	//Spiel beenden mit x
		frame.setSize(280,250); 								//Größe des Fensters, x1=Beite, x2=Höhe
		
		frame.setLayout(null); 									//Kein vorgefertigtes Layout vewenden
		frame.setVisible(true); 								//Frame ist sichtbar
		frame.setLocationRelativeTo(null);						// Fenster startet in der Mitte
		frame.setResizable(false);								// Fenstergröße manuell nicht veränderbar
	}
}
