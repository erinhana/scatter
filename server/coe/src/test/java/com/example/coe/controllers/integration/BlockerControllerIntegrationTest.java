package com.example.coe.controllers.integration;

import com.example.coe.controllers.integration.extensions.DockerComposeExtension;
import com.example.coe.controllers.integration.responses.ErrorItemResponse;
import com.example.coe.controllers.integration.responses.ErrorResponse;
import com.example.coe.models.blockers.BlockerDetailViewModel;
import com.example.coe.models.blockers.BlockerViewModel;
import com.example.coe.models.blockers.CreateBlockerViewModel;
import com.example.coe.models.blockers.UpdateBlockerViewModel;
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
                .isEqualTo("No study guide");
        assertThat(blockerDetailResponse.getDescription())
                .isEqualTo("Lost study guide");
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


    //TODO Still need to add tests for Get Blocker Types

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
                .isEqualTo(2);
        assertThat(blockerResponse.getTitle())
                .isEqualTo("Test blocker");
        assertThat(blockerResponse.getDescription())
                .isEqualTo("Test blocker description");
        //TODO Ask Mark why userId and blockerTypeId is being returned as zero

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
                15,
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
    void updateBlocker_whenSuppliedWithInValidData_returnsBadRequest() throws Exception {
        UpdateBlockerViewModel updateBlocker = new UpdateBlockerViewModel();


    }


    @Test
    void deleteBlocker_whenCalledWithValidId_returnsIsNoContent() throws Exception {
        var result = mockMvc.perform(delete("/blockers/1"))
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
