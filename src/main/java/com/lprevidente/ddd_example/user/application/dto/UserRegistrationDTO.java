package com.lprevidente.ddd_example.user.application.dto; // application/dto/UserRegistrationDTO.java

import jakarta.validation.constraints.NotBlank;

/** Data Transfer Object for user registration requests */
public record UserRegistrationDTO(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String email,
    @NotBlank String password) {}
