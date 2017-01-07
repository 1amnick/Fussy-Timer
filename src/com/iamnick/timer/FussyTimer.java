package com.iamnick.timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/*
 * Created 12/15/16
 * @author 1amnick
 * (cc) by - nc
 * 
 */

public class FussyTimer {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		Gui G = new Gui();
		Socket socket = new Socket("irc.chat.twitch.tv", 6667);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		Chatter chat = new Chatter(socket, writer, reader);
	}

}
