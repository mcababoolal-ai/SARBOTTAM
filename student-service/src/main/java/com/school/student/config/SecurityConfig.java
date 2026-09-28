package com.school.student.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {
  @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${app.security-enabled}") boolean enabled) throws Exception {
    http.csrf(csrf -> csrf.disable());
    if (enabled) http.authorizeHttpRequests(a -> a.requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated()).oauth2ResourceServer(o -> o.jwt());
    else http.authorizeHttpRequests(a -> a.anyRequest().permitAll());
    return http.build();
  }
}
