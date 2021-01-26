package cui;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domein.DomeinController;
import resources.Taal;

public class UC7Applicatie {
	// properties
	private final DomeinController dc;
	private Scanner input;
	private UC8Applicatie uc8;
	
	// constructor
	public UC7Applicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
		uc8 = new UC8Applicatie(dc);
	}
	
	public void wijzigSpel() {
		
		String[] eigenSpellen = dc.geefSpellenOpMaker();
		List<String> spelbordNamen = null;
		boolean spelNrFlag = true;
		boolean spelbordNrFlag = true;
		boolean nogEenWijzigingFlag = false;
		boolean wijzigingInvoerFlag;
		int wijzigingKeuze = 0;
		int doorgaanKeuze = 0;
		int spelNr = 0;
		int spelbordNr = 0;
		int teller = 1;
		
		
		// spelkeuze
		System.out.println(Taal.getVertaling("BeschikbareSpellen"));
		try {
			for (String spelNaam : eigenSpellen) {
				System.out.printf("%s %d: %s%n", Taal.getVertaling("Spel"), teller, spelNaam);
				teller++;
			}
		} catch (NullPointerException e) {
			System.err.println(Taal.getVertaling("GeenSpellen"));
		}
		
		// kiest spel
		do {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
				System.out.print(Taal.getVertaling("NumSpelAanpassen") + "\n");
				spelNr = Integer.parseInt(input.nextLine());
				if (spelNr < 1 || spelNr > eigenSpellen.length) {
					throw new IllegalArgumentException();
				}
				spelNrFlag = false;
			} catch (IllegalArgumentException e) {
				System.err.println(Taal.getVertaling("KeuzeMenuForm"));
			} catch (InterruptedException e) {

			}
		} while (spelNrFlag);
		
		dc.kiesSpel(eigenSpellen[--spelNr]);
		
		// spelbordkeuze
		do {
			
			try {
				spelbordNamen = dc.geefNamenSpelborden();
				for (String string : spelbordNamen) {
					System.out.println(string);
				}
				
			} catch (NullPointerException e) {
				System.err.println(Taal.getVertaling("GeenSpellen"));
			}
			
			// kiest spelbord
			do {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
					System.out.print(Taal.getVertaling("NumSpelbordAanpassen") + "\n");
					spelbordNr = Integer.parseInt(input.nextLine());
					if (spelbordNr < 1 || spelbordNr > spelbordNamen.size()) {
						throw new IllegalArgumentException();
					}
					spelbordNrFlag = false;
				} catch (IllegalArgumentException e) {
					System.err.println(Taal.getVertaling("KeuzeMenuForm"));
				} catch (InterruptedException e) {
	
				}
			} while (spelbordNrFlag);
			
			
			do {
				wijzigingInvoerFlag = true;
				try {
					dc.kiesSpelbord(spelbordNr);
					
					System.out.printf(Taal.getVertaling("spelbordAanpassenOfVerwijderen") + "%n");
					wijzigingKeuze = Integer.parseInt(input.nextLine());
					if (wijzigingKeuze < 1 || wijzigingKeuze > 2) {
						throw new IllegalArgumentException();
					}
					if (wijzigingKeuze ==  1) {
						uc8.maakSpelbord();
					} else if (wijzigingKeuze == 2) {
						if (dc.geefAantalSpelborden() > 1) {
							dc.verwijderSpelbord();
							System.out.printf("%s %d %s%n", Taal.getVertaling("Spelbord"), spelbordNr, Taal.getVertaling("verwijderd"));
						} else {
							System.out.printf("%s %d %s%n", Taal.getVertaling("Spelbord"), spelbordNr, Taal.getVertaling("nietVerwijderd"));
						}
					}
					wijzigingInvoerFlag = false;
				} catch (IllegalArgumentException e) {
					System.err.printf(Taal.getVertaling("KeuzeMenuForm"));
				}
			} while (wijzigingInvoerFlag);
			
			//keuze doorgaan
			do {
				wijzigingInvoerFlag = true;
				try {
					System.out.printf(Taal.getVertaling("spelbordWijzigen"));
					doorgaanKeuze = Integer.parseInt(input.nextLine());
					
					if (doorgaanKeuze < 1 || doorgaanKeuze > 2) {
						throw new IllegalArgumentException();
					}
					if (doorgaanKeuze == 1) {
						nogEenWijzigingFlag = true;
					} else if (doorgaanKeuze == 2) {
						nogEenWijzigingFlag = false;
					}
					
					wijzigingInvoerFlag = false;
				} catch (IllegalArgumentException e) {
					System.err.println(Taal.getVertaling("KeuzeMenuForm"));
				}
			} while (wijzigingInvoerFlag);
			
		} while (nogEenWijzigingFlag);
		
		System.out.printf("%s %s %d %s%n", dc.geefSpelNaam(), Taal.getVertaling("heeftSpelborden"), dc.geefAantalSpelborden(), dc.geefAantalSpelborden() == 1 ? Taal.getVertaling("éénSpelbord") : Taal.getVertaling("meerdereSpelborden"));
		
	}
}
