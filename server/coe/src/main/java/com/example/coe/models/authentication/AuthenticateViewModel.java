package com.example.coe.models.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
public class AuthenticateViewModel {

    @NotBlank
    @Email
    @JsonProperty("emailAddress")
    private String emailAddress;

    @NotBlank
    @JsonProperty("password")
    private String password;

}