package com.iamnick.timer;

import java.io.*;
import java.net.Socket;


/*
 * Created 12/15/16
 * @author 1amnick
 * (cc) by - nc
 * 
 */

public class FussyTimer {

	static String version = "FussyTimer v1.01 09.2.17 \"Look Ma, I started versioning numbers!\"";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		
		PrintStream out = new PrintStream(new FileOutputStream("log.log"));
		System.setOut(out);

		Gui G = new Gui();
		Socket socket = new Socket("irc.chat.twitch.tv", 6667);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		Chatter chat = new Chatter(socket, writer, reader);
	}

}
