package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeRepository1 employeeRepository1;

    public EmployeeService(EmployeeRepository1 employeeRepository1) {
        this.employeeRepository1 = employeeRepository1;
    }

    public List<Employee> getEmployees() {
        return employeeRepository1.findAll();
    }

    public List<Employee> getEmployeesPaginized(Integer page, Integer pageSize) {
        return employeeRepository1.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public Employee getEmployee(String employeeId) {
        return employeeRepository1.findById(employeeId).orElse(null);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository1.findAllByGender(gender);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository1.save(employee);
    }

    public Employee updateEmployee(String employeeId, Employee employee) {
        if(getEmployee(employeeId)!=null){
            employee.setId(employeeId);
            return employeeRepository1.save(employee);
        }
        return null;
    }

    public void deleteEmployee(String employeeId) {
        employeeRepository1.deleteById(employeeId);
    }
}
