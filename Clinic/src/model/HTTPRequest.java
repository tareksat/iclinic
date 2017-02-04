package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HTTPRequest {
	
	public static JSONArray getCatNames() throws IOException{
		URL obj = new URL(GlobalVar.raspberrypiIP+"load_cat_names");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", GlobalVar.USER_AGENT);  
	
		
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
			
		} else {
			System.out.println("POST request not worked");
		}
		return null;
	}
	/***************************   get category data  ***********************/
	public static Category monitor(String catName) throws IOException{

		URL obj = new URL(GlobalVar.raspberrypiIP+"load_cat_Data/"+catName);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", GlobalVar.USER_AGENT);  
		
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
			
		} else {
			System.out.println("POST request not worked");
		}
		return null;
	}
	/***************************   get patient by number returns patient number or null ***********************/
	public static String getPatientByNumber(String catName, int patientNumber) throws IOException{
		
		URL obj = new URL(GlobalVar.raspberrypiIP+"get_selected_patient");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");  
		con.setRequestProperty("User-Agent", GlobalVar.USER_AGENT);  
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");  
		con.setRequestProperty("Content-Type","application/json");  
		
		// For POST only - START
		JSONObject json = new JSONObject();

		json.put("category_name", catName);
		json.put("patient_number", patientNumber);
		System.out.println(json);
		con.setDoOutput(true);
		
		OutputStream os = con.getOutputStream();
		os.write(json.toJSONString().getBytes());
		os.flush();
		os.close();
				// For POST only - END
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
            String patient  =    String.valueOf(jsonObject.get("patient"));
            return patient;
			       } catch (Exception ex) {
			                  System.err.println("Reciever exception"+ex.getMessage().toString());
			
			       } 
			/***************************************************/
		} else {
			System.out.println("POST request not worked");
		}
		return null;
	}
	
/**********************************   get patient returns patient number or null **************************/
	public static String getPatient(String catName) throws IOException{
		
		URL obj = new URL(GlobalVar.raspberrypiIP+"get_patient/"+catName);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", GlobalVar.USER_AGENT);  
	
		
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
             String patient  =    String.valueOf(jsonObject.get("patient"));
             return patient;
			       } catch (Exception ex) {
			                  System.err.println("Reciever exception"+ex.getMessage().toString());
			
			       } 
			/***************************************************/
			
		} else {
			System.out.println("POST request not worked");
		}
		return null;
	}
}
