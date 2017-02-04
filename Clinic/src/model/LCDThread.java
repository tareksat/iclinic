package model;

import java.net.HttpURLConnection;
import java.net.URL;

public class LCDThread implements Runnable{

	private Thread thread;
	private String data;
	
	public LCDThread(String data) {
		super();
		this.data = data;
		thread = new Thread(this, data);
	}



	@Override
	public void run() {
		try{
			URL obj = new URL(GlobalVar.LCDIP+this.data);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", GlobalVar.USER_AGENT); 
			con.getResponseCode();
		}catch(Exception e){}
		
	}
	
	public void start(){
		thread.start();
	}

}
