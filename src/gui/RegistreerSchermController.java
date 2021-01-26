package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class RegistreerSchermController extends GridPane{
	//Properties
	@FXML
	private Label lblVoornaam;
	@FXML
	private Label lblNaam;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private Button btnRegistreer;
	@FXML
	private TextField tfVoornaam;
	@FXML
	private TextField tfNaam;
	@FXML
	private TextField tfGebruikersnaam;
	@FXML
	private PasswordField tfWachtwoord;
	@FXML
	private Label lblHeader;
	@FXML
	private Button btnCancel;
	
	private final DomeinController dc;
	
	//Constructor
	public RegistreerSchermController(DomeinController dc) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistreerScherm.fxml"));
		loader.setController(this);
		loader.setRoot(this);

		this.dc = dc;
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		initialiseerScherm();
	}

	//action event voor registreer button
	@FXML
	public void btnRegistreerAfhandeling(ActionEvent event) {
		try {
			dc.registreer(tfNaam.getText(), tfVoornaam.getText(), tfGebruikersnaam.getText(), tfWachtwoord.getText());		
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText(Taal.getVertaling("RegistratieGelukt"));
			alert.showAndWait();
			
		} catch (IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(Taal.getVertaling("RegistratieMislukt")+" "+e.getMessage());
			alert.showAndWait();
		}
	}
	
	@FXML
	public void btnCancelAfhandeling(ActionEvent event) {
		// naar vorig scherm gaan
		InloggenOfRegisterenSchermController scherm = new InloggenOfRegisterenSchermController(this.dc);
		Scene scene = new Scene(scherm,300,200);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
	
	private void initialiseerScherm() {
		lblHeader.setText(Taal.getVertaling("headerGui"));
		lblVoornaam.setText(Taal.getVertaling("voornaamGui"));
		lblNaam.setText(Taal.getVertaling("naamGui"));
		lblGebruikersnaam.setText(Taal.getVertaling("gebruikersnaamGui"));
		lblWachtwoord.setText(Taal.getVertaling("wachtwoordGui"));
		btnRegistreer.setText(Taal.getVertaling("registreerTekst"));
		btnCancel.setText(Taal.getVertaling("cancelGui"));
	}

}
