package com.pulseapi.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "monitoring_results")
public class MonitoringResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private long resultId;

    @Column(name = "api_id", nullable = false)
    private long apiId;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "response_time")
    private Long responseTime;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "checked_at")
    private Timestamp checkedAt;

    public MonitoringResult() {
    }

    public MonitoringResult(long apiId, Integer statusCode,
                            Long responseTime, String status,
                            String errorMessage) {

        this.apiId = apiId;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.status = status;
        this.errorMessage = errorMessage;
        this.checkedAt = new Timestamp(System.currentTimeMillis());
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public long getApiId() {
        return apiId;
    }

    public void setApiId(long apiId) {
        this.apiId = apiId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Timestamp getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(Timestamp checkedAt) {
        this.checkedAt = checkedAt;
    }

    @Override
    public String toString() {
        return "MonitoringResult{" +
                "resultId=" + resultId +
                ", apiId=" + apiId +
                ", statusCode=" + statusCode +
                ", responseTime=" + responseTime +
                ", status='" + status + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", checkedAt=" + checkedAt +
                '}';
    }
}