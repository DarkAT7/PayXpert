package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.FinancialRecord;
import java.time.LocalDate;
import java.util.List;

public interface IFinancialService {
    /**
     * Add a new financial record
     * @param record The financial record to add
     * @return true if successful, false otherwise
     */
    boolean addFinancialRecord(FinancialRecord record);

    /**
     * Get all financial records for an employee
     * @param employeeId The ID of the employee
     * @return List of financial records
     */
    List<FinancialRecord> getFinancialRecordsByEmployee(int employeeId);

    /**
     * Get financial records for an employee within a date range
     * @param employeeId The ID of the employee
     * @param startDate Start date of the range
     * @param endDate End date of the range
     * @return List of financial records
     */
    List<FinancialRecord> getFinancialRecordsByDateRange(int employeeId, LocalDate startDate, LocalDate endDate);

    /**
     * Get a specific financial record by its ID
     * @param recordId The ID of the financial record
     * @return The financial record if found, null otherwise
     */
    FinancialRecord getFinancialRecordById(int recordId);

    /**
     * Get all financial records in the system
     * @return List of all financial records
     */
    List<FinancialRecord> getAllFinancialRecords();

    /**
     * Update an existing financial record
     * @param record The financial record to update
     * @return true if successful, false otherwise
     */
    boolean updateFinancialRecord(FinancialRecord record);
}
