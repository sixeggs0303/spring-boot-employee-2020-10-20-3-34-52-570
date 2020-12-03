package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository1;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository1 employeeRepository1;

    private final String employee1 = "Marcus";
    private final String employee2 = "Theo";
    private final String employee3 = "Linne";
    private final Integer age = 22;
    private final String male = "male";
    private final String female = "female";
    private final String employeeId1 = "1";
    private final String employeeId2 = "2";
    private final String employeeId3 = "3";
    private final Integer salary = 10000;

    @Test
    void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        final List<Employee> expected = new ArrayList<>();
        expected.add(new Employee());
        expected.add(new Employee());
        when(employeeRepository1.findAll()).thenReturn(expected);

        //when
        final List<Employee> actual = employeeService.getEmployees();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_an_employee_when_get_employee_given_an_employee() {
        //given
        Employee expected = new Employee();
        when(employeeRepository1.findById(employeeId1)).thenReturn(Optional.of(expected));

        //when
        final Employee actual = employeeService.getEmployee(employeeId1);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_only_male_employee_when_get_employee_given_gender_is_male() {
        //given
        final List<Employee> fullList = new ArrayList<>();
        fullList.add(new Employee());
        fullList.add(new Employee());

        when(employeeRepository1.findAllByGender(any())).thenReturn(fullList);

        //when
        final List<Employee> actual = employeeService.getEmployeesByGender("male");

        //then
        assertEquals(fullList, actual);
    }

    @Test
    void should_return_correct_page_when_get_employee_given_page_and_page_size() {
        //given
        final List<Employee> fullList = new ArrayList<>();
        fullList.add(new Employee());
        fullList.add(new Employee());
        fullList.add(new Employee());

        final Page<Employee> expected = new PageImpl<>(fullList);

        when(employeeRepository1.findAll((Pageable) any())).thenReturn(expected);
        //when
        final List<Employee> actual = employeeService.getEmployeesPaginized(1, 3);

        //then
        assertEquals(expected.toList(), actual);
    }

    @Test
    void should_return_created_employee_when_create_employee_given_an_employee() {
        //given
        Employee expected = new Employee();
        when(employeeRepository1.save(any())).thenReturn(expected);

        //when
        final Employee actual = employeeService.createEmployee(expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_updated_employee_when_update_employee_given_an_employee_id_and_employee() {
        //given
        Employee expected = new Employee();
        when(employeeRepository1.findById(any())).thenReturn(Optional.of(expected));
        when(employeeRepository1.save(any())).thenReturn(expected);

        //when
        Employee actual = employeeService.updateEmployee(employeeId1, expected);

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_call_repository_delete_by_id_when_delete_employee_given_an_employee_id() {
        //given

        //when
        employeeService.deleteEmployee(employeeId1);

        //then
        verify(employeeRepository1, times(1)).deleteById(employeeId1);
    }
}
