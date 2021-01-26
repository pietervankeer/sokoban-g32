package domein;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import exception.fouteBewegingException;
import persistentie.VeldMapper;

/**
 * De spelborden die in een spel zitten, bestaat uit meerdere velden
 */
public class Spelbord {
	// properties
	private final int id;
	/**
	 * Lijst van alle velden in het spelbord
	 */
	private List<Veld> velden = new ArrayList<>();
	/**
	 * 2 dim String Array van het spelbord dat de speler speelt
	 */
	private String[][] huidigSpelbordArray;
	/**
	 * 2 dim String Array van het spelbord dat de speler origineel gestart is
	 */
	private String[][] origineelSpelbordArray;
	
	// constructors
	/**
	 * Constructor die de lijst van velden en het id instelt
	 * 
	 * @param velden die in het spelbord zitten
	 * @param id van het spelbord
	 */
	public Spelbord(List<Veld> velden, int id) {
		this.id = id;
		setVelden(velden);
	}

	// getters & setters
	public int getId() {
		return this.id;
	}

	public List<Veld> getVelden() {
		return this.velden;
	}

	public void setVelden(List<Veld> velden) {
		this.velden = velden;
	}
	
	public String[][] getHuidigSpelbordArray() {
		return huidigSpelbordArray;
	}
	
	public String[][] getOrigineelSpelbordArray() {
		return this.origineelSpelbordArray;
	}

	// public methods

	// UC 4

	/**
	 * De 2 dim String huidigSpelbordArray en origineelSpelbordArray opvullen met alle velden die in het spelbord zitten
	 */
	public void initialiseerSpelbord() {
		
		 huidigSpelbordArray = new String[10][10];
		 origineelSpelbordArray = new String[10][10];
		 for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					huidigSpelbordArray[i][j] = " ";
					origineelSpelbordArray[i][j] = " ";
				}
			}
		 
		 List<Veld> velden = this.getVelden();
			for (Veld v : velden) {
				int x = v.getxCord();
				int y = v.getyCord();
				String type = "";
				if (v.getIsKist()) {
					type = "K";
				} else if (v.getIsMannetje()) {
					type = "M";
				} else if (v instanceof Muur) {
					type = "X";
				} else if (v instanceof Doel) {
					type = "D";
				} else {
					type = " ";
				}

				huidigSpelbordArray[x][y] = type;
				origineelSpelbordArray[x][y] = type;
			}			
	}
	
	/**
	 * Wijzigt het meegegeven veld via de positie in een ander veld meegegeven via actie, hierna voegt het dit veld toe aan de lijst 
	 * van velden en initialiseert het
	 * 
	 * Actie: 1. muur plaatsen 2. doel plaatsen 3. mannetje plaatsen 4. kist plaatsen 5. veld leegmaken of verwijderen
	 * 
	 * @param positie de coordinaten van het veld dat we willen wijzigen
	 * @param actie de actie de we willen uitvoeren op het veld
	 */
	public void wijzigVeld(Point2D positie, int actie) {
		Veld v = new Veld((int) positie.getY(), (int) positie.getX(), false, false);
		switch (actie) {
		case 1:
			v = new Muur((int) positie.getY(), (int) positie.getX());
			voegVeldToe(v);
			break;
		case 2:
			v = new Doel((int) positie.getY(), (int) positie.getX());
			voegVeldToe(v);
			break;
		case 3:
			v = new Veld((int) positie.getY(), (int) positie.getX(), true, false);
			voegVeldToe(v);
			break;
		case 4:
			v = new Veld((int) positie.getY(), (int) positie.getX(), false, true);
			voegVeldToe(v);
			break;
		default:
			Veld teVerwijderen = null;
			for (Veld veld : getVelden()) {
				if (veld.getxCord() == v.getxCord() && veld.getyCord() == v.getyCord()) {
					teVerwijderen = veld;
				}
			}
			
			List<Veld> nieuweVelden = new ArrayList<Veld>();
			nieuweVelden = getVelden();
			nieuweVelden.remove(teVerwijderen);
			setVelden(nieuweVelden);
			break;
		}
		initialiseerSpelbord();
	}
	
	/**
	 * Deze methode verplaatst het mannetje in de 2 dim String array huidigSpelbordArray
	 * 
	 * @param richting waarin de gebruiker wenst te bewegen
	 * @throws fouteBewegingException 
	 */
	public void verplaatsMannetje(char richting) throws fouteBewegingException {
		
	int xCordMannetje = 0, yCordMannetje = 0;
	int tellerRij = 0, tellerKolom = 0;

	
	// mannetje zoeken in je String[][]
	Point2D mannetje = null;

	for (String[] rij : huidigSpelbordArray) {
		tellerKolom = 0;
		for (String kolom : rij) {
			if (kolom.equals("M")) {
				xCordMannetje = tellerKolom;
				yCordMannetje = tellerRij;
				mannetje = new Point2D.Double(xCordMannetje, yCordMannetje);
			}
			tellerKolom++;
		}

		tellerRij++;
	}

	// alle doelen in een lijst steken als Point2D objectedn (coordinaten)
	tellerRij = 0;
	tellerKolom = 0;
	List<Point2D> doelen = new ArrayList<Point2D>();
	for (String[] rij : origineelSpelbordArray) {
		tellerKolom = 0;
		for (String kolom : rij) {
			if (kolom.equals("D")) {
				doelen.add(new Point2D.Double(tellerKolom, tellerRij));
			}
			tellerKolom++;
		}
		tellerRij++;
	}

	// code voor verplaatsing naar alle richtingen en exceptions voor als beweging
	// fout is
	switch (richting) {
	case 'z':
		if (huidigSpelbordArray[yCordMannetje - 1][xCordMannetje].equals("X")) {
			throw new fouteBewegingException("");
		} else if (huidigSpelbordArray[yCordMannetje - 1][xCordMannetje].equals("K")) {
			if (huidigSpelbordArray[yCordMannetje - 2][xCordMannetje].equals("X")) {
				throw new fouteBewegingException("");
			} else if (huidigSpelbordArray[yCordMannetje - 2][xCordMannetje].equals("K")) {
				throw new fouteBewegingException("");
			}
			huidigSpelbordArray[yCordMannetje - 1][xCordMannetje] = "M";
			huidigSpelbordArray[yCordMannetje - 2][xCordMannetje] = "K";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}

		} else {
			huidigSpelbordArray[yCordMannetje - 1][xCordMannetje] = "M";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}
		}
		break;

	case 'q':
		if (huidigSpelbordArray[yCordMannetje][xCordMannetje - 1].equals("X")) {
			throw new fouteBewegingException("");
		} else if (huidigSpelbordArray[yCordMannetje][xCordMannetje - 1].equals("K")) {
			if (huidigSpelbordArray[yCordMannetje][xCordMannetje - 2].equals("X")) {
				throw new fouteBewegingException("");
			} else if (huidigSpelbordArray[yCordMannetje][xCordMannetje - 2].equals("K")) {
				throw new fouteBewegingException("");
			}
			huidigSpelbordArray[yCordMannetje][xCordMannetje - 1] = "M";
			huidigSpelbordArray[yCordMannetje][xCordMannetje - 2] = "K";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}

		} else {
			huidigSpelbordArray[yCordMannetje][xCordMannetje - 1] = "M";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}
		}
		break;

	case 's':
		if (huidigSpelbordArray[yCordMannetje + 1][xCordMannetje].equals("X")) {
			throw new fouteBewegingException("");
		} else if (huidigSpelbordArray[yCordMannetje + 1][xCordMannetje].equals("K")) {
			if (huidigSpelbordArray[yCordMannetje + 2][xCordMannetje].equals("X")) {
				throw new fouteBewegingException("");
			} else if (huidigSpelbordArray[yCordMannetje + 2][xCordMannetje].equals("K")) {
				throw new fouteBewegingException("");
			}
			huidigSpelbordArray[yCordMannetje + 1][xCordMannetje] = "M";
			huidigSpelbordArray[yCordMannetje + 2][xCordMannetje] = "K";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}

		} else {
			huidigSpelbordArray[yCordMannetje + 1][xCordMannetje] = "M";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}
		}
		break;
	case 'd':
		if (huidigSpelbordArray[yCordMannetje][xCordMannetje + 1].equals("X")) {
			throw new fouteBewegingException("");
		} else if (huidigSpelbordArray[yCordMannetje][xCordMannetje + 1].equals("K")) {
			if (huidigSpelbordArray[yCordMannetje][xCordMannetje + 2].equals("X")) {
				throw new fouteBewegingException("");
			} else if (huidigSpelbordArray[yCordMannetje][xCordMannetje + 2].equals("K")) {
				throw new fouteBewegingException("");
			}
			huidigSpelbordArray[yCordMannetje][xCordMannetje + 1] = "M";
			huidigSpelbordArray[yCordMannetje][xCordMannetje + 2] = "K";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}

		} else {
			huidigSpelbordArray[yCordMannetje][xCordMannetje + 1] = "M";
			if (doelen.contains(mannetje)) {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = "D";
			} else {
				huidigSpelbordArray[yCordMannetje][xCordMannetje] = " ";
			}
		}
		break;
	case 'e':
		System.exit(0);
		break;
	case 'r':
		for(int i = 0; i < origineelSpelbordArray.length; i++){
			huidigSpelbordArray[i] = origineelSpelbordArray[i].clone();
		}
		break;
	}
		
	}

	/**
	 * Deze hulpmethode controleert of het spelbord voltooid is
	 * 
	 * @return true als alle doelen een kist op hen hebben staan en er 1 mannetje is
	 */
	public boolean isSpelbordVoltooid() {
		// controleren of alle kisten op alle doelen staan
		List<Point2D> kisten = new ArrayList<Point2D>();
		List<Point2D> doelen = new ArrayList<Point2D>();

		boolean voltooid = false;
		
		int tellerKolom;
		int tellerRij = 0;
		for (String[] rij : this.origineelSpelbordArray) {
			tellerKolom = 0;
			for (String kolom : rij) {
				if (kolom.equals("D")) {
					doelen.add(new Point2D.Double(tellerKolom, tellerRij));
				}
				tellerKolom++;
			}
			tellerRij++;
		}
		tellerRij = 0;
		for (String[] rij : this.huidigSpelbordArray) {
			tellerKolom = 0;
			for (String kolom : rij) {
				if (kolom.equals("K")) {
					kisten.add(new Point2D.Double(tellerKolom, tellerRij));
				}
				tellerKolom++;
			}
			tellerRij++;
		}

		if (doelen.equals(kisten)) {
			voltooid = true;
		}
		
		return voltooid;
	}
	
	// UC 6
	/**
	 * Roept de setter op van velden om een nieuwe lijst van aangepaste velden (overschreven) mee te geven
	 * 
	 * @param veld v dat werd meegegeven
	 */
	public void voegVeldToe(Veld v) {
		int index = -1;
		List<Veld> nieuweLijst = getVelden();
		
		for (int i = 0; i < getVelden().size(); i++) {
			if (getVelden().get(i).getxCord() == v.getxCord() && getVelden().get(i).getyCord() == v.getyCord()) {
				index = i;
			}
		}
		
		if (index >= 0) {
			nieuweLijst.remove(index);
			nieuweLijst.add(v);
			setVelden(nieuweLijst);
		} else {
			nieuweLijst.add(v);
			setVelden(nieuweLijst);
		}
		
	}
	
	/**
	 * Roept de verschillende validaties op en return dan true of false
	 * 
	 * @return true als er 1 mannetje is en als er evenveel kisten als doelen zijn, anders false
	 */
	public boolean valideerSpelbord() {
//		System.out.printf("Rand is %b, mannetje is %b, kisten en doelen zijn %b, veld is %b", valideerRand(), valideerMannetje(), valideerKistenDoelen(), valideerVeld());
		return valideerRand() &&  valideerMannetje() && valideerKistenDoelen() && valideerVeld();
	}
	
	/**
	 * Validatie voor te controleren of elk veld bereikbaar is
	 * 
	 * @return true als alle velden bereikbaar zijn
	 */
	private boolean valideerVeld() {
		int xCord = -1, yCord = -1;
		
		for (Veld v : getVelden()) {
			if (v.getIsMannetje()) {
				xCord = v.getxCord();
				yCord = v.getyCord();
				break;
			}
		}
		
		if (xCord == -1 && yCord == -1) {
			return false;
		}
		
		Point2D mannetje = new Point2D.Double();
		mannetje.setLocation(xCord, yCord);
		List<Point2D> buren = new ArrayList<Point2D>();
		buren.add(mannetje);
		List<Point2D> bereikbareVelden = floodFill(xCord, yCord, buren);
		
		
		List<Point2D> verwijderVelden = new ArrayList<Point2D>();
		List<Point2D> legeVelden = new ArrayList<Point2D>();
		for (Point2D point2d : bereikbareVelden) {
			if (getHuidigSpelbordArray()[(int)point2d.getY()][(int)point2d.getX()] == " ") {
				legeVelden.add(point2d);
			}
		}
		for (Veld veld : getVelden()) {
			if (!(veld instanceof Muur)) {
				for (Point2D point2d : bereikbareVelden) {
					if (veld.getyCord() == point2d.getX() && veld.getxCord() == point2d.getY()) {
						verwijderVelden.add(point2d);
					}
				}
			}
		}
		bereikbareVelden.removeAll(legeVelden);
		bereikbareVelden.removeAll(verwijderVelden);
		
		boolean veldValidatie = false;
		if (bereikbareVelden.isEmpty()) {
			veldValidatie = true;
		}
		
		return veldValidatie;
		
	}
	
	/**
	 * Validatie voor te controleren of er evenveel kisten als doelen zijn
	 * 
	 * @param bereikbareVelden alle velden waar het mannetje naartoe kan bewegen
	 * @return true als er evenveel doelen als kisten zijn
	 */
	private boolean valideerKistenDoelen() {
		int tellerKisten = 0;
		int tellerDoelen = 0;
		for (Veld v : getVelden()) {
			if (v instanceof Doel) {
				tellerDoelen++;
			} else if (v.getIsKist()) {
				tellerKisten++;
			}
		}
		
		return (tellerKisten == tellerDoelen) && tellerDoelen != 0;
	}
	
	/**
	 * Validatie die controleert of er 1 mannetje is
	 * 
	 * @return true als er 1 mannetje is
	 */
	private boolean valideerMannetje() {
		boolean mannetjeInSpel = false;
		
		for (Veld v : getVelden()) {
			if (v.getIsMannetje()) {
				mannetjeInSpel = true;
			}
		}
		
		return mannetjeInSpel;
	}
	
	/**
	 * Validatie die controleert of de rand van muren een gesloten figuur vormt
	 * 
	 * @return true als de rand van muren een gesloten figuur is
	 */
	private boolean valideerRand() {
		int startX = -1, startY = -1;
		
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (getHuidigSpelbordArray()[y][x].equals("X") && geefAlleAanliggendeBuren(x, y).size() == 2) {
					startX = x;
					startY = y;
					break;
				} 
			}
		}
		
		if (startX == -1 && startY == -1) {
			return false;
		}
		
		List<Point2D> startPosities = geefAlleAanliggendeBuren(startX, startY);
		List<Point2D> beginPositie1 = new ArrayList<Point2D>();
		List<Point2D> beginPositie2 = new ArrayList<Point2D>();
		beginPositie1.add(startPosities.get(0));
		beginPositie2.add(startPosities.get(1));
		
		huidigSpelbordArray[startY][startX] = " ";
		geefTotaalBuren((int)startPosities.get(0).getX(), (int)startPosities.get(0).getY(), beginPositie1);
		geefTotaalBuren((int)startPosities.get(1).getX(), (int)startPosities.get(1).getY(), beginPositie2);
		huidigSpelbordArray[startY][startX] = "X";
		return beginPositie1.containsAll(beginPositie2) && beginPositie2.containsAll(beginPositie1);
	}
	
	/**
	 * Geeft van een bepaald veld al zijn buren waar we nog niet eerder gepaseerd zijn
	 * 
	 * @param x coordinaat van het veld
	 * @param y coordinaat van het veld
	 * @param lijst van alle coordinaten van velden
	 */
	private void geefTotaalBuren(int x, int y, List<Point2D> lijst) {
		List<Point2D> nieuweBuren = new ArrayList<Point2D>();
		
		for (Point2D coord : geefAlleAanliggendeBuren(x, y)) {
			if (!lijst.contains(coord)) {
				lijst.add(coord);
				nieuweBuren.add(coord);
			}
		}
		
		for (Point2D coord : nieuweBuren) {
			geefTotaalBuren((int)coord.getX(), (int)coord.getY(), lijst);
		}
	}
	
	/**
	 * Geeft alle aanliggende buren (links, rechts, boven, onder) van een bepaald veld
	 * 
	 * @param x coordinaat van het veld
	 * @param y coordinaat van het veld
	 * @return lijst van Point2D objecten (coordinaten) van alle buren
	 */
	private List<Point2D> geefAlleAanliggendeBuren(int x, int y) {
		
		List<Point2D> buren = new ArrayList<Point2D>();
		
		String type = getHuidigSpelbordArray()[y][x];
		
		if (y != 9 && getHuidigSpelbordArray()[y + 1][x] == type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x, y + 1);
			buren.add(coord); 	}
		if (y != 0 && getHuidigSpelbordArray()[y - 1][x] == type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x, y - 1);
			buren.add(coord); 	}
		if (x != 9 && getHuidigSpelbordArray()[y][x + 1] == type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x + 1, y);
			buren.add(coord); 	}
		if (x != 0 && getHuidigSpelbordArray()[y][x - 1] == type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x - 1, y);
			buren.add(coord); 	}
		
		return buren;
	}
	
	/**
	 * Kijkt voor een veld naar zijn buren , als deze nog niet in de lijst van buren zit voegen we ze toe aan nieuweBuren waar we dan de floodFill
	 * methode ook gaan op uitvoeren om zo over alle velden die geen muren zijn te lopen. Als uiteindelijk de size van buren = veldenZonderMuren
	 * dan kan je elk veld bereiken
	 * 
	 * @param x coordinaat van startveld (mannetje)
	 * @param y coordinaat van startveld (mannetje)
	 * @param buren die we al bezocht hebben
	 * @return lijst van Point2D objecten (coordinaten) van alle bezochte velden 
	 */
	private List<Point2D> floodFill(int x, int y, List<Point2D> buren) {
		List<Point2D> alleBuren = new ArrayList<Point2D>();
			
		String type = "X";
		
		if (y != 9 && getHuidigSpelbordArray()[y + 1][x] != type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x, y + 1);
			alleBuren.add(coord); 	}
		if (y != 0 && getHuidigSpelbordArray()[y - 1][x] != type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x, y - 1);
			alleBuren.add(coord); 	}
		if (x != 9 && getHuidigSpelbordArray()[y][x + 1] != type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x + 1, y);
			alleBuren.add(coord); 	}
		if (x != 0 && getHuidigSpelbordArray()[y][x - 1] != type) {
			Point2D coord = new Point2D.Double();
			coord.setLocation(x - 1, y);
			alleBuren.add(coord); 	}
		
		List<Point2D> nieuweBuren = new ArrayList<Point2D>();
		for (Point2D p : alleBuren) {
			if (!buren.contains(p)) {
				buren.add(p);
				nieuweBuren.add(p);
			}
		}
		
		List<Point2D> veldenZonderMuren = new ArrayList<Point2D>();
		for (Veld veld : getVelden()) {
			if (!(veld instanceof Muur)) {
				Point2D veldZonderMuur = new Point2D.Double();
				veldZonderMuur.setLocation(veld.getxCord(), veld.getyCord());
				veldenZonderMuren.add(veldZonderMuur);
			}
		}
		
		List<Point2D> overeenkomendeVelden = new ArrayList<Point2D>();
		for (Point2D b : buren) {
			for (Point2D v : veldenZonderMuren) {
				if (v.equals(b)) {
					overeenkomendeVelden.add(v);
				}
			}
		}
		
		veldenZonderMuren.removeAll(overeenkomendeVelden);
		if (veldenZonderMuren.isEmpty()) {
			return buren;
		}
		
		
		for (Point2D p : nieuweBuren) {
			floodFill((int)p.getX(), (int)p.getY(), buren);
		}
		
		return buren;
	}

	
}
