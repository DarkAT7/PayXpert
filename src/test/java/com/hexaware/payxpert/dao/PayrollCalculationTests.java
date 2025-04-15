package com.hexaware.payxpert.dao;

import static org.junit.Assert.*;

import com.hexaware.payxpert.exception.TaxCalculationException;
import org.junit.Before;
import org.junit.Test;

import com.hexaware.payxpert.dao.EmployeeServiceImpl;
import com.hexaware.payxpert.dao.PayrollServiceImpl;
import com.hexaware.payxpert.dao.TaxServiceImpl;
import com.hexaware.payxpert.entity.Employee;
import com.hexaware.payxpert.entity.Payroll;
import com.hexaware.payxpert.entity.Tax;

import java.time.LocalDate;

public class PayrollCalculationTests {

    private PayrollServiceImpl payrollService;
    private EmployeeServiceImpl employeeService;
    private TaxServiceImpl taxService;
    private int testEmployeeId;
    private int testPayrollId;

    @Before
    public void setUp() {
        payrollService = new PayrollServiceImpl();
        employeeService = new EmployeeServiceImpl();
        taxService = new TaxServiceImpl();

        // Create a test employee
        Employee emp = new Employee();
        emp.setFirstName("Test");
        emp.setLastName("User");
        emp.setDateOfBirth(LocalDate.of(1990, 1, 1));
        emp.setGender("M");
        emp.setEmail("test.user@hexaware.com");
        emp.setPhoneNumber("9999999999");
        emp.setAddress("Test Address");
        emp.setPosition("Developer");
        emp.setJoiningDate(LocalDate.of(2021, 1, 1));

        boolean added = employeeService.addEmployee(emp);
        assertTrue("Employee creation failed", added);
        testEmployeeId = emp.getEmployeeId();

        // Generate payroll
        Payroll payroll = payrollService.generatePayroll(
                testEmployeeId,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 31),
                50000,  // Basic Salary
                5000,   // Overtime
                3000    // Deductions
        );
        testPayrollId = payroll.getPayrollId();
    }

    /**
     * Test Case: CalculateGrossSalaryForEmployee
     * Objective: Verify that the system correctly calculates gross salary.
     */
    @Test
    public void testCalculateGrossSalaryForEmployee() {
        double expectedGross = 50000 + 5000;
        Payroll payroll = payrollService.getPayrollById(testPayrollId);
        double actualGross = payroll.getBasicSalary() + payroll.getOvertimePay();
        assertEquals("Incorrect gross salary", expectedGross, actualGross, 0.001);
    }

    /**
     * Test Case: CalculateNetSalaryAfterDeductions
     * Objective: Ensure correct net salary calculation after deductions.
     */
    @Test
    public void testCalculateNetSalaryAfterDeductions() {
        double expectedNet = 50000 + 5000 - 3000;
        Payroll payroll = payrollService.getPayrollById(testPayrollId);
        double actualNet = payroll.getNetSalary();
        assertEquals("Incorrect net salary", expectedNet, actualNet, 0.001);
    }

    /**
     * Test Case: VerifyTaxCalculationForHighIncomeEmployee
     * Objective: Test tax calculation for high-income employee.
     */
    @Test
    public void testVerifyTaxCalculationForHighIncomeEmployee() throws TaxCalculationException {
        double monthlyIncome = 2500000 / 12.0;
        for (int i = 1; i <= 12; i++) {
            payrollService.generatePayroll(
                    testEmployeeId,
                    LocalDate.of(2023, i, 1),
                    LocalDate.of(2023, i, 28),
                    monthlyIncome,
                    0,
                    0
            );
        }

        Tax tax = taxService.calculateTax(testEmployeeId, 2023);
        double expectedTax = calculateExpectedTax(2500000);
        assertEquals("High income tax calculation mismatch", expectedTax, tax.getTaxAmount(), 0.01);
    }

    /**
     * Test Case: ProcessPayrollForMultipleEmployees
     * Objective: Test payroll generation for multiple employees.
     */
    @Test
    public void testProcessPayrollForMultipleEmployees() {
        Employee emp1 = new Employee();
        emp1.setFirstName("Emp1");
        emp1.setLastName("A");
        emp1.setDateOfBirth(LocalDate.of(1991, 5, 5));
        emp1.setGender("M");
        emp1.setEmail("emp1@hexaware.com");
        emp1.setPhoneNumber("8888888888");
        emp1.setAddress("A1");
        emp1.setPosition("Dev");
        emp1.setJoiningDate(LocalDate.of(2022, 1, 1));
        employeeService.addEmployee(emp1);

        Employee emp2 = new Employee();
        emp2.setFirstName("Emp2");
        emp2.setLastName("B");
        emp2.setDateOfBirth(LocalDate.of(1992, 6, 6));
        emp2.setGender("F");
        emp2.setEmail("emp2@hexaware.com");
        emp2.setPhoneNumber("7777777777");
        emp2.setAddress("B2");
        emp2.setPosition("Tester");
        emp2.setJoiningDate(LocalDate.of(2022, 1, 1));
        employeeService.addEmployee(emp2);

        Payroll p1 = payrollService.generatePayroll(emp1.getEmployeeId(), LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31), 40000, 2000, 5000);
        Payroll p2 = payrollService.generatePayroll(emp2.getEmployeeId(), LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31), 45000, 3000, 6000);

        assertNotNull("Payroll for employee 1 failed", p1);
        assertNotNull("Payroll for employee 2 failed", p2);
    }

    /**
     * Test Case: VerifyErrorHandlingForInvalidEmployeeData
     * Objective: Ensure the system handles invalid input gracefully.
     */
    @Test
    public void testVerifyErrorHandlingForInvalidEmployeeData() {
        try {
            payrollService.generatePayroll(-1, LocalDate.now(), LocalDate.now(), 50000, 0, 0);
            fail("Expected exception for invalid employee ID");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Employee not found"));
        }

        try {
            payrollService.generatePayroll(testEmployeeId, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 1, 1), 50000, 0, 0);
            fail("Expected exception for invalid date range");
        } catch (Exception e) {
            assertTrue(true); // valid exception
        }

        try {
            payrollService.generatePayroll(testEmployeeId, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31), -50000, 0, 0);
            fail("Expected exception for negative salary");
        } catch (Exception e) {
            assertTrue(true); // valid exception
        }
    }

    // Tax calculation logic copied to match the TaxServiceImpl rule
    private double calculateExpectedTax(double income) {
        double tax = 0;
        if (income > 2000000) {
            tax += (income - 2000000) * 0.15;
            income = 2000000;
        }
        if (income > 1500000) {
            tax += (income - 1500000) * 0.12;
            income = 1500000;
        }
        if (income > 1000000) {
            tax += (income - 1000000) * 0.10;
            income = 1000000;
        }
        if (income > 600000) {
            tax += (income - 600000) * 0.06;
        }
        return tax;
    }
}
