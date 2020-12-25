package com.example.server.controller;

import com.example.server.entity.User;
import com.example.server.security.JwtAuthenticationService;
import com.example.server.security.JwtTokenConfig;
import com.example.server.security.payload.LoginRequest;
import com.example.server.security.payload.RegisterRequest;
import com.example.server.security.payload.TokenResponse;
import com.example.server.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final JwtAuthenticationService authenticationService;
    private final JwtTokenUtil tokenUtil;
    private final JwtTokenConfig tokenConfig;

    @Autowired
    public AuthenticationController(JwtTokenUtil tokenUtil,
                                    JwtTokenConfig tokenConfig, JwtAuthenticationService authenticationService) {
        this.tokenUtil = tokenUtil;
        this.tokenConfig = tokenConfig;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateCredentials(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = this.authenticationService.authenticateCredentials(loginRequest);

        String token = this.tokenUtil.createToken(authentication);

        return ResponseEntity.ok(new TokenResponse(this.tokenConfig.getTokenPrefix() + token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){

        User user = this.authenticationService.registerUser(registerRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
