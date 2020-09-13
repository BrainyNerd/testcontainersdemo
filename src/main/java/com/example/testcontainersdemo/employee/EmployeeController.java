package com.example.testcontainersdemo.employee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employees")
@EnableSwagger2
public class EmployeeController {

    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees(){
        return employeeService.fetchAllEmployees();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeWithId(@PathVariable("id") Integer id) throws Exception {
        return employeeService.fetchEmployeeWithId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody AddEmployeeRequest request){
        return employeeService.registerEmployee(mapRequest(request));
    }

    //region Mappers
    private Employee mapRequest(AddEmployeeRequest request){
        return Employee.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .build();
    }
    //endregion
}
