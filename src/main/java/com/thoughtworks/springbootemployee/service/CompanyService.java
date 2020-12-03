package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    // add employee Service
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company createCompany(Company company) {
        return companyRepository.create(company);
    }

    public Company getCompany(int companyId) {
        return companyRepository.find(companyId);
    }

    public List<Employee> getEmployeeList(int companyId) {
        return companyRepository.findEmployees(companyId);
    }

    public List<Company> getCompaniesPaginized(int page, int pageSize) {
        return companyRepository.findAll(page, pageSize);
    }

    public Company updateCompany(Integer companyId, Company companyUpdated) {
        return companyRepository.update(companyId, companyUpdated);
    }

    public void deleteCompany(Integer companyId) {
        companyRepository.delete(companyId);
    }
}
