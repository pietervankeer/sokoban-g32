package gui;

import java.io.IOException;
import java.util.Optional;

import domein.DomeinController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import resources.Taal;

public class MenuSchermController extends GridPane {
	@FXML
	private Label lblMenuHeader;
	@FXML
	private Button btnSpeelSpel;
	@FXML
	private Button btnMaakNieuwSpel;
	@FXML
	private Button btnWijzigSpel;
	@FXML
	private Button btnAfsluiten;

	private final DomeinController dc;

	public MenuSchermController(DomeinController dc) {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuScherm.fxml"));
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
		lblMenuHeader.setText(Taal.getVertaling("MenuHeader"));
		btnSpeelSpel.setText(Taal.getVertaling("btnSpeelSpel"));
		btnMaakNieuwSpel.setText(Taal.getVertaling("btnMaakSpel"));
		btnWijzigSpel.setText(Taal.getVertaling("btnWijzigSpel"));
		btnAfsluiten.setText(Taal.getVertaling("btnAfsluiten"));
		if (!dc.isAdmin()) {
			btnMaakNieuwSpel.setVisible(false);
			btnWijzigSpel.setVisible(false);
		}
	}

	// Event Listener on Button[#btnSpeelSpel].onAction
	@FXML
	public void btnSpeelSpelAfhandeling(ActionEvent event) {
		LijstVanSpellenSchermController lijst = new LijstVanSpellenSchermController(this.dc, dc.speelSpel(), false);
		Scene scene = new Scene(lijst);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#btnMaakNieuwSpel].onAction
	@FXML
	public void btnMaakNieuwSpelAfhandeling(ActionEvent event) {
		NieuwSpelSchermController nieuwSpel = new NieuwSpelSchermController(this.dc);
		Scene scene = new Scene(nieuwSpel);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#btnWijzigSpel].onAction
	@FXML
	public void btnWijzigSpelAfhandeling(ActionEvent event) {
		LijstVanSpellenSchermController lijst = new LijstVanSpellenSchermController(this.dc, dc.geefSpellenOpMaker(),
				true);
		Scene scene = new Scene(lijst, 230, 160);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// Event Listener on Button[#btnWijzigSpel].onAction
	@FXML
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
