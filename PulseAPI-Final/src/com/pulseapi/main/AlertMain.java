package com.pulseapi.main;

import com.pulseapi.model.Alert;
import com.pulseapi.repo.AlertCRUD;

import java.util.Scanner;

public class AlertMain {

    public static void startAlertMenu() {

        Scanner sc = new Scanner(System.in);
        AlertCRUD alertCRUD = new AlertCRUD();

        int choice;

        do {
            System.out.println("\n===== ALERT MENU =====");
            System.out.println("1. Insert Alert");
            System.out.println("2. Get All Alerts");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter API ID: ");
                    long apiId = sc.nextLong();
                    sc.nextLine();

                    System.out.print("Enter Alert Type: ");
                    String alertType = sc.nextLine();

                    System.out.print("Enter Message: ");
                    String message = sc.nextLine();

                    System.out.print("Enter Alert Status: ");
                    String alertStatus = sc.nextLine();

                    Alert alert = new Alert(
                            apiId,
                            alertType,
                            message,
                            alertStatus
                    );

                    alertCRUD.insertAlert(alert);
                    break;

                case 2:
                    alertCRUD.getAllAlerts();
                    break;

                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 3);
    }
}
