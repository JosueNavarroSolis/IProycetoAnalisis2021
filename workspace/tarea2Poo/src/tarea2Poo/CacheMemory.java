package tarea2Poo;

import java.util.Hashtable;

/**
 * 
 * @author BISCUIT
 * Simulated cache memory implemented as singleton
 */

public class CacheMemory {
	private static CacheMemory instance;
	private Hashtable<String, Search> memory; //hash table that simulates cache memory
	
	private CacheMemory(){
		memory = new Hashtable<String, Search>();
	}
	
	public static CacheMemory getInstance(){
		if(instance == null){
			instance = new CacheMemory();
		}
		return instance;
	}
	
	/**
	 * Checks if the search word has already been used
	 * @param searchWords Search word used
	 * @return Search object if mapped, else returns null
	 */
	public Search checkMemory(String searchWords){
		Search tmpSearchObj = null;
		
		tmpSearchObj = memory.get(searchWords);
		
		return tmpSearchObj;
	}
	
	/**
	 * Maps search object in hash table
	 * @param searchWords key to map in hash table
	 * @param searchObj object to be mapped
	 */
	public void addObjToMemory(String searchWords, Search searchObj){
		memory.put(searchWords, searchObj);
	}

}
