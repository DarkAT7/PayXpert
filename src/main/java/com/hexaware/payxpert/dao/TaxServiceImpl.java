package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.Tax;
import com.hexaware.payxpert.exception.DatabaseConnectionException;
import com.hexaware.payxpert.exception.TaxCalculationException;
import com.hexaware.payxpert.util.DBConnUtil;
import com.hexaware.payxpert.util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxServiceImpl implements ITaxService {
    private Connection getConnection() throws DatabaseConnectionException {
        String connectionString = DBPropertyUtil.getConnectionString("src/main/resources/database.properties");
        return DBConnUtil.getConnection(connectionString);
    }

    @Override
    public Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException {
        try (Connection conn = getConnection()) {
            // First, get the total income for the employee for the given year
            double totalIncome = calculateTotalIncome(conn, employeeId, taxYear);
            double taxAmount = calculateTaxAmount(totalIncome);

            // Create and save tax record
            Tax tax = new Tax();
            tax.setEmployeeId(employeeId);
            tax.setTaxYear(taxYear);
            tax.setTaxableIncome(totalIncome);
            tax.setTaxAmount(taxAmount);

            // Save to database
            saveTaxRecord(conn, tax);
            
            return tax;
        } catch (Exception e) {
            throw new TaxCalculationException("Error calculating tax: " + e.getMessage());
        }
    }

    private double calculateTotalIncome(Connection conn, int employeeId, int taxYear) throws SQLException {
        // First, get the monthly income
        String sql = "SELECT AVG(NetSalary + Deductions) as MonthlyIncome FROM Payroll " +
                    "WHERE EmployeeID = ? AND YEAR(PayPeriodStartDate) = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setInt(2, taxYear);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double monthlyIncome = rs.getDouble("MonthlyIncome");
                if (rs.wasNull()) {
                    return 0.0; // Handle case when no payroll records exist
                }
                // Annualize the income (multiply by 12)
                return monthlyIncome * 12;
            }
            return 0.0;
        }
    }

    private double calculateTaxAmount(double income) {
        double tax = 0.0;
        
        // No tax for income up to 600,000
        if (income <= 600000) {
            return 0.0;
        }

        // 6% tax for income between 600,001 and 1,000,000
        if (income > 600000) {
            double taxableAmount = Math.min(income - 600000, 400000); // 1000000 - 600000 = 400000
            tax += taxableAmount * 0.06;
        }

        // 10% tax for income between 1,000,001 and 1,500,000
        if (income > 1000000) {
            double taxableAmount = Math.min(income - 1000000, 500000); // 1500000 - 1000000 = 500000
            tax += taxableAmount * 0.10;
        }

        // 12% tax for income between 1,500,001 and 2,000,000
        if (income > 1500000) {
            double taxableAmount = Math.min(income - 1500000, 500000); // 2000000 - 1500000 = 500000
            tax += taxableAmount * 0.12;
        }

        // 15% tax for income above 2,000,000
        if (income > 2000000) {
            double taxableAmount = income - 2000000;
            tax += taxableAmount * 0.15;
        }

        return tax;
    }

    private void saveTaxRecord(Connection conn, Tax tax) throws SQLException {
        String sql = "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, tax.getEmployeeId());
            stmt.setInt(2, tax.getTaxYear());
            stmt.setDouble(3, tax.getTaxableIncome());
            stmt.setDouble(4, tax.getTaxAmount());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                tax.setTaxId(rs.getInt(1));
            }
        }
    }

    @Override
    public Tax getTaxById(int taxId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tax WHERE TaxID = ?")) {
            
            stmt.setInt(1, taxId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Tax tax = new Tax();
                tax.setTaxId(rs.getInt("TaxID"));
                tax.setEmployeeId(rs.getInt("EmployeeID"));
                tax.setTaxYear(rs.getInt("TaxYear"));
                tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
                tax.setTaxAmount(rs.getDouble("TaxAmount"));
                return tax;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tax> getTaxesForEmployee(int employeeId) {
        List<Tax> taxes = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tax WHERE EmployeeID = ?")) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tax tax = new Tax();
                tax.setTaxId(rs.getInt("TaxID"));
                tax.setEmployeeId(rs.getInt("EmployeeID"));
                tax.setTaxYear(rs.getInt("TaxYear"));
                tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
                tax.setTaxAmount(rs.getDouble("TaxAmount"));
                taxes.add(tax);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taxes;
    }

    @Override
    public List<Tax> getTaxesForYear(int taxYear) {
        List<Tax> taxes = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tax WHERE TaxYear = ?")) {
            
            stmt.setInt(1, taxYear);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tax tax = new Tax();
                tax.setTaxId(rs.getInt("TaxID"));
                tax.setEmployeeId(rs.getInt("EmployeeID"));
                tax.setTaxYear(rs.getInt("TaxYear"));
                tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
                tax.setTaxAmount(rs.getDouble("TaxAmount"));
                taxes.add(tax);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taxes;
    }
}
