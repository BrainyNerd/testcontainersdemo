package com.example.testcontainersdemo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> fetchAllEmployees() {
        return repository.findAll();
    }

    public Employee fetchEmployeeWithId(Integer id) throws Exception {
        System.out.println("id = " + id);
        return repository
                .findById(id)
                .orElseThrow(Exception::new);
    }

    public Employee registerEmployee(Employee employee) {
        return repository.save(employee);
    }
}
