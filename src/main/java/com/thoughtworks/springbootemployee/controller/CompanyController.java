package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final List<Company> companies = new ArrayList<>();
    @Autowired
    CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable Integer companyId) {
        return companyService.getCompany(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable Integer companyId) {
        return companyService.getEmployeeList(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getEmployeesInPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return companyService.getCompaniesPaginized(page, pageSize);
    }

    @PostMapping
    public Company createCompany(@RequestBody Company companyUpdate) {
        return companyService.createCompany(companyUpdate);
    }
}
