package gui;

import java.util.Optional;

import domein.DomeinController;
import exception.fouteBewegingException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import resources.Taal;

public class SpeelSpelScherm extends GridPane {
	// properties
	private DomeinController dc;
	private int aantalZetten;
	private String[][] huidigSpelbord;
	Label lblMessage;
	Label lblAantalZetten;

	// constructor
	public SpeelSpelScherm(DomeinController dc) {
		this.dc = dc;
		aantalZetten = 0;
		initialiseerScherm();
		toonSpelbord();
		initialiseerButtons();
		updateAantalZetten();
	}

	// helper methods
	private void initialiseerScherm() {
		dc.initialiseerSpelbord();
		huidigSpelbord = dc.geefHuidigSpelbordArray();

		// error message
		lblMessage = new Label();
		this.add(lblMessage, 3, 13, 4, 1);
		lblMessage.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lblMessage.setTextFill(Color.web("#FF2D00"));
		lblMessage.setAlignment(Pos.CENTER);
		lblMessage.setWrapText(true);

		// aantal zetten
		lblAantalZetten = new Label();
		this.add(lblAantalZetten, 0, 10, 2, 1);
		lblAantalZetten.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lblAantalZetten.setWrapText(true);
	}

	private void initialiseerButtons() {
		// movement buttons instellen
		Image arrowUp = new Image(getClass().getResourceAsStream("/images/rsz_arrowup.png"));
		Image arrowDown = new Image(getClass().getResourceAsStream("/images/rsz_arrowdown.png"));
		Image arrowLeft = new Image(getClass().getResourceAsStream("/images/rsz_arrowleft.png"));
		Image arrowRight = new Image(getClass().getResourceAsStream("/images/rsz_arrowright.png"));
		Insets paddingBtn = new Insets(10, 10, 10, 10);

		Button btnUp = new Button();
		btnUp.setText(Taal.getVertaling("Up"));
		btnUp.setPadding(paddingBtn);
		this.add(btnUp, 4, 10, 2, 1);
		btnUp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnUp.setOnAction(this::btnUpAfhandeling);

		Button btnDown = new Button();
		btnDown.setText(Taal.getVertaling("Down"));
		btnDown.setPadding(paddingBtn);
		this.add(btnDown, 4, 12, 2, 1);
		btnDown.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnDown.setOnAction(this::btnDownAfhandeling);

		Button btnLeft = new Button();
		btnLeft.setText(Taal.getVertaling("Left"));
		btnLeft.setPadding(paddingBtn);
		this.add(btnLeft, 2, 11, 2, 1);
		btnLeft.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnLeft.setOnAction(this::btnLeftAfhandeling);

		Button btnRight = new Button();
		btnRight.setText(Taal.getVertaling("Right"));
		btnRight.setPadding(paddingBtn);
		this.add(btnRight, 6, 11, 2, 1);
		btnRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnRight.setOnAction(this::btnRightAfhandeling);

		// button voor reset knop
		Button btnReset = new Button();
		btnReset.setText(Taal.getVertaling("Reset"));
		btnReset.setPadding(paddingBtn);
		this.add(btnReset, 8, 10, 2, 1);
		btnReset.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnReset.setOnAction(this::btnResetAfhandeling);

		// button voor reset knop
		Button btnAfsluiten = new Button();
		btnAfsluiten.setText(Taal.getVertaling("Afsluiten"));
		btnAfsluiten.setPadding(paddingBtn);
		this.add(btnAfsluiten, 8, 11, 2, 1);
		btnAfsluiten.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnAfsluiten.setOnAction(this::btnAfsluitenAfhandeling);

	}

	private void updateAantalZetten() {
		if (dc.isSpelbordVoltooid()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(Taal.getVertaling("Hoera"));
			alert.setContentText(Taal.getVertaling("AantalZetten") + aantalZetten);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.CANCEL) {
				try {
					// naar volgend scherm gaan
					dc.selecteerVolgendSpelbord();
					SpeelSpelScherm speelSpel = new SpeelSpelScherm(this.dc);
					Scene scene = new Scene(speelSpel, 640, 840);
					Stage stage = (Stage) this.getScene().getWindow();
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					// gepaste melding geven
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setHeaderText(Taal.getVertaling("error"));
					alertError.setContentText(Taal.getVertaling("geenSpelborden"));
					alertError.show();
					// terug naar menu gaan
					MenuSchermController menu = new MenuSchermController(dc);
					Scene scene = new Scene(menu, 300, 200);
					Stage stage = (Stage) this.getScene().getWindow();
					stage.setScene(scene);
					stage.show();
				}
			}
		}
		lblAantalZetten.setText(String.format("%s: %d", Taal.getVertaling("AantalZetten"), this.aantalZetten));
	}

	private void toonSpelbord() {
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

	/***
	 * movement event listeners
	 * 
	 * @param event
	 */
	// Event Listener on Button[#btnUp].onAction
	public void btnUpAfhandeling(ActionEvent event) {
		try {
			dc.verplaatsingMannetje('z');
			aantalZetten++;
			updateAantalZetten();
			toonSpelbord();
		} catch (IllegalArgumentException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		} catch (fouteBewegingException e) {
			lblMessage.setText(Taal.getVertaling("FouteBeweging"));
		} catch (StringIndexOutOfBoundsException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		}
	}

	// Event Listener on Button[#btnLeft].onAction
	public void btnLeftAfhandeling(ActionEvent event) {
		try {
			dc.verplaatsingMannetje('q');
			aantalZetten++;
			updateAantalZetten();
			toonSpelbord();
		} catch (IllegalArgumentException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		} catch (fouteBewegingException e) {
			lblMessage.setText(Taal.getVertaling("FouteBeweging"));
		} catch (StringIndexOutOfBoundsException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		}
	}

	// Event Listener on Button[#btnRight].onAction
	public void btnRightAfhandeling(ActionEvent event) {
		try {
			dc.verplaatsingMannetje('d');
			aantalZetten++;
			updateAantalZetten();
			toonSpelbord();
		} catch (IllegalArgumentException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		} catch (fouteBewegingException e) {
			lblMessage.setText(Taal.getVertaling("FouteBeweging"));
		} catch (StringIndexOutOfBoundsException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		}
	}

	// Event Listener on Button[#btnDown].onAction
	public void btnDownAfhandeling(ActionEvent event) {
		try {
			dc.verplaatsingMannetje('s');
			aantalZetten++;
			updateAantalZetten();
			toonSpelbord();
		} catch (IllegalArgumentException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		} catch (fouteBewegingException e) {
			lblMessage.setText(Taal.getVertaling("FouteBeweging"));
		} catch (StringIndexOutOfBoundsException e) {
			lblMessage.setText(Taal.getVertaling("KiesZQSD"));
		}
	}

	// Event Listener on Button[#btnReset].onAction
	public void btnResetAfhandeling(ActionEvent event) {
		aantalZetten = 0;
		lblAantalZetten.setText("");
		initialiseerScherm();
		toonSpelbord();
		updateAantalZetten();
	}

	// Event Listener on Button[#btnAfsluiten].onAction
	public void btnAfsluitenAfhandeling(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(Taal.getVertaling("bevestig"));
		alert.setContentText(Taal.getVertaling("stoppen"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK) {
			event.consume();
		} else {
			Platform.exit();
		}
	}
}
