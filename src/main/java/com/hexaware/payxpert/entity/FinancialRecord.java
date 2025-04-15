package com.hexaware.payxpert.entity;

import java.time.LocalDate;

public class FinancialRecord {
    private int recordId;
    private int employeeId;
    private LocalDate recordDate;
    private String description;
    private double amount;
    private String recordType;

    // Default Constructor
    public FinancialRecord() {
    }

    // Parameterized Constructor
    public FinancialRecord(int recordId, int employeeId, LocalDate recordDate,
                          String description, double amount, String recordType) {
        this.recordId = recordId;
        this.employeeId = employeeId;
        this.recordDate = recordDate;
        this.description = description;
        this.amount = amount;
        this.recordType = recordType;
    }

    // Getters and Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "FinancialRecord{" +
                "recordId=" + recordId +
                ", employeeId=" + employeeId +
                ", recordDate=" + recordDate +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", recordType='" + recordType + '\'' +
                '}';
    }
}
