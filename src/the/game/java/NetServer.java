package the.game.java;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Der NetServer ist die Grundlage für den Multiplayer
 * Die verschiedenen Clients können sich mit ihm verbinden und senden
 * ggf. Spieldaten an ihn.
 * Die Aufgabe des Servers ist es die Spieldaten sinnvoll an alle Clients zu verteilen.
 * Aufbau-Prinzip: Stern-Topologie
 */
public class NetServer {

	private InetAddress addr;
	private int port;
	private Selector selector;
	private Map<SocketChannel,List<byte[]>> dataMap;
	private List<SocketChannel> channels;
	private int connectionCount = 0;
	private int teamID = 0;
    
    /********************************************************
     * 
     * KONSTRUKTOR
     * 
    /********************************************************/

	/**
	 * KONSTRUKTOR
	 * @param addr	IP-Adresse
	 * @param port	Port
	 * @throws IOException
	 */
	public NetServer(InetAddress addr, int port) throws IOException {
		this.addr = addr;
		this.port = port;
		dataMap = new HashMap<SocketChannel,List<byte[]>>();
		channels = new ArrayList<SocketChannel>();
		startServer();
	}
    
    
    /********************************************************
     * 
     * START SERVER
     * 
    /********************************************************/

	/**
	 * SERVER STARTEN
	 * Startet Server und beinhaltet die Run-Schleife
	 * @throws IOException
	 */
	private void startServer() throws IOException {
        // erstelle selector und channel
		this.selector = Selector.open();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

        // an Port binden
		InetSocketAddress listenAddr = new InetSocketAddress(this.addr, this.port);
		serverChannel.socket().bind(listenAddr);
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

		// PrintOut
		log("Server ready.");

        // Run-Schleife
		while (true) {
            // warte auf Events
			this.selector.select();

            // aufwachen und selektierte Keys aus selector bearbeiten
			Iterator keys = this.selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				// Aktuellen Key holen
				SelectionKey key = (SelectionKey) keys.next();
				
				// key aus Iteration entfernen, damit er nicht wieder und wieder auftaucht
				keys.remove();
				
				// Wenn Key ungültig, nächsten Schleifendurchlauf starten
				if (! key.isValid()) {
					continue;
				}
				
				// Neue Verbindung?
				if (key.isAcceptable()) {
					this.accept(key);
				}
				// Input zu lesen?
				else if (key.isReadable()) {
					this.read(key);
				}
				// Output zu schreiben?
				else if (key.isWritable()) {
					this.write(key);
				}
			}
		}
	}
    
    
    /********************************************************
     * 
     * ACCEPT KEY
     * 
    /********************************************************/
	
	/**
	 * NEUE VERBINDUNG AKZEPTIEREN
	 * @param key		Key, für die neue Verbindung
	 * @throws IOException
	 */
	private void accept(SelectionKey key) throws IOException {
		// Channel holen
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		
		// ID und TeamID vergeben
		channel.write(ByteBuffer.wrap(new NetMessage("_id " + String.format("%02d", connectionCount) + " " + String.format("%02d", teamID)).getMsg().getBytes()));
		
		// TeamID für nächste Vergabe vorbereiten
		teamID++;
        if(teamID>1)
        	teamID = 0 ;
        
        // Socket erstellen
		Socket socket = channel.socket();
		// Adresse des Clienten abfragen und printen
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		log("Connected to: " + remoteAddr);
		
        // Channel als Key in dataMap angeben
		dataMap.put(channel, new ArrayList<byte[]>());
		// Channel in Channellist hinzufügen
		channels.add(channel);
		// Channel für selector mit Read-Operation registrieren
		channel.register(this.selector, SelectionKey.OP_READ);
        
        // Alle Clienten über Neueintritt informieren
		route(new NetMessage(true, connectionCount).getMsg().getBytes());
		connectionCount++;
	}
    
    
    /********************************************************
     * 
     * READ KEY
     * 
    /********************************************************/

	/**
	 * INPUT LESEN
	 * @param key
	 * @throws IOException
	 */
	private void read(SelectionKey key) throws IOException {
		// Channel holen
		SocketChannel channel = (SocketChannel) key.channel();
		
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		int numRead = -1;
		try {
			// input auslesen und in buffer schreiben
			numRead = channel.read(buffer);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Wenn Verbindung verloren:
		if (numRead == -1) {
			// Channel aus ChannelList entfernen
			for(int a=0; a<channels.size(); a++) {
				if(channel.equals(channels.get(a))==false) {
					channels.remove(a);
					break;
				}
			}
			// Channel aus dataMap entfernen
			this.dataMap.remove(channel);
			
			// Socket holen und log printen
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			log("Connection closed by client: " + remoteAddr);
			// Channel schließen
			channel.close();
			// Key schließen
			key.cancel();
			return;	// Methode verlassen
		}
		
		// Wenn Auslesen erfolgreich: buffer in byteArray kopieren
		byte[] data = new byte[numRead];
		System.arraycopy(buffer.array(), 0, data, 0, numRead);
		log("Got: " + new String(data, "US-ASCII"));
		
        // Nachricht weiterleiten an alle anderen Clients
		route(key, data);
    }
    
    
    /********************************************************
     * 
     * WRITE KEY
     * 
    /********************************************************/
	
	/**
	 * OUTPUT SCHREIBEN
	 * @param key	SelectionKey
	 * @throws IOException
	 */
	private void write(SelectionKey key) throws IOException {
    	/** Channel und Daten holen */
		SocketChannel channel = (SocketChannel) key.channel();	// Aktuellen channel ansprechen
		List<byte[]> pendingData = this.dataMap.get(channel);	// DatenList für channel ansprechen
		Iterator<byte[]> items = pendingData.iterator();		// Iterator für DatenList erzeugen
        
        /** Alle Nachrichten senden */
		while (items.hasNext()) {					// Daten der DatenList Stück für Stück durchgehen
			byte[] item = items.next();				// Nächste Botschaft abfragen
			items.remove();							// Botschaft aus Liste entfernen
			channel.write(ByteBuffer.wrap(item));	// Botschaft absenden
		}
		key.interestOps(SelectionKey.OP_READ);		// Schreiboperation für diesen Key anmelden
	}
    
    
    /********************************************************
     * 
     * ROUTING MESSAGES
     * 
    /********************************************************/
	
	/**
	 * Ausstehende Daten für alle Clients verteilen, außer Absender der Daten
	 * Schreiboperation wird angemeldet und bei WRITE KEY abgesendet
	 * @param key	SelectionKey des Absenders
	 * @param data	ByteArray für Daten
	 */
	private void route(SelectionKey key, byte[] data) {
    	// Alle channels durchgehen
		for(int a=0; a<channels.size(); a++) {
    		// Aktuellen channel ansprechen
			SocketChannel channel = channels.get(a);
    		// Wenn aktueller channel nicht der Absender ist:
			if(channel.equals(key.channel())==false) {
    			// DatenList ansprechen
				List<byte[]> pendingData = this.dataMap.get(channel);
    			// DatenList ergänzen
				pendingData.add(data);
    			// Schreiboperation anmelden (sucht key in selector)
				channel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
			}
		}
	}
	/**
	 * Ausstehende Daten für alle Clients verteilen
	 * Schreiboperation wird angemeldet und bei WRITE KEY abgesendet
	 * @param data	ByteArray für Daten
	 */
	private void route(byte[] data) {
    	// Alle channels durchgehen
		for(int a=0; a<channels.size(); a++) {
    		// Aktuellen channel ansprechen
			SocketChannel channel = channels.get(a);
    		// DatenList ansprechen
			List<byte[]> pendingData = this.dataMap.get(channel);
			// DatenList ergänzen
			pendingData.add(data);
			// Schreiboperation anmelden (sucht key in selector)
			channel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
		}
	}
    
    
    /********************************************************
     * 
     * MISC
     * 
    /********************************************************/

	private static void log(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) throws Exception {
		new NetServer(null, 8000);	//8989
	} 
}
