package domein;

import java.util.List;

import resources.Taal;

/**
 * De speler die de applicatie gebruikt
 */
public class Speler {
	// properties
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean isAdmin = false;
	private String naam;
	private String voornaam;
	private int id;
	/**
	 * Lijst van spellen die de speler aangemaakt heeft
	 */
	private List<Spel> spellen;

	// constructor
	public Speler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor die 6 parameters verwacht (extra parameter id)
	 * 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @param isAdmin
	 * @param naam
	 * @param voornaam
	 * @param id
	 */
	public Speler(String gebruikersnaam, String wachtwoord, boolean isAdmin, String naam, String voornaam, int id) {
		super();
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setIsAdmin(isAdmin);
		setNaam(naam);
		setVoornaam(voornaam);
		setId(id);
	}

	/**
	 * Constructor die 5 parameters verwacht
	 * 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @param isAdmin
	 * @param naam
	 * @param voornaam
	 */
	public Speler(String gebruikersnaam, String wachtwoord, boolean isAdmin, String naam, String voornaam) {
		super();
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setIsAdmin(isAdmin);
		setNaam(naam);
		setVoornaam(voornaam);
	}

	// getters
	public String getGebruikersnaam() {
		return this.gebruikersnaam;
	}

	public String getNaam() {
		return this.naam;
	}

	public String getVoornaam() {
		return this.voornaam;
	}

	public String getWachtwoord() {
		return this.wachtwoord;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}
	public int getId() {
		return this.id;
	}
	// setters
	private void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public final void setGebruikersnaam(String gebruikersnaam) {
		controleerGebruikersnaam(gebruikersnaam);
		this.gebruikersnaam = gebruikersnaam;
	}

	public final void setWachtwoord(String wachtwoord) {
		controleerWachtwoord(wachtwoord);
		this.wachtwoord = wachtwoord;
	}

	public final void setNaam(String naam) {
		controleerNaam(naam);
		this.naam = naam;
	}

	public final void setVoornaam(String voornaam) {
		controleerNaam(voornaam);
		this.voornaam = voornaam;
	}
	private final void setId(int id) {
		this.id = id;
	}
	
	// methods

	// hulpmethodes
	/**
	 * Controleert of gebruikersnaam niet leeg is en langer is dan 7 letters
	 * 
	 * @param gebruikersnaam
	 */
	private void controleerGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam.isBlank()) {
			throw new IllegalArgumentException(Taal.getVertaling("leegError"));
		} else if (gebruikersnaam.length() < 8) {
			throw new IllegalArgumentException(Taal.getVertaling("min8CharError"));
		}
	}
	
	/**
	 * Controleert of het wachtwoord niet leeg is en of het wachtwoord voldoet aan de opgelegde eisen via isCorrectWachtwoord
	 * 
	 * @param wachtwoord
	 */
	private void controleerWachtwoord(String wachtwoord) {
		if (isCorrectWachtwoord(wachtwoord) == false || wachtwoord.isBlank()) {
			throw new IllegalArgumentException(Taal.getVertaling("voorwaardenError"));
		}
	}
	
	/**
	 * Controleert of de naam van de speler alleen uit letters bestaat
	 * 
	 * @param naam
	 */
	private void controleerNaam(String naam) {
		if (naam.matches("\\D*") == false) {
			throw new IllegalArgumentException(Taal.getVertaling("enkelLettersError"));
		}
	}


	/***
	 * hulpmethode voor te controleren of het wachtwoord correcte regex is
	 * 
	 * @param input (meegegeven wachtwoord)
	 * @return boolean die bepaald of gegeven wachtwoord correct is
	 */
	private boolean isCorrectWachtwoord(String input) {
		char currentCharacter;
		boolean numberPresent = false;
		boolean upperCasePresent = false;
		boolean lowerCasePresent = false;
		boolean correctLength = false;

		for (int i = 0; i < input.length(); i++) {
			currentCharacter = input.charAt(i);
			if (Character.isDigit(currentCharacter)) {
				numberPresent = true;
			} else if (Character.isUpperCase(currentCharacter)) {
				upperCasePresent = true;
			} else if (Character.isLowerCase(currentCharacter)) {
				lowerCasePresent = true;
			}

			if (input.length() >= 8) {
				correctLength = true;
			}
		}

		return numberPresent && upperCasePresent && lowerCasePresent && correctLength;
	}
}
