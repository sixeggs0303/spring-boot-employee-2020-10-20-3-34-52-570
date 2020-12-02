package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return this.companies;
    }

    public List<Company> findAll(Integer page, Integer pageSize) {
        return this.companies
                .stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        this.companies.add(company);
        return company;
    }

    public Company find(Integer companyId) {
        return this.companies.stream()
                .filter(company -> companyId.equals(company.getCompanyId()))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployees(Integer companyId) {
        return Objects.requireNonNull(this.companies.stream()
                .filter(company -> companyId.equals(company.getCompanyId()))
                .findFirst()
                .orElse(null))
                .getEmployees();
    }

    public Company update(Integer companyId, Company companyUpdated) {
        companies.stream()
                .filter(company -> companyId.equals(company.getCompanyId()))
                .findFirst()
                .ifPresent(company -> {
                    companies.remove(company);
                    companies.add(companyUpdated);
                });
        return companyUpdated;
    }

    public void delete(Integer companyId) {
        companies.stream()
                .filter(company -> companyId.equals(company.getCompanyId()))
                .findFirst()
                .ifPresent(company -> companies.remove(company));
    }
}
