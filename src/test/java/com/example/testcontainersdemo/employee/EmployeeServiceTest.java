package com.example.testcontainersdemo.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    EmployeeService employeeService;


    @BeforeEach
    void setUp() {
        employeeService=new EmployeeService(employeeRepository);
    }

    @Test
    void shouldFetchAllEmployees() {
        Employee firstEmployee = mock(Employee.class);
        Employee secondEmployee = mock(Employee.class);
        when(employeeRepository.findAll()).thenReturn(List.of(firstEmployee, secondEmployee));

        List<Employee> employees = employeeService.fetchAllEmployees();
        assertThat(employees).containsExactly(firstEmployee, secondEmployee);
    }

    @Test
    void shouldFetchEmployeeProvidedId() throws Exception {
        Employee firstEmployee = mock(Employee.class);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(firstEmployee));

        Employee employee = employeeService.fetchEmployeeWithId(1);
        assertEquals(firstEmployee, employee);
    }

    @Test
    void shouldThrowExceptionIfNoEmployeeExistWithProvidedId() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> employeeService.fetchEmployeeWithId(1));
    }

    @Test
    void shouldAddNewEmployee() {
        Employee employeeDraft = mock(Employee.class);
        Employee expectedEmployee = mock(Employee.class);
        when(employeeRepository.save(employeeDraft)).thenReturn(expectedEmployee);
        Employee employee = employeeService.registerEmployee(employeeDraft);
        assertEquals(expectedEmployee, employee);
    }
}
