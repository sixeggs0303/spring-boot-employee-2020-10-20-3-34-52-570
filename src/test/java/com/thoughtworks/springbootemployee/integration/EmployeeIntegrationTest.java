package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    public static final String EMPLOYEES_URI = "/employees/";

    @Test
    void should_return_all_employees_when_get_all_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Marcus", 22, "male", 50000);
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Marcus"))
                .andExpect(jsonPath("$[0].age").value(22))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(50000));
    }

    @Test
    void should_return_one_employee_when_get_employee_given_employee_id() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("Marcus", 22, "male", 50000));

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Marcus"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(50000));
    }

    @Test
    void should_return_male_employees_when_get_employee_by_gender_given_gender_is_male() throws Exception {
        //given
        employeeRepository.save(new Employee("Marcus", 18, "male", 50000));
        employeeRepository.save(new Employee("Linne", 18, "female", 50000));

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI).param("gender", "male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Marcus"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(50000));
    }

    @Test
    void should_return_correct_page_when_get_employee_given_employees_and_page_and_page_size() throws Exception {
        //given
        employeeRepository.save(new Employee("Marcus", 22, "male", 50000));
        employeeRepository.save(new Employee("Theo", 22, "male", 0));

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI).param("page", "1").param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(1))
                .andExpect(jsonPath("$.content[0].id").isString())
                .andExpect(jsonPath("$.content[0].name").value("Marcus"))
                .andExpect(jsonPath("$.content[0].age").value(22))
                .andExpect(jsonPath("$.content[0].gender").value("male"))
                .andExpect(jsonPath("$.content[0].salary").value(50000));
    }

    @Test
    void should_return_employee_when_create_given_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"name\": \"Marcus\",\n" +
                "    \"age\": 22,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 50000\n" +
                "}";

        //when
        //then
        mockMvc.perform(post(EMPLOYEES_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Marcus"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(50000));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("Marcus", employees.get(0).getName());
        assertEquals(22, employees.get(0).getAge());
    }

    @Test
    void should_return_updated_employee_when_update_given_employee() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("Marcus", 18, "male", 50000));
        String employeeAsJson = "{\n" +
                "    \"name\": \"Marcus\",\n" +
                "    \"age\": 22,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 50000\n" +
                "}";

        //when
        //then
        mockMvc.perform(put(EMPLOYEES_URI + employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Marcus"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(50000));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("Marcus", employees.get(0).getName());
        assertEquals(22, employees.get(0).getAge());
    }

    @Test
    void should_return_no_content_when_delete_given_only_one_employee() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("Marcus", 22, "male", 50000));
        //when
        //then
        mockMvc.perform(delete(EMPLOYEES_URI + employee.getId()))
                .andExpect(status().isNoContent());

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(0, employees.size());
    }

    @Test
    void should_return_404_when_get_employee_given_wrong_employee_id() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI + new ObjectId().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_400_when_get_employee_given_invalid_employee_id() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI + "123"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void should_return_404_when_update_employee_given_employee_and_wrong_employee_id() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("Marcus", 18, "male", 50000));
        String employeeAsJson = "{\n" +
                "    \"name\": \"Marcus\",\n" +
                "    \"age\": 22,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 50000\n" +
                "}";

        //when
        //then
        mockMvc.perform(put(EMPLOYEES_URI + new ObjectId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isNotFound());
    }


    @Test
    void should_return_400_when_update_employee_given_employee_and_invalid_employee_id() throws Exception {
        //given
        Employee employee = employeeRepository.save(new Employee("Marcus", 18, "male", 50000));
        String employeeAsJson = "{\n" +
                "    \"name\": \"Marcus\",\n" +
                "    \"age\": 22,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 50000\n" +
                "}";

        //when
        //then
        mockMvc.perform(put(EMPLOYEES_URI + "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_404_when_delete_employee_given_wrong_employee_id() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete(EMPLOYEES_URI + new ObjectId().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_400_when_delete_employee_given_invalid_employee_id() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete(EMPLOYEES_URI + "123"))
                .andExpect(status().isBadRequest());
    }
}
