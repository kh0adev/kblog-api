package com.kblog.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final JwtTokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtTokenService tokenService,
            AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> generateToken(
            @RequestBody LoginRequest request) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password());

                try {
                    var authentication = authenticationManager.authenticate(authenticationToken);
                } catch (InternalAuthenticationServiceException e) {
                    e.printStackTrace(); // Log lỗi thật
                    return ResponseEntity.status(401).body("Authentication failed: " + e.getCause().getMessage());
                }


        var authentication = authenticationManager.authenticate(authenticationToken);
        var token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(token);
    }

}

