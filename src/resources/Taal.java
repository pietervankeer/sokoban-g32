package resources;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasse Taal die zorgt voor de vertalingen in alle verschillende talen
 */
public class Taal {

	/**
	 * Attribuut ResourceBundle resources waar je dan je taal als een locale object insteekt
	 */
	private static ResourceBundle resources;
	
	/**
	 * Constructor die controle doet of de correcte taal ingevoerd wordt
	 * 
	 * @param currentLocale die de ingevoerde taal bevat
	 */
	public Taal(Locale currentLocale) {
		if (!(currentLocale.getLanguage().equals("nl") ||
			currentLocale.getLanguage().equals("fr") ||
			currentLocale.getLanguage().equals("en"))) {
			throw new IllegalArgumentException("Taal bestaat niet - Langue n'existe pas - Language doesn't exist");
		}
		resources = ResourceBundle.getBundle("resources.Sokoban", currentLocale);
	}
	
	/**
	 * Static methode die ervoor zorgt dat je in eender welke klasse de methode Taal.getVertaling() kan oproepen om
	 * daar dan de correcte tekst af te drukken
	 * 
	 * @param keywoord die overeenkomt met de keys uit de property bestanden
	 * @return De juiste vertaling van het keywoord als String, de value dus
	 */
	public static String getVertaling(String keywoord) {
		return resources.getString(keywoord);
	}
}