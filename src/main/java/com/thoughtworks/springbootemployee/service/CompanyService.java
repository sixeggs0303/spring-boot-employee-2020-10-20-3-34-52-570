package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.CompanyRepository1;
import com.thoughtworks.springbootemployee.response.CompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository1 companyRepository1;
    @Autowired
    private EmployeeService employeeService;

    public CompanyService(CompanyRepository1 companyRepository1, EmployeeService employeeService) {
        this.companyRepository1 = companyRepository1;
        this.employeeService = employeeService;
    }

    public List<Company> getCompanies() {
        return companyRepository1.findAll();
    }

    public Company createCompany(Company company) {
        return companyRepository1.save(company);
    }

    public Company getCompany(String companyId) {
        return companyRepository1.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeeList(String companyId) {
        return getCompany(companyId).getEmployeesId().stream()
                .map(employeeService::getEmployee)
                .collect(Collectors.toList());
    }

    public List<Company> getCompaniesPaginized(int page, int pageSize) {
        return companyRepository1.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public Company updateCompany(String companyId, Company companyUpdated) {
        if(getCompany(companyId)!=null){
            companyUpdated.setCompanyId(companyId);
            return companyRepository1.save(companyUpdated);
        }
        return null;
    }

    public void deleteCompany(String companyId) {
        companyRepository1.deleteById(companyId);
    }
}
