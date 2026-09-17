package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.User;
import com.pulseapi.repo.UserCRUD;

public class UserMain {

    public static void startUserMenu() {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== USER CRUD MENU =====");
            System.out.println("1. Insert User");
            System.out.println("2. Get User By ID");
            System.out.println("3. Get All Users");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // INSERT USER
                case 1:

                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter user email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter user password: ");
                    String password = scanner.nextLine();

                    User user = new User(name, email, password);

                    UserCRUD.insertUser(user);

                    break;

                // GET USER BY ID
                case 2:

                    System.out.print("Enter user ID: ");
                    long readUserId = scanner.nextLong();

                    UserCRUD.getUserById(readUserId);

                    break;

                // GET ALL USERS
                case 3:

                    UserCRUD.getAllUsers();

                    break;

                // UPDATE USER
                case 4:

                    System.out.print("Enter user ID to update: ");
                    long updateUserId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();

                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();

                    UserCRUD.updateUser(
                            updateUserId,
                            newName,
                            newEmail,
                            newPassword
                    );

                    break;

                // DELETE USER
                case 5:

                    System.out.print("Enter user ID to delete: ");
                    long deleteUserId = scanner.nextLong();

                    UserCRUD.deleteUser(deleteUserId);

                    break;

                // BACK
                case 6:

                    System.out.println("Returning to Main Menu...");

                    break;

                default:

                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);


    }
}