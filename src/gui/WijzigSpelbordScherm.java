package gui;

import java.awt.geom.Point2D;
import java.util.InputMismatchException;
import java.util.Optional;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class WijzigSpelbordScherm extends GridPane {

	// properties
	DomeinController dc;
	String[][] huidigSpelbord;
	ComboBox<String> cboCords;
	ComboBox<String> cboCords1;
	ComboBox<String> cboActies;
	boolean maakNieuwSpel;
	boolean spelToegevoegd;

	// constructors
	public WijzigSpelbordScherm(DomeinController dc, boolean maakNieuwSpel) {
		this.dc = dc;
		initialiseer();
		toonSpelbord();
		this.maakNieuwSpel = maakNieuwSpel;
	}

	// methodes
	private void initialiseer() {
		dc.initialiseerSpelbordUC6();
		// dc.initialiseerSpelbord();

		// dc.selecteerVolgendSpelbord();
		// knoppen en labels
		Label lblX = new Label("X:");
		Label lblY = new Label("Y:");
		Label lblActie = new Label(Taal.getVertaling("actie"));
		Label lblFiller = new Label(" ");
		// knopjes
		Insets paddingBtn = new Insets(3, 3, 3, 3);
		Button btnSubmit = new Button(Taal.getVertaling("bevestig"));
		Button btnFinish = new Button(Taal.getVertaling("finish"));
		btnSubmit.setOnAction(this::btnSubmitAfhandeling);
		btnFinish.setOnAction(this::btnFinishAfhandeling);

		this.add(lblX, 1, 12);
		this.add(lblY, 1, 13);
		this.add(lblActie, 1, 14);
		this.add(lblFiller, 1, 11);
		this.add(btnSubmit, 1, 15);
		btnSubmit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnSubmit.setPadding(paddingBtn);
		this.add(btnFinish, 1, 16, 3, 1);
		btnFinish.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnFinish.setPadding(paddingBtn);

		// cboCords
		String[] getallen = new String[10];
		for (int i = 0; i < 10; i++) {
			getallen[i] = String.format("%d", i + 1);
		}
		cboCords = new ComboBox<String>();
		cboCords1 = new ComboBox<String>();
		cboActies = new ComboBox<String>();
		ObservableList<String> lijst = FXCollections.observableArrayList(getallen);
		cboCords.setItems(lijst);
		cboCords1.setItems(lijst);
		cboCords.setPromptText(Taal.getVertaling("maakKeuze")); // default text wijzigen.
		cboCords1.setPromptText(Taal.getVertaling("maakKeuze")); // default text wijzigen.
		cboCords.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		cboCords1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.add(cboCords, 2, 12, 2, 1);
		this.add(cboCords1, 2, 13, 2, 1);

		// cboActies
		String[] array = { Taal.getVertaling("plaatsMuur"), Taal.getVertaling("plaatsDoel"),
				Taal.getVertaling("plaatsMannetje"), Taal.getVertaling("plaatsKist"), Taal.getVertaling("veldLeeg") };
		ObservableList<String> actieLijst = FXCollections.observableArrayList(array);
		cboActies.setItems(actieLijst);
		cboActies.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		cboActies.setPromptText(Taal.getVertaling("maakKeuze")); // default text wijzigen.
		this.add(cboActies, 2, 14, 2, 1);
	}

	// spelbord tonen op het scherm
	private void toonSpelbord() {
		huidigSpelbord = dc.geefHuidigSpelbordArrayUC6();
		for (int rij = 0; rij < huidigSpelbord.length; rij++) {
			for (int kolom = 0; kolom < huidigSpelbord[rij].length; kolom++) {
				Label lblCel = new Label();
				Image image = null;
				switch (huidigSpelbord[rij][kolom]) {
				case "K":
					image = new Image(getClass().getResourceAsStream("/images/kistVeld.png"));
					break;
				case "M":
					image = new Image(getClass().getResourceAsStream("/images/spelerVeld.png"));
					break;
				case "X":
					image = new Image(getClass().getResourceAsStream("/images/muurVeld.png"));
					break;
				case "D":
					image = new Image(getClass().getResourceAsStream("/images/doelVeld.png"));
					break;
				case " ":
					image = new Image(getClass().getResourceAsStream("/images/leegVeld.png"));
					break;
				default:
					break;
				}
				if (image != null) {
					lblCel.setGraphic(new ImageView(image));
				}

				this.add(lblCel, kolom, rij);
			}
		}

	}

	// afhandeling
	public void btnSubmitAfhandeling(ActionEvent event) {
		try {
			Point2D positie = new Point2D.Double();
			int x, y, actie;
			x = cboCords.getSelectionModel().getSelectedIndex();
			y = cboCords1.getSelectionModel().getSelectedIndex();
			actie = cboActies.getSelectionModel().getSelectedIndex() + 1;
			positie.setLocation(x, y);
			dc.wijzigVeld(positie, actie);
			toonSpelbord();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(String.format("%s%n", e.getMessage()));
			alert.setTitle("Error");
			alert.show();
		}
	}

	public void btnFinishAfhandeling(ActionEvent event) {
		// alert popup voor bevestiging wijziging
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(Taal.getVertaling("bevestigWijzigingen"));
		alert.setTitle(Taal.getVertaling("bevestig"));
		Optional<ButtonType> result = alert.showAndWait();
		// als het maaknieuwSpel is
		if (result.get() == ButtonType.OK && maakNieuwSpel) {
			// spelbord toevoegen aan spel
			try {
				if (dc.valideerSpelbord()) {
					dc.wijzigSpelbord();
					dc.voegSpelbordToe();
					// toonMenuScherm();
				} else {
					Alert alertErrorValidatie = new Alert(AlertType.ERROR);
					alertErrorValidatie.setContentText(Taal.getVertaling(""));
				}
			} catch (Exception e) {
				Alert alertError = new Alert(AlertType.ERROR);
				alertError.setContentText(e.getMessage());
				alertError.show();
			}
			Alert alertNieuwSpel = new Alert(AlertType.CONFIRMATION);
			alertNieuwSpel.setContentText(Taal.getVertaling("NogNieuwSpelbord"));
			alertNieuwSpel.setTitle(Taal.getVertaling("Spelbord"));
			Optional<ButtonType> resultNieuwSpel = alertNieuwSpel.showAndWait();
			// vraag nog een spelbord toevoegen
			if (resultNieuwSpel.get() == ButtonType.OK) {
				dc.maakSpelbord();
				toonWijzigScherm();
			} else if (resultNieuwSpel.get() == ButtonType.CANCEL) {
				dc.voegSpelToe();
				toonMenuScherm();
			}
			// als het gewoon wijzigspel is
		} else if (result.get() == ButtonType.OK && maakNieuwSpel == false) {
			dc.valideerSpelbord();
			dc.wijzigSpelbord();
			toonMenuScherm();
		}
	}

	// helper methods
	private void toonMenuScherm() {
		MenuSchermController menu = new MenuSchermController(dc);
		Scene scene = new Scene(menu, 300, 300);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	private void toonWijzigScherm() {
		WijzigSpelbordScherm wijzigSpelbordScherm = new WijzigSpelbordScherm(this.dc, true);
		Scene scene = new Scene(wijzigSpelbordScherm, 640, 800);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
