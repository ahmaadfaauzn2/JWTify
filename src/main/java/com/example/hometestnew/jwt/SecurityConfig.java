package com.example.hometestnew.jwt;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/registration", "/profile", "/profile/update", "/profile/image", "/banner", "/services", "/balance", "/topup", "/transaction", "/transaction/history", "/v1/api-docs", "/v2/api-docs", "/v3/api-docs","/v3/api-docs/**", "/swagger-resources", "/swagger-resouirces/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/auth-controller/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


}


