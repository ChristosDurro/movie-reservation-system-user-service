# User Service - Movie Reservation System

## Overview
The **User Service** is a core microservice of the **Movie Reservation System**, responsible for handling user authentication, authorization, and profile management. It provides functionalities such as user registration, login, JWT-based authentication, and user-related data retrieval.

## Features
- **User Registration**: Users can sign up with a username, email, and password.
- **User Authentication**: Secure login using JWT-based authentication.
- **Token Management**: JWT generation and validation for secure API access.
- **User Profile Management**: Retrieve and update user information.
- **Microservices Communication**: Works with other services via REST APIs and Feign clients.

## Technologies Used
- **Spring Boot** - Backend framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database interaction
- **MySQL** - Database for storing user data
- **JWT (JSON Web Tokens)** - Secure authentication
- **Feign Clients** - Inter-service communication

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/users` | Get all users |
| GET    | `/users/id/{id}` | Get user by id |
| GET    | `/users/{identifier}` | Get user by email or username |
| GET    | `/users/validateToken` | Validate JWT token | 
| POST   | `/users/register` | Registers a new user |
| POST   | `/users/login` | Authenticates user and returns JWT token |
| PUT    | `/users/update/{id}` | Updates user |

## Setup & Installation
### Prerequisites
- Java 17+
- MySQL database
- Maven

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/ChristosDurro/movie-reservation-system-user-service.git
   ```
2. Configure the database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/userdb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## Authentication & Security
- Uses **JWT tokens** for secure authentication.
- Passwords are hashed using **BCrypt** before storage.

## Future Improvements
- Implement OAuth2 for social login options.
- Enhance security with refresh tokens.
