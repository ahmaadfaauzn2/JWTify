# JWTify

A Spring Boot application using PostgreSQL for database management. This project just for learning JWT.

## Tech Stack

- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **API Documentation**: Swagger UI

## Features

- User authentication and profile management using JWT
- Transactions, top-up functionality, and transaction history
- Access to available services, banners, and balance details
- Interactive API documentation via Swagger UI


# Security: JSON Web Token (JWT) Authentication
This application uses JWT (JSON Web Token) for secure, stateless user authentication. The JWT mechanism ensures that users are authenticated without needing to store session data on the server. Here’s how it works:
1. **Token Generation :** Upon successful login via the /login endpoint, the server generates a JWT, which includes encoded user details and a signature for data integrity. The token is then sent to the client.
2. **Token-Based Access:** The client includes the JWT in the Authorization header as a Bearer token for all subsequent requests to protected endpoints. But you can use the JWT in the top right Lock Symbol to use the token. (e.g., /profile, /transaction/history), like this:

```
Authorization: Bearer <your_jwt_token_here>
 ```
3. **Token Validation:** The server verifies the JWT on each request to a protected endpoint. If the token is valid and unexpired, access is granted. This approach allows the application to remain stateless and improves scalability.
4. **Token Expiration:** Tokens expire after a specific period (e.g., 12 hours), at which point the user must reauthenticate.



## Prerequisites

Ensure you have the following installed:

- Java 17 or above
- Maven or Gradle (your preferred build tool)
- PostgreSQL

## Getting Started

## 1. Clone the Repository

```
git clone https://github.com/ahmaadfaauzn2/hometest-java-program.git
```

## 2. Configure the Application

Open src/main/resources/application.properties and update the database connection settings:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/hometestdb
spring.datasource.username=hometestuser
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## 3. Build the Application

# Using Maven:

```
mvn clean install
```

# Using Gradle:

```
gradle build
```

## 4. Run the Application

To start the Spring Boot application, use one of the following commands:

# With Maven:

```
mvn spring-boot:run
```
# With Gradle:

```
gradle bootRun
```

The application will run by default at http://localhost:8080.

# 5. Access the API
Once the application is running, you can view and test the API endpoints via Swagger UI at:

```
https://hometest-java-program-production.up.railway.app/swagger-ui/index.html#/
```

# Test the API 

Heres the API Documentation API you can try !
https://hometest-java-program-production.up.railway.app/swagger-ui/index.html#/

# API Endpoints
# User Management:

- POST /registration: Register a new user
- POST /login: Log in with registered user credentials
- GET /profile: Retrieve the current user's profile
- PUT /profile/update: Update user profile information
- PUT /profile/image: Update the user profile image
- Transactions:

- POST /transaction: Create a new transaction
- POST /topup: Top up the user's balance
- GET /transaction/history: Retrieve transaction history
- Services and Other:

- GET /services: List all available services
- GET /banner: Retrieve banner information
- GET /balance: Retrieve current user balance



