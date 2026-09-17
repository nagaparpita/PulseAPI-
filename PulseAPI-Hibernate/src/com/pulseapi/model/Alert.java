package com.pulseapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private long alertId;

    @Column(name = "api_id", nullable = false)
    private long apiId;

    @Column(name = "alert_type", length = 50)
    private String alertType;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "sent_at")
    private java.sql.Timestamp sentAt;

    @Column(name = "alert_status", length = 20)
    private String alertStatus;

    public Alert() {
    }

    public Alert(long apiId, String alertType, String message, String alertStatus) {
        this.apiId = apiId;
        this.alertType = alertType;
        this.message = message;
        this.alertStatus = alertStatus;
        this.sentAt = new java.sql.Timestamp(System.currentTimeMillis());
    }

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public long getApiId() {
        return apiId;
    }

    public void setApiId(long apiId) {
        this.apiId = apiId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public java.sql.Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(java.sql.Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertId=" + alertId +
                ", apiId=" + apiId +
                ", alertType='" + alertType + '\'' +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                ", alertStatus='" + alertStatus + '\'' +
                '}';
    }
}