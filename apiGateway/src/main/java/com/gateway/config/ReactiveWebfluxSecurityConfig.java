package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.POST;

@EnableWebFluxSecurity
public class ReactiveWebfluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //Disable csrf
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }
}
