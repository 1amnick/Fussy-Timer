package com.iamnick.timer;

import java.sql.*;

import org.sqlite.SQLiteException;

public class DB {

	public static boolean isSubbed(String nameQuery) throws Exception {
		// TODO check user if they are subscribers
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
		// TODO check user if they are subscribers
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
			System.out.println(e.getMessage());
		} 

		return conn;
	}

}
