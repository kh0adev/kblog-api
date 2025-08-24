package com.kblog.api.controllers;

import com.kblog.application.dtos.LoginRequest;
import com.kblog.application.dtos.RegisterRequest;
import com.kblog.application.services.AuthService;
import com.kblog.application.services.JwtGenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    private final JwtGenerator jwtGenerator;

    private final AuthenticationManager authenticationManager;
    private final AuthService service;

    public AuthController(JwtGenerator tokenService,
            AuthenticationManager authenticationManager, AuthService service) {
        this.jwtGenerator = tokenService;
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> generateToken(
            @RequestBody LoginRequest request) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.userName(),
                request.password());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var token = jwtGenerator.generateToken(authentication);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity.noContent().build();
    }

}
