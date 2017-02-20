package com.iamnick.timer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;

import org.sqlite.SQLiteException;

public class DB {

	public static boolean isSubbed(String nameQuery) throws Exception {
		Connection conn = DBConnect("ExternalSubDB");
		String query = "SELECT * from " + "ExternalSub" + " where User like '" + nameQuery + "'";
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
		String query = "SELECT * from " + table + " where Name like '" + nameQuery + "'";
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
		String query = "SELECT * from JoinEvent where Name like '" + nameQuery + "'";
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
		String query = "SELECT * from " + "Follower" + " where Name like '" + nameQuery + "'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);


		String temp = rs.getString("UpdatedAt");
		rs.close();
		date = Parse.date(temp);


		return date;
	}

	public static boolean toggleJoin(String nameQuery) throws Exception{
		Connection conn = DBConnect("JoinDB");
		String query = "SELECT * from JoinEvent where Name like '" + nameQuery + "'";
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
		String query = "SELECT * from CurrencyUser where Name like '" + nameQuery + "'";
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




	static Connection conn = null;

	static String home = System.getProperty("user.home");
	public void getHome(){
		System.out.println(home);
	}
	public static Connection DBConnect(String table) throws Exception{

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
