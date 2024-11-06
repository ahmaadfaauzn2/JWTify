package com.example.hometestnew.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(SecurityConfig.class);
   

//    // Bean to configure CORS in Spring Security
//    @Bean
//    public CorsConfigurationSource customCorsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("https://hometestjavaprogrammer-production.up.railway.app");
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/registration", "/profile", "/profile/update", "/profile/image", "/banner", "/services", "/balance", "/topup", "/transaction", "/transaction/history", "/v1/api-docs", "/v2/api-docs", "/v3/api-docs","/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/auth-controller/**", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


}


