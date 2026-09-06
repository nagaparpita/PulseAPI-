package com.pulseapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pulseapi.connection.DBConnection;
import com.pulseapi.model.User;

public class UserDAO {
	
	//Insert Query
	public void addUser(User user) {
		
		Connection con= DBConnection.getConnection();
		
		try {
			
			String sql = """
					INSERT INTO users(name,email, password)
					 VALUES (?,?,?)
					""";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3,  user.getPassword());
			
			int rowAffected = ps.executeUpdate();
			
			if(rowAffected == 1) {
				System.out.println("Insertion Done.");
			}
			
			ps.close();
			con.close();
				
		}catch(SQLException e) {
			
			System.out.println("Insertion Done.");
			e.printStackTrace();
			
		}
	}
	
	//Update Query
	public void updateUser(User user) {
		
		Connection con = DBConnection.getConnection();
		
		try {
			String sql = """
					UPDATE users 
					SET name =?,email =?, password =? 
					WHERE user_id =?
					""";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getUserId());
			
			int result = ps.executeUpdate();
			
			if(result == 1) {
				System.out.println("Updation Done.");
			}else {
				System.out.println("User not found.");
			}
			
			ps.close();
			con.close();
	
		} catch(SQLException e) {
			System.out.println("Updation failed.");
			e.printStackTrace();
		}
	}
	
	//Delete Query
public void deleteUser(int userId) {
		
		Connection con = DBConnection.getConnection();
		
		try {
			String sql = """
					DELETE FROM users
					WHERE user_id =?
					""";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, userId);
			
			int result = ps.executeUpdate();
			
			if(result == 1) {
				System.out.println("Deletion Done.");
			}else {
				System.out.println("User not found.");
			}
			
			ps.close();
			con.close();
	
		} catch(SQLException e) {
			System.out.println("Deletion failed.");
			e.printStackTrace();
		}
	}
	
	//Select Query
	public void getAllUsers() {
		Connection con = DBConnection.getConnection();
		
		
		
		try {
			
			Statement statement = con.createStatement();
			
			String sql ="""
					SELECT user_id, name, email, password
					FROM users
					""";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				
				User user = mapRow(rs);
				
				System.out.println(user);
			}
			
			rs.close();
			statement.close();
			con.close();
		} catch(SQLException e) {
			
			System.out.println("Selection failed.");
			e.printStackTrace();
		}		
	}
	
	//Mapping Row
	public User mapRow(ResultSet rs) throws SQLException {
		User user = new User();
		
		user.setUserId(rs.getInt("user_id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		
		return user;
	}
	

}
