# PayXpert - Payroll Management System

PayXpert is a comprehensive Payroll Management System implemented in Java that handles employee data, payroll processing, tax calculations, and financial record management.

## Features

- Employee Management (CRUD operations)
- Payroll Processing
- Tax Calculation
- Financial Record Management
- Automated Salary Calculations
- Report Generation

## Prerequisites

- Java 11 or higher
- Maven
- MySQL 8.0 or higher

## Setup Instructions

1. Clone the repository
2. Create MySQL database using the SQL script in `src/main/resources/database.sql`
3. Configure database connection in `src/main/resources/database.properties`
4. Build the project:
   ```bash
   mvn clean install
   ```

## Database Configuration

Update the database connection details in `src/main/resources/database.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/payxpert
db.username=your_username
db.password=your_password
```

## Project Structure

```
payxpert/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hexaware/
│   │   │           └── payxpert/
│   │   │               ├── dao/
│   │   │               ├── entity/
│   │   │               ├── exception/
│   │   │               ├── util/
│   │   │               └── main/
│   │   └── resources/
│   └── test/
└── pom.xml
```

## Running Tests

```bash
mvn test
```

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
