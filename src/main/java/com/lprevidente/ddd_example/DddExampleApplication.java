package com.lprevidente.ddd_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class DddExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(DddExampleApplication.class, args);
  }
}
