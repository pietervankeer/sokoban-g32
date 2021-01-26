package gui;

import java.io.IOException;
import java.util.List;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class LijstVanSpelbordenSchermController extends GridPane {
	// properties
	@FXML
	private ComboBox<String> cboSpelborden;
	@FXML
	private Button btnTerug;
	@FXML
	private Button btnSubmit;
	@FXML
	private Label lblMessage;

	private final DomeinController dc;
	private List<String> spelborden;

	// constructor
	public LijstVanSpelbordenSchermController(DomeinController dc, List<String> spelborden) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LijstVanSpelbordenScherm.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		this.dc = dc;
		this.spelborden = spelborden;
		initialiseer();
	}

	private void initialiseer() {

		// combobox
		ObservableList<String> lijst = FXCollections.observableArrayList(this.spelborden);
		cboSpelborden.setItems(lijst);
		cboSpelborden.setPromptText(Taal.getVertaling("kiesSpelbord")); // default text wijzigen.

		// terug knop
		btnTerug.setText(Taal.getVertaling("cancelGui"));
		// submit knop
		btnSubmit.setText(Taal.getVertaling("bevestig"));
	}

	// afhandeling
	// Event Listener on Button[#btnTerug].onAction
	@FXML
	public void btnTerugAfhandeling(ActionEvent event) {
		LijstVanSpellenSchermController spellenScherm = new LijstVanSpellenSchermController(dc, dc.geefSpellenOpMaker(),
				true);
		Scene scene = new Scene(spellenScherm);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#btnSubmit].onAction
	@FXML
	public void btnSubmitAfhandeling(ActionEvent event) {
		try {
			int index = cboSpelborden.getSelectionModel().getSelectedIndex();
			dc.kiesSpelbord(index + 1);
			startWijzigSpelbordScherm();
		} catch (Exception e) {
			lblMessage.setText(e.getMessage());
		}
	}

	private void startWijzigSpelbordScherm() {
		WijzigSpelbordScherm wijzigSpelbordScherm = new WijzigSpelbordScherm(this.dc,true);
		Scene scene = new Scene(wijzigSpelbordScherm, 640, 800);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
}
