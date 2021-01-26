package cui;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import domein.DomeinController;
import domein.Spel;
import resources.Taal;

public class UC5Applicatie {

	// propeties
	private final DomeinController dc;
	private Scanner input;
	private final UC6Applicatie uc6;

	// constructors
	public UC5Applicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
		this.uc6 = new UC6Applicatie(dc);
	}

	// methoden
	public void maakNieuwSpel() {
		// variabelen
		String uitvoer = "";
		String spelNaam = "";
		boolean maakSpelFlag = true;
		boolean extraSpelbordFlag = false;
		boolean invoerFlag = true;
		boolean spelValidatieFlag = false;

		// spel aanmaken
		System.out.printf("%n%s%n", Taal.getVertaling("maakNieuwSpelHeader"));
		// zolang de speler geen geldige naam ingeeft blijven we dit opnieuw vragen.
		do {
			System.out.printf("%n%s", Taal.getVertaling("geefNaamSpel"));
			try {
				spelNaam = input.nextLine();
				dc.maakSpel(spelNaam);
				maakSpelFlag = false;
			} catch (IllegalArgumentException ex) {
				System.err.printf(ex.getMessage());
			} catch (RuntimeException ex) {
				System.err.printf("%n%s", Taal.getVertaling("bestaatAlSpel"));
			}
		} while (maakSpelFlag);

		// spelborden toevoegen
		// zolang de speler spelborden wil toevoegen.

		do {

			do {
				// als speler de vraag niet juist beantwoord wordt deze opnieuw gesteld.
				if (!spelValidatieFlag) {
					uc6.maakSpelbord();
				}

				// invoer check
				do {
					try {
						System.out.printf("%n%s", Taal.getVertaling("extraSpelbord"));
						int invoer = Integer.parseInt(input.nextLine());

						if (invoer < 1 || invoer > 2) {
							throw new IllegalArgumentException();
						} else if (invoer == 1) {
							extraSpelbordFlag = true;
							invoerFlag = false;
						} else if (invoer == 2) {
							extraSpelbordFlag = false;
							invoerFlag = false;
						} else if (invoer == 2 && dc.geefAantalSpelborden() < 1) {
							System.out.println("spelVerwijderd");
							System.exit(0);
						}
					} catch (IllegalArgumentException e) {
						System.err.println(Taal.getVertaling("invoerException"));
						invoerFlag = true;
					}
				} while (invoerFlag);

			} while (extraSpelbordFlag);

			if (dc.geefAantalSpelborden() > 0) {
				dc.voegSpelToe();
				spelValidatieFlag = false;
			} else {
				System.out.println(Taal.getVertaling("onvoldoendeSpelborden"));
				spelValidatieFlag = true;
			}
		} while (spelValidatieFlag);
		// registratie spel met spelborden is gelukt;
		System.out.printf(Taal.getVertaling("spelGeregistreerd"), spelNaam, dc.geefAantalSpelborden());
	}
}
