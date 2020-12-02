package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getEmployees() {
        return this.employees;
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable Integer employeeId) {
        return this.employees
                .stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> getEmployeesByGender(@RequestParam String gender){
        return this.employees
                .stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employeeUpdate) {
        employees.add(employeeUpdate);
        return employeeUpdate;
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employeeUpdate) {
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
