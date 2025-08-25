package com.kblog.application.services;

import com.kblog.application.dtos.UserResponse;
import com.kblog.domain.entities.User;
import com.kblog.infrastructure.repositories.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Page<UserResponse> findAll(Pageable amount) {

        return repository.findAll(amount).map(u -> toUserResponse(u));
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getEmail(),
                user.getPhoneNumber());
    }

}
