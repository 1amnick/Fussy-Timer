package com.iamnick.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

public class SettingsManager implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		
		
		if(e.getActionCommand() == "Save"){
			System.out.println("Saving Settings...");
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
