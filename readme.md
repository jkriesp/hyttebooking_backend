# Hyttebooking Application

## Overview
Hyttebooking is a cabin booking application designed to manage reservations for family-owned cabins. This project encompasses both the backend service, built with Spring Boot, providing a RESTful API, and the frontend component (to be developed), which interacts with this API.

### Frontend
The frontend component will be developed using React and will provide a user interface for the application. The frontend will allow users to view available cabins, book them, and manage their bookings.
- [Frontend Repository](https://github.com/jkriesp/hyttebooking)
## Features
- User management: Create, update, and delete users.
- Cabin management: Add, update, and remove cabin details.
- Booking management: Users can book cabins, view their bookings, and cancel them.

## Getting Started

### Prerequisites
- Java JDK 11 or later
- Maven 3.6 or later
- PostgreSQL for the production database
- H2 database for development testing

### Setting Up the Project
1. Clone the repository:
   ```sh
   git clone https://yourrepositoryurl.com/hyttebooking.git
   cd hyttebooking
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   java -jar target/hyttebooking-0.0.1-SNAPSHOT.jar
   ```
   Alternatively, you can run the application directly through Maven:
   ```sh
   mvn spring-boot:run
   ```

### Configuration
- The application's configuration is found in the `application.properties` file located in the `src/main/resources` directory.
- Configure the database connection settings appropriate to your environment in the `application-dev.properties` and `application-prod.properties` files.

## API Documentation
The REST API documentation is automatically generated via SpringDoc OpenAPI and can be accessed at `http://localhost:8080/swagger-ui.html` when the application is running.

## Development
- Use the H2 database for development purposes. The H2 console can be accessed at `http://localhost:8080/h2-console`.
- For production, configure the PostgreSQL database connection settings.

## Testing
- Run unit and integration tests with Maven:
  ```sh
  mvn test
  ```

## Deployment
- Build a production-ready JAR file:
  ```sh
  mvn clean package
  ```
- Deploy the JAR file to your production environment and start the application with:
  ```sh
  java -jar target/hyttebooking-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
  ```

## Links
- Project Link: [Not_yet_live](https://yourrepositoryurl.com/hyttebooking)
