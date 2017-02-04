package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;
import model.ClinicWaititngList;
import model.GlobalVariables;
import model.HTTPRequest;
import model.PreClinicWaititngList;

public class MainWindowController {

	@FXML private TableView<Category> catTable;
	@FXML private TableView<PreClinicWaititngList> preClinicTable;
	@FXML private TableView<ClinicWaititngList> clinicTable;
	
	@FXML private TableColumn<Category, String> categoryNameColumn;
	@FXML private TableColumn<Category, String> currentPatientColumn;
	@FXML private TableColumn<Category, Integer> clinicWaitingColumn;
	@FXML private TableColumn<Category, Integer> preClinicWaitingColumn;
	
	@FXML private TableColumn<PreClinicWaititngList, Integer> preClinicWaitingListColumn;
	@FXML private TableColumn<ClinicWaititngList, Integer> clinicWaitingListColumn;

	@FXML private Button cat1Button;
	@FXML private Button cat2Button;
	@FXML private Button cat3Button;
	@FXML private Button statusButton;
	
	private Category tempCat;
	private long selectedPatient;
	private JSONArray array;
	public void mainWndow() throws IOException {
		loadCatTable();
		
		/***********************  timer to update screen data ************************/
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
					javafx.application.Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							
								try{
									loadCatTableData();
									//refreshTables();
								
								}catch(Exception e){}
						}
					});
			}
		}, 2000,2000);
		/*************************  End of timer ********************************/
	}
	
	public void initialize(){

		categoryNameColumn.setCellValueFactory
                (new PropertyValueFactory<Category, String>("catName"));

		currentPatientColumn.setCellValueFactory
                (new PropertyValueFactory<Category, String>("currentPatient"));

		clinicWaitingColumn.setCellValueFactory
        (new PropertyValueFactory<Category, Integer>("waitingPatients"));
		
		preClinicWaitingColumn.setCellValueFactory
        (new PropertyValueFactory<Category, Integer>("preClinicWaitingPatients"));
		
		preClinicWaitingListColumn.setCellValueFactory
        (new PropertyValueFactory<PreClinicWaititngList, Integer>("preClinicWaitingList"));
		
		clinicWaitingListColumn.setCellValueFactory
        (new PropertyValueFactory<ClinicWaititngList, Integer>("clinicWaitingList"));
		

        // selected row
		catTable.getSelectionModel().selectedItemProperty().addListener(
                (obseravble, oldValue, newValue) -> {
					try {
						loadPreClinicTable(newValue);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		
		preClinicTable.getSelectionModel().selectedItemProperty().addListener(
                (obseravble, oldValue, newValue) -> {getSelectedPatient(newValue);});
    }

    private void getSelectedPatient(Object value){
    		if(!(value == null)){
	    		PreClinicWaititngList temp = (PreClinicWaititngList) value;
	    		this.selectedPatient = temp.getPreClinicWaitingList();
	    		statusButton.setText("Change "+this.selectedPatient+" Status");
    		}
    }
    /********************   Load category table data for the first time only! *****************/
    private void loadCatTable() throws IOException{
	    	 this.array = (JSONArray) HTTPRequest.getCatNames();
			ArrayList<Category> list = new ArrayList<Category>();
			for(int i=0;i<array.size();i++){
				list.add(HTTPRequest.monitor(array.get(i).toString()));
			}
			cat1Button.setText(GlobalVariables.cat1);
			cat2Button.setText(GlobalVariables.cat2);
			cat3Button.setText(GlobalVariables.cat3);
			
			ObservableList<Category> catList = FXCollections.observableArrayList(list);
			catTable.setItems(catList);
    }
    /********************   Load clinic waiting tables data periodically by timer *****************/
    private void loadCatTableData() throws IOException{
    	ArrayList<Category> list = new ArrayList<Category>();
		for(int i=0;i<array.size();i++){
			list.add(HTTPRequest.monitor(array.get(i).toString()));
		}
		
		ObservableList<Category> catList = FXCollections.observableArrayList(list);
		catTable.setItems(catList);
    }
    		private void loadPreClinicTable(Object category) throws IOException{
    			if(category != null){
	    	    		Category cat = (Category) category;
	    	    		tempCat = cat;
	    	    		ArrayList<PreClinicWaititngList> preClinic = new ArrayList<>();
	    	    		ArrayList<ClinicWaititngList> clinic = new ArrayList<>();
	    	    		
	    	    		JSONArray pArray = (JSONArray) HTTPRequest.getWaitingList(cat.getCatName(), "p");
	    	    		JSONArray cArray = (JSONArray) HTTPRequest.getWaitingList(cat.getCatName(), "c");
	    	    		
	    	    		for(int i=0; i<pArray.size();i++){
	    	    			preClinic.add(new PreClinicWaititngList((long) pArray.get(i)));
	    	    		}
	    	    		
	    	    		for(int i=0; i<cArray.size();i++){
	    	    			clinic.add(new ClinicWaititngList((long) cArray.get(i)));
	    	    		}
	    	    		
	    	    		ObservableList<PreClinicWaititngList> pList = FXCollections.observableArrayList(preClinic);
	    	    		ObservableList<ClinicWaititngList> cList = FXCollections.observableArrayList(clinic);
	    	    		
	    	    		preClinicTable.setItems(pList);
	    	    		clinicTable.setItems(cList);
	    	    		
    			}
    		}
    		
	/****************************   category 1 add patient  **********************/
	@FXML 
	public void categroy1() throws IOException{
		String catName = cat1Button.getText();
		HTTPRequest.addNewPatient(catName);
		loadCatTable();
	}
	
	/****************************   category 2 add patient  **********************/
	@FXML 
	public void categroy2() throws IOException{
		String catName = cat2Button.getText();
		HTTPRequest.addNewPatient(catName);
		loadCatTable();
	}
	
	/****************************   category 3 add patient  **********************/
	@FXML 
	public void categroy3() throws IOException{
		String catName = cat3Button.getText();
		HTTPRequest.addNewPatient(catName);
		loadCatTable();
	}
	
	/****************************   change patient status **********************/
	@FXML 
	public void changePatientStatus() throws IOException{
		int p = (int) this.selectedPatient;
		HTTPRequest.changePatientStatus(this.tempCat.getCatName(), p);
		loadPreClinicTable(tempCat);
		loadCatTable();
		statusButton.setText("Change Status");
	}
	
	public void refreshTables() throws IOException{
		loadCatTable();
		loadPreClinicTable(tempCat);
	}
	
	/****************************   System reset 
	 * @throws IOException **********************/
	@FXML
	public void reset() throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to Reset the system ");
		Optional<ButtonType> result =  alert.showAndWait();
		if(result.get() == ButtonType.OK){
				HTTPRequest.rst();
			}
		
	}
}
