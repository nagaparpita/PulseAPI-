package com.pulseapi.model;

public class MonitoringResult {

    private int resultId;
    private int apiId;
    private int statusCode;
    private long responseTime;
    private String status;
    private String errorMessage;

    // Used when we create an empty object
    public MonitoringResult() {

    }

    // Used when we insert a new monitoring result
    public MonitoringResult(int apiId, int statusCode,
                            long responseTime, String status,
                            String errorMessage) {

        this.apiId = apiId;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    // Used when we update an existing result
    // resultId is needed to identify the record
    public MonitoringResult(int resultId, int apiId,
                            int statusCode, long responseTime,
                            String status, String errorMessage) {

        this.resultId = resultId;
        this.apiId = apiId;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    // Getter and Setter for resultId
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    // Getter and Setter for apiId
    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    // Getter and Setter for statusCode
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    // Getter and Setter for responseTime
    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter for errorMessage
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Used to display monitoring result details
    @Override
    public String toString() {
        return "Result ID: " + resultId +
               "\nAPI ID: " + apiId +
               "\nStatus Code: " + statusCode +
               "\nResponse Time: " + responseTime +
               "\nStatus: " + status +
               "\nError Message: " + errorMessage +
               "\n";
    }
}