package cui;

import java.awt.geom.Point2D;
import java.util.Scanner;

import domein.DomeinController;
import resources.Taal;

public class UC6Applicatie {
	// properties
	private final DomeinController dc;
	private Scanner input;
	// private final UC7Applicatie uc7;

	public UC6Applicatie(DomeinController dc) {
		this.dc = dc;
		this.input = new Scanner(System.in);
	}

	public void maakSpelbord() {
		boolean nogAanpassen = false;
		boolean wijzigVeldFlag = true;
		boolean onvolledigSpelbordAanpassenFlag = false;
		
		dc.maakSpelbord();
		toonSpelbord(dc.geefHuidigSpelbordArrayUC6());
		do {
			do {
				do {
					try {
						Point2D positie = vraagPositie();
						int actie = vraagActie();
						dc.wijzigVeld(positie, actie);
						wijzigVeldFlag = false;
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				} while (wijzigVeldFlag);
				toonSpelbord(dc.geefHuidigSpelbordArrayUC6());
				nogAanpassen = vraagNogAanpassen();
			} while (nogAanpassen);
			
			if (dc.valideerSpelbord()) {
				dc.voegSpelbordToe();
				System.out.println(Taal.getVertaling("succesvolToegevoegd"));
				onvolledigSpelbordAanpassenFlag = false;
			} else {
				System.out.println(Taal.getVertaling("spelbordOnvolledig") + "\n");
				if (vraagVerwijderen()) {
					System.out.println(Taal.getVertaling("afsluiten"));
					onvolledigSpelbordAanpassenFlag = false;
				} else {
					onvolledigSpelbordAanpassenFlag = true;
				}
			} 
		} while (onvolledigSpelbordAanpassenFlag);
	}

	private boolean vraagVerwijderen() {
		boolean verwijderen = false, isGelukt = true;
		int invoer = 0;
		do {
			try {
				System.out.println(Taal.getVertaling("spelbordAanpassenOfVerwijderen"));
				invoer = Integer.parseInt(input.nextLine());
				if (invoer < 1 || invoer > 2) {
					throw new IllegalArgumentException();
				}
				isGelukt = false;
				if (invoer == 2) {
					verwijderen = true;
				} else {
					verwijderen = false;
				}
			} catch (IllegalArgumentException e) {
				System.err.println(Taal.getVertaling("KeuzeMenuForm"));
			}
		} while (isGelukt);
		
		return verwijderen;
	}

	private boolean vraagNogAanpassen() {
		boolean nogAanpassen = false;
		int invoer;
		boolean isGelukt = true;

		do {
			try {

				System.out.println(Taal.getVertaling("nogAanpassing"));
				invoer = Integer.parseInt(input.nextLine());
				if (invoer < 1 || invoer > 2) {
					throw new IllegalArgumentException();
				} 
				
				if (invoer == 1) {
					nogAanpassen = true;
				} else {
					nogAanpassen = false;
				}
				isGelukt = false;
			} catch (IllegalArgumentException e) {
				System.err.println(Taal.getVertaling("KeuzeMenuForm"));

			}
		} while (isGelukt);
		return nogAanpassen;
	}

	private Point2D vraagPositie() {
		int y, x;
		Point2D positie = new Point2D.Double();
		boolean isGelukt = true;
		do {
			try {
				System.out.println(Taal.getVertaling("rijAanpassen"));
				y = Integer.parseInt(input.nextLine());
				System.out.println(Taal.getVertaling("kolomAanpassen"));
				x = Integer.parseInt(input.nextLine());
				positie.setLocation(x, y);
				isGelukt = false;
			} catch (NumberFormatException e) {
				System.err.println(Taal.getVertaling("FormErrorMisMatch"));
			}
		} while (isGelukt);
		return positie;
	}

	private int vraagActie() {
		int actie = 0;
		boolean isGelukt = true;
		do {
			try {
				System.out.println(Taal.getVertaling("kiesActie"));
				actie = Integer.parseInt(input.nextLine());
				if (actie < 1 || actie > 5) {
					throw new IllegalArgumentException();
				}
				isGelukt = false;
			} catch (IllegalArgumentException e) {
				System.err.println(Taal.getVertaling("KeuzeMenuForm"));
			} 
		} while (isGelukt);
		return actie;
	}

	// spelbord wordt getoond
	private void toonSpelbord(String[][] spelbord) {
		String grid = "";
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
