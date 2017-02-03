package com.iamnick.timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.net.Socket;




/*
 * All login info is in TempLoginInfo.java which is just 3 getter methods for oauth account and channel.
 */

public class Chatter {

	static String twitchChannel;
	public Chatter(Socket socket, BufferedWriter writer, BufferedReader reader) throws Exception{


		SettingsManager chat = new SettingsManager();
		chat.loadChatBotSettings();
		String OAUTH = chat.getOAUTH();
		String account = chat.getAccount();
		twitchChannel = chat.getChannel();


		//request for login
		writer.write("PASS " + OAUTH + "\r\n");
		System.out.println("Submitted Password/OAUTH Token");
		writer.write("NICK " + account + "\r\n");
		System.out.println("NICK " + account);
		writer.flush();

		String line = null;
		while ((line = reader.readLine( )) != null){
			System.out.println(line);
			if(line.contains("376")){//wait for login confirmation
				writer.write("CAP REQ :twitch.tv/membership\r\n"); //gotta see who joins and who become leafs
				System.out.println("CAP REQ :twitch.tv/membership");
				writer.flush();

				break; //continue on 
			}else if(line.contains("Login authentication failed")){
				System.out.println(line);
				Thread.sleep(5000);
				break;
			}
		}

		//I FINALLY get to JOIN!!!!!1!!
		writer.write("JOIN " + twitchChannel + "\r\n");
		writer.flush();

		while ((line = reader.readLine( )) != null){
			System.out.println(line);


			if(line.startsWith("PING ")) {
				writer.write("PONG " + "still alive!" + "\r\n");//it says pong but not in chat
				writer.flush();
				System.out.println("PONG still alive!");// this puts the pong in the log
			}

			if(line.contains(" PRIVMSG ") && !line.contains(account) ){
				//System.out.println("not by me");
				String[] temp = Parse.Message(line);
				if( temp[3].startsWith("!")){
					Commands.execute(temp, writer);
				}
				
			}
		}


		socket.close();


	}


	
	
	public static void chat(String message, BufferedWriter writer) throws IOException{

		writer.write("PRIVMSG " + twitchChannel + " :" + message + "\r\n");
		writer.flush();
	}
}
