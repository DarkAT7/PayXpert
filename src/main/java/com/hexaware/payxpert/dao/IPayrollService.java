package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.Payroll;
import java.time.LocalDate;
import java.util.List;

public interface IPayrollService {
    Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate, double basicSalary, double overtimePay, double deductions);
    Payroll getPayrollById(int payrollId);
    List<Payroll> getPayrollsForEmployee(int employeeId);
    List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate);
}
