# StockFlow API

StockFlow API is a Spring Boot-based inventory and order management backend designed for modern retail and stock operations. It provides secure authentication, inventory tracking, customer and supplier management, order workflows, payment/pricing logic, and automated receipt delivery.

## Overview

The project is built with Java 21 and Spring Boot 3, using MySQL as the primary database. It supports both standard REST endpoints and integrates with Docker for local development.

StockFlow API is designed to manage:

- Products, categories, customers, and suppliers
- Inventory stock-in and stock-out operations
- Order lifecycle management
- Invoice and receipt generation
- Email notifications and notifications workflows
- Secure JWT-based access control
- Payment and pricing features
- File uploads and document handling

## Key Features

### Authentication & Authorization

- JWT-based authentication
- Refresh token support
- Role-based access control
- Secure registration and login flow
- Password hashing with BCrypt
- Request authentication via Spring Security filters

### Inventory Management

- Product CRUD operations
- Category management
- Supplier management
- Customer management
- Stock movement tracking
- Inventory audit trail
- Quantity updates and stock history

### Order Management

- Create, update, and view orders
- Order item tracking
- Customer-specific order retrieval
- Order status handling
- Audit-friendly transactional flows
- Event-driven order processing

### Billing, Pricing & Payment

- Pricing configuration and logic
- Payment support via Stripe integration
- Billing-related modules for invoicing and payment flows
- Customer-facing financial workflows

### Receipt & Notification System

- PDF receipt generation with OpenPDF
- Automatic customer invoice/receipt creation
- Email sending through SMTP and Resend
- Notification-driven order completion workflows
- File upload support for document-related workflows

### Security & Reliability

- Global exception handling
- Standardized API responses
- Validation on incoming requests
- Rate limiting using Bucket4j
- Correlation IDs and logging support
- Auditing fields for created/updated metadata

### Infrastructure & DevOps

- Docker support via Dockerfile and Compose
- MySQL container setup
- Environment-variable-based configuration
- Upload directory support for generated files and media

## Tech Stack

### Backend

- Java 21
- Spring Boot 3.3.x
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

### Additional Libraries

- JWT (jjwt)
- Bucket4j
- OpenPDF
- Stripe Java SDK
- Resend Java SDK
- Twilio SDK
- dotenv-java
- Lombok
- Spring Validation

### Tools

- Docker
- Docker Compose
- Git & GitHub
- Postman / Insomnia

## Project Structure

```bash
src/
├── main/
│   └── java/
│       └── com/
│           └── fizoind/
│               └── stockflow_api/
│                   ├── auditing
│                   ├── authentication
│                   ├── cart
│                   ├── cartItem
│                   ├── category
│                   ├── common
│                   ├── configuration
│                   ├── customer
│                   ├── dashboard
│                   ├── email
│                   ├── exception
│                   ├── file
│                   ├── filter
│                   ├── interceptor
│                   ├── invoice
│                   ├── order
│                   ├── orderItem
│                   ├── payment
│                   ├── pricing
│                   ├── product
│                   ├── receipt
│                   ├── retry
│                   ├── schedule
│                   ├── stockmovement
│                   ├── supplier
│                   ├── user
│                   └── StockflowApiApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Core Modules

### Authentication Module

Handles user registration, login, JWT issuance, token refresh, and access control.

### Product & Inventory Module

Manages product records, stock quantity updates, stock movement tracking, and inventory history.

### Category, Supplier & Customer Modules

Support business entity management needed for modern inventory workflows.

### Order & Receipt Module

Controls customer orders, order items, invoice generation, and receipt-related automation.

### Payment & Pricing Module

Provides pricing logic and payment integrations for financial operations.

### Dashboard & Analytics Module

Supports operational visibility and summary data for business activity.

### File & Email Module

Handles upload workflows, generated receipts, and email notifications.

## Configuration

The application uses environment variables for sensitive configuration.

Example variables:

```env
PORT=5670
DB_USER=your_db_user
DB_PASSWORD=your_db_password
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/stock_flow_db
JWT_SECRET=your_jwt_secret
EMAIL_USERNAME=your_email@example.com
EMAIL_PASSWORD=your_email_password
RESEND_API_KEY=your_resend_key
RESEND_FROM=onboarding@resend.dev
FRONTEND_ORIGIN=http://localhost:3000
IMAGE=http://localhost:5670
STRIPE_SECRET_KEY=your_stripe_secret
STRIPE_WEBHOOK_SECRET=your_stripe_webhook_secret
```

## Running the Application

### Prerequisites

- Java 21
- Maven
- MySQL
- Docker (optional, for containerized setup)
- Git

### Local Run

```bash
git clone https://github.com/Fazilfizzo/stockflow-api.git
cd stockflow-api
mvn clean install
mvn spring-boot:run
```

The application runs by default on:

```bash
http://localhost:5670
```

### Docker Run

```bash
docker compose up --build
```

This sets up:

- MySQL database service
- StockFlow API application service
- Shared upload volume

## Database Setup

Create a MySQL database:

```sql
CREATE DATABASE stock_flow_db;
```

The project is configured to use the database name `stock_flow_db` unless you override it in environment variables.

## Example API Areas

The project includes endpoints for:

- Authentication
- Product management
- Category management
- Supplier management
- Customer management
- Stock movement
- Order management
- Billing / payment workflows
- Receipt generation
- File uploads

## Notes on Architecture

The system emphasizes a clean layered backend structure with:

- DTOs for data transfer
- Service-layer business logic
- JPA repositories for persistence
- Exception handling for consistent API behavior
- Logging and audit support
- Event-driven processing for order workflows and associated notifications

## Future Enhancements

The project continues to evolve with opportunities such as:

- stronger test coverage
- Swagger/OpenAPI documentation
- Redis caching
- improved analytics/dashboard reporting
- deployment automation
- frontend integration

## License

This project is intended for educational and portfolio use, and is open for learning purposes.

## Author

Developed as a backend inventory and workflow management application using Java and Spring Boot.

---

For the latest project status and implementation changes, see the repository commit history and source structure in this project.
