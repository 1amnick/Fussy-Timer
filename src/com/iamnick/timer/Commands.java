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
	}

}
