package the.game.java;

public class NetMessage {
	
	protected static int LENGTH = 54;
	
	private String msg = new String();
	private int counter = 0;
	private boolean valid = true;
	
	public NetMessage(String text) {
		msg = fillSpace(text);
	}
	
	public NetMessage(int netID, String text) {
		msg = 	"_i " 		+ String.format("%02d", netID) + 
				" _t "		+ text;
		msg = fillSpace(msg);
	}
	
	public NetMessage(int netID, String obj, int remoteID, int x, int y) {
		msg = 	"_i " 		+ String.format("%02d", netID) + 
				" _t pos " 	+ 
				"_n " 		+ obj + " " + String.format("%02d", remoteID) +
				" _d " 		+ String.format("%04d", x) + 
				" " 		+ String.format("%04d", y);
		msg = fillSpace(msg);
	}
	
	public NetMessage(int netID, String obj, int remoteID, int x, int y, int angle, boolean alive) {
		int al = 0;
		if(alive)
			al = 1;
		
		msg = 	"_i " 		+ String.format("%02d", netID) + 
				" _t pos " 	+ 
				"_n " 		+ obj + " " + String.format("%02d", remoteID) +
				" _d " 		+ String.format("%04d", x) + 
				" " 		+ String.format("%04d", y) +
				" " 		+ String.format("%01d", al) +
				" _a " 		+ String.format("%04d", angle);
		msg = fillSpace(msg);
	}
	
	public NetMessage(int netID, String obj, int remoteID, int x, int y, int mx, int my) {
		msg = 	"_i " 		+ String.format("%02d", netID) + 
				" _t pos " 	+ 
				"_n " 		+ obj + " " + String.format("%02d", remoteID) +
				" _d " 		+ String.format("%04d", x) + 
				" " 		+ String.format("%04d", y) + 
				" " 		+ String.format("%04d", mx) + 
				" " 		+ String.format("%04d", my);
		msg = fillSpace(msg);
	}
	
	public NetMessage(boolean forNEW, int netID) {
		msg = 	"_new " 	+ String.format("%02d", netID);
		for(int a=1; a<=netID; a++) {
			msg += " " + String.format("%02d", netID-a);
		}
		msg = fillSpace(msg);
	}
	
	/**
	 * PROJECTILES
	 */
	public NetMessage(int netID, String obj, int remoteID, int x, int y, 
			int speed, int vigor, int spread, double angle) {
		
		msg = 	"_i " 		+ String.format("%02d", netID) + 
				" _t pro " 	+ 
				"_n " 		+ obj + " " + String.format("%02d", remoteID) +
				" _d " 		+ String.format("%04d", x) + 
				" " 		+ String.format("%04d", y) + 
				" " 		+ String.format("%02d", speed) + 
				" " 		+ String.format("%02d", vigor) +
				" " 		+ String.format("%02d", spread) +
				" " 		+ String.format("%02d", (int)angle);
		msg = fillSpace(msg);
	}
	
	
	
	public NetMessage(int netID, String type, String obj, int remoteID, int x, int y) {
		msg = 	"_i " 		+ String.format("%02d", netID) + 
				" _t " 		+ type +
				" _n " 		+ obj + " " + String.format("%02d", remoteID) +
				" _d " 		+ String.format("%04d", x) + 
				" " 		+ String.format("%04d", y);
		msg = fillSpace(msg);
	}
	
	public static NetMessage getHitReport(int netID, String obj, int remoteID, int val1, int val2) {
		return new NetMessage(netID, "hit", obj, remoteID, val1, val2);
	}
	
	public static NetMessage getDeathReport(int netID, String obj, int remoteID, int val1, int val2) {
		return new NetMessage(netID, "hit", obj, remoteID, val1, val2);
	}
	
	private static String fillSpace(String s) {
		if(s.length()<LENGTH-1)
			s += " ";
			
		while(s.length()<LENGTH) {
			s += "-";
		}
		return s;
	}
	
	public String getMsg() {
		counter++;
		return msg;
	}
	
	public boolean isValid(int connections) {
		if(counter>=connections)
			valid = false;
		return valid;
	}
}
/**
 * "_i " + String.format("%02d", netID) + 
		" _t pos " + 
		"_n " + obj + 
		" _x " + String.format("%04d", x) + 
		" _y " + String.format("%04d", y);
 */