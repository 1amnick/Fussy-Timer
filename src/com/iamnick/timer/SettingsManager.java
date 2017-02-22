package com.iamnick.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class SettingsManager implements ActionListener {

	String OAUTH = null;
	String Channel = null;
	String Account = null;



	@Override
	public void actionPerformed(ActionEvent e) {
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



	public void loadChatBotSettings(){
		try {
			BufferedReader loader = new BufferedReader(new FileReader("timertchatsettings.txt"));
			try {
				Channel = (loader.readLine());
				Account = (loader.readLine());
				OAUTH  = (loader.readLine());
				loader.close();
			} catch (IOException e) {
				//if there is an error in reading the line
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			//nothing to load lets make a baby
			//e.printStackTrace();
			System.out.println("Twitch file not found.\nCreating File...");
			try{
				PrintWriter writer = new PrintWriter("timertchatsettings.txt", "UTF-8");
				writer.println("#channel");
				writer.println("account");
				writer.println("oauth:blahsomelettersandnumbers");
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				//it was a miscarriage
			}


		}


	}
	public static String checkForUpdates(){
		URL url;
		try {
			url = new URL("https://raw.githubusercontent.com/1amnick/Fussy-Timer/master/version.txt");
			
			double latest = 1337.1337;
			@SuppressWarnings("resource")
			Scanner s = new Scanner(url.openStream()).useLocale(Locale.US);
			Thread.sleep(100);
			latest = s.nextDouble();
			Thread.sleep(100);
			s.close();
			if(latest > FussyTimer.versionNumber){
				String yes = "There is an update! You have v" + FussyTimer.versionNumber + " and the latest is v"+latest +" Go ask @1amNick for a copy or download @ https://git.io/vDF6z";
				return yes;
			}else {
				return "You have the latest version SeemsGood";
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
			return "Unable to check for a newer version. BibleThump You can check manually @ https://git.io/vDFwj";
		}
	}

	public String getOAUTH() {
		return OAUTH;
	}

	public String getChannel() {
		return Channel;
	}

	public String getAccount() {
		return Account;
	}
}
