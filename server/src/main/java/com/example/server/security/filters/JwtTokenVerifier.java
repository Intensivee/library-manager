package com.example.server.security.filters;

import com.example.server.security.JwtTokenConfig;
import com.example.server.security.util.JwtTokenUtil;
import com.google.common.base.Strings;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtTokenConfig tokenConfig;
    private final JwtTokenUtil tokenUtil;

    @Autowired
    public JwtTokenVerifier(JwtTokenConfig tokenConfig, JwtTokenUtil tokenUtil) {
        this.tokenConfig = tokenConfig;
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(this.tokenConfig.getAuthenticationHeaders());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(this.tokenConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.replace(this.tokenConfig.getTokenPrefix(), "");

            String email = this.tokenUtil.getEmail(token);

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = this.tokenUtil.getAuthorities(token)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e){
            throw new IllegalStateException("Token cannot be trusted");
        }
        // pass request+response to next filter in chain
        filterChain.doFilter(request, response);
    }
}
