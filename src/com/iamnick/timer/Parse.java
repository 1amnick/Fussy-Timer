package com.iamnick.timer;

public class Parse {

	public static int[] date(String cal) {
		String[] dateAndTime = cal.split("[ ]");
		String[] dates = dateAndTime[0].split("-");
		
		int[] date = new int[dates.length];
		for(int i = 0;i < dates.length;i++)
		{
		   date[i] = Integer.parseInt(dates[i]);
		}
		
		return date;
	}

	
	/**
	 * @param message
	 * @return a tokenized input of the chat message [0] is null [1] is the user name [2] is the channel [3] is everything the user put into the command including the !command
	 */
	public static String[] Message(String message){
		String[] tokens = message.split("[:#]");
		String[] temp = tokens[1].split("!");
		tokens[1] = temp[0];
		tokens[2] = "#" + tokens[2];
		
		return tokens;
	}
	
	
}
