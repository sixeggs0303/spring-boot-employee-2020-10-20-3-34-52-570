package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        return employeeRepository.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public Employee getEmployee(String employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(String employeeId, Employee employee) {
        if(getEmployee(employeeId)!=null){
            employee.setId(employeeId);
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
