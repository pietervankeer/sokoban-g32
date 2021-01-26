package main;
	
import domein.DomeinController;
import gui.SpeelSpelScherm;
import gui.taalSelectorSchermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StartupFX extends Application {
	@Override
	public void start(Stage primaryStage) {
		DomeinController dc = new DomeinController();
//		SpeelSpelScherm tsScherm = new SpeelSpelScherm(dc);
//		Scene scene = new Scene(tsScherm,400,400);
//		primaryStage.setScene(scene);
//		primaryStage.show();

		try {
			taalSelectorSchermController root = new taalSelectorSchermController(dc);
			Scene scene = new Scene(root,380,200);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sokoban");
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
