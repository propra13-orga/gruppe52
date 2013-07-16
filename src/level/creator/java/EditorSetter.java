package level.creator.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import the.game.java.Controls;
import the.game.java.Savegame;

/**
 * Erstellt das Panel in dem der Creator läuft
 * @author Kapie
 *
 */
public class EditorSetter extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public static Timer timer;
	
	public static Current currentpos=new Current();										// Objekt der Klasse Current um auf Objekte und Methoden aus Current zuzugreifen
	
	public static List<Point> currentList = new ArrayList<Point>();
	
	private JButton starten;												// "Ferig" Button um die map auszugeben
	private JButton öffnen;
	
	public static JCheckBox darkness;
	public static JCheckBox multi;
	
	static int arrayLenghtX=48;												// Map 48*20 Pixel breit
	static int arrayLenghtY=26;												// Map 25*20 Pixel hoch
	public static int[][] map;												// Zweidimensionales Array stellt Map dar
	
	static boolean dark=false;
	static boolean multiplay=false;
	
	int defaultx=3;															//	Startposition des Spielfelds
	int defaulty=100;
	
	public static int offsetX=4;
	public static int offsetY=102;
	
	int stripesy=25;														// Anzahl der Streifen in der Map
	int stripesx=48;
	
	int drawx;																// Aktuell gezeichneter Streifen
	int drawy;
	
	int rueckFileChooser;
	
	public static int stabilisation=1;

	/*
	 * Initialisierung aller nötigen Bilder
	 */
	private Image font = getImage("levelCreatorFont.png");
	
    private Image wall = getImage("wall.png");
    private Image trap = getImage("mine.gif");
    private Image heart = getImage("heart.png");
    private Image finalGoal = getImage("goal.png");
    private Image shield = getImage("shield.png");
    private Image mana = getImage("mana.png");
    private Image checkpoint = getImage("checkpoint.png");
    private Image spawn = getImage("spawn.png");
    private Image bouncy = getImage("bouncyCreator.png");
    private Image tracker = getImage("trackerCreator.png");
    private Image vertiMonster = getImage("v_monster.png");
    private Image horiMonster = getImage("h_monster.png");
    private Image r_u_monster = getImage("ru_monster.png");
    private Image l_u_monster = getImage("lu_monster.png");
    private Image credit = getImage("goodies/credits.png");
    private Image marker = getImageFromLC("marker.png");
    private Image shop = getImage("npc/npcShop.png");
    private Image dalek = getImage("traps/dalek.png");
    private Image door_h_up = getImage("door/door_h_up.png");
    private Image door_h_down = getImage("door/door_h_down.png");
    private Image door_v_right = getImage("door/door_v_right.png");
    private Image door_v_left = getImage("door/door_v_left.png");

    /**
     * Konstruktor der Klasse EditorSetter
     */
    public EditorSetter() {	// Konstruktor
		addKeyListener(new KAdapter());		// Fügt KeyListener hinzu und erstellt ein neues Objekt der Klasse TAdapter, welches die Methoden 'keyReleased' und 'keyPressed' überschreibt
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MAdapter());
		
        setFocusable(true);					// Kann fokussiert werden; Relevant für den Keylistener
        setBackground(Color.LIGHT_GRAY);			// Setzt Hintergrund auf Grau
        setDoubleBuffered(true);			// verhindert mögliches Flackern
        setOpaque(true);					// setzt ein JPanel undurchsichtig
        setLayout(null);
        
        timer = new Timer(8, this);									// erzeugt ein Objekt timer der Klasse Timer; Parameter: legt das Aktualisierungsintervall fest (in millisekunden)
        timer.start();

		/**
		 *  Button wird hinzugefügt
		 */
		
		starten = new JButton("Speichern unter...");								// neuer Button (starten) mit Aufschrift "Fertig"
		starten.setBounds(824,50,140,30);								// x1=x-Koordinate, x2=y-Koordinate, x3=Breite, x4=Höhe
		starten.addActionListener(this);								// ActionListener wird mit Button verknüpft
		add(starten); 													// Button starten hinzufügen
		
		öffnen = new JButton("Öffnen");
		öffnen.setBounds(664, 50, 140, 30);
		öffnen.addActionListener(this);
		add(öffnen);
        
		darkness = new JCheckBox("Dunkelheit"); //TODO:
		darkness.setSelected(false);
		darkness.setBounds(760, 630, 100, 20);
		add(darkness);
		
		multi = new JCheckBox("Multiplayer"); //TODO:
		multi.setSelected(false);
		multi.setBounds(760, 660, 100, 20);
		add(multi);
		
		map = new int[arrayLenghtX+1][arrayLenghtY+2];						// 2D-Array mit arrayLenghtX(48)*arrayLenghtY(25) wird angelegt
		resetMap();
		
			
    }
    
    /**
     * Setzt alle ArrayInhalte auf 0
     */
    public static void resetMap() {											// resetet Map
    	for(int a=0; a<arrayLenghtX; a++){
			for(int b=0; b<arrayLenghtY; b++){
				map[a][b] = 0;
			}
		}
    }
    
    /**
     * paint Methode des LevelCreators
     */
    public void paint(Graphics g){										// Zeichnet Map 
		super.paint(g);
        
        Graphics2D g2d = (Graphics2D)g;
		
		//g2d.drawImage(bg, 3, 100, this);										//Hintergrund
        
        g2d.drawImage(font, 24, 20, this);
        
        /**
         *  Streifen für Map werden gezeichnet
         */
        g2d.setColor(Color.GRAY);
		for(int laufy=0; laufy<=stripesy+1;laufy++){
			drawy=defaulty-1+20*laufy;											// drawy = aktuell gezeichneter Streifen von < nach >
			g2d.fillRect(defaultx-1, drawy, stripesx*20+1, 2);
		}
		for(int laufx=0; laufx<=stripesx; laufx++){
			drawx=defaultx-1+20*laufx;											// drawx = aktuell gezeichneter Streifen von ^ nach v
			g2d.fillRect(drawx, defaulty-1, 2, stripesy*20+1+20);
			
		}
		g2d.setColor(Color.BLACK);
		/**
		 *  Legende für Leveleditor
		 */
		
		g2d.drawImage(wall, 23, 630, this);																	// Wand
		g2d.drawString("Drücke '1' um eine Wand einzufügen", 63, 645);
		g2d.drawImage(credit, 23, 660, this);																	// Falle
		g2d.drawString("Drücke '2' um einen Credit einzufügen", 63, 675);
		g2d.drawImage(heart, 23, 690, this);																// Leben
		g2d.drawString("Drücke '3' um ein Leben einzufügen", 63, 705);
		g2d.drawImage(finalGoal, 23, 720, this);															// Ziel
		g2d.drawString("Drücke '4' um ein Ziel einzufügen", 63, 735);
		g2d.drawImage(shield, 23, 750, this);																// Rüstung
		g2d.drawString("Drücke '5' um eine Rüstung einzufügen", 63, 765);
		g2d.drawImage(mana, 23, 780, this);																// Zaubertrank
		g2d.drawString("Drücke '6' um einen Manatrank einzufügen", 63, 795);
		g2d.drawImage(checkpoint, 23, 810, this);																// Checkpoint
		g2d.drawString("Drücke '7' um einen Checkpoint einzufügen", 63, 825);
		g2d.drawImage(spawn, 23, 840, this);																// Checkpoint
		g2d.drawString("Drücke '8' um einen Startpunkt einzufügen", 63, 855);
		g2d.drawImage(shop, 23, 870, this);
		g2d.drawString("Drücke '9' um einen Shop einzufügen" , 63, 885);
		
		g2d.fillRect(323, 630, 2, 270);																		// Trennlinie
		
		g2d.drawImage(horiMonster, 345, 630, this);															// Horizontales Monster
		g2d.drawString("Drücke 'q' um einen horizontalen Feind einzufügen", 385, 645);
		g2d.drawImage(vertiMonster, 345, 660, this);														// Vertikales Monster
		g2d.drawString("Drücke 'w' um einen vertikalen Feind einzufügen", 385, 675);
		g2d.drawImage(r_u_monster, 345, 690, this);															// right-up Monster
		g2d.drawString("Drücke 'e' um einen rechts-oben-diagonalen Feind einzufügen", 385, 705);
		g2d.drawImage(l_u_monster, 345, 720, this);															// left-up Monster
		g2d.drawString("Drücke 'r' um einen links-oben-diagonalen Feind einzufügen", 385, 735);
		g2d.drawImage(bouncy, 345, 750, this);																// Bouncy
		g2d.drawString("Drücke 't' um einen Bouncy einzufügen", 385, 765);
		g2d.drawImage(tracker, 345, 780, this);																// Tracker
		g2d.drawString("Drücke 'z' um einen Tracker einzufügen", 385, 795);
		g2d.drawImage(trap, 345, 810, this);																	// Falle
		g2d.drawString("Drücke 'u' um eine Falle einzufügen", 385, 825);
		g2d.drawImage(dalek, 345, 840, this);																	// Falle
		g2d.drawString("Drücke 'i' um einen Dalek einzufügen", 385, 855);
		
		g2d.fillRect(740, 620, 2, 270);	
		
		g2d.drawImage(door_h_up, 760, 710, this);
		g2d.drawString("'a'", 800, 725);
		g2d.drawImage(door_h_down, 830, 710, this);
		g2d.drawString("'s'", 870, 725);
		g2d.drawString("Horizontale Türen", 760, 755);
		g2d.drawImage(door_v_right, 760, 770, this);
		g2d.drawString("'d'", 800, 785);
		g2d.drawImage(door_v_left, 830, 770, this);
		g2d.drawString("'f'", 870, 785);
		g2d.drawString("Vertikale Türen", 760, 815);
		
		//System.out.println(currentpos.getcurrentXPixel());
		//System.out.println(currentpos.getcurrentYPixel());
		
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
						g2d.drawImage(credit,a*20+defaultx,b*20+defaulty, this);
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
					case 6:																			// Array Inhalt = 6 => Zaubertrank
						g2d.drawImage(mana,a*20+defaultx,b*20+defaulty, this);
						break;
					case 7:
						g2d.drawImage(checkpoint, a*20+defaultx, b*20+defaulty, this);
						break;
					case 8:
						g2d.drawImage(spawn, a*20+defaultx, b*20+defaulty, this);
						break;
					case 9:
						g2d.drawImage(shop, a*20+defaultx, b*20+defaulty, this);
						break;
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
					case 17:																			// Array Inhalt = 16 => Tracker
						g2d.drawImage(trap,a*20+defaultx,b*20+defaulty, this);
						break;
					case 18:
						g2d.drawImage(dalek, a*20+defaultx-1, b*20+defaulty-5, this);
						break;
					case 20:
						g2d.drawImage(door_h_up, a*20+defaultx, b*20+defaulty, this);
						break;
					case 21:
						g2d.drawImage(door_v_right, a*20+defaultx, b*20+defaulty, this);
						break;
					case 22:
						g2d.drawImage(door_h_down, a*20+defaultx, b*20+defaulty, this);
						break;
					case 23:
						g2d.drawImage(door_v_left, a*20+defaultx, b*20+defaulty, this);
						break;
				}
			}
		}
		
		
		
		g2d.setColor(Color.red);
		
		if(Current.isDoorChooserActivated()){
			g2d.drawString("Türwahl aktiviert", 850, 93);
		}
		
		int index=Door.findDoorInList(Door.pixelToArray(Current.currentXPixel-defaultx), Door.pixelToArray(Current.currentYPixel-defaulty));
		if(index>=0){
			g2d.drawString("Verbunden ist: " + Door.doorList.get(index).x +"/"+ Door.doorList.get(index).y +" mit "+ Door.doorList.get(index).goalx +"/"+ Door.doorList.get(index).goaly, 750, 93);

		}
		
		for(int a=0; a<Door.doorList.size(); a++) {
			if(Door.doorList.get(a).goalx>=0 && Door.doorList.get(a).goaly>=0) {
				int x1 = Door.doorList.get(a).x + defaultx;
				int x2 = Door.doorList.get(a).goalx + defaultx;
				int y1 = Door.doorList.get(a).y + defaulty;
				int y2 = Door.doorList.get(a).goaly + defaulty;
				g2d.drawLine(x1, y1, x2, y2);
			}
		}
		
		g2d.drawRect(currentpos.getcurrentXPixel(), currentpos.getcurrentYPixel(),17, 17);							// Aktuell ausgewähltes Feld wird mit rotem Rechteck markiert
		
		
		// AUSWAHL DARSTELLEN
		if(!currentList.isEmpty()){
			for(int y=0; y<currentList.size(); y++){
				g2d.drawImage(marker, (int)(currentList.get(y).getX()*20+offsetX), (int)(currentList.get(y).getY()*20+offsetY), this);
			}
		}
		
		
		Toolkit.getDefaultToolkit().sync();									// Fenster wird "flüssig" dargestellt
		g2d.dispose();
	}
    
    /**
     * Sorgt für den Effekt nach Button drücken
     */
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
			/*for(int a=0; a<arrayLenghtX; a++){
				for(int b=0; b<arrayLenghtY; b++){
					switch(map[a][b]){
						case 1:																				// Array Inhalt = 1 => Wand
							System.out.println("<wall x=\"" + a + "\" y=\"" + b + "\" width=\"1\" height=\"1\"");
							break;
						case 2:																				// Array Inhalt = 2 => Falle
							System.out.println("<credits x=\"" + a*20 + "\" y=\"" + b*20 + "\"");
							break;
						case 3:																				// Array Inhalt = 3 => Leben
							System.out.println("<life x=\"" + a*20 + "\" y=\"" + b*20 + "\"");
							break;
						case 4:																				// Array Inhalt = 4 => Ziel
							System.out.println("<goal x=\"" + a + "\" y=\"" + b + "\" width=\"1\" height=\"1\"");
							break;
						case 5:																				// Array Inhalt = 5 => Rüstung
							System.out.println("<shield x=\"" + a*20 + "\" y=\"" + b*20 + "\"");
							break;
						case 6:																			// Array Inhalt = 6 => Zaubertrank
							System.out.println("<mana x=\"" + a*20 + "\" y=\"" + b*20 + "\"");
							break;
						case 7:
							System.out.println("<checkpoint x=\"" + a*20 + "\" y=\"" + b*20 + "\"");
							break;
						case 8:
							System.out.println("<spawn x=\"" + a + "\" y=\"" + b + "\"");
							break;
						case 11:																			// Array Inhalt = 11 => horizontales Monster
							System.out.println("<enemy x=\"" + a*20 + "\" y=\"" + b*20 + "\" movex=\"1\" movey=\"0\"");					
							break;	
						case 12:																			// Array Inhalt = 12 => vertikales Monster
							System.out.println("<enemy x=\"" + a*20 + "\" y=\"" + b*20 + "\" movex=\"0\" movey=\"1\"");
							break;
						case 13:																			// Array Inhalt = 13 => right-up Monster
							System.out.println("<enemy x=\"" + a*20 + "\" y=\"" + b*20 + "\" movex=\"1\" movey=\"-1\"");
							break;
						case 14:																			// Array Inhalt = 14 => left-up Monster							
							System.out.println("<enemy x=\"" + a*20 + "\" y=\"" + b*20 + "\" movex=\"-1\" movey=\"-1\"");
							break;
						case 15:																			// Array Inhalt = 15 => Bouncy
							System.out.println("<bouncy x=\"" + a*20 + "\" y=\"" + b*20 + "\" movex=\"-1\" movey=\"1\"");
							break;
						case 16:																			// Array Inhalt = 16 => Tracker
							System.out.println("<tracker x=\"" + a*20 + "\" y=\"" + b*20 + "\"");
							break;
						case 17:																				// Array Inhalt = 17 => Falle
							System.out.println("<mine x=\"" + a + "\" y=\"" + b + "\"");
							break;
					}
				}
			}*/
			
			
			if(darkness.isSelected()){
				dark=true;
			}
			
			if(multi.isSelected()){
				multiplay=true;
			}
			
			JFileChooser chooser = new JFileChooser();
			//chooser.showSaveDialog(null);
			String pfad = "maps";
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);									// Fenster zum 'Speichern unter'
			chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			chooser.setVisible(true);
			File file = new File(pfad.trim());
			FileNameExtensionFilter plainFilter = new FileNameExtensionFilter("xml", "xml");
			chooser.setFileFilter(plainFilter);
			
			rueckFileChooser=chooser.showSaveDialog(this);
			if(rueckFileChooser==JFileChooser.APPROVE_OPTION){
				pfad = chooser.getSelectedFile().toString();
				file = new File(pfad);
				if(plainFilter.accept(file)){
					// Map wird als .xml gespeichert
					Save.savegame(pfad);
				}else{
					// Datei entspricht nicht .xml
					System.out.println(pfad + " ist der falsche Datentyp");
				}
			}
			chooser.setVisible(false);

		}
		
		if (event.getSource()==öffnen){
			JFileChooser chooser = new JFileChooser();
			rueckFileChooser=chooser.showOpenDialog(null);										// Fenster zum 'Öffnen'
			if(rueckFileChooser==JFileChooser.APPROVE_OPTION){
				System.out.println("Die zu öffnende Datei ist: " + chooser.getSelectedFile().getName());
				try {
					// Öffnet die dem Pfad entsprechende Datei.
					Open.open(chooser.getSelectedFile().getPath());
				} catch (IOException | SAXException
						| ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
	

    
    
	/**
	 * Bekommt einen Bildnamen und gibt das entsprechende Bild zurück
	 * Nach dem Bild wird im game-Ordner gesucht
	 * @param path Bildname
	 * @return Bild
	 */
	public static Image getImage(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
		Image img = null;
		try {
			img = ImageIO.read(new File("src/the/game/java/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	/**
	 * Bekommt einen Bildnamen und gibt das entsprechende Bild zurück
	 * Nach dem Bild wird im levelCreator-Ordner gesucht
	 * @param path Bildname
	 * @return Bild
	 */
	public static Image getImageFromLC(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
		Image img = null;
		try {
			img = ImageIO.read(new File("src/level/creator/java/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
    
	/**
	 * Tastenabfrage
	 */
	public class KAdapter extends KeyAdapter {
		/**
		 * Taste losgelassen
		 */
        public void keyReleased(KeyEvent event) {		// Wenn Taste losgelassen wird:
        	currentpos.keyReleased(event);
        }
        /**
         * Taste gedrückt?
         */
        public void keyPressed(KeyEvent event) {		// Wenn Taste gedrückt wird:
        	currentpos.keyPressend(event);
        }
    }
	
	/**
	 * Mausabfrage
	 */
    public class MAdapter implements MouseInputListener {

    	
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		//Current current = new Current();
		@Override
		/**
		 * Maus gedrückt?
		 */
		public void mousePressed(MouseEvent e) {
			//int button = e.getButton();
			if(e.getButton() == MouseEvent.BUTTON1){
				System.out.println("Maus klick, yeah, Alter!");
				if(e.getX()-defaultx>=0 && e.getY()-defaulty>=0){
					//int restx = 
					int feldx = (e.getX()-defaultx-((e.getX()-defaultx)%20))/20;
					int feldy = (e.getY()-defaulty-((e.getY()-defaulty)%20))/20;
					
					if(feldx<0 || feldx+1>arrayLenghtX || feldy<0 || feldy+1>arrayLenghtY){
						return;
					}
					System.out.println("Du klickst in Feld ["+feldx+"]["+feldy+"].");
					currentpos.setcurrentXPixel(defaultx+1+feldx*20);
					currentpos.setcurrentYPixel(defaulty+1+feldy*20);
					repaint();
				 }
				
			}
			
		}

		@Override
		/**
		 * Maus losgelassen?
		 */
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				if(!currentList.isEmpty()){
					currentpos.setcurrentXPixel((int)currentList.get((currentList.size()-1)).getX()*20+offsetX);
					currentpos.setcurrentYPixel((int)currentList.get((currentList.size()-1)).getY()*20+offsetY);
					//repaint();
					
				}
			} else if(e.getButton() == MouseEvent.BUTTON3){
				currentList.clear();
			}
			
		}


		
		@Override
		/**
		 * Maus gedragged?
		 */
		public void mouseDragged(MouseEvent e) {
			//if(e.getButton() == MouseEvent.BUTTON1){
				System.out.println("Maus dragged, yeah, Alter!");
				if(e.getX()-defaultx>=0 && e.getY()-defaulty>=0){
					//int restx = 
					int feldx = (e.getX()-defaultx-((e.getX()-defaultx)%20))/20;
					int feldy = (e.getY()-defaulty-((e.getY()-defaulty)%20))/20;
					if(feldx<0 || feldx+1>arrayLenghtX || feldy<0 || feldy+1>arrayLenghtY){
						return;
					}
					Point po = new Point(feldx, feldy);
					if(isAlreadyExistent(po)==false) {
						//if(defaultx+1<=feldx && feldx<=48*20+defaultx+1 && defaulty+1<=feldy && feldy<=25*20+defaulty+1){
							currentList.add(po);
							System.out.println("Du klickst in Feld ["+feldx+"]["+feldy+"].");
						//}
						
					}
					//repaint();
				}
			//}
			
		}
		/**
		 * Prüft ob Punkt bereits in Liste existiert
		 * @param po Punkt der zu überprüfen ist
		 * @return Existiert der Punkt bereits in Liste?
		 */
		private boolean isAlreadyExistent(Point po) {
			if(!currentList.isEmpty()){
				for(int a=0; a<currentList.size(); a++){
					if(currentList.get(a).equals(po))
						return true;
				}
			}
			return false;
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		
    }
}
