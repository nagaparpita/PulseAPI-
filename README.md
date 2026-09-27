# PulseAPI – API Monitoring and Alerting System

## 📌 Project Overview

PulseAPI is a web-based API Monitoring and Alerting System developed to monitor APIs automatically and track their availability and performance.

The system allows users to register, log in, add their APIs, and monitor their API status. If an API goes down or faces an error, the system automatically creates an alert and displays it on the Alerts page.

## 🎯 Objectives

* Monitor APIs automatically
* Detect API UP/DOWN status
* Track HTTP status codes
* Measure API response time
* Store monitoring history
* Generate alerts when an API has a problem
* Prevent duplicate active alerts
* Resolve alerts when the API becomes available again
* Provide user-specific API and monitoring data

## ✨ Features

* User Registration and Login
* Add API for Monitoring
* View User-specific APIs
* Automatic API Monitoring
* UP/DOWN Status Detection
* HTTP Status Code Tracking
* Response Time Tracking
* Monitoring History
* Automatic Alert Generation
* Duplicate Alert Prevention
* ACTIVE and RESOLVED Alert Status
* Dashboard Statistics
* User-specific Alerts
* API Deletion with Related Monitoring Data Cleanup

## 🛠️ Technologies Used

### Frontend

* HTML
* CSS
* JavaScript

### Backend

* Java
* Spring Boot
* Spring Web / REST API

### Database

* MySQL

### ORM

* Hibernate
* Jakarta Persistence / JPA

### Tools

* IntelliJ IDEA
* MySQL Workbench
* Git
* GitHub

## 🏗️ System Architecture

```text
Frontend
HTML + CSS + JavaScript
        ↓
Spring Boot REST API
        ↓
Hibernate / JPA
        ↓
MySQL Database
```

## 🗄️ Database Structure

The project uses the following main tables:

* `users` – stores user registration details
* `apis` – stores APIs added by users
* `monitoring_results` – stores API monitoring history
* `alerts` – stores API problem alerts

### Relationships

```text
User
  │
  └── 1 : Many ──> APIs
                       │
                       ├── 1 : Many ──> Monitoring Results
                       │
                       └── 1 : Many ──> Alerts
```

## ⚙️ How the System Works

1. User registers an account.
2. User logs in using email and password.
3. User adds an API with its URL, HTTP method, and monitoring interval.
4. The API is stored in the MySQL database with the user's `userId`.
5. The system automatically monitors the API.
6. The monitoring process checks the API response and response time.
7. If the API is working, its status is stored as `UP`.
8. If the API has a problem, its status is stored as `DOWN`.
9. A new alert is created for the API problem.
10. The alert is displayed on the Alerts page.
11. When the API becomes available again, the active alert is marked as `RESOLVED`.

## 🔔 Alert Handling

PulseAPI prevents repeated alerts for the same ongoing API problem.

```text
API DOWN
   ↓
Check Active Alert
   ↓
No Active Alert
   ↓
Create Alert
   ↓
Status = ACTIVE
```

If the API remains down, another duplicate active alert is not created.

When the API becomes available:

```text
API UP
   ↓
Active Alert Found
   ↓
Alert Status = RESOLVED
```

## 📊 Monitoring

For every monitoring check, the system stores information such as:

* API ID
* HTTP Status Code
* Response Time
* UP/DOWN Status
* Error Message
* Checked Time

This allows the user to view the monitoring history of their APIs.

## 🖥️ Project Modules

* Registration
* Login
* Dashboard
* API Management
* API Monitoring
* Monitoring Results
* Alerts

## 🚀 Future Enhancements

* Email notifications for API failures
* Graphs for response-time analysis
* More monitoring frequency options
* Authentication improvements
* Deployment to a cloud platform

## 👩‍💻 Project

**PulseAPI – API Monitoring and Alerting System**

Developed as an academic project using Java, Spring Boot, Hibernate, MySQL, HTML, CSS and JavaScript.

