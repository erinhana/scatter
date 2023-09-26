package com.example.coe.security;

import com.example.coe.exception.NotFoundException;
import com.example.coe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        var user = userRepository.findFirstByEmailAddress(emailAddress)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return UserPrincipal.create(user);
    }
}
