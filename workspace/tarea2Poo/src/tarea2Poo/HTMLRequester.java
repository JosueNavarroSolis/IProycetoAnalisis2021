package tarea2Poo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HTMLRequester {
	
	HTMLRequester(){}
	
	/**
	 * Makes threads for HTTP request and waits for all of them to finish
	 * @param searchWords search words to be requested
	 * @return returns a synchronized thread safe list containing all the requests made to the server
	 * @throws InterruptedException
	 */
	public static synchronized List<Search> makeRequest(String searchWords) throws InterruptedException{
		List<Search> results = Collections.synchronizedList(new ArrayList<Search>());
		
		ArrayList<String> searchWordList = new ArrayList<String>(Arrays.asList(searchWords.split(" "))); //splits word search string into substrings of individual searchwords
		
		TSearch[] threadArray = new TSearch[searchWordList.size()]; //array consisting of all the threads created 
		
		
		int index = 0; //index for current thread
		
		for(String searchWord : searchWordList){
			synchronized(results){
				TSearch threadSearch = new TSearch(results, searchWord); //creates thread object with ref to list
				threadArray[index] = threadSearch; //adds thread to array
				threadSearch.run();
				index++;
			}
				
		}
		
		for(int i = 0; i < threadArray.length; i++){
			threadArray[i].join(); //waits for all threads to die
		}
		
		return results;
	}
}
