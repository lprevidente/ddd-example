package com.lprevidente.orgcraft.user.application.command;

import com.lprevidente.orgcraft.user.api.UserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record UpdateUser(
    @NotNull UserId id, //
    @NotBlank String firstName,
    @NotBlank String lastName) {}
