package com.iamnick.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SettingsManager implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		
		
		if(e.getActionCommand() == "Save"){
			System.out.println("Saving Settings...");
			saveSettings();
		}else if(e.getActionCommand() == "Load"){
			System.out.println("Loading Settings...");
			loadSettings();
		}
		
	
	}

	public void loadSettings(){
		try {
			BufferedReader loader = new BufferedReader(new FileReader("timersettings.txt"));
			try {
				Gui.inputMinutes.setText(loader.readLine());
				Gui.inputSeconds.setText(loader.readLine());
				Gui.EndMessage.setText(loader.readLine());
				loader.close();
			} catch (IOException e) {
				//if there is an error in reading the line
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			//nothing to load do nothing
			//e.printStackTrace();
			System.out.println(" Settings file not found.\nCreating File...");
			saveSettings();
		}
		
		
	}
	
	private void saveSettings() {
		try{
			PrintWriter writer = new PrintWriter("timersettings.txt", "UTF-8");
			writer.println(Gui.inputMinutes.getText());
			writer.println(Gui.inputSeconds.getText());
			writer.println(Gui.EndMessage.getText());
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
