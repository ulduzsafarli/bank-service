# Bank Service Project

## Overview

The Bank Service Project is a backend application that manages various banking operations, including cards, deposits, transactions, and notifications. It leverages a robust tech stack featuring Java, Spring Boot, and PostgreSQL, delivering scalable and reliable solutions for banking services.

## Key Features

- **Card Management**: Handle multiple types of bank cards and related operations.
- **Transaction Processing**: Execute and monitor banking transactions.
- **Deposit Management**: Manage deposits with flexible interest options (monthly and annual).
- **Notifications**: Manage and send notifications for various banking activities.

## Technologies

- **Java 21**: Backend development.
- **Spring Boot 3.3.0**: Framework for application and dependency management.
- **Spring Security**: Authentication and authorization.
- **PostgreSQL**: Database for data management.
- **JJWT**: Handling JSON Web Tokens (JWT).
- **Lombok**: Reduces boilerplate code in the Java codebase.
- **Liquibase**: For managing database migrations.
- **Gradle**: Build automation.

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/ulduzsafarli/bank-service.git
   cd bank-service
   ```

2. **Configure Application Properties**

   Add `application.properties` or `application.yml` under `src/main/resources` and configure your database:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bank_service
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Build the Project**

   Build the project with Gradle:

   ```bash
   ./gradlew build
   ```

4. **Run the Application**

   Start the application:

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
- **Update User**: `PUT /api/v1/users/{id}`
- **Delete User**: `DELETE /api/v1/users/{id}`

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
- **Update Account**: `PUT /api/v1/accounts/{id}`
- **Delete Account**: `DELETE /api/v1/accounts/{id}`
- **Change Account Status**: `PUT /api/v1/accounts/{id}/status/{status}`

### Support

- **Create Request**: `POST /api/v1/support/request`
- **View Requests**: `GET /api/v1/support/requests`
- **Respond**: `PUT /api/v1/support/respond/{id}`
- **Unanswered Requests**: `GET /api/v1/support/unanswered-requests`

### Notifications

- **View Notifications**: `GET /api/v1/notifications`
- **Create Notification**: `POST /api/v1/notifications`
- **Update Notification**: `PUT /api/v1/notifications/{id}`
- **Delete Notification**: `DELETE /api/v1/notifications/{id}`

### Currency

- **View Currency Info**: `GET /api/v1/currency`
- **Upload Currency File**: `POST /api/v1/currency/file`

## How to Use

1. **Authentication**: Ensure proper authentication configurations are set.
2. **Operations**: Use the API to perform balance checks, transfers, and more operations.
3. **Error Handling**: Detailed error messages and logs are available to assist in troubleshooting.

## Contact Information

For queries or assistance, please reach out via:

- **Email**: [ulduzsafarli.dv@gmail.com](mailto:ulduzsafarli.dv@gmail.com)
