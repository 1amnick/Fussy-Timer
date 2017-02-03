package com.iamnick.timer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;

public class JSON {
	public void getfile(){
		
	}
	public JsonValue request(String request, String auth) throws IOException {
		
		URL url = new URL(request);
		URLConnection uc = url.openConnection();

		uc.setRequestProperty("Accept", "application/vnd.twitchtv.v3+json");

	 	if (auth != null) {
			uc.setRequestProperty("Authorization", "OAuth " + auth);
	 	}

		InputStreamReader isr = new InputStreamReader(uc.getInputStream());

		return Json.parse(new BufferedReader(isr).lines().collect(Collectors.joining()));
	}
		
}
