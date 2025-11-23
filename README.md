# Campus Reservation System — Backend (Spring Boot)

A Spring Boot application for managing **campuses**, **lokalen (rooms)**, **users**, and **reservations**.  
The system allows users to reserve one or multiple rooms on a campus during a specific date range.

---

## Features

### Campus Management
- Retrieve all campuses
- Retrieve all rooms for a campus
- Filter rooms with query parameters:
    - `availableFrom={date}`
    - `availableUntil={date}`
    - `minNumberOfSeats={number}`

Example:
/campus/PROXIMUS/rooms?availableFrom=2025-01-10&availableUntil=2025-01-12&minNumberOfSeats=25

---

### Lokaal (Room) Management
- Rooms belong to a campus
- Rooms can be reserved by many users (via reservations)

### User Management
- Create users
- List users
- Retrieve reservations per user

### Reservatie Management
- Create reservations that connect:
    - one user
    - one or multiple lokalen
- Reservations include:
    - start date
    - end date
    - optional comment
- Prevents overlapping reservations

---

## Technologies Used

- Java 21
- Spring Boot 3+
- Spring Data JPA
- Hibernate
- H2/MySQL

---

## Running the Application

### 1. Clone the Repository
```bash
git clone https://github.com/MauroUCLL/CampusApp.git
```
### 2. Run with maven
```bash
mvn spring-boot:run
```
### 3. App url
```bash
http://localhost:8080
```

---

## Swagger available at:
```bash
http://localhost:8080/swagger-ui.html
```
## Available database profiles MySQL and H2:
### in file application.properties
### for MySQL:
```bash
spring.profiles.active=mysql
```
### for H2:
```bash
spring.profiles.active=h2
```