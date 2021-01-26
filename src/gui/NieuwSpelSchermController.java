package gui;

import java.io.IOException;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class NieuwSpelSchermController extends GridPane {
	// properties
	@FXML
	private Button btnTerug;
	@FXML
	private Label lblSpelNaam;
	@FXML
	private TextField txfSpelNaam;
	@FXML
	private Button btnSubmit;
	@FXML
	private Label lblHeader;
	@FXML
	private Label lblMessage;

	private final DomeinController dc;

	// constructor
	public NieuwSpelSchermController(DomeinController dc) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NieuwSpelScherm.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.dc = dc;
		initialiseer();
	}

	private void initialiseer() {
		lblHeader.setText(Taal.getVertaling("btnMaakSpel"));
		lblSpelNaam.setText(Taal.getVertaling("naamGui"));
		btnSubmit.setText(Taal.getVertaling("bevestig"));
		btnTerug.setText(Taal.getVertaling("cancelGui"));

	}

	// Event Listener on Button[#btnTerug].onAction
	@FXML
	public void btnTerugAfhandeling(ActionEvent event) {
		MenuSchermController menu = new MenuSchermController(this.dc);
		Scene scene = new Scene(menu, 300, 300);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#btnSubmit].onAction
	@FXML
	public void btnSubmitAfhandeling(ActionEvent event) {
		try {
			String spelnaam = txfSpelNaam.getText();
			dc.maakSpel(spelnaam);
			dc.maakSpelbord();
			
			//naar volgend scherm
			WijzigSpelbordScherm wijzigSpelbordScherm = new WijzigSpelbordScherm(this.dc,true);
			Scene scene = new Scene(wijzigSpelbordScherm, 640, 800);
			Stage stage = (Stage) this.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setContentText(e.getMessage());
			error.show();
			lblMessage.setText(e.getMessage());
		}
	}
}
