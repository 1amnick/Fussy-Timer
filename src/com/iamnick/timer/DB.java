package com.iamnick.timer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Stack;


import org.sqlite.SQLiteException;

public class DB {

	public static boolean isSubbed(String nameQuery) throws Exception {
		Connection conn = DBConnect("ExternalSubDB");
		String query = "SELECT * from " + "ExternalSub" + " where User = '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try{
			rs.getString("User");
			rs.close();
			return true;
		}catch (SQLException c) {
			rs.close();
			return false;
		}
	}

	public static boolean findName(String nameQuery, String table, String file) throws Exception {
		Connection conn = DBConnect(file);
		String query = "SELECT * from " + table + " where Name = '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try{
			rs.getString("Name");
			rs.close();
			return true;
		}catch (SQLException c) {
			rs.close();
			return false;
		}
	}
	public static void updateJoin(String nameQuery, String update) throws Exception{
		Connection conn = DBConnect("JoinDB");
		String query = "SELECT * from JoinEvent where Name = '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int ID = rs.getInt("ID");
		rs.close();
		System.out.println(ID);
		String updateQuery = "UPDATE JoinEvent SET Message = ? " + "WHERE ID = ?";

		PreparedStatement pstmt = conn.prepareStatement(updateQuery);
		pstmt.setString(1, update);
		pstmt.setInt(2, ID);
		try{
			System.out.println("UPDATE JoinEvent SET Message = " + update + " WHERE ID = " + ID);
			pstmt.executeUpdate();
		}catch (SQLiteException e){
			e.printStackTrace();
			
		}

	}

	public static int[] getFollowDate(String nameQuery) throws Exception{
		int date[] = null;
		Connection conn = DBConnect("FollowerDB");
		String query = "SELECT * from " + "Follower" + " where Name = '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);


		String temp = rs.getString("UpdatedAt");
		rs.close();
		date = Parse.date(temp);


		return date;
	}

	public static boolean toggleJoin(String nameQuery) throws Exception{
		Connection conn = DBConnect("JoinDB");
		String query = "SELECT * from JoinEvent where Name = '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int ID = rs.getInt("ID");
		int enabled = rs.getInt("Enabled");
		rs.close();
		System.out.println(ID + " " + enabled);
		String updateQuery = "UPDATE JoinEvent SET Enabled = ? " + "WHERE ID = ?";

		PreparedStatement pstmt = conn.prepareStatement(updateQuery);
		if(enabled == 1){
			pstmt.setInt(1, 0); //Set it to off
			pstmt.setInt(2, ID);
			pstmt.executeUpdate();
			return false;
		}
		else if(enabled == 0){
			pstmt.setInt(1, 1); //Set it to ON
			pstmt.setInt(2, ID);
			pstmt.executeUpdate();
			return true;
		}


		return false;
	}

	public static void resetUser(String nameQuery) throws Exception{
		Connection conn = DBConnect("CurrencyDB");
		String query = "SELECT * from CurrencyUser where Name = '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			String Points = rs.getString("Points");
			String Hours = rs.getString("Hours");
			String Name = rs.getString("Name");
			rs.close();
			String message = "Resetting " + Name + " removing " + Points + " fuzzes and " + Hours + " hours.\n";
			System.out.print(message);
			try {
				
				Files.write(Paths.get("resetlog.txt"), message.getBytes(), StandardOpenOption.APPEND);
				
			}catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			String updateQuery = "UPDATE CurrencyUser SET Points = ? " + "WHERE Name = ?";

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);

			pstmt.setString(1, "0"); 
			pstmt.setString(2, nameQuery);
			pstmt.executeUpdate();

			
			String updateQuery2= "UPDATE CurrencyUser SET Hours = ? " + "WHERE Name = ?";

			PreparedStatement pstmt2 = conn.prepareStatement(updateQuery2);

			
			pstmt2.setString(1, "00:00:00");
			pstmt2.setString(2, nameQuery);
			pstmt2.executeUpdate();
			
		} catch (SQLException e){
			//name does not exist do nothing
			//e.printStackTrace(); /*uncomment to debug weird stuff */
		
		}
	}
	
	
	public static String getLadderTime(String nameQuery) {
		//#1 fjollefjols (49483) - #2 mikto1000 (43736) - #3 ghost1988nl (34324) - #4 quarkdragon (21470) - #5 1amnick (20577) - #6 kosumo_ (20139) - #7 theunamusedfox (17679) - #8 mrgamy (16558) - #9 amazing_couchpotato (14768) - #10 chemienerd1999 (14355) -
		// SELECT Name,Points FROM CurrencyUser WHERE Points > 1344 ORDER BY Points
		// SELECT count(*) FROM CurrencyUser WHERE Points > 1345 ORDER BY Points
		float minutes = 0;
		int Rank = 0;
		String Name = "";
		
		Connection conn = DBConnect("CurrencyDB");
		String queryPoints = "SELECT MinutesWatched,Name from CurrencyUser where Name = '" + nameQuery + "'";
		
		try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryPoints);
		minutes = rs.getInt("MinutesWatched");
		Name = rs.getString("Name");
		rs.close();
		String queryRank = "SELECT count(*) FROM CurrencyUser WHERE MinutesWatched > " + minutes + " ORDER BY MinutesWatched";
		Statement stmt2 = conn.createStatement();
		ResultSet rs2 = stmt2.executeQuery(queryRank);
		Rank = rs2.getInt("count(*)");
		rs2.close();
		String queryAbove = "SELECT Name,MinutesWatched FROM CurrencyUser WHERE MinutesWatched > " + minutes + " ORDER BY MinutesWatched ASC";
		Statement stmt3 = conn.createStatement();
		ResultSet rs3 = stmt3.executeQuery(queryAbove);
		Stack<String> stack = new Stack<String>();
		
		for(int i =0;i<=5;i++){
			String ladderName;
			float ladderMinutes;
			ladderName = rs3.getString("Name");
			ladderMinutes = rs3.getInt("MinutesWatched");
			String line = "#" + (Rank - i - 2) + " " + ladderName + " (" + truncate(ladderMinutes) + ")" ;
			stack.add(line);
			rs3.next();
		}
		rs3.close();
		String out = "Your rank on the ladder is as follows: ";
		for(int i = 0;i<5;i++){
			out = out + " - " + stack.pop();
		}
		
		out = out + " - " + "#" + (Rank - 2) + " " + Name + " (" + truncate(minutes) + ")" ;
		
		
		String queryBelow = "SELECT Name,MinutesWatched FROM CurrencyUser WHERE MinutesWatched < " + minutes + " ORDER BY MinutesWatched DESC";
		Statement stmt4 = conn.createStatement();
		ResultSet rs4 = stmt4.executeQuery(queryBelow);
		
		for(int i=1;i<=5;i++){
			String ladderName;
			float ladderMinutes;
			ladderName = rs4.getString("Name");
			ladderMinutes = rs4.getInt("MinutesWatched");
			out = out + " - " + "#" + (Rank + i - 2) + " " + ladderName + " (" + truncate(ladderMinutes) + ")" ;

			rs4.next();
		}
		rs4.close();
		
		
		return out;
		
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Your rank is either too high or something went wrong";
	}

	public static String truncate(float F){
		
		String out = "";
		float flo = F/60;
		out = out + flo;
		String[] temp = out.split("[.]");
		out = temp[0] + "." + temp[1].substring(0, 1);
		
		
		return out;
	}
	
	public static String getLadderFuzz(String nameQuery) {
		//#1 fjollefjols (49483) - #2 mikto1000 (43736) - #3 ghost1988nl (34324) - #4 quarkdragon (21470) - #5 1amnick (20577) - #6 kosumo_ (20139) - #7 theunamusedfox (17679) - #8 mrgamy (16558) - #9 amazing_couchpotato (14768) - #10 chemienerd1999 (14355) -
		// SELECT Name,Points FROM CurrencyUser WHERE Points > 1344 ORDER BY Points
		// SELECT count(*) FROM CurrencyUser WHERE Points > 1345 ORDER BY Points
		int Points = 0;
		int Rank = 0;
		String Name = "";
		
		Connection conn = DBConnect("CurrencyDB");
		String queryPoints = "SELECT Points,Name from CurrencyUser where Name = '" + nameQuery + "'";
		
		try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryPoints);
		Points = rs.getInt("Points");
		Name = rs.getString("Name");
		rs.close();
		String queryRank = "SELECT count(*) FROM CurrencyUser WHERE Points > " + Points + " ORDER BY Points";
		Statement stmt2 = conn.createStatement();
		ResultSet rs2 = stmt2.executeQuery(queryRank);
		Rank = rs2.getInt("count(*)");
		rs2.close();
		String queryAbove = "SELECT Name,Points FROM CurrencyUser WHERE Points > " + Points + " ORDER BY Points ASC";
		Statement stmt3 = conn.createStatement();
		ResultSet rs3 = stmt3.executeQuery(queryAbove);
		Stack<String> stack = new Stack<String>();
		
		for(int i =0;i<=5;i++){
			String lName;
			int lPoints;
			lName = rs3.getString("Name");
			lPoints = rs3.getInt("Points");
			String line = "#" + (Rank - i - 2) + " " + lName + " (" + lPoints + ")" ;
			stack.add(line);
			rs3.next();
		}
		rs3.close();
		String out = "Your rank on the ladder is as follows: ";
		for(int i = 0;i<5;i++){
			out = out + " - " + stack.pop();
		}
		
		out = out + " - " + "#" + (Rank - 2) + " " + Name + " (" + Points + ")" ;
		
		
		String queryBelow = "SELECT Name,Points FROM CurrencyUser WHERE Points < " + Points + " ORDER BY Points DESC";
		Statement stmt4 = conn.createStatement();
		ResultSet rs4 = stmt4.executeQuery(queryBelow);
		
		for(int i=1;i<=5;i++){
			String lName;
			int lPoints;
			lName = rs4.getString("Name");
			lPoints = rs4.getInt("Points");
			out =out + " - " + "#" + (Rank + i - 2) + " " + lName + " (" + lPoints + ")" ;

			rs4.next();
		}
		rs4.close();
		
		
		return out;
		
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Your rank is either too high or something went wrong";
	}



	static Connection conn = null;

	static String home = System.getProperty("user.home");
	public void getHome(){
		System.out.println(home);
	}
	public static Connection DBConnect(String table) {

		try {
			// db parameters
			String url;
			if(home.contains("Maddin")){
				url = "jdbc:sqlite:" + home + "\\Streaming\\New Bot\\Twitch\\Databases\\" + table + ".sqlite";
			} else {
				url = "jdbc:sqlite:" + home + "\\AppData\\Roaming\\AnkhHeart\\AnkhBotR2\\Twitch\\Databases\\" + table + ".sqlite";
			}
			//
			// create a connection to the database
			System.out.println(url);
			conn = DriverManager.getConnection(url);

			System.out.println("Connected to SQLite Database");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 

		return conn;
	}



}
