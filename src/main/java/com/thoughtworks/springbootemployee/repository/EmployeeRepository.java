package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll(){
        return this.employees;
    }

    public Employee find(Integer employeeId){
        return this.employees.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .orElse(null);
    }

    public Employee create(Employee employee){
        this.employees.add(employee);
        return employee;
    }

    public Employee update(Employee employeeUpdated){
        employees.stream()
                .filter(employee -> employeeUpdated.getId().equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> {
                    employees.remove(employee);
                    employees.add(employeeUpdated);
                });
        return employeeUpdated;
    }

    public void delete(Integer employeeId){
        employees.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> employees.remove(employee));
    }
}
