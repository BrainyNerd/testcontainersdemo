package com.example.testcontainersdemo.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Transactional
    void shouldFetchAllEmployees() throws Exception {
        employeeRepository.save(new Employee(1, "John", "Smith"));
        employeeRepository.save(new Employee(2, "Sam", "Child"));
        employeeRepository.save(new Employee(3, "James", "Bond"));

        mockMvc.perform(get("/api/v1/employees")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getData("response/fetchAllEmployees.json")));

    }

    @Test
    @Transactional
    void shouldAddNewEmployee() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/v1/employees")
                .content(getData("requests/addEmployee.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(getData("response/addEmployee.json")));
    }

    //region Helper to read json file
    private String getData(String file) {
        try {
            return IOUtils.toString(new InputStreamReader(Objects.requireNonNull(EmployeeIntegrationTest.class.getClassLoader().getResourceAsStream(file))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion
}
