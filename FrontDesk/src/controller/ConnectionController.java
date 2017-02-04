package controller;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ConnectionThread;
import model.GlobalVariables;

public class ConnectionController {

	@FXML ProgressBar progress;
	@FXML Label message;
	private Main main;
	private Stage stage;
	private ConnectionThread thread;
	
	public void setMain(Main main, Stage stage){
		this.main = main;
		this.stage = stage;
		connect();
		thread = new ConnectionThread();
		thread.start();
	}
	
	private void connect(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			float counter = 0;
			int timeOut = 0;
			@Override
			public void run() {
					javafx.application.Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
								try{
									System.out.println(GlobalVariables.connectionFlag);
									counter += 0.1;
									timeOut +=1;
									progress.setProgress(counter);
									if(counter >= 1) {
										counter = 0;
										if(timeOut >=9){
											GlobalVariables.connectionFlag = 2;
										}
									}
									if(GlobalVariables.connectionFlag == 1){
										timer.cancel();
										main.mainWindow();
										stage.close();
									}
									else if(GlobalVariables.connectionFlag == 2){
										timer.cancel();
										Alert errotAlert = new Alert(AlertType.ERROR);
								    		errotAlert.setTitle("error message");
								    		errotAlert.setHeaderText("Error!");
								    		errotAlert.setContentText("Unable to connect to server");
								    		errotAlert.showAndWait();
								    		message.setText("Unable to connect");
									}
								}catch(Exception e){}
						}
					});
			}
		}, 1000,1000);
	}
	
}
