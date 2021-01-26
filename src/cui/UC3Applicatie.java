package cui;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domein.DomeinController;
import domein.Spel;
import resources.Taal;

public class UC3Applicatie {
	// properties
	private final DomeinController dc;
	private String[] spellenNamen;
	private Scanner input;
	private String[][] huidigSpelbord;
	private UC4Applicatie uc4;
	private UC5Applicatie uc5;

	// constructors
	public UC3Applicatie(DomeinController dc) {
		this.dc = dc;
		input = new Scanner(System.in);
		uc4 = new UC4Applicatie(dc);
		this.uc5 = new UC5Applicatie(dc);
	}

	// main
	public void kiesSpel() {
		spellenNamen = dc.speelSpel();
		boolean spelNrFlag = true;
		int spelNr = 0;
		int teller = 1;
		System.out.println(Taal.getVertaling("BeschikbareSpellen"));
		try {
			for (String spelNaam : spellenNamen) {
				System.out.printf("%s %d: %s%n", Taal.getVertaling("Spel"), teller, spelNaam);
				teller++;
			}
		} catch (NullPointerException e) {
			System.err.println(Taal.getVertaling("GeenSpellen"));
		}

		do {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
				System.out.print(Taal.getVertaling("NumSpel") + "\n");
				spelNr = Integer.parseInt(input.nextLine());
				if (spelNr < 1 || spelNr > spellenNamen.length) {
					throw new IllegalArgumentException();
				}
				spelNrFlag = false;
			} catch (IllegalArgumentException e) {
				System.err.println(Taal.getVertaling("KeuzeMenuForm"));
			} catch (InterruptedException e) {

			}
		} while (spelNrFlag);

		String spelNaam = spellenNamen[spelNr - 1];
		dc.kiesSpel(spelNaam);
		speelSpel();
	}

	public void speelSpel() {
		boolean eindeSpelFlag = true;
		do {
			uc4.voltooiSpel();
			System.out.printf("%n%s %d%n", Taal.getVertaling("aantalVoltooideSpelborden"),
					dc.geefVerhoudingSpelborden()[1]);
			System.out.printf("%s %d%n", Taal.getVertaling("aantalSpelborden"), dc.geefVerhoudingSpelborden()[0]);
			

			if (dc.geefVerhoudingSpelborden()[0] == dc.geefVerhoudingSpelborden()[1]) {
				eindeSpelFlag = false;
			} else {	
				dc.selecteerVolgendSpelbord();
			}
		} while (eindeSpelFlag);
	}

}
