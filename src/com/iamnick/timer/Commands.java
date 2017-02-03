package com.iamnick.timer;

import java.io.BufferedWriter;


public class Commands {

	public static void execute(String[] message, BufferedWriter writer) throws Exception {
		if (message[3].startsWith("!changejoin")){
			if(DB.isSubbed(message[1])){//SELECT * from ExternalSub where User like nameQuery
				DB.updateJoin(message[1], message[3].substring(12));
				Chatter.chat("Your join message has been changed to " + message[3].substring(12) , writer);
			}else{
				Chatter.chat("Sorry this command is for gamewisp subscribers only.", writer);
			}
		}
		else if (message[3].equalsIgnoreCase("!togglejoin")){
			if (DB.findName(message[1], "JoinEvent", "JoinDB")){
				boolean t = DB.toggleJoin(message[1]);
				if(t){
					Chatter.chat("Your join message has been toggled ON", writer);
				}else{
					Chatter.chat("Your join message has been toggled OFF", writer);
				}
			}else{
				Chatter.chat("Sorry you must be a regular to enable your join message.", writer);
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
