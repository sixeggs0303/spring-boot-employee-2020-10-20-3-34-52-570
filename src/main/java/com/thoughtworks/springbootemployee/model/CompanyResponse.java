package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class CompanyResponse {
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    public CompanyResponse(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employeesNumber = employees.size();
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
