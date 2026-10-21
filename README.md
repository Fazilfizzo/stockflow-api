# StockFlow API

StockFlow API is a Spring Boot-based inventory and order management backend designed for retail, warehouse, and stock operations. It provides secure authentication, product and inventory tracking, supplier and customer management, order processing, pricing/payment support, invoicing and receipts, and file/email-driven workflows.

## Why this project

This project is built to handle real-world inventory and order operations in a clean backend architecture. It combines secure access control, audit-friendly persistence, stock movement tracking, and operational automation in one API layer.

## Core capabilities

- Product, category, supplier, and customer management
- Inventory stock-in and stock-out tracking
- Order creation and lifecycle management
- Pricing and payment-ready architecture
- PDF receipt generation
- Email notifications and automated communication flows
- JWT-based authentication and refresh-token support
- Global validation and exception handling
- Dockerized local development setup

## Features

### Authentication & security

- JWT-based authentication
- Refresh token support
- Role-based access control
- Password hashing with BCrypt
- Spring Security integration
- Request validation and exception handling
- Rate limiting with Bucket4j
- Audit metadata for created/updated actions

### Inventory management

- Product CRUD operations
- Product search, filtering, sorting, and pagination
- Category management
- Supplier management
- Customer management
- Stock movement tracking with movement history
- Inventory quantity updates and audit trail

### Order management

- Create and manage customer orders
- Track order items
- View orders by customer or status
- Handle order status transitions
- Event-driven order processing for downstream workflows

### Billing & payment

- Pricing module support
- Payment flow integration via Stripe SDK
- Billing-related backend support for invoice and payment workflows

### Receipt & communication

- PDF receipt generation with OpenPDF
- Automated order confirmation and receipt delivery
- Email sending with SMTP/Resend integration
- File upload support for generated documents and media

## Tech stack

### Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

### Additional libraries

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

## Project structure

```bash
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── fizoind/
│   │           └── stockflow_api/
│   │               ├── auditing
│   │               ├── authentication
│   │               ├── cart
│   │               ├── cartItem
│   │               ├── category
│   │               ├── common
│   │               ├── configuration
│   │               ├── customer
│   │               ├── dashboard
│   │               ├── email
│   │               ├── exception
│   │               ├── file
│   │               ├── filter
│   │               ├── interceptor
│   │               ├── invoice
│   │               ├── order
│   │               ├── orderItem
│   │               ├── payment
│   │               ├── pricing
│   │               ├── product
│   │               ├── receipt
│   │               ├── retry
│   │               ├── schedule
│   │               ├── stockmovement
│   │               ├── supplier
│   │               ├── user
│   │               └── StockflowApiApplication.java
│   └── resources/
│       └── application.properties
├── test/
├── Dockerfile
├── compose.yaml
├── pom.xml
├── README.md
└── uploads/
```

## Main modules

### Authentication module
Handles user registration, login, JWT issuance, refresh-token logic, and role-based access control.

### Product & inventory module
Manages product records, stock movement, inventory history, and quantity updates.

### Order module
Controls order creation, status management, customer order lookup, and downstream processing.

### Receipt & email module
Generates PDF documents and sends automated notifications after order-related events.

### Payment & pricing module
Supports pricing logic and payment-related integrations for business transactions.

### Dashboard & reporting module
Provides operational visibility and summary data for business workflows.

## Configuration

The application is configured through environment variables for database, JWT, email, and payment settings.

Example:

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

## Getting started

### Prerequisites

- Java 21
- Maven
- MySQL
- Docker (optional, for container based setup)
- Git

### Run locally

```bash
git clone https://github.com/Fazilfizzo/stockflow-api.git
cd stockflow-api
mvn clean install
mvn spring-boot:run
```

The app runs on:

```bash
http://localhost:5670
```

### Run with Docker

```bash
docker compose up --build
```

This starts:

- MySQL database
- StockFlow API application
- Upload volume for files and generated documents

## Database setup

Create the database:

```sql
CREATE DATABASE stock_flow_db;
```

## Example API areas

The project includes endpoints for:

- authentication
- product management
- category management
- supplier management
- customer management
- stock movement
- order management
- payment and pricing operations
- receipt generation
- file upload and email workflows

## Development notes

The architecture follows a modular backend pattern with DTOs, service-layer logic, JPA repositories, centralized exception handling, validation, and event-driven processing.

This helps keep the API scalable, maintainable, and suitable for real-world operational workflows.

## Future improvements

Planned and possible enhancements include:

- expanded automated tests
- Swagger/OpenAPI documentation
- Redis caching
- improved analytics dashboards
- deployment automation
- frontend integration

## License

This project is intended for educational and portfolio use.

## Author

Developed as a backend inventory and operations management application using Java and Spring Boot.

---

For the latest implementation details, check the repository commit history and source code in this project.
