package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.Employee;
import com.hexaware.payxpert.exception.DatabaseConnectionException;
import com.hexaware.payxpert.util.DBConnUtil;
import com.hexaware.payxpert.util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService {
    private Connection getConnection() throws DatabaseConnectionException {
        String connectionString = DBPropertyUtil.getConnectionString("src/main/resources/database.properties");
        return DBConnUtil.getConnection(connectionString);
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Employee WHERE EmployeeID = ?")) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeID"));
                employee.setFirstName(rs.getString("FirstName"));
                employee.setLastName(rs.getString("LastName"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                employee.setGender(rs.getString("Gender"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                employee.setAddress(rs.getString("Address"));
                employee.setPosition(rs.getString("Position"));
                employee.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
                if (rs.getDate("TerminationDate") != null) {
                    employee.setTerminationDate(rs.getDate("TerminationDate").toLocalDate());
                }
                return employee;
            }
            return null;
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeID"));
                employee.setFirstName(rs.getString("FirstName"));
                employee.setLastName(rs.getString("LastName"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                employee.setGender(rs.getString("Gender"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                employee.setAddress(rs.getString("Address"));
                employee.setPosition(rs.getString("Position"));
                employee.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
                if (rs.getDate("TerminationDate") != null) {
                    employee.setTerminationDate(rs.getDate("TerminationDate").toLocalDate());
                }
                employees.add(employee);
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, " +
                    "PhoneNumber, Address, Position, JoiningDate, TerminationDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            stmt.setString(4, employee.getGender());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getPhoneNumber());
            stmt.setString(7, employee.getAddress());
            stmt.setString(8, employee.getPosition());
            stmt.setDate(9, Date.valueOf(employee.getJoiningDate()));
            if (employee.getTerminationDate() != null) {
                stmt.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                stmt.setNull(10, Types.DATE);
            }
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE Employee SET FirstName=?, LastName=?, DateOfBirth=?, Gender=?, " +
                    "Email=?, PhoneNumber=?, Address=?, Position=?, JoiningDate=?, TerminationDate=? " +
                    "WHERE EmployeeID=?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            stmt.setString(4, employee.getGender());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getPhoneNumber());
            stmt.setString(7, employee.getAddress());
            stmt.setString(8, employee.getPosition());
            stmt.setDate(9, Date.valueOf(employee.getJoiningDate()));
            if (employee.getTerminationDate() != null) {
                stmt.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                stmt.setNull(10, Types.DATE);
            }
            stmt.setInt(11, employee.getEmployeeId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeEmployee(int employeeId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Employee WHERE EmployeeID = ?")) {
            
            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
