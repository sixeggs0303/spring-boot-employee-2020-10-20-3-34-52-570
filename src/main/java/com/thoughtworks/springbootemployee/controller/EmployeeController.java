package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable Integer employeeId) {
        return this.employees
                .stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Employee create(@RequestBody Employee employeeUpdate) {
        employees.add(employeeUpdate);
        return employeeUpdate;
    }

    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable Integer employeeId, @RequestBody Employee employeeUpdate) {
        employees.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> {
                    employees.remove(employee);
                    employees.add(employeeUpdate);
                });
        return employeeUpdate;
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable Integer employeeId) {
        employees.stream()
                .filter(employee -> employeeId.equals(employee.getId()))
                .findFirst()
                .ifPresent(employee -> employees.remove(employee));
    }
}
