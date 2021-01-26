package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class InloggenOfRegisterenSchermController extends GridPane {
	@FXML
	private Label lblHeader;
	@FXML
	private Button btnInloggen;
	@FXML
	private Button btnRegisteren;
	private final DomeinController dc;

	public InloggenOfRegisterenSchermController(DomeinController dc) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("inloggenOfRegisterenScherm.fxml"));
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

	private void initialiseerScherm() {
		lblHeader.setText(Taal.getVertaling("inloggenOfRegistrerenHeader"));
		btnInloggen.setText(Taal.getVertaling("aanmeldTekst"));
		btnRegisteren.setText(Taal.getVertaling("registreerTekst"));
	}

	// Event Listener on Button[#btnInloggen].onAction
	@FXML
	public void btnInloggenAfhandeling(ActionEvent event) {
		InlogSchermController is = new InlogSchermController(this.dc);
		Scene scene = new Scene(is);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#btnRegisteren].onAction
	@FXML
	public void btnRegisterenAfhandeling(ActionEvent event) {
		RegistreerSchermController rs = new RegistreerSchermController(dc);
		Scene scene = new Scene(rs, 330, 300);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
