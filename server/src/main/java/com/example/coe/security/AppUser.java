package com.example.coe.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AppUser {

    @Bean
    @RequestScope
    public CurrentAppUser get() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            return CurrentAppUser.builder()
                    .id(userPrincipal.getId())
                    .email(userPrincipal.getUsername())
                    .build();
        }
        throw new AccessDeniedException("Access denied");
    }

    /**
     * The current logged-in user.
     */
    @Getter
    @Builder
    public static class CurrentAppUser {

        private int id;
        private String email;
    }
}
