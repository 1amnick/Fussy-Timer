package com.iamnick.timer;

import java.io.IOException;
import java.io.PrintWriter;

/*
 * Created 12/15/16
 * @author 1amnick
 * (cc) by - nc
 * 
 */

public class Timer implements Runnable{

	@Override
	public void run() {
		int time = Gui.getTimeInSeconds();
		Gui.timerHook(true);
		while(time >= 0){

			try{
				PrintWriter writer = new PrintWriter("timer.txt", "UTF-8");
				writer.print(toTime(time));
				//System.out.println(time); //debug stuff
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);// im sure there is a better way
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Gui.stopTimer() == true){
				Gui.start.setText("Start!");
				break;
			}


			time--;
		}
		
		Gui.timerHook(false);
		Gui.start.setText("Start!");
		if(time == -1){
			try{
				PrintWriter writer = new PrintWriter("timer.txt", "UTF-8");
				writer.print(Gui.getEndMessage());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}


	}
	public static String toTime(int seconds){
		String clocksecs = null;
		int minutes = seconds / 60;
		int temp = seconds%60;
		if(temp < 10){
			clocksecs = "0" + temp;//this adds a 0 before the number so the time will look like  4:03 instead of an ugly 4:3
		}else{
			clocksecs = "" + temp;
		}
		Gui.updateTimer(minutes, clocksecs);
		return minutes + ":" + clocksecs;
		
	}

}
