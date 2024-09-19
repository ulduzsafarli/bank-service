# Bank Service Project

## Overview

The Bank Service Project is a comprehensive backend application designed for managing banking operations. It includes functionality for handling credits, cards, transactions, deposits, and notifications. This project leverages Java, Spring Boot, and PostgreSQL to provide a robust and scalable solution for banking services.

## Features

- **Cards Management**: Manage different types of bank cards and their operations.
- **Transactions Processing**: Process and track banking transactions.
- **Deposits Management**: Manage deposits with monthly and annual interest rates.
- **Notifications**: Send notifications related to banking operations.

## Technologies Used

- **Java 21**: The primary programming language for backend development.
- **Spring Boot 3.3.0**: For building the application and managing dependencies.
- **Spring Security**: For authentication and authorization.
- **PostgreSQL**: Database for storing banking data.
- **JJWT**: For handling JSON Web Tokens.
- **Lombok**: For reducing boilerplate code.
- **Liquibase**: For database migrations.
- **Gradle**: Build automation tool.

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/ulduzsafarli/bank-service.git
   cd bank-service
   ```

2. **Configure Application Properties**

   Create an `application.properties` or `application.yml` file in the `src/main/resources` directory and configure your database and other settings:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bank_service
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Build the Project**

   Use Gradle to build the project:

   ```bash
   ./gradlew build
   ```

4. **Run the Application**

   Run the application using:

   ```bash
   ./gradlew bootRun
   ```

## API Endpoints

### Operations
- **Transfer**: `POST /api/v1/operations/transfer`
- **Withdrawal**: `POST /api/v1/operations/withdrawal`
- **Check Balance**: `GET /api/v1/operations/{accountNumber}/balance`

### Users
- **Get All Users**: `GET /api/v1/users`
- **Search Users**: `GET /api/v1/users/search`
- **Get User by ID**: `GET /api/v1/users/{id}`
- **Update User by ID**: `PUT /api/v1/users/{id}`
- **Delete User by ID**: `DELETE /api/v1/users/{id}`

### Transactions
- **Search Transactions**: `GET /api/v1/transactions/search`
- **Get Transaction by ID**: `GET /api/v1/transactions/{id}`

### Authentication
- **Authenticate**: `POST /api/v1/auth/authenticate`
- **Change Password**: `POST /api/v1/auth/change-password`
- **Register**: `POST /api/v1/auth/register`

### Exchange
- **Exchange from AZN**: `GET /api/v1/exchange/from-AZN`
- **Exchange to AZN**: `GET /api/v1/exchange/to-AZN`

### Accounts
- **Get All Accounts**: `GET /api/v1/accounts`
- **Search Accounts**: `GET /api/v1/accounts/search`
- **Get Account by ID**: `GET /api/v1/accounts/{id}`
- **Update Account by ID**: `PUT /api/v1/accounts/{id}`
- **Delete Account by ID**: `DELETE /api/v1/accounts/{id}`
- **Update Account Status**: `PUT /api/v1/accounts/{id}/status/{status}`

### Support
- **Create Support Request**: `POST /api/v1/support/request`
- **Get All Support Requests**: `GET /api/v1/support/requests`
- **Respond to Support Request**: `PUT /api/v1/support/respond/{id}`
- **Get Unanswered Requests**: `GET /api/v1/support/unanswered-requests`

### Notifications
- **Get All Notifications**: `GET /api/v1/notifications`
- **Create Notification**: `POST /api/v1/notifications`
- **Get Notification by ID**: `GET /api/v1/notifications/{id}`
- **Update Notification by ID**: `PUT /api/v1/notifications/{id}`
- **Delete Notification by ID**: `DELETE /api/v1/notifications/{id}`

### Currency
- **Get Currency Information**: `GET /api/v1/currency`
- **Upload Currency File**: `POST /api/v1/currency/file`

## Usage

1. **Authentication**

   - Ensure that your application is configured with valid authentication credentials.
   - Use the provided API endpoints to perform banking operations.

2. **Error Handling**

   - The application provides detailed error messages and logs for debugging purposes.

## Contact

For any questions or support, please contact:

- **Email**: [my-email@example.com](mailto:ulduzsafarli.dv@example.com)
