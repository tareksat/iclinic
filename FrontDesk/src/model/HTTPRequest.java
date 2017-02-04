package model;

import java.io.BufferedReader;



import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import model.GlobalVariables;
public class HTTPRequest {
	
	
	public static JSONArray getCatNames() throws IOException{
		URL obj = new URL(GlobalVariables.raspberrypiIP+"load_cat_names");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT);  
	
		
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			/***********  decode json string ********************/
			try {
        	     JSONParser jsonParser = new JSONParser();
        	     Object object = jsonParser.parse(response.toString());
    	         JSONArray array = (JSONArray)object;
    	         
             return array;
			       } catch (Exception ex) {
			                  System.err.println("Reciever exception"+ex.getMessage().toString());
			
			       } 
			/***************************************************/
			
		} 
		return null;
	}
	/***************************   get category data  ***********************/
	public static Category monitor(String catName) throws IOException{

		URL obj = new URL(GlobalVariables.raspberrypiIP+"load_cat_Data/"+catName);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT);  
		
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			/***********  decode json string ********************/
			try {
        	     JSONParser jsonParser = new JSONParser();
  	         JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
             String currentPatient   =    String.valueOf(jsonObject.get("current_patient"));
             String lastPatient      =    String.valueOf(jsonObject.get("last_patient"));
             int waitingPatients     =   Integer.parseInt(String.valueOf(jsonObject.get("waiting_c_list")));
             int preClinicWaitingPatients     =   Integer.parseInt(String.valueOf(jsonObject.get("waiting_p_list")));
             return new Category(catName, currentPatient, lastPatient, waitingPatients, preClinicWaitingPatients);
			       } catch (Exception ex) {
			                  System.err.println("Reciever exception"+ex.getMessage().toString());
			
			       } 
			/***************************************************/
			
		} 
		return null;
	}
	
	/***************************   get waiting lists  ***********************/
	public static JSONArray getWaitingList(String catName, String type) throws IOException{
		URL obj = new URL(GlobalVariables.raspberrypiIP+"load_waiting_lists/"+catName);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT);  
	
		
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			/***********  decode json string ********************/
			try {
        	     JSONParser jsonParser = new JSONParser();
  	         JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
  	         Object pListObject   =    jsonObject.get("P-List");
  	         Object cListObject   =    jsonObject.get("C-List");
  	         JSONArray pList = (JSONArray)pListObject;
  	         JSONArray cList = (JSONArray)cListObject;
  	         
  	         if(type.equals("p")){
  	        	 	return pList;
  	         }
  	         else{
  	        	 return cList;
  	         }
  	       
			       } catch (Exception ex) {
			                  System.err.println("Reciever exception"+ex.getMessage().toString());
			
			       } 
			
			/****************************************************************/
			
		} 
		return null;
	}
	
	/***************************   Add new Patient  ***************************/
		public static String addNewPatient(String catName) throws IOException{
			URL obj = new URL(GlobalVariables.raspberrypiIP+"add_new_patient/"+catName);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");  
			con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT);  
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");  
			con.setRequestProperty("Content-Type","application/json");  
			
			Date cdate = new Date();
			String date = cdate.getDate()+"/"+(cdate.getMonth()+1)+"/"+(cdate.getYear()+1900);
			String time = cdate.getHours()+":"+cdate.getMinutes()+":"+cdate.getSeconds();
			
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				/***********  decode json string ********************/
				try {
	       	     JSONParser jsonParser = new JSONParser();
	 	         JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
	 	         String patientNum  		=    String.valueOf(jsonObject.get("last_patient"));
	 	         String waitingPatients  =    String.valueOf(jsonObject.get("waiting_patients"));
	 	         
	 	         PrintThread printThread = new PrintThread(patientNum, catName, patientNum, date, time, waitingPatients);
	 	         printThread.start();
	            return null;
				       } catch (Exception ex) {
				                  System.err.println("Reciever exception"+ex.getMessage().toString());
				
				       } 
				/***************************************************/
			} 
			return null;
		}
	/***************************   change patient status  ***************************/
		public static String changePatientStatus(String catName, int patientNumber) throws IOException{
			
			URL obj = new URL(GlobalVariables.raspberrypiIP+"change_patient_status");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");  
			con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT);  
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");  
			con.setRequestProperty("Content-Type","application/json");  
			
			JSONObject json = new JSONObject();

			json.put("category", catName);
			json.put("patient_number", patientNumber);
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(json.toJSONString().getBytes());
			os.flush();
			os.close();
			
			int responseCode = con.getResponseCode();
			return null;
		}
		
	/*****************************  reset *************************/
		public static void rst() throws IOException{
				URL obj = new URL(GlobalVariables.raspberrypiIP+"reset");
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT); 
				 con.getResponseCode();
			}
}
