package model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PrintThread implements Runnable{

	private Thread thread;
	private String catName, patientNumber,date, time, waiting;
	
	public PrintThread(String name, String catName, String patientNumber, String date, String time, String waiting) {
		super();
		this.catName = catName;
		this.patientNumber = patientNumber;
		this.date = date;
		this.time = time;
		this.waiting = waiting;
		thread = new Thread(this, name);
	}

	@Override
	public void run() {
		try{
			URL obj = new URL(GlobalVariables.PrinterIP+"*"+catName+","+patientNumber+"$"+date+"@"+time+"."+waiting+"+");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", GlobalVariables.USER_AGENT);
			int responseCode = con.getResponseCode();
		}catch(Exception e){}
	}
	
	public void start(){
		thread.start();
	}
}
