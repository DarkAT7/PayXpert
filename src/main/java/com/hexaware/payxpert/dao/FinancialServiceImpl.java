package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.FinancialRecord;
import com.hexaware.payxpert.exception.DatabaseConnectionException;
import com.hexaware.payxpert.util.DBConnUtil;
import com.hexaware.payxpert.util.DBPropertyUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialServiceImpl implements IFinancialService {
    private Connection getConnection() throws DatabaseConnectionException {
        String connectionString = DBPropertyUtil.getConnectionString("src/main/resources/database.properties");
        return DBConnUtil.getConnection(connectionString);
    }

    @Override
    public boolean addFinancialRecord(FinancialRecord record) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) " +
                "VALUES (?, ?, ?, ?, ?)"
             )) {
            stmt.setInt(1, record.getEmployeeId());
            stmt.setDate(2, Date.valueOf(record.getRecordDate()));
            stmt.setString(3, record.getDescription());
            stmt.setDouble(4, record.getAmount());
            stmt.setString(5, record.getRecordType());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsByEmployee(int employeeId) {
        List<FinancialRecord> records = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM FinancialRecord WHERE EmployeeID = ? ORDER BY RecordDate DESC"
             )) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                records.add(extractFinancialRecord(rs));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsByDateRange(int employeeId, LocalDate startDate, LocalDate endDate) {
        List<FinancialRecord> records = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM FinancialRecord WHERE EmployeeID = ? AND RecordDate BETWEEN ? AND ? ORDER BY RecordDate DESC"
             )) {
            stmt.setInt(1, employeeId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                records.add(extractFinancialRecord(rs));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public List<FinancialRecord> getAllFinancialRecords() {
        List<FinancialRecord> records = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM FinancialRecord ORDER BY RecordDate DESC"
             )) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                records.add(extractFinancialRecord(rs));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public FinancialRecord getFinancialRecordById(int recordId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM FinancialRecord WHERE RecordID = ?"
             )) {
            stmt.setInt(1, recordId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractFinancialRecord(rs);
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FinancialRecord extractFinancialRecord(ResultSet rs) throws SQLException {
        FinancialRecord record = new FinancialRecord();
        record.setRecordId(rs.getInt("RecordID"));
        record.setEmployeeId(rs.getInt("EmployeeID"));
        record.setRecordDate(rs.getDate("RecordDate").toLocalDate());
        record.setDescription(rs.getString("Description"));
        record.setAmount(rs.getDouble("Amount"));
        record.setRecordType(rs.getString("RecordType"));
        return record;
    }

    @Override
    public boolean updateFinancialRecord(FinancialRecord record) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "UPDATE FinancialRecord SET RecordDate = ?, Description = ?, Amount = ?, RecordType = ? " +
                "WHERE RecordID = ? AND EmployeeID = ?"
             )) {
            stmt.setDate(1, Date.valueOf(record.getRecordDate()));
            stmt.setString(2, record.getDescription());
            stmt.setDouble(3, record.getAmount());
            stmt.setString(4, record.getRecordType());
            stmt.setInt(5, record.getRecordId());
            stmt.setInt(6, record.getEmployeeId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
