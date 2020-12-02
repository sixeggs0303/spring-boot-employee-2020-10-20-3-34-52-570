package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyServiceTest {
    @Test
    void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        final List<Employee> employeesList = new ArrayList<>();
        employeesList.add(new Employee(1, "Marcus", 22, "male", 50));
        employeesList.add(new Employee(2, "Theo", 22, "male", 50000));
        employeesList.forEach(employeeRepository::create);

        final List<Company> expected = new ArrayList<>();
        expected.add(new Company("Google", 1,employeesList.size(), employeesList));
        expected.add(new Company("Facebook", 2,employeesList.size(), employeesList));
        expected.forEach(companyRepository::create);

        //when
        final List<Company> companies = companyService.getCompanies();

        //then
        assertEquals(expected, companies);
    }

    @Test
    void should_return_a_company_when_get_company_given_a_company() {
        //given
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        final List<Employee> employeesList = new ArrayList<>();
        employeesList.add(new Employee(1, "Marcus", 22, "male", 50));
        employeesList.add(new Employee(2, "Theo", 22, "male", 50000));
        employeesList.forEach(employeeRepository::create);

        final List<Company> companyList = new ArrayList<>();
        final Company expected = new Company("Google", 1,employeesList.size(), employeesList);
        companyRepository.create(expected);

        //when
        final Company company = companyService.getCompany(1);

        //then
        assertEquals(expected, company);
    }

    @Test
    void should_return_employee_list_when_get_a_company_employee_list_given_a_company() {
        //given
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        CompanyRepository companyRepository = new CompanyRepository();
        CompanyService companyService = new CompanyService(companyRepository);
        final List<Employee> expectedEmployeesList = new ArrayList<>();
        expectedEmployeesList.add(new Employee(1, "Marcus", 22, "male", 50));
        expectedEmployeesList.add(new Employee(2, "Theo", 22, "male", 50000));
        expectedEmployeesList.forEach(employeeRepository::create);

        final List<Company> companyList = new ArrayList<>();
        final Company expected = new Company("Google", 1,expectedEmployeesList.size(), expectedEmployeesList);
        companyRepository.create(expected);

        //when
        final List<Employee> employeesList = companyService.getEmployeeList(1);

        //then
        assertEquals(expectedEmployeesList, employeesList);
    }
}
