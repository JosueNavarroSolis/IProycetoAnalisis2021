package tarea2Poo;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppGui implements IConstants{
	
	private static AppGui instance;
	private int windowHeight;
	private JPanel searchSpace;
	
	
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
		
		searchSpace = new JPanel();
		
		searchSpace.setBounds(0, 0, WINDOW_WIDTH, 100);
		searchSpace.setBackground(Color.blue);
		mainWindow.add(searchSpace);
	}

}
