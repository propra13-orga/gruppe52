package level.creator.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;


public class Editor extends JFrame implements ActionListener {

	/**
	 *  Mit dieser Klasse wird der Editor gestartet.
	 */
	
	private static final long serialVersionUID = 1L;
	
	Current currentpos=new Current();										// Objekt der Klasse Current um auf Objekte und Methoden aus Current zuzugreifen
	
	private JButton starten;												// "Ferig" Button um die map auszugeben
	
	static int arrayLenghtX=48;												// Map 48*20 Pixel breit
	static int arrayLenghtY=25;												// Map 25*20 Pixel hoch
	public static int[][] map;												// Zweidimensionales Array stellt Map dar
	
	public static Timer timer;												// erzeugt eine Variable timer des Typs Timer
	
	int defaultx=3;															//	Startposition des Spielfelds
	int defaulty=100;
	
	int stripesy=25;														// Anzahl der Streifen in der Map
	int stripesx=48;
	
	int drawx;																// Aktuell gezeichneter Streifen
	int drawy;
	
	public static int stabilisation=1;										// Stabilisiert die Bewegung
	
	/**
	 * Initialisierung aller nötigen Bilder
	 */
	
	private ImageIcon ii;
	
    private Image wall = setImagePath("wall.png");
    private Image trap = setImagePath("mine.gif");
    private Image heart = setImagePath("heart.png");
    private Image finalGoal = setImagePath("goal.png");
    private Image shield = setImagePath("shield.png");
    private Image bouncy = setImagePath("allenemy.png");
    private Image tracker = setImagePath("allenemy.png");
    private Image vertiMonster = setImagePath("allenemy.png");
    private Image horiMonster = setImagePath("allenemy.png");
    private Image r_u_monster = setImagePath("allenemy.png");
    private Image l_u_monster = setImagePath("allenemy.png");
    //private Image bg = setImagePath("bg.png");
	
	public Editor(){
		addKeyListener(new TAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(966, 900);
		setSize(1000, 900);
		setLocationRelativeTo(null);									// Fenster im zentrum
		setTitle("Level Creator");
		setResizable(false);											// Größe unveränderbar
		setVisible(true);
		setLayout(null);
		setFocusable(true);												// Kann fokussiert werden; Relevant für den Keylistener
        //setBackground(Color.GREEN);
		//setDoubleBuffered(true);										// verhindert mögliches Flackern
		
		/**
		 *  Button wird hinzugefügt
		 */
		
		starten = new JButton("Fertig");								// neuer Button (starten) mit Aufschrift "Fertig"
		starten.setBounds(400,800,160,40);								// x1=x-Koordinate, x2=y-Koordinate, x3=Breite, x4=Höhe
		starten.addActionListener(this);								// ActionListener wird mit Button verknüpft
		add(starten); 													// Button starten hinzufügen
		
		map = new int[arrayLenghtX][arrayLenghtY];						// 2D-Array mit arrayLenghtX(48)*arrayLenghtY(25) wird angelegt
		resetMap();
		
		timer = new Timer(80, this);									// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt das Aktualisierungsintervall fest (in millisekunden)
        timer.start();													// startet den timer
	}
	
    public Image setImagePath(String path) {							// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(this.getClass().getResource(path));
    	Image img = ii.getImage();
    	return img;
    }
    
    private void resetMap() {											// resetet Map
    	for(int a=0; a<arrayLenghtX; a++){
			for(int b=0; b<arrayLenghtY; b++){
				map[a][b] = 0;
			}
		}
    }
	
    /**
     * Zeichnen des gesamten Fensters
     */
    
	public void paint(Graphics g){										// Zeichnet Map 
		super.paint(g);
        
        Graphics2D g2d = (Graphics2D)g;
		
		//g2d.drawImage(bg, 3, 100, this);										//Hintergrund
        
        /**
         *  Streifen für Map werden gezeichnet
         */
       
		for(int laufy=0; laufy<=stripesy;laufy++){
			drawy=defaulty-1+20*laufy;											// drawy = aktuell gezeichneter Streifen von < nach >
			g2d.fillRect(defaultx-1, drawy, stripesx*20+1, 2);
		}
		for(int laufx=0; laufx<=stripesx; laufx++){
			drawx=defaultx-1+20*laufx;											// drawx = aktuell gezeichneter Streifen von ^ nach v
			g2d.fillRect(drawx, defaulty-1, 2, stripesy*20+1);
			
		}

		/**
		 *  Legende für Leveleditor
		 */
		
		g2d.drawImage(wall, 23, 620, this);																	// Wand
		g2d.drawString("Drücke '1' um eine Wand einzufügen", 63, 635);
		g2d.drawImage(trap, 23, 650, this);																	// Falle
		g2d.drawString("Drücke '2' um eine Falle einzufügen", 63, 665);
		g2d.drawImage(heart, 23, 680, this);																// Leben
		g2d.drawString("Drücke '3' um ein Leben einzufügen", 63, 695);
		g2d.drawImage(finalGoal, 23, 710, this);															// Ziel
		g2d.drawString("Drücke '4' um ein Ziel einzufügen", 63, 725);
		g2d.drawImage(shield, 23, 740, this);																// Rüstung
		g2d.drawString("Drücke '5' um eine Rüstung einzufügen", 63, 755);
		//g2d.drawImage(trank, 23, 770, this);																// Zaubertrank
		g2d.drawString("Drücke '6' um einen Zaubertrank einzufügen", 63, 785);
		g2d.fillRect(323, 620, 2, 170);																		// Trennlinie
		g2d.drawImage(horiMonster, 345, 620, this);															// Horizontales Monster
		g2d.drawString("Drücke 'q' um einen horizontalen Feind einzufügen", 385, 635);
		g2d.drawImage(vertiMonster, 345, 650, this);														// Vertikales Monster
		g2d.drawString("Drücke 'w' um einen vertikalen Feind einzufügen", 385, 665);
		g2d.drawImage(r_u_monster, 345, 680, this);															// right-up Monster
		g2d.drawString("Drücke 'e' um einen rechts-oben-diagonalen Feind einzufügen", 385, 695);
		g2d.drawImage(l_u_monster, 345, 710, this);															// left-up Monster
		g2d.drawString("Drücke 'r' um einen links-oben-diagonalen Feind einzufügen", 385, 725);
		g2d.drawImage(bouncy, 345, 740, this);																// Bouncy
		g2d.drawString("Drücke 't' um einen Bouncy einzufügen", 385, 755);
		g2d.drawImage(tracker, 345, 770, this);																// Tracker
		g2d.drawString("Drücke 'z' um einen Tracker einzufügen", 385, 785);
		
		g2d.setColor(Color.red);
		g2d.drawRect(currentpos.getCurrentX(), currentpos.getCurrentY(),17, 17);							// Aktuell ausgewähltes Feld wird mit rotem Rechteck markiert
		//System.out.println(currentpos.getCurrentX());
		//System.out.println(currentpos.getCurrentY());
		
		/**
		 *  Images einfügen
		 */
		
		//int imageType = currentpos.getLoadingImage();
		for(int a=0; a<arrayLenghtX; a++){
			for(int b=0; b<arrayLenghtY; b++){
				/**
				 * Array-Inhalt wird überprüft
				 */
				switch(map[a][b]){							
					case 1:																				// Array Inhalt = 1 => Wand
						g2d.drawImage(wall,a*20+defaultx,b*20+defaulty, this);
						break;
					case 2:																				// Array Inhalt = 2 => Falle
						g2d.drawImage(trap,a*20+defaultx,b*20+defaulty, this);
						break;
					case 3:																				// Array Inhalt = 3 => Leben
						g2d.drawImage(heart,a*20+defaultx,b*20+defaulty, this);
						break;
					case 4:																				// Array Inhalt = 4 => Ziel
						g2d.drawImage(finalGoal,a*20+defaultx,b*20+defaulty, this);
						break;
					case 5:																				// Array Inhalt = 5 => Rüstung
						g2d.drawImage(shield,a*20+defaultx,b*20+defaulty, this);
						break;
					/*case 6:																			// Array Inhalt = 6 => Zaubertrank
						g2d.drawImage(trank,a*20+defaultx,b*20+defaulty, this);
						break;*/
					case 11:																			// Array Inhalt = 11 => horizontales Monster
						g2d.drawImage(horiMonster,a*20+defaultx,b*20+defaulty, this);
						break;	
					case 12:																			// Array Inhalt = 12 => vertikales Monster
						g2d.drawImage(vertiMonster,a*20+defaultx,b*20+defaulty, this);
						break;
					case 13:																			// Array Inhalt = 13 => right-up Monster
						g2d.drawImage(r_u_monster,a*20+defaultx,b*20+defaulty, this);
						break;
					case 14:																			// Array Inhalt = 14 => left-up Monster							
						g2d.drawImage(l_u_monster,a*20+defaultx,b*20+defaulty, this);
						break;
					case 15:																			// Array Inhalt = 15 => Bouncy
						g2d.drawImage(bouncy,a*20+defaultx,b*20+defaulty, this);
						break;
					case 16:																			// Array Inhalt = 16 => Tracker
						g2d.drawImage(tracker,a*20+defaultx,b*20+defaulty, this);
						break;
				}
			}
		}
		
		
		Toolkit.getDefaultToolkit().sync();									// Fenster wird "flüssig" dargestellt
		g2d.dispose();
	}
	
	
	public void actionPerformed(ActionEvent event) {		
		if(stabilisation >= 2){
			currentpos.move();												// Positionsdaten der bewegbaren Objekte aktualisieren und im Anschluss neu zeichnen

			stabilisation = 0;
		}
		repaint();
		
		/**
		 *  Button-Abfrage; Der Inhalt des Arrays wird ausgegeben und das Fenster geschlossen
		 */
		
		if (event.getSource()==starten){
			for(int a=0; a<arrayLenghtY; a++){
				for(int b=0; b<arrayLenghtX; b++){
					if(map[b][a]<10){
						System.out.print(map[b][a]+"; ");
					}else{
						System.out.print(map[b][a]+";");
					}
				}
				System.out.print("/");
				System.out.println();
			}
			dispose();
		}
		
		
														
    }
	
	public static void main(String[] args){
		new Editor();
		//repaint();
		
	}

    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent event) {		// Wenn Taste losgelassen wird:
        	currentpos.keyReleased(event);
        }

        public void keyPressed(KeyEvent event) {		// Wenn Taste gedrückt wird:
        	currentpos.keyPressend(event);
        }
    }
	
	
}
