package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.response.CompanyResponse;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final List<Company> companies = new ArrayList<>();
    @Autowired
    CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return formatListOfCompanyResponse(this.companyService.getCompanies());
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable String companyId) {
        return formatCompanyResponse(this.companyService.getCompany(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable String companyId) {
        return companyService.getEmployeeList(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getEmployeesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return formatListOfCompanyResponse(this.companyService.getCompaniesPaginized(page, pageSize));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company companyUpdate) {
        return companyService.createCompany(companyUpdate);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable String companyId, @RequestBody Company companyUpdated) {
        return companyService.updateCompany(companyId, companyUpdated);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable String companyId) {
        companyService.deleteCompany(companyId);
    }

    private CompanyResponse formatCompanyResponse(Company company){
        if(company==null){
            return null;
        }
        List<Employee> employees = this.companyService.getEmployeeList(company.getCompanyId());
        return new CompanyResponse(company.getCompanyName(), employees.size(), employees);
    }

    private List<CompanyResponse> formatListOfCompanyResponse(List<Company> companies){
        return companies.stream()
                .map(this::formatCompanyResponse)
                .collect(Collectors.toList());
    }
}
