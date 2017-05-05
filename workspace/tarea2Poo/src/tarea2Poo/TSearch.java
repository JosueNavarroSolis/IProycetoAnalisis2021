package tarea2Poo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TSearch extends Thread {
	
	private final String USER_AGENT = "3.2";
	private List<Search> results;
	private String searchWord;
	
	public TSearch(List<Search> pResults, String pSearchWord){
		results = pResults;
		searchWord = pSearchWord;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long time = 0;
		
		CacheMemory cmemory = CacheMemory.getInstance();
		
		Search searchObj = null;
		
		searchObj = cmemory.checkMemory(searchWord);
		
		if(searchObj != null){
			results.add(searchObj);
			time = System.currentTimeMillis() - startTime;
			searchObj.setTime(time);
		} else {
		
			String url = "http://www.google.com/search?q="+searchWord;
			
			String response = null;
			try {
				response = sendGet(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			time = System.currentTimeMillis() - startTime;
		
			searchObj = new Search(searchWord, response, time);
		
			results.add(searchObj);
			cmemory.addObjToMemory(searchWord, searchObj);
		}

	}
	
	private String sendGet(String url) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();

	}

}
