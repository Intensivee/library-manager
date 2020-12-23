package com.example.server.security;

import com.example.server.repository.UserRepository;
import com.example.server.security.sth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map( user -> new JwtUserDetails(
                        user.getUsername(),
                        user.getPassword(),
                        UserRole.values()[user.getRole()].getGrantedAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found", email)));
    }
}
