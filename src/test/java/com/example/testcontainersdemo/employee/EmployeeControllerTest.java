package com.example.testcontainersdemo.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    EmployeeService employeeService;

    EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    void shouldFetchAllEmployees() {
        Employee firstEmployee = mock(Employee.class);
        Employee secondEmployee = mock(Employee.class);
        when(employeeService.fetchAllEmployees()).thenReturn(List.of(firstEmployee, secondEmployee));

        List<Employee> actualEmployees = employeeController.getAllEmployees();

        assertThat(actualEmployees).containsExactly(firstEmployee, secondEmployee);
    }

    @Test
    void shouldFetchEmployeeWithProvidedId() throws Exception {
        Employee firstEmployee = mock(Employee.class);
        when(employeeService.fetchEmployeeWithId(1)).thenReturn(firstEmployee);

        Employee actualEmployee = employeeController.getEmployeeWithId(1);

        assertEquals(actualEmployee, firstEmployee);
    }

    @Test
    void shouldThrowErrorIfEmployeeWithProvidedIdDoesNotExist() throws Exception {
        when(employeeService.fetchEmployeeWithId(1)).thenThrow(new Exception());

        assertThrows(Exception.class, () -> employeeController.getEmployeeWithId(1));
    }

    @Test
    void shouldAddNewEmployee() {
        AddEmployeeRequest request = new AddEmployeeRequest();
        request.setFirstname("John");
        request.setLastname("Doe");

        Employee expectedResponse = new Employee(1, "John", "Doe");
        when(employeeService.registerEmployee(new Employee(null, "John", "Doe")))
                .thenReturn(expectedResponse);

        Employee actualResponse = employeeController.addEmployee(request);
        assertEquals(expectedResponse, actualResponse);
    }
}
