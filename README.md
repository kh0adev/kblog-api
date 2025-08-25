# KBlog API

KBlog API is a RESTful backend built with **Spring Boot**, designed for a blogging platform. It handles user authentication, blog post management, and basic comment functionality.

> Technologies: Spring Boot · Java · Spring Security · JWT · JPA · H2 Database

---

## 🚀 Features

- User authentication with JWT (login/signup)
- Secure access to protected routes
- CRUD operations for blog posts
- In-memory H2 database for development/testing
- RESTful endpoints with clean architecture

---

## 🛠️ Tech Stack

| Tech             | Purpose                         |
|------------------|----------------------------------|
| Spring Boot      | Java backend framework          |
| Spring Security  | Authentication & authorization  |
| JWT              | Token-based user auth           |
| JPA (Hibernate)  | ORM for data persistence        |
| H2 Database      | Lightweight in-memory DB        |

---

## 📦 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/kh0adev/kblog-api.git
cd kblog-api
```

### 2. Run the Application

Make sure you have Java 17+ and Maven installed.

```bash
./mvnw spring-boot:run
```
## ⚙️ Configuration

You can customize environment variables inside application.properties:

```properties
# H2 settings
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 🔐 Authentication

### ✅ Signup

**POST** `/api/auth/register`

**Request Body:**
```json
{
  "userName": "john",
  "password": "password123",
  "firstName": "John",
  "lastName": "Dang",
  "email": "John@abc",
  "phoneNumber": "+8888888",
}
```
### 🔑 Login

 
**POST** `/api/auth/login`

**Response:**
```json
{
  "token": "JWT_TOKEN_HERE"
}
```

## 📘 API Endpoints

### 🧑 Auth

| Method | Endpoint             | Description           |
|--------|----------------------|-----------------------|
| POST   | /api/auth/register   | Register new user     |
| POST   | /api/auth/login      | Login and get JWT     |

### 📝 Posts

| Method | Endpoint             | Description              |
|--------|----------------------|--------------------------|
| GET    | /api/posts           | Get all posts            |
| GET    | /api/posts/{id}      | Get single post          |
| GET    | /api/posts/own       | Get my post              |
| POST   | /api/posts           | Create new post (auth)   |
| PUT    | /api/posts/{id}      | Update post (auth)       |
| GET    | /api/posts/pending   | Get all pending posts (ADMIN)       |
| PUT    | /api/posts/{id}/approve      | Approve post (ADMIN)       |
| PUT    | /api/posts/{id}/reject      | Reject post (ADMIN)       |
| PUT    | /api/posts/{id}      | Delete post (auth) - future     |

### 💬 Comments (future)


## 🧪 H2 Database Console

You can access the H2 web console at:

http://localhost:8080/h2-console


**JDBC URL:** `jdbc:h2:mem:testdb`  
**Username:** `sa`  
**Password:** *(leave empty)*

## 🧑‍💻 Contributing

Contributions, issues, and feature requests are welcome!  
Feel free to open a pull request or submit an issue.


## 📬 Contact

Created by @kh0adev

Feel free to reach out!
