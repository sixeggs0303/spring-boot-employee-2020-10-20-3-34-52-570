package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String companyName;
    private Integer companyId;
    private Integer employeesNumber;
    private List<Employee> employees;
    private List<String> employeesId;

    // no need to have employee list
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
