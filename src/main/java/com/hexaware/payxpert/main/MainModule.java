package com.hexaware.payxpert.main;

import com.hexaware.payxpert.dao.*;
import com.hexaware.payxpert.entity.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IEmployeeService employeeService = new EmployeeServiceImpl();
    private static final IPayrollService payrollService = new PayrollServiceImpl();
    private static final ITaxService taxService = new TaxServiceImpl();
    private static final IFinancialService financialService = new FinancialServiceImpl();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        while (true) {
            try {
                displayMainMenu();
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        handleEmployeeOperations();
                        break;
                    case 2:
                        handlePayrollOperations();
                        break;
                    case 3:
                        handleTaxOperations();
                        break;
                    case 4:
                        handleFinancialRecordOperations();
                        break;
                    case 0:
                        System.out.println("Thank you for using PayXpert!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== PayXpert - Payroll Management System ===");
        System.out.println("1. Employee Management");
        System.out.println("2. Payroll Management");
        System.out.println("3. Tax Management");
        System.out.println("4. Financial Record Management");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleEmployeeOperations() {
        while (true) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add New Employee");
            System.out.println("2. View Employee Details");
            System.out.println("3. Update Employee");
            System.out.println("4. Remove Employee");
            System.out.println("5. View All Employees");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        addNewEmployee();
                        break;
                    case 2:
                        viewEmployeeDetails();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        removeEmployee();
                        break;
                    case 5:
                        viewAllEmployees();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void handlePayrollOperations() {
        while (true) {
            System.out.println("\n=== Payroll Management ===");
            System.out.println("1. Generate Payroll");
            System.out.println("2. View Payroll Details");
            System.out.println("3. View Employee Payroll History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        generatePayroll();
                        break;
                    case 2:
                        viewPayrollDetails();
                        break;
                    case 3:
                        viewEmployeePayrollHistory();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void addNewEmployee() {
        try {
            Employee employee = new Employee();
            
            System.out.println("\nEnter Employee Details:");
            System.out.print("First Name: ");
            employee.setFirstName(scanner.nextLine());
            
            System.out.print("Last Name: ");
            employee.setLastName(scanner.nextLine());
            
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            employee.setDateOfBirth(LocalDate.parse(scanner.nextLine(), dateFormatter));
            
            System.out.print("Gender: ");
            employee.setGender(scanner.nextLine());
            
            System.out.print("Email: ");
            employee.setEmail(scanner.nextLine());
            
            System.out.print("Phone Number: ");
            employee.setPhoneNumber(scanner.nextLine());
            
            System.out.print("Address: ");
            employee.setAddress(scanner.nextLine());
            
            System.out.print("Position: ");
            employee.setPosition(scanner.nextLine());
            
            System.out.print("Joining Date (YYYY-MM-DD): ");
            employee.setJoiningDate(LocalDate.parse(scanner.nextLine(), dateFormatter));

            if (employeeService.addEmployee(employee)) {
                System.out.println("Employee added successfully!");
            } else {
                System.out.println("Failed to add employee.");
            }
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void viewEmployeeDetails() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee != null) {
                System.out.println("\nEmployee Details:");
                System.out.println(employee);
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Employee ID.");
        }
    }

    private static void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("First Name [" + employee.getFirstName() + "]: ");
            String input = scanner.nextLine();
            if (!input.isEmpty()) employee.setFirstName(input);
            
            System.out.print("Last Name [" + employee.getLastName() + "]: ");
            input = scanner.nextLine();
            if (!input.isEmpty()) employee.setLastName(input);
            
            // Add more fields as needed...

            if (employeeService.updateEmployee(employee)) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Failed to update employee.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Employee ID.");
        }
    }

    private static void removeEmployee() {
        try {
            System.out.print("Enter Employee ID to remove: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Are you sure you want to remove this employee? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                if (employeeService.removeEmployee(employeeId)) {
                    System.out.println("Employee removed successfully!");
                } else {
                    System.out.println("Failed to remove employee.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Employee ID.");
        }
    }

    private static void viewAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\nAll Employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
            System.out.println("-----------------");
        }
    }

    private static void generatePayroll() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
            
            System.out.print("Enter End Date (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
            
            System.out.print("Enter Basic Salary: ");
            double basicSalary = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Overtime Pay: ");
            double overtimePay = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Deductions: ");
            double deductions = Double.parseDouble(scanner.nextLine());
            
            Payroll payroll = payrollService.generatePayroll(employeeId, startDate, endDate, basicSalary, overtimePay, deductions);
            if (payroll != null) {
                System.out.println("\nPayroll generated successfully:");
                System.out.println(payroll);
            } else {
                System.out.println("Failed to generate payroll.");
            }
        } catch (Exception e) {
            System.out.println("Error generating payroll: " + e.getMessage());
        }
    }

    private static void viewPayrollDetails() {
        try {
            System.out.print("Enter Payroll ID: ");
            int payrollId = Integer.parseInt(scanner.nextLine());
            
            Payroll payroll = payrollService.getPayrollById(payrollId);
            if (payroll != null) {
                System.out.println("\nPayroll Details:");
                System.out.println(payroll);
            } else {
                System.out.println("Payroll not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Payroll ID.");
        }
    }

    private static void handleFinancialRecordOperations() {
        while (true) {
            System.out.println("\n=== Financial Record Management ===");
            System.out.println("1. Add Financial Record");
            System.out.println("2. View Financial Record");
            System.out.println("3. Update Financial Record");
            System.out.println("4. View All Financial Records");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        addFinancialRecord();
                        break;
                    case 2:
                        viewFinancialRecord();
                        break;
                    case 3:
                        updateFinancialRecord();
                        break;
                    case 4:
                        viewAllFinancialRecords();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void addFinancialRecord() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Record Date (YYYY-MM-DD): ");
            LocalDate recordDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
            
            System.out.print("Enter Description: ");
            String description = scanner.nextLine();
            
            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Enter Record Type (INCOME/EXPENSE/BONUS/DEDUCTION): ");
            String recordType = scanner.nextLine().toUpperCase();
            
            FinancialRecord record = new FinancialRecord();
            record.setEmployeeId(employeeId);
            record.setRecordDate(recordDate);
            record.setDescription(description);
            record.setAmount(amount);
            record.setRecordType(recordType);
            
            if (financialService.addFinancialRecord(record)) {
                System.out.println("Financial record added successfully!");
            } else {
                System.out.println("Failed to add financial record.");
            }
        } catch (Exception e) {
            System.out.println("Error adding financial record: " + e.getMessage());
        }
    }

    private static void viewFinancialRecord() {
        try {
            System.out.print("Enter Financial Record ID: ");
            int recordId = Integer.parseInt(scanner.nextLine());
            
            FinancialRecord record = financialService.getFinancialRecordById(recordId);
            if (record != null) {
                System.out.println("\nFinancial Record Details:");
                System.out.println(record);
            } else {
                System.out.println("Financial record not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Record ID.");
        }
    }

    private static void updateFinancialRecord() {
        try {
            System.out.print("Enter Financial Record ID: ");
            int recordId = Integer.parseInt(scanner.nextLine());
            
            FinancialRecord record = financialService.getFinancialRecordById(recordId);
            if (record == null) {
                System.out.println("Financial record not found.");
                return;
            }

            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Description [" + record.getDescription() + "]: ");
            String input = scanner.nextLine();
            if (!input.isEmpty()) record.setDescription(input);
            
            System.out.print("Amount [" + record.getAmount() + "]: ");
            input = scanner.nextLine();
            if (!input.isEmpty()) record.setAmount(Double.parseDouble(input));
            
            System.out.print("Record Type [" + record.getRecordType() + "]: ");
            input = scanner.nextLine();
            if (!input.isEmpty()) record.setRecordType(input.toUpperCase());
            
            if (financialService.updateFinancialRecord(record)) {
                System.out.println("Financial record updated successfully!");
            } else {
                System.out.println("Failed to update financial record.");
            }
        } catch (Exception e) {
            System.out.println("Error updating financial record: " + e.getMessage());
        }
    }

    private static void viewAllFinancialRecords() {
        try {
            System.out.print("Enter Employee ID (or 0 for all records): ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            List<FinancialRecord> records;
            if (employeeId == 0) {
                records = financialService.getAllFinancialRecords();
            } else {
                records = financialService.getFinancialRecordsByEmployee(employeeId);
            }
            
            if (records.isEmpty()) {
                System.out.println("No financial records found.");
                return;
            }

            System.out.println("\nFinancial Records:");
            for (FinancialRecord record : records) {
                System.out.println(record);
                System.out.println("-----------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Employee ID.");
        }
    }

    private static void handleTaxOperations() {
        while (true) {
            System.out.println("\n=== Tax Management ===");
            System.out.println("1. Calculate Tax");
            System.out.println("2. View Tax Details");
            System.out.println("3. View Employee Tax History");
            System.out.println("4. View Tax Records by Year");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        calculateTax();
                        break;
                    case 2:
                        viewTaxDetails();
                        break;
                    case 3:
                        viewEmployeeTaxHistory();
                        break;
                    case 4:
                        viewTaxRecordsByYear();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void calculateTax() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Tax Year: ");
            int taxYear = Integer.parseInt(scanner.nextLine());
            
            Tax tax = taxService.calculateTax(employeeId, taxYear);
            if (tax != null) {
                System.out.println("\nTax Calculation Results:");
                System.out.println("Taxable Income: ₹" + String.format("%.2f", tax.getTaxableIncome()));
                System.out.println("Tax Amount: ₹" + String.format("%.2f", tax.getTaxAmount()));
                System.out.println("Effective Tax Rate: " + 
                    String.format("%.2f%%", (tax.getTaxAmount() / tax.getTaxableIncome()) * 100));
            } else {
                System.out.println("Failed to calculate tax.");
            }
        } catch (Exception e) {
            System.out.println("Error calculating tax: " + e.getMessage());
        }
    }

    private static void viewTaxDetails() {
        try {
            System.out.print("Enter Tax ID: ");
            int taxId = Integer.parseInt(scanner.nextLine());
            
            Tax tax = taxService.getTaxById(taxId);
            if (tax != null) {
                System.out.println("\nTax Details:");
                System.out.println(tax);
            } else {
                System.out.println("Tax record not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Tax ID.");
        }
    }

    private static void viewEmployeeTaxHistory() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            List<Tax> taxes = taxService.getTaxesForEmployee(employeeId);
            if (taxes.isEmpty()) {
                System.out.println("No tax records found for this employee.");
                return;
            }

            System.out.println("\nTax History:");
            for (Tax tax : taxes) {
                System.out.println(tax);
                System.out.println("-----------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Employee ID.");
        }
    }

    private static void viewTaxRecordsByYear() {
        try {
            System.out.print("Enter Tax Year: ");
            int taxYear = Integer.parseInt(scanner.nextLine());
            
            List<Tax> taxes = taxService.getTaxesForYear(taxYear);
            if (taxes.isEmpty()) {
                System.out.println("No tax records found for the specified year.");
                return;
            }

            System.out.println("\nTax Records for Year " + taxYear + ":");
            for (Tax tax : taxes) {
                System.out.println(tax);
                System.out.println("-----------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid year.");
        }
    }

    private static void viewEmployeePayrollHistory() {
        try {
            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(scanner.nextLine());
            
            List<Payroll> payrolls = payrollService.getPayrollsForEmployee(employeeId);
            if (payrolls.isEmpty()) {
                System.out.println("No payroll records found for this employee.");
                return;
            }

            System.out.println("\nPayroll History:");
            for (Payroll payroll : payrolls) {
                System.out.println(payroll);
                System.out.println("-----------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Employee ID.");
        }
    }
}

