package com.iamnick.timer;

import java.io.IOException;
import java.io.PrintWriter;

/*
 *  Created 12/15/16
 * @author 1amnick
 * (cc) by - nc
 * 
 */

public class CounterUpper implements Runnable{
//its a stupid name i know but who cares
	int time = 0;
	@Override
	public void run() {
		Gui.timerHook(true);
		while(time >= 0){

			try{
				PrintWriter writer = new PrintWriter("timer.txt", "UTF-8");
				writer.print(Timer.toTime(time));
				//System.out.println(time); //debug stuff
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);//i know i probably could use math with system uptime or getting computer time for a more accurate timer but its good enough
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Gui.stopTimer() == true){
				Gui.start.setText("Start!");
				break;
			}


			time++;
		}
		
		Gui.timerHook(false);
	}

}
