package com.lprevidente.ddd_example.team.application.command;

import com.lprevidente.ddd_example.pipeline.Command;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddUserToTeam(@NotNull UUID teamId, @NotNull UUID userId) implements Command<Void> {}
