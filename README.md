# HRMS Payroll Backend

## 1. Module Overview

The **HRMS Payroll Backend** is a Spring Boot REST API application used to manage employee and payroll information.

The module provides APIs to:

* Create employee records
* View employee records
* Update employee information
* Delete employee records
* Search employees by name, email, department, or salary
* Filter and sort employee records
* Support pagination
* Handle application errors using global exception handling
* Store employee and payroll data in PostgreSQL

The backend exposes REST APIs that can be tested using **Postman** or any frontend application.

---

## 2. Entities

### Employee Entity

The main entity in the project is `Employee`.

The employee table contains fields such as:

| Field         | Description           |
| ------------- | --------------------- |
| `id`          | Unique employee ID    |
| `name`        | Employee name         |
| `email`       | Employee email        |
| `department`  | Employee department   |
| `basicSalary` | Employee basic salary |
| `allowances`  | Additional allowances |
| `deductions`  | Salary deductions     |

The `Employee` class is mapped to the `employees` table using JPA/Hibernate.

Example employee data:

```json
{
  "name": "Srilok",
  "email": "srilok.kumar@example.com",
  "department": "ECE",
  "basicSalary": 60000,
  "allowances": 5345,
  "deductions": 3000
}
```

---

## 3. APIs

### 3.1 Create Employee

**Method:** `POST`

**Endpoint:**

```text
/api/employees
```

**Request Body:**

```json
{
  "name": "Srilok",
  "email": "srilok.kumar@example.com",
  "department": "ECE",
  "basicSalary": 60000,
  "allowances": 5345,
  "deductions": 3000
}
```

This API creates a new employee record in the database.

---

### 3.2 Get All Employees

**Method:** `GET`

**Endpoint:**

```text
/api/employees
```

This API returns the list of employees.

---

### 3.3 Get Employee by ID

**Method:** `GET`

**Endpoint:**

```text
/api/employees/{id}
```

**Example:**

```text
/api/employees/1
```

This API returns the employee with the specified ID.

---

### 3.4 Update Employee

**Method:** `PUT`

**Endpoint:**

```text
/api/employees/{id}
```

**Example:**

```text
/api/employees/1
```

**Request Body:**

```json
{
  "name": "Srilok",
  "email": "srilok.kumar@example.com",
  "department": "ECE",
  "basicSalary": 65000,
  "allowances": 5000,
  "deductions": 3000
}
```

This API updates an existing employee record.

---

### 3.5 Delete Employee

**Method:** `DELETE`

**Endpoint:**

```text
/api/employees/{id}
```

**Example:**

```text
/api/employees/1
```

This API deletes the employee with the specified ID.

---

### 3.6 Search Employees

**Method:** `GET`

**Endpoint:**

```text
/api/employees/search
```

The search API can be used to search employees using different criteria.

Example:

```text
/api/employees/search?name=Srilok
```

Example department search:

```text
/api/employees/search?department=ECE
```

Example salary search:

```text
/api/employees/search?salary=60000
```

The salary search returns employees whose salary matches the requested salary according to the implemented search logic.

---

### 3.7 Pagination and Sorting

The employee API also supports pagination and sorting.

Example:

```text
/api/employees?page=0&size=10
```

Example with sorting:

```text
/api/employees?page=0&size=10&sort=name,asc
```

Pagination helps retrieve employee records in smaller groups instead of loading all records at once.

---

## 4. How to Run the Project

### Prerequisites

Install the following:

* Java 17
* Maven
* PostgreSQL
* Git
* Postman (optional, for API testing)

### Step 1: Clone the Repository

```bash
git clone <repository-url>
```

### Step 2: Open the Project

Open the project in **IntelliJ IDEA** or **VS Code**.

### Step 3: Configure PostgreSQL

Create the required PostgreSQL database.

Update the database configuration in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/payroll
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Step 4: Build the Project

Run:

```bash
mvn clean install
```

### Step 5: Start the Application

Run:

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application class from IntelliJ IDEA.

The application will start on:

```text
http://localhost:8080
```

---

## 5. Sample API Requests

### Create Employee

```http
POST http://localhost:8080/api/employees
Content-Type: application/json
```

Request:

```json
{
  "name": "Srilok",
  "email": "srilok.kumar@example.com",
  "department": "ECE",
  "basicSalary": 60000,
  "allowances": 5345,
  "deductions": 3000
}
```

### Get Employees

```http
GET http://localhost:8080/api/employees
```

### Get Employee by ID

```http
GET http://localhost:8080/api/employees/1
```

### Search by Name

```http
GET http://localhost:8080/api/employees/search?name=Srilok
```

### Search by Salary

```http
GET http://localhost:8080/api/employees/search?salary=60000
```

### Update Employee

```http
PUT http://localhost:8080/api/employees/1
```

### Delete Employee

```http
DELETE http://localhost:8080/api/employees/1
```

---

## 6. Error Handling

The application uses **Global Exception Handling** to handle errors across the REST APIs.

For example, if an employee ID does not exist, the API returns an appropriate error response instead of exposing an internal server error.

This makes the API responses easier to understand and helps identify problems during API testing.

---

## 7. Technology Stack

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* REST API
* Postman
* Git/GitHub
* Docker

---

## 8. Project Purpose

The purpose of this module is to provide a backend service for managing employee and payroll information through REST APIs.

The application follows a layered backend architecture where the controller handles API requests, the service layer contains business logic, and the repository layer communicates with the PostgreSQL database.
