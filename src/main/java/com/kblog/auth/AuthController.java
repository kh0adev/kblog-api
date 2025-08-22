package com.kblog.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity register(
            @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity.created(null).build();
    }

}
