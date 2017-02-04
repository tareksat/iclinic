package application;
	// front desk //
import java.io.IOException;
import java.util.Locale;

import controller.ConnectionController;
import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.HTTPRequest;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		//mainWindow();
		connect();
	}
	
	public void connect(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/ConnectionView.fxml"));
			AnchorPane pane = loader.load();
			Stage stage = new Stage();
			
			ConnectionController controller = loader.getController();
			controller.setMain(this, stage);
			
			Scene scene = new Scene(pane);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.setTitle("Connecting");
			stage.getIcons().add(new Image("/assets/logo.png"));
			stage.setResizable(false);
			stage.show();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	public void mainWindow(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
			AnchorPane pane = loader.load();
			
			MainWindowController mainWindowController = loader.getController();
			mainWindowController.mainWndow();
			
			Scene scene = new Scene(pane);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Front Desk");
			primaryStage.getIcons().add(new Image("/assets/logo.png"));
			primaryStage.setResizable(false);
			primaryStage.show();
			
			//connect();
			
		}catch(Exception e){
			System.err.println(e.getMessage().toString());
		}
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.CANADA);
		launch(args);
	}
}
