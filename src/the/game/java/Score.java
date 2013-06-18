package the.game.java;

import java.util.ArrayList;
import java.util.List;

	/**
	 * 
	 *  In dieser Klasse kann der Punktestand eines Spielers abgefragt und verändert werden.
	 *  Der kleinst-mögliche Punktestand beträgt 0.
	 *
	 */

public class Score {

	public int scr;  //Score des Spielers
	
	private Score() {
		scr = 100;
	}
	
	public static List<Score> scoreList = new ArrayList<Score>();
	
	public static void createScore() {			// für jeden Spieler einen
		scoreList.add(new Score());
	}
	
	public int getScore(){						// Gibt den aktuellen score zurück
		return scr;
	}
	
	public void setScore(int wert){				// Liest den Wert ein um den der score verändert werden soll und rechnet dementsprechend
		if(scr+wert>0){
			scr=scr+wert;
		}else{
			scr=0;								// nur 0 oder positive Punktestände möglich
		}
	}
	
	public void setAbsoluteScore(int newScore) {
		scr = newScore;
	}
	
	/*public static void main(String[] args){
		Score test= new Score();
		test.setScore(200);
		test.setScore(700);
		test.setScore(-800);
		test.setScore(-200);
		System.out.println(test.getStore());
	}*/
}
