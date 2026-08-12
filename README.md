# PulseAPI

## API Monitoring and Alerting Platform

PulseAPI is an API monitoring platform that continuously monitors APIs
and checks their availability, response time, HTTP status code, and failures.

## Technologies Used

- Java
- Spring Boot
- MySQL
- Spring Data JPA
- Hibernate
- Spring Scheduler
- RestTemplate / WebClient

## Main Features

- User registration and login
- Add and manage APIs
- Automatic API monitoring
- Response time monitoring
- HTTP status monitoring
- API failure detection
- Monitoring history
- Email/notification alerts

## Database

The project uses MySQL for storing:

- Users
- APIs
- Monitoring Results
- Alerts

## Database Design

The database contains the following relationships:

- Users → APIs : 1:N
- APIs → Monitoring Results : 1:N
- APIs → Alerts : 1:N

## Documentation

[Database Design and ER Diagram](documentation/PulseAPI_Database_Design.pdf)
