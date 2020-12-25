package com.example.server.controller;

import com.example.server.security.JwtTokenConfig;
import com.example.server.security.JwtTokenUtil;
import com.example.server.security.payload.LoginRequest;
import com.example.server.security.payload.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil tokenUtil;
    private final JwtTokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil tokenUtil, JwtTokenConfig tokenConfig, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
        this.tokenConfig = tokenConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateCredentials(@RequestBody LoginRequest loginRequest){

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = this.tokenUtil.createToken(authentication);

        return ResponseEntity.ok(new TokenResponse(this.tokenConfig.getTokenPrefix() + token));
    }
}
