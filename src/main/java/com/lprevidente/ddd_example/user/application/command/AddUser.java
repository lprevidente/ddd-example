package com.lprevidente.ddd_example.user.application.command; // application/dto/UserRegistrationDTO.java

import com.lprevidente.ddd_example.pipeline.Command;
import com.lprevidente.ddd_example.user.domain.UserId;
import jakarta.validation.constraints.NotBlank;

public record AddUser(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String email,
    @NotBlank String password)
    implements Command<UserId> {}
