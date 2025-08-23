package com.kblog.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.kblog.user.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

}