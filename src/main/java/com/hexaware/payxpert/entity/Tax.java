package com.hexaware.payxpert.entity;

public class Tax {
    private int taxId;
    private int employeeId;
    private int taxYear;
    private double taxableIncome;
    private double taxAmount;

    // Default Constructor
    public Tax() {
    }

    // Parameterized Constructor
    public Tax(int taxId, int employeeId, int taxYear, double taxableIncome, double taxAmount) {
        this.taxId = taxId;
        this.employeeId = employeeId;
        this.taxYear = taxYear;
        this.taxableIncome = taxableIncome;
        this.taxAmount = taxAmount;
    }

    // Getters and Setters
    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(int taxYear) {
        this.taxYear = taxYear;
    }

    public double getTaxableIncome() {
        return taxableIncome;
    }

    public void setTaxableIncome(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "taxId=" + taxId +
                ", employeeId=" + employeeId +
                ", taxYear=" + taxYear +
                ", taxableIncome=" + taxableIncome +
                ", taxAmount=" + taxAmount +
                '}';
    }
}
