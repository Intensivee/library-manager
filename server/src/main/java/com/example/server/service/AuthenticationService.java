package com.example.server.service;

import com.example.server.entity.User;
import com.example.server.exception.RegistrationException;
import com.example.server.payload.LoginRequest;
import com.example.server.payload.RegisterRequest;
import com.example.server.repository.UserRepository;
import com.example.server.security.JwtUserDetails;
import com.example.server.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository,
                                 @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return this.userRepository.findByEmail(email)
                .map( user -> new JwtUserDetails(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        UserRole.values()[user.getRole()].getGrantedAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with Email %s not found", email)));
    }

    public UserDetails loadUserById(Long id) {
        return this.userRepository.findById(id)
                .map( user -> new JwtUserDetails(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        UserRole.values()[user.getRole()].getGrantedAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id %s not found", id)));
    }

    public Authentication authenticateCredentials(LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public User registerUser(RegisterRequest registerRequest){
        this.userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent( user -> {
                    throw new RegistrationException(String.format(
                            "Email: %s already exists.", registerRequest.getEmail()
                    ));
                });

        User user = this.createUserFromRegisterRequest(registerRequest);
        return this.userRepository.save(user);
    }

    private User createUserFromRegisterRequest(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        return user;
    }
}
