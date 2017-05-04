package tarea2Poo;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AppGui implements IConstants{
	
	private static AppGui instance;
	private int windowHeight;
	private JPanel searchSpace;
	private JTextArea textArea;
	
	
	private AppGui(){
		windowHeight = WINDOW_HEIGHT;
	}
	
	public static AppGui getInstance(){
		if(instance == null){
			instance = new AppGui();
		}
		return instance;
	}
	
	public void runGui(){
		JFrame mainWindow = new JFrame("Fast Text Content");
		mainWindow.setSize(WINDOW_WIDTH, windowHeight);
		mainWindow.setVisible(true);
		mainWindow.setLayout(null);
		mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		searchSpace = new JPanel();
		
		searchSpace.setBounds(0, 0, WINDOW_WIDTH, 100);
		searchSpace.setBackground(Color.lightGray);
		searchSpace.setLayout(null);
		mainWindow.add(searchSpace);
		
		JLabel textAreaLbl = new JLabel("Search Words:");
		textAreaLbl.setBounds(910, 40, 100, 20);
		searchSpace.add(textAreaLbl);
		
		textArea = new JTextArea();
		textArea.setBounds(1000, 40, 300, 20);
		searchSpace.add(textArea);
		
		JButton searchButton = new JButton("Search...");
		searchButton.setBounds(1050, 65, 150, 30);
		searchSpace.add(searchButton);
		
		
		
	}

}
