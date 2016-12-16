package com.iamnick.timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Created 12/15/16
 * @author 1amnick
 * (cc) by - nc
 * 
 */

public class Gui implements ActionListener {

	SettingsManager settings = new SettingsManager();
	static String endText = "";
	static boolean isTimerRunning = false;
	private static boolean killTimer;

	/* GUI Variables */
	JFrame frame = new JFrame("Fussy Timer");//top of the window to ya!
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	static JButton start = new JButton("Start!");
	static JTextField inputMinutes = new JTextField(3);
	JLabel splitter = new JLabel(":");
	static JTextField inputSeconds = new JTextField(3);

	static JTextField EndMessage = new JTextField(15);
	
	static JButton save = new JButton("Save");
	static JButton load = new JButton("Load");

	public Gui(){
		panel.add(inputMinutes);
		inputMinutes.setText("5");// 5 minute default
		panel.add(splitter);
		panel.add(inputSeconds);
		inputSeconds.setText("00");// and 0 added seconds
		panel.add(start);
		EndMessage.setText(""); //can be set to whatever for a default 
		panel.add(save);

		
		
		//panel2
		panel2.add(EndMessage);
		panel2.add(load);

		frame.add(panel);
		frame.add(panel2); //make sure this bar shows under the time settings
		frame.setLayout(new FlowLayout()); //i think this is magic
		frame.setSize(300, 120); //random numbers go here
		
		
		//load settings here
		
		frame.setVisible(true);


		start.addActionListener(this);
		save.addActionListener(settings);
		load.addActionListener(settings);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}




	public void startTimer(){
		(new Thread(new Timer())).start();
	}
	public static boolean stopTimer(){
		return killTimer;
	}
	public void startCounter(){
		(new Thread(new CounterUpper())).start();
	}

	public static int getTimeInSeconds() throws NumberFormatException{
		int minutes = 0;
		int seconds = 0;
		int timer = -1;
		try{
			minutes = Integer.parseInt(inputMinutes.getText());
			seconds = Integer.parseInt(inputSeconds.getText());
		}catch (NumberFormatException e){
			e.printStackTrace();
		}

		timer = (minutes * 60) + seconds;
		return timer;
	}

	public static boolean timerHook(Boolean B){
		isTimerRunning = B;
		return true;
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if (getTimeInSeconds() == 0){
			if(isTimerRunning == false){
				killTimer = false;
				startCounter();
				start.setText("Stop!");
			}else{
				killTimer = true;
				start.setText("Start!");
			}
		}else{
			if(isTimerRunning == false){
				killTimer = false;
				startTimer();
				start.setText("Stop!");
			}else{
				killTimer = true;
				start.setText("Start!");
			}
		}
	}




	public static void updateTimer(int minutes, String clocksecs) {
		inputMinutes.setText(minutes +"");
		inputSeconds.setText(clocksecs);

	}




	public static String getEndMessage() {
		endText = EndMessage.getText();
		return endText;
	}

}
