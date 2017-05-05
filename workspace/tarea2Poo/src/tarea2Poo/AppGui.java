package tarea2Poo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * @author BISCUIT
 * GUI implementation for the application
 * Implemented as singleton
 */

public class AppGui implements IConstants{
	
	private static AppGui instance;
	private int windowHeight;
	private JPanel htmlPanel;
	
	
	private AppGui(){
		windowHeight = WINDOW_HEIGHT;
	}
	
	public static AppGui getInstance(){
		if(instance == null){
			instance = new AppGui();
		}
		return instance;
	}
	/**
	 * Starts application
	 */
	public void runGui(){ //initialization of application GUI
		JFrame mainWindow = new JFrame("Fast Text Content");
		mainWindow.setSize(WINDOW_WIDTH, windowHeight);
		mainWindow.setVisible(true);
		mainWindow.setLayout(null);
		mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel searchSpace = new JPanel();
		
		
		
		searchSpace.setBounds(0, 0, WINDOW_WIDTH, 100);
		searchSpace.setBackground(Color.lightGray);
		searchSpace.setLayout(null);
		mainWindow.add(searchSpace);
		
		JLabel textAreaLbl = new JLabel("Search Words:");
		textAreaLbl.setBounds(910, 40, 100, 20);
		searchSpace.add(textAreaLbl);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(1000, 40, 300, 20);
		searchSpace.add(textArea);
		
		htmlPanel = new JPanel();
		htmlPanel.setLayout(null);
		
		
		JScrollPane htmlViewPane = new JScrollPane(htmlPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		htmlViewPane.setBounds(0, 100, WINDOW_WIDTH - 16, 562);
		mainWindow.add(htmlViewPane);
		htmlViewPane.setVisible(true);
		
		JButton searchButton = new JButton("Search...");
		searchButton.setBounds(1050, 65, 150, 30);
		searchButton.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		        requestSearch(textArea.getText());
		        htmlPanel.repaint();
		        htmlViewPane.revalidate();
		    } 
		});
		searchSpace.add(searchButton);
		
		
		
		
		
		mainWindow.repaint();
		
	}
	/**
	 * Procedure used for search button to search for the words in the text area
	 * @param searchWords words to be searched
	 */
	private void requestSearch(String searchWords){
		if(searchWords != ""){
			List<Search> results = null;
			try {
				results = HTMLRequester.makeRequest(searchWords); //assigns the results returned by the http get request
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int YEnd = 0; //marks where the next web viewer needs to go
			htmlPanel.removeAll();
			htmlPanel.setPreferredSize(new Dimension(1920, 2010*results.size()));
			
			for(Search searchObj : results){ //creates a new web viewer and info with every search object returned by HTMLRequester
				JPanel searchPanel = new JPanel();
				searchPanel.setBounds(0, YEnd, 1920, 2000);
				searchPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				JEditorPane htmlView = new JEditorPane();
				htmlView.setEditable(false);
				htmlView.setContentType("text/html");
				htmlView.getDocument().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
				htmlView.setText(searchObj.getHtml());
				htmlView.setPreferredSize(new Dimension(1920, 1900));
				
				JLabel infoLbl = new JLabel("Search Word: "+searchObj.getSearchWord()+" / Time (ms): "+ searchObj.getTime());
				infoLbl.setPreferredSize(new Dimension(250, 20));
				searchPanel.add(infoLbl);
				searchPanel.add(htmlView);
				
				htmlPanel.add(searchPanel);
				YEnd += 1010;
			}
		}
	}

}
