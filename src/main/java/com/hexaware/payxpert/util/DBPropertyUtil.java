package com.hexaware.payxpert.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.hexaware.payxpert.exception.DatabaseConnectionException;

public class DBPropertyUtil {
    public static String getConnectionString(String propertyFileName) throws DatabaseConnectionException {
        Properties properties = new Properties();
        String connectionString = null;
        
        try (FileInputStream input = new FileInputStream(propertyFileName)) {
            properties.load(input);
            
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            
            connectionString = String.format("%s?user=%s&password=%s", url, username, password);
            
        } catch (IOException e) {
            throw new DatabaseConnectionException("Error loading database properties: " + e.getMessage());
        }
        
        return connectionString;
    }
}
