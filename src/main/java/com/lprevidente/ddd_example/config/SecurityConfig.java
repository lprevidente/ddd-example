package com.lprevidente.ddd_example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      // Desabilita CSRF (você já tinha)
      .csrf(AbstractHttpConfigurer::disable)
      // Permite que o console H2 carregue seus frames
      .headers(headers -> headers
        .frameOptions(frame -> frame.disable())
      )
      // Autoriza todas as requisições (inclui /h2-console)
      .authorizeHttpRequests(auth -> auth
        .anyRequest().permitAll()
      );

    return http.build();
  }
}
