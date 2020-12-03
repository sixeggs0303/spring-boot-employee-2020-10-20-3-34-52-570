package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository1;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyRepository1 companyRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    public static final String COMPANIES_URI = "/companies/";

    @Test
    void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //given
        Company company = new Company("Facebook", new ArrayList<>());
        companyRepository.save(company);

        //when
        //then
        mockMvc.perform(get(COMPANIES_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Facebook"))
                .andExpect(jsonPath("$[0].employeesNumber").value(0))
                .andExpect(jsonPath("$[0].employees").value(new ArrayList<>()));
    }

    @Test
    void should_return_company_when_create_given_company() throws Exception {
        //given
        String companyAsJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "    \"employeesId\": [\"5fc8bb88a807b8276b16bbde\", \"5fc8bb91a807b8276b16bbdf\", \"5fc8bb9aa807b8276b16bbe0\"]\n" +
                "}";
        List<String> employeeIdList = new ArrayList<>();
        employeeIdList.add("5fc8bb88a807b8276b16bbde");
        employeeIdList.add("5fc8bb91a807b8276b16bbdf");
        employeeIdList.add("5fc8bb9aa807b8276b16bbe0");


        //when
        //then
        mockMvc.perform(post(COMPANIES_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(companyAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").isString())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employeesId").value(employeeIdList));

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("OOCL", companies.get(0).getCompanyName());
        assertEquals(employeeIdList, companies.get(0).getEmployeesId());
    }

    @Test
    void should_return_updated_company_when_update_given_company_id() throws Exception {
        //given
        List<String> employeeIdList = new ArrayList<>();
        employeeIdList.add("5fc8bb88a807b8276b16bbde");
        employeeIdList.add("5fc8bb91a807b8276b16bbdf");
        employeeIdList.add("5fc8bb9aa807b8276b16bbe0");
        Company company = companyRepository.save(new Company("Facebook", employeeIdList));
        String companyAsJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "    \"employeesId\": [\"5fc8bb88a807b8276b16bbde\", \"5fc8bb91a807b8276b16bbdf\"]\n" +
                "}";
        employeeIdList.remove("5fc8bb9aa807b8276b16bbe0");

        //when
        //then
        mockMvc.perform(put(COMPANIES_URI+company.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isString())
                .andExpect(jsonPath("$.companyName").value("OOCL"))
                .andExpect(jsonPath("$.employeesId").value(employeeIdList));

        List<Company> companies = companyRepository.findAll();
        assertEquals(1, companies.size());
        assertEquals("OOCL", companies.get(0).getCompanyName());
        assertEquals(employeeIdList, companies.get(0).getEmployeesId());
    }
}
