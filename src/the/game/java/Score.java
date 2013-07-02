package the.game.java;

	/**
	 * 
	 *  In dieser Klasse kann der Punktestand eines Spielers abgefragt und ver�ndert werden.
	 *  Der kleinst-m�gliche Punktestand betr�gt 0.
	 *
	 */

public class Score {

	private int scr;  //Score des Spielers
	
	public Score() {
		scr = 10000;
	}
	
	/*public static List<Score> scoreList = new ArrayList<Score>();
	
	public static void createScore() {			// f�r jeden Spieler einen
		scoreList.add(new Score());
	}*/
	
	public int getScore(){						// Gibt den aktuellen score zur�ck
		return scr;
	}
	
	public void setScore(int wert){				// Liest den Wert ein um den der score ver�ndert werden soll und rechnet dementsprechend
		if(scr+wert>0){
			scr=scr+wert;
		}else{
			scr=0;								// nur 0 oder positive Punktest�nde m�glich
		}
	}
	
	public void setAbsoluteScore(int newScore) {
		scr = newScore;
	}
}
