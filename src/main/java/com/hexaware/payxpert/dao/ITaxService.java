package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.Tax;
import com.hexaware.payxpert.exception.TaxCalculationException;
import java.util.List;

public interface ITaxService {
    Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException;
    Tax getTaxById(int taxId);
    List<Tax> getTaxesForEmployee(int employeeId);
    List<Tax> getTaxesForYear(int taxYear);
}
