package com.lprevidente.ddd_example.user.application.command;

import com.lprevidente.ddd_example.user.api.UserId;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record DeleteUser(@NotNull UserId id) {}
