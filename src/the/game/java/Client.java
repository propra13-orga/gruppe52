package the.game.java;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.LinkedList;
import java.util.Queue;

public class Client implements Runnable {
	
	private Thread thread; // Clientthread
	
	// Server adresse und Port
	private String host;
	private int port;
	
	// SocketChannel für Client (Verbindung zum Server)
	protected SocketChannel channel = null;
	
	// In-&OutputHandler für Client
	protected NetHandler handler = null;
	
	// Buffer zum lesen und schreiben
	protected ByteBuffer buffer = ByteBuffer.allocate(1000);
	Queue<String> queue = new LinkedList<String>();
	
	// Charset and encoder for US-ASCII //TODO: 
	protected static Charset charset = Charset.forName("US-ASCII");
	private static CharsetEncoder encoder = charset.newEncoder();
	
	/**
	 * KONSTRUKTOR
	 * @param host	Host-Adresse
	 * @param port	Port
	 */
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		handler = new NetHandler();
		init();
	}
	
	/**
	 * Initialisieren
	 * Verbinet Client mit angegebenem Host und Port
	 */
	private void init() {
		try {
			
			// Erstellt Adressenobjekt für angegebene Adresse/Port
			InetSocketAddress isa =
			new InetSocketAddress(InetAddress.getByName(host), port);
			
			channel = SocketChannel.open(); // öffnet channel
			channel.configureBlocking(false); // Channel blockt nicht
			channel.connect(isa); // verbindet zum channel mit angegebener adresse
			
			// setzt 'timeout' von 5 sekunden und wartet darauf zu connecten
			long timeout = System.currentTimeMillis() + 5000;
			while(!channel.finishConnect()) {
				threadSleep(250);
				if (System.currentTimeMillis() > timeout) {
					throw new Exception("connection timout!");
				}
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
			cleanup();
		}
	}
	
	/**
	 * Gibt die Hostadresse zurück
	 * @return
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Gibt den Port zurück
	 * @return
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Starte den Client (wenn nicht schon gestartet)
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	/**
	 * Stoppe Client
	 */
	public void stop() {
		thread = null;
	}
	
	/**
	 * Run Loop für Client
	 */
	public void run() {
		try {
			// solange nicht stop() aufgerufen
			while (thread == Thread.currentThread()) {
				
				// Input verarbeiten
				processIncomingMessages();
				// Output verarbeiten
				processOutcomingMessages();
				// Weiterführende Methode
				doWhileRunning();
				
				// kurze Pause um anderen Threads etwas Zeit zu geben
				threadSleep(50L);
			}
		} catch (Exception ex) {
			System.out.println("error occured! connection terminated!");
			ex.printStackTrace();
		} finally {
			cleanup();
		}
	}
	
	/**
	 * Output verarbeiten
	 */
	protected void processOutcomingMessages() {
		
	}
	
	/**
	 * Weiterführende Methode zum Überschreiben
	 * Wird in der Runner Schleife aufgerufen
	 */
	protected void doWhileRunning() {
		
	}
	
	/**
	 * Channel schließen, wenn noch nicht erledigt
	 */
	private void cleanup() {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception ex) {
					
			}
		}
		System.exit(0);
	}
	
	/**
	 * Input verarbeiten
	 * @throws IOException
	 */
	private void processIncomingMessages() throws IOException {
		
		ReadableByteChannel rbc = (ReadableByteChannel) channel;
		buffer.clear(); // buffer leeren
		
		// input auslesen und in buffer schreiben
		int numBytesRead = rbc.read(buffer);
		
		// Wenn Verbindung verloren:
		if (numBytesRead < 0) {
			System.out.println("connection terminated");
			cleanup();
			return;
		} 
		// Wenn Verbindung weiterhin bestehend:
		else if (numBytesRead > 0) {	// wenn es input gibt:
			try {
				
				handleIncomingMessages();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Input interpretieren
	 * Zum Überschreiben!
	 */
	protected void handleIncomingMessages() {
		
	}
	
	/**
	 * Thread schlafen legen
	 * @param time
	 */
	private void threadSleep(long time) {
		try { Thread.sleep(time); }
		catch (InterruptedException e) {}
	}
}