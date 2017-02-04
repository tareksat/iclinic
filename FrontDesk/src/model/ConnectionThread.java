package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class ConnectionThread implements Runnable{

	private Thread thread;
	
	
	public ConnectionThread() {
		thread = new Thread(this, "Connection");
	}


	@Override
	public void run() {
		try{
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
	    	         
	    	         GlobalVariables.cat1 = array.get(0).toString();
	    	         GlobalVariables.cat2 = array.get(1).toString();
	    	         GlobalVariables.cat3 = array.get(2).toString();
	    	         
				       } catch (Exception ex) {
				                  System.err.println("Reciever exception"+ex.getMessage().toString());
				
				       } 
				/***************************************************/
				GlobalVariables.connectionFlag = 1; // connected
				
			} 
			else{
				GlobalVariables.connectionFlag = 2; // unable to connect
			}
		}catch(Exception e){
			System.out.println(e.getMessage().toString());
		}

	}
	
	public void start(){
		thread.start();
	}

}
