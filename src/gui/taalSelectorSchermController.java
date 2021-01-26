package gui;

import java.io.IOException;
import java.util.Locale;

import domein.DomeinController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class taalSelectorSchermController extends GridPane {

	// Properties
	@FXML
	private Label taalSelectorLabel;
	@FXML
	private Button btnNederlands;
	@FXML
	private Button btnFrans;
	@FXML
	private Button btnEngels;
	private final DomeinController dc;

	// constructor
	public taalSelectorSchermController(DomeinController dc) {
		this.dc = dc;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("taalSelectorScherm.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Event Listener on Button[#btnNederlands].onMouseClicked
	@FXML
	public void btnNederlandsAfhandeling(MouseEvent event) {
			Taal taal = new Taal(new Locale("nl"));
			startAanmeldRegistreerSelector();
	}

	// Event Listener on Button[#btnFrans].onMouseClicked
	@FXML
	public void btnFransAfhandeling(MouseEvent event) {
		Taal taal = new Taal(new Locale("fr"));
		startAanmeldRegistreerSelector();
	}

	// Event Listener on Button[#btnEngels].onMouseClicked
	@FXML
	public void btnEngelsAfhandeling(MouseEvent event) {
		Taal taal = new Taal(new Locale("en"));
		startAanmeldRegistreerSelector();
	}
	
	
	//helper methods
	private void startAanmeldRegistreerSelector() {
		InloggenOfRegisterenSchermController scherm = new InloggenOfRegisterenSchermController(dc);
		Scene scene = new Scene(scherm);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}
