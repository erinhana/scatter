package com.example.coe.integration;

import com.example.coe.integration.extensions.DockerComposeExtension;
import com.example.coe.integration.responses.ErrorItemResponse;
import com.example.coe.integration.responses.ErrorResponse;
import com.example.coe.models.blockers.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, DockerComposeExtension.class})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BlockerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getBlocker_whenCalledWithValidId_returnsIsOk() throws Exception {

        var result = mockMvc.perform(get("/blockers/1"))
                .andExpect(status().isOk())
                .andReturn();


        var blockerDetailResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BlockerDetailViewModel.class);

        assertThat(blockerDetailResponse.getId())
                .isEqualTo(1);
        assertThat(blockerDetailResponse.getTitle())
                .isEqualTo("Misplaced car keys");
        assertThat(blockerDetailResponse.getDescription())
                .isEqualTo("No spare set");
        assertThat(blockerDetailResponse.getCreatedAt())
                .isNotNull();
        assertThat(blockerDetailResponse.getUpdatedAt())
                .isNotNull();

    }

    @Test
    void getBlocker_whenCalledWithInvalidId_throwsNotFoundException() throws Exception {

        var result = mockMvc.perform(get("/blockers/5"))
                .andExpect(status().isNotFound())
                .andReturn();


        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("Blocker not found");
    }

    @Test
    void getAllBlockers_whenCalled_returnsIsOk() throws Exception {

        var result = mockMvc.perform(get("/blockers"))
                .andExpect(status().isOk())
                .andReturn();

        var blockerResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<List<BlockerViewModel>>() {
                });

        assertThat(blockerResponse)
                .isNotEmpty();
        assertThat(blockerResponse.get(0).getId())
                .isEqualTo(1);
        assertThat(blockerResponse.get(0).getUserId())
                .isEqualTo(1);
        assertThat(blockerResponse.get(0).getTitle())
                .isEqualTo("Misplaced car keys");
        assertThat(blockerResponse.get(0).getDescription())
                .isEqualTo("No spare set");
        assertThat(blockerResponse.get(0).getBlockerTypeId())
                .isEqualTo(1);


    }

    @Test
    void getBlockerType_whenCalled_returnsIsOk() throws Exception {

        var result = mockMvc.perform(get("/blockers/types"))
                .andExpect(status().isOk())
                .andReturn();

        var blockerTypeResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                new TypeReference<List<BlockerTypeViewModel>>() {
                });

        assertThat(blockerTypeResponse.get(0).getId())
                .isEqualTo(1);
        assertThat(blockerTypeResponse.get(0).getDescription())
                .isEqualTo("Distraction");


    }

    @Test
    void createBlocker_whenSuppliedWithValidData_returnsCreatedResponse() throws Exception {

        var newBlocker = new CreateBlockerViewModel(1, "Test blocker", "Test blocker description", 1);

        var result = mockMvc.perform(post("/blockers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBlocker)))
                .andExpect(status().isCreated())
                .andReturn();

        var blockerResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BlockerViewModel.class);

        assertThat(blockerResponse.getId())
                .isEqualTo(4);
        assertThat(blockerResponse.getTitle())
                .isEqualTo("Test blocker");
        assertThat(blockerResponse.getDescription())
                .isEqualTo("Test blocker description");


    }

    // We want this test to fail correctly
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    @Test
    void createBlocker_whenSuppliedWithInValidData_returnsBadRequest() throws Exception {

        var newBlocker = new CreateBlockerViewModel();


        var result = mockMvc.perform(post("/blockers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBlocker)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        ErrorItemResponse[] expectedFieldErrors = new ErrorItemResponse[]{
                new ErrorItemResponse("title", "must not be null"),
                new ErrorItemResponse("blockerTypeId", "must be greater than 0"),
                new ErrorItemResponse("userId", "must be greater than 0"),
                new ErrorItemResponse("description", "must not be null")
        };

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");
        assertThatJson(errorResponse.getFieldErrors())
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedFieldErrors);

    }


    @Test
    void updateBlocker_whenSuppliedWithValidData_returnsNoContent() throws Exception {
        UpdateBlockerViewModel updateBlocker = new UpdateBlockerViewModel(
                5,
                "Bad weather",
                "Unable to walk dog because of the weather",
                5);

        mockMvc.perform(put("/blockers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBlocker)))
                .andExpect(status().isNoContent());
    }


    @Test
    void updateBlocker_whenSuppliedWithValidDataButNoRecord_returnsNotFound() throws Exception {
        UpdateBlockerViewModel updateBlocker = new UpdateBlockerViewModel(
                5,
                "Bad weather",
                "Unable to walk dog because of the weather",
                5);

        mockMvc.perform(put("/blockers/98")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBlocker)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBlocker_whenSuppliedWithInValidData_returnsBadRequest() throws Exception {
        UpdateBlockerViewModel updateBlocker = new UpdateBlockerViewModel();

        var result = mockMvc.perform(put("/blockers/98")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBlocker)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");


    }


    @Test
    void deleteBlocker_whenCalledWithValidId_returnsIsNoContent() throws Exception {
        var result = mockMvc.perform(delete("/blockers/2"))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(result.getResponse().getContentAsByteArray()).isEmpty();
    }

    @Test
    void deleteBlocker_whenCalledWithInvalidId_throwsNotFoundException() throws Exception {
        var result = mockMvc.perform(delete("/blockers/99"))
                .andExpect(status().isNotFound())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("No blocker exists with Id");

    }


}
