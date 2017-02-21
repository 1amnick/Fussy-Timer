package com.iamnick.timer;

import java.io.BufferedWriter;


public class Commands {

	public static void execute(String[] message, BufferedWriter writer) throws Exception {
		if (message[3].startsWith("!changejoin")){
			if(DB.isSubbed(message[1])){//SELECT * from ExternalSub where User like nameQuery
				DB.updateJoin(message[1], message[3].substring(12));
				Chatter.chat("Your join message has been changed to " + message[3].substring(12));
			}else{
				Chatter.chat("Sorry this command is for gamewisp subscribers only.");
			}
		}
		else if (message[3].equalsIgnoreCase("!togglejoin")){
			if (DB.findName(message[1], "JoinEvent", "JoinDB")){
				boolean t = DB.toggleJoin(message[1]);
				if(t){
					Chatter.chat("Your join message has been toggled ON");
				}else{
					Chatter.chat("Your join message has been toggled OFF");
				}
			}else{
				Chatter.chat("Sorry you must be a regular to enable your join message.");
			}
		} else if(message[3].equalsIgnoreCase("!aboutbot")){
			Chatter.chat(FussyTimer.version + " " +SettingsManager.checkForUpdates());
		}else if(message[3].startsWith("!timeout")){
			String[] tokens = Parse.spaced(message[3]);
			int t = 0;
			try{
				t = Integer.parseInt(tokens[2]);
				Chatter.chat(message[1] +"! How dare you try to time out " + tokens[1] + "!");
				Thread.sleep(500);
				Chatter.chat("Let us know how it feels to be timed out for " + t + " seconds instead.");
				Chatter.chat(".timeout " + message[1] + " " + t + " You deserved it." );
			}catch(Exception e){
				//fail,do nothing
			}
		}
//			else if(message[3].equalsIgnoreCase("!fight")) {
//		
//			FightAndBet.addFighter(message[1], 100);
//		
//		} else if(message[3].equalsIgnoreCase("!closeBet")){
//			Fighter winner = FightAndBet.closeBets();
//			Chatter.chat("The Winner is " + winner.name , writer);
//		}
		
	}



}
