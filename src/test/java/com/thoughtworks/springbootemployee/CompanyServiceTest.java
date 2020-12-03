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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CompanyServiceTest {
    private final String employee1 = "Marcus";
    private final String employee2 = "Theo";
    private final Integer age = 22;
    private final String male = "male";
    private final Integer employeeId1 = 1;
    private final Integer employeeId2 = 2;
    private final Integer salary = 10000;

    private final Integer companyId1 = 1;
    private final Integer companyId2 = 2;
    private final Integer companyId3 = 3;
    private final String companyName1 = "Google";
    private final String companyName2 = "Facebook";
    private final String companyName3 = "Apple";

//    @Test
//    void should_return_all_companies_when_get_all_given_all_companies() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> employeesList = new ArrayList<>();
//        employeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        employeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        employeesList.forEach(employeeRepository::create);
//
//        final List<Company> expected = new ArrayList<>();
//        expected.add(new Company(companyName1, companyId1, employeesList.size(), employeesList));
//        expected.add(new Company(companyName2, companyId2, employeesList.size(), employeesList));
//        expected.forEach(companyRepository::create);
//
//        //when
//        final List<Company> companies = companyService.getCompanies();
//
//        //then
//        assertEquals(expected, companies);
//    }
//
//    @Test
//    void should_return_a_company_when_get_company_given_a_company() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> employeesList = new ArrayList<>();
//        employeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        employeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        employeesList.forEach(employeeRepository::create);
//
//        final Company expected = new Company(companyName1, companyId1, employeesList.size(), employeesList);
//        companyRepository.create(expected);
//
//        //when
//        final Company company = companyService.getCompany(companyId1);
//
//        //then
//        assertEquals(expected, company);
//    }
//
//    @Test
//    void should_return_employee_list_when_get_a_company_employee_list_given_a_company() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> expectedEmployeesList = new ArrayList<>();
//        expectedEmployeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        expectedEmployeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        expectedEmployeesList.forEach(employeeRepository::create);
//
//        final List<Company> companyList = new ArrayList<>();
//        final Company expected = new Company(companyName1, companyId1, expectedEmployeesList.size(), expectedEmployeesList);
//        companyRepository.create(expected);
//
//        //when
//        final List<Employee> employeesList = companyService.getEmployeeList(1);
//
//        //then
//        assertEquals(expectedEmployeesList, employeesList);
//    }
//
//    @Test
//    void should_return_correct_page_when_get_all_given_all_companies_and_page_with_page_size() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> employeesList = new ArrayList<>();
//        employeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        employeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        employeesList.forEach(employeeRepository::create);
//
//        final List<Company> fullList = new ArrayList<>();
//        fullList.add(new Company(companyName1, companyId1, employeesList.size(), employeesList));
//        fullList.add(new Company(companyName2, companyId2, employeesList.size(), employeesList));
//        fullList.add(new Company(companyName3, companyId3, employeesList.size(), employeesList));
//        fullList.forEach(companyRepository::create);
//
//        final List<Company> expected = fullList.stream()
//                .limit(2)
//                .collect(Collectors.toList());
//
//        // add page 2 to do testing
//        //when
//        final List<Company> companies = companyService.getCompaniesPaginized(1, 2);
//
//        //then
//        assertEquals(expected, companies);
//    }
//
//    @Test
//    void should_return_a_company_when_create_given_a_company() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> employeesList = new ArrayList<>();
//        employeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        employeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        employeesList.forEach(employeeRepository::create);
//
//        final Company expected = new Company(companyName1, companyId1, employeesList.size(), employeesList);
//
//        //when
//        companyService.createCompany(expected);
//        final Company company = companyService.getCompany(expected.getCompanyId());
//
//        //then
//        assertEquals(expected, company);
//    }
//
//    @Test
//    void should_return_updated_company_when_update_given_a_company_id_and_company_updates() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> employeesList = new ArrayList<>();
//        employeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        employeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        employeesList.forEach(employeeRepository::create);
//
//        final Company beforeUpdateCompany = new Company(companyName1, companyId1, employeesList.size(), employeesList);
//        companyService.createCompany(beforeUpdateCompany);
//        final Company expected = new Company(companyName2, companyId1, employeesList.size(), employeesList);
//
//        //when
//        companyService.updateCompany(expected.getCompanyId(), expected);
//        final Company company = companyService.getCompany(expected.getCompanyId());
//
//        //then
//        assertEquals(expected, company);
//    }
//
//    @Test
//    void should_return_null_when_delete_given_a_company_id() {
//        //given
//        EmployeeRepository employeeRepository = new EmployeeRepository();
//
//        CompanyRepository companyRepository = new CompanyRepository();
//        CompanyService companyService = new CompanyService(companyRepository);
//        final List<Employee> employeesList = new ArrayList<>();
//        employeesList.add(new Employee(employeeId1, employee1, age, male, salary));
//        employeesList.add(new Employee(employeeId2, employee2, age, male, salary));
//        employeesList.forEach(employeeRepository::create);
//
//        final Company expected = new Company(companyName1, 1, employeesList.size(), employeesList);
//        companyService.createCompany(expected);
//
//        //when
//        companyService.deleteCompany(expected.getCompanyId());
//        final Company company = companyService.getCompany(expected.getCompanyId());
//
//        //then
//        assertNull(company);
//    }
}
