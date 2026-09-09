package com.pulseapi.model;

public class API {

    private int apiId;
    private int userId;
    private String apiName;
    private String apiUrl;
    private String httpMethod;
    private int monitoringInterval;
    private boolean status;

    // Default Constructor
    public API() {

    }

    // Constructor for Insertion
    public API(int userId, String apiName, String apiUrl,
               String httpMethod, int monitoringInterval, boolean status) {

        this.userId = userId;
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.httpMethod = httpMethod;
        this.monitoringInterval = monitoringInterval;
        this.status = status;
    }

    // Constructor for Updation
    public API(int apiId, int userId, String apiName, String apiUrl,
               String httpMethod, int monitoringInterval, boolean status) {

        this.apiId = apiId;
        this.userId = userId;
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.httpMethod = httpMethod;
        this.monitoringInterval = monitoringInterval;
        this.status = status;
    }

    // Getters and Setters

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public int getMonitoringInterval() {
        return monitoringInterval;
    }

    public void setMonitoringInterval(int monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // toString()

    @Override
    public String toString() {

        return "API ID: " + apiId +
               "\nUser ID: " + userId +
               "\nAPI Name: " + apiName +
               "\nAPI URL: " + apiUrl +
               "\nHTTP Method: " + httpMethod +
               "\nMonitoring Interval: " + monitoringInterval +
               "\nStatus: " + status +
               "\n";
    }
}