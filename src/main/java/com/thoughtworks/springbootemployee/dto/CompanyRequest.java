package com.thoughtworks.springbootemployee.dto;

import java.util.List;

public class CompanyRequest {
    private String companyName;
    private List<String> employeesId;

    public CompanyRequest() {
    }

    public CompanyRequest(String companyName, List<String> employeesId) {
        this.companyName = companyName;
        this.employeesId = employeesId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(List<String> employeesId) {
        this.employeesId = employeesId;
    }
}
