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
        expected.add(new Company("Google", employeesList.size(), employeesList));
        expected.add(new Company("Facebook", employeesList.size(), employeesList));
        expected.forEach(companyRepository::create);

        //when
        final List<Company> companies = companyService.getCompanies();

        //then
        assertEquals(expected, companies);
    }
}
