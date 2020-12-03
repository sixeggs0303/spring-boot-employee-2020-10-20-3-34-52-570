package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
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
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getCompanies() {
        List<Company> companies = this.companyService.getCompanies();
        return companies.stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompany(@PathVariable String companyId) throws CompanyNotFoundException {
        return companyMapper.toResponse(this.companyService.getCompany(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployees(@PathVariable String companyId) throws CompanyNotFoundException {
        List<Employee> employeeList = companyService.getEmployeeList(companyId);
        return employeeList.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<CompanyResponse> getEmployeesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Company> companies = this.companyService.getCompaniesPaginated(page, pageSize);
        return companies.map(companyMapper::toResponse);
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

//    private CompanyResponse formatCompanyResponse(Company company) throws CompanyNotFoundException {
//        if (company == null) {
//            throw new CompanyNotFoundException();
//        }
//        List<Employee> employees = this.companyService.getEmployeeList(company.getCompanyId());
//        return new CompanyResponse(company.getCompanyName(), employees.size(), employees);
//    }
//
//    private List<CompanyResponse> formatListOfCompanyResponse(List<Company> companies) throws CompanyNotFoundException{
//        return companies.stream()
//                .map(this::formatCompanyResponse)
//                .collect(Collectors.toList());
//    }
}
