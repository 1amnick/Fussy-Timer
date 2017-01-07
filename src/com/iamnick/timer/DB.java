package com.iamnick.timer;

import java.sql.*;

import org.sqlite.SQLiteException;

public class DB {

	public static boolean isSubbed(String nameQuery) throws Exception {
		// TODO check user if they are subscribers
		Connection conn = DBConnect("ExternalSubDB");
		String query = "SELECT * from ExternalSub where User like '" + nameQuery + "'";
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


	static Connection conn = null;

	static String home = System.getProperty("user.home");
	public void getHome(){
		System.out.println(home);
	}
	public static Connection DBConnect(String table) throws Exception{

		try {
			// db parameters
			String url = "jdbc:sqlite:" + home + "\\AppData\\Roaming\\AnkhHeart\\AnkhBotR2\\Twitch\\Databases\\" + table + ".sqlite";//+ table +".sqlite";
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
