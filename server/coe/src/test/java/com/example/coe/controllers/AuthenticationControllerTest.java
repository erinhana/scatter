package com.example.coe.controllers;

import com.example.coe.models.authentication.AuthenticateViewModel;
import com.example.coe.security.JwtTokenUtil;
import com.example.coe.security.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController authenticationController;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    UserSecurityService userSecurityService;
    @Mock
    UserDetails userDetails;
    @Mock
    AuthenticationManager authenticationManager;


    @Test
    void authenticate_withValidCredentials_returnsAuthenticationToken() {

        //Arrange
        var loginDetails = new AuthenticateViewModel();

        when(userSecurityService.loadUserByUsername(loginDetails.getEmailAddress())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(any());

        //Act
        var result = authenticationController.authenticate(loginDetails);

        //Assert
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userSecurityService, times(1)).loadUserByUsername(any());
        verify(jwtTokenUtil, times(1)).generateToken(any());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
    }

}
