-- Reset Database
DROP DATABASE IF EXISTS payxpert;
CREATE DATABASE payxpert;
USE payxpert;

-- Create Database Schema
-- Create Employee table
CREATE TABLE IF NOT EXISTS Employee (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Gender VARCHAR(10) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Address TEXT NOT NULL,
    Position VARCHAR(50) NOT NULL,
    JoiningDate DATE NOT NULL,
    TerminationDate DATE
);

-- Create Payroll table
CREATE TABLE IF NOT EXISTS Payroll (
    PayrollID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    PayPeriodStartDate DATE NOT NULL,
    PayPeriodEndDate DATE NOT NULL,
    BasicSalary DECIMAL(10,2) NOT NULL,
    OvertimePay DECIMAL(10,2) NOT NULL,
    Deductions DECIMAL(10,2) NOT NULL,
    NetSalary DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

-- Create Tax table
CREATE TABLE IF NOT EXISTS Tax (
    TaxID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    TaxYear INT NOT NULL,
    TaxableIncome DECIMAL(10,2) NOT NULL,
    TaxAmount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

-- Create FinancialRecord table
CREATE TABLE IF NOT EXISTS FinancialRecord (
    RecordID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT NOT NULL,
    RecordDate DATE NOT NULL,
    Description TEXT NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    RecordType VARCHAR(50) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

-- Insert Sample Data
-- Insert sample employee data
INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate) VALUES
('Rajesh', 'Kumar', '1990-05-15', 'Male', 'rajesh.kumar@hexaware.com', '9811234567', 'B-12, Sector 62, Noida, Uttar Pradesh', 'Senior Software Engineer', '2021-02-15'),
('Priya', 'Sharma', '1992-08-23', 'Female', 'priya.sharma@hexaware.com', '9822345678', '404, Palm Grove Apartments, Bandra West, Mumbai, Maharashtra', 'HR Manager', '2020-03-20'),
('Amit', 'Patel', '1988-11-30', 'Male', 'amit.patel@hexaware.com', '9833456789', '15, Richmond Road, Bangalore, Karnataka', 'Project Manager', '2019-06-10'),
('Sneha', 'Verma', '1995-02-18', 'Female', 'sneha.verma@hexaware.com', '9844567890', '7A, Salt Lake City, Kolkata, West Bengal', 'Business Analyst', '2023-04-05'),
('Vikram', 'Singh', '1987-07-25', 'Male', 'vikram.singh@hexaware.com', '9855678901', '22, Civil Lines, Jaipur, Rajasthan', 'Technical Lead', '2018-09-12'),
('Neha', 'Gupta', '1993-04-12', 'Female', 'neha.gupta@hexaware.com', '9866789012', 'C-503, DLF Phase 3, Gurgaon, Haryana', 'Marketing Manager', '2020-11-30'),
('Arun', 'Reddy', '1991-09-08', 'Male', 'arun.reddy@hexaware.com', '9877890123', '45, Jubilee Hills, Hyderabad, Telangana', 'Software Architect', '2017-02-15'),
('Deepika', 'Nair', '1994-12-03', 'Female', 'deepika.nair@hexaware.com', '9888901234', '28, MG Road, Kochi, Kerala', 'Quality Analyst', '2022-07-22'),
('Suresh', 'Mehta', '1989-03-28', 'Male', 'suresh.mehta@hexaware.com', '9899012345', '201, Ashok Nagar, Ahmedabad, Gujarat', 'Finance Manager', '2018-12-01'),
('Anita', 'Desai', '1992-06-15', 'Female', 'anita.desai@hexaware.com', '9800123456', '12, Koregaon Park, Pune, Maharashtra', 'Systems Engineer', '2023-01-10');

-- Insert sample payroll data (for the last month)
INSERT INTO Payroll (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) VALUES
(1, '2025-03-01', '2025-03-31', 180000.00, 15000.00, 19500.00, 175500.00),
(2, '2025-03-01', '2025-03-31', 150000.00, 8000.00, 15800.00, 142200.00),
(3, '2025-03-01', '2025-03-31', 250000.00, 20000.00, 27000.00, 243000.00),
(4, '2025-03-01', '2025-03-31', 120000.00, 5000.00, 12500.00, 112500.00),
(5, '2025-03-01', '2025-03-31', 200000.00, 18000.00, 21800.00, 196200.00),
(6, '2025-03-01', '2025-03-31', 160000.00, 12000.00, 17200.00, 154800.00),
(7, '2025-03-01', '2025-03-31', 220000.00, 25000.00, 24500.00, 220500.00),
(8, '2025-03-01', '2025-03-31', 130000.00, 8000.00, 13800.00, 124200.00),
(9, '2025-03-01', '2025-03-31', 190000.00, 15000.00, 20500.00, 184500.00),
(10, '2025-03-01', '2025-03-31', 140000.00, 10000.00, 15000.00, 135000.00);

-- Insert sample tax records for the previous year
INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES
(1, 2024, 2340000.00, 269400.00),
(2, 2024, 1896000.00, 186960.00),
(3, 2024, 3240000.00, 426000.00),
(4, 2024, 1500000.00, 126000.00),
(5, 2024, 2616000.00, 322920.00),
(6, 2024, 2064000.00, 217680.00),
(7, 2024, 2940000.00, 371100.00),
(8, 2024, 1656000.00, 146520.00),
(9, 2024, 2460000.00, 299100.00),
(10, 2024, 1800000.00, 171000.00);

-- Insert sample financial records
INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES
(1, '2025-03-31', 'Monthly Salary', 175500.00, 'credit'),
(2, '2025-03-31', 'Monthly Salary', 142200.00, 'credit'),
(3, '2025-03-31', 'Monthly Salary', 243000.00, 'credit'),
(4, '2025-03-31', 'Monthly Salary', 112500.00, 'credit'),
(5, '2025-03-31', 'Monthly Salary', 196200.00, 'credit'),
(6, '2025-03-31', 'Monthly Salary', 154800.00, 'credit'),
(7, '2025-03-31', 'Monthly Salary', 220500.00, 'credit'),
(8, '2025-03-31', 'Monthly Salary', 124200.00, 'credit'),
(9, '2025-03-31', 'Monthly Salary', 184500.00, 'credit'),
(10, '2025-03-31', 'Monthly Salary', 135000.00, 'credit');
