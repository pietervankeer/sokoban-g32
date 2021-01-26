package cui;

import java.util.Scanner;

import domein.DomeinController;
import exception.fouteBewegingException;
import resources.Taal;

public class UC4Applicatie {

	// properties
	private final DomeinController dc;
	private Scanner input;
	private String grid = "";

	// constructors
	public UC4Applicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
	}

	public void voltooiSpel() {
		int aantalZetten = 0;
		char keuze = ' ';
		boolean spelbordVoltooidFlag = true;

		// 2 dimensionale array overlopen en alles in een String steken om af te printen
		dc.initialiseerSpelbord();
		do {
			int tellerKolom = 0;
			int tellerRij = 0;
			//alles in string steken en spelbord printen
			toonSpelbord(dc.geefHuidigSpelbordArray());
			System.out.printf("%s %d", Taal.getVertaling("AantalZetten"), aantalZetten);
			// kiezen welke actie de speler wil doen
			// kiezen welke richting de speler in wil bewegen
			System.out.printf("%n%s%n%n", Taal.getVertaling("BeweegSpeler"));
			System.out.printf("%6s%3s%3s%n%3s%3s%3s%n%n", "[Z]", "[E]", "[R]", "[Q]", "[S]", "[D]");
			System.out.printf("%s%n", Taal.getVertaling("KeuzemenuBeweeg"));

			try {

				keuze = input.nextLine().toLowerCase().charAt(0);
				String keuzeString = String.format("%c", keuze);
				String opties = "zqsdre";

				if (!opties.contains(keuzeString)) {
					throw new IllegalArgumentException();
				}
				dc.verplaatsingMannetje(keuze);
				aantalZetten++;
				
			} catch (IllegalArgumentException e) {
				System.err.printf("%s%n", Taal.getVertaling("KiesZQSD"));
			} catch (fouteBewegingException e) {
				System.err.printf("%s%n", Taal.getVertaling("FouteBeweging"));
			} catch (StringIndexOutOfBoundsException e) {
				System.err.printf("%s%n", Taal.getVertaling("KiesZQSD"));
			}

			// controleren of alle kisten op alle doelen staan
			if (dc.isSpelbordVoltooid()) {
				spelbordVoltooidFlag = false;
			}

		} while (spelbordVoltooidFlag);

		// voltooide spelbord nog eens tonen
		toonSpelbord(dc.geefHuidigSpelbordArray());
		System.out.printf("%s %d", Taal.getVertaling("AantalZetten"), aantalZetten);
	}

	
	
	//adhv spelbord wordt opmaak/(scherm) spelbord getoond
	private void toonSpelbord(String[][] spelbord) {
		grid = "";
		for (String[] rij : spelbord) {
			int i = 0;
			grid += String.format("%n|");
			for (String kolom : rij) {
				grid += String.format("%3s", kolom);
			}
			if (i < 9) {
				grid += String.format("%3s", "|");
			}
			i++;
		}
		for (int i = 0; i < 11; i++) {
			System.out.printf("%3s", "===");
		}
		System.out.print("=");
		System.out.println(grid);
		for (int i = 0; i < 11; i++) {
			System.out.printf("%3s", "===");
		}
		System.out.print("=");
		System.out.println();
		System.out.printf("%12s%s\n", "", "    ==");
		System.out.printf("%12s%s\n", "", "   ====");
		System.out.printf("%12s%s\n", "", "  ======");
		System.out.printf("%12s%s\n", "", "==========");
	}
}
