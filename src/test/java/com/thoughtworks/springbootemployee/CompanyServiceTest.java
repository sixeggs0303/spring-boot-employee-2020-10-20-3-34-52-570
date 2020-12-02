package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CompanyServiceTest {
    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Marcus", 22, "male", 10000));
        employeeList.add(new Employee(2, "Theo", 22, "male", 10));

        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);

        List<Company> expected = new ArrayList<>();
        expected.add(new Company(1, "Google", employeeList.size(), employeeList));
        expected.add(new Company(2, "Facebook", employeeList.size(), employeeList));
        expected.forEach(companyRepository::create);

        //when
        List<Company> companies = companyService.getCompanies();

        //then
        assertEquals(expected, companies);
    }

    @Test
    public void should_return_a_company_when_get_company_given_a_company() {
        //given
        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);

        Company expected = new Company(1, "Facebook", 0, new ArrayList<>());
        companyRepository.create(expected);

        //when
        Company company = companyService.getCompany(1);

        //then
        assertEquals(expected, company);
    }

    @Test
    public void should_return_list_of_employee_when_get_employees_of_company_given_a_company() {
        //given
        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);

        Company expected = new Company(1, "Facebook", 0, new ArrayList<>());
        companyRepository.create(expected);

        //when
        List<Employee> employees = companyService.getEmployeesOfCompany(1);

        //then
        assertEquals(expected.getEmployees(), employees);
    }

    @Test
    public void should_return_first_page_of_companies_when_get_company_paginated_given_page_and_page_size() {
        //given
        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        List<Company> expected = new ArrayList<>();
        expected.add(new Company(1, "Google", 0, new ArrayList<>()));
        expected.add(new Company(2, "Facebook", 0, new ArrayList<>()));
        expected.add(new Company(3, "Apple", 0, new ArrayList<>()));
        expected.forEach(companyRepository::create);
        expected = expected.stream().skip(2).collect(Collectors.toList());

        //when
        final List<Company> companies = companyService.getCompaniesPaginated(2, 2);

        //then
        assertEquals(expected, companies);
    }

    @Test
    void should_return_created_company_when_create_company_given_a_company() {
        //given
        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        Company expected = new Company(1, "Google", 0, new ArrayList<>());
        Integer companyId = expected.getCompanyId();

        //when
        companyService.createCompany(expected);

        //then
        assertEquals(expected, companyService.getCompany(companyId));
    }

    @Test
    void should_return_updated_company_when_update_company_given_a_company_id_and_company() {
        //given
        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        Company expected = new Company(1, "Google", 0, new ArrayList<>());
        companyService.createCompany(expected);
        expected.setCompanyName("Alphabet");
        Integer companyId = expected.getCompanyId();

        //when
        companyService.updateCompany(companyId, expected);

        //then
        assertEquals(expected, companyService.getCompany(companyId));
    }

    @Test
    void should_return_null_when_delete_company_given_a_company_id() {
        //given
        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        Company expected = new Company(1, "Google", 0, new ArrayList<>());
        companyService.createCompany(expected);
        Integer companyId = expected.getCompanyId();

        //when
        companyService.deleteCompany(companyId);

        //then
        assertNull(companyService.getCompany(companyId));
    }
}
