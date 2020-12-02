package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesPaginized(Integer page, Integer pageSize) {
        return employeeRepository.findAll(page, pageSize);
    }

    public Employee getEmployee(Integer employeeId) {
        return employeeRepository.find(employeeId);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.create(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee employee) {
        return employeeRepository.update(employeeId, employee);
    }

    public void deleteEmployee(Integer employeeId) {
        employeeRepository.delete(employeeId);
    }
}
