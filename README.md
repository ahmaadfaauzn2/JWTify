HomeTest Java Program
A simple Spring Boot application that uses PostgreSQL for database management. This project provides a home testing system with API endpoints for interacting with user profiles, transactions, and services, including Swagger UI for easy testing of API functionality.

Tech Stack
Backend: Spring Boot
Database: PostgreSQL
API Documentation: Swagger UI
Features
Authentication and profile management.
Transactions, top-up functionality, and transaction history.
Access to available services, banners, and balance details.
API documentation available via Swagger UI.
Prerequisites
Before you start, ensure you have the following installed:

Java 17 or above
Maven or Gradle (depending on your build tool preference)
PostgreSQL
Getting Started
1. Clone the Repository
bash
Copy code
git clone https://github.com/ahmaadfaauzn2/hometest-java-program.git
2. Configure Application
Navigate to src/main/resources/application.properties and configure the database connection properties:

properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/hometestdb
spring.datasource.username=hometestuser
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
If you're using another environment (e.g., Railway, Heroku), make sure to update the application.properties with the provided connection strings.

3. Build the Application
Using Maven:

bash
Copy code
mvn clean install
Using Gradle:

bash
Copy code
gradle build
4. Run the Application
To start the Spring Boot application, run the following command:

bash
Copy code
mvn spring-boot:run
or

bash
Copy code
gradle bootRun
By default, the app will run on http://localhost:8080.

5. Access the API
Once the application is up and running, you can view and test the available API endpoints using Swagger UI at the following link:

Swagger UI Documentation

API Endpoints
POST /registration: Register a new user.
POST /login: Login with registered user credentials.
GET /profile: Get the current user's profile.
PUT /profile/update: Update the current user's profile information.
PUT /profile/image: Update the profile image of the user.
POST /transaction: Create a new transaction.
POST /topup: Top up the user's balance.
GET /transaction/history: Retrieve the transaction history of the user.
GET /services: List all available services.
GET /banner: Retrieve the banner information.
GET /balance: Retrieve the user's current balance.
Contributing
Feel free to fork this repository and create a pull request if you'd like to contribute improvements, bug fixes, or new features.

License
This project is licensed under the MIT License - see the LICENSE file for details.

