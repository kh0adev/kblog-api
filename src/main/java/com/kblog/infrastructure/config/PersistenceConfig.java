package com.kblog.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import com.kblog.domain.entities.Post;
import com.kblog.domain.entities.User;
import com.kblog.domain.enums.PostStatus;
import com.kblog.domain.enums.UserRole;
import com.kblog.infrastructure.repositories.PostRepository;
import com.kblog.infrastructure.repositories.UserRepository;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
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
            PostRepository postRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            User appUserA = User.builder()
                    .userName("test123")
                    .password(passwordEncoder.encode("test123"))
                    .role(UserRole.ADMIN).build();

            User appUserB = User.builder()
                    .userName("user123")
                    .password(passwordEncoder.encode("user123"))
                    .role(UserRole.USER).build();

            Post post = Post.builder()
                    .title("Test Post")
                    .content("This is a test post")
                    .status(PostStatus.NEW)
                    .author(appUserB)
                    .build();

            post.setCreatedBy(appUserB);
            post.setLastModifiedBy(appUserB);
            post.setCreatedDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            post.setLastModifiedDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

            Post post2 = Post.builder()
                    .title("Test Post 2")
                    .content("This is a test post2")
                    .status(PostStatus.APPROVED)
                    .author(appUserB)
                    .build();

            post2.setCreatedBy(appUserB);
            post2.setLastModifiedBy(appUserB);
            post2.setCreatedDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            post2.setLastModifiedDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            Post post3 = Post.builder()
                    .title("Test Post 3")
                    .content("This is a test post 3")
                    .status(PostStatus.REJECTED)
                    .author(appUserB)
                    .build();

            post3.setCreatedBy(appUserB);
            post3.setLastModifiedBy(appUserB);
            post3.setCreatedDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            post3.setLastModifiedDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

            appUserRepository.save(appUserA);
            appUserRepository.save(appUserB);
            postRepository.save(post);
            postRepository.save(post2);
            postRepository.save(post3);
        };
    }
}
