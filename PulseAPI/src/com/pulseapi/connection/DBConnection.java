package com.pulseapi.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static Connection getConnection() {
		
		Connection con = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/pulseapi_db";
			String username = "root";
			String password = "admin";
			
			con = DriverManager.getConnection(url, username, password);
			
			System.out.println("Database Connected Successfully!");
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return con;
	}
}
