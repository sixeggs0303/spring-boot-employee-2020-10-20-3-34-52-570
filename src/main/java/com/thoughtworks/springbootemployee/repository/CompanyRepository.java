package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public List<Company> findAll(){
        return this.companies;
    }

    public Company create(Company company){
        this.companies.add(company);
        return company;
    }

    public Company find(Integer companyId) {
        return this.companies.stream()
                .filter(company -> companyId.equals(company.getCompanyId()))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployees(Integer companyId){
        return null;
    }
}
