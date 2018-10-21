package com.adambooking.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.adambooking.constants.*;

public class DBConnector {
	
	private static String className = "DBConnector";
	private static Connection connection;
	
	
	/*
	public static void connect() throws ClassNotFoundException, SQLException {
		String methodName = "connect";
		System.out.println(className + "-->" + methodName);
		Class.forName("com.mysql.jdbc.Driver");
		//Connection con = DriverManager.getConnection("jdbc:mysql://adambookingdb.cpxkasgjmuy9.us-east-1.rds.amazonaws.com:3306/adamBooking_Schema", "admin", "adam2017");
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "root");
				
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from booking");
		int i = 0;
		while (rs.next()){
			System.out.println(rs.getString(i) + "  ");
			i++;
		}
		
		con.close();
			
	}*/
	
	public static void openDatabaseConnection() throws SQLException, ClassNotFoundException{
		String methodName = "createConnection";
		//System.out.println(className + "-->" + methodName);
		Class.forName(QueryConstants.JDBC_JAR);
		connection = DriverManager.getConnection(QueryConstants.CONNECTION_URL, QueryConstants.DB_USER, QueryConstants.DB_PASSWORD);
		//connection.close();
		//return connection;
	}
	
	public static void closeDatabaseConnection() throws Exception{
		connection.close();
	}
	
	public static ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException{
		String methodName = "executeQuery";
		//System.out.println(className + "-->" + methodName);
		Statement stmt;
		ResultSet rs = null;
		stmt = connection.createStatement();
		rs = stmt.executeQuery(query);
		//connection.close();
		return rs;
		
	}
	
	
	//statement.executeUpdate
	public static void executeUpdate(String query) throws SQLException, ClassNotFoundException{
		String methodName = "executeUpdate";
		//System.out.println(className + "-->" + methodName);
		Statement stmt;
		ResultSet rs = null;
		stmt = connection.createStatement();
		stmt.executeUpdate(query);
		//connection.close();
		
	}
	
		


}















