package domein;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import exception.fouteBewegingException;
import persistentie.VeldMapper;
import resources.Taal;

/**
 * Het spel dat gespeeld wordt
 */
public class Spel {
	
	// properties
	/**
	 * Het huidigeSpelbord dat de speler aan het spelen is in een spel
	 */
	private Spelbord huidigSpelbord;

	private int spelID;

	private String naam;
	private int aantalVoltooideSpelborden;
	/**
	 * Alle spelborden die het spel bevat
	 */
	private List<Spelbord> spelborden;
	/**
	 * Spel is de repository van spelbord dus spelbordMapper zit in Spel
	 */
	private VeldMapper spelbordMapper;

	// constructors
	/**
	 * Constructor waarin een spel aangemaakt wordt en alle spelborden worden opgehaald
	 * 
	 * @param naam van het spel
	 */
	public Spel(String naam) {
		setNaam(naam);
		spelbordMapper = new VeldMapper();
		spelborden = spelbordMapper.geefSpelborden(this.getNaam());
		stelHuidigSpelbordIn();
		setAantalVoltooideSpelborden(bepaalVoltooideSpelborden());
	}
	
	/**
	 * Constructor die de constructor met 1 parameter aanroept en dan het ID instelt van het spel.
	 * 
	 * @param naam van het spel
	 * @param spelID van het spel
	 */
	public Spel(String naam, int spelID) {
		this(naam);
		setSpelID(spelID);
	}

	// getters & setters
	public int getSpelID() {
		return spelID;
	}
	
	public void setSpelID(int spelID) {
		this.spelID = spelID;
	}
	
	public List<Spelbord> getSpelborden() {
		return spelborden;
	}

	public void setSpelborden(List<Spelbord> spelborden) {
		this.spelborden = spelborden;
	}

	public String getNaam() {
		return naam;
	}

	private void setNaam(String naam) {
		if (naam.isEmpty())
			throw new IllegalArgumentException(Taal.getVertaling("geenNaamIngevuld"));
		else if (bevatSpatie(naam))
			throw new IllegalArgumentException(Taal.getVertaling("naamBevatSpatie"));
		else
			this.naam = naam;
	}

	public int getAantalVoltooideSpelborden() {
		return this.aantalVoltooideSpelborden;
	}

	private void setAantalVoltooideSpelborden(int aantal) {
		aantalVoltooideSpelborden = aantal;
	}
	
	public Spelbord getHuidigSpelbord() {
		return this.huidigSpelbord;
	}
	
	private void setHuidigSpelbord(Spelbord huidigSpelbord) {
		this.huidigSpelbord = huidigSpelbord;
	}
	
	/**
	 * Geeft het totale aantal spelborden in de array spelborden weer
	 * 
	 * @return totaal aantal spelborden
	 */
	public int geefAantalSpelborden() {
		return spelborden.size();
	}


	// methoden

	/**
	 * Voegt een spelbord toe aan de lijst van spelborden
	 * 
	 * @param het spelbord sp dat toegevoegd moet worden
	 */
	public void addSpelbord(Spelbord sp) {
		spelborden.add(sp);
	}
	
	/**
	 * Geeft een 2 dim String Array van het huidigSpelbord terug
	 * 
	 * @return 2 dim String Array
	 */
	public String[][] geefHuidigSpelbordArray() {
		return huidigSpelbord.getHuidigSpelbordArray();
	}
	
	/**
	 * De 2 dim String huidigSpelbordArray opvullen via initialiseerSpelbord in Spelbord
	 */
	public void initialiseerSpelbord() {
		huidigSpelbord.initialiseerSpelbord();
	}
	
	/**
	 * Deze methode verplaatst het mannetje in de 2 dim String array huidigSpelbordArray
	 * 
	 * @param richting waarin de gebruiker wenst te bewegen
	 * @throws fouteBewegingException 
	 */
	public void verplaatsMannetje(char richting) throws fouteBewegingException {
		huidigSpelbord.verplaatsMannetje(richting);
		
	}
	
	/**
	 * Deze hulpmethode controleert of het spelbord voltooid is
	 * 
	 * @return true als alle doelen een kist op hen hebben staan en er 1 mannetje is
	 */
	public boolean isSpelbordVoltooid() {
		if (huidigSpelbord.isSpelbordVoltooid() ) {
			setAantalVoltooideSpelborden(getAantalVoltooideSpelborden() + 1); 
			return true;
		} else {
			return false;
		}

	}
	
	
	
	/**
	 * Zet het attribuut huidigSpelbord in op het eerste spelbord uit de lijst van spelborden als deze niet leeg is
	 */
	private void stelHuidigSpelbordIn() {
		if (!spelborden.isEmpty()) {
			huidigSpelbord = spelborden.get(0);
		}
	}

	/**
	 * haalt het volgend spelbord op uit de lijst van spelborden en steekt dat
	 * spelbord in huidigSpelbord.
	 */
	public void selecteerVolgendSpelbord() {
		this.huidigSpelbord = geefSpelbord(huidigSpelbord.getId() + 1);
		// misschien nog outofboundsexception
	}


	// spelbordrepos
	/**
	 * Geeft het spelbord terug dat werd gevraagd via het meegegeven id
	 * 
	 * @param id dat werd meegegeven
	 * @return het spelbord dat bij het meegegeven id past
	 */
	public Spelbord geefSpelbord(int id) {
		Spelbord gevraagdSpelbord = null;
		for (Spelbord sb : spelborden) {
			if (sb.getId() == id) {
				gevraagdSpelbord = sb;
			}
		}
		return gevraagdSpelbord;
	}

	// hulpmethodes
	/**
	 * Geeft het aantal voltooide spelborden terug
	 * 
	 * @return aantal voltooide spelborden
	 */
	private int bepaalVoltooideSpelborden() {
		int aantalVoltooide = 0;
		for (int i = 0; i < spelborden.size(); i++) {
			if (spelborden.get(i).equals(huidigSpelbord)) {
				aantalVoltooide = i;
			}
		}
		return aantalVoltooide;
	}

	// UC 5
	/**
	 * deze methode dient om te kijken of er spaties in een string zitten
	 * 
	 * @param str: de string waar mogelijks spaties inzitten
	 * @return of er al dan niet spaties in de string zitten.
	 */
	private boolean bevatSpatie(String str) {
		boolean spatie = false;
		if (str.split(" ").length > 1 || str.isBlank()) {
			spatie = true;
		}
		return spatie;
	}

	// UC 6

	/**
	 * Roept de methode geefGrootsteSpelbordID op en voegt er 1 aan toe, dit is het nieuwe getal dat meegegeven wordt
	 * 
	 * @return het gegenereerde spelbordID
	 */
	public int genereerSpelbordID() {
		return geefGrootsteSpelbordID() + 1;
	}

	/**
	 * Geeft het grootste bestaande spelbordID terug van de spelborden in dit spel
	 * 
	 * @return grootste bestaande spelbordID
	 */
	private int geefGrootsteSpelbordID() {
		int hoogsteID = 0;
		for (Spelbord sb : spelborden) {
			if (sb.getId() > hoogsteID)
				hoogsteID = sb.getId();
		}
		return hoogsteID;
	}
	
	/**
	 * Roept de methode setSpelborden op om het nieuwe spelbord aan toe te voegen en stelt direct het huidigSpelbord in op het nieuwe meegegeven spelbord
	 * 
	 * @param het spelbord sp dat toegevoegd moet worden
	 */
	public void voegSpelbordToe(Spelbord sp) {
		List<Spelbord> nieuweLijst = getSpelborden();
		nieuweLijst.add(sp);
		setSpelborden(nieuweLijst);
		setHuidigSpelbord(sp);
	}
	
	// UC 7
	/**
	 * Geeft een lijst terug met alle namen van de spelborden in het spel
	 * 
	 * @return lijst van strings met alle namen van de spelborden
	 */
	public List<String> geefNamenSpelborden() {
		List<String> res = new ArrayList<String>();
		for (int i = 1; i <= spelborden.size(); i++) {
			res.add(String.format("%s %d", Taal.getVertaling("Spelbord"), i));
		}
		return res;
	}
	
	/**
	 * Roept de methode wijzigSpelbord op in de spelbordMapper om alle wijzigingen aan de spelborden te registeren in de databank
	 */
	public void wijzigSpelborden(Spelbord spelbord) {
		spelbordMapper.wijzigSpelbord(spelbord, getSpelID(), getNaam());
	}
	
	/**
	 * Gaat het meegegeven spelbord verwijderen uit de databank en uit de lijst van spelborden in het spel
	 * 
	 * @param het spelbord dat moet verwijderd worden uit het spel zijn lijst met spelborden en uit de databank
	 */
	public void verwijderSpelbord(Spelbord spelbord) {
		Spelbord teVerwijderen = null;
		for (Spelbord sp : spelborden) {
			if (sp.getId() == spelbord.getId()) {
				teVerwijderen = sp;
			}
		}
		spelborden.remove(teVerwijderen);
		spelbordMapper.verwijderSpelbord(spelbord.getId(), getNaam());
	}

}
