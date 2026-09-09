package com.pulseapi.model;

public class Alert {

    private int alertId;
    private int apiId;
    private String alertType;
    private String message;
    private String alertStatus;

    // Used when we create an empty object
    public Alert() {}

    // Used when we insert a new alert
    public Alert(int apiId, String alertType,
                 String message, String alertStatus) {
        this.apiId = apiId;
        this.alertType = alertType;
        this.message = message;
        this.alertStatus = alertStatus;
    }

    // Getter and Setter for alertId
    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    // Getter and Setter for apiId
    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    // Getter and Setter for alertType
    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for alertStatus
    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    // Used to display alert details
    @Override
    public String toString() {
        return "Alert ID: " + alertId +
               "\nAPI ID: " + apiId +
               "\nAlert Type: " + alertType +
               "\nMessage: " + message +
               "\nAlert Status: " + alertStatus +
               "\n";
    }
}