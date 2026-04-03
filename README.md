**FinanceDataProcessingAndAccessControl**

**Overview**
This project is a backend system for managing financial records and generating dashboard insights. It allows users to perform CRUD operations on financial data and provides summary analytics with role-based access control.

**Technologies Used**
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL

**Project Structure**
The application follows a layered architecture:
- Controller → Handles API requests  
- Service → Contains business logic  
- Repository → Handles database operations  
- Entity → Represents database tables  

**User Roles & Access**

| Role    | Access                                |
|---------|--------------------------------------|
| VIEWER  | Can only view dashboard & records    |
| ANALYST | Can view and filter records          |
| ADMIN   | Full access (create, update, delete) |

> Role is passed via request header:  
> `role : ADMIN`


**Features**
- Create financial records  
- View all records
- Update records 
- Delete records  
- Dashboard summary (total income, expenses, net balance, category-wise totals, recent transactions)  
- Role-based access control  
- Input validation (e.g., amount > 0)  

**API Endpoints**

**1️⃣ User Management (ADMIN only)**

| Method | URL             | Description          |
|--------|-----------------|----------------------|
| POST   | `/users`        | Create user          |
| GET    | `/users`        | List all users       |
| PUT    | `/users/{id}`   | Update user          |
| DELETE | `/users/{id}`   | Deactivate user      |

**Sample Request (Create User):**
**JSON Format**
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "ADMIN"
}

**2️⃣ Financial Records**

| Method | URL               | Role                   | Description               |
| ------ | ----------------- | ---------------------- | ------------------------- |
| POST   | `/records`        | ADMIN                  | Create financial record   |
| GET    | `/records`        | VIEWER, ANALYST, ADMIN | List all records          |
| PUT    | `/records/{id}`   | ADMIN                  | Update record             |
| DELETE | `/records/{id}`   | ADMIN                  | Delete record             |
| GET    | `/records/filter` | ANALYST, ADMIN         | Filter by type & category |

**Sample Request (Create Record):**
**JSON Format**
{
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "description": "April salary",
  "userId": 1
}

**3️⃣ Dashboard Summary**

| Method | URL          | Role                   | Description           |
| ------ | ------------ | ---------------------- | --------------------- |
| GET    | `/dashboard` | VIEWER, ANALYST, ADMIN | Get dashboard summary |

**Sample Response:**
**JSON Format**
{
  "totalIncome": 100000,
  "totalExpense": 3000,
  "netBalance": 97000,
  "categoryTotals": {
      "Salary": 100000,
      "Food": 3000
  },
  "recentTransactions": [
      {
          "id": 1,
          "amount": 3000,
          "type": "EXPENSE",
          "category": "Food",
          "date": "2026-04-02",
          "description": "Groceries",
          "createdBy": "John Doe"
      }
  ]
}

**Validation & Error Handling**
* Amount must be > 0
* Inactive users cannot create records
* Invalid requests return descriptive error messages
* Role-based restrictions enforced

**Database**
**Database used: MySQL**
**Tables:**
**1. User**

| Column   | Type    |
| -------- | ------- |
| id       | BIGINT  |
| name     | VARCHAR |
| email    | VARCHAR |
| password | VARCHAR |
| role     | VARCHAR |
| active   | BOOLEAN |

**2. FinancialRecord**

| Column        | Type    |
| ------------- | ------- |
| id            | BIGINT  |
| amount        | DOUBLE  |
| type          | VARCHAR |
| category      | VARCHAR |
| date          | DATE    |
| description   | VARCHAR |
| created_by_id | BIGINT  |

**Example Queries:**
SELECT * FROM user;
SELECT * FROM financial_record;

**How to Run**
**1. Start MySQL and create a database:**
   CREATE DATABASE finance_db;
**2. Update application.properties:**
   spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.hibernate.ddl-auto=update
3. Run Spring Boot application
4. Test APIs using Postman

**Assumptions**
Roles are passed via request headers (role: ADMIN/ANALYST/VIEWER)
Authentication is simplified (no login system implemented)
All data is stored in MySQL

**Conclusion**
This project demonstrates backend development skills including:
API design
CRUD operations
Role-based access control
Data validation and error handling
Dashboard summary & aggregation
