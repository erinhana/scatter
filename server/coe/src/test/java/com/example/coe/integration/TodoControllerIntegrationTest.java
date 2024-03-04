package com.example.coe.integration;

import com.example.coe.integration.extensions.DockerComposeExtension;
import com.example.coe.integration.responses.ErrorResponse;
import com.example.coe.models.todos.TodoDetailViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, DockerComposeExtension.class})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTodo_whenSuppliedWithValidData_returnsIsOk() throws Exception {

        var result = mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andReturn();

        var todoDetailResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), TodoDetailViewModel.class);

        assertThat(todoDetailResponse.getId())
                .isEqualTo(1);
        assertThat(todoDetailResponse.getUserId())
                .isEqualTo(1);
        assertThat(todoDetailResponse.getDescription())
                .isEqualTo("Collect Prescription");
        assertThat(todoDetailResponse.getDeadline())
                .isEqualTo("2023-10-02");
        assertThat(todoDetailResponse.getCreatedAt())
                .isNotNull();
        assertThat(todoDetailResponse.getUpdatedAt())
                .isNotNull();
        assertThat(todoDetailResponse.getCompletedAt())
                .isNull();

    }

    @Test
    void getTodo_whenCalledWithInvalidId_throwsNotFoundException() throws Exception {

        var result = mockMvc.perform(get("/todos/98"))
                .andExpect(status().isNotFound())
                .andReturn();


        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("Todo not found");
    }

}

//    @Test
//    void createTodo_whenSuppliedWithValidData_returnsIsCreated() throws Exception {
//
//    }

