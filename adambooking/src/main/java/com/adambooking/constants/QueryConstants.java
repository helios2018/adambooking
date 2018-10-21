package com.adambooking.constants;


public class QueryConstants {
	
	public static String JDBC_JAR = "com.mysql.jdbc.Driver";	
	//public static String CONNECTION_URL = "jdbc:mysql://adambookingdb.cpxkasgjmuy9.us-east-1.rds.amazonaws.com:3306/adamBooking_Schema";
	//public static String DB_USER = "admin";
	//public static String DB_PASSWORD = "adam2017";
	
	public static String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/adamBooking";
	public static String DB_USER = "root";
	public static String DB_PASSWORD = "root";
		
	public static String SELECT_FROM_BOOKING = "select * from booking";
	public static String SELECT_FROM_UNAVAILABLE_SLOTS = "select * from unavailable_slots";
	public static String INSERT_INTO_BOOKING = "";
	public static String SELECT_LAST_ID_FROM_BOOKING = "select max(id) as id from booking";


}















