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

	
	//remember to change these numbers incrementally Kappa
	
	static Double versionNumber = 1.04;
	//make sure you change the version.txt file to fit and comply with FDC and HBP standards.
	static String version = "FussyTimer v"+versionNumber+" 20.2.17 The \"have you tried turning it off and on again?\" Edition. More info at https://git.io/vDFwj";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		
		PrintStream out = new PrintStream(new FileOutputStream("log.log"));
		System.setOut(out);
		System.setErr(out);
		
		Gui G = new Gui();
		Socket socket = new Socket("irc.chat.twitch.tv", 6667);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		Chatter chat = new Chatter(socket, writer, reader);
	}

}