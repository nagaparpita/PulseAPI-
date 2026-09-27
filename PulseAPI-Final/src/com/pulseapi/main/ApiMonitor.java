package com.pulseapi.main;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pulseapi.model.Api;
import com.pulseapi.repo.ApiCRUD;
import com.pulseapi.model.MonitoringResult;
import com.pulseapi.repo.MonitoringResultCRUD;
import com.pulseapi.model.Alert;
import com.pulseapi.repo.AlertCRUD;

@Component
public class ApiMonitor {

    // Object used to save monitoring results in database
    private MonitoringResultCRUD monitoringResultCRUD =
            new MonitoringResultCRUD();

    // Object used to save alerts in database
    private AlertCRUD alertCRUD =
            new AlertCRUD();


    // Check all saved APIs every 1 minute
    @Scheduled(fixedRate = 60000)
    public void checkApis() {

        // Get all APIs from database
        List<Api> apis =
                ApiCRUD.getAllApisForMonitoring();

        System.out.println(
                "========== API MONITORING =========="
        );

        // If no API is saved, stop monitoring
        if (apis.isEmpty()) {

            System.out.println(
                    "No APIs found for monitoring."
            );

            return;
        }

        // Check each API one by one
        for (Api api : apis) {

            checkSingleApi(api);
        }

        System.out.println(
                "===================================="
        );
    }


    // Check one API
    private void checkSingleApi(Api api) {

        System.out.println(
                "Checking: " +
                        api.getApiName()
        );

        System.out.println(
                "URL: " +
                        api.getApiUrl()
        );

        try {

            // Remove accidental spaces from URL
            URL url =
                    URI.create(
                            api.getApiUrl().trim()
                    ).toURL();

            // Open connection with API
            HttpURLConnection connection =
                    (HttpURLConnection)
                            url.openConnection();

            // Use HTTP method stored in database
            connection.setRequestMethod(
                    api.getHttpMethod()
            );

            // Maximum time to connect
            connection.setConnectTimeout(
                    10000
            );

            // Maximum time to wait for response
            connection.setReadTimeout(
                    10000
            );

            // Start response time calculation
            long startTime =
                    System.currentTimeMillis();

            // Send request and get response code
            int responseCode =
                    connection.getResponseCode();

            // Calculate response time
            long responseTime =
                    System.currentTimeMillis()
                            - startTime;


            // Check whether API is working
            if (responseCode >= 200 &&
                    responseCode < 400) {

                System.out.println(
                        "API STATUS: UP"
                );

                // Update API status in MySQL
                ApiCRUD.updateApiStatus(
                        api.getApiId(),
                        "UP"
                );

                // Create monitoring result for UP API
                MonitoringResult result =
                        new MonitoringResult(
                                api.getApiId(),
                                responseCode,
                                responseTime,
                                "UP",
                                null
                        );

                // Save monitoring result in MySQL
                monitoringResultCRUD
                        .insertMonitoringResult(result);

                // Resolve active alert when API is working again
                alertCRUD.resolveActiveAlert(
                        api.getApiId()
                );

            } else {

                System.out.println(
                        "API STATUS: DOWN"
                );

                // Update API status in MySQL
                ApiCRUD.updateApiStatus(
                        api.getApiId(),
                        "DOWN"
                );

                // Create monitoring result for DOWN API
                MonitoringResult result =
                        new MonitoringResult(
                                api.getApiId(),
                                responseCode,
                                responseTime,
                                "DOWN",
                                null
                        );

                // Save monitoring result in MySQL
                monitoringResultCRUD
                        .insertMonitoringResult(result);

                // Check whether an active alert already exists
                boolean alertExists =
                        alertCRUD.hasActiveAlert(
                                api.getApiId()
                        );

                // Create alert only if no active alert exists
                if (!alertExists) {

                    Alert alert =
                            new Alert(
                                    api.getApiId(),
                                    "API_DOWN",
                                    api.getApiName() +
                                            " is not responding.",
                                    "ACTIVE"
                            );

                    alertCRUD.insertAlert(alert);

                    System.out.println(
                            "Alert created for: " +
                                    api.getApiName()
                    );
                }
            }


            // Display HTTP response code
            System.out.println(
                    "Response Code: " +
                            responseCode
            );

            // Display API response time
            System.out.println(
                    "Response Time: " +
                            responseTime +
                            " ms"
            );

            System.out.println(
                    "-----------------------------"
            );

            // Close API connection
            connection.disconnect();

        } catch (Exception exception) {

            // If connection fails, API is considered DOWN
            System.out.println(
                    "API STATUS: DOWN"
            );

            // Display error reason
            System.out.println(
                    "Error: " +
                            exception.getMessage()
            );

            // Update DOWN status in MySQL
            ApiCRUD.updateApiStatus(
                    api.getApiId(),
                    "DOWN"
            );

            // Create monitoring result for failed API
            MonitoringResult result =
                    new MonitoringResult(
                            api.getApiId(),
                            null,
                            null,
                            "DOWN",
                            exception.getMessage()
                    );

            // Save failed monitoring result in MySQL
            monitoringResultCRUD
                    .insertMonitoringResult(result);

            // Check whether an active alert already exists
            boolean alertExists =
                    alertCRUD.hasActiveAlert(
                            api.getApiId()
                    );

// Create alert only if no active alert exists
            if (!alertExists) {

                Alert alert =
                        new Alert(
                                api.getApiId(),
                                "API_DOWN",
                                api.getApiName() +
                                        " is not responding. Error: " +
                                        exception.getMessage(),
                                "ACTIVE"
                        );

                alertCRUD.insertAlert(alert);

                System.out.println(
                        "Alert created for: " +
                                api.getApiName()
                );
            }

            System.out.println(
                    "-----------------------------"
            );
        }
    }

}