package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository1 employeeRepository1;

    @AfterEach
    void tearDown() {
        employeeRepository1.deleteAll();
    }

    public static final String EMPLOYEES_URI = "/employees/";

    @Test
    void should_return_all_employees_when_get_all_given_employees() throws Exception {
        //given
       Employee employee = new Employee("Theo", 18, "male", 50000);
       employeeRepository1.save(employee);

        //when
        //then
        mockMvc.perform(get(EMPLOYEES_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Theo"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(50000));
    }

    @Test
    void should_return_employee_when_create_given_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"name\": \"Theo\",\n" +
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
                .andExpect(jsonPath("$.name").value("Theo"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(50000));

        List<Employee> employees = employeeRepository1.findAll();
        assertEquals(1, employees.size());
        assertEquals("Theo", employees.get(0).getName());
        assertEquals(22, employees.get(0).getAge());
    }

    @Test
    void should_return_updated_employee_when_update_given_employee() throws Exception {
        //given
        Employee employee = new Employee("Theo", 18, "male", 50000);
        employeeRepository1.save(employee);
        String employeeAsJson = "{\n" +
                "    \"name\": \"Theo\",\n" +
                "    \"age\": 22,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 50000\n" +
                "}";

        //when
        //then
        mockMvc.perform(put(EMPLOYEES_URI+employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Theo"))
                .andExpect(jsonPath("$.age").value(22))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(50000));

        List<Employee> employees = employeeRepository1.findAll();
        assertEquals(1, employees.size());
        assertEquals("Theo", employees.get(0).getName());
        assertEquals(22, employees.get(0).getAge());
    }
}
