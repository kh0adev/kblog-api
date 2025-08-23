package com.kblog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import com.kblog.user.User;
import com.kblog.user.UserRepository;
import com.kblog.user.UserRole;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class PersistenceConfig {

    @Bean
    AuditorAware<User> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof User)
                .map(User.class::cast);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            User appUserA = User.builder()
                    .userName("test123")
                    .password(passwordEncoder.encode("test123"))
                    .role(UserRole.ADMIN).build();

            appUserRepository.save(appUserA);
        };
    }
}
