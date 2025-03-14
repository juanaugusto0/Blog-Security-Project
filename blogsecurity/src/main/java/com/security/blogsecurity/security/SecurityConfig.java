package com.security.blogsecurity.security;

import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorizeRequests ->{
                    authorizeRequests.requestMatchers("/h2-console/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults())
            .csrf(csrf -> {
                csrf.ignoringRequestMatchers("/h2-console/**");
            })
            .headers(headers -> {
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
            })
        .build();
    }

}
