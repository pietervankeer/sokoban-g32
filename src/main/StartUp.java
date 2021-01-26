package main;


import cui.UC1Applicatie;
import domein.DomeinController;

public class StartUp {
	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		UC1Applicatie UC1 = new UC1Applicatie(dc);
		UC1Applicatie.startUpPrint();
		UC1.kiesTaal();
		UC1.kiesRegistreerAanmelden();
	}
}
