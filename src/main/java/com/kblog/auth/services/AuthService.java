package com.kblog.auth.services;

import com.kblog.auth.dtos.RegisterRequest;
import com.kblog.user.models.User;
import com.kblog.user.models.UserRole;
import com.kblog.user.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User register(RegisterRequest request) {

        var userExists = appUserRepository.findByUserName(request.userName());

        if (userExists.isPresent()) {
            throw new IllegalStateException("Username already exists");
        } else {
            var user = User.builder()
            .userName(request.userName())
            .email(request.email())
            .firstName(request.firstName())
            .lastName(request.lastName())
            .phoneNumber(request.phoneNumber())
            .role(UserRole.ADMIN)
            .password(passwordEncoder.encode(request.password()))
            .build();
            return appUserRepository.save(user);
        }
    }
}
