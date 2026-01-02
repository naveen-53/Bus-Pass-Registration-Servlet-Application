# Bus Pass Registration — Jakarta Servlet Backend

A **pure backend, modern servlet-based** REST application built using:

- Jakarta Servlet 6 (Tomcat 10+)
- Java 17
- Maven
- MySQL
- Jackson (JSON)
- SLF4J + Logback (logging)
- JUnit (unit testing)
- Secure Authentication & Authorization with Password Hashing

This application provides CRUD APIs to manage **Bus Pass registrations**.

---

## Features

- REST APIs (GET / POST / PUT / DELETE)
- JDBC DAO Layer + Service Layer  
- JSON via Jackson  
- Authentication & Authorization  
- Password hashing (secure)  
- Logging to console + file  
- Unit test cases  
- MySQL persistent storage  

---

## Tech Stack

| Component | Version |
|--------|--------|
| Java | 17 |
| Jakarta Servlet | 6.0 |
| Tomcat | 10.1+ |
| MySQL | 8+ |
| Maven | 3.9+ |
| JSON | Jackson |
| Logging | SLF4J + Logback |
| Testing | JUnit |

---

## Database Setup

```sql
CREATE DATABASE bus_pass_db;
```

---
## API Endpoints
#### Base URL : http://localhost:8080/BusPassRegistrationApp
1. GET /api/passes
2. GET /api/passes/{id}
3. POST /api/passes  
Content-Type: application/json  
**RequestBody**
```
{
  "name": "Sathiya Francis",
  "email": "sathiyafran@gmail.com",
  "passType": "AC",
  "startDate": "2026-01-01",
  "endDate": "2026-02-01"
}
```
4. PUT /api/passes/{id}
5. DELETE /api/passes/{id}

---

## Unit Tests

1. Service Layer
   - Business logic testing
   - Mock DAO
2. DAO Layer
   - Database behavior validation
3. Servlet Layer
   - Mock HTTP Request / Response
   - JSON payload testing

---

## Logging
The project uses:  
- SLF4J API
- Logback implementation

---

## Liquibase
- Used liquibase for version control for DB
