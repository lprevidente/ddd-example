package com.lprevidente.orgcraft.team.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record AddUserToTeam(@NotNull UUID teamId, @NotNull UUID userId) {}
