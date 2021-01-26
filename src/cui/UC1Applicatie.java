package cui;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domein.DomeinController;
import resources.Taal;

public class UC1Applicatie {

	// variabelen
	private final DomeinController dc;
	private UC2Applicatie uc2;
	private UC3Applicatie uc3;
	private UC5Applicatie uc5;
	private UC7Applicatie uc7;
	private Scanner input;

	// constructor
	public UC1Applicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
		this.uc2 = new UC2Applicatie(dc);
		this.uc3 = new UC3Applicatie(dc);
		this.uc5 = new UC5Applicatie(dc);
		this.uc7 = new UC7Applicatie(dc);
	}
	
	public static void startUpPrint() {

		String s = "█", x ="█";
        String O = "";

        //47 x 11
        String[][] sokoban = {
                {O, O, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, O, O},
                {O, x, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, x, O},
                {x, O, O, s, s, s, s, s, O, s, s, s, s, s, O, s, O, O, s, s, O, s, s, s, s, s, O, s, s, s, s, O, O, s, s, s, s, s, O, s, O, O, O, s, O, O, x},
                {x, O, O, s, O, O, O, O, O, s, O, O, O, s, O, s, O, s, s, O, O, s, O, O, O, s, O, s, O, O, s, O, O, s, O, O, O, s, O, s, s, O, O, s, O, O, x},
                {x, O, O, s, O, O, O, O, O, s, O, O, O, s, O, s, s, s, O, O, O, s, O, O, O, s, O, s, O, O, s, O, O, s, O, O, O, s, O, s, s, s, O, s, O, O, x},
                {x, O, O, s, s, s, s, s, O, s, O, O, O, s, O, s, s, O, O, O, O, s, O, O, O, s, O, s, s, s, s, s, O, s, s, s, s, s, O, s, O, s, O, s, O, O, x},
                {x, O, O, O, O, O, O, s, O, s, O, O, O, s, O, s, s, s, O, O, O, s, O, O, O, s, O, s, O, O, O, s, O, s, O, O, O, s, O, s, O, s, s, s, O, O, x},
                {x, O, O, O, O, O, O, s, O, s, O, O, O, s, O, s, O, s, s, O, O, s, O, O, O, s, O, s, O, O, O, s, O, s, O, O, O, s, O, s, O, O, s, s, O, O, x},
                {x, O, O, s, s, s, s, s, O, s, s, s, s, s, O, s, O, O, s, s, O, s, s, s, s, s, O, s, s, s, s, s, O, s, O, O, O, s, O, s, O, O, O, s, O, O, x},
                {O, x, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, x, O},
                {O, O, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, O, O}
        };

        try {
            for (String[] rij : sokoban) {
                for (String val : rij) {
                    TimeUnit.MILLISECONDS.sleep(3);
                    System.out.printf("%1s", val);
                }
                System.out.println();
            }
        } catch (InterruptedException e) {
        }
	}
	
	public void kiesTaal() {
		boolean taalFlag = true;

		do {
			
			System.out.printf("%n%26S%s %n%26S%s %n%26S%s%n", "", "▶ Kies uw taal (nl)", "", "▶ Choisissez votre langue (fr)", "", "▶ Choose your language (en)");

			try {
				Taal taal = new Taal(new Locale(input.nextLine()));
				taalFlag = false;
			} catch (IllegalArgumentException e) {
				System.err.printf("%s%n", e.getMessage());
			}
		} while (taalFlag);
		String line = "";
		for(int i = 0; i < 47; i++) {
			line += String.format("%1s", "▬");
		}
		System.out.printf("%s%n", line);

	}

	public void kiesRegistreerAanmelden() {
		boolean keuzeFlag = true;
		do {
			System.out.printf("%s%n", Taal.getVertaling("LoginSigninForm"));
			try {
				int keuze = Integer.parseInt(input.nextLine());
				// input.nextLine();
				if (keuze == 1) {
					keuzeFlag = false;
					meldAan();
				} else if (keuze == 2) {
					keuzeFlag = false;
					uc2.registreer();
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				System.err.printf("%s%n", Taal.getVertaling("FormError"));
			}
		} while (keuzeFlag);

	}

	public void meldAan() {
		boolean meldAanFlag;
		do {
			try {
				System.out.printf("%s: ", Taal.getVertaling("Gebruikersnaam"));
				String gebruikersnaam = input.nextLine();
				System.out.printf("%s: ", Taal.getVertaling("Wachtwoord"));
				String wachtwoord = input.nextLine();
				dc.meldAan(gebruikersnaam, wachtwoord);
				System.out.printf("%s%n", Taal.getVertaling("GebruikerAangemeld"));
				meldAanFlag = false;
			} catch (Exception e) {
				System.out.printf("%s%n", Taal.getVertaling("GebruikerNietAangemeld"));
				meldAanFlag = true;
			}
		} while (meldAanFlag);
		if (dc.isAdmin()) {
			boolean keuzeMenuFlag = true;
			do {
				System.out.printf("%s", Taal.getVertaling("MenuAdmin"));
				try {
					int keuzeBijMenu = Integer.parseInt(input.nextLine());
					switch (keuzeBijMenu) {
					case 1:
						keuzeMenuFlag = false;
						uc3.kiesSpel();
						break;
					case 2:
						keuzeMenuFlag = false;
						uc5.maakNieuwSpel();
						break;
					case 3:
						keuzeMenuFlag = false;
						uc7.wijzigSpel();
						break;
					case 4:
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

		} else {
			boolean keuzeMenuFlag = true;
			do {
				System.out.printf("%s", Taal.getVertaling("Menu"));
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
}
