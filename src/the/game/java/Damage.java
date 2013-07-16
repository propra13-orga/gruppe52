package the.game.java;

/**
 * 
 * Schadensystem: Schere-Stein-Papier-Prinzip (erweitert)
 * 
 * Diese Klasse ermittelt einen Schadenswert durch Vergleichen
 * von Schadenstyp von Angreifer und Opfer.
 * 
 * ÜBERSICHT:
 * _________________________________________________
 * |-ANGREIFER-|---------------GEGNER---------------|
 * |		   | GEWINNT		VERLIERT	NEUTRAL	|
 * |TYP  0:	   | 0,1,2,3		0,1,2,3		-		|
 * |TYP  1:	   | 0,2			0,3			1		|
 * |TYP  2:	   | 0,3			0,1			2		|
 * |TYP  3:	   | 0,1			0,2			3		|
 * |___________|____________________________________|
 * 
 * BELEGUNG:
 * Spieler (default):	0
 * Enemy Simple:		1
 * Enemy Bouncy:		2
 * Minen:				2
 * Enemy Tracker:		3
 * 
 */

public class Damage {
	
	public final static int DT_1 = 1;
	public final static int DT_2 = 2;
	public final static int DT_3 = 3;
	
	/********************************************************
     * 
     * HAUPTTEIL
     * 
    /********************************************************/
	
	/**
	 * SCHADEN ABFRAGEN
	 * @param damage			Schaden (absolut)
	 * @param offenderType		AngreiferTyp
	 * @param victimType		OpferTyp
	 * @return					Schaden * Faktor (Faktor abhängig von Relation der beiden Typen)
	 */
	public static int getDamage(int damage, int offenderType, int victimType) {
		if(offenderType <= 0 || victimType <= 0)	// Wenn Angreifertyp <0 ODER Opfertyp == 0 dann Schaden auf alles 
			return damage;
		
		if(offenderType == victimType) {	// GLEICH
			return 0;
		}
		else {								// UNGLEICH
			return doOffender(offenderType, victimType) * damage;
		}
	}
	
	/**
	 * ERMITTELT DEN TYP DES ANGREIFERS UND LEITET WEITER
	 * @param offenderType		AngreiferTyp
	 * @param victimType		OpferTyp
	 * @return					Damage-Faktor
	 */
	public static int doOffender(int offenderType, int victimType) {
		switch(offenderType) {
		case DT_1:
			return doOffender1(victimType);
		case DT_2:
			return doOffender2(victimType);
		case DT_3:
			return doOffender3(victimType);
		default:
			return 0;
		}
	}
	
	
	/********************************************************
     * 
     * EINZELVERHÄLTNISSE
     * 
    /********************************************************/
	
	/** Typ 1 */
	public static int doOffender1(int victimType) {
		switch(victimType) {
		case DT_2:
			return 1;
		case DT_3:
			return 0;
		default:
			return 0;
		}
	}
	
	/** Typ 2 */
	public static int doOffender2(int victimType) {
		switch(victimType) {
		case DT_1:
			return 0;
		case DT_3:
			return 1;
		default:
			return 0;
		}
	}
	
	/** Typ 3 */
	public static int doOffender3(int victimType) {
		switch(victimType) {
		case DT_1:
			return 1;
		case DT_2:
			return 0;
		default:
			return 0;
		}
	}
}
