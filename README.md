# PulseAPI

## Database Design and ER Diagram

This repository contains the **Database Design and Entity-Relationship (ER) Diagram** for the **PulseAPI – API Monitoring and Alerting Platform**.

The document describes the database structure used for the project, including the main tables, fields, primary keys, foreign keys, and relationships between the tables.

## Database Tables

The database contains four main tables:

- Users
- APIs
- Monitoring Results
- Alerts

## Database Relationships

- Users → APIs : 1:N
- APIs → Monitoring Results : 1:N
- APIs → Alerts : 1:N

## Documentation

[View Database Design and ER Diagram](documentation/PulseAPI_Database_Design_ER_Diagram.pdf)
