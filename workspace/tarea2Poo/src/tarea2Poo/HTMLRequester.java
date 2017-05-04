package tarea2Poo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HTMLRequester {
	
	HTMLRequester(){}
	
	public static synchronized List<Search> makeRequest(String searchWords) throws InterruptedException{
		List<Search> results = Collections.synchronizedList(new ArrayList<Search>());
		
		ArrayList<String> searchWordList = new ArrayList<String>(Arrays.asList(searchWords.split(" ")));
		
		TSearch[] threadArray = new TSearch[searchWordList.size()];
		
		int index = 0;
		
		for(String searchWord : searchWordList){
			synchronized(results){
				TSearch threadSearch = new TSearch(results, searchWord);
				threadArray[index] = threadSearch;
				threadSearch.run();
				index++;
			}
				
		}
		
		for(int i = 0; i < threadArray.length; i++){
			threadArray[i].join();
		}
			
			
		
		
		
		
		return results;
	}
}
