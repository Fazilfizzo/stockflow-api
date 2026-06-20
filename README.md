# StockFlow API

A robust and scalable Inventory Management REST API built with Java and Spring Boot. The project is designed to manage products, categories, suppliers, customers, stock movements, authentication, and role-based access control while following clean backend engineering practices.

---

# Features

## Authentication & Authorization

* JWT-based authentication
* Refresh token support
* Role-based access control (Admin / User)
* Secure login and registration system
* Spring security integration
* Password encryption using BCrypt

## Refresh Token Workflow
The API implements a complete refresh token mechanism:
* Generate access and refresh tokens during login
* Persist refresh tokens securely
* Validate refresh token expiration
* Revoke invalid or expired refresh tokens


## Inventory Management

* Product management
 - Create products
 - Update products
 - Delete products
 - Search products
 - Pagination and sorting
 - Product categorization
  
* Category management
* Supplier management
* Customer management
* Stock in / stock out tracking
* Inventory quantity updates
* Search, filtering, pagination, and sorting

## API Features

* RESTful API architecture
* Request validation
* Global exception handling
* Standardized API responses
* Auditing support

## Event-Driven Order Processing
The order creation workflow has been refactored using Spring Events.
When an order is successfully created:
* Order is persisted
* OrderCreatedEvent is published
* Event listener processes the event
* Receipt PDF is generated
* Receipt is emailed to the customer

## PDF Receipt Generation
Automatically generates professional PDF receipts containing:
* Order information
* Customer information
* Purchased products
* Quantities
* Unit prices
* Total amount
Generated receipts are attached to customer emails.

## Email Notifications
SMTP-based email integration.
Supported features:
* Receipt delivery
* PDF attachment support
* Automated order confirmation emails
* Event-driven email sending

## Stock Tracking
The system automatically tracks inventory changes.
Supported stock operations:

# Stock In
Increase inventory when:
* New inventory arrives
* Supplier deliveries are received
  
# Stock Out
Decrease inventory when:
*Customer orders are placed

# Stock Movement History
Every inventory change is recorded with:
* Product
* Quantity
* Movement type
* Timestamp
* User information
This provides a complete inventory audit trail.

## Rate Limiting
The API uses Bucket4j to protect endpoints from abuse.

Subscription Plans:
# BASIC Plan
5 requests per minute

# PROFESSIONAL Plan
10 requests per minute

Rate Limit Headers
Responses include:
* X-RateLimit-Limit
* X-RateLimit-Remaining
* Retry-After

# Benefits
* Prevent API abuse
* Fair resource usage
* Protect backend services
* Improve system stability

  

## Auditing
The project includes basic auditing functionality:

* `createdAt`
* `updatedAt`
* `createdBy`
* `updatedBy`

## Security

* Spring Security integration
* JWT token authentication
* Protected endpoints
* Role-based endpoint access

---

# Tech Stack

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Maven

## Database

* MySQL

## Tools & Utilities
* Postman
* Git & GitHub

---

# Project Structure

```bash
src
 ┣ main
 ┃ ┣ java
 ┃ ┃ ┗ com.fizoind.stockflow_api
 ┃ ┃    ┣ auditing
 ┃ ┃    ┣ authentication
 ┃ ┃    ┣ category
 ┃ ┃    ┣ common
 ┃ ┃    ┣ configuration
 ┃ ┃    ┣ customer
 ┃ ┃    ┣ exception
 ┃ ┃    ┣ filter
 ┃ ┃    ┣ order
 ┃ ┃    ┣ orderItem
 ┃ ┃    ┣ payment
 ┃ ┃    ┣ product
 ┃ ┃    ┣ stockmovement
 ┃ ┃    ┣ supplier
 ┃ ┃    ┣ user
 ┃ ┃    ┗ StockflowApiApplication
 ┃ ┗ resources
 ┗ test
```

---

# Core Modules

## Authentication

Handles:

* User registration
* Login
* JWT authentication
* Role-based authorization

## Product Management

Handles:

* Product CRUD operations
* Product stock tracking
* Product search and filtering

## Category Management

Handles:

* Product categorization
* Category CRUD operations

## Supplier Management

Handles:

* Supplier records
* Supplier-product relationships

## Customer Management

Handles:

* Customer information management
* Customer order relationships

## Order & Order Item Management

Handles:

* Customer orders
* Order items
* Purchase tracking

## Stock Movement

Handles:
* Stock in
* Stock out
* Inventory movement history

## Common Package

Contains shared utilities and reusable components.

## Configuration Package

Contains Spring Boot configuration classes.

## Filter Package

Contains request/response filtering and logging.

## Exception Package

Contains custom exceptions and global exception handling.

## Auditing Package

Contains auditing configuration and reusable audit fields.

---

# API Modules

## Authentication

* Register user
* Login user
* JWT token generation

## Products

* Create product
* Update product
* Delete product (soft delete)
* Get all products
* Search products

## Categories

* Create category
* Update category
* Delete category
* View categories

## Suppliers

* Add supplier
* Update supplier
* Delete supplier
* View suppliers

## Customers

* Add customer
* Update customer
* Delete customer
* View customers

## Stock Management

* Stock In
* Stock Out
* Inventory tracking
* Quantity management

## Order

* create order
* cancel order
* get all orders
* get all orders of certain customer
* update order status(by admin)
* get orders by status(either PENDING, CANCELLED, SHIPPED)
* GET order of certain customer

---



# Validation & Exception Handling

The API uses:

* Bean Validation on supplier dto(`@Valid`)
* Custom exception handling
* Global exception responses

This ensures:

* Clean error messages
* Better API consistency
* Improved client-side debugging

---

# Environment Variables

Sensitive configuration values are managed using environment variables.

Example:

```properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

---

# Getting Started

## Prerequisites

* Java 21
* Maven
* MySQL
* Git

---

# Installation

## 1. Clone the repository

```bash
git clone https://github.com/your-username/stockflow-api.git
cd stockflow-api
```

## 2. Configure database

Create a MySQL database:

```sql
CREATE DATABASE stockflow_db;
```

---

## 3. Configure environment variables

Set the following variables in your IDE or operating system:

```env
DB_USER=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_secret_key
```

---

## 4. Run the application

```bash
mvn clean install
mvn spring-boot:run
```

Application will start at:

```bash
http://localhost:5670
```

---

# Example API Endpoints

## Authentication

```http
POST /auth/register
POST /auth/login
```

## Products

```http
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}
```

## Categories

```http
GET    /categories
POST   /categories
PUT    /categories/{id}
DELETE /categories/{id}
```

---

# Testing the API

You can test the API using:

* Postman
* Insomnia
* Swagger (if enabled)

---

# Future Improvements

* Docker support
* pagination & sorting
* Redis caching
* Email notifications
* Report generation
* Unit and integration testing
* API documentation with Swagger/OpenAPI
* React frontend integration
* Cloud deployment

---

# Author

Developed as a backend inventory management system project using Spring Boot and modern backend development practices.

---

# License

This project is open-source and available for learning and portfolio purposes.
