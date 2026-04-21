package com.lprevidente.ddd_example.team.application.command;

import com.lprevidente.ddd_example.team.domain.TeamId;
import com.lprevidente.ddd_example.team.domain.UserId;
import jakarta.validation.constraints.NotNull;
import org.jmolecules.architecture.cqrs.Command;

@Command
public record RemoveUserFromTeam(@NotNull TeamId teamId, @NotNull UserId userId) {}
