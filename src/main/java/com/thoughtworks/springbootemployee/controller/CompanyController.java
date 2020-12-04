package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.response.CompanyResponse;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private CompanyService companyService;

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return formatListOfCompanyResponse(this.companyService.getCompanies());
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable String companyId) throws CompanyNotFoundException {
        return formatCompanyResponse(this.companyService.getCompany(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable String companyId) throws CompanyNotFoundException {
        return companyService.getEmployeeList(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<CompanyResponse> getEmployeesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return formatListOfCompanyResponse(this.companyService.getCompaniesPaginated(page, pageSize));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company companyUpdate) {
        return companyService.createCompany(companyUpdate);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable String companyId, @RequestBody Company companyUpdated) throws CompanyNotFoundException {
        return companyService.updateCompany(companyId, companyUpdated);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable String companyId) {
        companyService.deleteCompany(companyId);
    }

    private CompanyResponse formatCompanyResponse(Company company) throws CompanyNotFoundException {
        if (company == null) {
            throw new CompanyNotFoundException();
        }
        List<Employee> employees = this.companyService.getEmployeeList(company.getCompanyId());
        return new CompanyResponse(company.getCompanyName(), employees.size(), employees);
    }

    private List<CompanyResponse> formatListOfCompanyResponse(List<Company> companies) throws CompanyNotFoundException{
        return companies.stream()
                .map(this::formatCompanyResponse)
                .collect(Collectors.toList());
    }
}
