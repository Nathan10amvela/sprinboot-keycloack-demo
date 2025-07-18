package com.example.demo.config;

import com.example.demo.config.JwtConverter; // remplace avec ton vrai package
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class SecurityConfig {

  private final JwtConverter jwtConverter;

  public SecurityConfig(JwtConverter jwtConverter) {
    this.jwtConverter = jwtConverter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/public").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults()) // nouvelle façon recommandée
            .oauth2ResourceServer(resource -> resource
                    .jwt(jwt -> jwt
                            .jwtAuthenticationConverter(jwtConverter)
                    )
            );

    return http.build();
  }
}
