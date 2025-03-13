package com.lprevidente.ddd_example.team.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RemoveUserFromTeam(@NotNull UUID teamId, @NotNull UUID userId) {}
