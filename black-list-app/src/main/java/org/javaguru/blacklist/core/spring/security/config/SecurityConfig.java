package org.javaguru.blacklist.core.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/blacklist/person/delete/**", "/blacklist/person/check/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/blacklist/persons/get/web").authenticated()
                        .requestMatchers("/blacklist/person/check/web").authenticated()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().permitAll())
                .httpBasic(httpBasic -> {})
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }
}
