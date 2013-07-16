package the.game.java;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Queue;

public class NetClient extends Client {

	public NetClient(String host, int port) {
		super(host, port);
	}
	
	protected void doWhileRunning() {
		
	}
	
	/**
	 * Input an handler weiterleiten
	 */
	protected void handleIncomingMessages() {
		buffer.flip(); // flip buffer for reading
		CharBuffer cb = charset.decode(buffer); // read from buffer and decode
		String input = cb.toString();
		input = input.trim();
		
		//System.out.println("NetClient: input: " + input);
		
		// Weiterleiten der Nachricht an NetHandler
		handler.handleInput(input);
	}
	
	/**
	 * Output verarbeiten
	 */
	protected void processOutcomingMessages() {
		Queue<NetMessage> q = handler.getOutputSchedule();
		
		WritableByteChannel wbc = (WritableByteChannel) channel;
		buffer.clear(); // clear buffer before writing
		
		// Alle auszugebende Elemente senden
		while(q.size()>0) {
			buffer = ByteBuffer.wrap(q.poll().getMsg().getBytes());
			try {
				wbc.write(buffer);
			} catch (IOException e) {
				System.err.println("Schreibvorgang fehlgeschlagen!");
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Client starten und mit Server verbinden
	 * @param args
	 */
	public static void main(String[] args) {
		NetClient client = new NetClient(MultiplayerWindow.getIPAdr(),8000);
		client.start();
	}

}
