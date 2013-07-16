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
	/**
	 * Gibt den aktuellen score zur�ck
	 * @return score
	 */
	public int getScore(){						// Gibt den aktuellen score zur�ck
		return scr;
	}
	
	/**
	 * Liest den Wert ein um den der score ver�ndert werden soll und rechnet dementsprechend
	 * @param wert Wert um den der Score ver�ndert werden soll
	 */
	public void setScore(int wert){				// Liest den Wert ein um den der score ver�ndert werden soll und rechnet dementsprechend
		if(scr+wert>0){
			scr=scr+wert;
		}else{
			scr=0;								// nur 0 oder positive Punktest�nde m�glich
		}
	}
	
	/**
	 * Liest den Wert ein, den der Score erhalten soll und ersetzt dementsprechend
	 * @param newScore neuer Scorestand
	 */
	public void setAbsoluteScore(int newScore) {
		scr = newScore;
	}
}
