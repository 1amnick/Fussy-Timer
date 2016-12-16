package com.iamnick.timer;

import java.io.IOException;
import java.io.PrintWriter;

public class Timer implements Runnable{

	@Override
	public void run() {
		int time = Gui.getTimeInSeconds();
		Gui.timerHook(true);
		while(time >= 0){

			try{
				PrintWriter writer = new PrintWriter("timer.txt", "UTF-8");
				writer.print(toTime(time));
				//System.out.println(time);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
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
		int sec = seconds%60;
		if(sec < 10){
			clocksecs = "0" + sec;
		}else{
			clocksecs = "" + sec;
		}
		Gui.updateTimer(minutes, clocksecs);
		return minutes + ":" + clocksecs;
		
	}

}
