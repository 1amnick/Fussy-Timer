package com.iamnick.timer;

import java.io.IOException;
import java.io.PrintWriter;

public class CountUpper implements Runnable{

	int time = 0;
	@Override
	public void run() {
		Gui.timerHook(true);
		while(time >= 0){

			try{
				PrintWriter writer = new PrintWriter("timer.txt", "UTF-8");
				writer.print(Timer.toTime(time));
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


			time++;
		}
		
		Gui.timerHook(false);
	}

}
