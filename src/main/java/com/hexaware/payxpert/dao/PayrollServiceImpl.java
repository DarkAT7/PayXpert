package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.Payroll;
import com.hexaware.payxpert.exception.DatabaseConnectionException;
import com.hexaware.payxpert.util.DBConnUtil;
import com.hexaware.payxpert.util.DBPropertyUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollServiceImpl implements IPayrollService {
    private Connection getConnection() throws DatabaseConnectionException {
        String connectionString = DBPropertyUtil.getConnectionString("src/main/resources/database.properties");
        return DBConnUtil.getConnection(connectionString);
    }

    @Override
    public Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate, double basicSalary, double overtimePay, double deductions) {
        try (Connection conn = getConnection()) {
            // First verify if employee exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM Employee WHERE EmployeeID = ?");
            checkStmt.setInt(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Employee not found");
            }

            // Use the provided salary details

            // Calculate net salary
            double netSalary = basicSalary + overtimePay - deductions;

            // Insert the payroll record
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Payroll (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, " +
                "BasicSalary, OvertimePay, Deductions, NetSalary) VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, employeeId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            stmt.setDouble(4, basicSalary);
            stmt.setDouble(5, overtimePay);
            stmt.setDouble(6, deductions);
            stmt.setDouble(7, netSalary);
            
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(generatedKeys.getInt(1));
                payroll.setEmployeeId(employeeId);
                payroll.setPayPeriodStartDate(startDate);
                payroll.setPayPeriodEndDate(endDate);
                payroll.setBasicSalary(basicSalary);
                payroll.setOvertimePay(overtimePay);
                payroll.setDeductions(deductions);
                payroll.setNetSalary(netSalary);
                return payroll;
            }
            
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Payroll getPayrollById(int payrollId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Payroll WHERE PayrollID = ?")) {
            
            stmt.setInt(1, payrollId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("PayrollID"));
                payroll.setEmployeeId(rs.getInt("EmployeeID"));
                payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toLocalDate());
                payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toLocalDate());
                payroll.setBasicSalary(rs.getDouble("BasicSalary"));
                payroll.setOvertimePay(rs.getDouble("OvertimePay"));
                payroll.setDeductions(rs.getDouble("Deductions"));
                payroll.setNetSalary(rs.getDouble("NetSalary"));
                return payroll;
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payroll> getPayrollsForEmployee(int employeeId) {
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Payroll WHERE EmployeeID = ?")) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("PayrollID"));
                payroll.setEmployeeId(rs.getInt("EmployeeID"));
                payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toLocalDate());
                payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toLocalDate());
                payroll.setBasicSalary(rs.getDouble("BasicSalary"));
                payroll.setOvertimePay(rs.getDouble("OvertimePay"));
                payroll.setDeductions(rs.getDouble("Deductions"));
                payroll.setNetSalary(rs.getDouble("NetSalary"));
                payrolls.add(payroll);
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return payrolls;
    }

    @Override
    public List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) {
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?")) {
            
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("PayrollID"));
                payroll.setEmployeeId(rs.getInt("EmployeeID"));
                payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toLocalDate());
                payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toLocalDate());
                payroll.setBasicSalary(rs.getDouble("BasicSalary"));
                payroll.setOvertimePay(rs.getDouble("OvertimePay"));
                payroll.setDeductions(rs.getDouble("Deductions"));
                payroll.setNetSalary(rs.getDouble("NetSalary"));
                payrolls.add(payroll);
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return payrolls;
    }
}
