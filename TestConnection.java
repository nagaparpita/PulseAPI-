package com.pulseapi.main;

import java.sql.Connection;
import com.pulseapi.connection.DBConnection;

public class TestConnection {
	
	public static void main(String[] args) {
		
		Connection con = DBConnection.getConnection();
		
		if(con!= null) {
			System.out.println("Connection Successfully!");

		} else {
			
			System.out.println("Connection Failed!");
		}
	}

}
