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

    // region Add Employee
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
    //endregion

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
