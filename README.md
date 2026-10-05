# 🎓 Smart Campus Placement & Drive Management Portal

A production-ready, full-stack enterprise campus placement platform built with **Java 17**, **Spring Boot 3.x (Spring Web, Spring Data JPA)**, **MySQL** (with **H2 in-memory fallback**), and a responsive **Bootstrap 5 & Vanilla JS Fetch API** frontend dashboard.

---

## 🚀 Key Features

1. **Section A: Student Profile Registration**
   - Register students with Roll Number, Full Name, Campus Email, Phone, Branch (CSE, IT, ECE, MECH, CIVIL), CGPA, and Active Backlogs.
   - Comprehensive server-side Jakarta validation (`@NotBlank`, `@Email`, `@DecimalMin`, `@DecimalMax`, `@Min`).
   - Duplicate prevention on Roll Number and Email.

2. **Section B: Company Drive Publishing**
   - Create corporate placement drives specifying Company Name, Job Role, Package (in LPA), Minimum CGPA threshold, Maximum Allowed Backlogs, Eligible Branches (multi-select), Drive Date, and Status.

3. **Section C: Real-Time Dynamic Candidate Eligibility Screening**
   - Live bidirectional synchronization between Min CGPA range slider and numeric input.
   - Instant filtering by maximum allowable backlogs (Strict 0, Up to 1, Up to 2, or Any).
   - Multi-branch selection checkboxes with instant client debounce and zero page reload.
   - **One-Click Drive Criteria Preset**: Select any published drive to automatically populate the eligibility filters with that company's specific cutoffs.
   - Dynamic table rendering with branch color badges, visual CGPA indicator badges, and clean backlog indicators.
   - Client-side quick search and **One-Click Export to CSV**.
   - "Apply Student" modal to submit candidates directly to company drives.

---

## 🛠️ Tech Stack & Architecture

- **Backend Language:** Java 17 (LTS)
- **Framework:** Spring Boot 3.2.5
- **ORM / Persistence:** Spring Data JPA / Hibernate 6
- **Database:** 
  - Primary: **MySQL 8.x** (`jdbc:mysql://localhost:3306/placement_db`)
  - Local Dev Fallback: **H2 Database** in-memory (`jdbc:h2:mem:placement_db`)
- **Frontend:** Single-file static HTML5, Bootstrap 5.3.3 (CDN), Bootstrap Icons, and Vanilla JavaScript (Fetch API) located at `src/main/resources/static/index.html`.
- **Build Tool:** Maven 3.x

---

## 📂 Project Structure

```
Smart-Placement-Portal
│   pom.xml
│   README.md
├───src
    ├───main
        ├───java
        │   └───com
        │       └───placement
        │           └───portal
        │               │   PlacementPortalApplication.java
        │               ├───controller
        │               │       PlacementController.java
        │               ├───dto
        │               │       ApiResponse.java
        │               │       DriveApplicationRequest.java
        │               ├───entity
        │               │       CompanyDrive.java
        │               │       DriveApplication.java
        │               │       Student.java
        │               ├───exception
        │               │       DuplicateResourceException.java
        │               │       GlobalExceptionHandler.java
        │               │       ResourceNotFoundException.java
        │               ├───repository
        │               │       DriveApplicationRepository.java
        │               │       DriveRepository.java
        │               │       StudentRepository.java
        │               └───service
        │                       PlacementService.java
        │                       PlacementServiceImpl.java
        └───resources
            │   application.properties
            │   application-h2.properties
            │   data.sql
            │   schema.sql
            └───static
                    index.html
```

---

## ⚙️ How to Run

### Option 1: Run with MySQL (Production / Local DB)
1. Ensure your MySQL server is running on `localhost:3306`.
2. Configure credentials in `src/main/resources/application.properties` (defaults: `username=root`, `password=root`).
3. Run the application:
   ```bash
   mvn clean spring-boot:run
   ```
4. Spring Boot will automatically initialize `schema.sql` and load the mock dataset from `data.sql`.

### Option 2: Instant Run with H2 In-Memory (Zero Setup)
No MySQL installation required! Simply activate the `h2` profile:
```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=h2
```
Or set `spring.profiles.active=h2` in `application.properties`.
- Web Dashboard: `http://localhost:8080/`
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:placement_db`, User: `sa`, Password: *leave blank*)

---

## 📡 REST API Documentation

| Method | Endpoint | Description | Request Body / Parameters |
|---|---|---|---|
| `POST` | `/api/placement/students` | Register a new student | JSON `Student` object |
| `GET` | `/api/placement/students` | List all registered students | None |
| `POST` | `/api/placement/drives` | Create a company placement drive | JSON `CompanyDrive` object |
| `GET` | `/api/placement/drives` | List all company placement drives | None |
| `GET` | `/api/placement/eligible` | Filter candidates by criteria | `?minCgpa=7.5&maxBacklogs=0&branches=CSE,IT` |
| `POST` | `/api/placement/apply` | Submit student application | `{ "studentId": 1, "driveId": 2 }` |
| `GET` | `/api/placement/stats` | Dashboard statistics metrics | None |
