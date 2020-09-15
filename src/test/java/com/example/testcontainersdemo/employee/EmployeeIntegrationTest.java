package com.example.testcontainersdemo.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        String expectedResponse = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"firstname\": \"John\",\n" +
                "    \"lastname\": \"Smith\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"firstname\": \"Sam\",\n" +
                "    \"lastname\": \"Child\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"firstname\": \"James\",\n" +
                "    \"lastname\": \"Bond\"\n" +
                "  }\n" +
                "]\n";

        mockMvc.perform(get("/api/v1/employees")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @Transactional
    void shouldFetchEmployeeWithProvidedId() throws Exception {
        employeeRepository.save(new Employee(4, "John", "Smith"));
        employeeRepository.save(new Employee(5, "Sam", "Child"));
        employeeRepository.save(new Employee(6, "James", "Bond"));

        String expectedResponse = "{\n" +
                "  \"id\": 5,\n" +
                "  \"firstname\": \"Sam\",\n" +
                "  \"lastname\": \"Child\"\n" +
                "}\n";

        mockMvc.perform(get("/api/v1/employees/5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @Transactional
    void shouldAddNewEmployee() throws Exception {

        String requestBody = "{\n" +
                "  \"firstname\": \"Jack\",\n" +
                "  \"lastname\": \"Sparrow\"\n" +
                "}\n";

        MockHttpServletRequestBuilder request = post("/api/v1/employees")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        String expectedResponse = "{\n" +
                "  \"id\": 7,\n" +
                "  \"firstname\": \"Jack\",\n" +
                "  \"lastname\": \"Sparrow\"\n" +
                "}\n";

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().json(expectedResponse));
    }
}
