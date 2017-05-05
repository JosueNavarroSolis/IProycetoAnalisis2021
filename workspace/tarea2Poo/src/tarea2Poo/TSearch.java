package tarea2Poo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 
 * @author BISCUIT
 * Thread class that sends request, adds Search object to search words list and measures response time for each response
 */

public class TSearch extends Thread {
	
	private final String USER_AGENT = "3.0";
	private List<Search> results; //ref to results list
	private String searchWord;
	
	public TSearch(List<Search> pResults, String pSearchWord){
		results = pResults;
		searchWord = pSearchWord;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis(); //timer start
		long time = 0;
		
		CacheMemory cmemory = CacheMemory.getInstance(); //instance of cache memory
		
		Search searchObj = null; //temporary search object
		
		searchObj = cmemory.checkMemory(searchWord); //checks cache memory for already created objects
		
		if(searchObj != null){ //if true gets search object from cache memory
			results.add(searchObj);
			time = System.currentTimeMillis() - startTime;
			searchObj.setTime(time);
		} else {
		
			String url = "http://www.google.com/search?q="+searchWord; //url to be requested
			
			String response = null; //html response
			try {
				response = sendGet(url); //gives response the html value
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			time = System.currentTimeMillis() - startTime; //time stop
		
			searchObj = new Search(searchWord, response, time); //creates Search object
		
			results.add(searchObj); //adds Search object to synchronized list
			cmemory.addObjToMemory(searchWord, searchObj); //maps object in cache memory
		}

	}
	
	/**
	 * Sends get request to an url and returns server response.
	 * @param url server address
	 * @return string with html response
	 * @throws IOException
	 */
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
