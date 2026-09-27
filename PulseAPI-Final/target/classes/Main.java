package com.pulseapi.main;

import java.util.Scanner;

import com.pulseapi.connection.HibernateUtil;
import com.pulseapi.main.MonitoringResultMain;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== PULSE API MONITORING SYSTEM =====");
            System.out.println("1. User Operations");
            System.out.println("2. API Operations");
            System.out.println("3. Alert Operations");
            System.out.println("4. Monitoring Result Operations");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    UserMain.startUserMenu();
                    break;

                case 2:
                    ApiMain.startApiMenu();
                    break;

                case 3:
                    AlertMain.startAlertMenu();
                    break;

                case 4:
                    MonitoringResultMain.startMonitoringResultMenu();
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    HibernateUtil.shutdown();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        scanner.close();

        System.out.println("Hibernate SessionFactory closed.");
    }
}