package domein;

import persistentie.SpelerMapper;

public class SpelerRepository {
	//properties
	private final SpelerMapper spelerMapper;
	
	// constructor
	public SpelerRepository() {
		spelerMapper = new SpelerMapper();
	}

	// methods
	/**
	 * Geeft de speler met de geven gebruikersnaam en wachtwoord, hiervoor roept het de spelerMapper aan
	 * 
	 * @param gebruikersnaam: de gebruikersnaam de speler die wilt inloggen
	 * @param wachtwoord: het wachtwoord van de speler die wilt inloggen
	 * @return De speler als die bestaat met dat wachtwoord, anders null
	 */
	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
		Speler s = spelerMapper.geefSpeler(gebruikersnaam);
		if (s.getWachtwoord().equals(wachtwoord)) {
			return s;
		}

		return null;
	}
	
	/**
	 * Geeft de meegegeven speler mee aan de spelerMapper die gaat controleren of ze uniek is
	 * 
	 * @param speler de speler die moet toegevoegd worden.
	 */
	public void voegSpelerToe(Speler toeTeVoegenSpeler) {
		spelerMapper.controleerUniekeGebruikersnaam(toeTeVoegenSpeler);
	}
}
