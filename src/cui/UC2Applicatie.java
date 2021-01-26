package cui;

import java.util.InputMismatchException;
import java.util.Scanner;

import domein.DomeinController;
import resources.Taal;

public class UC2Applicatie {
	// variabelen
	private UC3Applicatie uc3;
	private final DomeinController dc;
	private Scanner input;

	// constructor
	public UC2Applicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
	}

	// method
	public void registreer() {
		String naam = "", voornaam = "", gebruikersnaam = "", wachtwoord = "";
		boolean registreerFlag = false;
		// login form weergeven
		do {
			registreerFlag = true;
			try {
				System.out.printf("%n%s ", Taal.getVertaling("Voornaam"));
				voornaam = input.nextLine();
				System.out.printf("%n%s ", Taal.getVertaling("Naam"));
				naam = input.nextLine();
				System.out.printf("%n%s ", Taal.getVertaling("Gebruikersnaam2"));
				gebruikersnaam = input.nextLine();
				System.out.printf("%n%s ", Taal.getVertaling("Wachtwoord2"));
				wachtwoord = input.nextLine();
				registreerFlag = true;
				dc.registreer(naam, voornaam, gebruikersnaam, wachtwoord);
			
			} catch (IllegalArgumentException e){
				System.err.printf("%s%n", e.getMessage());
				registreerFlag = false;
			}
		} while (!registreerFlag);

		// speler registeren
		System.out.printf("%n%s %s", Taal.getVertaling("GebruikersnaamAangemeldeSpeler"), dc.geefGebruikersnaam());
		boolean keuzeMenuFlag = true;
		do {
			System.out.printf("%n%s", Taal.getVertaling("Menu"));
			try {
				int keuzeBijMenu = Integer.parseInt(input.nextLine());
				switch (keuzeBijMenu) {
				case 1:
					keuzeMenuFlag = false;
					uc3.kiesSpel();
					break;
				case 2:
					keuzeMenuFlag = false;
					System.exit(0);
					break;
				default:
					throw new IllegalArgumentException();
				}

			} catch (IllegalArgumentException e) {
				System.err.printf("%s%n", Taal.getVertaling("KeuzeMenuForm"));
			}
		} while (keuzeMenuFlag);
	}
}
