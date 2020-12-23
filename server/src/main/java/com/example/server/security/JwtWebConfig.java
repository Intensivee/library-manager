package com.example.server.security;

import com.example.server.security.filters.JwtAuthenticationFilter;
import com.example.server.security.filters.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.server.security.sth.UserPermission.CATEGORY_WRITE;

@Configuration
@EnableWebSecurity
public class JwtWebConfig extends WebSecurityConfigurerAdapter {

    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenConfig tokenConfig;
    private final JwtTokenUtil tokenUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtWebConfig(JwtUserDetailsService userDetailsService, JwtTokenConfig tokenConfig,
                        JwtTokenUtil tokenUtils, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.tokenConfig = tokenConfig;
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenConfig, tokenUtils))
                .addFilterAfter(new JwtTokenVerifier(tokenConfig, tokenUtils), JwtAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js").permitAll()
                    .antMatchers("/books/**").permitAll()
                    .antMatchers("/authors").permitAll()
                    .antMatchers(HttpMethod.GET, "/categories").permitAll()
                    .antMatchers("/categories").hasAnyAuthority(CATEGORY_WRITE.getPermission())
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoder);
        provider.setUserDetailsService(this.userDetailsService);
        return provider;
    }
}
