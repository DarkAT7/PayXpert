package com.hexaware.payxpert.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hexaware.payxpert.exception.DatabaseConnectionException;

public class DBConnUtil {
    public static Connection getConnection(String connectionString) throws DatabaseConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
        }
    }
}
