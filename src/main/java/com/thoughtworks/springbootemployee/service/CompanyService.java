package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    //confirm employee id exist
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompany(String companyId) throws CompanyNotFoundException {
        return companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> getEmployeeList(String companyId) throws CompanyNotFoundException {
        return employeeService.getEmployeesById(getCompany(companyId).getEmployeesId());
    }

    public Page<Company> getCompaniesPaginated(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    //confirm employee id exist
    public Company updateCompany(String companyId, Company companyUpdated) throws CompanyNotFoundException {
        if (this.companyRepository.existsById(companyId)) {
            companyUpdated.setCompanyId(companyId);
            return companyRepository.save(companyUpdated);
        }
        throw new CompanyNotFoundException();
    }

    public void deleteCompany(String companyId) {
        companyRepository.deleteById(companyId);
    }
}
