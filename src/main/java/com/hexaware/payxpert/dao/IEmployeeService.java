package com.hexaware.payxpert.dao;

import com.hexaware.payxpert.entity.Employee;
import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployees();
    boolean addEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean removeEmployee(int employeeId);
}
