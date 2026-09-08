package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.User;
import com.pulseapi.repo.UserRepo;

public class TestUserDAO {
	
	public static void main(String[] args) {
		
		UserRepo userDAO = new UserRepo();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			System.out.println("\n===== User CRUD OPERATIONS =====");
			System.out.println("1. Insertion");
			System.out.println("2. Updation");
			System.out.println("3. Deletion");
			System.out.println("4. Get Users");
			System.out.println("5. Exit");
			
			System.out.println("Kindly choose an option: ");
			
			int option = sc.nextInt();
			
			switch (option) {
			
			case 1: {
				
				System.out.println("Enter User Name: ");
				String name = sc.next();
				
				System.out.println("Enter User Email: ");
				String email = sc.next();
				
				System.out.println("Enter User Password: ");
				String password = sc.next();
				
				userDAO.addUser(
						new User(name,email, password)
						);
				break;
			}
			
			case 2:{
				
				System.out.println("Enter User ID: ");
				int userId = sc.nextInt();
				
				System.out.println("Enter New User Name: ");
				String name = sc.next();
				
				System.out.println("Enter New User Email: ");
				String email = sc.next();
				
				System.out.println("Enter New User Password: ");
				String password = sc.next();
				
				userDAO.updateUser(
						new User(userId, name, email, password)
						);
				break;
			}
			
			case 3: {
				System.out.println("Enter UserID to Delete:  ");
				int userId = sc.nextInt();
				
				userDAO.deleteUser(userId);
				break;
			}
			
			case 4: {
				
				System.out.println("\n===== ALL USERS =====");
				
				userDAO.getAllUsers();
				
				break;
			}
			
			case 5: {
				
				System.out.println("Program Exited.");
				
				sc.close();
				System.exit(0);
				
				break;
			}
			
			default: {
				
				System.out.println("Invalid Option.");
			}
			}
		}
	
	}

}
