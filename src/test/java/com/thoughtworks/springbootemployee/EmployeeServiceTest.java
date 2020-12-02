package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {
    @Test
    void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        final List<Employee> expected = new ArrayList<>();
        expected.add(new Employee(1, "Marcus", 22, "male", 50));
        expected.add(new Employee(2, "Theo", 22, "male", 50000));
        expected.forEach(employeeRepository::create);

        //when
        final List<Employee> employees = employeeService.getEmployees();

        //then
        assertEquals(expected, employees);
    }

    @Test
    void should_return_an_employee_when_get_employee_given_an_employee() {
        //given
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee expected = new Employee(1, "Marcus", 22, "male", 50);
        employeeRepository.create(expected);

        //when
        final Employee employee = employeeService.getEmployee(expected.getId());

        //then
        assertEquals(expected, employee);
    }
}
