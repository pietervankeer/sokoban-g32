package gui;

import java.io.IOException;

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

public class LijstVanSpellenSchermController extends GridPane {
	@FXML
	private ComboBox<String> cboSpellen;
	@FXML
	private Button btnSubmit;
	@FXML
	private Button btnTerug;
	@FXML
	private Label lblMessage;
	@FXML
	private Label lblGeenSpellen;

	private final DomeinController dc;
	private String[] spellen;
	private boolean isWijzigSpel;

	public LijstVanSpellenSchermController(DomeinController dc, String[] spellen, boolean wijzigSpel) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LijstVanSpellenScherm.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.dc = dc;
		this.spellen = spellen;
		this.isWijzigSpel = wijzigSpel;
		initialiseer();
		if (cboSpellen.getItems().size()==1) {
			lblMessage.setText(Taal.getVertaling("GeenZelfgemaakteSpellen"));
		}
	}

	private void initialiseer() {
		ObservableList<String> lijst = FXCollections.observableArrayList(spellen);
		cboSpellen.setItems(lijst);
		cboSpellen.setPromptText(Taal.getVertaling("kiesSpel")); // default text wijzigen.

		btnTerug.setText(Taal.getVertaling("cancelGui"));
		btnSubmit.setText(Taal.getVertaling("bevestig"));

	}

	// Event Listener on Button[#btnSubmit].onAction
	@FXML
	public void btnSubmitAfhandeling(ActionEvent event) {
		try {
			int index = cboSpellen.getSelectionModel().getSelectedIndex();
			String spel = this.spellen[index];
			dc.kiesSpel(spel);
			if (isWijzigSpel)
				startWijzigSpelScherm();
			else
				startSpeelSpelScherm();
		} catch (Exception e) {
			lblMessage.setText(e.getMessage());
		}
	}

	@FXML
	public void btnTerugAfhandeling(ActionEvent event) {
		MenuSchermController menu = new MenuSchermController(dc);
		Scene scene = new Scene(menu, 300, 300);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// roept wijzigSpelScherm aan
	private void startWijzigSpelScherm() {
		LijstVanSpelbordenSchermController volgendScherm = new LijstVanSpelbordenSchermController(this.dc,
				dc.geefNamenSpelborden());
		Scene scene = new Scene(volgendScherm);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// roept het speelSpelScherm aan
	private void startSpeelSpelScherm() {
		// naar volgend scherm gaan
		SpeelSpelScherm speelSpel = new SpeelSpelScherm(this.dc);
		Scene scene = new Scene(speelSpel, 640, 840);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}
