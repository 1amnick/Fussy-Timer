package com.iamnick.timer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BotScrubber implements Runnable {
	public void run(){
		retry:
		try {
			BufferedReader loader = new BufferedReader(new FileReader("botlist.txt"));
			
			try {
				String temp = null;
				while ((temp = loader.readLine()) != null) {
					System.out.println(temp);
					DB.resetUser(temp);
				}
				loader.close();
			} catch (IOException e) {
				//if there is an error in reading the line
				e.printStackTrace();
			} catch (Exception e) {
				//if there is an error running DB.resetUser()
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			//nothing to load do nothing
			//e.printStackTrace();
			System.out.println("Bot list file not found. Creating File...");
			try{
				PrintWriter writer = new PrintWriter("botlist.txt", "UTF-8");
				writer.println("1ambot");
				writer.println("moobot");
				writer.println("nightbot");
				writer.println("muxybot");
				writer.close();
			
				PrintWriter writer2 = new PrintWriter("resetlog.txt", "UTF-8");
				writer2.println("This is a log of all the removed Fuzzes from the bot list");
				writer2.close();
				break retry;
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}


	}
	
	
	
}
