package com.example.coe.integration;

import com.example.coe.integration.extensions.DockerComposeExtension;
import com.example.coe.integration.responses.ErrorItemResponse;
import com.example.coe.integration.responses.ErrorResponse;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.activities.CreateActivityViewModel;
import com.example.coe.models.activities.UpdateActivityViewModel;
import com.example.coe.models.blockers.UpdateBlockerViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, DockerComposeExtension.class})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ActivityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createActivity_whenSuppliedWithValidData_returnsIsCreated() throws Exception {

        var newActivity = new CreateActivityViewModel(9, "Work out", "Run a 5k");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newActivity)))
                .andExpect(status().isCreated())
                .andReturn();

        var activityResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ActivityViewModel.class);

        assertThat(activityResponse.getId())
                .isEqualTo(11);
        assertThat(activityResponse.getTitle())
                .isEqualTo(newActivity.getTitle());
        assertThat(activityResponse.getDescription())
                .isEqualTo(newActivity.getDescription());
        assertThat(activityResponse.getTodoId())
                .isEqualTo(newActivity.getTodoId());
    }

    @Test
    void createActivity_whenSuppliedWithInValidData_returnsBadRequest() throws Exception {

        var newActivity = new CreateActivityViewModel(9, "Test", "Test");

        var result = mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newActivity)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        ErrorItemResponse[] expectedFieldErrors = new ErrorItemResponse[]{
                new ErrorItemResponse("todoId", "No todo exists with Id %d"),
                new ErrorItemResponse("title", "must not be blank"),
                new ErrorItemResponse("description", "must not be blank"),
        };

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");
        assertThatJson(errorResponse.getFieldErrors())
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedFieldErrors);
    }

    @Test
    void updateActivity_whenSuppliedWithValidData_returnsNoContent() throws Exception {
        UpdateActivityViewModel updateActivity = new UpdateActivityViewModel(
                9,
                9,
                "Work out",
                "Run a 10k",
                60
        );

        mockMvc.perform(put("/activities/9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateActivity)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateActivity_whenSuppliedWithValidDataButNoRecord_returnsNotFound() throws Exception {
        UpdateActivityViewModel updateActivity = new UpdateActivityViewModel(
                11,
                11,
                "Clean car",
                "Hoovered the inside",
                60
        );

        mockMvc.perform(put("/activities/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateActivity)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateActivity_whenSuppliedWithInValidData_returnsBadRequest() throws Exception {
        UpdateActivityViewModel updateActivity = new UpdateActivityViewModel(
                11,
                9,
                "",
                "",
                60
        );

        var result = mockMvc.perform(put("/activities/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateActivity)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");


    }
}


