package controller;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import model.Category;
import model.HTTPRequest;
import model.LCDThread;


public class MainWindowController {
	
	@FXML private Label currentPatient;
	@FXML private Label lastPatient;
	@FXML private Label waitingPatients;
	@FXML private Label preclinicPatients;
	@FXML private Button nextButton;
	@FXML private ComboBox<String> catList; 
	
	private String catName="";
	
	public void mainWndow(){
		try{
			catList.getItems().add("Please select category...");
			JSONArray array = HTTPRequest.getCatNames();
			for(int i=0; i<array.size(); i++){
				catList.getItems().add((String) array.get(i));
			}
			catList.getSelectionModel().select(0);
			
			/***********************  timer to update screen data ************************/
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
						javafx.application.Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								if(!catName.isEmpty() && !catName.equals("Please select category...")){
									try{
									Category cat = HTTPRequest.monitor(catName);
									currentPatient.setText(cat.getCurrentPatient());
									lastPatient.setText(cat.getLastPatient());
									waitingPatients.setText(cat.getWaitingPatients()+"");
									preclinicPatients.setText(cat.getPreClinicWaitingPatients()+"");	
									}catch(Exception e){}
								}
								
							}
						});
				}
			}, 3000,3000);
			/*************************  End of timer ********************************/
			
		}catch(Exception e){
			
		}
		
	}
	
	public void updateData() throws IOException{
		if(!catName.isEmpty() && !catName.equals("Please select category...")){
			Category cat = HTTPRequest.monitor(catName);
			currentPatient.setText(cat.getCurrentPatient());
			lastPatient.setText(cat.getLastPatient());
			waitingPatients.setText(cat.getWaitingPatients()+"");
			preclinicPatients.setText(cat.getPreClinicWaitingPatients()+"");	
		}
	}
	
	@FXML
	public void selectcat() throws IOException{
		catName = catList.getSelectionModel().getSelectedItem();
		if(!catName.equals("Please select category...")){
			Category cat = HTTPRequest.monitor(catName);
			currentPatient.setText(cat.getCurrentPatient());
			lastPatient.setText(cat.getLastPatient());
			waitingPatients.setText(cat.getWaitingPatients()+"");
			preclinicPatients.setText(cat.getPreClinicWaitingPatients()+"");	
		}
	}
	
	@FXML
	public void next() throws IOException{
		if(!catName.isEmpty() && !catName.equals("Please select category...")){
			String patientNumber = HTTPRequest.getPatient(catName);
			if(patientNumber.equals("null")){
				Alert errotAlert = new Alert(AlertType.INFORMATION);
		    		errotAlert.setTitle("INFO");
		    		errotAlert.setHeaderText("information!");
		    		errotAlert.setContentText("No more patients!");
		    		errotAlert.showAndWait();
			}
			else{
				this.currentPatient.setText(patientNumber);
				updateData();
				LCDThread lcdThread = new LCDThread(patientNumber);
				lcdThread.start();
			}
		}
		else{
			Alert errotAlert = new Alert(AlertType.ERROR);
	    		errotAlert.setTitle("error message");
	    		errotAlert.setHeaderText("Error!");
	    		errotAlert.setContentText("Please select category first!");
	    		errotAlert.showAndWait();
		}
	}
	
	@FXML
	public void stop() throws IOException{
		LCDThread lcdThread = new LCDThread("OFF");
		lcdThread.start();
	}
	
	@FXML
	public void fastTrack() throws NumberFormatException, IOException{
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Fast Track patient");
		dialog.setContentText("Please enter patient number");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			String x = HTTPRequest.getPatientByNumber(catName, Integer.parseInt(result.get()));
			if(!x.equals("null")){
				currentPatient.setText(x);
				LCDThread lcdThread = new LCDThread(x);
				lcdThread.start();
			}
			else{
				Alert errotAlert = new Alert(AlertType.ERROR);
		    		errotAlert.setTitle("error message");
		    		errotAlert.setHeaderText("Error!");
		    		errotAlert.setContentText("No patient with this number!");
		    		errotAlert.showAndWait();
			}
		}
	}
}	
	
