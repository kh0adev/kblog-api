package com.kblog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kblog.user.User;
import com.kblog.user.UserRepository;
import com.kblog.user.UserRole;

@Configuration
public class PersistenceConfig {

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
