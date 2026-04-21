package com.lprevidente.ddd_example.user.application.command;

import com.lprevidente.ddd_example.user.domain.UserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record UpdateUser(
    @NotNull UserId id, //
    @NotBlank String firstName,
    @NotBlank String lastName) {}
