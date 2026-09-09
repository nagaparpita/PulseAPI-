package com.pulseapi.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pulseapi.connection.DBConnection;
import com.pulseapi.model.User;

public class UserRepo {
	
	// Insert Query
	public void addUser(User user) {
		
		// We need connection to communicate with database
		Connection con = DBConnection.getConnection();
		
		try {
			
			// Query to insert new user into users table
			// ? is used because actual values will be passed later
			String sql = """
					INSERT INTO users(name,email, password)
					 VALUES (?,?,?)
					""";
			
			// PreparedStatement is used to prepare and execute parameterized query
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Passing user values to ? placeholders
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			
			// executeUpdate() is used because INSERT changes data in database
			int rowAffected = ps.executeUpdate();
			
			// If one row is affected, insertion is successful
			if(rowAffected == 1) {
				System.out.println("Insertion Done.");
			}
			
			// Closing resources after operation is completed
			ps.close();
			con.close();
				
		} catch(SQLException e) {
			
			// If any database error occurs, it comes here
			System.out.println("Insertion failed.");
			e.printStackTrace();
		}
	}
	
	
	// Update Query
	public void updateUser(User user) {
		
		// Getting database connection
		Connection con = DBConnection.getConnection();
		
		try {
			
			// Updating user details using user_id
			// user_id is used in WHERE so that only required user is updated
			String sql = """
					UPDATE users 
					SET name =?,email =?, password =? 
					WHERE user_id =?
					""";
			
			// Preparing the update query
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Passing new values to the query
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			
			// Passing user_id for WHERE condition
			ps.setInt(4, user.getUserId());
			
			// executeUpdate() is used because UPDATE changes existing data
			int result = ps.executeUpdate();
			
			// If one row is affected, user is updated
			if(result == 1) {
				System.out.println("Updation Done.");
			}else {
				System.out.println("User not found.");
			}
			
			// Closing resources
			ps.close();
			con.close();
	
		} catch(SQLException e) {
			
			System.out.println("Updation failed.");
			e.printStackTrace();
		}
	}
	
	
	// Delete Query
	public void deleteUser(int userId) {
		
		// Getting database connection
		Connection con = DBConnection.getConnection();
		
		try {
			
			// Delete user using user_id
			String sql = """
					DELETE FROM users
					WHERE user_id =?
					""";
			
			// Preparing delete query
			PreparedStatement ps = con.prepareStatement(sql);
			
			// Passing user_id to ? placeholder
			ps.setInt(1, userId);
			
			// executeUpdate() is used because DELETE changes database data
			int result = ps.executeUpdate();
			
			// Check whether user was deleted
			if(result == 1) {
				System.out.println("Deletion Done.");
			}else {
				System.out.println("User not found.");
			}
			
			// Closing resources
			ps.close();
			con.close();
	
		} catch(SQLException e) {
			
			System.out.println("Deletion failed.");
			e.printStackTrace();
		}
	}
	
	
	// Select Query
	public void getAllUsers() {
		
		// Getting database connection
		Connection con = DBConnection.getConnection();
		
		try {
			
			// Statement is used to execute simple SQL query
			Statement statement = con.createStatement();
			
			// Query to get all users from users table
			String sql = """
					SELECT user_id, name, email, password
					FROM users
					""";
			
			// executeQuery() is used for SELECT because it returns data
			ResultSet rs = statement.executeQuery(sql);
			
			// ResultSet contains the data returned by SELECT query
			// next() moves to the next row
			while(rs.next()) {
				
				// Convert current database row into User object
				User user = mapRow(rs);
				
				// Print User object
				System.out.println(user);
			}
			
			// Closing resources after reading data
			rs.close();
			statement.close();
			con.close();
			
		} catch(SQLException e) {
			
			System.out.println("Selection failed.");
			e.printStackTrace();
		}		
	}
	
	
	// Mapping Row
	public User mapRow(ResultSet rs) throws SQLException {
		
		// Creating empty User object
		User user = new User();
		
		// Getting values from current ResultSet row
		// and storing them in User object
		user.setUserId(rs.getInt("user_id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		
		// Returning User object
		return user;
	}
}