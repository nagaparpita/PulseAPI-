package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.model.Alert;
import com.pulseapi.repo.AlertRepo;

public class TestAlertRepo {

    public static void main(String[] args) {

        // Create AlertRepo object to call alert methods
        AlertRepo alertRepo = new AlertRepo();

        // Scanner is used to take input from the user
        Scanner sc = new Scanner(System.in);

        // Keep showing the menu until user selects Exit
        while (true) {

            System.out.println("\n===== ALERT OPERATIONS =====");
            System.out.println("1. Insertion");
            System.out.println("2. Get Alerts");
            System.out.println("3. Exit");

            // Take user's choice
            System.out.print("Kindly choose an option: ");

            int option = sc.nextInt();

            switch (option) {

            case 1: {

                // Alert belongs to a particular API
                System.out.println("Enter API ID:");
                int apiId = sc.nextInt();

                // Type tells what kind of alert occurred
                System.out.println("Enter Alert Type:");
                String alertType = sc.next();

                // Enter message related to the alert
                System.out.println("Enter Message:");
                String message = sc.next();

                // Status tells whether alert was sent or not
                System.out.println("Enter Alert Status:");
                String alertStatus = sc.next();

                // Create Alert object and send it to Repo
                alertRepo.addAlert(
                        new Alert(apiId, alertType,
                                message, alertStatus)
                );

                break;
            }

            case 2: {

                // Get and display all alerts
                System.out.println("\n===== ALL ALERTS =====");

                alertRepo.getAllAlerts();

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