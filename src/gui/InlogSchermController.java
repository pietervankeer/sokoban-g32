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

public class InlogSchermController extends GridPane {
	// properties
	@FXML
	private Label lblWelkom;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private PasswordField pwdWachtwoord;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private Button btnLogIn;
	@FXML
	private Button btnCancel;
	
	private final DomeinController dc;

	// constructors
	public InlogSchermController(DomeinController dc) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("inlogScherm.fxml"));
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

	// alle tekstvelden opvullen in de juiste taal.
	private void initialiseerScherm() {
		lblWelkom.setText(Taal.getVertaling("inloggen"));
		lblGebruikersnaam.setText(Taal.getVertaling("Gebruikersnaam"));
		lblWachtwoord.setText(Taal.getVertaling("Wachtwoord"));
		btnLogIn.setText(Taal.getVertaling("inloggen"));
		btnCancel.setText(Taal.getVertaling("cancelGui"));
	}

	// Event Listener on Button[#btnLogIn].onAction
	@FXML
	public void btnLogInAfhandeling(ActionEvent event) {
		try {
			dc.meldAan(txfGebruikersnaam.getText(), pwdWachtwoord.getText());
			startMenuScherm();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.setContentText(Taal.getVertaling("GebruikerNietAangemeld"));
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void btnCancelAfhandeling(ActionEvent event) {
		// naar vorig scherm gaan
		InloggenOfRegisterenSchermController scherm = new InloggenOfRegisterenSchermController(this.dc);
		Scene scene = new Scene(scherm,300,200);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
	
	
	private void startMenuScherm() {
		// naar volgend scherm gaan
		MenuSchermController menu = new MenuSchermController(this.dc);
		Scene scene = new Scene(menu, 300, 300);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
}
