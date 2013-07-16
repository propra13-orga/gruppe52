package the.game.java;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *  SHOP
 *  Aufzurufen über: 	new Shop();
 *  Schließen über:		ESCAPE
 *  Funktion:			Zeigt einen Shop, der Items zusammen mit einigen Infos auflistet
 *  					Diese Items kann der Spieler(1) kaufen. Er bezahlt mit Punkten.
 */
public class Shop {
	
	
	public static boolean show = false;
	public static int[][] shopMap;
	private static int squareLength = 42;
	private static int iconFieldWidth = 62;
	private static int iconFieldHeight = 42;
	private static int shopWidth = 560;
	private static int shopHeight = 320;
	private static int fixPointX;
	private static int fixPointY;
	private static int currentCat=0;
	
	private static int itemAreaFixPointX = 0;
	private static int itemAreaFixPointY = 1;
	private static int itemAreaWidth = 6;
	private static int itemAreaHeight = 6;
	
	private static String imageTagShop = "shop";
	private static String imageTagIcons = "";
	private static String imageTagInfos = "";
	private static String imageTagIconSelected = "shopIconSelected";
	private static String imageTagIconHover = "shopIconHover";
	private static String imageTagItemAttachment = "shopIconAttachment";
	private static String imageTagCredits = "shopCredits";
	private static String imageTagButtonBuyHover = "shopBuyHover";
	private static String imageTagButtonCatMenuHover = "shoptCatMenuHover";
	
	private static int borderLe;
	private static int borderRi;
	private static int borderUp;
	private static int borderDo;
	
	private static int buttonBuyBorderLe;
	private static int buttonBuyBorderUp;
	private static int buttonBuyBorderRi;
	private static int buttonBuyBorderDo;
	
	private static int itemCount;
	private static int currentCredits;
	private static int currentSelection = -1;
	
	private static boolean flagHoverButtonBuy = false;
	private static boolean flagHoverButtonCatMenu = false;
	
	private static ImageIcon ii;
	private static Image buttonBuy;
	private static Image buttonBuyHover;
	private static Image buttonBuyHoverNV;
	//private static Image buttonBuySelected;
	
	/**
	 * Konstruktor der Klasse Shop.
	 * Stellt das Shopfenster dar.
	 */
	public Shop() {
		if(show==false) {
			shopMap = new int[getShopDimensions(0)][getShopDimensions(1)];
			setVariable();
			show = true;
			setShopDisplay();
		}
	}
		
	/**
	 * Ermittelt Arraygröße
	 * @param axis <=0 für Breite, >0 für Höhe
	 * @return die Arraygröße
	 */
	private static int getShopDimensions(int axis) {	// Ermittelt Arraygröße
		int value;
		if(axis<=0)
			value = shopWidth;
		else
			value = shopHeight;
		value = (value - (value % squareLength)) / squareLength;
		if(value % squareLength>0)
			value++;
		return value;
	}
	/**
	 * Stellt (Teil 1) der Shopanzeige dar
	 */
	private void setVariable() {
		fixPointX = (Runner.getWidthF() / 2) - (shopWidth / 2);
		fixPointY = (Runner.getHeightF() / 2) - (shopHeight / 2);
		
		borderLe = fixPointX + (itemAreaFixPointX * squareLength);
		borderRi = borderLe + (itemAreaWidth * iconFieldWidth);
		borderUp = fixPointY + (itemAreaFixPointY * squareLength);
		borderDo = borderUp + (itemAreaHeight * iconFieldHeight);
		
		buttonBuy = DisplayManager.getImage("shop/buttonBuy.png");
		buttonBuyHover = DisplayManager.getImage("shop/buttonBuyHover.png");
		buttonBuyHoverNV = DisplayManager.getImage("shop/buttonBuyHoverNotValid.png");
		//buttonBuySelected = getImage("shop/buttonBuySelected.png");
		
		buttonBuyBorderLe = borderRi;
		buttonBuyBorderUp = borderDo-iconFieldHeight;
		buttonBuyBorderRi = buttonBuyBorderLe + buttonBuy.getWidth(null);
		buttonBuyBorderDo = buttonBuyBorderUp + buttonBuy.getHeight(null);
	}
	
	/**
	 * Stellt (Teil 2) der Shopanzeige dar
	 */
	private static void setShopDisplay() {
		if(show) {
			DisplayManager.displayImage("transparentLayer.png", 0, 0, imageTagShop, true);
			DisplayManager.displayImage("shop/shopBackground.png", fixPointX-5, fixPointY-5, imageTagShop, true);
			DisplayManager.displayImage("shop/titel.png", fixPointX-5, fixPointY-5, imageTagShop, true);
			DisplayManager.displayImage("shop/catMenu.png", fixPointX, fixPointY-5, imageTagShop, true);
			DisplayManager.displayImage(buttonBuy, buttonBuyBorderLe, buttonBuyBorderUp, imageTagShop, true);

	        for(int a=0; a < getShopDimensions(0); a++) {	// Spaltenweise
				for(int b=0; b<getShopDimensions(1); b++) {		// Zeilenweise
					if(b>=itemAreaFixPointY && a>= itemAreaFixPointX && a<itemAreaFixPointX+itemAreaWidth && b<itemAreaFixPointY+itemAreaHeight)
						DisplayManager.displayImage("shop/iconFieldBg.png", fixPointX+a*iconFieldWidth, fixPointY+b*iconFieldHeight, imageTagShop, true);
				}
			}
	        displayCredits();
	        chooseItemCat(1);
		}
	}
	
	/**     
	 * Sorgt dafür, dass Items ausgewählt werden können   
	 */
	private static void selectItem(int fieldNo) {
		
			if(fieldNo<itemCount)	// Wenn gewähltes Feld ein Item enthält:
				switch(currentCat) {
				case 1:
					selectItem_Weapon(fieldNo);	// auswählen
					break;
				case 2:
					selectItem_Ammo(fieldNo);
				}
				currentSelection = fieldNo;
			
		
	}
	/**
	 * Waffe in Feld nr (parameter) wird ausgewählt
	 * @param fieldNo Feldnummer
	 */
	private static void selectItem_Weapon(int fieldNo) {
		displaySelectionFrame(fieldNo, false);
		displaySelectedItemInfo_Weapon(fieldNo);
	}
	/**
	 * Munition in FeldNr (parameter) wird ausgewählt
	 * @param fieldNo Feldnummer
	 */
	private static void selectItem_Ammo(int fieldNo) {
		displaySelectionFrame(fieldNo, false);
		displaySelectedItemInfo_Ammo(fieldNo);
	}
	/**
	 * Stellt das Auswahlfenster dar
	 * @param fieldNo Feldnummer
	 * @param isHover Schwebt die Maus über Bild?
	 */
	private static void displaySelectionFrame(int fieldNo, boolean isHover) {
		if(show) {
			
			int POSX = (fieldNo % itemAreaWidth);
			int POSY = ((fieldNo - POSX) / itemAreaWidth);
			POSX += borderLe + (POSX * (iconFieldWidth-1));
			POSY += borderUp + (POSY * (iconFieldHeight-1));;
			
			if(isHover) {
				DisplayManager.removeChangeableImages(imageTagIconHover);
				DisplayManager.displayImage("shop/iconFieldHover.png", POSX, POSY, imageTagIconHover, true);
			} else {
				DisplayManager.removeChangeableImages(imageTagIconSelected);
				DisplayManager.displayImage("shop/iconFieldSelected.png", POSX, POSY, imageTagIconSelected, true);
			}
		}
	}
	//TODO: Kommentar
	private static void setDisplayItemAttachment() {
		boolean already = false;
		
		for(int itemNo=0; itemNo<itemCount; itemNo++) {
			already = isWeaponAlreadyInUse(itemNo);			// Überprüft, ob Item schon im Besitz des Spielers ist
			
			switch(currentCat) {
			case 1:
				if(already)	{									// wenn ja, icon setzen
					displayAtIcon(itemNo, "shop/iconFieldAlreadyBuyed.png", imageTagItemAttachment, true);
				} else {							// wenn noch nichts zutraf:
					if(isEnoughMoney(itemNo)==false)			// Überprüfen ob genug Geld vorhanden, wenn nicht, icon setzen
						displayAtIcon(itemNo, "shop/iconFieldTooLessMoney.png", imageTagItemAttachment, true);
				}
				break;
			case 2:
				if(already)	{									// wenn ja, icon setzen
					if(isEnoughMoney(itemNo)==false)			// Überprüfen ob genug Geld vorhanden, wenn nicht, icon setzen
						displayAtIcon(itemNo, "shop/iconFieldTooLessMoney.png", imageTagItemAttachment, true);
				} else {							// wenn noch nichts zutraf:
					displayAtIcon(itemNo, "shop/iconFieldLocked.png", imageTagItemAttachment, true);
				}
				break;
			}
			
			
			already = false;
		}
	}
	/**
	 * Gibt an, ob sich die Waffe bereits in Benutzung befindet
	 * @param itemNo Nr des Items
	 * @return befindet sich die Waffe bereits in benutzung?
	 */
	private static boolean isWeaponAlreadyInUse(int itemNo) {
		return Player.playerList.get(0).isInUse(itemNo+1);
	}
	/**
	 * Gibt an ob man genug Credits für ein Item hat
	 * @param itemNo Nr des Items das gekauft werden soll
	 * @return kann die Waffe finanziell gesehen gekauft werden?
	 */
	private static boolean isEnoughMoney(int itemNo) {
		boolean isEnough = false;
		if(getPrice(itemNo)<=currentCredits) {		// Überprüfen ob genug Geld vorhanden
			isEnough = true;
		}
		return isEnough;
	}
	/**
	 * gibt den Preis eines bestimmten Items zurück 
	 * wenn keine Kategorie vorhanden oder Item nicht vorhanden oder Price nicht vorhanden, dann Rückgabe = 0
	 * @param itemNo Nr des Items
	 * @return Preis des Items
	 */
	private static int getPrice(int itemNo) {	// gibt den Preis eines bestimmten Items zurück, wenn keine Kategorie vorhanden oder Item nicht vorhanden oder Price nicht vorhanden, dann Rückgabe = 0
		int price = 0;
		switch(currentCat) {
		case 1:
			if(itemNo<itemCount)	// Wenn gewähltes Feld ein Item enthält:
				price = Weapon.weaponList.get(itemNo+1).price;
			break;
		case 2:
			if(itemNo<itemCount)	// Wenn gewähltes Feld ein Item enthält:
				price = Weapon.weaponList.get(itemNo+1).magPackPrice;
			break;
		}
		return price;
	}
	//TODO: Kommentar
	private static void displayAtIcon(int fieldNo, String imgPath, String imageTag, boolean isMoreThanOne) {	// zeigt Bild für angegebenes IconFeld dar. isMoreThanOne=true, können mehrere dargestellt werden, wenn isMoreThanOne=false, werden jedesmal alle vorherigen mit dem angegebenen Tag gelöscht
		if(show) {
	
			int POSX = (fieldNo % itemAreaWidth);
			int POSY = ((fieldNo - POSX) / itemAreaWidth);
			POSX += borderLe + (POSX * (iconFieldWidth-1));
			POSY += borderUp + (POSY * (iconFieldHeight-1));;
			
			if(isMoreThanOne==false)
				DisplayManager.removeChangeableImages(imageTag);
			DisplayManager.displayImage(imgPath, POSX, POSY, imageTag, true);
		}
	}
	/**
	 * Stellt alle Informationen zu der ausgewählten Waffe dar
	 * @param fieldNo Feldnummer
	 */
	private static void displaySelectedItemInfo_Weapon(int fieldNo) {
		// fieldNo sollte bei 0 beginnen
		
		String[] titel = new String[10];
		titel[0] = Weapon.weaponList.get(fieldNo+1).name;
		titel[1] = "Magazingröße";
		titel[2] = "Schaden";
		titel[3] = "Mündungsgeschw.";
		titel[4] = "Feuerrate";
		titel[5] = "Nachladezeit";
		titel[6] = "Preis";
		
		String[] info = new String[10];
		info[1] = String.valueOf(Weapon.weaponList.get(fieldNo+1).magSize);
		info[2] = String.valueOf(Weapon.weaponList.get(fieldNo+1).damage);
		info[3] = String.valueOf(Weapon.weaponList.get(fieldNo+1).bulletSpeed);
		info[4] = String.valueOf(Weapon.weaponList.get(fieldNo+1).fireRate) + "/s";
		info[5] = String.valueOf(Weapon.weaponList.get(fieldNo+1).reloadTime) + "ms";
		info[6] = String.valueOf(Weapon.weaponList.get(fieldNo+1).price) + " C";
		
		displaySelectedItemInfo(titel, info);
	}
	/**
	 * Stellt alle Informationen zu der ausgewählten Munition dar
	 * @param fieldNo Feldnummer
	 */
	private static void displaySelectedItemInfo_Ammo(int fieldNo) {
		// fieldNo sollte bei 0 beginnen
		
		String[] titel = new String[10];
		titel[0] = "AmmoPack: " + Weapon.weaponList.get(fieldNo+1).name;
		titel[1] = "Größe";
		titel[2] = "Preis";
		
		String[] info = new String[10];
		info[1] = String.valueOf(Weapon.weaponList.get(fieldNo+1).magPackSize) + " Mags";
		info[2] = String.valueOf(Weapon.weaponList.get(fieldNo+1).magPackPrice) + " C";
		
		displaySelectedItemInfo(titel, info);
	}
	//TODO: Kommentar
	private static void displaySelectedItemInfo(String[] titel, String[] info) {
		int infoAreaFixPointX = fixPointX + ((itemAreaFixPointX + itemAreaWidth) * iconFieldWidth);
		int infoAreaFixPointY = fixPointY + (itemAreaFixPointY * iconFieldHeight) + 10;
		int distance = 130;
		imageTagInfos = "shopInfo";
		
		DisplayManager.removeChangeableStrings(imageTagInfos);
		
		DisplayManager.displayString(titel[0], infoAreaFixPointX, infoAreaFixPointY, imageTagInfos);	// ITEMNAME
		for(int a=1; a<titel.length; a++) {
			if(titel[a]!=null && info[a]!=null) {
				DisplayManager.displayString(titel[a], infoAreaFixPointX, infoAreaFixPointY + (a)*20, imageTagInfos);
				DisplayManager.displayString(info[a], infoAreaFixPointX + distance, infoAreaFixPointY + (a)*20, imageTagInfos);
			}
		}
	}
	/**
	 * Gibt die Nr des Feldes an, dass getroffen wird
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 * @return Rückgabewert <0 wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert = feldnummer
	 */
	private static int isInItemSquare(int posx, int posy) {	// Rückgabewert <0 wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert = feldnummer
		int hitFieldNo = -1;

		if(posx>borderLe && posx<borderRi && posy>borderUp && posy<borderDo) {
			posx -= borderLe;										// Rand links  abziehen
			posy -= borderUp;									// Rand rechts abziehen
			posx = (posx - (posx % iconFieldWidth)) / iconFieldWidth;	// Spalte ermitteln
			posy = (posy - (posy % iconFieldHeight)) / iconFieldHeight;	// Zeile  ermitteln
			hitFieldNo = posy * itemAreaWidth + posx;				// ermitteln der Feldnummer
		}
		//System.out.println(hitFieldNo);
		//System.out.println(borderLe + "  " + borderRi + "  " + borderUp + "  " + borderDo + "  |  " + asd + "  " + asdf + "  |  " + hitFieldNo);
		return hitFieldNo;
	}
	/**
	 * Gibt an, ob der KaufButton gedrückt wird
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 * @return false wenn der KaufButton bereich nicht getroffen; wenn Feld getroffen, dann Rückgabewert true
	 */
	private static boolean isInButtonBuy(int posx, int posy) {	// Rückgabewert false wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert true
		boolean hit = false;

		if(posx>buttonBuyBorderLe && posx<buttonBuyBorderRi && posy>buttonBuyBorderUp && posy<buttonBuyBorderDo)
			hit = true;
		
		return hit;
	}
	/**
	 * Gibt an, ob der obere Button zum Wechsel zwischen Waffe und Munition gedrückt wurde
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 * @return false wenn der WEchselbuttonbereich nicht getroffen; wenn Feld getroffen, dann Rückgabewert true
	 */
	private static boolean isInUpperButtonCatMenu(int posx, int posy) {	// Rückgabewert false wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert true
		boolean hit = false;

		if(posx>(borderRi-28) && posx<(borderRi-2) && posy>(fixPointY-5) && posy<(fixPointY+14))
			hit = true;
		return hit;
	}
	/**
	 * Gibt an, ob der untere Button zum Wechsel zwischen Waffe und Munition gedrückt wurde
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 * @return false wenn der WEchselbuttonbereich nicht getroffen; wenn Feld getroffen, dann Rückgabewert true
	 */
	private static boolean isInLowerButtonCatMenu(int posx, int posy) {	// Rückgabewert false wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert true
		boolean hit = false;

		if(posx>(borderRi-28) && posx<(borderRi-2) && posy>(fixPointY+16) && posy<(fixPointY+35))
			hit = true;
		return hit;
	}
	/**
	 * Verarbeitet den Mouseklick
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 */
	public static void receiveMouseKlick(int posx, int posy) {
		{	// Icon Selection
			int fieldNo = isInItemSquare(posx, posy);
			if(fieldNo>=0)
				selectItem(fieldNo);
		}
		{	// Button Selection
			boolean hit = isInButtonBuy(posx, posy);
			if(hit) {
				buyItem();
			}
		}
		{	// Category Menu
			if(isInUpperButtonCatMenu(posx, posy)) {
				setCatPrev();
			} else if(isInLowerButtonCatMenu(posx, posy)) {
				setCatNext();
			}
		}
	}
	/**
	 * Verarbeitet die Mausbewegung
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 */
	public static void receiveMouseMovement(int posx, int posy) {
		//boolean already = false;	// um weitere überprüfungen überflüssig zu machen
		
		// Icon Hover
		//already = 
		setItemHover(posx, posy);				// setzt Hover, sofern Bereich richtig ist. gibt true aus wenn erfolgreich, false wenn Bereich falsch
		
		// Button Hover (keine überprüfung von already um löschen des Hoovers zu ermöglichen
		//already = 
		setButtonBuyHover(posx, posy);			// setzt Hover, sofern Bereich richtig ist. gibt true aus wenn erfolgreich, false wenn Bereich falsch
	
		// Category Menu Hover
		setCatMenuHover(posx, posy);
	}
	
	/**
	 * Verarbeitet den Hover
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 */
	public static boolean setItemHover(int posx, int posy) {
		int fieldNo = isInItemSquare(posx, posy);
		boolean hit = false;
		if(fieldNo >= 0) {
			displaySelectionFrame(fieldNo, true);
			hit = true;
		}
		return hit;
	}
	/**
	 * Hoveranzige des Kaufbuttons
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 * @return TODO: return?
	 */
	public static boolean setButtonBuyHover(int posx, int posy) {
		boolean hit = isInButtonBuy(posx, posy);
			if(hit) {
				if(flagHoverButtonBuy==false) {	// nur Bild ausgeben, wenn noch nicht angezeigt
					if(isSelectionValid()==false)	// Wenn Selektion ungültig, dann folgendes Hoverbild zusätzlich laden:
						DisplayManager.displayImage(buttonBuyHoverNV, buttonBuyBorderLe, buttonBuyBorderUp, imageTagButtonBuyHover, true);
					DisplayManager.displayImage(buttonBuyHover, buttonBuyBorderLe, buttonBuyBorderUp, imageTagButtonBuyHover, true);	// Hover laden
					flagHoverButtonBuy = true;
				}
			} else {
				flagHoverButtonBuy = false;
				DisplayManager.removeChangeableImages(imageTagButtonBuyHover);
			}
		return hit;
	}
	/**
	 * Hoveranzige des WEchselbuttons
	 * @param posx x-Position der Maus
	 * @param posy x-Position der Maus
	 * @return TODO: return?
	 */
	public static boolean setCatMenuHover(int posx, int posy) {
		boolean hit = false;
		
		if(isInUpperButtonCatMenu(posx, posy)) {
			if(flagHoverButtonCatMenu==false) {	// nur Bild ausgeben, wenn noch nicht angezeigt
				DisplayManager.displayImage("shop/catMenuSelectHover.png", (borderRi-27), (fixPointY-5), imageTagButtonCatMenuHover, true);	// Hover laden
				flagHoverButtonCatMenu = true;
			}
		} else if(isInLowerButtonCatMenu(posx, posy)) {
			if(flagHoverButtonCatMenu==false) {	// nur Bild ausgeben, wenn noch nicht angezeigt
				DisplayManager.displayImage("shop/catMenuSelectHover.png", (borderRi-27), (fixPointY+15), imageTagButtonCatMenuHover, true);		// Hover laden
				flagHoverButtonCatMenu = true;
			}
		} else {
			flagHoverButtonCatMenu = false;
			DisplayManager.removeChangeableImages(imageTagButtonCatMenuHover);
		}
		return hit;		
	}
	
	/**
	 * Wählt eine Kategorie (Waffe oder Munition)
	 * @param catID Nr der Kategorie
	 */
	private static void chooseItemCat(int catID) {
		switch(catID) {
		case 1:
			chooseItemCatActions(catID);
			displayItemCat_Weapon();
			setDisplayItemAttachment();
			break;
		case 2:
			chooseItemCatActions(catID);
			displayItemCat_Ammo();
			setDisplayItemAttachment();
			break;
		}
	}
	//TODO: kommentar
	private static void chooseItemCatActions(int catID) {
		if(currentCat>0) {
			unloadItemImages();
		}
		currentCat = catID;
	}
	
	/**
	 * Stellt das Waffenmenü dar
	 */
	private static void displayItemCat_Weapon() {
		if(imageTagIcons!="")	// wenn bereits eine Kategorie geladen wurde:
			unloadItemImages();	// Bilder löschen
		imageTagIcons = "shopItemsWeapon";
		int zeile = itemAreaFixPointY;
		int spalte = itemAreaFixPointX;
		itemCount = 0;	// Anzahl an gelisteten Items
		
		// Items auflisten
		for(int a=1; a < Weapon.weaponList.size(); a++) {	// Spaltenweise (a=1 start, da faust nicht erwerbbar)
			if(spalte>itemAreaWidth-1) {
				spalte = itemAreaFixPointX;
				zeile++;
			}
			
			DisplayManager.displayImage(Weapon.weaponList.get(a).img, fixPointX+spalte*iconFieldWidth, fixPointY+zeile*iconFieldHeight+10, imageTagIcons, true);
			spalte++;
			itemCount++;
		}
		
		// Titelleiste anpassen
		displayCatMenuTitle("weapon");
	}
	/**
	 * Stellt das Munitionsmenü dar
	 */
	private static void displayItemCat_Ammo() {
		if(imageTagIcons!="")	// wenn bereits eine Kategorie geladen wurde:
			unloadItemImages();	// Bilder löschen
		imageTagIcons = "shopItemsAmmo";
		int zeile = itemAreaFixPointY;
		int spalte = itemAreaFixPointX;
		itemCount = 0;	// Anzahl an gelisteten Items
		for(int a=1; a < Weapon.weaponList.size(); a++) {	// Spaltenweise (a=1 start, da faust nicht erwerbbar)
			if(spalte>itemAreaWidth-1) {
				spalte = itemAreaFixPointX;
				zeile++;
			}
			
			DisplayManager.displayImage(Weapon.weaponList.get(a).img, fixPointX+spalte*iconFieldWidth, fixPointY+zeile*iconFieldHeight+10, imageTagIcons, true);
			spalte++;
			itemCount++;
		}
		
		// Titelleiste anpassen
		displayCatMenuTitle("ammo");
	}
	
	/**
	 * Ändert den angezeigten Kategorietitel
	 * @param category Waffe oder Munition
	 */
	private static void displayCatMenuTitle(String category) {
		DisplayManager.removeChangeableImages("catMenu");
		DisplayManager.displayImage("shop/catMenu_" + category + ".png", fixPointX-5, fixPointY-5, "catMenu", true);
	}
	
	/**
	 * Nächste Kategorie wird gewählt
	 */
	private static void setCatNext() {
		chooseItemCat(currentCat+1);
	}
	/**
	 * Vorherige Kategorie wird gewählt
	 */
	private static void setCatPrev() {
		chooseItemCat(currentCat-1);
	}
	
	/**
	 * Credits werden dargestellt
	 */
	private static void displayCredits() {	// zeigt den aktuellen CreditStand oben rechts in der Ecke an
		//int posx = fixPointX + shopWidth - iconFieldWidth;
		//int posy = fixPointY + 10;
		
		currentCredits = Player.playerList.get(0).score.getScore();
		displayCreditsNumberCheck();			
	}
	/**
	 * Creditsziffern werden dargestellt
	 */
	private static void displayCreditsNumberCheck() {
		DisplayManager.removeChangeableImages(imageTagCredits);	// löschen, falls dies nicht der erste Aufruf ist, sondern eine Aktualisierung
		
		int value = currentCredits;
		int counter = 0;
		while(value / Math.pow(10, counter) >= 1) {
			counter++;
		}

		for(int ziffer = 0; ziffer < counter; ziffer++ ) {
			displayCreditsNumberCheckDisplay((int) (value/Math.pow(10, ziffer) % 10), ziffer);
		}
		if(counter<=0) {	// falls kein Geld vorhanden Null anzeigen
			displayCreditsNumberCheckDisplay(0, 0);
		}
	}
	/**
	 * Ziffern werden ausgesucht, die dargestellt werden sollen
	 * @param number Zahl die an Position von ziffer steht
	 * @param ziffer Wievielte Ziffer wird betrachtet
	 */
	private static void displayCreditsNumberCheckDisplay(int number, int ziffer) {
		int imgSizeX = 15;
		String path = "numbers/";
		if(number>9)
			number=0;
		
		switch(number) {
		case 0:
			path += "0";
			break;
		case 1:
			path += "1";
			break;
		case 2:
			path += "2";
			break;
		case 3:
			path += "3";
			break;
		case 4:
			path += "4";
			break;
		case 5:
			path += "5";
			break;
		case 6:
			path += "6";
			break;
		case 7:
			path += "7";
			break;
		case 8:
			path += "8";
			break;
		case 9:
			path += "9";
			break;
		}
		path += ".png";
		DisplayManager.displayImage(path, fixPointX+shopWidth-(imgSizeX*(ziffer+2)), fixPointY-5, imageTagCredits, true);
	}
	
	/**
	 * Entfernt die Itemsbilder
	 */
	private static void unloadItemImages() {
		DisplayManager.removeChangeableImages(imageTagIcons);
		DisplayManager.removeChangeableStrings(imageTagInfos);
		DisplayManager.removeChangeableImages(imageTagIconSelected);
		DisplayManager.removeChangeableImages(imageTagIconHover);
		DisplayManager.removeChangeableImages(imageTagItemAttachment);
		DisplayManager.removeChangeableImages("catMenu");
		DisplayManager.removeChangeableImages(imageTagButtonCatMenuHover);
		currentSelection = -1;
	}
	/**
	 * Entfernt alle Basic-Shopbilder
	 */
	private static void unloadShopImages() {
		DisplayManager.removeChangeableImages(imageTagShop);
		DisplayManager.removeChangeableStrings(imageTagCredits);
		DisplayManager.removeChangeableImages(imageTagButtonBuyHover);
		DisplayManager.removeChangeableImages(imageTagCredits);
	}
	/**
	 * Schließt den Shop
	 */
	public static void closeShop() {
		show = false;
		unloadItemImages();
		unloadShopImages();
	}
	
	/**
	 * bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
	 * @param path Bildpfad
	 * @return Bild
	 */
	private static Image getImage(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
		Image img = null;
		try {
			img = ImageIO.read(new File("src/the/game/java/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * Kaufen eines Items
	 */
	private static void buyItem() {
		if(isSelectionValid()) {
			int price = getPrice(currentSelection);
			switch(currentCat) {
			case 1:
				Player.playerList.get(0).score.setScore(price * (-1));
				Player.playerList.get(0).chooseWeapon(currentSelection+1);
				break;
			case 2:
				Player.playerList.get(0).score.setScore(price * (-1));
				Player.playerList.get(0).addAmmo(currentSelection+1, getMagPackSize(currentSelection));
				break;
			}
			updateScreen();
		}
	}
	/**
	 * Gibt die Zahl der Magazine an, die man bei einem Kauf erwirbt
	 * @param fieldNo Feldnummer
	 * @return Zahl der Magazine
	 */
	private static int getMagPackSize(int fieldNo) {
		return Weapon.weaponList.get(fieldNo+1).magPackSize;
	}
	/**
	 * Überprüft ob Auswahl den Itemfeldes gültig ist
	 * @return wenn ja, valid = flase
	 */
	private static boolean isSelectionValid() {
		boolean valid=false;
		
		switch(currentCat) {
		case 1:
			valid = !isWeaponAlreadyInUse(currentSelection);	// Überprüft ob Waffe bereits in benutzung (wenn ja, valid = flase)
			break;
		case 2:
			valid = isWeaponAlreadyInUse(currentSelection);	// Überprüft ob Waffe bereits in benutzung (wenn ja, valid = flase)
			break;
		}
		
		if(valid)
			valid = (currentSelection<itemCount);	// Wenn gewähltes Feld ein Item enthält, gültig
		
		if(valid)
			valid = isEnoughMoney(currentSelection);				// Überprüft ob genug Geld vorhanden ist, um das Item zu bezahlen
		return valid;
	}
	/**
	 * Updated den Bildschirm
	 */
	private static void updateScreen() {
		displayCredits();
		unloadItemImages();
		chooseItemCat(currentCat);
	}
}
