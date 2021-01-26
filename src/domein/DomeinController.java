package domein;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import exception.fouteBewegingException;
import resources.Taal;

/**
 * 
 * DomeinController die communiceert met ui en met domein
 *
 */
public class DomeinController {
	// properties

	private SpelerRepository spelerRepos;
	private SpelRepository spelRepos;
	/**
	 * De speler die aangemeld is en die de applicatie kan gebruiken
	 */
	private Speler aangemeldeSpeler;
	/**
	 * Het huidige spel dat de aangemelde speler geselecteerd heeft om te spelen of
	 * dat aangemaakt is/gewijzigd moet worden
	 */
	private Spel huidigSpel;
	/**
	 * Het huidige spelbord dat aangemaakt of gewijzigd wordt door een admin
	 */
	private Spelbord huidigSpelbord;

	// constructor
	public DomeinController() {
		spelerRepos = new SpelerRepository();
		spelRepos = new SpelRepository();
	}

	// methods
	/**
	 * Zet de speler die overeenkomt met de meegegeven gebruikersnaam en wachtwoord in als aangemeldeSpeler
	 * 
	 * @param gebruikersnaam waarmee de gebruiker wilt inloggen.
	 * @param wachtwoord waarmee de gebruiker wilt inloggen.
	 */
	public void meldAan(String gebruikersnaam, String wachtwoord) {
		Speler gevondenSpeler = spelerRepos.geefSpeler(gebruikersnaam, wachtwoord);
		if (gevondenSpeler != null) {
			setSpeler(gevondenSpeler);
		} else {
			throw new IllegalArgumentException(Taal.getVertaling("geenGevondenSpeler")); // moet nog gewijzigd worden
		}
	}

	/**
	 * Geeft de gebruikersnaam terug van de aangemelde speler
	 * 
	 * @return geeft de gebruikersnaam van de aangemelde speler weer.
	 */
	public String geefGebruikersnaam() {
		return aangemeldeSpeler.getGebruikersnaam();
	}

	/**
	 * Spel de properties aangemeldeSpeler in
	 * 
	 * @param speler is de in te stellen speler.
	 */
	public void setSpeler(Speler speler) {
		this.aangemeldeSpeler = speler;
	}

	/**
	 * Controleert of de gebruiker een admin is
	 * 
	 * @return true als de aangemeldeSpeler adminrechten heeft
	 */
	public boolean isAdmin() {
		return aangemeldeSpeler.getIsAdmin();

	}

	/**
	 * Een nieuwe speler wordt aangemaakt en toegevoegd aan de databank, daarna ook ingesteld als aangemeldeSpeler
	 * 
	 * @param naam van de toe te voegen speler
	 * @param voornaam van de toe te voegen speler
	 * @param gebruikersnaam van de toe te voegen speler
	 * @param wachtwoord van de toe te voegen speler
	 */
	public void registreer(String naam, String voornaam, String gebruikersnaam, String wachtwoord) {
		// exceptions staan in setters maar moeten nog in 3 talen komen.
		Speler nieuweSpeler = new Speler(gebruikersnaam, wachtwoord, false, naam, voornaam);
		spelerRepos.voegSpelerToe(nieuweSpeler);
		aangemeldeSpeler = nieuweSpeler;

	}

	// UC 3
	/**
	 * Deze methode gaat in de klasse spel de methode selecteerVolgendSpelbord()
	 * aanroepen
	 */
	public void selecteerVolgendSpelbord() {
		// volgend spelbord selecteren
		huidigSpel.selecteerVolgendSpelbord();
	}

	/**
	 * Deze methode geeft een 2 dim array terug van ons huidigSpelbord
	 * 
	 * @return 2 dim array van huidigSpelbord
	 */
	public String[][] geefHuidigSpelbordArray() {
		return huidigSpel.geefHuidigSpelbordArray();
	}

	/**
	 * Deze methode een lijst van namen van alle spellen terug
	 * 
	 * @return array van namen van alle spellen
	 */
	public String[] speelSpel() {
		return spelRepos.geefAlleSpelNamen();
	}

	/**
	 * Het attribuut huidigSpel wordt opgevuld met een object van Spel die we uit de
	 * databank halen via de ingegeven naam
	 * 
	 * @param naam van het gekozen spel
	 */
	public void kiesSpel(String naam) {
		huidigSpel = spelRepos.geefSpel(naam);
		// System.out.println(huidigSpel.getNaam() + " " + huidigSpel.getSpeler());
	}

	// UC 4

	/**
	 * InitialiseerSpelbord in huidigSpel oproepen
	 */
	public void initialiseerSpelbord() {
		huidigSpel.initialiseerSpelbord();
	}

	/**
	 * Deze methode verplaatst het mannetje in de 2 dim String array
	 * huidigSpelbordArray
	 * 
	 * @param richting waarin de gebruiker wenst te bewegen
	 * @throws fouteBewegingException
	 */
	public void verplaatsingMannetje(char richting) throws fouteBewegingException {
		huidigSpel.verplaatsMannetje(richting);
	}

	/**
	 * Deze hulpmethode controleert of het spelbord voltooid is
	 * 
	 * @return true als alle doelen een kist op hen hebben staan en dus spelbord
	 *         voltooid is
	 */
	public boolean isSpelbordVoltooid() {
		return huidigSpel.isSpelbordVoltooid();
	}

	// UC5
	/**
	 * Roept getAantalSpelborden van Spel aan
	 * 
	 * @return haalt het aantal spelborden op
	 */
	public int geefAantalSpelborden() {
		return huidigSpel.geefAantalSpelborden();
	}

	/**
	 * Roept de constructor op die enkel propertie naam invult
	 * 
	 * @param naam: de naam van het spel
	 */
	public void maakSpel(String naam) {
		String[] spelNamen = spelRepos.geefAlleSpelNamen();
		for (String string : spelNamen) {
			if (string.equals(naam)) {
				throw new RuntimeException();
			}
		}
		huidigSpel = new Spel(naam);
	}

	/**
	 * Voegt een spel toe aan de databank
	 */
	public void voegSpelToe() {
		spelRepos.voegSpelToe(huidigSpel, aangemeldeSpeler);
	}

	/**
	 * Geeft een int Array terug met 2 getallen, het aantal spelborden en het aantal voltooide spelborden
	 * 
	 * @return int Array met het aantal spelborden en het aantal voltooide spelborden
	 */
	public int[] geefVerhoudingSpelborden() {
		int[] verhoudingSpelborden = new int[2];

		verhoudingSpelborden[0] = huidigSpel.geefAantalSpelborden();
		verhoudingSpelborden[1] = huidigSpel.getAantalVoltooideSpelborden();

		return verhoudingSpelborden;
	}

	// UC 6

	/**
	 * InitialiseerSpelbord in huidigSpelbord oproepen
	 */
	public void initialiseerSpelbordUC6() {
		huidigSpelbord.initialiseerSpelbord();
	}

	/**
	 * Deze methode geeft een 2 dim array terug van ons huidigSpelbord
	 * 
	 * @return 2 dim array van huidigSpelbord
	 */
	public String[][] geefHuidigSpelbordArrayUC6() {
		return huidigSpelbord.getHuidigSpelbordArray();
	}

	/**
	 * Roept de getter in Spelbord aan
	 *
	 * @return geeft de 2 dim array terug
	 */
	public String[][] geefOrigineelSpelbordArray() {
		return huidigSpelbord.getOrigineelSpelbordArray();
	}

	/**
	 * Maakt een nieuw spelbord aan en voegt deze toe aan spel
	 */
	public void maakSpelbord() {
		huidigSpelbord = new Spelbord(new ArrayList<Veld>(), huidigSpel.genereerSpelbordID());
		initialiseerSpelbordUC6();
	}

	/**
	 * Roept wijzigVeld in Spelbord aan
	 * 
	 * @param positie van het te wijzigen veld
	 * @param actie om op het geselecteerde veld uit te voeren
	 */
	public void wijzigVeld(Point2D positie, int actie) {
		huidigSpelbord.wijzigVeld(positie, actie);
	}

	/**
	 * Deze methode controleert of het huidigSpelbord dat we aanpassen voldoet aan
	 * de validaties
	 * 
	 * @return true als het spelbord aan alle validaties voldoet, anders false
	 */
	public boolean valideerSpelbord() {
		return huidigSpelbord.valideerSpelbord();
	}

	/**
	 * Voegt het huidigSpelbord toe aan de lijst met spelborden in Spel
	 */
	public void voegSpelbordToe() {
		huidigSpel.voegSpelbordToe(huidigSpelbord);
	}

	// UC7
	/**
	 * Geeft alle namen van de spellen terug op de naam van een maker
	 * 
	 * @return array van strings van alle spellen op de naam van de aangemelde
	 *         speler
	 */
	public String[] geefSpellenOpMaker() {
		return spelRepos.geefAlleSpellenMaker(aangemeldeSpeler.getGebruikersnaam());
	}

	/**
	 * Geeft alle namen van de spelborden in 1 spel terug
	 * 
	 * @return lijst van strings met alle namen van de spelborden in 1 spel
	 */
	public List<String> geefNamenSpelborden() {
		return huidigSpel.geefNamenSpelborden();
	}

	/**
	 * Stelt huidigSpelbord in op het nieuwe gekozen spelbord met het meegegeven id
	 * 
	 * @param id van het spelbord dat gekozen werd
	 */
	public void kiesSpelbord(int id) {
		huidigSpelbord = huidigSpel.geefSpelbord(id);
	}

	/**
	 * Roept de methode wijzigSpelborden aan in Spel
	 */
	public void wijzigSpelbord() {
		huidigSpel.wijzigSpelborden(huidigSpelbord);
	}

	/**
	 * Roept de methode verwijderSpelbord aan in Spel en geeft het huidigSpelbord
	 * mee
	 */
	public void verwijderSpelbord() {
		huidigSpel.verwijderSpelbord(huidigSpelbord);
	}

	/**
	 * Geeft de spel van het huidige spel terug
	 * 
	 * @return De naam van het huidigSpel
	 */
	public String geefSpelNaam() {
		return huidigSpel.getNaam();
	}

}