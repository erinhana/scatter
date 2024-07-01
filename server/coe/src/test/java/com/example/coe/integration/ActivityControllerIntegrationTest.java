package com.example.coe.integration;

import com.example.coe.integration.extensions.DockerComposeExtension;
import com.example.coe.integration.responses.ErrorItemResponse;
import com.example.coe.integration.responses.ErrorResponse;
import com.example.coe.models.activities.ActivityDetailViewModel;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.activities.CreateActivityViewModel;
import com.example.coe.models.activities.UpdateActivityViewModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void getActivity_whenCalledWithValidId_returnsIsOk() throws Exception {

        var result = mockMvc.perform(get("/activities/2"))
                .andExpect(status().isOk())
                .andReturn();


        var activityResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ActivityDetailViewModel.class);

        assertThat(activityResponse.getId())
                .isEqualTo(2);
        assertThat(activityResponse.getTodoId())
                .isEqualTo(2);
        assertThat(activityResponse.getTitle())
                .isEqualTo("Walked dog");
        assertThat(activityResponse.getDescription())
                .isEqualTo("Took dog to the park");
        assertThat(activityResponse.getCreatedAt())
                .isNotNull();
        assertThat(activityResponse.getUpdatedAt())
                .isNotNull();
        assertThat(activityResponse.getTimeSpent())
                .isEqualTo(60);
    }

    @Test
    void getActivity_whenCalledWithInValidId_returnsBadRequest() throws Exception {

        var result = mockMvc.perform(get("/activities/98"))
                .andExpect(status().isNotFound())
                .andReturn();


        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("No activity exists with this id");

    }

    @Test
    void getAllActivities_whenCalled_returnsIsOk() throws Exception {

        var result = mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andReturn();

        var activityResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<List<ActivityViewModel>>() {
                });

        AssertionsForInterfaceTypes.assertThat(activityResponse)
                .isNotEmpty();
        assertThat(activityResponse.get(0).getId())
                .isEqualTo(1);
        assertThat(activityResponse.get(0).getTodoId())
                .isEqualTo(1);
        assertThat(activityResponse.get(0).getTitle())
                .isEqualTo("Collected prescription from Chemists");
        assertThat(activityResponse.get(0).getDescription())
                .isEqualTo("Collect Prescription");

    }


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
                new ErrorItemResponse("description", "size must be between 5 and 100"),
                new ErrorItemResponse("title", "size must be between 5 and 100"),
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
                1,
                9,
                "",
                "",
                60
        );

        var result = mockMvc.perform(put("/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateActivity)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");

    }

    @Test
    void deleteActivity_whenCalledWithValidId_returnsIsNoContent() throws Exception {
        var result = mockMvc.perform(delete("/activities/2"))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(result.getResponse().getContentAsByteArray()).isEmpty();
    }

    @Test
    void deleteActivity_whenCalledWithInvalidId_throwsNotFoundException() throws Exception {
        var result = mockMvc.perform(delete("/activities/99"))
                .andExpect(status().isNotFound())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("No activity exists with this Id");

    }


}


