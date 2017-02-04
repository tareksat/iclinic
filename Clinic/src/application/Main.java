package application;
	// clinic//
import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
		
	@Override
	public void start(Stage primaryStage) {
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
			AnchorPane pane = loader.load();
			
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.mainWndow();
			
			Scene scene = new Scene(pane);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Clinic 2");
			primaryStage.getIcons().add(new Image("/assets/logo.png"));
			primaryStage.setResizable(false);
			primaryStage.show();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
