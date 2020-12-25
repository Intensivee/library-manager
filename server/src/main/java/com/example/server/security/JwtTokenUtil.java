package com.example.server.security;

import com.example.server.security.JwtTokenConfig;
import com.example.server.security.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private final JwtTokenConfig tokenConfig;

    @Autowired
    public JwtTokenUtil(JwtTokenConfig jwtConfig) {
        this.tokenConfig = jwtConfig;
    }

    public String createToken(Authentication authResult) {

        JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();


        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .claim("email", userDetails.getUsername())
                .claim("authorities", authResult.getAuthorities())  // body
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(this.tokenConfig.getTokenExpirationAfterDays())))
                .signWith(this.tokenConfig.getSecretKeyForSigning())
                .compact();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.getClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(this.tokenConfig.getSecretKeyForSigning())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getId(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public String getEmail(String token) {
        Claims claims = this.getClaims(token);
        return (String) claims.get("email");
    }

    public Set<String> getAuthorities(String token) {
        Claims claims = this.getClaims(token);
        var authorities = (List<Map<String, String>>) claims.get("authorities");
        return authorities.stream()
                .map(auth -> auth.get("authority"))
                .collect(Collectors.toSet());
    }

    public Date getExpirationDate(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    public Boolean isExpired(String token) {
        return this.getExpirationDate(token).before(new Date());
    }
}
