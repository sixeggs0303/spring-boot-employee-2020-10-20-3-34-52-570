package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyMongoRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyMongoRepository companyMongoRepository;

    @Test
    public void should_return_all_companies_when_get_all_given_all_companies() {
        //given
        final List<Company> expected = new ArrayList<>();
        expected.add(new Company());
        expected.add(new Company());
        when(companyMongoRepository.findAll()).thenReturn(expected);

        //when
        final List<Company> actual = companyService.getCompanies();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void should_return_a_company_when_get_company_given_a_company() {
        //given
        Company expected = new Company();
        when(companyMongoRepository.findById(any())).thenReturn(Optional.of(expected));

        //when
        final Company actual = companyService.getCompany(expected.getCompanyId());

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void should_return_list_of_employee_when_get_employees_of_company_given_a_company() {
        //given
        List<Employee> expected = new ArrayList<>();
        Company company = new Company("1", "Google", expected.size(), expected);
        when(companyMongoRepository.findById(any())).thenReturn(Optional.of(company));

        //when
        final List<Employee> actual = companyService.getEmployeesOfCompany(company.getCompanyId());

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void should_return_first_page_of_companies_when_get_company_paginated_given_page_and_page_size() {
        //given
        Company company1 = new Company();
        Company company2 = new Company();
        Page<Company> expected = new PageImpl<>(Arrays.asList(company1, company2));
        when(companyMongoRepository.findAll((Pageable) any())).thenReturn(expected);

        //when
        final List<Company> actual = companyService.getCompaniesPaginated(1, 2);

        //then
        assertEquals(expected.toList(), actual);
    }

    @Test
    void should_return_created_company_when_create_company_given_a_company() {
        //given
        Company expected = new Company();
        when(companyMongoRepository.save(any())).thenReturn(expected);

        //when
        Company actual = companyService.createCompany(expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_updated_company_when_update_company_given_a_company_id_and_company() {
        //given
        Company expected = new Company();
        when(companyMongoRepository.findById(any())).thenReturn(Optional.of(expected));
        when(companyMongoRepository.save(any())).thenReturn(expected);
        String companyId = expected.getCompanyId();

        //when
        Company actual = companyService.updateCompany(companyId, expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_call_repository_delete_when_delete_company_given_a_company_id() {
        //given
        String companyId = "1";

        //when
        companyService.deleteCompany(companyId);

        //then
        verify(companyMongoRepository, times(1)).deleteById(companyId);
    }
}
