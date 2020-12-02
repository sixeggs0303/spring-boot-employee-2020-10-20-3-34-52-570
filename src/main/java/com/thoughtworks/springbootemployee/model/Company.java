package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private String companyName;
    private Integer companyId;
    private Integer employeesNumber;
    private List<Employee> employees;

    public Company(String companyName, Integer companyId, Integer employeesNumber, List<Employee> employees) {
        this.companyName = companyName;
        this.companyId = companyId;
        this.employeesNumber = employeesNumber;
        this.employees = employees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Company() {
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
