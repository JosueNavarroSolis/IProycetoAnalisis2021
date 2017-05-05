package tarea2Poo;

public class Search {
	private String searchWord;
	private long time;
	private String html;
	
	/**
	 * Class made to group important search information
	 * @param pSearchWord word used for the search
	 * @param pHtml HTML page
	 * @param pTime amount of time that took to create object
	 */
	public Search(String pSearchWord, String pHtml, long pTime){
		searchWord = pSearchWord;
		html = pHtml;
		time = pTime;
	}


	protected String getSearchWord() {
		return searchWord;
	}


	protected long getTime() {
		return time;
	}


	protected void setTime(long time) {
		this.time = time;
	}


	protected String getHtml() {
		return html;
	}
	
	
}
