package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String companyId;
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;
    private List<String> employeesId;

    // no need to have employee list
    public Company(String companyName, List<String> employeesId) {
        this.companyName = companyName;
        this.employeesId = employeesId;
    }

    public String getCompanyId() {
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

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<String> getEmployeesId() {
        return employeesId;
    }
}
