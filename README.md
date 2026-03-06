# 🛒 E-Commerce Platform

## 📌 Overview

This repository contains a full-stack E-Commerce application built with Spring Boot. It utilizes a highly optimized **hybrid architecture**: Spring MVC controllers are used strictly to serve initial JavaServer Pages (JSP), while all dynamic data interactions, form submissions, and state changes are handled asynchronously via RESTful APIs and AJAX.

The system provides a complete suite of services to manage users, products, categories, shopping carts, and orders. It enforces Role-Based Access Control (RBAC) using JWT authentication to securely separate Admin and User capabilities.

## 🛠️ Technology Stack

### Backend

* **Java 17**
* **Spring Boot 3.x** (Web, Security, Data JPA)
* **Spring Security & JWT** (JSON Web Tokens)
* **Database:** MySQL / PostgreSQL
* **Build Tool:** Maven

### Frontend

* **JSP** (JavaServer Pages) for view routing
* **JavaScript** (AJAX / Fetch API) for asynchronous API calls
* **HTML5 / CSS3 / Bootstrap**

## ✨ Key Features

### 🔐 Authentication & Authorization

* Secure user registration and login via REST APIs.
* Stateless session management using JWT.
* Role-based access control with distinct `ADMIN` and `USER` roles.

### 📦 Product & Category Management

* **Admin:** Create, update, and delete products and categories dynamically without page reloads.
* **User:** Browse all products and categories, and view specific product details.
* Automated linking between categories and products (One-to-Many).

### 🛒 Cart Management

* Dedicated cart for each logged-in user (One-to-One).
* Users can add products, update quantities, and remove items instantly via API calls.
* Automatic calculation of the total cart amount.

### 🧾 Order Management

* Seamless checkout process converting a User's Cart into a finalized Order.
* Automatic clearing of the cart upon successful order placement.
* Order status tracking (`CREATED`, `PAID`, `SHIPPED`, `DELIVERED`).
* Users can view specific order details and their complete order history.

---

## 🗄️ Database Schema & Relationships

The application relies on the following core entities and relationships:

* **Category → Products:** One-to-Many
* **User → Cart:** One-to-One
* **User → Orders:** One-to-Many
* **Order → OrderItems:** One-to-Many
* **Cart → CartItems:** One-to-Many

**Mandatory Tables:** `User`, `Role`, `Product`, `Category`, `Cart`, `CartItem`, `Order`, `OrderItem`.

---

## 📡 REST API Reference

*(All API calls below are consumed by the JSP frontend via AJAX after the initial page load)*

### Authentication

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Authenticate user & get JWT | Public |

### Products

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | `/api/products` | Get all products | Public/User |
| GET | `/api/products/{id}` | Get product details by ID | Public/User |
| POST | `/api/admin/products` | Add a new product | Admin |
| PUT | `/api/admin/products/{id}` | Update existing product | Admin |
| DELETE | `/api/admin/products/{id}` | Delete a product | Admin |

### Categories

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | `/api/categories` | View all categories | Public/User |
| POST | `/api/admin/categories` | Create a new category | Admin |

### Cart

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| GET | `/api/cart` | View current user's cart | User |
| POST | `/api/cart/add` | Add product to cart | User |
| PUT | `/api/cart/update` | Update item quantity | User |
| DELETE | `/api/cart/remove/{itemId}` | Remove item from cart | User |

### Orders

| Method | Endpoint | Description | Access |
| --- | --- | --- | --- |
| POST | `/api/orders` | Place order from cart | User |
| GET | `/api/orders/user` | View order history | User |
| GET | `/api/orders/{id}` | View specific order details | User |

---

## 🏗️ Architecture & Best Practices

* **Hybrid Routing Strategy:** Strict separation between standard `@Controller` classes (which only return `ModelAndView` or view names for JSP rendering) and `@RestController` classes (which strictly process JSON data for the frontend).
* **Layered Architecture:** Clear separation of concerns using Controllers, Services, and Repositories.
* **Exception Handling:** Global exception handler (`@ControllerAdvice`) for unified and clean API error JSON responses.

---

## ⚙️ Local Development Setup

### Prerequisites

* Java 17 installed
* Maven installed
* PostgreSQL running locally

### 1. Database Configuration

Update the `src/main/resources/application.properties` file with your database credentials:

```properties
# Database connection
spring.datasource.url=jdbc:postgresql://localhost:5432/ecom_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret
jwt.secret=your_super_secret_key_for_jwt_generation_make_it_long
jwt.expiration=86400000

```

### 2. Build and Run

Clone the repository and run the application using Maven:

```bash
git clone https://github.com/your-username/ecommerce-platform.git
cd ecommerce-platform
mvn clean install
mvn spring-boot:run

```

The server will start on `http://localhost:8080`.
