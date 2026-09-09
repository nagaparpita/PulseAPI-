package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.API;
import com.pulseapi.repo.APIRepo;

public class TestAPIRepo {

    public static void main(String[] args) {

        // Create APIRepo object to call API CRUD methods
        APIRepo apiRepo = new APIRepo();

        // Scanner is used to take input from the user
        Scanner sc = new Scanner(System.in);

        // Keep the program running until the user selects Exit
        while (true) {

            System.out.println("\n===== API CRUD OPERATIONS =====");
            System.out.println("1. Insertion");
            System.out.println("2. Updation");
            System.out.println("3. Deletion");
            System.out.println("4. Get APIs");
            System.out.println("5. Exit");

            // Take the user's choice
            System.out.print("Kindly choose an option: ");

            int option = sc.nextInt();

            // Perform operation according to the selected option
            switch (option) {

            case 1: {

                // Take API details for inserting a new API
                System.out.println("Enter User ID:");
                int userId = sc.nextInt();

                System.out.println("Enter API Name:");
                String apiName = sc.next();

                System.out.println("Enter API URL:");
                String apiUrl = sc.next();

                System.out.println("Enter HTTP Method:");
                String httpMethod = sc.next();

                System.out.println("Enter Monitoring Interval:");
                int monitoringInterval = sc.nextInt();

                System.out.println("Enter Status (true/false):");
                boolean status = sc.nextBoolean();

                // Create API object with entered details
                // and send it to APIRepo for insertion
                apiRepo.addAPI(
                        new API(userId, apiName, apiUrl,
                                httpMethod, monitoringInterval, status)
                );

                break;
            }

            case 2: {

                // Take API ID to identify which API should be updated
                System.out.println("Enter API ID:");
                int apiId = sc.nextInt();

                System.out.println("Enter User ID:");
                int userId = sc.nextInt();

                // Take new API details
                System.out.println("Enter New API Name:");
                String apiName = sc.next();

                System.out.println("Enter New API URL:");
                String apiUrl = sc.next();

                System.out.println("Enter New HTTP Method:");
                String httpMethod = sc.next();

                System.out.println("Enter New Monitoring Interval:");
                int monitoringInterval = sc.nextInt();

                System.out.println("Enter New Status (true/false):");
                boolean status = sc.nextBoolean();

                // Create API object with API ID and new details
                // and send it to APIRepo for updation
                apiRepo.updateAPI(
                        new API(apiId, userId, apiName, apiUrl,
                                httpMethod, monitoringInterval, status)
                );

                break;
            }

            case 3: {

                // Take API ID because we need to delete a specific API
                System.out.println("Enter API ID to Delete:");
                int apiId = sc.nextInt();

                // Call delete method from APIRepo
                apiRepo.deleteAPI(apiId);

                break;
            }

            case 4: {

                // Get and display all APIs from the database
                System.out.println("\n===== ALL APIs =====");

                apiRepo.getAllAPIs();

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