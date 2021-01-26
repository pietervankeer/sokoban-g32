package domein;

import java.util.ArrayList;
import java.util.List;

import persistentie.SpelMapper;

public class SpelRepository {
	// properties
	private List<Spel> spellen;
	private SpelMapper spelMapper;

	// constructor
	public SpelRepository() {
		spelMapper = new SpelMapper();
	}

	// getters & setters

	public List<Spel> getSpellen(String gebruikersnaam) {
		return spellen;
	}

	// public methods
	/**
	 * Geeft het spel terug dat de ingevoerde naam heeft
	 * 
	 * @param naam
	 * @return het spel op de meegegeven naam
	 */
	public Spel geefSpel(String naam) {
		Spel resultSpel = spelMapper.geefSpel(naam);

		return resultSpel;
	}

	/**
	 * Geeft een String Array van alle spelnamen
	 * 
	 * @return String Array van alle spelnamen
	 */
	public String[] geefAlleSpelNamen() {
		spellen = spelMapper.geefSpellen();

		String[] spellenNamen = new String[spellen.size()];
		for (int i = 0; i < spellenNamen.length; i++) {
			spellenNamen[i] = spellen.get(i).getNaam();
		}
		return spellenNamen;
	}

	// UC5	
	/**
	 * Geeft het aangemaakte spel en de maker van het spel mee aan de spelMapper om het toe te voegen aan de databank
	 * 
	 * @param spel
	 * @param speler
	 */
	public void voegSpelToe(Spel spel, Speler speler) {
		spelMapper.voegSpelToe(spel, speler);
	}
	
	/**
	 * Geeft het spel door aan de mapper om dit te verwijderen uit de databank
	 * 
	 * @param spel
	 */
	public void verwijderSpel(Spel spel) {
		spelMapper.verwijderSpel(spel);
	}
	
	// UC7
	/**
	 * Geeft een String Array van alle namen van spellen van een maker
	 * 
	 * @param naam van de maker
	 * @return String Array van alle namen van spellen van de meegegeven maker
	 */
	public String[] geefAlleSpellenMaker(String naam) {
		spellen = spelMapper.geefSpellen(naam);

		String[] spellenNamen = new String[spellen.size()];
		for (int i = 0; i < spellenNamen.length; i++) {
			spellenNamen[i] = spellen.get(i).getNaam();
		}
		return spellenNamen;
	}

}
