package com.example.coe.integration;

import com.example.coe.integration.extensions.DockerComposeExtension;
import com.example.coe.integration.responses.ErrorItemResponse;
import com.example.coe.integration.responses.ErrorResponse;
import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.models.users.UserViewModel;
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
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, DockerComposeExtension.class})
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createUser_whenSuppliedWithValidData_returnsIsCreated() throws Exception {

        var newUser = new CreateUserViewModel("testuser@test.com", "Test", "User", "Password1");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andReturn();

        var userResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), UserViewModel.class);

        assertThat(userResponse.getId())
                .isEqualTo(11);
        assertThat(userResponse.getEmailAddress())
                .isEqualTo(newUser.getEmailAddress());
    }

    @Test
    void createUser_whenSuppliedWithInvalidData_returnsBadRequest() throws Exception {

        var newUser = new CreateUserViewModel();

        var result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        ErrorItemResponse[] expectedFieldErrors = new ErrorItemResponse[]{
                new ErrorItemResponse("emailAddress", "must not be blank"),
                new ErrorItemResponse("firstName", "must not be blank"),
                new ErrorItemResponse("lastName", "must not be blank"),
                new ErrorItemResponse("password", "must not be blank")
        };

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");
        assertThatJson(errorResponse.getFieldErrors())
                .when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedFieldErrors);
    }

    @Test
    void updateUser_whenSuppliedWithValidData_returnsNoContent() throws Exception {
        UpdateUserViewModel updateUser = new UpdateUserViewModel(
                "testuser_update@test.com",
                "Test",
                "User",
                "Password1");

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_whenSuppliedWithValidDataButNoRecord_returnsNotFound() throws Exception {
        UpdateUserViewModel updateUser = new UpdateUserViewModel(
                "janedoe@test.com",
                "Jane",
                "Doe",
                "Password2");

        mockMvc.perform(put("/users/98")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_whenSuppliedWithInvalidData_returnsBadRequest() throws Exception {
        UpdateUserViewModel updateUser = new UpdateUserViewModel();

        var result = mockMvc.perform(put("/users/98")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("validation error");
    }

    @Test
    void deleteUser_whenCalledWithValidId_returnsIsNoContent() throws Exception {
        var result = mockMvc.perform(delete("/users/2"))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(result.getResponse().getContentAsByteArray()).isEmpty();
    }

    @Test
    void deleteUser_whenCalledWithInvalidId_throwsNotFoundException() throws Exception {
        var result = mockMvc.perform(delete("/users/99"))
                .andExpect(status().isNotFound())
                .andReturn();

        var errorResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(errorResponse.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(errorResponse.getMessage()).isEqualTo("No user exists with Id");

    }
}

