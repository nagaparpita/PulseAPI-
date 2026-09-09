package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.MonitoringResult;
import com.pulseapi.repo.MonitoringResultRepo;

public class TestMonitoringResultRepo {

    public static void main(String[] args) {

        // Create Repo object to call monitoring result methods
        MonitoringResultRepo resultRepo = new MonitoringResultRepo();

        // Scanner is used to take input from the user
        Scanner sc = new Scanner(System.in);

        // Keep showing the menu until user selects Exit
        while (true) {

            System.out.println("\n===== MONITORING RESULT OPERATIONS =====");
            System.out.println("1. Insertion");
            System.out.println("2. Get Monitoring Results");
            System.out.println("3. Exit");

            // Take user's choice
            System.out.print("Kindly choose an option: ");

            int option = sc.nextInt();

            switch (option) {

            case 1: {

                // API ID is needed because the result belongs to an API
                System.out.println("Enter API ID:");
                int apiId = sc.nextInt();

                // Status code shows the response received from the API
                System.out.println("Enter Status Code:");
                int statusCode = sc.nextInt();

                // Response time shows how long the API took to respond
                System.out.println("Enter Response Time:");
                long responseTime = sc.nextLong();

                // Status shows whether the API is UP or DOWN
                System.out.println("Enter Status (UP/DOWN):");
                String status = sc.next();

                // Enter error message if there is an error
                System.out.println("Enter Error Message:");
                String errorMessage = sc.next();

                // Create MonitoringResult object
                // and send it to Repo for insertion
                resultRepo.addMonitoringResult(
                        new MonitoringResult(apiId, statusCode,
                                responseTime, status, errorMessage)
                );

                break;
            }

            case 2: {

                // Get and display all monitoring results
                System.out.println("\n===== ALL MONITORING RESULTS =====");

                resultRepo.getAllMonitoringResults();

                break;
            }

            case 3: {

                // Close Scanner before exiting
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