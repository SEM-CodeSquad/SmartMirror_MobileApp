package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Post {
	
	private final String USER = "Mozilla/5.0";
	
	public static void main(String[] args) throws Exception {
		
		Post http = new Post();
		System.out.println("Testing - Send HTTP POST request");
		http.sendPost();
	}
	
	private void sendPost() throws Exception{
		URL url = new URL ("localhost:5600");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String data = "PostIt goes here";
		conn.setRequestMethod("POST");
		conn.setRequestProperty("content type",USER);
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + data);
		System.out.println("Response Code : " + responseCode);
	}
	
	
		
		
		
	}
