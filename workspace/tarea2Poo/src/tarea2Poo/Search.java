package tarea2Poo;

public class Search {
	private String searchWord;
	private long time;
	private String html;
	
	
	public Search(String pSearchWord, String pHtml, long pTime){
		searchWord = pSearchWord;
		html = pHtml;
		time = pTime;
	}


	protected String getSearchWord() {
		return searchWord;
	}


	private void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
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


	private void setHtml(String html) {
		this.html = html;
	}
	
	
}
