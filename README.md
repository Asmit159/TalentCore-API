# 🏢 TalentCore-API  -  an Employee REST Service (Spring Boot)

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-green?style=flat&logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue?style=flat&logo=mysql)](https://www.mysql.com/)
[![Build](https://img.shields.io/badge/Build-Maven-C71A36?style=flat&logo=apachemaven)](https://maven.apache.org/)

A production-grade **REST API** designed to manage employee records with clean architecture and scalable design patterns. 

While this project started as part of the **REST API module**, I have significantly extended it to bridge the gap between "tutorial code" and **real-world backend engineering**. It focuses on separation of concerns, data integrity, and API usability.

---

## The "Why" Behind This Project

Many CRUD projects stop at "it works." My goal here was to build a backend foundation that mimics a professional environment. Instead of cluttering controllers with logic, this project emphasizes:

* **Clean Architecture:** Strict separation between Controller, Service, and Repository layers.
* **Resilience:** Global exception handling to ensure the API never breaks silently or returns stack traces to the client.
* **Scalability:** Implementation of Pagination and Sorting to handle large datasets effectively.
* **Usability:** Search endpoints that mimic real-world requirements (e.g., admin dashboards).

---

## Key Features

### Core Architecture
* **Layered Design:** `Controller` → `Service` (Business Logic) → `Repository` (Data Access).
* **DTO Pattern:** Internal entities are decoupled from the API layer to prevent over-posting and accidental data exposure.
* **Dependency Injection:** Fully leveraging Spring's IoC container.

### API Capabilities
* **Advanced CRUD:** Complete lifecycle management for employee records.
* **Smart Pagination & Sorting:** efficiently loads data chunks (e.g., `?page=0&size=10&sort=lastName`).
* **Dynamic Search:** Filter employees by email, name, or department without fetching the entire database.
* **Input Validation:** Hibernate Validator prevents bad data (e.g., invalid emails) from ever reaching the database.
* **Global Error Handling:** A centralized `@ControllerAdvice` ensures all errors return a consistent, structured JSON response.

---

## Tech Stack

* **Language:** Java 25
* **Framework:** Spring Boot 3 (Web, Data JPA)
* **Database:** MySQL
* **Build Tool:** Maven
* **Testing/Debug:** Postman

---

## API Reference

### Employee Management

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/employees` | Fetch all employees (supports pagination) |
| `GET` | `/api/employees/{id}` | Get specific employee details |
| `POST` | `/api/employees` | Create a new employee |
| `PUT` | `/api/employees/{id}` | Update an existing employee |
| `DELETE` | `/api/employees/{id}` | Remove an employee |

### Search & Filtering
* **Search by Email:** `GET /api/employees/search?email=example@gmail.com`
* **Sorted List:** `GET /api/employees?page=0&size=5&sort=firstName,asc`

---

## Getting Started

### 1. Prerequisites
Ensure you have **Java**, **Maven**, and **MySQL** installed.

### 2. Database Setup
Create the database in your MySQL client:
```sql
CREATE DATABASE employee_directory;
```
### 3. Configuration
```
Update src/main/resources/application.properties with your credentials:

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_directory
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```
# Hibernate Settings
```
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
### 4. Run the Application
```Shell
mvn spring-boot:run
```
The API will be available at http://localhost:8080/api/employees.

###  Future Roadmap
This project is designed to be a living base for a complete Employee Management System. Upcoming planned features:

[ ] Security: JWT Authentication & Role-Based Access Control (Admin vs User).

[ ] Testing: Unit tests with JUnit 5 and Mockito.

[ ] Docs: Swagger/OpenAPI integration.

[ ] Containerization: Docker support for easy deployment.

### 👤 Author
Asmit Mandal -  Aspiring Software Engineer & Spring Boot Enthusiast

If you have any feedback or suggestions, feel free to reach out or open an issue!
And Don't forget to ⭐ the repo.
