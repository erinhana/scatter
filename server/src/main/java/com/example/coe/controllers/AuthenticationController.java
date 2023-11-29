package com.example.coe.controllers;

import com.example.coe.models.authentication.AuthenticateViewModel;
import com.example.coe.models.authentication.JwtResponseViewModel;
import com.example.coe.security.JwtTokenUtil;
import com.example.coe.security.UserSecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserSecurityService userSecurityService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticateViewModel viewModel) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(viewModel.getEmailAddress(), viewModel.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException ex) {

            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        var userDetails = userSecurityService.loadUserByUsername(viewModel.getEmailAddress());

        var responseToken = new JwtResponseViewModel(jwtTokenUtil.generateToken(userDetails));

        return ResponseEntity.ok(responseToken);
    }

}
