package com.lprevidente.ddd_example.user.application.command;

import jakarta.validation.constraints.NotBlank;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record AddUser(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String email,
    @NotBlank String password) {}
