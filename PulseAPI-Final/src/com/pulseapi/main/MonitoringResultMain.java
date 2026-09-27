package com.pulseapi.main;

import com.pulseapi.model.MonitoringResult;
import com.pulseapi.repo.MonitoringResultCRUD;

import java.util.Scanner;

public class MonitoringResultMain {

    public static void startMonitoringResultMenu() {

        Scanner scanner = new Scanner(System.in);
        MonitoringResultCRUD crud = new MonitoringResultCRUD();

        int choice;

        do {

            System.out.println("\n===== MONITORING RESULT MENU =====");
            System.out.println("1. Insert Monitoring Result");
            System.out.println("2. Get All Monitoring Results");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter API ID: ");
                    long apiId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Enter Status Code: ");
                    int statusCode = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Response Time in milliseconds: ");
                    long responseTime = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Enter Status: ");
                    String status = scanner.nextLine();

                    System.out.print("Enter Error Message: ");
                    String errorMessage = scanner.nextLine();

                    MonitoringResult result = new MonitoringResult(
                            apiId,
                            statusCode,
                            responseTime,
                            status,
                            errorMessage
                    );

                    crud.insertMonitoringResult(result);
                    break;

                case 2:
                    crud.getAllMonitoringResults();
                    break;

                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 3);
    }
}