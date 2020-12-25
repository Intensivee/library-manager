package com.example.server.config;

import com.example.server.security.JwtTokenConfig;
import com.example.server.security.JwtTokenVerifierFilter;
import com.example.server.security.JwtAuthenticationEntryPoint;
import com.example.server.security.JwtTokenUtil;
import com.example.server.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.server.security.UserPermission.*;
import static com.example.server.security.UserRole.*;

@Configuration
@EnableWebSecurity
public class JwtWebConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService userDetailsService;
    private final JwtTokenConfig tokenConfig;
    private final JwtTokenUtil tokenUtils;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public JwtWebConfig(AuthenticationService userDetailsService, JwtTokenConfig tokenConfig,
                        JwtTokenUtil tokenUtils, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenConfig = tokenConfig;
        this.tokenUtils = tokenUtils;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                    .userDetailsService(this.userDetailsService)
                    .passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler) // 401 instead of 403 when unauthorized token
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(new JwtTokenVerifierFilter(tokenConfig, tokenUtils), UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                // ------- general -------
                    .antMatchers("/authentication/**").permitAll()
                    .antMatchers("/",
                            "/**/*.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js").permitAll()
                // ------- books -------
                    .antMatchers("/books/**").permitAll()
                // ------- authors -------
                    .antMatchers("/authors").permitAll()
                // ------- categories -------
                    .antMatchers(HttpMethod.GET, "/categories").permitAll()
                    .antMatchers("/categories").hasAnyAuthority(CATEGORY_WRITE.getPermission())
                // ------- copies -------
                    .antMatchers("/copies/search/findByUserId/*").hasAnyAuthority(USER_READ.getPermission())
                    .antMatchers(HttpMethod.GET, "/copies/**").permitAll()
                    .antMatchers("/copies/**").hasAnyAuthority(COPY_WRITE.getPermission())
                // ------- users -------
                    .antMatchers(HttpMethod.GET, "/users/*").hasAnyRole(USER.name(), AUTHORIZED_USER.name(), ADMIN.name())
                    .antMatchers(HttpMethod.PUT, "/users/*").hasAnyRole(USER.name(), AUTHORIZED_USER.name(), ADMIN.name())
                    .antMatchers("/users/**").hasAnyRole(ADMIN.name())
                .anyRequest().authenticated();
    }
}
