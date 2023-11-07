package com.example.coe.controllers.integration;

import com.example.coe.models.blockers.BlockerViewModel;
import com.example.coe.models.blockers.CreateBlockerViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BlockerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
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
                .isEqualTo(1);
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

        var blockerResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), BlockerViewModel.class);

        assertThat(blockerResponse.getId())
                .isEqualTo(1);
        assertThat(blockerResponse.getTitle())
                .isEqualTo("Test blocker");
        assertThat(blockerResponse.getDescription())
                .isEqualTo("Test blocker description");

    }
}
