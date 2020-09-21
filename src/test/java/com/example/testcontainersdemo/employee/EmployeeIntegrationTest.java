package com.example.testcontainersdemo.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

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
                "  \"id\": 1,\n" +
                "  \"firstname\": \"Jack\",\n" +
                "  \"lastname\": \"Sparrow\"\n" +
                "}\n";

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().json(expectedResponse));
    }
}
