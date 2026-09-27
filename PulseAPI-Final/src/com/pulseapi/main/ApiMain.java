package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.Api;
import com.pulseapi.repo.ApiCRUD;

public class ApiMain {

    public static void startApiMenu() {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== API CRUD MENU =====");
            System.out.println("1. Insert API");
            System.out.println("2. Get API By ID");
            System.out.println("3. Get All APIs");
            System.out.println("4. Update API");
            System.out.println("5. Delete API");
            System.out.println("6. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // INSERT API
                case 1:

                    System.out.print("Enter user ID: ");
                    long userId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Enter API name: ");
                    String apiName = scanner.nextLine();

                    System.out.print("Enter API URL: ");
                    String apiUrl = scanner.nextLine();

                    System.out.print("Enter HTTP method: ");
                    String httpMethod = scanner.nextLine();

                    System.out.print("Enter monitoring interval in seconds: ");
                    int monitoringInterval = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter API status: ");
                    String status = scanner.nextLine();

                    Api api = new Api(
                            userId,
                            apiName,
                            apiUrl,
                            httpMethod,
                            monitoringInterval,
                            status
                    );

                    ApiCRUD.insertApi(api);

                    break;

                // GET API BY ID
                case 2:

                    System.out.print("Enter API ID: ");
                    long readApiId = scanner.nextLong();

                    ApiCRUD.getApiById(readApiId);

                    break;

                // GET ALL APIS
                case 3:

                    ApiCRUD.getAllApis();

                    break;

                // UPDATE API
                case 4:

                    System.out.print("Enter API ID to update: ");
                    long updateApiId = scanner.nextLong();

                    System.out.print("Enter new user ID: ");
                    long newUserId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Enter new API name: ");
                    String newApiName = scanner.nextLine();

                    System.out.print("Enter new API URL: ");
                    String newApiUrl = scanner.nextLine();

                    System.out.print("Enter new HTTP method: ");
                    String newHttpMethod = scanner.nextLine();

                    System.out.print("Enter new monitoring interval in seconds: ");
                    int newMonitoringInterval = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new API status: ");
                    String newStatus = scanner.nextLine();

                    ApiCRUD.updateApi(
                            updateApiId,
                            newUserId,
                            newApiName,
                            newApiUrl,
                            newHttpMethod,
                            newMonitoringInterval,
                            newStatus
                    );

                    break;

                // DELETE API
                case 5:

                    System.out.print("Enter API ID to delete: ");
                    long deleteApiId = scanner.nextLong();

                    ApiCRUD.deleteApi(deleteApiId);

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