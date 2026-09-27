package com.pulseapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apis")
public class Api {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    private long apiId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "api_name", nullable = false, length = 100)
    private String apiName;

    @Column(name = "api_url", nullable = false, length = 500)
    private String apiUrl;

    @Column(name = "http_method", length = 10)
    private String httpMethod = "GET";

    @Column(name = "monitoring_interval", nullable = false)
    private int monitoringInterval;

    @Column(name = "status", length = 20)
    private String status = "UNKNOWN";

    public Api() {
    }

    public Api(
            long userId,
            String apiName,
            String apiUrl,
            String httpMethod,
            int monitoringInterval,
            String status) {

        this.userId = userId;
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.httpMethod = httpMethod;
        this.monitoringInterval = monitoringInterval;
        this.status = status;
    }

    public long getApiId() {
        return apiId;
    }

    public void setApiId(long apiId) {
        this.apiId = apiId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Api{" +
                "apiId=" + apiId +
                ", userId=" + userId +
                ", apiName='" + apiName + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", monitoringInterval=" + monitoringInterval +
                ", status='" + status + '\'' +
                '}';
    }
}
