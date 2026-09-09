package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.User;
import com.pulseapi.repo.UserRepo;

public class TestUserRepo {

    public static void main(String[] args) {

        // Create UserRepo object to call User CRUD methods
        UserRepo userRepo = new UserRepo();

        // Scanner is used to take input from the user
        Scanner sc = new Scanner(System.in);

        // Keep the program running until the user selects Exit
        while (true) {

            System.out.println("\n===== USER CRUD OPERATIONS =====");
            System.out.println("1. Insertion");
            System.out.println("2. Updation");
            System.out.println("3. Deletion");
            System.out.println("4. Get Users");
            System.out.println("5. Exit");

            // Take the user's choice
            System.out.print("Kindly choose an option: ");

            int option = sc.nextInt();

            // Perform operation according to the selected option
            switch (option) {

            case 1: {

                // Take user details for inserting a new user
                System.out.println("Enter User Name:");

                String name = sc.next();

                System.out.println("Enter User Email:");

                String email = sc.next();

                System.out.println("Enter User Password:");

                String password = sc.next();

                // Create User object with the entered details
                // and send it to UserRepo for insertion
                userRepo.addUser(
                        new User(name, email, password)
                );

                break;

            }

            case 2: {

                // Take User ID to identify which user should be updated
                System.out.println("Enter User ID:");

                int userId = sc.nextInt();

                // Take new user details
                System.out.println("Enter New User Name:");

                String name = sc.next();

                System.out.println("Enter New User Email:");

                String email = sc.next();

                System.out.println("Enter New User Password:");

                String password = sc.next();

                // Create User object with User ID and new details
                // and send it to UserRepo for updation
                userRepo.updateUser(
                        new User(userId, name, email, password)
                );

                break;

            }

            case 3: {

                // Take User ID because we need to delete a specific user
                System.out.println("Enter User ID to Delete:");

                int userId = sc.nextInt();

                // Call delete method from UserRepo
                userRepo.deleteUser(userId);

                break;

            }

            case 4: {

                // Get and display all users from the database
                System.out.println("\n===== ALL USERS =====");

                userRepo.getAllUsers();

                break;

            }

            case 5: {

                // Close Scanner before exiting the program
                System.out.println("Program Exited.");

                sc.close();

                // Stop the program
                System.exit(0);

                break;

            }

            default: {

                // This runs when the user enters an invalid option
                System.out.println("Invalid Option.");

            }

            }

        }

    }

}