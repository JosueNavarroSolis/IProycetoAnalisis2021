package tarea2Poo;

import java.util.Hashtable;

public class CacheMemory {
	private static CacheMemory instance;
	private Hashtable<String, Search> memory;
	
	private CacheMemory(){
		memory = new Hashtable<String, Search>();
	}
	
	public static CacheMemory getInstance(){
		if(instance == null){
			instance = new CacheMemory();
		}
		return instance;
	}
	
	public Search checkMemory(String searchWords){
		Search tmpSearchObj = null;
		
		tmpSearchObj = memory.get(searchWords);
		
		return tmpSearchObj;
	}
	
	public void addObjToMemory(String searchWords, Search searchObj){
		memory.put(searchWords, searchObj);
	}

}
