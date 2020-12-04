package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeService employeeService;


    // change null to exception
    public CompanyService(CompanyRepository companyRepository, EmployeeService employeeService) {
        this.companyRepository = companyRepository;
        this.employeeService = employeeService;
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    //confirm employee id exist
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompany(String companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeeList(String companyId) throws EmployeeNotFoundException {
        return getCompany(companyId).getEmployeesId().stream()
                .map(this.employeeService::getEmployee)
                .collect(Collectors.toList());
    }

    public List<Company> getCompaniesPaginized(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize)).toList();
    }

    //confirm employee id exist
    public Company updateCompany(String companyId, Company companyUpdated) {
        if (getCompany(companyId) != null) {
            companyUpdated.setCompanyId(companyId);
            return companyRepository.save(companyUpdated);
        }
        return null;
    }

    public void deleteCompany(String companyId) {
        companyRepository.deleteById(companyId);
    }
}
