package the.game.java;

import java.awt.Image;

import javax.swing.ImageIcon;


public class Shop {
	
	/**
	 *  SHOP
	 *  Aufzurufen über: 	new Shop();
	 *  Schließen über:		ESCAPE
	 *  Funktion:			Zeigt einen Shop, der Items zusammen mit einigen Infos auflistet
	 *  					Diese Items kann der Spieler(1) kaufen. Er bezahlt mit Punkten.
	 */
	
	
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
	private static int itemAreaFixPointY = 2;
	private static int itemAreaWidth = 6;
	private static int itemAreaHeight = 5;
	
	private static String imageTagShop = "shop";
	private static String imageTagIcons = "";
	private static String imageTagInfos = "";
	private static String imageTagIconSelected = "shopIconSelected";
	private static String imageTagIconHover = "shopIconHover";
	private static String imageTagItemAttachment = "shopIconAttachment";
	private static String imageTagCredits = "shopCredits";
	private static String imageTagButtonBuyHover = "shopBuyHover";
	
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
	
	private static ImageIcon ii;
	private static Image buttonBuy;
	private static Image buttonBuyHover;
	private static Image buttonBuyHoverNV;
	//private static Image buttonBuySelected;
	
	public Shop() {
		shopMap = new int[getShopDimensions(0)][getShopDimensions(1)];
		setVariable();
		show = true;
		setShopDisplay();
	}
		
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
	
	private void setVariable() {
		fixPointX = (Runner.getWidthF() / 2) - (shopWidth / 2);
		fixPointY = (Runner.getHeightF() / 2) - (shopHeight / 2);
		
		borderLe = fixPointX + (itemAreaFixPointX * squareLength);
		borderRi = borderLe + (itemAreaWidth * iconFieldWidth);
		borderUp = fixPointY + (itemAreaFixPointY * squareLength);
		borderDo = borderUp + (itemAreaHeight * iconFieldHeight);
		
		buttonBuy = getImage("shop/buttonBuy.png");
		buttonBuyHover = getImage("shop/buttonBuyHover.png");
		buttonBuyHoverNV = getImage("shop/buttonBuyHoverNotValid.png");
		//buttonBuySelected = getImage("shop/buttonBuySelected.png");
		
		buttonBuyBorderLe = borderRi;
		buttonBuyBorderUp = borderDo-iconFieldHeight;
		buttonBuyBorderRi = buttonBuyBorderLe + buttonBuy.getWidth(null);
		buttonBuyBorderDo = buttonBuyBorderUp + buttonBuy.getHeight(null);
	}
	
	private static void setShopDisplay() {
		if(show) {
			DisplayManager.displayImage("transparentLayer.png", 0, 0, imageTagShop);
			DisplayManager.displayImage("shop/shopBackground.png", fixPointX-5, fixPointY-5, imageTagShop);
			DisplayManager.displayImage("shop/title.png", fixPointX, fixPointY, imageTagShop);
			DisplayManager.displayImage(buttonBuy, buttonBuyBorderLe, buttonBuyBorderUp, imageTagShop);

	        for(int a=0; a < getShopDimensions(0); a++) {	// Spaltenweise
				for(int b=0; b<getShopDimensions(1); b++) {		// Zeilenweise
					if(b>=itemAreaFixPointY && a>= itemAreaFixPointX && a<itemAreaFixPointX+itemAreaWidth && b<itemAreaFixPointY+itemAreaHeight)
						DisplayManager.displayImage("shop/iconFieldBg.png", fixPointX+a*iconFieldWidth, fixPointY+b*iconFieldHeight, imageTagShop);
				}
			}
	        displayCredits();
	        chooseItemCat(2);
		}
	}
	
	/**     Select Item     */
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
	
	private static void selectItem_Weapon(int fieldNo) {
		displaySelectionFrame(fieldNo, false);
		displaySelectedItemInfo_Weapon(fieldNo);
	}
	private static void selectItem_Ammo(int fieldNo) {
		displaySelectionFrame(fieldNo, false);
		displaySelectedItemInfo_Weapon(fieldNo); //TODO: anpassen an AMMO
	}
	
	private static void displaySelectionFrame(int fieldNo, boolean isHover) {
		if(show) {
			
			int POSX = (fieldNo % itemAreaWidth);
			int POSY = ((fieldNo - POSX) / itemAreaWidth);
			POSX += borderLe + (POSX * (iconFieldWidth-1));
			POSY += borderUp + (POSY * (iconFieldHeight-1));;
			
			if(isHover) {
				DisplayManager.removeChangeableImages(imageTagIconHover);
				DisplayManager.displayImage("shop/iconFieldHover.png", POSX, POSY, imageTagIconHover);
			} else {
				DisplayManager.removeChangeableImages(imageTagIconSelected);
				DisplayManager.displayImage("shop/iconFieldSelected.png", POSX, POSY, imageTagIconSelected);
			}
		}
	}
	
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
	
	private static boolean isWeaponAlreadyInUse(int itemNo) {
		boolean isInUse = false;
		for(int a=0; a<WeaponManager.weaponManagerList.get(0).weaponInUseList.size(); a++) {		// Überprüfen ob bereits in Besitz
			if(WeaponManager.weaponManagerList.get(0).weaponInUseList.get(a).weaponID==(itemNo+1)) {
				
				isInUse = true;
			}
		}
		return isInUse;
	}
	
	private static boolean isEnoughMoney(int itemNo) {
		boolean isEnough = false;
		if(getPrice(itemNo)<=currentCredits) {		// Überprüfen ob genug Geld vorhanden
			isEnough = true;
		}
		return isEnough;
	}
	
	private static int getPrice(int itemNo) {	// gibt den Preis eines bestimmten Items zurück, wenn keine Kategorie vorhanden oder Item nicht vorhanden oder Price nicht vorhanden, dann Rückgabe = 0
		int price = 0;
		switch(currentCat) {
		case 1:
			if(itemNo<itemCount)	// Wenn gewähltes Feld ein Item enthält:
				price = Weapon.weaponList.get(itemNo+1).price;
			break;
		}
		return price;
	}
	
	private static void displayAtIcon(int fieldNo, String imgPath, String imageTag, boolean isMoreThanOne) {	// zeigt Bild für angegebenes IconFeld dar. isMoreThanOne=true, können mehrere dargestellt werden, wenn isMoreThanOne=false, werden jedesmal alle vorherigen mit dem angegebenen Tag gelöscht
		if(show) {
	
			int POSX = (fieldNo % itemAreaWidth);
			int POSY = ((fieldNo - POSX) / itemAreaWidth);
			POSX += borderLe + (POSX * (iconFieldWidth-1));
			POSY += borderUp + (POSY * (iconFieldHeight-1));;
			
			if(isMoreThanOne==false)
				DisplayManager.removeChangeableImages(imageTag);
			DisplayManager.displayImage(imgPath, POSX, POSY, imageTag);
		}
	}
	
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
		info[6] = String.valueOf(Weapon.weaponList.get(fieldNo+1).price) + "C";
		
		displaySelectedItemInfo(titel, info);
	}
	
	private static void displaySelectedItemInfo(String[] titel, String[] info) {
		int infoAreaFixPointX = fixPointX + ((itemAreaFixPointX + itemAreaWidth) * iconFieldWidth);
		int infoAreaFixPointY = fixPointY + (itemAreaFixPointY * iconFieldHeight) + 10;
		int distance = 130;
		imageTagInfos = "shopInfo";
		
		DisplayManager.removeChangeableStrings(imageTagInfos);
		
		DisplayManager.displayString(titel[0], infoAreaFixPointX, infoAreaFixPointY-20, imageTagInfos);	// ITEMNAME
		for(int a=1; a<titel.length; a++) {
			if(titel[a]!=null && info[a]!=null) {
				DisplayManager.displayString(titel[a], infoAreaFixPointX, infoAreaFixPointY + (a-1)*20, imageTagInfos);
				DisplayManager.displayString(info[a], infoAreaFixPointX + distance, infoAreaFixPointY + (a-1)*20, imageTagInfos);
			}
		}
	}
	
	public static int isInItemSquare(int posx, int posy) {	// Rückgabewert <0 wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert = feldnummer
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
	
	public static boolean isInButtonBuy(int posx, int posy) {	// Rückgabewert false wenn kein Bereich betroffen; wenn Feld getroffen, dann Rückgabewert true
		boolean hit = false;

		if(posx>buttonBuyBorderLe && posx<buttonBuyBorderRi && posy>buttonBuyBorderUp && posy<buttonBuyBorderDo)
			hit = true;
		
		return hit;
	}
	
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
	}
	
	public static void receiveMouseMovement(int posx, int posy) {
		//boolean already = false;	// um weitere überprüfungen überflüssig zu machen
		
		// Icon Hover
		//already = 
		setItemHover(posx, posy);				// setzt Hover, sofern Bereich richtig ist. gibt true aus wenn erfolgreich, false wenn Bereich falsch
		
		// Button Hover (keine überprüfung von already um löschen des Hoovers zu ermöglichen
		//already = 
		setButtonBuyHover(posx, posy);			// setzt Hover, sofern Bereich richtig ist. gibt true aus wenn erfolgreich, false wenn Bereich falsch
	}
	
	/**     Hover     */
	public static boolean setItemHover(int posx, int posy) {
		int fieldNo = isInItemSquare(posx, posy);
		boolean hit = false;
		if(fieldNo >= 0) {
			displaySelectionFrame(fieldNo, true);
			hit = true;
		}
		return hit;
	}
	public static boolean setButtonBuyHover(int posx, int posy) {
		boolean hit = isInButtonBuy(posx, posy);
			if(hit) {
				if(flagHoverButtonBuy==false) {	// nur Bild ausgeben, wenn noch nicht angezeigt
					if(isSelectionValid()==false)	// Wenn Selektion ungültig, dann folgendes Hoverbild zusätzlich laden:
						DisplayManager.displayImage(buttonBuyHoverNV, buttonBuyBorderLe, buttonBuyBorderUp, imageTagButtonBuyHover);
					DisplayManager.displayImage(buttonBuyHover, buttonBuyBorderLe, buttonBuyBorderUp, imageTagButtonBuyHover);	// Hover laden
					flagHoverButtonBuy = true;
				}
			} else {
				flagHoverButtonBuy = false;
				DisplayManager.removeChangeableImages(imageTagButtonBuyHover);
			}
		return hit;
	}
	
	/**     Choose ItemKategorie     */
	private static void chooseItemCat(int catID) {
		currentCat = catID;
		switch(currentCat) {
		case 1:
			displayItemCat_Weapon();
			break;
		case 2:
			displayItemCat_Ammo();
			break;
		}
		setDisplayItemAttachment();
	}
	
	private static void displayItemCat_Weapon() {
		if(imageTagIcons!="")	// wenn bereits eine Kategorie geladen wurde:
			unloadItemImages();	// Bilder löschen
		imageTagIcons = "shopItemsWeapon";
		int zeile = itemAreaFixPointY;
		int spalte = itemAreaFixPointX;
		itemCount = 0;	// Anzahl an gelisteten Items
		for(int a=1; a < Weapon.weaponList.size(); a++) {	// Spaltenweise (a=1 start, da faust nicht erwerbbar)
			if(spalte>itemAreaWidth-1) {
				spalte = itemAreaFixPointX;
				zeile++;
			}
			
			DisplayManager.displayImage(Weapon.weaponList.get(a).imgPath, fixPointX+spalte*iconFieldWidth, fixPointY+zeile*iconFieldHeight+10, imageTagIcons);
			spalte++;
			itemCount++;
		}
	}
	
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
			
			DisplayManager.displayImage(Weapon.weaponList.get(a).imgPath, fixPointX+spalte*iconFieldWidth, fixPointY+zeile*iconFieldHeight+10, imageTagIcons);
			spalte++;
			itemCount++;
		}
	}
	
	/**     Display: Credits     */
	private static void displayCredits() {	// zeigt den aktuellen CreditStand oben rechts in der Ecke an
		int posx = fixPointX + shopWidth - iconFieldWidth * 2;
		int posy = fixPointY + 10;
		
		currentCredits = Score.scoreList.get(0).getScore();
		String text = "Credits:  " + String.valueOf(currentCredits);
		DisplayManager.removeChangeableStrings(imageTagCredits);		// löschen, falls dies nicht der erste Aufruf ist, sondern eine Aktualisierung
		DisplayManager.displayString(text, posx, posy, imageTagCredits);
	}
	
	/**     Unload/Close Operations     */
	private static void unloadItemImages() {
		DisplayManager.removeChangeableImages(imageTagIcons);
		DisplayManager.removeChangeableStrings(imageTagInfos);
		DisplayManager.removeChangeableImages(imageTagIconSelected);
		DisplayManager.removeChangeableImages(imageTagIconHover);
		DisplayManager.removeChangeableImages(imageTagItemAttachment);
	}
	
	private static void unloadShopImages() {
		DisplayManager.removeChangeableImages(imageTagShop);
		DisplayManager.removeChangeableStrings(imageTagCredits);
		DisplayManager.removeChangeableImages(imageTagButtonBuyHover);
	}
	
	public static void closeShop() {
		show = false;
		unloadItemImages();
		unloadShopImages();
	}
	
	/**     HILFSMETHODEN     */
	public static Image getImage(String path) {	// bekommt Bildpfad und gibt eine Ausgabe vom Typ Image zurück
    	ii = new ImageIcon(Shop.class.getResource(path));
    	return ii.getImage();
    }
	
	/**     KAUFEN     */
	private static void buyItem() {
		if(isSelectionValid()) {
			int price = getPrice(currentSelection);
			switch(currentCat) {
			case 1:
				Score.scoreList.get(0).setScore(price * (-1));
				WeaponManager.weaponManagerList.get(0).chooseWeapon(currentSelection+1);
				updateScreen();
				break;
			case 2:
				Score.scoreList.get(0).setScore(price * (-1));
				WeaponManager.weaponManagerList.get(0).addAmmo(currentSelection+1, 5);
				updateScreen();
				break;
			}
			
		}
	}
	
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
	
	private static void updateScreen() {
		displayCredits();
		unloadItemImages();
		chooseItemCat(currentCat);
	}
}
