package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
