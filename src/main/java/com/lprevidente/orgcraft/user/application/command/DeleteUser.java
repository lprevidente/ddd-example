package com.lprevidente.orgcraft.user.application.command;

import com.lprevidente.orgcraft.user.api.UserId;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record DeleteUser(@NotNull UserId id) {}
